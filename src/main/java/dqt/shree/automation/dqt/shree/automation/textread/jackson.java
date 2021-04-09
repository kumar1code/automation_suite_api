package dqt.shree.automation.dqt.shree.automation.textread;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

public class jackson {


    public static void main(String args[])
    {
        jackson.jackson();
    }
    public static void jackson()
    {
        String query_url = "http://uscdc01tljdv002.globalservices.bcdtravel.local:8080//DQTAPIProject-0.0.1-SNAPSHOT/InvApi/getFilteredTransErrors/1/5";
        //String json = "{\"include\":[{\"dataid\":[\"IV\",\"BE\"]}],\"includeCriticalErrors\":1}";
        //String json = "{ \"method\" : \"guru.test\", \"params\" : [ \"jinu awad\" ], \"id\" : 123 }";
        String json = "{\"include\":[{\"dataid\": [\"LU\"]}],\"exclude\": [],\"includeCriticalErrors\": \"1\",\"filter\": [],\"order\": {\"column\": \"globalclientcd\",\"value\": \"asc\"},\"combined\": {\"column\": \"dataid\"}}";

        try {
            URL url = new URL(query_url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            OutputStream os = conn.getOutputStream();
            os.write(json.getBytes("UTF-8"));
            os.close();
            // read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());
            String result = IOUtils.toString(in, "UTF-8");
            System.out.println(result);
            System.out.println("result after Reading JSON Response");
            JSONObject myResponse = new JSONObject(result);
            long v= myResponse.getInt("totalCount");
            //System.out.println("jsonrpc- "+myResponse.getString("jsonrpc"));
           // System.out.println("totalCount- "+myResponse.getInt("totalCount"));
            System.out.println(v);
            if(v<=0)
            {
             System.out.println("count is 0");
            }
            in.close();
            conn.disconnect();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}






