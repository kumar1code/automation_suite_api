package dqt.shree.automation.dqt.shree.automation.devautomation;

import java.util.logging.Logger;

public class DevSanityMain {

    static final Logger logger= Logger.getLogger(DevSanityMain.class.getName());

    public static void main(String[] args) {
        logger.info("Dev Sanity");


        try {
            DevSanity.yamlFileToMap();
            DevSanity.pagination();
            DevSanity.getAllErrorFieldsForGrp();
            DevSanity.getTransErrors();
            DevSanity.getTransErrorsCount();
            DevSanity.getFilteredTransErrors();
            DevSanity.updateBulkCorrectWithFilterGrp();
            DevSanity.updateBulkIgnoreWithFilterGrp();
            DevSanity.getInvoice();
            DevSanity.postTransSubmitCorrection();
            DevSanity.csvFile();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
