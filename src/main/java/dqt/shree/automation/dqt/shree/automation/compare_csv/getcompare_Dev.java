package dqt.shree.automation.dqt.shree.automation.compare_csv;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.relevantcodes.extentreports.NetworkMode;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Logger;


public class getcompare_Dev {

    public static void getcompare() throws Exception {


        ExtentReports extent = new ExtentReports("Report/report.html", true, NetworkMode.OFFLINE);
        ExtentTest test = extent.startTest("Difference Report", "Below is the log of differnce found ");

        FileInputStream fileInputStream1 = new FileInputStream("C:\\Uniquekey\\getpostqa 29-07-2020 05-04-04.xls");

        HSSFWorkbook workbook1 = new HSSFWorkbook(fileInputStream1);

        HSSFSheet worksheet1 = workbook1.getSheet("1");

        int rowCount1 = worksheet1.getPhysicalNumberOfRows();

        FileInputStream fileInputStream2 = new FileInputStream("C:\\Uniquekey\\getpostqa 29-07-2020 09-25-06.xls");

        HSSFWorkbook workbook2 = new HSSFWorkbook(fileInputStream2);

        HSSFSheet worksheet2 = workbook2.getSheet("1");

        int rowCount2 = worksheet2.getPhysicalNumberOfRows();


        if (rowCount1 == rowCount2) {
            for (int i = 1; i < rowCount1; i++) {
                HSSFRow row1 = worksheet1.getRow(i);
                HSSFRow row2 = worksheet2.getRow(i);


                String Uniquekeystr1 = "";
                HSSFCell Uniquekey1 = row1.getCell(6);
                if (Uniquekey1 != null) {
                    Uniquekey1.setCellType(CellType.STRING);
                    Uniquekeystr1 = Uniquekey1.getStringCellValue();
                }


                String Uniquekeystr2 = "";
                HSSFCell Uniquekey2 = row2.getCell(6);
                if (Uniquekey2 != null) {
                    Uniquekey2.setCellType(CellType.STRING);
                    Uniquekeystr2 = Uniquekey2.getStringCellValue();
                }

                if (!Uniquekeystr1.equals(Uniquekeystr2)) {
                    System.out.println("[ERROR] :" + "Diference for uniquekey (sheet1) " + Uniquekeystr1 + "| sheet 1 id = " + Uniquekeystr1 + " | sheet 2 id = " + Uniquekeystr2);
                    test.log(LogStatus.ERROR, "Diference for id (book1) " + Uniquekeystr1 + "| Book 1 id = " + Uniquekeystr1 + "| Book 2 id = " + Uniquekeystr2);
                }


                String ErrorCountstr1 = "";
                HSSFCell ErrorCount1 = row1.getCell(7);
                if (ErrorCount1 != null) {
                    ErrorCount1.setCellType(CellType.STRING);
                    ErrorCountstr1 = ErrorCount1.getStringCellValue();
                }


                String ErrorCountstr2 = "";
                HSSFCell ErrorCount2 = row2.getCell(7);
                if (ErrorCount2 != null) {
                    ErrorCount2.setCellType(CellType.STRING);
                    ErrorCountstr2 = ErrorCount2.getStringCellValue();
                }

                if (!ErrorCountstr1.equals(ErrorCountstr2)) {
                    System.out.println("[ERROR] :" + "Diference for errorcount (sheet1) " + ErrorCountstr1 + "| sheet 1 id = " + ErrorCountstr1 + " | sheet 2 id = " + ErrorCountstr2);
                    test.log(LogStatus.ERROR, "Diference for id (book1) " + ErrorCountstr1 + "| Book 1 id = " + ErrorCountstr1 + "| Book 2 id = " + ErrorCountstr2);
                }


                System.out.println("[Processing] :" +  "=> Sheet 1 uniquekey = " + Uniquekeystr1 + " Sheet 2 uniquekey = " + Uniquekeystr2);
                System.out.println("[Processing] :" +  "=> Sheet 1 errorcount = " + ErrorCountstr1 + " Sheet 2 errorcount = " + ErrorCountstr2);

            }
            test.log(LogStatus.INFO, "Completed Successfully");

        } else {
            test.log(LogStatus.ERROR, "Row count 1=" + rowCount1 + "  Rocunt 2 = " + rowCount2);

            System.out.println("Row count 1=" + rowCount1 + "  Rocunt 2 = " + rowCount2);
        }


        extent.endTest(test);
        extent.flush();
    }

}




