package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import business.Highscore;
import util.Tool;

/**
 * Managing the Data in the "highscore" Table on the DB
 * Insert/Select (List)
 *
 * @author D. Tsichlakis
 * @version 1.0
 */
public class DataHighscore {
    private static SQLiteDatabase database;
    private static final String LOG_TAG = DataHighscore.class.getSimpleName();

    /**
     * Get all highscore entries from DB (Highscore Table)
     * and return a highscore List with the Highscore Objects
     *
     * @param context The current context.
     *
     * @return  Highscore List
     */
    public static List<Highscore> getHighscore(Context context){
        database = DBCreator.getInstance(context).getWritableDatabase();
        Cursor cursor = database.query(DBCreator.TABELLEHIGHSCORE, null,null, null, null, null, DBCreator.HIGHSCOREFELD+" DESC,"+DBCreator.TIMEFELD+","+DBCreator.DATEFELD + " DESC");
        List<Highscore> highScoreList = new ArrayList<>();
        while (cursor.moveToNext()) {
            highScoreList.add(higscoreFromCursor(cursor));
        }
        Log.d(LOG_TAG,"Anzahl Highscores " + highScoreList.size());
        cursor.close();
        database.close();
        return highScoreList;
    }

    /**
     * Creates a Highscore Object  from the
     * data of the DB row (cursor)
     *
     * @param cursor The database cursor
     * @return  Highscore Object
     */
    private static Highscore higscoreFromCursor(Cursor cursor){
        Highscore highscore = new Highscore();
        highscore.setId(cursor.getInt(cursor.getColumnIndex(DBCreator.IDFELD)));
        highscore.setDate(Tool.stringToDate(cursor.getString(cursor.getColumnIndex(DBCreator.DATEFELD))));
        highscore.setTime(cursor.getInt(cursor.getColumnIndex(DBCreator.TIMEFELD)));
        highscore.setHighscore(cursor.getInt(cursor.getColumnIndex(DBCreator.HIGHSCOREFELD)));
        highscore.setName(cursor.getString(cursor.getColumnIndex(DBCreator.NAMEFELD)));
        highscore.setCity(cursor.getString(cursor.getColumnIndex(DBCreator.CITYFELD)));
        //highscore.setLon(cursor.getDouble(cursor.getColumnIndex(DBCreator.LONFELD)));
        return highscore;
    }

    /**
     * Insert Highscore Object into DB.
     *
     * @param highscore The Highscore Object.
     * @param context   The current context.
     */
    public static void insertHigscore (Highscore highscore,Context context){
        try{
            database = DBCreator.getInstance(context).getWritableDatabase();
            ContentValues cvalues = createContentValues(highscore);
            Log.d(LOG_TAG,"Speichere Highscore");
            database.insert(DBCreator.TABELLEHIGHSCORE, null, cvalues);
            database.close();
        }catch(Exception e){
            Log.e(LOG_TAG,"Error in updatePlan ",e);
        }
    }

    /**
     * Create and return ContentValues Object from
     * Highscore Object.
     *
     * @param highscore The Highscore Object.
     * @return cv  ContentValues Object.
     */
    private static ContentValues createContentValues(Highscore highscore){
        ContentValues cv = new ContentValues();
        cv.put(DBCreator.NAMEFELD, highscore.getName());
        cv.put(DBCreator.HIGHSCOREFELD, highscore.getHighscore());
        cv.put(DBCreator.TIMEFELD, highscore.getTime());
        cv.put(DBCreator.DATEFELD,Tool.dateToString(highscore.getDate()));
        cv.put(DBCreator.CITYFELD,highscore.getCity());
        //cv.put(DBCreator.LONFELD,highscore.getLon());
        return cv;
    }
}
