package data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by admin on 22.05.2017.
 * as
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
    public static final String TIMEfELD = "gameTime";
    public static final String DATEFELD ="date";
    public static final String LATFELD ="lat";
    public static final String LONFELD ="lon";
    public DBCreator(Context context) {
        super(context, DATENBANK_NAME, null, DATENBANK_VERSION);
        Log.d(LOG_TAG, "DBCreator hat die Datenbank " + getDatabaseName() + "erstellt");
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(LOG_TAG, "Erstelle Datenbank");
        db.execSQL("CREATE TABLE if not exists `"+TABELLESETTINGS+"` ( `"+IDFELD+"` INTEGER, `"+RECHENEINHEITENFELD+"` TEXT, `"+MAXPOINTSFELD+"` INTEGER, `"+HIGHSCOREFELD+"` INTEGER, `"+NAMEFELD+"` TEXT, `"+ZAHLENRAUM+"` INTEGER, PRIMARY KEY(`id`) );");
        db.execSQL("CREATE TABLE if not exists `"+TABELLEHIGHSCORE+"` ( `"+IDFELD+"` INTEGER PRIMARY KEY AUTOINCREMENT, `"+HIGHSCOREFELD+"` INTEGER, `"+TIMEfELD+"` INTEGER, `"+DATEFELD+"` text, `"+NAMEFELD+"` TEXT,`"+LATFELD+"` NUMERIC,`"+LONFELD+"` NUMERIC);");

        db.execSQL("INSERT INTO `"+TABELLESETTINGS+"` VALUES(1,'1,2,3,4',18,0,'',50)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //falls updates gemacht werden hier Änderungen eintragen
    }
    /**
     * Erstelle eine Instanz nach Singelton Pattern
     * */
    public static synchronized DBCreator getInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = new DBCreator(context.getApplicationContext());
        }
        return INSTANCE;
    }

}
