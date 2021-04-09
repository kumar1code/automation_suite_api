package dqt.shree.automation.dqt.shree.automation.qaapi;

import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.InputStream;

public class qamain {

      public static qaauto craeteqaauto()
  {
      qaauto autom= new qaauto();
      autom.setRequest("{\"include\":[{\"dataid\":[\"IV\",\"BE\"]}],\"includeCriticalErrors\":\"1\"}");
      autom.setUrl("http://uscdc01tljdv003.globalservices.bcdtravel.local:8080//DQTAPIProject-0.0.1-SNAPSHOT/InvApi/getTransErrors/1/2");
      return autom;

  }
//    public void yamlFileToMap() throws FileNotFoundException {
//        Yaml yaml = new Yaml();
//        String path = "D:\\workspace_deployment\\automation\\dqt.shree.automation\\src\\main\\java\\dqt\\shree\\automation\\dqt\\shree\\automation\\qaapi\\qaapi.yaml";
//        Map<String, String> map = yaml.load(new FileInputStream(new File(path)));
//        System.out.println(map.get("transerror_url"));
//
//    }


    public static void main(String[] args) {
//        try {
//            qamain obj = new qamain();
//            obj.yamlFileToMap();
//        }
//        catch (IOException e)
//        {
//            e.printStackTrace();
//        }
//    }
//}

        qaauto autom = craeteqaauto();

        Yaml yaml = new Yaml();
        try {
            String datafile = "D:\\workspace_deployment\\automation\\dqt.shree.automation\\src\\main\\properties\\dev.yaml";
            yaml.dump(autom, new FileWriter(new File(datafile)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Done");

    }
}

