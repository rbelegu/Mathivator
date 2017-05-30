package data;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import business.Settings;
/**
 * Created by admin on 22.05.2017.
 */
public class DataSettings {
    private static SQLiteDatabase database;
    private static final String LOG_TAG = DataSettings.class.getSimpleName();

    public static Settings getSettings(Context context){
        database = DBCreator.getInstance(context).getWritableDatabase();
        Cursor cursor = database.query(DBCreator.TABELLESETTINGS, null,DBCreator.IDFELD+"=1", null, null, null, null);
        cursor.moveToFirst();
        Settings settings = settingFromCursor(cursor);
        cursor.close();
        database.close();
        return settings;
    }

    private static Settings settingFromCursor(Cursor cursor){
        Settings settings = new Settings();
        settings.setId( cursor.getInt(cursor.getColumnIndex(DBCreator.IDFELD)));
        settings.setName(cursor.getString(cursor.getColumnIndex(DBCreator.NAMEFELD)));
        String rechenEinheitenString = cursor.getString(cursor.getColumnIndex(DBCreator.RECHENEINHEITENFELD));
        settings.setRechenOperatoren(generateRecheneinheiten(rechenEinheitenString));
        settings.setMaximumPoints(cursor.getInt(cursor.getColumnIndex(DBCreator.MAXPOINTSFELD)));
        settings.setZahlenRaum(cursor.getInt(cursor.getColumnIndex(DBCreator.ZAHLENRAUM)));
        return settings;
    }

    private static List<Integer> generateRecheneinheiten(String rechenEinheitenString){
        List<Integer> list = new ArrayList<>();
        for(String s : rechenEinheitenString.split(",")){
            list.add(Integer.parseInt(s));
        }
        return list;
    }

    public static void saveSettings (Settings settings,Context context){
        try{
            database = DBCreator.getInstance(context).getWritableDatabase();
            ContentValues cvvalues = createContentValues(settings);
            String where = DBCreator.IDFELD + "='" + settings.getId()+"'";
            database.update(DBCreator.TABELLESETTINGS,cvvalues,where,null);
            database.close();
        }catch(Exception e){
            Log.e(LOG_TAG,"Error in updatePlan ",e);
        }
    }

    private static ContentValues createContentValues(Settings settings){
        ContentValues cv = new ContentValues();
        cv.put(DBCreator.IDFELD, settings.getId());
        cv.put(DBCreator.NAMEFELD, settings.getName());
        cv.put(DBCreator.MAXPOINTSFELD, settings.getMaximumPoints());
        String listString = "";
        for (Integer re : settings.getRechenOperatoren()) {
            if(listString.length()>0){
                listString += ",";
            }
            listString += re;
        }
        cv.put(DBCreator.RECHENEINHEITENFELD, listString);
        cv.put(DBCreator.MAXPOINTSFELD,settings.getMaximumPoints());
        cv.put(DBCreator.HIGHSCOREFELD, settings.getHighScore());
        cv.put(DBCreator.ZAHLENRAUM, settings.getZahlenRaum());
        return cv;
    }
}
