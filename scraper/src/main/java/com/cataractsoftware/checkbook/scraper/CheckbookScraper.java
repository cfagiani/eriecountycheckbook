package com.cataractsoftware.checkbook.scraper;

import org.cataractsoftware.datasponge.DataSponge;

/**
 * @author Christopher Fagiani
 */
public class CheckbookScraper {
    public static void main(String[] args) {
        checkArgs(args);
        try {
            DataSponge.main(new String[]{"--job",args[0],"--server", "--singleJob"});
        } catch (Exception e) {
            System.err.println("Could not run sponge: " + e.getMessage());
            e.printStackTrace(System.err);
        }


    }

    private static void checkArgs(String[] args) {
        if (args == null || args.length != 1) {
            System.out.println("You must specify the path to the config file on the command line");
            System.exit(1);
        }

    }
}
