package dqt.shree.automation.dqt.shree.automation.fetchuniquekey;

import java.io.*;
//import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.github.wnameless.json.flattener.JsonFlattener;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.yaml.snakeyaml.Yaml;
import java.lang.*;


import java.io.FileNotFoundException;
import java.net.SocketTimeoutException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.io.PrintWriter;
import java.io.File;


public class ukeyqa {

    static String did,d1,s1,s2,s3,s5,v0,v1,v2,v3,v4,v5,v6,v7,v8,v9;

    public static StringEntity[] getRequestArray(String file) {
        List<String> words = readArray(file);

        int size = words.size();
        StringEntity[] postRequests = new StringEntity[size];

        for (int i = 0; i < words.size(); i = i + 1) {
            try {
                StringEntity entity = new StringEntity(words.get(i));
                did = words.get(i);
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

        public static void uniquekey() throws Exception {

            StringEntity[] postRequests = getRequestArray("C:\\Dataid\\dataidQA.txt");
            List<String> words = readArray("C:\\Dataid\\dataidQA.txt");

            //List<String> keystore= new ArrayList<String>();
            HashSet<String> keystore = new HashSet<String>();

            for (int i = 0; i <=40; i++) {

                d1 = words.get(i);
                //System.out.println(d1);

                s1="  {\"include\":[{\"dataid\":[\"";
                s3= d1;
                s2="\"]}],\"exclude\": [],\"includeCriticalErrors\": \"1\",\"filter\": [],\"order\": {\"column\": \"globalclientcd\",\"value\": \"asc\"},\"combined\": {\"column\": \"dataid\"}}   ";

                s5= s1+s3+s2;

                System.out.println(s5);


                DefaultHttpClient httpClient5 = new DefaultHttpClient();
                HttpPost postRequest = new HttpPost("http://uscdc01tljdv003.globalservices.bcdtravel.local:8080///DQTAPIProject-0.0.1-SNAPSHOT/InvApi/getFilteredTransErrors/1/500");
                postRequest.addHeader("content-type", "application/json");

                StringEntity[] postRequests1 = new StringEntity[1];
                StringEntity entity4 = new StringEntity(s5);
                postRequests1[0] = entity4;

                int timeout5 = 60;
                HttpParams httpParams5 = httpClient5.getParams();
                //httpParams.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, timeout * 1000);
                httpParams5.setParameter(CoreConnectionPNames.SO_TIMEOUT, timeout5 * 1000);

                try {
                    postRequest.setEntity(postRequests1[0]);
                    long startTime5 = System.currentTimeMillis();
                    //Send the request; It will immediately return the response in HttpResponse object if any
                    HttpResponse response6 = httpClient5.execute(postRequest);
                    long elapsedTime5 = System.currentTimeMillis() - startTime5;
                    String responseBody5 = EntityUtils.toString(response6.getEntity());
                    System.out.println(responseBody5);

                    JSONObject myResponse = new JSONObject(responseBody5);
                    JSONArray obj_JSONArray = myResponse.getJSONArray("transErrors");

                    JSONObject myResponse1 = new JSONObject(responseBody5);

                    long x = myResponse1.getInt("totalCount");
                    //System.out.println("totalcount is " + x);
                    if(x==0)
                        continue;


                    try {
                        for (int l = 0; l <= 4; l++) {

                            JSONObject myResponse0 = obj_JSONArray.getJSONObject(l);

                            v0 = myResponse0.getString("uniquekey");

                            keystore.add(v0);

                            System.out.println(v0);

                        }
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }

//                    JSONObject myResponse0 = obj_JSONArray.getJSONObject(0);
//                    v0 = myResponse0.getString("uniquekey");
//
//                    keystore.add(v0);
//
//                    JSONObject myResponse1 = obj_JSONArray.getJSONObject(1);
//                    v1 = myResponse1.getString("uniquekey");
//
//                    keystore.add(v1);


                    System.out.println(keystore.toString());


                } catch (SocketTimeoutException ex) {
                    String exc = ex.getMessage();
                    System.out.println("Request Timeout in FilteredTransErrors API");

                }
            }

            List<String> list = new ArrayList<String>(keystore);
            Collections.sort(list);

            //String uky=keystore.toString();
            String uky=list.toString();

            String[] uky2=uky.split(",");
            //System.out.println(uky2.length);

//      for(String val:uky2)
//      System.out.println(val);

            DateFormat dateFormat = new SimpleDateFormat("(yyyy-MM-dd HH-mm-ss)");
            Date date = new Date();
            FileWriter writer = new FileWriter("C:\\Uniquekey\\QA\\uniquekeyqa" + " " + dateFormat.format(date) + ".txt", true);

            for (int i=0; i < uky2.length; i++)
            {
                System.out.println(uky2[i]);
                String temp=uky2[i];
                try {

                    writer.write( temp + System.lineSeparator()  );
                    writer.flush();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }
}
