package stage_1.udacity.popular_movies_app;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by boody 2 on 02/11/2016.
 */

public class JsonParsing {

    public List<Movie> StringParseJson(String buffer) {
        List<Movie> movieList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(buffer);
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            JSONObject jsonObjectMovie;
            Movie movie;
            for (int i = 0; i < jsonArray.length(); i++) {
                movie = new Movie();
                jsonObjectMovie = jsonArray.getJSONObject(i);
                movie.setId(Integer.parseInt(jsonObjectMovie.getString("id")));
                movie.setYear(jsonObjectMovie.getString("release_date"));
                movie.setTitle(jsonObjectMovie.getString("original_title"));
                movie.setRate(jsonObjectMovie.getDouble("vote_average"));
                movie.setImage("https://image.tmdb.org/t/p/w500/" + jsonObjectMovie.getString("poster_path"));
                movie.setDescription(jsonObjectMovie.getString("overview"));
                movieList.add(movie);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return movieList;
    }

    public List<Trailer> TrailerParseJson(String buffer) {
        List<Trailer> trailerList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(buffer);
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            JSONObject jsonObjectMovie;
            Trailer trailer;
            for (int i = 0; i < jsonArray.length(); i++) {
                trailer = new Trailer();
                jsonObjectMovie = jsonArray.getJSONObject(i);
                trailer.setKey(jsonObjectMovie.getString("key"));
                trailer.setName(jsonObjectMovie.getString("name"));
                trailerList.add(trailer);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return trailerList;
    }

    public List<Review> ReviewParseJson(String buffer) {
        List<Review> reviewList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(buffer);
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            JSONObject jsonObjectMovie;
            Review review;
            for (int i = 0; i < jsonArray.length(); i++) {
                review = new Review();
                jsonObjectMovie = jsonArray.getJSONObject(i);
                review.setAuthor(jsonObjectMovie.getString("author"));
                review.setContent(jsonObjectMovie.getString("content"));
                reviewList.add(review);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return reviewList;
    }
}











