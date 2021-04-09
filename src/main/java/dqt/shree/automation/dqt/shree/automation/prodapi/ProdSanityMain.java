package dqt.shree.automation.dqt.shree.automation.prodapi;

import java.util.logging.Logger;

public class ProdSanityMain {

    static final Logger logger= Logger.getLogger(ProdSanityMain.class.getName());

    public static void main(String[] args) {
        logger.info("Prod Sanity");

        try {
            ProdSanity.yamlFileToMap();
            ProdSanity.pagination();
            ProdSanity.getAllErrorFieldsForGrp();
            ProdSanity.getTransErrors();
            ProdSanity.getTransErrorsCount();
            ProdSanity.getFilteredTransErrors();
            //ProdSanity.updateBulkCorrectWithFilterGrp();
            //ProdSanity.updateBulkIgnoreWithFilterGrp();
            ProdSanity.getInvoice();
            ProdSanity.postTransSubmitCorrection();
            ProdSanity.csvFile();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
