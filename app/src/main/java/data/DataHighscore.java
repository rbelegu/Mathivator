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
 * Created by admin on 22.05.2017.
 */
public class DataHighscore {
    private static SQLiteDatabase database;
    private static final String LOG_TAG = DataHighscore.class.getSimpleName();

    public static List<Highscore> getHighscore(Context context){
        database = DBCreator.getInstance(context).getWritableDatabase();
        Cursor cursor = database.query(DBCreator.TABELLEHIGHSCORE, null,null, null, null, null, DBCreator.HIGHSCOREFELD+","+DBCreator.DATEFELD);
        List<Highscore> highScoreList = new ArrayList<>();
        while (cursor.moveToNext()) {
            highScoreList.add(higscoreFromCursor(cursor));
        }
        Log.d(LOG_TAG,"Anzahl Highscores " + highScoreList.size());
        cursor.close();
        database.close();
        cursor.close();
        database.close();
        return highScoreList;
    }

    private static Highscore higscoreFromCursor(Cursor cursor){
        Highscore highscore = new Highscore();
        highscore.setId(cursor.getInt(cursor.getColumnIndex(DBCreator.IDFELD)));
        highscore.setDate(Tool.stringToDate(cursor.getString(cursor.getColumnIndex(DBCreator.DATEFELD))));
        highscore.setTime(cursor.getInt(cursor.getColumnIndex(DBCreator.TIMEfELD)));
        highscore.setHighscore(cursor.getInt(cursor.getColumnIndex(DBCreator.HIGHSCOREFELD)));
        highscore.setName(cursor.getString(cursor.getColumnIndex(DBCreator.NAMEFELD)));
        highscore.setLat(cursor.getDouble(cursor.getColumnIndex(DBCreator.LATFELD)));
        highscore.setLon(cursor.getDouble(cursor.getColumnIndex(DBCreator.LONFELD)));
        return highscore;
    }


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

    private static ContentValues createContentValues(Highscore highscore){
        ContentValues cv = new ContentValues();
        cv.put(DBCreator.NAMEFELD, highscore.getName());
        cv.put(DBCreator.HIGHSCOREFELD, highscore.getHighscore());
        cv.put(DBCreator.TIMEfELD, highscore.getTime());
        cv.put(DBCreator.DATEFELD,Tool.dateToString(highscore.getDate()));
        cv.put(DBCreator.LATFELD,highscore.getLat());
        cv.put(DBCreator.LONFELD,highscore.getLon());
        return cv;
    }
}
