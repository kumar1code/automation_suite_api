package dqt.shree.automation.dqt.shree.automation.fetchuniquekey;

import java.util.logging.Logger;

public class UkeyFetchQAMain {

    static final Logger logger= Logger.getLogger(UkeyFetchQAMain.class.getName());


    public static void main(String[] args) {
        logger.info("Uniquekey Fetching in QA Environment");

        try {

            UkeyQAFetch.uniquekey();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
