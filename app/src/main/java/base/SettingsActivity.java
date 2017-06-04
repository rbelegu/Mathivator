package base;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.mathivator.R;
import java.util.Iterator;
import java.util.List;
import business.Settings;
import data.DataSettings;
import global.GlobalEvents;

/**
 * Class SettingsActibity
 * Different methods to manage Player's Name (with ChangedListener),
 * "Zahlenraum", "Rechenoperatoren" and max possibel "Highscore"
 *
 * @author D. Tsichlakis + N. Hafen
 * @version 1.0
 */
public class SettingsActivity extends AppCompatActivity {
    private static final String LOG_TAG = AppCompatActivity.class.getSimpleName();
    private Button plus;
    private Button minus;
    private Button mal;
    private Button durch;
    private Button zehn;
    private Button zwanzig;
    private Button fuenfzig;
    private Button hundert;
    private Button zweiFuenfzig;
    private Button fuenfhundert;
    private Button tausend;
    private EditText name;
    private TextView highscore;
    private Settings settings;
    private int zahlenraumPoints;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initialize();
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            clickSave();
            }
        });
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

    /**
     * Initialize
     */
    private void initialize(){
        settings = DataSettings.getSettings(this);
        Log.d(LOG_TAG,"zahlenraum" + settings.getZahlenRaum());
        plus = (Button)this.findViewById(R.id.plus);
        minus = (Button)this.findViewById(R.id.minus);
        mal = (Button)this.findViewById(R.id.mal);
        durch = (Button)this.findViewById(R.id.durch);
        zehn = (Button)this.findViewById(R.id.zahlenraum1);
        zwanzig = (Button)this.findViewById(R.id.zahlenraum2);
        fuenfzig = (Button)this.findViewById(R.id.zahlenraum3);
        hundert = (Button)this.findViewById(R.id.zahlenraum4);
        zweiFuenfzig = (Button)this.findViewById(R.id.zahlenraum5);
        fuenfhundert = (Button)this.findViewById(R.id.zahlenraum6);
        tausend = (Button)this.findViewById(R.id.zahlenraum7);
        name = (EditText)this.findViewById(R.id.settingsName);
        highscore = (TextView)this.findViewById(R.id.highscoreView);
        fillField();
    }

    /**
     * "Rechenoperator" changed, then run methods
     * update Highscore and save settings
     *
     * @param view Current view.
     *
     */
    public void clickRechenoperation(View view){
        Button b = (Button) view;
        addRechenoperation(b.getId());
        calcHighscore();
        clickSave();
    }

    /**
     * Check which Rechenoperator has changed
     *
     * @param id ID of Rechenoperator.
     *
     */
    private void addRechenoperation(int id){
        switch(id){
            case R.id.plus :
                changeRechenoperation(1,plus);
                break;
            case R.id.minus :
                changeRechenoperation(2,minus);
                break;
            case R.id.mal :
                changeRechenoperation(3,mal);
                break;
            case R.id.durch :
                changeRechenoperation(4,durch);
                break;
        }
    }

    /**
     * Check if Rechenoperator Status from activated to deactivated
     * or reverse has changed. Depends on case call method.
     *
     * @param id ID of Rechenoperator.
     * @param b Button.
     *
     */
    private void changeRechenoperation(int id,Button b){
        if(!settings.getRechenOperatoren().contains(id)){
            settings.getRechenOperatoren().add(id);
            setActive(b);
        }else{
            Iterator<Integer> it = settings.getRechenOperatoren().iterator();
            while(it.hasNext()){
                Integer val = it.next();
                if(val == id){
                    it.remove();
                    break;
                }
            }
            setInActive(b);
        }
    }

    /**
     * Calculate and show new Highscore
     *
     *
     */
    private void calcHighscore(){
        int typeCount = 0;
        int typeNr = 0;
        int maxPoints = 0;
        if(settings.getRechenOperatoren().isEmpty()){
            highscore.setText(String.valueOf(maxPoints));
            return;
        }
        int length = settings.getRechenOperatoren().size();
        int typeLength = Game.EXCERCICECOUNT / length;
        for(int i=0;i<Game.EXCERCICECOUNT;i++){
            if(typeCount == typeLength){
                typeNr++;
                typeCount = 0;
            }
            int operator = settings.getRechenOperatoren().get(typeNr);
            switch(operator){
                case 1:
                    maxPoints += zahlenraumPoints;
                    Log.d(LOG_TAG,"Plus - Gewichtung 1");
                    break;
                case 2:
                    maxPoints += zahlenraumPoints;
                    Log.d(LOG_TAG,"Minus - Gewichtung 1");
                    break;
                case 3:
                    maxPoints += zahlenraumPoints * 2;
                    Log.d(LOG_TAG,"Mal - Gewichtung 2");
                    break;
                case 4:
                    maxPoints += zahlenraumPoints * 2;
                    Log.d(LOG_TAG,"Teilen - Gewichtung 2");
                    break;
            }
            typeCount++;
        }
        Log.d(LOG_TAG, "Max HighScore" + maxPoints);
        settings.setHighScore(maxPoints);
       highscore.setText(String.valueOf(maxPoints));
    }


    /**
     * "Zahlenraum" changed, then run methods
     * update Highscore and save settings
     *
     * @param v Current view.
     *
     */
    public void clickZahlenraum(View v){
        Button button = (Button)v;
        clearZahlenraum();
        String buttonText = button.getText().toString();
        Log.d(LOG_TAG,"Text " + buttonText);
        fillZahlenraum(Integer.parseInt(buttonText));
        calcHighscore();
        clickSave();

    }

    /**
     * Save Settings itno DB
     *
     */
    public void clickSave(){
        settings.setName(name.getText().toString());
        settings.setMaximumPoints(zahlenraumPoints);
        DataSettings.saveSettings(settings,this);
    }

    /**
     * Fill the fields in the View with the values
     * direct or by using other methods
     *
     */
    private void fillField(){
        fillRecheneinheiten(settings.getRechenOperatoren());
        fillZahlenraum(settings.getZahlenRaum());
        name.setText(settings.getName());
        highscore.setText(String.valueOf(settings.getHighScore()));

    }

    /**
     * Check if Rechneinheit (Rechenoperator) is in the List.
     * If Yes set then call Method to set Rechenoperator Button to active.
     *
     * @param recheneinheiten List with "Recheneinheiten".
     */
    private void fillRecheneinheiten(List<Integer> recheneinheiten) {
        if (recheneinheiten.contains(1)) {
            setActive(plus);
        }
        if (recheneinheiten.contains(2)) {
            setActive(minus);
        }
        if (recheneinheiten.contains(3)) {
            setActive(mal);
        }
        if (recheneinheiten.contains(4)) {
            setActive(durch);
        }
    }

    /**
     * Set all "Zahlraum" Buttons to inactive.
     *
     */
    private void clearZahlenraum(){
        setInActive(zehn);
        setInActive(zwanzig);
        setInActive(fuenfzig);
        setInActive(hundert);
        setInActive(zweiFuenfzig);
        setInActive(fuenfhundert);
        setInActive(tausend);
    }

    /**
     * Check which Zahlenraum is in the Parameter.
     * Then call Method to set this Zahlenraum Button to active.
     *
     * @param zahlenraum Zahlenraum.
     */
    private void fillZahlenraum(int zahlenraum){
        settings.setZahlenRaum(zahlenraum);
        switch(zahlenraum){
            case 10: setActive(zehn);
                zahlenraumPoints=1;
                break;
            case 20: setActive(zwanzig);
                zahlenraumPoints=2;
                break;
            case 50: setActive(fuenfzig);
                zahlenraumPoints=3;
                break;
            case 100: setActive(hundert);
                zahlenraumPoints=4;
                break;
            case 250: setActive(zweiFuenfzig);
                zahlenraumPoints=5;
                break;
            case 500: setActive(fuenfhundert);
                zahlenraumPoints=6;
                break;
            case 1000: setActive(tausend);
                zahlenraumPoints=7;
                break;
        }
    }

    /**
     * Set Button to active.
     *
     * @param b Button.
     */
    private void setActive(Button b){
        b.setBackgroundColor(ContextCompat.getColor(this,R.color.colorPrimary));
        b.setTextColor(Color.WHITE);
    }
    /**
     * Set Button to inactive.
     *
     * @param b Button.
     */
    private void setInActive(Button b){
        b.setBackgroundColor(ContextCompat.getColor(this,R.color.lightGrey));
        b.setTextColor(Color.DKGRAY);
    }
}
