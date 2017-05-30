package base;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import business.Exercise;
import business.Highscore;
import business.Settings;
import data.DataHighscore;
import data.DataSettings;
import global.GlobalEvents;

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

    private void initialize(){
        try {
            settings = DataSettings.getSettings(this);
            solutionView = (TextView) findViewById(R.id.answerBox);
            timeView = (TextView) findViewById(R.id.timeBox);
            exerciseView = (TextView) findViewById(R.id.exerciseBox);
            pointView = (TextView) findViewById(R.id.pointBox);
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

    private void createHistory(){
        history = (LinearLayout)findViewById(R.id.historyBox);
        historyList = new ArrayList<>();
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int boxWidth = (width/12);
        int count = 1;
        for(int i = 0; i < 12; i++){
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



    public void setNumber(View v){
        if(play) {
            Button b = (Button) v;
            int number = Integer.parseInt(b.getText().toString());
            solutionText += number;
            solutionView.setText(solutionText);
        }
    }

    public void onBack(View v){
        if(solutionText.length() > 0 && play){
            solutionText = solutionText.substring(0,solutionText.length()-1);
            solutionView.setText(solutionText);
        }
    }

    public void next(View v){
        if(play) {
            checkSolution();
            if (currentExerciseIndex < 12) {
                setExercise();
            } else {
                play = false;
                t.interrupt();
                finishGame();
                GlobalEvents.HIGHSCORE.goTo(v);
            }
        }
    }

    private void finishGame(){
        Highscore highscore = new Highscore();
        highscore.setHighscore(currentPoints);
        highscore.setName(settings.getName());
        Long d = new Date().getTime();
        highscore.setTime((int)((d-startTime)/1000));
        highscore.setDate(new Date(startTime));
        Location l = getLocation();
        if(l!=null) {
            highscore.setLat(l.getLatitude());
            highscore.setLon(l.getLongitude());
        }
        DataHighscore.insertHigscore(highscore,this);

    }

    private Location getLocation(){
        LocationManager locationManager =(LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        try {
            Location location = null;
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                            PackageManager.PERMISSION_GRANTED) {
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }else {
                Toast.makeText(this, R.string.error_permission, Toast.LENGTH_LONG).show();
            }
            return location;
        } catch (SecurityException e) {
            Log.e(LOG_TAG,"no gps alowed",e);
            return null;
        }
    }

    private void checkSolution(){
        if(!solutionText.isEmpty()) {
            Exercise ex = game.getExerciseList().get(currentExerciseIndex);

            if (ex.getSolution() == Integer.parseInt(solutionView.getText().toString())) {
                ex.setCorrect(true);
                currentPoints += settings.getMaximumPoints();
                historyList.get(currentExerciseIndex).setBackgroundColor(Color.GREEN);
                pointView.setText(String.valueOf(currentPoints));
            } else {
                ex.setCorrect(false);
                historyList.get(currentExerciseIndex).setBackgroundColor(Color.RED);
            }
            currentExerciseIndex++;
        }
    }

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
