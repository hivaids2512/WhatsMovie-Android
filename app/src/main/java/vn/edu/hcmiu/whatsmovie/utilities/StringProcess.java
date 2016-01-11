package vn.edu.hcmiu.whatsmovie.utilities;

import vn.edu.hcmiu.whatsmovie.configuration.Configuration;

/**
 * Created by quyquy on 1/11/2016.
 */
public class StringProcess {

    public static String processTitile(String title){
        if(title.length()> Configuration.TITLE_LENGTH){
            return title.substring(0, 9) + "...";
        }
        return title + "...";
    }
}
