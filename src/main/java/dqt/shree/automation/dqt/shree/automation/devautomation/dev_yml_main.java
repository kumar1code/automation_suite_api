package dqt.shree.automation.dqt.shree.automation.devautomation;

import java.util.logging.Logger;

public class dev_yml_main {

    static final Logger logger= Logger.getLogger(dev_yml_main.class.getName());


    public static void main(String[] args) {
        logger.info("Dev Sanity");


        try {
            dev_yml_auto.yamlFileToMap();
            dev_yml_auto.pagination();
            dev_yml_auto.getAllErrorFieldsForGrp();
            dev_yml_auto.getTransErrors();
            dev_yml_auto.getTransErrorsCount();
            dev_yml_auto.getFilteredTransErrors();
            dev_yml_auto.updateBulkCorrectWithFilterGrp();
            dev_yml_auto.updateBulkIgnoreWithFilterGrp();
            dev_yml_auto.getInvoice();
            dev_yml_auto.postTransSubmitCorrection();
            dev_yml_auto.csvFile();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
