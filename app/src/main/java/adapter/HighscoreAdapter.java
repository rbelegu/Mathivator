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
            TextView dateView = (TextView) rowView.findViewById(R.id.highscoreDate);
            TextView nameView = (TextView) rowView.findViewById(R.id.highscoreName);
            TextView pointView = (TextView) rowView.findViewById(R.id.highscorePoints);
            TextView timeView = (TextView) rowView.findViewById(R.id.highscoreTime);
            TextView latView = (TextView) rowView.findViewById(R.id.highscoreLat);
            TextView lonView = (TextView) rowView.findViewById(R.id.highscoreLon);
            dateView.setText(Tool.dateToString(item.getDate()));
            nameView.setText(item.getName());
            String punkte = parent.getResources().getString(R.string.punkte);
            String zeit = parent.getResources().getString(R.string.zeit);
            String lat = parent.getResources().getString(R.string.lat);
            String lon = parent.getResources().getString(R.string.lon);
            pointView.setText(punkte + String.valueOf(item.getHighscore()));
            timeView.setText(zeit + String.valueOf(item.getTime()));
            latView.setText(lat + String.valueOf(item.getLat()));
            lonView.setText(lon + String.valueOf(item.getLon()));
            return rowView;
        }
        return convertView;
    }
}