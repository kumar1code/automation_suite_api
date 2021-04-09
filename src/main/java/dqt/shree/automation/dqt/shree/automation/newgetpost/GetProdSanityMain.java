package dqt.shree.automation.dqt.shree.automation.newgetpost;

import java.util.logging.Logger;

public class GetProdSanityMain {

    static final Logger logger= Logger.getLogger(GetProdSanityMain.class.getName());


    public static void main(String[] args) {
        logger.info("GetPost PROD  Sanity");

        try {

            GetProdSanity.csvFile();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
