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

public class ReviewAdapter extends ArrayAdapter {

    private List<Review> reviewList;
    private Context context;

    public ReviewAdapter(Context context, int resource, List<Review> reviewList) {
        super(context, resource, reviewList);
        this.reviewList = reviewList;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = LayoutInflater.from(context).inflate(R.layout.review_item, parent, false);
        TextView author_txtview = (TextView) row.findViewById(R.id.author);
        TextView content_txtview = (TextView) row.findViewById(R.id.content);
        author_txtview.setText("Author: " + reviewList.get(position).getAuthor().toString());
        content_txtview.setText("Content: " + reviewList.get(position).getContent().toString());
        return row;

    }
}
