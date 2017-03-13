package stage_1.udacity.popular_movies_app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import static stage_1.udacity.popular_movies_app.MainActivity.large;
import static stage_1.udacity.popular_movies_app.MainActivity.xlarge;


public class DetialsFragment extends Fragment {
    Movie movieObject;
    List<Trailer> trailerList;
    TrailerAdapter trailerAdapter;
    ListView trailer_listView;
    List<Review> reviewList;
    ListView review_ListView;
    View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_detials, container, false);
        if (!large && !xlarge) {
            movieObject = (Movie) getArguments().getSerializable("movieObject");
            postMovie(movieObject);
        }
        return view;
    }

    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    public void postMovie(Movie movieX) {
        this.movieObject = movieX;
        TextView titleTxTView = (TextView) view.findViewById(R.id.textview_title);
        titleTxTView.setText(movieObject.getTitle().toString());

        ImageView posterImageView = (ImageView) view.findViewById(R.id.imageView_poster);
        //posterImageView.setImageResource(movieObject.getImage());
        Picasso.with(getContext()).load(movieObject.getImage()).into(posterImageView);

        TextView yearTxTView = (TextView) view.findViewById(R.id.textview_year);
        yearTxTView.setText(movieObject.getYear() + "");

        TextView rateTxTView = (TextView) view.findViewById(R.id.textview_rate);
        rateTxTView.setText(movieObject.getRate() + "/10");

        TextView descriptionTxTView = (TextView) view.findViewById(R.id.textview_description);
        descriptionTxTView.setText(movieObject.getDescription().toString());


        Database database = new Database(getContext());
        Button favButton = (Button) view.findViewById(R.id.fav_button);
        if (database.readMovie(movieObject.getId()) == null) {
            favButton.setText(R.string.fav);
        } else {
            favButton.setText(R.string.unfav);
        }
        favButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Database database = new Database(getContext());
                Button favButton = (Button) view.findViewById(R.id.fav_button);
                if (database.readMovie(movieObject.getId()) == null) {
                    favButton.setText(R.string.unfav);
                    database.createMovie(movieObject);
                    Toast toast = Toast.makeText(getContext(), "Successfully", Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    favButton.setText(R.string.fav);
                    database.deleteMovie(movieObject.getId());
                    Toast toast = Toast.makeText(getContext(), "Deleted Successfully", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
        trailer_listView = (ListView) view.findViewById(R.id.trailer_list);

        Background background = new Background() {
            @Override
            protected void onPostExecute(String stringBuffer) {
                JsonParsing jsonParsing = new JsonParsing();

                trailerList = jsonParsing.TrailerParseJson(stringBuffer);
                trailerAdapter = new TrailerAdapter(getContext(), R.layout.trailer_item, trailerList);
                trailer_listView.setAdapter(trailerAdapter);
            }
        };
        background.execute("https://api.themoviedb.org/3/movie/" + movieObject.getId() + "/videos?api_key=&language=en-US");

        trailer_listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        setListViewHeightBasedOnChildren(trailer_listView);

        trailer_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String url = "https://www.youtube.com/watch?v=" + trailerList.get(position).getKey();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });


        review_ListView = (ListView) view.findViewById(R.id.review_list);
        Background background1 = new Background() {
            @Override
            protected void onPostExecute(String stringBuffer) {
                JsonParsing jsonParsing = new JsonParsing();
                reviewList = jsonParsing.ReviewParseJson(stringBuffer);
                ReviewAdapter reviewAdapter = new ReviewAdapter(getContext(), R.layout.review_item, reviewList);
                review_ListView.setAdapter(reviewAdapter);
            }
        };
        background1.execute("https://api.themoviedb.org/3/movie/" + movieObject.getId() + "/reviews?api_key=&language=en-US");


    }
}
