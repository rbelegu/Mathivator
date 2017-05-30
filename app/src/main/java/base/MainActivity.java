package base;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.admin.mathivator.R;

import global.GlobalEvents;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = AppCompatActivity.class.getSimpleName();
    private View.OnClickListener settingsListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG,"Start ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    public void initialize(){
        Button settingsButton = (Button) findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(goToSettingsListener());
        Button playButton = (Button) findViewById(R.id.mainPlay);
        playButton.setOnClickListener(goToPlayListener());
        Button highscoreButton = (Button) findViewById(R.id.highscoreButton);
        highscoreButton.setOnClickListener(goToHighscoreListener());
    }

    public View.OnClickListener goToHighscoreListener(){
        settingsListener = new View.OnClickListener(){
            @Override
            public void onClick(View v){
                GlobalEvents.HIGHSCORE.goTo(v);
            }
        };
        return settingsListener;
    }

    public View.OnClickListener goToSettingsListener(){
        settingsListener = new View.OnClickListener(){
            @Override
            public void onClick(View v){
                GlobalEvents.SETTINGS.goTo(v);
            }
        };
        return settingsListener;
    }

    public View.OnClickListener goToPlayListener(){
        settingsListener = new View.OnClickListener(){
            @Override
            public void onClick(View v){
                GlobalEvents.PLAY.goTo(v);
            }
        };
        return settingsListener;
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
