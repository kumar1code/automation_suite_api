package dqt.shree.automation.dqt.shree.automation.prodapi;

public class prod_yaml_main {

    public static void main( String[] args )
    {
        System.out.println( "Prod Sanity" );
        try {
            prod_yaml_auto.yamlFileToMap();
            prod_yaml_auto.pagination();
            prod_yaml_auto.getAllErrorFieldsForGrp();
            prod_yaml_auto.getTransErrors();
            prod_yaml_auto.getTransErrorsCount();
            prod_yaml_auto.getFilteredTransErrors();
            //prodauto.updateBulkCorrectWithFilterGrp();
            //prodauto.updateBulkIgnoreWithFilterGrp();
            prod_yaml_auto.getInvoice();
            prod_yaml_auto.postTransSubmitCorrection();
            prod_yaml_auto.csvFile();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
