package dqt.shree.automation.dqt.shree.automation.textread;

import dqt.shree.automation.dqt.shree.automation.devautomation.devauto;

public class mainclass {

    public static void main( String[] args )
    {
        System.out.println( "QA Sanity" );
        try {
            ReadingFile.getTransErrorsCount();
//          ReadingFile.getFilteredTransErrors();
            ReadingFile.pagination();
//          ReadingFile.getAllErrorFieldsForGrp();
//          ReadingFile.getTransErrors();

            //ReadingFile.updateBulkCorrectWithFilterGrp();
            //ReadingFile.updateBulkIgnoreWithFilterGrp();
            ReadingFile.getInvoice();


        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
