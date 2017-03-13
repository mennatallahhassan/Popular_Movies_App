package stage_1.udacity.popular_movies_app;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by boody 2 on 21/11/2016.
 */

public class TrailerAdapter extends ArrayAdapter {
    private Context context;
    private List<Trailer> trailerList;

    public TrailerAdapter(Context context, int resource, List<Trailer> trailerList) {
        super(context, resource, trailerList);
        this.trailerList = trailerList;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = LayoutInflater.from(context).inflate(R.layout.trailer_item, parent, false);
        TextView textView = (TextView) row.findViewById(R.id.trailer_name);
        textView.setText(trailerList.get(position).getName().toString());
        return row;
    }
}
