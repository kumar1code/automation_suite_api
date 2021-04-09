package dqt.shree.automation.dqt.shree.automation.qaapi;


import java.util.logging.Logger;

public class QaSanityMain {

    static final Logger logger= Logger.getLogger(QaSanityMain.class.getName());


    public static void main(String[] args) {
        logger.info("QA Sanity");


        try {
            QaSanity.yamlFileToMap();
            QaSanity.pagination();
            QaSanity.getAllErrorFieldsForGrp();
            QaSanity.getTransErrors();
            QaSanity.getTransErrorsCount();
            QaSanity.getFilteredTransErrors();
            //QaSanity.updateBulkCorrectWithFilterGrp();
            //QaSanity.updateBulkIgnoreWithFilterGrp();
            QaSanity.getInvoice();
            QaSanity.postTransSubmitCorrection();
            QaSanity.csvFile();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
