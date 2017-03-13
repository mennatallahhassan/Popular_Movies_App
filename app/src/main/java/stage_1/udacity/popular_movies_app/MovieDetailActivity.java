package stage_1.udacity.popular_movies_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class MovieDetailActivity extends AppCompatActivity {
    Movie movieObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail_layout);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        DetialsFragment detialsFragment = new DetialsFragment();
        fragmentTransaction.replace(android.R.id.content, detialsFragment);
        fragmentTransaction.commit();

        Intent intent = getIntent();
        movieObject = (Movie) intent.getSerializableExtra("movieObject");
        Bundle bundle = new Bundle();
        bundle.putSerializable("movieObject", movieObject);

        detialsFragment.setArguments(bundle);
    }
}





