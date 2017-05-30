package base;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.admin.mathivator.R;

import java.util.List;

import adapter.HighscoreAdapter;
import business.Highscore;
import data.DataHighscore;

public class HighscoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);
        initPlanDayList(DataHighscore.getHighscore(this));

    }

    private void initPlanDayList(List<Highscore> highscoreList){
        HighscoreAdapter adapter = new HighscoreAdapter(this, R.layout.custom_highscore_item, highscoreList);
        ListView listView = (ListView)findViewById(R.id.highscoreList);
        if(listView != null) {
            listView.setAdapter(adapter);
            listView.setTag(highscoreList);
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
