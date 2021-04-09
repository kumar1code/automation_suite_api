package dqt.shree.automation.dqt.shree.automation.newgetpost;

import com.github.wnameless.json.flattener.JsonFlattener;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.SocketTimeoutException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GetProdSanity {

    private static int count;
    private static int count1;
    private static int count5=0;
    private static int count6=0;
    private static int count7=0;
    private static int count8=0;
    private static int count9=0;
    private static int count10=0;
    private static int count11=0;
    private static String ukey2;
    private static String responseBody;
    private static final String URL="http://dataselfservice.bcdtravel.com:8080//DQTAPIProject-0.0.1-SNAPSHOT/InvApi/postTransSubmitCorrection";
    private static final String G="yyyy-dd-MM";
    private static final String H ="hh.mm aa";
    private static final String J="content-type";
    private static final String K="application/json";
    private static final String B="Daily Automation Suite";
    private static final String C="Response Time Issue";
    private static final String D="Zero data issue";
    private static final String I ="Request Timeout";
    private static final String ERRORTYPE="errortype";



    static final Logger logger= Logger.getLogger(GetProdSanity.class.getName());

    GetProdSanity()
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

    public static void csvFile()  {

        int countsum;
        int count12;

        StringEntity[] postRequests = getRequestArray("C:\\Uniquekey\\Prod\\uniquekeyprod.txt");
        List<String> words = readArray("C:\\Uniquekey\\Prod\\uniquekeyprod.txt");


        DateFormat dateFormat0 = new SimpleDateFormat("dd-MM-yyyy hh-mm-ss");
        Date date0 = new Date();
        String dt0 = dateFormat0.format(date0);
        try(PrintWriter pw = new PrintWriter(new File("C:\\API_CSV\\GETPOST\\" + "getpostprod " + dt0 + ".csv"))) {

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

            for (int i = 0; i < 5; i++) {

                sb.append("QA");
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


                getInvc(words, sb, i);


                sb.append("\r\n");

                sb.append("QA");
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


                postTrns1(sb);

                sb.append("\r\n");

                sb.append("QA");
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


                postTrns2(sb);

            }
            logger.log(Level.INFO, "total number of mismatch is :{0} ", count5);

            countsum=count6+count7+count8;
            count12=count9+count10+count11;

            logger.log(Level.INFO, "total number of response time issue is :{0} ", countsum);
            logger.log(Level.INFO, "total number of zero data records is :{0} ", count12);


            logger.log(Level.INFO, "total number of zero data records(getinvoice) is :{0} ", count9);

            logger.log(Level.INFO, "total number of zero data records(posttranssumbitcorrection) is :{0} ", count10);


            pw.write(sb.toString());

        } catch (Exception e) {
            e.printStackTrace();

        }


    }

    private static void postTrns2(StringBuilder sb) throws IOException, JSONException {
        try {
            int count2;
            DefaultHttpClient httpClient8 = new DefaultHttpClient();
            HttpPost postRequest8 = new HttpPost(URL);
            postRequest8.addHeader(J, K);

            StringEntity[] postRequests8 = new StringEntity[1];
            StringEntity entity8 = new StringEntity(responseBody);
            postRequests8[0] = entity8;

            int timeout8 = 120;
            HttpParams httpParams8 = httpClient8.getParams();
            httpParams8.setParameter(CoreConnectionPNames.SO_TIMEOUT, timeout8 * 1000);

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
                if (key2.contains(ERRORTYPE) && (!myResponse8.getString(key2).equals("INFORMATION"))) {
                    count2++;
                }
            }

            logger.log(Level.INFO, "total error count in posttranssubmitcorrection2 is {0} ", count2);


            sb.append(elapsedTime8);
            sb.append(",");
            sb.append(B);
            sb.append(",");
            sb.append(ukey2);
            sb.append(",");
            sb.append(count2);
            sb.append(",");

            if (elapsedTime8 > 15000) {
                sb.append(C);
                count8++;
            } else {
                sb.append("N/A");
            }
            sb.append(",");

            if (count2 == 0) {
                count11++;
                sb.append(D);
            } else {
                sb.append("N/A");
            }
            sb.append(",");


            if ((count != count1) || (count1 != count2) ) {
                logger.info("there is a mismatch in errorcount. Please check");
                sb.append("Mismatch");
                count5++;
            } else {
                sb.append("No Mismatch");
            }

            sb.append("\r\n");
            sb.append("\r\n");

        } catch (SocketTimeoutException ex) {
            logger.info("Request Timeout in posttranssubmitcorrection2 API");
            sb.append(I);
            sb.append("\r\n");

        }
    }

    private static void postTrns1(StringBuilder sb) throws IOException, JSONException {
        try {
            DefaultHttpClient httpClient7 = new DefaultHttpClient();
            HttpPost postRequest7 = new HttpPost(URL);
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

            logger.info( responseBody3);

            String flattenJSONString2 = JsonFlattener.flatten(responseBody3);
            JSONObject myResponse6 = new JSONObject(flattenJSONString2.trim());
            Iterator<String> keys1 = myResponse6.keys();

            count1 = 0;

            while (keys1.hasNext()) {
                String key1 = keys1.next();
                if (key1.contains(ERRORTYPE) && (!myResponse6.getString(key1).equals("INFORMATION"))) {
                    count1++;
                }
            }

            logger.log(Level.INFO, "total error count in posttranssubmitcorrection1 is: {0} ", count1);

            sb.append(elapsedTime7);
            sb.append(",");
            sb.append(B);
            sb.append(",");
            sb.append(ukey2);
            sb.append(",");
            sb.append(count1);
            sb.append(",");

            if (elapsedTime7 > 180000) {
                sb.append(C);
                count7++;
            } else {
                sb.append("N/A");
            }
            sb.append(",");

            if (count1 == 0) {
                count10++;
                sb.append(D);
            } else {
                sb.append("N/A");
            }
            sb.append(",");
            sb.append("N/A");

        } catch (SocketTimeoutException ex) {
            logger.info("Request Timeout in posttranssubmitcorrection1 API");
            sb.append(I);
            sb.append("\r\n");

        }
    }

    private static void getInvc(List<String> words, StringBuilder sb, int i) throws IOException, JSONException {
        try {

            DefaultHttpClient httpClient6 = new DefaultHttpClient();
            ukey2 = words.get(i);

            String url1 = "http://dataselfservice.bcdtravel.com:8080/DQTAPIProject-0.0.1-SNAPSHOT/InvApi/getInvoice/" + ukey2;

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


            gtInvcSave(words, i, elapsedTime6);

            String flattenJSONString = JsonFlattener.flatten(responseBody);
            JSONObject myResponse4 = new JSONObject(flattenJSONString.trim());
            Iterator<String> keys = myResponse4.keys();

            count = 0;

            while (keys.hasNext()) {
                String key = keys.next();
                if (key.contains(ERRORTYPE)) {
                    count++;
                }
            }
            logger.log(Level.INFO, "total error count in getinvoice is: {0} ", count);

            sb.append(elapsedTime6);
            sb.append(",");
            sb.append(B);
            sb.append(",");
            sb.append(ukey2);
            sb.append(",");
            sb.append(count);
            sb.append(",");

            if (elapsedTime6 > 3000) {
                sb.append(C);
                count6++;
            } else {
                sb.append("N/A");
            }
            sb.append(",");

            if (count == 0) {
                count9++;
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

    private static void gtInvcSave(List<String> words, int i, long elapsedTime6) {
        DateFormat dateFormat = new SimpleDateFormat("(yyyy-MM-dd HH-mm-ss)");
        Date date = new Date();
        String x = "C:\\API_CSV\\GETPOST\\Response\\Prod\\" + words.get(i);
        try(FileWriter writer = new FileWriter(x + " " + dateFormat.format(date) + ".txt", true)){
            writer.write("Response:" + " " + responseBody + "\nTime:" + elapsedTime6 + " ms" + System.lineSeparator());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
