package global;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import base.GameActivity;
import base.HighscoreActivity;
import base.MainActivity;
import base.SettingsActivity;

/**
 * Created by admin on 24.05.2017.
 */

public enum GlobalEvents {
    PLAY(1),SETTINGS(2),HIGHSCORE(3),MAIN(4);
    private int id;
    private final String LOG_TAG = GlobalEvents.class.getName();
    GlobalEvents(int id){
        this.id=id;
    }
    public void goTo(View v){
        switch(id){
            case 1 : goToPlay(v);
                break;
            case 2 : goToSettings(v);
                break;
            case 3 : gotToHighScore(v);
                break;
            case 4 : goToMain(v);
                break;
        }
    }

    private void goToSettings(View v){
        try{
            Intent nextScreen = new Intent(v.getContext(), SettingsActivity.class);
            v.getContext().startActivity(nextScreen);
        }catch(Exception e){
            Log.d(LOG_TAG,"Error in goToSettingsListener " + e );
        }
    }

    private void goToMain(View v){
        try{
            Intent nextScreen = new Intent(v.getContext(), MainActivity.class);
            v.getContext().startActivity(nextScreen);
        }catch(Exception e){
            Log.d(LOG_TAG,"Error in goToMainListener " + e );
        }
    }

    private void goToPlay(View v){
        try{
            Intent nextScreen = new Intent(v.getContext(), GameActivity.class);
            v.getContext().startActivity(nextScreen);
        }catch(Exception e){
            Log.d(LOG_TAG,"Error in goToPlayListener " + e );
        }
    }
    private void gotToHighScore(View v){
        try{
            Intent nextScreen = new Intent(v.getContext(), HighscoreActivity.class);
            v.getContext().startActivity(nextScreen);
        }catch(Exception e){
            Log.d(LOG_TAG,"Error in gotToHighScore " + e );
        }
    }

}
