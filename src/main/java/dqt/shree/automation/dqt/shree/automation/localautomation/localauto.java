package dqt.shree.automation.dqt.shree.automation.localautomation;

import java.io.*;
//import java.nio.charset.StandardCharsets;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class localauto {

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

        postAPIrunner("http://localhost:8080//DQTAPIProject-0.0.1-SNAPSHOT/InvApi/getAllTransErrorsWithPagination/1/2", "C:\\API\\Requests\\pagination.txt", "C:\\API\\Local\\Responses\\pagination_local");
    }


    public static void getAllErrorFieldsForGrp() throws Exception {

        postAPIrunner("http://localhost:8080//DQTAPIProject-0.0.1-SNAPSHOT/InvApi/getAllErrorFieldsForGrp", "C:\\API\\Requests\\getAllErrorFieldsForGrp.txt", "C:\\API\\Local\\Responses\\getAllErrorFieldsForGrp_local");
    }


    public static void getTransErrors() throws Exception {

        postAPIrunner("http://localhost:8080//DQTAPIProject-0.0.1-SNAPSHOT/InvApi/getTransErrors/1/2", "C:\\API\\Requests\\getTransErrors.txt", "C:\\API\\Local\\Responses\\getTransErrors_local");
    }


    public static void getTransErrorsCount() throws Exception {

        postAPIrunner("http://localhost:8080//DQTAPIProject-0.0.1-SNAPSHOT/InvApi/getTransErrorsCount", "C:\\API\\Requests\\getTransErrorsCount.txt", "C:\\API\\Local\\Responses\\getTransErrorsCount_local");
    }


    public static void getFilteredTransErrors() throws Exception {

        postAPIrunner("http://localhost:8080//DQTAPIProject-0.0.1-SNAPSHOT/InvApi/getFilteredTransErrors/1/2", "C:\\API\\Requests\\getFilteredTransErrors.txt", "C:\\API\\Local\\Responses\\getFilteredTransErrors_local");
    }


    public static void updateBulkCorrectWithFilterGrp() throws Exception {

        postAPIrunner("http://localhost:8080//DQTAPIProject-0.0.1-SNAPSHOT/InvApi/updateBulkCorrectWithFilterGrp", "C:\\API\\Requests\\updateBulkCorrectWithFilterGrp.txt", "C:\\API\\Local\\Responses\\updateBulkCorrectWithFilterGrp_local");
    }


    public static void updateBulkIgnoreWithFilterGrp() throws Exception {

        postAPIrunner("http://localhost:8080//DQTAPIProject-0.0.1-SNAPSHOT/InvApi/updateBulkIgnoreWithFilterGrp", "C:\\API\\Requests\\updateBulkIgnoreWithFilterGrp.txt", "C:\\API\\Local\\Responses\\updateBulkIgnoreWithFilterGrp_local");
    }


    public static void getInvoice() throws Exception {


        DefaultHttpClient httpClient = new DefaultHttpClient();

        try {
            //Define a postRequest request
            HttpGet getRequest = new HttpGet("http://localhost:8080/DQTAPIProject-0.0.1-SNAPSHOT/InvApi/getInvoice/AABE10092019000000706188508291474T00000086051395444858770T1357001");
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
                FileWriter writer = new FileWriter("getinvoice_local" + " " + dateFormat.format(date) + ".txt", true);
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
    }


