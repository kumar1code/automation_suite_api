package dqt.shree.automation.dqt.shree.automation.fetchuniquekey;

import java.util.logging.Logger;

public class UkeyDevFetchMain {

    static final Logger logger= Logger.getLogger(UkeyDevFetchMain.class.getName());


    public static void main(String[] args) {
        logger.info("Uniquekey Fetching in DEV Environment");

        try {

            UkeyDevFetch.uniquekey();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
