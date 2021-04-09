package dqt.shree.automation.dqt.shree.automation.fetchuniquekey;


import java.io.*;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.net.SocketTimeoutException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;


public class UkeyDevFetch {


    static final Logger logger= Logger.getLogger(UkeyDevFetch.class.getName());

    UkeyDevFetch()
    {

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


    public static void uniquekey() throws JSONException,IOException {

        String s1;
        String s2;
        String s3;
        String d1;
        String s5;

        StringEntity[] postRequests = getRequestArray("C:\\Dataid\\dataidDEV.txt");
        List<String> words = readArray("C:\\Dataid\\dataidDEV.txt");

        HashSet<String> keystore = new HashSet<>();

        for (int i = 0; i < postRequests.length; i++) {

            d1 = words.get(i);

            s1="  {\"include\":[{\"dataid\":[\"";
            s3= d1;
            s2="\"]}],\"exclude\": [],\"includeCriticalErrors\": \"1\",\"filter\": [],\"order\": {\"column\": \"globalclientcd\",\"value\": \"asc\"},\"combined\": {\"column\": \"dataid\"}}   ";

            s5= s1+s3+s2;

            logger.info(s5);

            DefaultHttpClient httpClient5 = new DefaultHttpClient();
            HttpPost postRequest = new HttpPost("http://uscdc01tljdv002.globalservices.bcdtravel.local:8080//DQTAPIProject-0.0.1-SNAPSHOT/InvApi/getFilteredTransErrors/1/500");
            postRequest.addHeader("content-type", "application/json");

            StringEntity[] postRequests1 = new StringEntity[1];
            StringEntity entity4 = new StringEntity(s5);
            postRequests1[0] = entity4;

            int timeout5 = 60;
            HttpParams httpParams5 = httpClient5.getParams();
            httpParams5.setParameter(CoreConnectionPNames.SO_TIMEOUT, timeout5 * 1000);

            try {
                postRequest.setEntity(postRequests1[0]);
                //Send the request; It will immediately return the response in HttpResponse object if any
                HttpResponse response6 = httpClient5.execute(postRequest);
                String responseBody5 = EntityUtils.toString(response6.getEntity());
                logger.info(responseBody5);


                JSONObject myResponse = new JSONObject(responseBody5);
                JSONArray objJSONArray = myResponse.getJSONArray("transErrors");

                JSONObject myResponse1 = new JSONObject(responseBody5);
                long x = myResponse1.getInt("totalCount");
                if(x==0)
                    continue;

                devuky(keystore, objJSONArray);

                String f=keystore.toString();
                logger.info(f);

            } catch (SocketTimeoutException ex) {
                logger.info("Request Timeout in FilteredTransErrors API");
            }
        }

        List<String> list = new ArrayList<>(keystore);
        Collections.sort(list);
        String uky=list.toString();

        String[] uky2=uky.split(",");

        DateFormat dateFormat = new SimpleDateFormat("(yyyy-MM-dd HH-mm-ss)");
        Date date = new Date();

        try(FileWriter writer = new FileWriter("C:\\Uniquekey\\Dev\\uniquekeydev" + " " + dateFormat.format(date) + ".txt", true)) {

            for (String s : uky2) {
                logger.info(s);

                writer.write(s + System.lineSeparator());
                writer.flush();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void devuky(HashSet<String> keystore, JSONArray objJSONArray) {
        String v0;
        try {
            for (int l = 0; l <= 5; l++) {

                JSONObject myResponse0 = objJSONArray.getJSONObject(l);

                v0 = myResponse0.getString("uniquekey");

                keystore.add(v0);

                logger.info(v0);
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
