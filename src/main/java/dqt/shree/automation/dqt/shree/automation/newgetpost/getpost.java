package dqt.shree.automation.dqt.shree.automation.newgetpost;

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
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.net.SocketTimeoutException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class getpost {

    static String responseBody, ukey;
    static int count, count1, count2;


    public static StringEntity[] getRequestArray(String file) {
        List<String> words = readArray(file);

        int size = words.size();
        StringEntity[] postRequests = new StringEntity[size];

        for (int i = 0; i < words.size(); i = i + 1) {
            try {
                StringEntity entity = new StringEntity(words.get(i));
                ukey = words.get(i);
                // System.out.println(ukey);
                //StringEntity entity1 = new StringEntity("{\"include\":[{\"dataid\": [\"ZT\"]}],\"exclude\": [],\"includeCriticalErrors\":\"0\",\"filter\": [{\"column\": \"errorDesc\",\"value\": \"RJ-Invalid Currency Code|Invoice_GEN\"}],\"order\": {\"column\": \"globalclientcd\",\"value\": \"asc\"},\"combined\": {\"column\": \"dataid\"}}");

                //postRequests[i] = entity;
                //System.out.println(postRequests[i]);

                //postRequests[1] = entity1;
            } catch (Exception ex) {
                String exc = ex.getMessage();
                System.out.println(exc);
            }

        }
        //ukey=words.get(i);

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

    public static void csvFile() throws Exception {

        StringEntity[] postRequests = getRequestArray("C:\\Uniquekey\\Dev\\uniquekey.txt");
        List<String> words = readArray("C:\\Uniquekey\\Dev\\uniquekey.txt");

        //System.out.println(postRequests[0]);
        try {

            DateFormat dateFormat0 = new SimpleDateFormat("dd-MM-yyyy hh-mm-ss");
            Date date0 = new Date();
            String dt0 = dateFormat0.format(date0);
            PrintWriter pw = new PrintWriter(new File("C:\\API_CSV\\GETPOST\\" + "getpostdev " + dt0 + ".csv"));
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
            sb.append("ErrorCount");
            sb.append(",");
            sb.append(" Time Issues");
            sb.append(",");
            sb.append(" Zero Data Issue");
            sb.append(",");
            sb.append(" Errorcount Mismatch");

            sb.append("\r\n");

            for (int i = 0; i < postRequests.length; i++) {

                sb.append("DEV");
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
                String ukey2 = words.get(i);

                String url1 = "http://uscdc01tljdv002.globalservices.bcdtravel.local:8080/DQTAPIProject-0.0.1-SNAPSHOT/InvApi/getInvoice/" + ukey2;

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
                    sb.append(ukey2);
                    sb.append(",");
                    sb.append(count);
                    sb.append(",");

                    if (elapsedTime6 > 3000) {
                        sb.append("Response Time Issue");
                    } else {
                        sb.append("N/A");
                    }
                    sb.append(",");

                    if (count == 0) {
                        sb.append("Zero data issue");
                    } else {
                        sb.append("N/A");
                    }
                    sb.append(",");
                    sb.append("N/A");
                } catch (
                        SocketTimeoutException ex) {
                    String exc = ex.getMessage();
                    System.out.println("Request Timeout in getinvoice API");
                    sb.append("Request Timeout");
                }

                sb.append("\r\n");

                sb.append("DEV");
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
                HttpPost postRequest7 = new HttpPost("http://uscdc01tljdv002.globalservices.bcdtravel.local:8080//DQTAPIProject-0.0.1-SNAPSHOT/InvApi/postTransSubmitCorrection");
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
                    sb.append(ukey2);
                    sb.append(",");
                    sb.append(count1);
                    sb.append(",");

                    if (elapsedTime7 > 180000) {
                        sb.append("Response Time Issue");
                    } else {
                        sb.append("N/A");
                    }
                    sb.append(",");

                    if (count1 == 0) {
                        sb.append("Zero data issue");
                    } else {
                        sb.append("N/A");
                    }
                    sb.append(",");
                    sb.append("N/A");

                } catch (SocketTimeoutException ex) {
                    String exc = ex.getMessage();
                    System.out.println("Request Timeout in posttranssubmitcorrection1 API");
                    sb.append("Request Timeout");
                    sb.append("\r\n");

                }

                sb.append("\r\n");


                sb.append("DEV");
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
                HttpPost postRequest8 = new HttpPost("http://uscdc01tljdv002.globalservices.bcdtravel.local:8080//DQTAPIProject-0.0.1-SNAPSHOT/InvApi/postTransSubmitCorrection");
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
                    sb.append(ukey2);
                    sb.append(",");
                    sb.append(count2);
                    sb.append(",");

                    if (elapsedTime8 > 15000) {
                        sb.append("Response Time Issue");
                    } else {
                        sb.append("N/A");
                    }
                    sb.append(",");

                    if (count2 == 0) {
                        sb.append("Zero data issue");
                    } else {
                        sb.append("N/A");
                    }
                    sb.append(",");


                    if ((count != count1) || (count1 != count2) || (count != count2)) {
                        System.out.println("there is a mismatch in errorcount. Please check");
                        sb.append("Mismatch");
                    } else {
                        sb.append("No Mismatch");
                    }

                    sb.append("\r\n");
                    sb.append("\r\n");

                } catch (SocketTimeoutException ex) {
                    String exc = ex.getMessage();
                    System.out.println("Request Timeout in posttranssubmitcorrection2 API");
                    sb.append("Request Timeout");
                    sb.append("\r\n");

                }

            }
            pw.write(sb.toString());
            pw.close();

        } catch (Exception e) {
            e.printStackTrace();

        }


    }
}

