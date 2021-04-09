package dqt.shree.automation.dqt.shree.automation.getmethod;

import dqt.shree.automation.dqt.shree.automation.devautomation.devauto;

public class getmain {


    public static void main(String[] args) {
        System.out.println("get method rqst");
        try {
            getauto.sendGET();
            getauto.getinvoice();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}