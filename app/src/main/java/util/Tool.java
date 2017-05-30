package util;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by admin on 28.05.2017.
 */

public class Tool {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private static final String  LOGTAG = Tool.class.getSimpleName();;
    public static Date stringToDate(String s){
        try {
            return sdf.parse(s);
        }catch(Exception e){
            Log.e(LOGTAG,"Error in stringToDate " , e);
            return null;
        }
    }
    public static String dateToString(Date d){
        return sdf.format(d);
    }
}
