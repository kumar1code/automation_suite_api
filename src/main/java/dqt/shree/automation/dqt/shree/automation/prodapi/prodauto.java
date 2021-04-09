package dqt.shree.automation.dqt.shree.automation.prodapi;
//import java.io.File;
//import java.nio.charset.StandardCharsets;

//import java.io.StringWriter;

import com.github.wnameless.json.flattener.JsonFlattener;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


public class prodauto {

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

    public static void postAPIrunner(String url, String source, String destination) {

        DefaultHttpClient httpClient = new DefaultHttpClient();
        //HttpRequestBase request;
        try {
//
            //Define a postRequest request
            HttpPost postRequest = new HttpPost(url);
            //Set the API media type in http content-type header
            postRequest.addHeader("content-type", "application/json");

            //Set the request post body
            //String filelocation= "C:\\core\\abc.txt";
            StringEntity[] postRequests = getRequestArray(source);
            List<String> words = readArray(source);

            for (int i = 0; i < postRequests.length; i++) {

                postRequest.setEntity(postRequests[i]);

                long startTime = System.currentTimeMillis();
                //Send the request; It will immediately return the response in HttpResponse object if any

                HttpResponse response = httpClient.execute(postRequest);

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
                    FileWriter writer = new FileWriter(destination + " " + dateFormat.format(date) + ".txt", true);
                    writer.write("Request:" + " " + words.get(i) + System.lineSeparator() + "Response:" + " " + responseBody + "\nTime:" + elapsedTime + " ms" + System.lineSeparator());
                    writer.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        } catch (Exception ex) {
            String exc = ex.getMessage();
            System.out.println(exc);
        } finally {
            //Important: Close the connect
            httpClient.getConnectionManager().shutdown();
        }
    }


    public static void pagination() throws Exception {

        postAPIrunner("http://dataselfservice.bcdtravel.com:8080//DQTAPIProject-0.0.1-SNAPSHOT/InvApi/getAllTransErrorsWithPagination/1/2", "C:\\API\\Requests\\pagination.txt", "C:\\API\\Prod\\Responses\\pagination_prod");
    }


    public static void getAllErrorFieldsForGrp() throws Exception {

        postAPIrunner("http://dataselfservice.bcdtravel.com:8080//DQTAPIProject-0.0.1-SNAPSHOT/InvApi/getAllErrorFieldsForGrp", "C:\\API\\Requests\\getAllErrorFieldsForGrp.txt", "C:\\API\\Prod\\Responses\\getAllErrorFieldsForGrp_prod");
    }


    public static void getTransErrors() throws Exception {

        postAPIrunner("http://dataselfservice.bcdtravel.com:8080//DQTAPIProject-0.0.1-SNAPSHOT/InvApi/getTransErrors/1/2", "C:\\API\\Requests\\getTransErrors.txt", "C:\\API\\Prod\\Responses\\getTransErrors_prod");
    }


    public static void getTransErrorsCount() throws Exception {

        postAPIrunner("http://dataselfservice.bcdtravel.com:8080//DQTAPIProject-0.0.1-SNAPSHOT/InvApi/getTransErrorsCount", "C:\\API\\Requests\\getTransErrorsCount.txt", "C:\\API\\Prod\\Responses\\getTransErrorsCount_prod");
    }


    public static void getFilteredTransErrors() throws Exception {

        postAPIrunner("http://dataselfservice.bcdtravel.com:8080//DQTAPIProject-0.0.1-SNAPSHOT/InvApi/getFilteredTransErrors/1/2", "C:\\API\\Requests\\getFilteredTransErrors.txt", "C:\\API\\Prod\\Responses\\getFilteredTransErrors_prod");
    }


    public static void updateBulkCorrectWithFilterGrp() throws Exception {

        postAPIrunner("http://dataselfservice.bcdtravel.com:8080//DQTAPIProject-0.0.1-SNAPSHOT/InvApi/updateBulkCorrectWithFilterGrp", "C:\\API\\Requests\\updateBulkCorrectWithFilterGrp.txt", "C:\\API\\Prod\\Responses\\updateBulkCorrectWithFilterGrp_prod");
    }


    public static void updateBulkIgnoreWithFilterGrp() throws Exception {

        postAPIrunner("http://dataselfservice.bcdtravel.com:8080//DQTAPIProject-0.0.1-SNAPSHOT/InvApi/updateBulkIgnoreWithFilterGrp", "C:\\API\\Requests\\updateBulkIgnoreWithFilterGrp.txt", "C:\\API\\Prod\\Responses\\updateBulkIgnoreWithFilterGrp_prod");
    }


    public static void getInvoice() throws Exception {


        DefaultHttpClient httpClient = new DefaultHttpClient();

        try {
            //Define a postRequest request
            HttpGet getRequest = new HttpGet("http://dataselfservice.bcdtravel.com:8080/DQTAPIProject-0.0.1-SNAPSHOT/InvApi/getInvoice/20190708CAIC1830271681");
            //Set the API media type in http content-type header
            getRequest.addHeader("content-type", "application/json");

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
        try {
            HttpGet getRequest = new HttpGet("http://dataselfservice.bcdtravel.com:8080/DQTAPIProject-0.0.1-SNAPSHOT/InvApi/getInvoice/20190708CAIC1830271681");
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

            HttpPost postRequest = new HttpPost("http://dataselfservice.bcdtravel.com:8080//DQTAPIProject-0.0.1-SNAPSHOT/InvApi/postTransSubmitCorrection");
            postRequest.addHeader("content-type", "application/json");

            StringEntity[] postRequests = new StringEntity[1];
            StringEntity entity0 = new StringEntity(responseBody);

            postRequests[0] = entity0;

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
            String dt0=dateFormat0.format(date0);
            PrintWriter pw = new PrintWriter(new File("C:\\API_CSV\\PROD\\"+"Prod "+dt0+".csv"));
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
            String dt=dateFormat.format(date);
            sb.append(dt);
            sb.append(",");
            DateFormat dateFormat2 = new SimpleDateFormat("hh.mm aa");
            Date date2 = new Date();
            String dt2=dateFormat2.format(date2);
            sb.append(dt2);
            sb.append(",");
            sb.append("Transerrors");
            sb.append(",");

            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost postRequest = new HttpPost("http://dataselfservice.bcdtravel.com:8080//DQTAPIProject-0.0.1-SNAPSHOT/InvApi/getTransErrors/1/2");
            postRequest.addHeader("content-type", "application/json");

            StringEntity[] postRequests = new StringEntity[1];
            StringEntity entity0 = new StringEntity(("{\"include\":[{\"globalclientcd\":[\"4420\",\"3190\"]}],\"includeCriticalErrors\":\"1\"}"));
            postRequests[0] = entity0;
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

            if (elapsedTime>3000)
            {
                sb.append("Response Time Issue");
            }
            else
            {
                sb.append("N/A");
            }
            sb.append(",");


            if(w==0)
            {
                sb.append("Zero data issue");
            }
            else
            {
                sb.append("N/A");
            }
            sb.append(",");
            sb.append("N/A");


            sb.append("\r\n");

            sb.append("PROD");
            sb.append(",");
            DateFormat dateFormat3 = new SimpleDateFormat("yyyy-dd-MM");
            Date date3 = new Date();
            String dt3=dateFormat3.format(date3);
            sb.append(dt3);
            sb.append(",");
            DateFormat dateFormat4 = new SimpleDateFormat("hh.mm aa");
            Date date4 = new Date();
            String dt4=dateFormat4.format(date4);
            sb.append(dt4);
            sb.append(",");
            sb.append("AllErrorFieldsForGrp");
            sb.append(",");

            DefaultHttpClient httpClient2 = new DefaultHttpClient();
            HttpPost postRequest2 = new HttpPost("http://dataselfservice.bcdtravel.com:8080//DQTAPIProject-0.0.1-SNAPSHOT/InvApi/getAllErrorFieldsForGrp");
            postRequest2.addHeader("content-type", "application/json");

            StringEntity[] postRequests2 = new StringEntity[1];
            StringEntity entity1 = new StringEntity(("{\"include\":[{\"dataid\":[\"IV\"]}],\"exclude\":[],\"includeCriticalErrors\":\"1\",\"filter\":[],\"group\":{\"name\":\"globalclientcd\",\"value\":\"1052\"}}"));
            postRequests2[0] = entity1;
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

            if (elapsedTime2>3000)
            {
                sb.append("Response Time Issue");
            }
            else
            {
                sb.append("N/A");
            }
            sb.append(",");


            if(x==0)
            {
                sb.append("Zero data issue");
            }
            else
            {
                sb.append("N/A");
            }
            sb.append(",");
            sb.append("N/A");


            sb.append("\r\n");

            sb.append("PROD");
            sb.append(",");
            DateFormat dateFormat5 = new SimpleDateFormat("yyyy-dd-MM");
            Date date5 = new Date();
            String dt5=dateFormat5.format(date5);
            sb.append(dt5);
            sb.append(",");
            DateFormat dateFormat6 = new SimpleDateFormat("hh.mm aa");
            Date date6 = new Date();
            String dt6=dateFormat6.format(date6);
            sb.append(dt6);
            sb.append(",");
            sb.append("TransErrorsCount");
            sb.append(",");

            DefaultHttpClient httpClient3 = new DefaultHttpClient();
            HttpPost postRequest3 = new HttpPost("http://dataselfservice.bcdtravel.com:8080//DQTAPIProject-0.0.1-SNAPSHOT/InvApi/getTransErrorsCount");
            postRequest3.addHeader("content-type", "application/json");

            StringEntity[] postRequests3 = new StringEntity[1];
            StringEntity entity2 = new StringEntity(("{\"groups\":[{\"groupId\":\"admin2\",\"include\":[{\"dataid\": [\"IC\"],\"globalclientcd\": [\"1003\"]}],\"exclude\":[],\"includeCriticalErrors\": \"1\"}]}\n"));
            postRequests3[0] = entity2;
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
            JSONArray obj_JSONArray4=myResponse11.getJSONArray("transErrCnt");

            JSONObject myResponse12 = obj_JSONArray4.getJSONObject(0);
            long y= myResponse12.getInt("transCount");
            sb.append(y);
            sb.append(",");


            if (elapsedTime3>3000)
            {
                sb.append("Response Time Issue");
            }
            else
            {
                sb.append("N/A");
            }
            sb.append(",");


            System.out.println("transCount is " + y);

            if(y==0)
            {
                sb.append("Zero data issue");
            }
            else
            {
                sb.append("N/A");
            }
            sb.append(",");
            sb.append("N/A");



            sb.append("\r\n");

            sb.append("PROD");
            sb.append(",");

            DateFormat dateFormat7 = new SimpleDateFormat("yyyy-dd-MM");
            Date date7 = new Date();
            String dt7=dateFormat7.format(date7);
            sb.append(dt7);
            sb.append(",");

            DateFormat dateFormat8 = new SimpleDateFormat("hh.mm aa");
            Date date8 = new Date();
            String dt8=dateFormat8.format(date8);
            sb.append(dt8);
            sb.append(",");

            sb.append("AllTransErrorsWithPagination");
            sb.append(",");

            DefaultHttpClient httpClient4 = new DefaultHttpClient();
            HttpPost postRequest4 = new HttpPost("http://dataselfservice.bcdtravel.com:8080//DQTAPIProject-0.0.1-SNAPSHOT/InvApi/getAllTransErrorsWithPagination/1/2");
            postRequest4.addHeader("content-type", "application/json");

            StringEntity[] postRequests4 = new StringEntity[1];
            StringEntity entity3 = new StringEntity(("{\"groupId\":\"eX_dataid_In_rest_0\",\"include\":[{\"globalclientcd\":[\"4420\",\"3190\"]}],\"includeCriticalErrors\":\"0\"}"));
            postRequests4[0] = entity3;
            postRequest4.setEntity(postRequests4[0]);
            long startTime4 = System.currentTimeMillis();
            //Send the request; It will immediately return the response in HttpResponse object if any
            HttpResponse response5 = httpClient4.execute(postRequest4);
            long elapsedTime4 = System.currentTimeMillis() - startTime4;
            String responseBody2 = EntityUtils.toString(response5.getEntity());

            JSONObject myResponse = new JSONObject(responseBody2);
            JSONArray obj_JSONArray=myResponse.getJSONArray("transErrors");

            JSONObject myResponse2 = obj_JSONArray.getJSONObject(0);
            String v= myResponse2.getString("uniquekey");

            //long v = myResponse.getInt("uniquekey");
            System.out.println("uniquekeykey "+ v);



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

            if (elapsedTime4>4000)
            {
                sb.append("Response Time Issue");
            }
            else
            {
                sb.append("N/A");
            }

            sb.append(",");


            if(z==0)
            {
                sb.append("Zero data issue");
            }
            else
            {
                sb.append("N/A");
            }
            sb.append(",");
            sb.append("N/A");

            sb.append("\r\n");

            sb.append("PROD");
            sb.append(",");
            DateFormat dateFormat9 = new SimpleDateFormat("yyyy-dd-MM");
            Date date9 = new Date();
            String dt9=dateFormat9.format(date9);
            sb.append(dt9);
            sb.append(",");
            DateFormat dateFormat10 = new SimpleDateFormat("hh.mm aa");
            Date date10 = new Date();
            String dt10=dateFormat10.format(date10);
            sb.append(dt10);
            sb.append(",");
            sb.append("FilteredTransErrors");
            sb.append(",");

            DefaultHttpClient httpClient5 = new DefaultHttpClient();
            HttpPost postRequest5 = new HttpPost("http://dataselfservice.bcdtravel.com:8080//DQTAPIProject-0.0.1-SNAPSHOT/InvApi/getFilteredTransErrors/1/500");
            postRequest5.addHeader("content-type", "application/json");

            StringEntity[] postRequests5 = new StringEntity[1];
            StringEntity entity4 = new StringEntity(("{\"include\":[{\"globalclientcd\":[\"1003\"]}],\"exclude\":[{\"dataid\":[\"IN\"]}],\"includeCriticalErrors\":\"1\",\"filter\":[{\"column\":\"globalclientname\",\"value\":\"MARSH & MCLELLAN COMPANIES (MMC)\"}],\"order\":{\"column\":\"errorDesc\",\"value\":\"asc\"},\"combined\":{\"column\":\"errorType\"}}"));
            postRequests5[0] = entity4;
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

            if (elapsedTime5>6000)
            {
                sb.append("Response Time Issue");
            }
            else
            {
                sb.append("N/A");
            }

            sb.append(",");


            if(s==0)
            {
                sb.append("Zero data issue");
            }
            else
            {
                sb.append("N/A");
            }
            sb.append(",");
            sb.append("N/A");

            sb.append("\r\n");

            sb.append("PROD");
            sb.append(",");
            DateFormat dateFormat11 = new SimpleDateFormat("yyyy-dd-MM");
            Date date11 = new Date();
            String dt11=dateFormat11.format(date11);
            sb.append(dt11);
            sb.append(",");
            DateFormat dateFormat12 = new SimpleDateFormat("hh.mm aa");
            Date date12 = new Date();
            String dt12=dateFormat12.format(date12);
            sb.append(dt12);
            sb.append(",");
            sb.append("getInvoice");
            sb.append(",");

            DefaultHttpClient httpClient6 = new DefaultHttpClient();
            String ukey=v;

            String url1= "http://dataselfservice.bcdtravel.com:8080/DQTAPIProject-0.0.1-SNAPSHOT/InvApi/getInvoice/" + ukey;

            HttpGet getRequest = new HttpGet(url1);
            getRequest.addHeader("content-type", "application/json");
            long startTime6 = System.currentTimeMillis();
            //Send the request; It will immediately return the response in HttpResponse object if any

            HttpResponse response = httpClient6.execute(getRequest);

            long elapsedTime6 = System.currentTimeMillis() - startTime6;

            //verify the valid error code first

            HttpEntity response1 = response.getEntity();
            String responseBody = EntityUtils.toString(response.getEntity());
            System.out.println(responseBody);

            JSONObject myResponse3 = new JSONObject(responseBody);
            //String flattenJSON = JsonFlattener.flatten(myResponse3.toString());
            String flattenJSONString = JsonFlattener.flatten(responseBody);
            JSONObject myResponse4 = new JSONObject(flattenJSONString.trim());
            Iterator<String> keys=myResponse4.keys();

            int count=0;

            while(keys.hasNext())
            {
                String key = keys.next();
                if(key.contains("errortype"))
                {
                    count++;
                }
            }


            System.out.println("total error count in getinvoice is "+ count);
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

            if (elapsedTime6>3000)
            {
                sb.append("Response Time Issue");
            }
            else
            {
                sb.append("N/A");
            }
            sb.append(",");
            sb.append("N/A");
            sb.append(",");
            sb.append("N/A");

            sb.append("\r\n");

            sb.append("PROD");
            sb.append(",");
            DateFormat dateFormat13 = new SimpleDateFormat("yyyy-dd-MM");
            Date date13 = new Date();
            String dt13=dateFormat13.format(date13);
            sb.append(dt13);
            sb.append(",");
            DateFormat dateFormat14 = new SimpleDateFormat("hh.mm aa");
            Date date14 = new Date();
            String dt14=dateFormat14.format(date14);
            sb.append(dt14);
            sb.append(",");
            sb.append("posttranssubmitcorrection1");
            sb.append(",");

            DefaultHttpClient httpClient7 = new DefaultHttpClient();
            HttpPost postRequest7 = new HttpPost("http://dataselfservice.bcdtravel.com:8080//DQTAPIProject-0.0.1-SNAPSHOT/InvApi/postTransSubmitCorrection");
            postRequest7.addHeader("content-type", "application/json");

            StringEntity[] postRequests7 = new StringEntity[1];
            StringEntity entity7 = new StringEntity(responseBody);
            postRequests7[0] = entity7;
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
            Iterator<String> keys1=myResponse6.keys();

            int count1=0;

            while(keys1.hasNext())
            {
                String key1 = keys1.next();
                if(key1.contains("errortype") && (!myResponse6.getString(key1).equals("INFORMATION")) )
                {
                    count1++;
                }
            }

            System.out.println("total error count in posttranssubmitcorrection1 is "+ count1);



            sb.append(elapsedTime7);
            sb.append(",");
            sb.append("Daily Automation Suite");
            sb.append(",");
            sb.append(ukey);
            sb.append(",");
            sb.append(count1);
            sb.append(",");

            if (elapsedTime7>180000)
            {
                sb.append("Response Time Issue");
            }
            else
            {
                sb.append("N/A");
            }
            sb.append(",");
            sb.append("N/A");
            sb.append(",");
            sb.append("N/A");

            sb.append("\r\n");


            sb.append("PROD");
            sb.append(",");
            DateFormat dateFormat15 = new SimpleDateFormat("yyyy-dd-MM");
            Date date15 = new Date();
            String dt15=dateFormat15.format(date15);
            sb.append(dt15);
            sb.append(",");
            DateFormat dateFormat16 = new SimpleDateFormat("hh.mm aa");
            Date date16 = new Date();
            String dt16=dateFormat16.format(date16);
            sb.append(dt16);
            sb.append(",");
            sb.append("posttranssubmitcorrection2");
            sb.append(",");

            DefaultHttpClient httpClient8 = new DefaultHttpClient();
            HttpPost postRequest8 = new HttpPost("http://dataselfservice.bcdtravel.com:8080//DQTAPIProject-0.0.1-SNAPSHOT/InvApi/postTransSubmitCorrection");
            postRequest8.addHeader("content-type", "application/json");

            StringEntity[] postRequests8 = new StringEntity[1];
            StringEntity entity8 = new StringEntity(responseBody);
            postRequests8[0] = entity8;
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
            Iterator<String> keys2=myResponse8.keys();

            int count2=0;

            while(keys2.hasNext())
            {
                String key2 = keys2.next();
                if(key2.contains("errortype") && (!myResponse8.getString(key2).equals("INFORMATION")) )
                {
                    count2++;
                }
            }

            System.out.println("total error count in posttranssubmitcorrection2 is "+ count2);

            sb.append(elapsedTime8);
            sb.append(",");
            sb.append("Daily Automation Suite");
            sb.append(",");
            sb.append(ukey);
            sb.append(",");
            sb.append(count2);
            sb.append(",");

            if (elapsedTime8>15000)
            {
                sb.append("Response Time Issue");
            }
            else
            {
                sb.append("N/A");
            }
            sb.append(",");
            sb.append("N/A");
            sb.append(",");


            if((count!=count1) || (count1!=count2) || (count!=count2))
            {
                System.out.println("there is a mismatch in errorcount. Please check");
                sb.append("Mismatch");
            }
            else {
                sb.append("No Mismatch");
            }



            pw.write(sb.toString());
            pw.close();



        }
        catch(Exception e)
        {
            e.printStackTrace();

        }




    }
}












