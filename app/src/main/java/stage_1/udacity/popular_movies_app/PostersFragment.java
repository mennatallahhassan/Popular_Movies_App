package stage_1.udacity.popular_movies_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.List;

import static stage_1.udacity.popular_movies_app.MainActivity.large;
import static stage_1.udacity.popular_movies_app.MainActivity.xlarge;


public class PostersFragment extends Fragment {
    ImageBaseAdapter baseAdapter;
    List<Movie> movieList;
    GridView gridView;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_posters, container, false);
        gridView = (GridView) view.findViewById(R.id.grid_view_movies);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!large && !xlarge) {
                    Intent intent = new Intent(getContext(), MovieDetailActivity.class);
                    Movie movieObject = movieList.get(position);
                    intent.putExtra("movieObject", movieObject);
                    startActivity(intent);
                } else {
                    MainActivity mainActivity1 = (MainActivity) getActivity();
                    mainActivity1.sendMovieToActivity(movieList.get(position));
                }
            }
        });

        Background background = new Background() {
            @Override
            protected void onPostExecute(String stringBuffer) {
                //super.onPostExecute(moviesList);
                JsonParsing jsonParsing = new JsonParsing();
                movieList = jsonParsing.StringParseJson(stringBuffer.toString());
                baseAdapter = new ImageBaseAdapter(getContext(), R.id.imageView_item, movieList);
                gridView.setAdapter(baseAdapter);
                MainActivity mainActivity1 = (MainActivity) getActivity();
                mainActivity1.sendMovieToActivity(movieList.get(0));

            }
        };
        background.execute("https://api.themoviedb.org/3/movie/popular?api_key=&language=en-US");
        return view;
    }

    public void updateGrid(int id) {
        Background background = new Background() {
            @Override
            protected void onPostExecute(String stringBuffer) {
                JsonParsing jsonParsing = new JsonParsing();
                movieList = jsonParsing.StringParseJson(stringBuffer.toString());
                baseAdapter.clear();
                baseAdapter.addAll(movieList);
                baseAdapter.notifyDataSetChanged();
            }
        };
        switch (id) {
            case R.id.topRated_item:

                background.execute("https://api.themoviedb.org/3/movie/top_rated?api_key=&language=en-US");
                break;
            case R.id.popular_item:
                background.execute("https://api.themoviedb.org/3/movie/popular?api_key=&language=en-US");
                break;
            case R.id.fav_item:
                Database database = new Database(getContext());
                movieList = database.readListMovie();
                baseAdapter.clear();
                baseAdapter.addAll(movieList);
                baseAdapter.notifyDataSetChanged();
                break;
        }

    }
}






















