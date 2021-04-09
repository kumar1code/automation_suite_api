package dqt.shree.automation.dqt.shree.automation.getmethod;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class getauto {

    public static void sendGET() throws IOException {

        String GET_URL = "http://dataselfservice.bcdtravel.com:8080/DQTAPIProject-0.0.1-SNAPSHOT/InvApi/getInvoice/AABE10092019000000706188508291474T00000086051395444858770T1357001";
        long startTime = System.currentTimeMillis();
        URL obj = new URL(GET_URL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");

        //con.setRequestProperty("User-Agent", USER_AGENT);
        int responseCode = con.getResponseCode();
        long elapsedTime = System.currentTimeMillis() - startTime;

        //System.out.println("GET Response Code :: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            System.out.println(response.toString());
            try {
                DateFormat dateFormat = new SimpleDateFormat("(yyyy-MM-dd HH-mm-ss)");
                Date date= new Date();
                FileWriter writer = new FileWriter("getinvoice_prod" +" "+ dateFormat.format(date) + ".txt",true );
                writer.write(response + "\nTime:" + elapsedTime + " ms" + System.lineSeparator());
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else {
            System.out.println("GET request not worked");
        }

    }


    public static void getinvoice() throws Exception {


        DefaultHttpClient httpClient = new DefaultHttpClient();

        try {
            //Define a postRequest request
            HttpGet getRequest = new HttpGet("http://dataselfservice.bcdtravel.com:8080/DQTAPIProject-0.0.1-SNAPSHOT/InvApi/getInvoice/AABE10092019000000706188508291474T00000086051395444858770T1357001");
            //Set the API media type in http content-type header
            getRequest.addHeader("content-type", "application/json");

            //Set the request post body

//            StringEntity[] postRequests = new StringEntity[2];
//
//            StringEntity entity0 = new StringEntity("{\"include\":[ {\"globalclientcd\":[\"4420\",\"4417\"],\"dataid\": [\"IV\"] }],\"includeCriticalErrors\": \"0\"}");
//            StringEntity entity1 = new StringEntity("{\"groupId\":\" eX_dataid_In_rest_0\",\"include\":[{\"globalclientcd\":[\"4420\",\"3190\"]}],\"includeCriticalErrors\":\"0\"}");
//
//            postRequests[0] = entity0;
//            postRequests[1] = entity1;
//
//            for (int i = 0; i < postRequests.length; i++) {
//
//                postRequest.setEntity(postRequests[i]);

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
                    Date date= new Date();
                    FileWriter writer = new FileWriter("getinvoice2" +" "+ dateFormat.format(date) + ".txt",true );
                    writer.write(responseBody + "\nTime:" + elapsedTime + " ms" + System.lineSeparator());
                    writer.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
         catch (Exception ex) {
            String exc = ex.getMessage();
            System.out.println(exc);
        } finally {
            //Important: Close the connect
            httpClient.getConnectionManager().shutdown();
        }

    }
}
