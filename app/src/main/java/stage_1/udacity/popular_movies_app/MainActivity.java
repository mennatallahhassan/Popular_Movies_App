package stage_1.udacity.popular_movies_app;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    public static Boolean xlarge;
    public static Boolean large;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        xlarge = ((getApplicationContext().getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
        large = ((getApplicationContext().getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);

        if (large || xlarge) {
            setContentView(R.layout.tablet_two_fragments);

        } else {
            setContentView(R.layout.empty_acitvity);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            PostersFragment postersFragment = new PostersFragment();
            fragmentTransaction.replace(android.R.id.content, postersFragment);
            fragmentTransaction.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        PostersFragment postersFragment = (PostersFragment) getSupportFragmentManager().findFragmentById(R.id.poster_fragment_tablet);
        postersFragment.updateGrid(item.getItemId());
        return true;
    }

    public void sendMovieToActivity(Movie movieObject) {
        DetialsFragment detialsFragment = (DetialsFragment) getSupportFragmentManager().findFragmentById(R.id.details_fragment_tablet);
        detialsFragment.postMovie(movieObject);
    }
}

















