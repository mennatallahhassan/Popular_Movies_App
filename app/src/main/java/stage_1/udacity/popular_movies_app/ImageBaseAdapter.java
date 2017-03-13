package stage_1.udacity.popular_movies_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by boody 2 on 08/10/2016.
 */

public class ImageBaseAdapter extends ArrayAdapter {
    private List<Movie> movieList;
    private Context context;

    public ImageBaseAdapter(Context context, int resource, List<Movie> movieList) {
        super(context, resource, movieList);
        this.movieList = movieList;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        if (row == null) {
            row = LayoutInflater.from(context).inflate(R.layout.movies_grid_item, parent, false);
            RecycleView recycleView = new RecycleView();
            recycleView.imageView = (ImageView) row.findViewById(R.id.imageView_item);
            recycleView.movieObject = movieList.get(position);
            row.setTag(recycleView);
        }
        final RecycleView recycleView = (RecycleView) row.getTag();
        recycleView.imageView = (ImageView) row.findViewById(R.id.imageView_item);
        recycleView.movieObject = movieList.get(position);
        Picasso.with(context).load(recycleView.movieObject.getImage()).into(recycleView.imageView);

        return row;
    }

    public static class RecycleView {
        ImageView imageView;
        Movie movieObject;
    }
}

