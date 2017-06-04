package base;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.admin.mathivator.R;

/**
 * Class/Listener "MenuEventListener" as Singleton to handle Options-Menu Events.
 *
 * @author N. Hafen
 * @version 1.0
 */

public class MenuEventListener {
    private static MenuEventListener INSTANCE = new MenuEventListener();
    private static final String LOG_TAG = MenuEventListener.class.getSimpleName();

    /*
    * Method to get an Instance of this Listener.
    *
    * @return Instance of MenuEventListener
    * */
    public static MenuEventListener getInstance() {
        return INSTANCE;
    }
    private MenuEventListener() {
    }

    /*
    * Main method to handle all menuentries based on their IDs.
    *
    * @param activity Ancestor of Activities
    * @param id       Name/ID of the clicked activity.
    *
    * */
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

    /*
    * Method to call the "Rangliste".
    *
    * @param activity Ancestor of Activity
    *
    * */
    private void goToHighscore(AppCompatActivity activity){
        try{
            Intent nextScreen = new Intent(activity, HighscoreActivity.class);
            activity.startActivity(nextScreen);
        }catch(Exception e){
            Log.d(LOG_TAG,"Error in goToHighscore " + e );
        }
    }

    /*
    * Method to call the "Einstellungen".
    *
    * @param activity Ancestor of Activity
    *
    * */
    private void goToSettings(AppCompatActivity activity){
        try{
            Intent nextScreen = new Intent(activity, SettingsActivity.class);
            activity.startActivity(nextScreen);
        }catch(Exception e){
            Log.d(LOG_TAG,"Error in goToSettings " + e );
        }
    }

    /*
    * Method to call the "Start".
    *
    * @param activity Ancestor of Activity
    *
    * */
    private void goToMain(AppCompatActivity activity){
        try{
            Intent nextScreen = new Intent(activity, MainActivity.class);
            activity.startActivity(nextScreen);
        }catch(Exception e){
            Log.d(LOG_TAG,"Error in goToMain " + e );
        }
    }
    /*
    * Method to run the "Game".
    *
    * @param activity Ancestor of Activity
    *
    * */
    private void goToPlay(AppCompatActivity activity){
        try{
            Intent nextScreen = new Intent(activity, GameActivity.class);
            activity.startActivity(nextScreen);
        }catch(Exception e){
            Log.d(LOG_TAG,"Error in goToPlay " + e );
        }
    }
}
