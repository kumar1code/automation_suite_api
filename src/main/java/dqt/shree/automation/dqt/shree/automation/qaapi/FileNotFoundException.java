package dqt.shree.automation.dqt.shree.automation.qaapi;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class FileNotFoundException {

    public static void main( String[] args ){
        DateFormat dateFormat = new SimpleDateFormat("(yyyy)");
        Date date = new Date();
        String path="C:\\API\\QA\\"+dateFormat.format(date);
        File Dir=new File(path);
        if (!Dir.exists()) {
            if (Dir.mkdir()) {
                System.out.println("file created successfully");
            } else {
                System.out.println("file failed to be created ");

            }
        }
        else
        {
            System.out.println("file already exists");

        }
    }

}
