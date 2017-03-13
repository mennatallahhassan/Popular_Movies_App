package stage_1.udacity.popular_movies_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by boody 2 on 19/11/2016.
 */

public class Database extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "movieDatabase";
    private static final String TABLE_Movie = "movieTable";
    private static final String KEY_ID = "Id";
    private static final String KEY_Tile = "Tile";
    private static final String KEY_Image = "Image";
    private static final String KEY_Year = "Year";
    private static final String KEY_Rate = "Rate";
    private static final String KEY_Description = "Description";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_movieTable = "Create TABLE  " + TABLE_Movie + "(" +
                KEY_ID + " INTEGER PRIMARY KEY," +
                KEY_Tile + " TEXT," +
                KEY_Image + " TEXT," +
                KEY_Year + " TEXT," +
                KEY_Rate + " DOUBLE," +
                KEY_Description + " TEXT" +
                ")";
        db.execSQL(CREATE_movieTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + TABLE_Movie);
        onCreate(db);
    }


    public void createMovie(Movie movie) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, movie.getId());
        values.put(KEY_Tile, movie.getTitle());
        values.put(KEY_Image, movie.getImage());
        values.put(KEY_Year, movie.getYear());
        values.put(KEY_Rate, movie.getRate());
        values.put(KEY_Description, movie.getDescription());
        db.insert(TABLE_Movie, null, values);
        db.close();
    }

    public Movie readMovie(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String read_SQL = "Select * from " + TABLE_Movie + " where " + KEY_ID + "=" + id;
        Cursor cursor = db.rawQuery(read_SQL, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            int id_Movie = Integer.parseInt(cursor.getString(0));
            String title = cursor.getString(1);
            String image = cursor.getString(2);
            String year = cursor.getString(3);
            Double rate = Double.parseDouble(cursor.getString(4));
            String description = cursor.getString(5);
            Movie movie = new Movie(id_Movie, title, image, year, rate, description);
            db.close();
            return movie;
        }
        return null;
    }

    public List<Movie> readListMovie() {
        List<Movie> movieList = new ArrayList<Movie>();
        String selectQuery = "SELECT  * FROM " + TABLE_Movie;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                int id_Movie = Integer.parseInt(cursor.getString(0));
                String title = cursor.getString(1);
                String image = cursor.getString(2);
                String year = cursor.getString(3);
                Double rate = Double.parseDouble(cursor.getString(4));
                String description = cursor.getString(5);
                Movie movie = new Movie(id_Movie, title, image, year, rate, description);
                movieList.add(movie);
            } while (cursor.moveToNext());
        }
        db.close();
        return movieList;
    }

    public void deleteMovie(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String delete_sql = "Delete from " + TABLE_Movie + " where " + KEY_ID + "=" + id;
        db.execSQL(delete_sql);
        db.close();
    }
}
