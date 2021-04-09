package dqt.shree.automation.dqt.shree.automation.prodapi;




public class prodmain {

    public static void main( String[] args )
    {
        System.out.println( "Prod Sanity" );
        try {

            prodauto.pagination();
            prodauto.getAllErrorFieldsForGrp();
            prodauto.getTransErrors();
            prodauto.getTransErrorsCount();
            prodauto.getFilteredTransErrors();
            //prodauto.updateBulkCorrectWithFilterGrp();
            //prodauto.updateBulkIgnoreWithFilterGrp();
            prodauto.getInvoice();
            prodauto.postTransSubmitCorrection();
            prodauto.csvFile();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


}
