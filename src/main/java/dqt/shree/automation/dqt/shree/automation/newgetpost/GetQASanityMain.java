package dqt.shree.automation.dqt.shree.automation.newgetpost;

import java.util.logging.Logger;

public class GetQASanityMain {

    static final Logger logger= Logger.getLogger(GetQASanityMain.class.getName());


    public static void main(String[] args) {
        logger.info("GetPost QA  Sanity");

        try {

            GetQASanity.csvFile();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
