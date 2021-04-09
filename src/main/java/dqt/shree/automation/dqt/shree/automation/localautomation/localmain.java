package dqt.shree.automation.dqt.shree.automation.localautomation;

public class localmain {

    public static void main( String[] args )
    {
        System.out.println( "Local Sanity" );
        try {
            localauto.pagination();
            localauto.getAllErrorFieldsForGrp();
            localauto.getTransErrors();
            localauto.getTransErrorsCount();
            localauto.getFilteredTransErrors();
            localauto.updateBulkCorrectWithFilterGrp();
            localauto.updateBulkIgnoreWithFilterGrp();
            localauto.getInvoice();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}



