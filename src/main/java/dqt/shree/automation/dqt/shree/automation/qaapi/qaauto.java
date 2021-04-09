package dqt.shree.automation.dqt.shree.automation.qaapi;

import org.apache.http.entity.StringEntity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;


public class qaauto {

     String request;
     String url;


    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

     public String toString()
     {
       return "qaauto[request="+request+ " ,url= "+ url+"]";
     }



}

