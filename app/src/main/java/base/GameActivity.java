package base;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.mathivator.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import business.Exercise;
import business.Highscore;
import business.Settings;
import data.DataHighscore;
import data.DataSettings;
import global.GlobalEvents;

/**
 * Class GameActivity
 *
 * Handles Game Start, Switch between Excercises and GPS
 *
 *
 * @author R. Belegu
 * @version 1.0
 */

public class GameActivity extends AppCompatActivity {
    private static final String LOG_TAG = GameActivity.class.getSimpleName();
    private Settings settings;
    private Game game;
    LinearLayout history;
    int currentExerciseIndex = 0;
    int currentPoints = 0;
    String solutionText = "";
    TextView solutionView;
    TextView timeView;
    TextView pointView;
    TextView exerciseView;
    List<TextView> historyList;
    private long startTime;
    private long time = 0;
    boolean play;
    Thread t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        initialize();
    }
    /**
     * Load Settings and defines Views
     *
     */
    private void initialize(){
        try {
            settings = DataSettings.getSettings(this);
            solutionView = (TextView) findViewById(R.id.answerBox);
            timeView = (TextView) findViewById(R.id.timeBox);
            exerciseView = (TextView) findViewById(R.id.exerciseBox);
            pointView = (TextView) findViewById(R.id.pointBox);
            Log.d(LOG_TAG,"anzahl " + settings.getRechenOperatoren().size() );
            if(settings.getRechenOperatoren().isEmpty()){
                Toast.makeText(this, R.string.error_settings, Toast.LENGTH_LONG).show();

                GlobalEvents.SETTINGS.goTo(this.solutionView);
                return;
            }
            //TODO v1.1: chk Existing Game an restart it
            game = new Game(settings);
            game.initializeGame();
            startTime = new Date().getTime();
            initializeTime();
            setExercise();
            createHistory();
            play=true;

        }catch(Exception e){
            Log.e(LOG_TAG,"Es ist ein Fehler in der Funktion initialize Klasse GameActivity aufgetreten" + e.toString() ,e);
        }
    }
    /**
     * Sets up the timer in an Background-Thread
     *
     */
    private void initializeTime(){
        t = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                long t = new Date().getTime();
                                time = (int)((t-startTime)/1000);
                                timeView.setText(String.valueOf(time));
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    Log.e(LOG_TAG,"Error in time Thread",e);
                }
            }
        };

        t.start();
    }
    /**
     * Sets up the History and dynamically generates the
     * Excercise Success or Fail bar
     *
     */
    private void createHistory(){
        history = (LinearLayout)findViewById(R.id.historyBox);
        historyList = new ArrayList<>();
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int boxWidth = (width/Game.EXCERCICECOUNT);
        int count = 1;
        for(int i = 0; i < Game.EXCERCICECOUNT; i++){
            TextView historyBox = new TextView(this);
            historyBox.setText(String.valueOf(count));
            historyBox.setBackgroundColor(ContextCompat.getColor(this,R.color.lightGrey));
            historyBox.setWidth(boxWidth);
            historyBox.setPadding(2,2,2,2);
            history.addView(historyBox);
            historyList.add(historyBox);
            count++;
        }
    }

    /**
     * Sets solution text
     * @param v the actual View
     *
     */

    public void setNumber(View v){
        if(play) {
            Button b = (Button) v;
            int number = Integer.parseInt(b.getText().toString());
            solutionText += number;
            solutionView.setText(solutionText);
        }
    }
    /**
     * Goes Back one position Back if game is active and solution Text is bigger 1
     * Removes the Character at last Position
     * @param v the actual View
     *
     */

    public void onBack(View v){
        if(solutionText.length() > 0 && play){
            solutionText = solutionText.substring(0,solutionText.length()-1);
            solutionView.setText(solutionText);
        }
    }
    /**
     * Changes the Excercise if not the last one
     * If its the Last Excercise it stops the game and the timer Thread
     * @param v the actual View
     *
     */

    public void next(View v){
        if(play) {
            checkSolution();
            if (currentExerciseIndex < Game.EXCERCICECOUNT) {
                setExercise();
            } else {
                play = false;
                t.interrupt();
                finishGame();
                GlobalEvents.HIGHSCORE.goTo(v);
            }
        }
    }

    /**
     * Finish the Game and fills in HighScore Values and GPS Location
     *
     */
    private void finishGame(){
        Highscore highscore = new Highscore();
        highscore.setHighscore(currentPoints);
        highscore.setName(settings.getName());
        Long d = new Date().getTime();
        highscore.setTime((int)((d-startTime)/1000));
        highscore.setDate(new Date(startTime));
        String l = getLocation();
        if(l!=null) {
            highscore.setCity(l);
        }
        DataHighscore.insertHigscore(highscore,this);

    }

    /**
     * Read the GPS location and return the City
     *
     */

    private String getLocation(){
        String finalCity = null;
        LocationManager locationManager =(LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        try {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                            PackageManager.PERMISSION_GRANTED) {
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if(location != null) {
                    double lat = location.getLatitude();
                    double lng = location.getLongitude();
                    Geocoder geoCoder = new Geocoder(this, Locale.getDefault());
                    List<Address> address = geoCoder.getFromLocation(lat, lng, 1);
                    int maxLines = address.get(0).getMaxAddressLineIndex();
                    finalCity = address.get(0).getAddressLine(maxLines - 1);
                }else{
                    Toast.makeText(this, R.string.error_gpsnotfound, Toast.LENGTH_LONG).show();
                }
            }else {
                Toast.makeText(this, R.string.error_permission, Toast.LENGTH_LONG).show();
            }

            return finalCity;
        } catch (SecurityException e) {
            Log.e(LOG_TAG,"no gps alowed",e);
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * Checks the Solution and updates the dynamically created success bar
     * if the Solution is correct the Backgroundcolor is changed to green
     * otherwise its changed to red
     * If correct it also adds the Points to the current amount of Points
     *
     */

    private void checkSolution(){
        if(!solutionText.isEmpty()) {
            Exercise ex = game.getExerciseList().get(currentExerciseIndex);
            if (ex.getSolution() == Integer.parseInt(solutionView.getText().toString())) {
                currentPoints += ex.getPoints();
                historyList.get(currentExerciseIndex).setBackgroundColor(Color.GREEN);
                pointView.setText(String.valueOf(currentPoints));
            } else {
                historyList.get(currentExerciseIndex).setBackgroundColor(Color.RED);
            }
            currentExerciseIndex++;
        }
    }
    /**
     * Builds the Display of the Excercise based on the Operator
     *
     */

    private void setExercise(){
        Exercise exercise = game.getExerciseList().get(currentExerciseIndex);
        String aufgabe = "";
        aufgabe += exercise.getFirst();
        switch(exercise.getOperator()){
            case 1 : aufgabe += " + ";
                break;
            case 2 : aufgabe += " - ";
                break;
            case 3 : aufgabe += " * ";
                break;
            case 4 : aufgabe += " / ";
                break;
        }
        aufgabe += exercise.getSecond();
        exerciseView.setText(aufgabe);
        solutionView.setText("");
        solutionText="";
    }
    
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int btnSize = (size.y-100)/8;

        ArrayList<View> allButtons;
        allButtons = ((LinearLayout) findViewById(R.id.digitsBlock)).getTouchables();

        for(View btn_temp : allButtons)
        {
            btn_temp.getLayoutParams().height = btnSize;
            btn_temp.getLayoutParams().width = btnSize;
            btn_temp.requestLayout();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.globalmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        MenuEventListener.getInstance().activateMenu(this,id);
        return super.onOptionsItemSelected(item);
    }
}
