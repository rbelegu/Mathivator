package data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Creates Database with Tables
 * and Database connection
 *
 * @author D. Tsichlakis + G. Ramizi
 */

public class DBCreator extends SQLiteOpenHelper {
    public static DBCreator INSTANCE;
    public static final String DATENBANK_NAME = "mathivator.db";
    public static final int DATENBANK_VERSION = 1;
    public static final String LOG_TAG = SQLiteOpenHelper.class.getSimpleName();
    public static final String  IDFELD = "id";
    public static final String  RECHENEINHEITENFELD = "rechenEinheiten";
    public static final String  MAXPOINTSFELD = "maximumPoints";
    public static final String  HIGHSCOREFELD = "highScore";
    public static final String  NAMEFELD = "name";
    public static final String  ZAHLENRAUM = "zahlenRaum";
    public static final String  TABELLESETTINGS = "settings";
    public static final String TABELLEHIGHSCORE = "highscore";
    public static final String TIMEFELD = "gameTime";
    public static final String DATEFELD ="date";
    public static final String CITYFELD ="city";
    //public static final String LONFELD ="lon";


    /**
     * This handles the creation of the DB and
     * all database tables
     *
     * @param context The current context.
     */
    public DBCreator(Context context) {
        super(context, DATENBANK_NAME, null, DATENBANK_VERSION);
        Log.d(LOG_TAG, "DBCreator hat die Datenbank " + getDatabaseName() + "erstellt");
    }

    /**
     * Called when the database is created for the first time
     * Creates "settings" and "highscore" Table with attributes (incl. types)
     * Sets some default values in the "setting" Table
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(LOG_TAG, "Erstelle Datenbank");
        db.execSQL("CREATE TABLE if not exists `"+TABELLESETTINGS+"` ( `"+IDFELD+"` INTEGER, `"+RECHENEINHEITENFELD+"` TEXT, `"+MAXPOINTSFELD+"` INTEGER, `"+HIGHSCOREFELD+"` INTEGER, `"+NAMEFELD+"` TEXT, `"+ZAHLENRAUM+"` INTEGER, PRIMARY KEY(`id`) );");
        db.execSQL("CREATE TABLE if not exists `"+TABELLEHIGHSCORE+"` ( `"+IDFELD+"` INTEGER PRIMARY KEY AUTOINCREMENT, `"+HIGHSCOREFELD+"` INTEGER, `"+ TIMEFELD +"` INTEGER, `"+DATEFELD+"` text, `"+NAMEFELD+"` TEXT,`"+CITYFELD+"` STRING);");

        db.execSQL("INSERT INTO `"+TABELLESETTINGS+"` VALUES(1,'1,2,3,4',3,0,'',50)");
    }


    /**
     * Called when the database needs to be upgraded.
     *
     * @param db The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //falls updates gemacht werden hier Änderungen eintragen
    }

    /**
     * Creates and Return DB instance (Singelton Pattern)
     *
     * @param context The current context.     *
     * @return  DB instance
     */
    public static synchronized DBCreator getInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = new DBCreator(context.getApplicationContext());
        }
        return INSTANCE;
    }

}
