package dqt.shree.automation.dqt.shree.automation.textread;

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
//import java.text.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ReadingFile {


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

        public static void postAPIrunner(String url, String source, String destination, long maxTime) {

            DateFormat dateFormat1 = new SimpleDateFormat("(yyyy-MM-dd)");
            Date date1 = new Date();
            String path="C:\\API\\QA\\"+dateFormat1.format(date1);
            File Dir=new File(path);
            if (!Dir.exists()) {
                if (Dir.mkdir()) {
                    System.out.println("file created successfully");
                }
                else {
                    System.out.println("file failed to be created ");
                }
            }
            else
            {
                System.out.println("file already exists");
            }
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

                    String generalTime="Time taken is less than the desired "+ maxTime;

                    if (elapsedTime>maxTime)
                    {
                        generalTime="Time taken is more than the desired  "+ maxTime;

                     }

                    //verify the valid error code first
                    int statusCode = response.getStatusLine().getStatusCode();
                    if (statusCode != 200)
                    {
                        throw new RuntimeException("Failed with HTTP error code : " + statusCode);
                    }
                    //HttpEntity response1 = response.getEntity();
                    String responseBody = EntityUtils.toString(response.getEntity());
                    System.out.println(responseBody);

                    System.out.println(generalTime+" ms"+ "  Current time taken is "+ elapsedTime+ " ms");

                    JSONObject myResponse = new JSONObject(responseBody);
                    //JSONArray myResponse2= new JSONArray(responseBody);

                    long v;
                    if(myResponse.has("totalCount"))
                    {
                         v = myResponse.getInt("totalCount");
                         if(v==0)
                         {
                             System.out.println("totalcount is 0,check the request");
                         }
                         System.out.println("totalcount of particular api is "+ v);
                    }

                    else if(myResponse.has("transErrCnt"))
                    {
                        v = myResponse.getInt("transCount");
                        if(v==0)
                        {
                            System.out.println("transcount is 0,check the request");
                        }
                        System.out.println("transcount of particular api is "+ v);
                    }

                    try {
                        DateFormat dateFormat = new SimpleDateFormat("( HH-mm-ss)");
                        Date date = new Date();
                        FileWriter writer = new FileWriter( path +"\\"+ destination + " " + dateFormat.format(date) + ".txt", true);
                        writer.write("Request:" + " " + words.get(i) + System.lineSeparator() + "Response:" + " " + responseBody + "\nTime:" + elapsedTime + " ms" + System.lineSeparator()+ generalTime +" ms"+ System.lineSeparator());
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


        public static void pagination() throws Exception
        {
            String path="pagination_qa";
            postAPIrunner("http://uscdc01tljdv003.globalservices.bcdtravel.local:8080//DQTAPIProject-0.0.1-SNAPSHOT/InvApi/getAllTransErrorsWithPagination/1/2", "C:\\API\\Requests\\pagination.txt",  path, 4000 );
        }

        public static void getAllErrorFieldsForGrp() throws Exception
        {
            String path="getAllErrorFieldsForGrp_qa";
            postAPIrunner("http://uscdc01tljdv003.globalservices.bcdtravel.local:8080//DQTAPIProject-0.0.1-SNAPSHOT/InvApi/getAllErrorFieldsForGrp", "C:\\API\\Requests\\getAllErrorFieldsForGrp.txt", path,3000);
        }


        public static void getTransErrors() throws Exception
        {
            String path="getTransErrors_qa";
            postAPIrunner("http://uscdc01tljdv003.globalservices.bcdtravel.local:8080//DQTAPIProject-0.0.1-SNAPSHOT/InvApi/getTransErrors/1/2", "C:\\API\\Requests\\getTransErrors.txt", path,3000);
        }


        public static void getTransErrorsCount() throws Exception
        {
            String path="getTransErrorsCount_qa";
            postAPIrunner("http://uscdc01tljdv003.globalservices.bcdtravel.local:8080//DQTAPIProject-0.0.1-SNAPSHOT/InvApi/getTransErrorsCount", "C:\\API\\Requests\\getTransErrorsCount.txt", path,3000);
        }


        public static void getFilteredTransErrors() throws Exception
        {
            String path="getFilteredTransErrors_qa";
            postAPIrunner("http://uscdc01tljdv003.globalservices.bcdtravel.local:8080//DQTAPIProject-0.0.1-SNAPSHOT/InvApi/getFilteredTransErrors/1/500", "C:\\API\\Requests\\getFilteredTransErrors.txt", path, 4000);
        }


        public static void updateBulkCorrectWithFilterGrp() throws Exception
        {
            String path="updateBulkCorrectWithFilterGrp_qa";
            postAPIrunner("http://uscdc01tljdv003.globalservices.bcdtravel.local:8080//DQTAPIProject-0.0.1-SNAPSHOT/InvApi/updateBulkCorrectWithFilterGrp", "C:\\API\\Requests\\updateBulkCorrectWithFilterGrp.txt", path,2500);
        }


        public static void updateBulkIgnoreWithFilterGrp() throws Exception
        {
            String path="updateBulkIgnoreWithFilterGrp_qa";
            postAPIrunner("http://uscdc01tljdv003.globalservices.bcdtravel.local:8080//DQTAPIProject-0.0.1-SNAPSHOT/InvApi/updateBulkIgnoreWithFilterGrp", "C:\\API\\Requests\\updateBulkIgnoreWithFilterGrp.txt", path,2500);
        }


        public static void getInvoice() throws Exception {


            DefaultHttpClient httpClient = new DefaultHttpClient();

            try {
                //Define a postRequest request
                HttpGet getRequest = new HttpGet("http://uscdc01tljdv003.globalservices.bcdtravel.local:8080/DQTAPIProject-0.0.1-SNAPSHOT/InvApi/getInvoice/AABE10092019000000706188508291474T00000086051395444858770T1357001");
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
                    FileWriter writer = new FileWriter("getinvoice_qa" + " " + dateFormat.format(date) + ".txt", true);
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





