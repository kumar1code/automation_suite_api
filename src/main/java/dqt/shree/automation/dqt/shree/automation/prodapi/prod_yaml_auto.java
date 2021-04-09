package dqt.shree.automation.dqt.shree.automation.prodapi;

import com.github.wnameless.json.flattener.JsonFlattener;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.yaml.snakeyaml.Yaml;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.SocketTimeoutException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class prod_yaml_auto {

    static String a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20, a21, a22, a23, a24, a25, a26, a27, v;
    static String responseBody;
    static int count, count1, count2;

    public  static void yamlFileToMap() throws FileNotFoundException {
        Yaml yaml = new Yaml();
        String path = "src/main/properties/prod_url.yaml";
        Map<String,String> map = yaml.load(new FileInputStream(new File(path)));

        a0=map.get("transerrors_url");
        a1=map.get("pagination_url");
        a2=map.get("getAllErrorFieldsForGrp_url");
        a3=map.get("getTransErrorsCount_url");
        a4=map.get("getFilteredTransErrors_url");
        a5=map.get("getInvoice_url");
        a6=map.get("postTransSubmitCorrection_url");
        a7= map.get("updateBulkCorrectWithFilterGrp_url");
        a8= map.get("updateBulkIgnoreWithFilterGrp_url");

        String path2="src/main/properties/prod.yaml";
        Map<String,String> map2 = yaml.load(new FileInputStream(new File(path2)));

        a9 =map2.get("transerrors_source");
        a10=map2.get("pagination_source");
        a11=map2.get("getAllErrorFieldsForGrp_source");
        a12=map2.get("getTransErrorsCount_source");
        a13=map2.get("getFilteredTransErrors_source");
        a14=map2.get("updateBulkCorrectWithFilterGrp_source");
        a15=map2.get("updateBulkIgnoreWithFilterGrp_source");
        a16=map2.get("transerrors_destination");
        a17=map2.get("pagination_destination");
        a18=map2.get("getAllErrorFieldsForGrp_destination");
        a19=map2.get("getTransErrorsCount_destination");
        a20=map2.get("getFilteredTransErrors_destination");
        a21=map2.get("updateBulkCorrectWithFilterGrp_destination");
        a22=map2.get("updateBulkIgnoreWithFilterGrp_destination");

        a23=map2.get("transerrors_request");
        a24=map2.get("getAllErrorFieldsForGrp_request");
        a25=map2.get("getTransErrorsCount_request");
        a26=map2.get("pagination_request");
        a27=map2.get("getFilteredTransErrors_request");

    }


    public static StringEntity[] getRequestArray(String file) {
        List<String> words = readArray(file);

        int size = words.size();
        StringEntity[] postRequests = new StringEntity[size];

        for (int i = 0; i < words.size(); i = i + 1) {
            try {
                StringEntity entity = new StringEntity(words.get(i));
                //System.out.println(words.get(i));
                //StringEntity entity1 = new StringEntity("{\"include\":[{\"dataid\": [\"ZT\"]}],\"exclude\": [],\"includeCriticalErrors\":\"0\",\"filter\": [{\"column\": \"errorDesc\",\"value\": \"RJ-Invalid Currency Code|Invoice_GEN\"}],\"order\": {\"column\": \"globalclientcd\",\"value\": \"asc\"},\"combined\": {\"column\": \"dataid\"}}");

                postRequests[i] = entity;
                //postRequests[1] = entity1;
            } catch (Exception ex) {
                String exc = ex.getMessage();
                System.out.println(exc);
            }

        }
        return postRequests;
    }

    public static List<String> readArray(String file) {
        String thisLine = null;
        List<String> list = new ArrayList<String>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            while ((thisLine = br.readLine()) != null) {
                if (!thisLine.trim().isEmpty())
                    list.add(thisLine);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void postAPIrunner(String url, String source, String destination, int timeout) {


        //Define a postRequest request
        HttpPost postRequest = new HttpPost(url);
        //Set the API media type in http content-type header
        postRequest.addHeader("content-type", "application/json");
        // postRequest.addHeader("content-type", "application/json");


        //Set the request post body

        StringEntity[] postRequests = getRequestArray(source);
        List<String> words = readArray(source);

        for (int i = 0; i < postRequests.length; i++) {

            DefaultHttpClient httpClient = new DefaultHttpClient();
            //HttpRequestBase request;


            HttpParams httpParams = httpClient.getParams();
            //httpParams.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, timeout * 1000);
            httpParams.setParameter(CoreConnectionPNames.SO_TIMEOUT, timeout * 1000);

            try {

                postRequest.setEntity(postRequests[i]);
                //System.out.println("length = " + postRequests.length);
                long startTime = System.currentTimeMillis();
                //Send the request; It will immediately return the response in HttpResponse object if any

                HttpResponse response = httpClient.execute(postRequest);

                long elapsedTime = System.currentTimeMillis() - startTime;

                //verify the valid error code first
                int statusCode = response.getStatusLine().getStatusCode();
                //System.out.println("Status code is " + statusCode);

//                if(statusCode==408)
//                {
//                    System.out.println("Request timeout(Statuscode:408)");
//                }
                if (statusCode != 200) {
                    throw new RuntimeException(statusCode + " Failed with HTTP error code : " + statusCode);
                }
                //response.
                HttpEntity response1 = response.getEntity();
                String responseBody = EntityUtils.toString(response.getEntity());
                System.out.println(responseBody);
                try {
                    DateFormat dateFormat = new SimpleDateFormat("(yyyy-MM-dd HH-mm-ss)");
                    Date date = new Date();
                    FileWriter writer = new FileWriter(destination + " " + dateFormat.format(date) + ".txt", true);
                    writer.write("Request:" + " " + words.get(i) + System.lineSeparator() + "Response:" + " " + responseBody + "\nTime:" + elapsedTime + " ms" + System.lineSeparator());
                    writer.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (SocketTimeoutException ex) {
                String exc = ex.getMessage();
                System.out.println("Request Timeout");
            } catch (Exception ex) {
                String exc = ex.getMessage();
                System.out.println(exc);
            } finally {
                //Important: Close the connect
                httpClient.getConnectionManager().shutdown();
            }

        }
    }


    public static void pagination() throws Exception {

        postAPIrunner(a1, a10, a17, 30);
    }


    public static void getAllErrorFieldsForGrp() throws Exception {

        postAPIrunner(a2, a11, a18, 30);
    }


    public static void getTransErrors() throws Exception {

        postAPIrunner(a0, a9, a16, 30);
    }


    public static void getTransErrorsCount() throws Exception {

        postAPIrunner(a3, a12, a19, 30);
    }


    public static void getFilteredTransErrors() throws Exception {

        postAPIrunner(a4, a13, a20, 30);
    }


    public static void updateBulkCorrectWithFilterGrp() throws Exception {

        postAPIrunner(a7, a14, a21, 30);
    }


    public static void updateBulkIgnoreWithFilterGrp() throws Exception {

        postAPIrunner(a8, a15, a22, 30);
    }


    public static void getInvoice() throws Exception {


        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet getRequest = new HttpGet(a5);
        //Set the API media type in http content-type header
        getRequest.addHeader("content-type", "application/json");

        int timeout = 30;
        HttpParams httpParams = httpClient.getParams();
        //httpParams.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, timeout * 1000);
        httpParams.setParameter(CoreConnectionPNames.SO_TIMEOUT, timeout * 1000);


        try {

            long startTime = System.currentTimeMillis();
            //Send the request; It will immediately return the response in HttpResponse object if any

            HttpResponse response = httpClient.execute(getRequest);

            long elapsedTime = System.currentTimeMillis() - startTime;

            //verify the valid error code first
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                throw new RuntimeException("Failed with HTTP error code : " + statusCode);
            }
            //response.
            HttpEntity response1 = response.getEntity();
            String responseBody = EntityUtils.toString(response.getEntity());
            System.out.println(responseBody);
            try {
                DateFormat dateFormat = new SimpleDateFormat("(yyyy-MM-dd HH-mm-ss)");
                Date date = new Date();
                FileWriter writer = new FileWriter("getinvoice_prod" + " " + dateFormat.format(date) + ".txt", true);
                writer.write(responseBody + "\nTime:" + elapsedTime + " ms" + System.lineSeparator());
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (SocketTimeoutException ex) {
            String exc = ex.getMessage();
            System.out.println("Request Timeout");
        } catch (Exception ex) {
            String exc = ex.getMessage();
            System.out.println(exc);
        } finally {
            //Important: Close the connect
            httpClient.getConnectionManager().shutdown();
        }

    }

    public static void postTransSubmitCorrection() throws Exception {

        DefaultHttpClient httpClient = new DefaultHttpClient();

        HttpGet getRequest = new HttpGet(a5);
        getRequest.addHeader("content-type", "application/json");
        //long startTime = System.currentTimeMillis();
        //Send the request; It will immediately return the response in HttpResponse object if any

        HttpResponse response = httpClient.execute(getRequest);

        //long elapsedTime = System.currentTimeMillis() - startTime;

        //verify the valid error code first
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != 200) {
            throw new RuntimeException("Failed with HTTP error code : " + statusCode);
        }
        HttpEntity response1 = response.getEntity();
        String responseBody = EntityUtils.toString(response.getEntity());
        //System.out.println(responseBody);

        HttpPost postRequest = new HttpPost(a6);
        postRequest.addHeader("content-type", "application/json");

        StringEntity[] postRequests = new StringEntity[1];
        StringEntity entity0 = new StringEntity(responseBody);

        postRequests[0] = entity0;

        int timeout = 300;
        HttpParams httpParams = httpClient.getParams();
        //httpParams.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, timeout * 1000);
        httpParams.setParameter(CoreConnectionPNames.SO_TIMEOUT, timeout * 1000);


        try {

            postRequest.setEntity(postRequests[0]);

            long startTime = System.currentTimeMillis();
            //Send the request; It will immediately return the response in HttpResponse object if any

            HttpResponse response2 = httpClient.execute(postRequest);

            long elapsedTime = System.currentTimeMillis() - startTime;

            int statusCode2 = response2.getStatusLine().getStatusCode();
            if (statusCode2 != 200) {
                throw new RuntimeException("Failed with HTTP error code : " + statusCode2);
            }
            //response.
            HttpEntity response3 = response2.getEntity();
            String responseBody2 = EntityUtils.toString(response2.getEntity());
            System.out.println(responseBody2);

            try {
                DateFormat dateFormat = new SimpleDateFormat("(yyyy-MM-dd HH-mm-ss)");
                Date date = new Date();
                FileWriter writer = new FileWriter("posttranssubmitcorrection_prod" + " " + dateFormat.format(date) + ".txt", true);
                writer.write(responseBody2 + "\nTime:" + elapsedTime + " ms" + System.lineSeparator());
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }


        } catch (SocketTimeoutException ex) {
            String exc = ex.getMessage();
            System.out.println("Request Timeout");
        } catch (Exception ex) {
            String exc = ex.getMessage();
            System.out.println(exc);
        } finally {
            //Important: Close the connect
            httpClient.getConnectionManager().shutdown();
        }

    }

    public static void csvFile() throws Exception {

        try {
            DateFormat dateFormat0 = new SimpleDateFormat("dd-MM-yyyy hh-mm-ss");
            Date date0 = new Date();
            String dt0 = dateFormat0.format(date0);
            PrintWriter pw = new PrintWriter(new File("C:\\API_CSV\\PROD\\" + "Prod " + dt0 + ".csv"));
            StringBuilder sb = new StringBuilder();
            sb.append("Environment");
            sb.append(",");
            sb.append("Date");
            sb.append(",");
            sb.append("Time");
            sb.append(",");
            sb.append("API_Name");
            sb.append(",");
            sb.append("Response Time");
            sb.append(",");
            sb.append("Remarks");
            sb.append(",");
            sb.append("Uniquekey");
            sb.append(",");
            sb.append("Totalcount/ErrorCount");
            sb.append(",");
            sb.append(" Time Issues");
            sb.append(",");
            sb.append(" Zero Data Issue");
            sb.append(",");
            sb.append(" Errorcount Mismatch");


            sb.append("\r\n");

            sb.append("PROD");
            sb.append(",");
            DateFormat dateFormat = new SimpleDateFormat("yyyy-dd-MM");
            Date date = new Date();
            String dt = dateFormat.format(date);
            sb.append(dt);
            sb.append(",");
            DateFormat dateFormat2 = new SimpleDateFormat("hh.mm aa");
            Date date2 = new Date();
            String dt2 = dateFormat2.format(date2);
            sb.append(dt2);
            sb.append(",");
            sb.append("Transerrors");
            sb.append(",");

            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost postRequest = new HttpPost(a0);
            postRequest.addHeader("content-type", "application/json");

            StringEntity[] postRequests = new StringEntity[1];
            StringEntity entity0 = new StringEntity(a23);
            postRequests[0] = entity0;

            int timeout = 30;
            HttpParams httpParams = httpClient.getParams();
            //httpParams.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, timeout * 1000);
            httpParams.setParameter(CoreConnectionPNames.SO_TIMEOUT, timeout * 1000);

            try {
                postRequest.setEntity(postRequests[0]);
                long startTime = System.currentTimeMillis();
                //Send the request; It will immediately return the response in HttpResponse object if any
                HttpResponse response2 = httpClient.execute(postRequest);
                long elapsedTime = System.currentTimeMillis() - startTime;
                String responseBody9 = EntityUtils.toString(response2.getEntity());

                sb.append(elapsedTime);
                sb.append(",");
                sb.append("Daily Automation Suite");
                sb.append(",");
                sb.append("N/A");
                sb.append(",");

                JSONObject myResponse9 = new JSONObject(responseBody9);
                long w = myResponse9.getInt("totalCount");
                sb.append(w);
                sb.append(",");

                if (elapsedTime > 3000) {
                    sb.append("Response Time Issue");
                } else {
                    sb.append("N/A");
                }
                sb.append(",");


                if (w == 0) {
                    sb.append("Zero data issue");
                } else {
                    sb.append("N/A");
                }
                sb.append(",");
                sb.append("N/A");
            } catch (SocketTimeoutException ex) {
                String exc = ex.getMessage();
                System.out.println("Request Timeout in Transerrors API");
                sb.append("Request Timeout");
            }


            sb.append("\r\n");

            sb.append("PROD");
            sb.append(",");
            DateFormat dateFormat3 = new SimpleDateFormat("yyyy-dd-MM");
            Date date3 = new Date();
            String dt3 = dateFormat3.format(date3);
            sb.append(dt3);
            sb.append(",");
            DateFormat dateFormat4 = new SimpleDateFormat("hh.mm aa");
            Date date4 = new Date();
            String dt4 = dateFormat4.format(date4);
            sb.append(dt4);
            sb.append(",");
            sb.append("AllErrorFieldsForGrp");
            sb.append(",");

            DefaultHttpClient httpClient2 = new DefaultHttpClient();
            HttpPost postRequest2 = new HttpPost(a2);
            postRequest2.addHeader("content-type", "application/json");

            StringEntity[] postRequests2 = new StringEntity[1];
            StringEntity entity1 = new StringEntity(a24);
            postRequests2[0] = entity1;

            int timeout2 = 30;
            HttpParams httpParams2 = httpClient2.getParams();
            //httpParams.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, timeout * 1000);
            httpParams2.setParameter(CoreConnectionPNames.SO_TIMEOUT, timeout2 * 1000);

            try {

                postRequest2.setEntity(postRequests2[0]);
                long startTime2 = System.currentTimeMillis();
                //Send the request; It will immediately return the response in HttpResponse object if any
                HttpResponse response3 = httpClient2.execute(postRequest2);
                long elapsedTime2 = System.currentTimeMillis() - startTime2;
                String responseBody10 = EntityUtils.toString(response3.getEntity());

                sb.append(elapsedTime2);
                sb.append(",");
                sb.append("Daily Automation Suite");
                sb.append(",");
                sb.append("N/A");
                sb.append(",");
                JSONObject myResponse10 = new JSONObject(responseBody10);
                long x = myResponse10.getInt("totalCount");
                sb.append(x);
                sb.append(",");

                if (elapsedTime2 > 3000) {
                    sb.append("Response Time Issue");
                } else {
                    sb.append("N/A");
                }
                sb.append(",");


                if (x == 0) {
                    sb.append("Zero data issue");
                } else {
                    sb.append("N/A");
                }
                sb.append(",");
                sb.append("N/A");
            } catch (SocketTimeoutException ex) {
                String exc = ex.getMessage();
                System.out.println("Request Timeout in AllErrorFieldsForGrp API");
                sb.append("Request Timeout");
            }


            sb.append("\r\n");

            sb.append("PROD");
            sb.append(",");
            DateFormat dateFormat5 = new SimpleDateFormat("yyyy-dd-MM");
            Date date5 = new Date();
            String dt5 = dateFormat5.format(date5);
            sb.append(dt5);
            sb.append(",");
            DateFormat dateFormat6 = new SimpleDateFormat("hh.mm aa");
            Date date6 = new Date();
            String dt6 = dateFormat6.format(date6);
            sb.append(dt6);
            sb.append(",");
            sb.append("TransErrorsCount");
            sb.append(",");

            DefaultHttpClient httpClient3 = new DefaultHttpClient();
            HttpPost postRequest3 = new HttpPost(a3);
            postRequest3.addHeader("content-type", "application/json");

            StringEntity[] postRequests3 = new StringEntity[1];
            StringEntity entity2 = new StringEntity(a25);
            postRequests3[0] = entity2;

            int timeout3 = 30;
            HttpParams httpParams3 = httpClient3.getParams();
            //httpParams.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, timeout * 1000);
            httpParams3.setParameter(CoreConnectionPNames.SO_TIMEOUT, timeout3 * 1000);

            try {


                postRequest3.setEntity(postRequests3[0]);
                long startTime3 = System.currentTimeMillis();
                //Send the request; It will immediately return the response in HttpResponse object if any
                HttpResponse response4 = httpClient3.execute(postRequest3);
                long elapsedTime3 = System.currentTimeMillis() - startTime3;
                String responseBody11 = EntityUtils.toString(response4.getEntity());

                sb.append(elapsedTime3);
                sb.append(",");
                sb.append("Daily Automation Suite");
                sb.append(",");
                sb.append("N/A");
                sb.append(",");
                JSONObject myResponse11 = new JSONObject(responseBody11);
                JSONArray obj_JSONArray4 = myResponse11.getJSONArray("transErrCnt");

                JSONObject myResponse12 = obj_JSONArray4.getJSONObject(0);
                long y = myResponse12.getInt("transCount");
                sb.append(y);
                sb.append(",");


                if (elapsedTime3 > 3000) {
                    sb.append("Response Time Issue");
                } else {
                    sb.append("N/A");
                }
                sb.append(",");


                System.out.println("transCount is " + y);

                if (y == 0) {
                    sb.append("Zero data issue");
                } else {
                    sb.append("N/A");
                }
                sb.append(",");
                sb.append("N/A");
            } catch (SocketTimeoutException ex) {
                String exc = ex.getMessage();
                System.out.println("Request Timeout in TransErrorsCount API");
                sb.append("Request Timeout");
            }

            sb.append("\r\n");

            sb.append("PROD");
            sb.append(",");

            DateFormat dateFormat7 = new SimpleDateFormat("yyyy-dd-MM");
            Date date7 = new Date();
            String dt7 = dateFormat7.format(date7);
            sb.append(dt7);
            sb.append(",");

            DateFormat dateFormat8 = new SimpleDateFormat("hh.mm aa");
            Date date8 = new Date();
            String dt8 = dateFormat8.format(date8);
            sb.append(dt8);
            sb.append(",");

            sb.append("AllTransErrorsWithPagination");
            sb.append(",");

            DefaultHttpClient httpClient4 = new DefaultHttpClient();
            HttpPost postRequest4 = new HttpPost(a1);
            postRequest4.addHeader("content-type", "application/json");

            StringEntity[] postRequests4 = new StringEntity[1];
            StringEntity entity3 = new StringEntity(a26);
            postRequests4[0] = entity3;

            int timeout4 = 30;
            HttpParams httpParams4 = httpClient4.getParams();
            //httpParams.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, timeout * 1000);
            httpParams4.setParameter(CoreConnectionPNames.SO_TIMEOUT, timeout4 * 1000);

            try {
                postRequest4.setEntity(postRequests4[0]);
                long startTime4 = System.currentTimeMillis();
                //Send the request; It will immediately return the response in HttpResponse object if any
                HttpResponse response5 = httpClient4.execute(postRequest4);
                long elapsedTime4 = System.currentTimeMillis() - startTime4;
                String responseBody2 = EntityUtils.toString(response5.getEntity());

                JSONObject myResponse = new JSONObject(responseBody2);
                JSONArray obj_JSONArray = myResponse.getJSONArray("transErrors");

                JSONObject myResponse2 = obj_JSONArray.getJSONObject(0);
                v = myResponse2.getString("uniquekey");

                //long v = myResponse.getInt("uniquekey");
                System.out.println("uniquekeykey " + v);


                sb.append(elapsedTime4);
                sb.append(",");
                sb.append("Daily Automation Suite");
                sb.append(",");
                sb.append("N/A");
                sb.append(",");
                JSONObject myResponse13 = new JSONObject(responseBody2);
                long z = myResponse13.getInt("totalCount");
                sb.append(z);
                sb.append(",");

                if (elapsedTime4 > 4000) {
                    sb.append("Response Time Issue");
                } else {
                    sb.append("N/A");
                }

                sb.append(",");


                if (z == 0) {
                    sb.append("Zero data issue");
                } else {
                    sb.append("N/A");
                }
                sb.append(",");
                sb.append("N/A");
            } catch (SocketTimeoutException ex) {
                String exc = ex.getMessage();
                System.out.println("Request Timeout in AllTransErrorsWithPagination API");
                sb.append("Request Timeout");
            }

            sb.append("\r\n");

            sb.append("PROD");
            sb.append(",");
            DateFormat dateFormat9 = new SimpleDateFormat("yyyy-dd-MM");
            Date date9 = new Date();
            String dt9 = dateFormat9.format(date9);
            sb.append(dt9);
            sb.append(",");
            DateFormat dateFormat10 = new SimpleDateFormat("hh.mm aa");
            Date date10 = new Date();
            String dt10 = dateFormat10.format(date10);
            sb.append(dt10);
            sb.append(",");
            sb.append("FilteredTransErrors");
            sb.append(",");

            DefaultHttpClient httpClient5 = new DefaultHttpClient();
            HttpPost postRequest5 = new HttpPost(a4);
            postRequest5.addHeader("content-type", "application/json");

            StringEntity[] postRequests5 = new StringEntity[1];
            StringEntity entity4 = new StringEntity(a27);
            postRequests5[0] = entity4;

            int timeout5 = 30;
            HttpParams httpParams5 = httpClient5.getParams();
            //httpParams.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, timeout * 1000);
            httpParams5.setParameter(CoreConnectionPNames.SO_TIMEOUT, timeout5 * 1000);

            try {
                postRequest5.setEntity(postRequests5[0]);
                long startTime5 = System.currentTimeMillis();
                //Send the request; It will immediately return the response in HttpResponse object if any
                HttpResponse response6 = httpClient5.execute(postRequest5);
                long elapsedTime5 = System.currentTimeMillis() - startTime5;
                String responseBody5 = EntityUtils.toString(response6.getEntity());

                sb.append(elapsedTime5);
                sb.append(",");
                sb.append("Daily Automation Suite");
                sb.append(",");
                sb.append("N/A");
                sb.append(",");
                JSONObject myResponse14 = new JSONObject(responseBody5);
                long s = myResponse14.getInt("totalCount");
                sb.append(s);
                sb.append(",");

                if (elapsedTime5 > 6000) {
                    sb.append("Response Time Issue");
                } else {
                    sb.append("N/A");
                }

                sb.append(",");


                if (s == 0) {
                    sb.append("Zero data issue");
                } else {
                    sb.append("N/A");
                }
                sb.append(",");
                sb.append("N/A");
            } catch (SocketTimeoutException ex) {
                String exc = ex.getMessage();
                System.out.println("Request Timeout in FilteredTransErrors API");
                sb.append("Request Timeout");
            }

            sb.append("\r\n");

            sb.append("PROD");
            sb.append(",");
            DateFormat dateFormat11 = new SimpleDateFormat("yyyy-dd-MM");
            Date date11 = new Date();
            String dt11 = dateFormat11.format(date11);
            sb.append(dt11);
            sb.append(",");
            DateFormat dateFormat12 = new SimpleDateFormat("hh.mm aa");
            Date date12 = new Date();
            String dt12 = dateFormat12.format(date12);
            sb.append(dt12);
            sb.append(",");
            sb.append("getInvoice");
            sb.append(",");

            DefaultHttpClient httpClient6 = new DefaultHttpClient();
            String ukey = v;

            String url1 = "http://dataselfservice.bcdtravel.com:8080/DQTAPIProject-0.0.1-SNAPSHOT/InvApi/getInvoice/" + ukey;

            HttpGet getRequest = new HttpGet(url1);
            getRequest.addHeader("content-type", "application/json");

            int timeout6 = 30;
            HttpParams httpParams6 = httpClient6.getParams();
            //httpParams.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, timeout * 1000);
            httpParams6.setParameter(CoreConnectionPNames.SO_TIMEOUT, timeout6 * 1000);

            try {
                long startTime6 = System.currentTimeMillis();
                //Send the request; It will immediately return the response in HttpResponse object if any

                HttpResponse response = httpClient6.execute(getRequest);

                long elapsedTime6 = System.currentTimeMillis() - startTime6;

                //verify the valid error code first

                HttpEntity response1 = response.getEntity();
                responseBody = EntityUtils.toString(response.getEntity());
                System.out.println(responseBody);

                try {
                    DateFormat dateFormat20 = new SimpleDateFormat("(yyyy-MM-dd HH-mm-ss)");
                    Date date20 = new Date();
                    String x = "C:\\API_CSV\\CSV_GETINV\\getinvoice_prod" ;
                    FileWriter writer = new FileWriter(x + " " + dateFormat20.format(date20) + ".txt", true);
                    writer.write("Response:" + " " + responseBody + "\nTime:" + elapsedTime6 + " ms" + System.lineSeparator());
                    writer.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                JSONObject myResponse3 = new JSONObject(responseBody);
                //String flattenJSON = JsonFlattener.flatten(myResponse3.toString());
                String flattenJSONString = JsonFlattener.flatten(responseBody);
                JSONObject myResponse4 = new JSONObject(flattenJSONString.trim());
                Iterator<String> keys = myResponse4.keys();

                count = 0;

                while (keys.hasNext()) {
                    String key = keys.next();
                    if (key.contains("errortype")) {
                        count++;
                    }
                }


                System.out.println("total error count in getinvoice is " + count);
                //System.out.println(flattenJSONString);

//		    Map<String,Object>flattenJSONMap= JsonFlattener.flattenAsMap(myResponse3.toString());
//
//		    System.out.println(flattenJSONMap);


                sb.append(elapsedTime6);
                sb.append(",");
                sb.append("Daily Automation Suite");
                sb.append(",");
                sb.append(ukey);
                sb.append(",");
                sb.append(count);
                sb.append(",");

                if (elapsedTime6 > 3000) {
                    sb.append("Response Time Issue");
                } else {
                    sb.append("N/A");
                }
                sb.append(",");

                if(count==0) {
                    sb.append("Zero data issue");
                }
                else {
                    sb.append("N/A");
                }
                sb.append(",");
                sb.append("N/A");
            } catch (SocketTimeoutException ex) {
                String exc = ex.getMessage();
                System.out.println("Request Timeout in getinvoice API");
                sb.append("Request Timeout");
            }

            sb.append("\r\n");

            sb.append("PROD");
            sb.append(",");
            DateFormat dateFormat13 = new SimpleDateFormat("yyyy-dd-MM");
            Date date13 = new Date();
            String dt13 = dateFormat13.format(date13);
            sb.append(dt13);
            sb.append(",");
            DateFormat dateFormat14 = new SimpleDateFormat("hh.mm aa");
            Date date14 = new Date();
            String dt14 = dateFormat14.format(date14);
            sb.append(dt14);
            sb.append(",");
            sb.append("posttranssubmitcorrection1");
            sb.append(",");

            DefaultHttpClient httpClient7 = new DefaultHttpClient();
            HttpPost postRequest7 = new HttpPost(a6);
            postRequest7.addHeader("content-type", "application/json");

            StringEntity[] postRequests7 = new StringEntity[1];
            StringEntity entity7 = new StringEntity(responseBody);
            postRequests7[0] = entity7;

            int timeout7 = 300;
            HttpParams httpParams7 = httpClient7.getParams();
            //httpParams.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, timeout * 1000);
            httpParams7.setParameter(CoreConnectionPNames.SO_TIMEOUT, timeout7 * 1000);

            try {
                postRequest7.setEntity(postRequests7[0]);
                long startTime7 = System.currentTimeMillis();
                //Send the request; It will immediately return the response in HttpResponse object if any
                HttpResponse response7 = httpClient7.execute(postRequest7);
                long elapsedTime7 = System.currentTimeMillis() - startTime7;

                String responseBody3 = EntityUtils.toString(response7.getEntity());
                System.out.println(responseBody3);

                JSONObject myResponse5 = new JSONObject(responseBody3);
                //String flattenJSON = JsonFlattener.flatten(myResponse3.toString());
                String flattenJSONString2 = JsonFlattener.flatten(responseBody3);
                JSONObject myResponse6 = new JSONObject(flattenJSONString2.trim());
                Iterator<String> keys1 = myResponse6.keys();

                count1 = 0;

                while (keys1.hasNext()) {
                    String key1 = keys1.next();
                    if (key1.contains("errortype") && (!myResponse6.getString(key1).equals("INFORMATION"))) {
                        count1++;
                    }
                }

                System.out.println("total error count in posttranssubmitcorrection1 is " + count1);


                sb.append(elapsedTime7);
                sb.append(",");
                sb.append("Daily Automation Suite");
                sb.append(",");
                sb.append(ukey);
                sb.append(",");
                sb.append(count1);
                sb.append(",");

                if (elapsedTime7 > 180000) {
                    sb.append("Response Time Issue");
                } else {
                    sb.append("N/A");
                }
                sb.append(",");

                if(count1==0) {
                    sb.append("Zero data issue");
                }
                else {
                    sb.append("N/A");
                }
                sb.append(",");
                sb.append("N/A");

            } catch (SocketTimeoutException ex) {
                String exc = ex.getMessage();
                System.out.println("Request Timeout in posttranssubmitcorrection1 API");
                sb.append("Request Timeout");
            }

            sb.append("\r\n");


            sb.append("PROD");
            sb.append(",");
            DateFormat dateFormat15 = new SimpleDateFormat("yyyy-dd-MM");
            Date date15 = new Date();
            String dt15 = dateFormat15.format(date15);
            sb.append(dt15);
            sb.append(",");
            DateFormat dateFormat16 = new SimpleDateFormat("hh.mm aa");
            Date date16 = new Date();
            String dt16 = dateFormat16.format(date16);
            sb.append(dt16);
            sb.append(",");
            sb.append("posttranssubmitcorrection2");
            sb.append(",");

            DefaultHttpClient httpClient8 = new DefaultHttpClient();
            HttpPost postRequest8 = new HttpPost(a6);
            postRequest8.addHeader("content-type", "application/json");

            StringEntity[] postRequests8 = new StringEntity[1];
            StringEntity entity8 = new StringEntity(responseBody);
            postRequests8[0] = entity8;

            int timeout8 = 120;
            HttpParams httpParams8 = httpClient8.getParams();
            //httpParams.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, timeout * 1000);
            httpParams8.setParameter(CoreConnectionPNames.SO_TIMEOUT, timeout8 * 1000);

            try {
                postRequest8.setEntity(postRequests8[0]);
                long startTime8 = System.currentTimeMillis();
                //Send the request; It will immediately return the response in HttpResponse object if any
                HttpResponse response8 = httpClient8.execute(postRequest8);
                long elapsedTime8 = System.currentTimeMillis() - startTime8;

                String responseBody4 = EntityUtils.toString(response8.getEntity());
                System.out.println(responseBody4);

                JSONObject myResponse7 = new JSONObject(responseBody4);
                //String flattenJSON = JsonFlattener.flatten(myResponse3.toString());
                String flattenJSONString3 = JsonFlattener.flatten(responseBody4);
                JSONObject myResponse8 = new JSONObject(flattenJSONString3.trim());
                Iterator<String> keys2 = myResponse8.keys();

                count2 = 0;

                while (keys2.hasNext()) {
                    String key2 = keys2.next();
                    if (key2.contains("errortype") && (!myResponse8.getString(key2).equals("INFORMATION"))) {
                        count2++;
                    }
                }

                System.out.println("total error count in posttranssubmitcorrection2 is " + count2);

                sb.append(elapsedTime8);
                sb.append(",");
                sb.append("Daily Automation Suite");
                sb.append(",");
                sb.append(ukey);
                sb.append(",");
                sb.append(count2);
                sb.append(",");

                if (elapsedTime8 > 15000) {
                    sb.append("Response Time Issue");
                } else {
                    sb.append("N/A");
                }
                sb.append(",");

                if(count2==0) {
                    sb.append("Zero data issue");
                }
                else {
                    sb.append("N/A");
                }
                sb.append(",");


                if ((count != count1) || (count1 != count2) || (count != count2)) {
                    System.out.println("there is a mismatch in errorcount. Please check");
                    sb.append("Mismatch");
                } else {
                    sb.append("No Mismatch");
                }
            } catch (SocketTimeoutException ex) {
                String exc = ex.getMessage();
                System.out.println("Request Timeout in posttranssubmitcorrection2 API");
                sb.append("Request Timeout");
            }


            pw.write(sb.toString());
            pw.close();


        } catch (Exception e) {
            e.printStackTrace();

        }


    }
}
