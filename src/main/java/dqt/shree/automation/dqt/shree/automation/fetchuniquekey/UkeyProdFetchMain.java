package dqt.shree.automation.dqt.shree.automation.fetchuniquekey;

import java.util.logging.Logger;

public class UkeyProdFetchMain {

    static final Logger logger= Logger.getLogger(UkeyProdFetchMain.class.getName());


    public static void main(String[] args) {
        logger.info("Uniquekey Fetching in PROD Environment");

        try {

            UkeyProdFetch.uniquekey();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
