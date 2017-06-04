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
 * Class HighscoreAdapter
 * Adapter for the Highscore
 *
 * @author D. Tsichlakis
 * @version 1.0
 */

public class HighscoreAdapter extends ArrayAdapter<Highscore> {
    private Context context;
    private static class ViewHolder {
        private TextView itemView;
    }

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
            TextView rankView = (TextView) rowView.findViewById(R.id.highscoreRank);
            TextView dateView = (TextView) rowView.findViewById(R.id.highscoreDate);
            TextView nameView = (TextView) rowView.findViewById(R.id.highscoreName);
            TextView pointView = (TextView) rowView.findViewById(R.id.highscorePoints);
            TextView timeView = (TextView) rowView.findViewById(R.id.highscoreTime);
            TextView cityView = (TextView) rowView.findViewById(R.id.highscoreCity);
            rankView.setText(String.valueOf(position+1));
            dateView.setText(Tool.dateToString(item.getDate()));
            nameView.setText(item.getName());
            pointView.setText(String.valueOf(item.getHighscore()));
            timeView.setText(String.valueOf(item.getTime()));
            String city = String.valueOf(item.getCity());
            if (!city.equals("null")){
                cityView.setText(city);
            }
            else{
                cityView.setText("-");
            }
            return rowView;
        }
        return convertView;
    }
}