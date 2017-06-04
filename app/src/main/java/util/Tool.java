package util;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Util Class Tool
 * Convert date to string and reverse
 *
 * @author N. Hafen
 * @version 1.0
 */

public class Tool {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private static final String  LOGTAG = Tool.class.getSimpleName();

    /**
     * Convert string to a Date
     * and return a Date Object
     *
     * @param s String with a Date.
     *
     * @return  Date Object
     */
    public static Date stringToDate(String s){
        try {
            return sdf.parse(s);
        }catch(Exception e){
            Log.e(LOGTAG,"Error in stringToDate " , e);
            return null;
        }
    }

    /**
     * Convert Date Object to String
     * and return a string with the Date
     *
     * @param d Date Object.
     *
     * @return  Date as String
     */
    public static String dateToString(Date d){
        return sdf.format(d);
    }
}
