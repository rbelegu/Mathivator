package base;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.admin.mathivator.R;

import java.util.Iterator;
import java.util.List;

import business.Settings;
import data.DataSettings;

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
    private int operatorPoints;
    private int zahlenraumPoints;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initialize();
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

    private void initialize(){
        settings = DataSettings.getSettings(this);
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

    public void clickRechenoperation(View view){
        Button b = (Button) view;
        addRechenoperation(b.getId());
    }

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




    public void clickZahlenraum(View v){
        Button b = (Button)v;
        clearZahlenraum();
        fillZahlenraum(Integer.parseInt(((Button) v).getText().toString()));
    }

    public void clickSave(View v){
        settings.setName(name.getText().toString());
        settings.setMaximumPoints(operatorPoints*zahlenraumPoints);
        DataSettings.saveSettings(settings,this);
    }


    private void fillField(){
        fillRecheneinheiten(settings.getRechenOperatoren());
        fillZahlenraum(settings.getZahlenRaum());
        name.setText(settings.getName());
        highscore.setText(String.valueOf(settings.getHighScore()));

    }

    private void fillRecheneinheiten(List<Integer> recheneinheiten) {
        operatorPoints = 0;
        if (recheneinheiten.contains(1)) {
            operatorPoints+=1;
            setActive(plus);
        }
        if (recheneinheiten.contains(2)) {
            operatorPoints+=1;
            setActive(minus);
        }
        if (recheneinheiten.contains(3)) {
            operatorPoints+=2;
            setActive(mal);
        }
        if (recheneinheiten.contains(4)) {
            operatorPoints+=2;
            setActive(durch);
        }
    }

    private void clearZahlenraum(){
        setInActive(zehn);
        setInActive(zwanzig);
        setInActive(fuenfzig);
        setInActive(hundert);
        setInActive(zweiFuenfzig);
        setInActive(fuenfhundert);
        setInActive(tausend);
    }

    private void fillZahlenraum(int zahlenraum){
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

    private void setActive(Button b){
        b.setBackgroundColor(ContextCompat.getColor(this,R.color.colorPrimary));
        b.setTextColor(Color.WHITE);
    }
    private void setInActive(Button b){
        b.setBackgroundColor(ContextCompat.getColor(this,R.color.lightGrey));
        b.setTextColor(Color.DKGRAY);
    }
}
