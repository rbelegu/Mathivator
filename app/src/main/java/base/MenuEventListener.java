package base;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.admin.mathivator.R;

/**
 * Created by admin on 24.05.2017.
 */

public class MenuEventListener {
    private static MenuEventListener INSTANCE = new MenuEventListener();
    private static final String LOG_TAG = MenuEventListener.class.getSimpleName();

    public static MenuEventListener getInstance() {
        return INSTANCE;
    }
    private MenuEventListener() {
    }

    public void activateMenu(AppCompatActivity activity, int id){
        switch(id){
            case R.id.menuhome :
                goToMain(activity);
                break;
            case R.id.menuplay :
                goToPlay(activity);
                break;
            case R.id.menuhighscore :
                goToHighscore(activity);
                break;
            case R.id.menusettings :
                goToSettings(activity);
                break;
        }
    }

    private void goToHighscore(AppCompatActivity activity){
        try{
            Intent nextScreen = new Intent(activity, HighscoreActivity.class);
            activity.startActivity(nextScreen);
        }catch(Exception e){
            Log.d(LOG_TAG,"Error in goToHighscore " + e );
        }
    }

    private void goToSettings(AppCompatActivity activity){
        try{
            Intent nextScreen = new Intent(activity, SettingsActivity.class);
            activity.startActivity(nextScreen);
        }catch(Exception e){
            Log.d(LOG_TAG,"Error in goToSettings " + e );
        }
    }

    private void goToMain(AppCompatActivity activity){
        try{
            Intent nextScreen = new Intent(activity, MainActivity.class);
            activity.startActivity(nextScreen);
        }catch(Exception e){
            Log.d(LOG_TAG,"Error in goToMain " + e );
        }
    }

    private void goToPlay(AppCompatActivity activity){
        try{
            Intent nextScreen = new Intent(activity, GameActivity.class);
            activity.startActivity(nextScreen);
        }catch(Exception e){
            Log.d(LOG_TAG,"Error in goToPlay " + e );
        }
    }
}
