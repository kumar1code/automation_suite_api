package dqt.shree.automation.dqt.shree.automation.qaapi;

public class QA_yml_main {

    public static void main( String[] args)
    {
    System.out.println( "QA Sanity" );
    try
     {
        QA_yml_auto.yamlFileToMap();
        QA_yml_auto.pagination();
        QA_yml_auto.getAllErrorFieldsForGrp();
        QA_yml_auto.getTransErrors();
        QA_yml_auto.getTransErrorsCount();
        QA_yml_auto.getFilteredTransErrors();
        //QA_yml_auto.updateBulkCorrectWithFilterGrp();
        //QA_yml_auto.updateBulkIgnoreWithFilterGrp();
        QA_yml_auto.getInvoice();
        QA_yml_auto.postTransSubmitCorrection();
        QA_yml_auto.csvFile();
    }
    catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }

}

}
