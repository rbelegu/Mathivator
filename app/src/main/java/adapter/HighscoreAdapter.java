package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;
import business.Highscore;
import util.Tool;

import com.example.admin.mathivator.R;
/**
 Javadoc missing
 */

public class HighscoreAdapter extends ArrayAdapter<Highscore> {
    private Context context;

    public HighscoreAdapter(Context context, int textViewResourceId, List<Highscore> items) {
        super(context, textViewResourceId, items);
        this.context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.custom_highscore_item, parent, false);
            Highscore item = getItem(position);
            TextView dateView = (TextView) rowView.findViewById(R.id.highscoreDate);
            TextView nameView = (TextView) rowView.findViewById(R.id.highscoreName);
            TextView pointView = (TextView) rowView.findViewById(R.id.highscorePoints);
            TextView timeView = (TextView) rowView.findViewById(R.id.highscoreTime);
            TextView cityView = (TextView) rowView.findViewById(R.id.highscoreCity);
            if(item != null && item.getDate() != null) {
                dateView.setText(Tool.dateToString(item.getDate()));

                nameView.setText(item.getName());
                String punkte = parent.getResources().getString(R.string.punkte);
                String zeit = parent.getResources().getString(R.string.zeit);
                String ort = parent.getResources().getString(R.string.city);
                pointView.setText(punkte + String.valueOf(item.getHighscore()));
                timeView.setText(zeit + String.valueOf(item.getTime()));
                cityView.setText(ort + String.valueOf(item.getCity()));
            }

            return rowView;
        }
        return convertView;
    }
}