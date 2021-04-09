package dqt.shree.automation.dqt.shree.automation.prodapi;

import com.github.wnameless.json.flattener.JsonFlattener;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.yaml.snakeyaml.Yaml;

import org.apache.http.params.HttpParams;

import java.io.*;
import java.net.SocketTimeoutException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProdSanity {

    private static String a0;
    private static String a1;
    private static String a2;
    private static String a3;
    private static String a4;
    private static String a5;
    private static String a6;
    private static String a7;
    private static String a8;
    private static String a9;
    private static String a10;
    private static String a11;
    private static String a12;
    private static String a13;
    private static String a14;
    private static String a15;
    private static String a16;
    private static String a17;
    private static String a18;
    private static String a19;
    private static String a20;
    private static String a21;
    private static String a22;
    private static String a23;
    private static String a24;
    private static String a25;
    private static String a26;
    private static String a27;
    private static String v;

    private static final String A= "yyyy-MM-dd HH-mm-ss";
    private static final String B="Daily Automation Suite";
    private static final String C="Response Time Issue";
    private static final String D="Zero data issue";
    private static final String E="totalCount";
    private static final String F="errortype";
    private static final String G="yyyy-dd-MM";
    private static final String H ="hh.mm aa";
    private static final String I ="Request Timeout";
    private static final String J="content-type";
    private static final String K="application/json";
    private static final String L= "Failed with HTTP error code :";
    private static final String M= "Time:";



    private static String responseBody;

    private static int count;
    private static int count1;

    static final Logger logger= Logger.getLogger(ProdSanity.class.getName());

    ProdSanity()
    {

    }

    public static void yamlFileToMap() throws FileNotFoundException {
        Yaml yaml = new Yaml();
        String path = "src/main/properties/prod_url.yaml";
        Map<String, String> map = yaml.load(new FileInputStream(new File(path)));

        a0 = map.get("transerrors_url");
        a1 = map.get("pagination_url");
        a2 = map.get("getAllErrorFieldsForGrp_url");
        a3 = map.get("getTransErrorsCount_url");
        a4 = map.get("getFilteredTransErrors_url");
        a5 = map.get("getInvoice_url");
        a6 = map.get("postTransSubmitCorrection_url");
        a7 = map.get("updateBulkCorrectWithFilterGrp_url");
        a8 = map.get("updateBulkIgnoreWithFilterGrp_url");

        String path2 = "src/main/properties/prod.yaml";
        Map<String, String> map2 = yaml.load(new FileInputStream(new File(path2)));

        a9 = map2.get("transerrors_source");
        a10 = map2.get("pagination_source");
        a11 = map2.get("getAllErrorFieldsForGrp_source");
        a12 = map2.get("getTransErrorsCount_source");
        a13 = map2.get("getFilteredTransErrors_source");
        a14 = map2.get("updateBulkCorrectWithFilterGrp_source");
        a15 = map2.get("updateBulkIgnoreWithFilterGrp_source");
        a16 = map2.get("transerrors_destination");
        a17 = map2.get("pagination_destination");
        a18 = map2.get("getAllErrorFieldsForGrp_destination");
        a19 = map2.get("getTransErrorsCount_destination");
        a20 = map2.get("getFilteredTransErrors_destination");
        a21 = map2.get("updateBulkCorrectWithFilterGrp_destination");
        a22 = map2.get("updateBulkIgnoreWithFilterGrp_destination");

        a23 = map2.get("transerrors_request");
        a24 = map2.get("getAllErrorFieldsForGrp_request");
        a25 = map2.get("getTransErrorsCount_request");
        a26 = map2.get("pagination_request");
        a27 = map2.get("getFilteredTransErrors_request");


    }


    public static StringEntity[] getRequestArray(String file) {
        List<String> words = readArray(file);

        int size = words.size();
        StringEntity[] postRequests = new StringEntity[size];

        for (int i = 0; i < words.size(); i = i + 1) {
            try {
                StringEntity entity = new StringEntity(words.get(i));
                postRequests[i] = entity;
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
        return postRequests;
    }

    public static List<String> readArray(String file) {
        String thisLine;
        List<String> list = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file)))
        {

            while ((thisLine = br.readLine()) != null) {
                if (!thisLine.trim().isEmpty())
                    list.add(thisLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }



        return list;
    }

    private static void postApiRunner(String url, String source, String destination, int timeout) {


        //Define a postRequest request
        HttpPost postRequest = new HttpPost(url);
        //Set the API media type in http content-type header
        postRequest.addHeader(J, K);

        StringEntity[] postRequests = getRequestArray(source);
        List<String> words = readArray(source);

        for (int i = 0; i < postRequests.length; i++) {

            DefaultHttpClient httpClient = new DefaultHttpClient();


            HttpParams httpParams = httpClient.getParams();
            httpParams.setParameter(CoreConnectionPNames.SO_TIMEOUT, timeout * 1000);

            try {

                postRequest.setEntity(postRequests[i]);
                long startTime = System.currentTimeMillis();

                HttpResponse response = httpClient.execute(postRequest);

                long elapsedTime = System.currentTimeMillis() - startTime;

                //verify the valid error code first
                int statusCode = response.getStatusLine().getStatusCode();

                if (statusCode != 200) {
                    throw new IOException(statusCode + " Failed with HTTP error code : " + statusCode);
                }
                String responseBody = EntityUtils.toString(response.getEntity());
                logger.info(responseBody);
                DateFormat dateFormat = new SimpleDateFormat(A);
                Date date = new Date();

                postApi(destination, words, i, elapsedTime, responseBody, dateFormat, date);

            } catch (SocketTimeoutException ex) {
                logger.info(I);

            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                //Important: Close the connect
                httpClient.getConnectionManager().shutdown();
            }

        }
    }

    private static void postApi(String destination, List<String> words, int i, long elapsedTime, String responseBody, DateFormat dateFormat, Date date) {
        try (FileWriter writer = new FileWriter(destination + " " + dateFormat.format(date) + ".txt", true))
        {
            writer.write("Request:" + " " + words.get(i) + System.lineSeparator() + "Response:" + " " + responseBody + System.lineSeparator() + M + elapsedTime + " ms" + System.lineSeparator());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void pagination() {

        postApiRunner(a1, a10, a17, 30);
    }


    public static void getAllErrorFieldsForGrp() {

        postApiRunner(a2, a11, a18, 35);
    }


    public static void getTransErrors()  {

        postApiRunner(a0, a9, a16, 30);
    }


    public static void getTransErrorsCount()  {

        postApiRunner(a3, a12, a19, 30);
    }


    public static void getFilteredTransErrors()  {

        postApiRunner(a4, a13, a20, 30);
    }


    public static void updateBulkCorrectWithFilterGrp()  {

        postApiRunner(a7, a14, a21, 30);
    }


    public static void updateBulkIgnoreWithFilterGrp()  {

        postApiRunner(a8, a15, a22, 30);
    }


    public static void getInvoice() {


        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet getRequest = new HttpGet(a5);
        //Set the API media type in http content-type header
        getRequest.addHeader(J, K);

        int timeout = 30;
        HttpParams httpParams = httpClient.getParams();
        httpParams.setParameter(CoreConnectionPNames.SO_TIMEOUT, timeout * 1000);


        try {

            long startTime = System.currentTimeMillis();
            //Send the request; It will immediately return the response in HttpResponse object if any

            HttpResponse response = httpClient.execute(getRequest);

            long elapsedTime = System.currentTimeMillis() - startTime;

            //verify the valid error code first
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                throw new IOException(L + statusCode);
            }
            //response.
            String responseBody = EntityUtils.toString(response.getEntity());
            logger.info(responseBody);

            abc(elapsedTime, responseBody);

        } catch (SocketTimeoutException ex) {
            logger.info(I);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            //Important: Close the connect
            httpClient.getConnectionManager().shutdown();
        }

    }

    private static void abc(long elapsedTime, String responseBody) {
        DateFormat dateFormat = new SimpleDateFormat(A);
        Date date = new Date();
        try(FileWriter writer = new FileWriter("getinvoice_prod" + " " + dateFormat.format(date) + ".txt", true)) {

            writer.write(responseBody + System.lineSeparator() + M + elapsedTime + " ms" );
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void postTransSubmitCorrection() throws IOException  {

        DefaultHttpClient httpClient = new DefaultHttpClient();

        HttpGet getRequest = new HttpGet(a5);
        getRequest.addHeader(J, K);


        HttpResponse response = httpClient.execute(getRequest);
        //verify the valid error code first
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != 200) {
            throw new IOException(L + statusCode);
        }
        String responseBody = EntityUtils.toString(response.getEntity());

        HttpPost postRequest = new HttpPost(a6);
        postRequest.addHeader(J, K);

        StringEntity[] postRequests = new StringEntity[1];
        StringEntity entity0 = new StringEntity(responseBody);

        postRequests[0] = entity0;

        int timeout = 300;
        HttpParams httpParams = httpClient.getParams();
        httpParams.setParameter(CoreConnectionPNames.SO_TIMEOUT, timeout * 1000);


        try {

            postRequest.setEntity(postRequests[0]);

            long startTime = System.currentTimeMillis();
            //Send the request; It will immediately return the response in HttpResponse object if any

            HttpResponse response2 = httpClient.execute(postRequest);

            long elapsedTime = System.currentTimeMillis() - startTime;

            int statusCode2 = response2.getStatusLine().getStatusCode();
            if (statusCode2 != 200) {
                throw new IOException(L + statusCode2);
            }
            String responseBody2 = EntityUtils.toString(response2.getEntity());
            logger.info(responseBody2);

            bcd(elapsedTime, responseBody2);

        } catch (SocketTimeoutException ex) {
            logger.info(I);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            //Important: Close the connect
            httpClient.getConnectionManager().shutdown();
        }

    }

    private static void bcd(long elapsedTime, String responseBody2) {
        DateFormat dateFormat = new SimpleDateFormat(A);
        Date date = new Date();
        try(FileWriter writer = new FileWriter("posttranssubmitcorrection_prod" + " " + dateFormat.format(date) + ".txt", true)) {
            writer.write(responseBody2 + System.lineSeparator() + M + elapsedTime + " ms" );
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void csvFile()  {

        DateFormat dateFormat0 = new SimpleDateFormat(A);
        Date date0 = new Date();
        String dt0 = dateFormat0.format(date0);
        try(PrintWriter pw = new PrintWriter(new File("C:\\API_CSV\\Prod\\" + "Prod " + dt0 + ".csv"))) {


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
            sb.append("UniqueKey");
            sb.append(",");
            sb.append("TotalCount/TransCount/ErrorCount");
            sb.append(",");
            sb.append(" Time Issues");
            sb.append(",");
            sb.append(" Zero Data Issue");
            sb.append(",");
            sb.append(" ErrorCount Mismatch");


            sb.append("\r\n");

            sb.append("Prod");
            sb.append(",");
            DateFormat dateFormat = new SimpleDateFormat(G);
            Date date = new Date();
            String dt = dateFormat.format(date);
            sb.append(dt);
            sb.append(",");
            DateFormat dateFormat2 = new SimpleDateFormat(H);
            Date date2 = new Date();
            String dt2 = dateFormat2.format(date2);
            sb.append(dt2);
            sb.append(",");
            sb.append("Transerrors");
            sb.append(",");

            transErr(sb);


            sb.append("\r\n");

            sb.append("Prod");
            sb.append(",");
            DateFormat dateFormat3 = new SimpleDateFormat(G);
            Date date3 = new Date();
            String dt3 = dateFormat3.format(date3);
            sb.append(dt3);
            sb.append(",");
            DateFormat dateFormat4 = new SimpleDateFormat(H);
            Date date4 = new Date();
            String dt4 = dateFormat4.format(date4);
            sb.append(dt4);
            sb.append(",");
            sb.append("AllErrorFieldsForGrp");
            sb.append(",");

            groupApi(sb);


            sb.append("\r\n");

            sb.append("Prod");
            sb.append(",");
            DateFormat dateFormat5 = new SimpleDateFormat(G);
            Date date5 = new Date();
            String dt5 = dateFormat5.format(date5);
            sb.append(dt5);
            sb.append(",");
            DateFormat dateFormat6 = new SimpleDateFormat(H);
            Date date6 = new Date();
            String dt6 = dateFormat6.format(date6);
            sb.append(dt6);
            sb.append(",");
            sb.append("TransErrorsCount");
            sb.append(",");

            transErrCnt(sb);

            sb.append("\r\n");

            sb.append("Prod");
            sb.append(",");
            DateFormat dateFormat7 = new SimpleDateFormat(G);
            Date date7 = new Date();
            String dt7 = dateFormat7.format(date7);
            sb.append(dt7);
            sb.append(",");
            DateFormat dateFormat8 = new SimpleDateFormat(H);
            Date date8 = new Date();
            String dt8 = dateFormat8.format(date8);
            sb.append(dt8);
            sb.append(",");

            sb.append("AllTransErrorsWithPagination");
            sb.append(",");

            allPgntn(sb);

            sb.append("\r\n");

            sb.append("Prod");
            sb.append(",");
            DateFormat dateFormat9 = new SimpleDateFormat(G);
            Date date9 = new Date();
            String dt9 = dateFormat9.format(date9);
            sb.append(dt9);
            sb.append(",");
            DateFormat dateFormat10 = new SimpleDateFormat(H);
            Date date10 = new Date();
            String dt10 = dateFormat10.format(date10);
            sb.append(dt10);
            sb.append(",");
            sb.append("FilteredTransErrors");
            sb.append(",");

            filtrdTrnsErr(sb);

            sb.append("\r\n");


            sb.append("Prod");
            sb.append(",");
            DateFormat dateFormat11 = new SimpleDateFormat(G);
            Date date11 = new Date();
            String dt11 = dateFormat11.format(date11);
            sb.append(dt11);
            sb.append(",");
            DateFormat dateFormat12 = new SimpleDateFormat(H);
            Date date12 = new Date();
            String dt12 = dateFormat12.format(date12);
            sb.append(dt12);
            sb.append(",");
            sb.append("getInvoice");
            sb.append(",");

            invoiceGet(sb);


            sb.append("\r\n");

            sb.append("Prod");
            sb.append(",");
            DateFormat dateFormat13 = new SimpleDateFormat(G);
            Date date13 = new Date();
            String dt13 = dateFormat13.format(date13);
            sb.append(dt13);
            sb.append(",");
            DateFormat dateFormat14 = new SimpleDateFormat(H);
            Date date14 = new Date();
            String dt14 = dateFormat14.format(date14);
            sb.append(dt14);
            sb.append(",");
            sb.append("posttranssubmitcorrection1");
            sb.append(",");

            pOSTTrans1(sb);
            sb.append("\r\n");


            sb.append("Prod");
            sb.append(",");
            DateFormat dateFormat15 = new SimpleDateFormat(G);
            Date date15 = new Date();
            String dt15 = dateFormat15.format(date15);
            sb.append(dt15);
            sb.append(",");
            DateFormat dateFormat16 = new SimpleDateFormat(H);
            Date date16 = new Date();
            String dt16 = dateFormat16.format(date16);
            sb.append(dt16);
            sb.append(",");
            sb.append("posttranssubmitcorrection2");
            sb.append(",");


            pOSTTrans2(sb);


            pw.write(sb.toString());


        } catch (Exception e) {
            e.printStackTrace();

        }


    }

    private static void groupApi(StringBuilder sb) throws IOException, JSONException {
        try {

            DefaultHttpClient httpClient2 = new DefaultHttpClient();
            HttpPost postRequest2 = new HttpPost(a2);
            postRequest2.addHeader(J, K);

            StringEntity[] postRequests2 = new StringEntity[1];
            StringEntity entity1 = new StringEntity(a24);
            postRequests2[0] = entity1;

            int timeout2 = 30;
            HttpParams httpParams2 = httpClient2.getParams();
            httpParams2.setParameter(CoreConnectionPNames.SO_TIMEOUT, timeout2 * 1000);

            postRequest2.setEntity(postRequests2[0]);
            long startTime2 = System.currentTimeMillis();
            //Send the request; It will immediately return the response in HttpResponse object if any
            HttpResponse response3 = httpClient2.execute(postRequest2);
            long elapsedTime2 = System.currentTimeMillis() - startTime2;
            String responseBody10 = EntityUtils.toString(response3.getEntity());

            sb.append(elapsedTime2);
            sb.append(",");
            sb.append(B);
            sb.append(",");
            sb.append("N/A");
            sb.append(",");
            JSONObject myResponse10 = new JSONObject(responseBody10);
            long x = myResponse10.getInt(E);
            sb.append(x);
            sb.append(",");

            if (elapsedTime2 > 3000) {
                sb.append(C);
            } else {
                sb.append("N/A");
            }
            sb.append(",");


            if (x == 0) {
                sb.append(D);
            } else {
                sb.append("N/A");
            }
            sb.append(",");
            sb.append("N/A");
        } catch (SocketTimeoutException ex) {
            logger.info("Request Timeout in AllErrorFieldsForGrp API");

            sb.append(I);
        }
    }

    private static void transErr(StringBuilder sb) throws IOException, JSONException {
        try {

            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost postRequest = new HttpPost(a0);
            postRequest.addHeader(J, K);

            StringEntity[] postRequests = new StringEntity[1];
            StringEntity entity0 = new StringEntity(a23);
            postRequests[0] = entity0;

            int timeout = 30;
            HttpParams httpParams = httpClient.getParams();
            httpParams.setParameter(CoreConnectionPNames.SO_TIMEOUT, timeout * 1000);

            postRequest.setEntity(postRequests[0]);
            long startTime = System.currentTimeMillis();
            //Send the request; It will immediately return the response in HttpResponse object if any
            HttpResponse response2 = httpClient.execute(postRequest);
            long elapsedTime = System.currentTimeMillis() - startTime;
            String responseBody9 = EntityUtils.toString(response2.getEntity());

            sb.append(elapsedTime);
            sb.append(",");
            sb.append(B);
            sb.append(",");
            sb.append("N/A");
            sb.append(",");

            JSONObject myResponse9 = new JSONObject(responseBody9);
            long w = myResponse9.getInt(E);
            sb.append(w);
            sb.append(",");

            if (elapsedTime > 3000) {
                sb.append(C);
            } else {
                sb.append("N/A");
            }
            sb.append(",");


            if (w == 0) {
                sb.append(D);
            } else {
                sb.append("N/A");
            }
            sb.append(",");
            sb.append("N/A");
        } catch (SocketTimeoutException ex) {
            logger.info("Request Timeout in Transerrors API");
            sb.append(I);
        }
    }

    private static void transErrCnt(StringBuilder sb) throws IOException, JSONException {
        try {

            DefaultHttpClient httpClient3 = new DefaultHttpClient();
            HttpPost postRequest3 = new HttpPost(a3);
            postRequest3.addHeader(J, K);

            StringEntity[] postRequests3 = new StringEntity[1];
            StringEntity entity2 = new StringEntity(a25);
            postRequests3[0] = entity2;

            int timeout3 = 30;
            HttpParams httpParams3 = httpClient3.getParams();
            httpParams3.setParameter(CoreConnectionPNames.SO_TIMEOUT, timeout3 * 1000);

            postRequest3.setEntity(postRequests3[0]);
            long startTime3 = System.currentTimeMillis();
            //Send the request; It will immediately return the response in HttpResponse object if any
            HttpResponse response4 = httpClient3.execute(postRequest3);
            long elapsedTime3 = System.currentTimeMillis() - startTime3;
            String responseBody11 = EntityUtils.toString(response4.getEntity());

            sb.append(elapsedTime3);
            sb.append(",");
            sb.append(B);
            sb.append(",");
            sb.append("N/A");
            sb.append(",");
            JSONObject myResponse11 = new JSONObject(responseBody11);
            JSONArray objJSONArray4 = myResponse11.getJSONArray("transErrCnt");

            JSONObject myResponse12 = objJSONArray4.getJSONObject(0);
            long y = myResponse12.getInt("transCount");
            sb.append(y);
            sb.append(",");


            if (elapsedTime3 > 3000) {
                sb.append(C);
            } else {
                sb.append("N/A");
            }
            sb.append(",");


            logger.log(Level.INFO, "transCount is :{0} ", y);


            if (y == 0) {
                sb.append(D);
            } else {
                sb.append("N/A");
            }
            sb.append(",");
            sb.append("N/A");
        } catch (SocketTimeoutException ex) {
            logger.info("Request Timeout in TransErrorsCount API");
            sb.append(I);
        }
    }

    private static void allPgntn(StringBuilder sb) throws IOException, JSONException {
        try {
            DefaultHttpClient httpClient4 = new DefaultHttpClient();
            HttpPost postRequest4 = new HttpPost(a1);
            postRequest4.addHeader(J, K);

            StringEntity[] postRequests4 = new StringEntity[1];
            StringEntity entity3 = new StringEntity(a26);
            postRequests4[0] = entity3;

            int timeout4 = 30;
            HttpParams httpParams4 = httpClient4.getParams();
            httpParams4.setParameter(CoreConnectionPNames.SO_TIMEOUT, timeout4 * 1000);

            postRequest4.setEntity(postRequests4[0]);
            long startTime4 = System.currentTimeMillis();
            //Send the request; It will immediately return the response in HttpResponse object if any
            HttpResponse response5 = httpClient4.execute(postRequest4);

            long elapsedTime4 = System.currentTimeMillis() - startTime4;
            String responseBody2 = EntityUtils.toString(response5.getEntity());

            JSONObject myResponse = new JSONObject(responseBody2);
            JSONArray objJSONArray = myResponse.getJSONArray("transErrors");

            JSONObject myResponse2 = objJSONArray.getJSONObject(1);
            v = myResponse2.getString("uniquekey");


            logger.log(Level.INFO, "uniquekeykey: {0} ", v);



            sb.append(elapsedTime4);
            sb.append(",");
            sb.append(B);
            sb.append(",");
            sb.append("N/A");
            sb.append(",");
            JSONObject myResponse13 = new JSONObject(responseBody2);
            long z = myResponse13.getInt(E);
            sb.append(z);
            sb.append(",");

            if (elapsedTime4 > 4000) {
                sb.append(C);
            } else {
                sb.append("N/A");
            }

            sb.append(",");


            if (z == 0) {
                sb.append(D);
            } else {
                sb.append("N/A");
            }
            sb.append(",");
            sb.append("N/A");
        } catch (SocketTimeoutException ex) {
            logger.info("Request Timeout in AllTransErrorsWithPagination API");
            sb.append(I);
        }
    }

    private static void filtrdTrnsErr(StringBuilder sb) throws IOException, JSONException {
        try {

            DefaultHttpClient httpClient5 = new DefaultHttpClient();
            HttpPost postRequest5 = new HttpPost(a4);
            postRequest5.addHeader(J, K);

            StringEntity[] postRequests5 = new StringEntity[1];
            StringEntity entity4 = new StringEntity(a27);
            postRequests5[0] = entity4;

            int timeout5 = 30;
            HttpParams httpParams5 = httpClient5.getParams();
            httpParams5.setParameter(CoreConnectionPNames.SO_TIMEOUT, timeout5 * 1000);
            postRequest5.setEntity(postRequests5[0]);
            long startTime5 = System.currentTimeMillis();
            //Send the request; It will immediately return the response in HttpResponse object if any
            HttpResponse response6 = httpClient5.execute(postRequest5);
            long elapsedTime5 = System.currentTimeMillis() - startTime5;
            String responseBody5 = EntityUtils.toString(response6.getEntity());

            sb.append(elapsedTime5);
            sb.append(",");
            sb.append(B);
            sb.append(",");
            sb.append("N/A");
            sb.append(",");
            JSONObject myResponse14 = new JSONObject(responseBody5);
            long s = myResponse14.getInt(E);
            sb.append(s);
            sb.append(",");

            if (elapsedTime5 > 6000) {
                sb.append(C);
            } else {
                sb.append("N/A");
            }

            sb.append(",");


            if (s == 0) {
                sb.append(D);
            } else {
                sb.append("N/A");
            }
            sb.append(",");
            sb.append("N/A");
        } catch (SocketTimeoutException ex) {
            logger.info("Request Timeout in FilteredTransErrors API");
            sb.append(I);
        }
    }

    private static void invoiceGet(StringBuilder sb) throws IOException, JSONException {
        try {

            DefaultHttpClient httpClient6 = new DefaultHttpClient();


            String url1 = "http://dataselfservice.bcdtravel.com:8080//DQTAPIProject-0.0.1-SNAPSHOT/InvApi/getInvoice/" + v;

            HttpGet getRequest = new HttpGet(url1);
            getRequest.addHeader(J, K);

            int timeout6 = 30;
            HttpParams httpParams6 = httpClient6.getParams();
            httpParams6.setParameter(CoreConnectionPNames.SO_TIMEOUT, timeout6 * 1000);


            long startTime6 = System.currentTimeMillis();
            //Send the request; It will immediately return the response in HttpResponse object if any

            HttpResponse response = httpClient6.execute(getRequest);

            long elapsedTime6 = System.currentTimeMillis() - startTime6;

            //verify the valid error code first

            responseBody = EntityUtils.toString(response.getEntity());
            logger.info(responseBody);

            def(elapsedTime6);


            String flattenJSONString = JsonFlattener.flatten(responseBody);
            JSONObject myResponse4 = new JSONObject(flattenJSONString.trim());
            Iterator<String> keys = myResponse4.keys();

            count = 0;

            while (keys.hasNext()) {
                String key = keys.next();
                if (key.contains(F)) {
                    count++;
                }
            }


            logger.log(Level.INFO, "total error count in getinvoice is: {0} ", count);



            sb.append(elapsedTime6);
            sb.append(",");
            sb.append(B);
            sb.append(",");
            sb.append(v);
            sb.append(",");
            sb.append(count);
            sb.append(",");

            if (elapsedTime6 > 3000) {
                sb.append(C);
            } else {
                sb.append("N/A");
            }
            sb.append(",");

            if (count == 0) {
                sb.append(D);
            } else {
                sb.append("N/A");
            }
            sb.append(",");
            sb.append("N/A");
        } catch (SocketTimeoutException ex) {
            logger.info("Request Timeout in getinvoice API");
            sb.append(I);
        }
    }

    private static void pOSTTrans2(StringBuilder sb) throws IOException, JSONException {
        try {

            DefaultHttpClient httpClient8 = new DefaultHttpClient();
            HttpPost postRequest8 = new HttpPost(a6);
            postRequest8.addHeader(J, K);

            StringEntity[] postRequests8 = new StringEntity[1];
            StringEntity entity8 = new StringEntity(responseBody);
            postRequests8[0] = entity8;

            int timeout8 = 120;
            HttpParams httpParams8 = httpClient8.getParams();
            httpParams8.setParameter(CoreConnectionPNames.SO_TIMEOUT, timeout8 * 1000);

            int count2;

            postRequest8.setEntity(postRequests8[0]);
            long startTime8 = System.currentTimeMillis();
            //Send the request; It will immediately return the response in HttpResponse object if any
            HttpResponse response8 = httpClient8.execute(postRequest8);
            long elapsedTime8 = System.currentTimeMillis() - startTime8;

            String responseBody4 = EntityUtils.toString(response8.getEntity());
            logger.info(responseBody4);


            String flattenJSONString3 = JsonFlattener.flatten(responseBody4);
            JSONObject myResponse8 = new JSONObject(flattenJSONString3.trim());
            Iterator<String> keys2 = myResponse8.keys();

            count2 = 0;

            while (keys2.hasNext()) {
                String key2 = keys2.next();
                if (key2.contains(F) && (!myResponse8.getString(key2).equals("INFORMATION"))) {
                    count2++;
                }
            }

            logger.log(Level.INFO, "total error count in posttranssubmitcorrection2 is: {0} ", count2);


            sb.append(elapsedTime8);
            sb.append(",");
            sb.append(B);
            sb.append(",");
            sb.append(v);
            sb.append(",");
            sb.append(count2);
            sb.append(",");

            if (elapsedTime8 > 15000) {
                sb.append(C);
            } else {
                sb.append("N/A");
            }
            sb.append(",");

            if(count2==0) {
                sb.append(D);
            }
            else {
                sb.append("N/A");
            }
            sb.append(",");


            if ((count != count1) || (count1 != count2)) {
                logger.info("there is a mismatch in errorcount. Please check");
                sb.append("Mismatch");
            } else {
                sb.append("No Mismatch");
            }
        } catch (SocketTimeoutException ex) {
            ex.printStackTrace();
            logger.info("Request Timeout in posttranssubmitcorrection2 API");
            sb.append(I);
        }
    }

    private static void pOSTTrans1(StringBuilder sb) throws IOException, JSONException {
        try {
            DefaultHttpClient httpClient7 = new DefaultHttpClient();
            HttpPost postRequest7 = new HttpPost(a6);
            postRequest7.addHeader(J, K);

            StringEntity[] postRequests7 = new StringEntity[1];
            StringEntity entity7 = new StringEntity(responseBody);
            postRequests7[0] = entity7;

            int timeout7 = 300;
            HttpParams httpParams7 = httpClient7.getParams();
            httpParams7.setParameter(CoreConnectionPNames.SO_TIMEOUT, timeout7 * 1000);


            postRequest7.setEntity(postRequests7[0]);
            long startTime7 = System.currentTimeMillis();
            //Send the request; It will immediately return the response in HttpResponse object if any
            HttpResponse response7 = httpClient7.execute(postRequest7);
            long elapsedTime7 = System.currentTimeMillis() - startTime7;

            String responseBody3 = EntityUtils.toString(response7.getEntity());
            logger.info(responseBody3);


            String flattenJSONString2 = JsonFlattener.flatten(responseBody3);
            JSONObject myResponse6 = new JSONObject(flattenJSONString2.trim());
            Iterator<String> keys1 = myResponse6.keys();

            count1 = 0;

            while (keys1.hasNext()) {
                String key1 =  keys1.next();
                if (key1.contains(F) && (!myResponse6.getString(key1).equals("INFORMATION"))) {
                    count1++;
                }
            }

            logger.log(Level.INFO, "total error count in posttranssubmitcorrection1 is: {0} ", count1);



            sb.append(elapsedTime7);
            sb.append(",");
            sb.append(B);
            sb.append(",");
            sb.append(v);
            sb.append(",");
            sb.append(count1);
            sb.append(",");

            if (elapsedTime7 > 180000) {
                sb.append(C);
            } else {
                sb.append("N/A");
            }
            sb.append(",");

            if(count1==0) {
                sb.append(D);
            }
            else {
                sb.append("N/A");
            }
            sb.append(",");
            sb.append("N/A");

        } catch (SocketTimeoutException ex) {
            ex.printStackTrace();
            logger.info("Request Timeout in posttranssubmitcorrection1 API");
            sb.append(I);
        }
    }


    private static void def(long elapsedTime6) {
        DateFormat dateFormat20 = new SimpleDateFormat(A);
        Date date20 = new Date();
        String x = "C:\\API_CSV\\CSV_GETINV\\getinvoice_dev";
        try (FileWriter writer = new FileWriter(x + " " + dateFormat20.format(date20) + ".txt", true)){
            writer.write("Response:" + " " + responseBody + "\nTime:" + elapsedTime6 + " ms" );
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
