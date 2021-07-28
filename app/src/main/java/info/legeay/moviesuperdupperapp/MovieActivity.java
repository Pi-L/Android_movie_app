package info.legeay.moviesuperdupperapp;

import android.os.Build;
import android.os.Bundle;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

import info.legeay.moviesuperdupperapp.domain.Movie;
import info.legeay.moviesuperdupperapp.dto.MovieDTO;

// import info.legeay.moviesuperdupperapp.databinding.ActivityMovieBinding;

public class MovieActivity extends AppCompatActivity {

    //private ActivityMovieBinding binding;
    private Movie movie;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("PIL", "MovieActivity: onCreate()");

        // binding = ActivityMovieBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_movie);

        Bundle extras = getIntent().getExtras();

        this.movie = new Movie(extras.getString("filmTitle"),
                "25-12-2012",
                "Action, Adventure, Fantasy, Sci-Fi",
                "toto",
                "Super duper movie !! Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum",
                "toto, titi, tutu",
                "best movie",
                "nope");

        ObjectMapper mapper = new ObjectMapper();
        InputStream inputStream = null;
        MovieDTO movieDTO = null;
        try {
            inputStream = getAssets().open("movies.json");
            movieDTO = mapper.readValue(inputStream, MovieDTO.class);
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("PIL", e.getMessage());
        }

        if(movieDTO != null) this.movie = movieDTO.toMovie();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(this.movie.getTitle());

        ((TextView) findViewById(R.id.movie_title)).setText(this.movie.getTitle());

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar
                .make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());


        updateUi();
    }

    private void updateUi() {
        // toolbar
        CollapsingToolbarLayout toolBarLayout = findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(this.movie.getTitle());

        // scroll content

        ImageView imageViewthumb = findViewById(R.id.movie_poster_thumb);
        // imageViewthumb.setS

        TextView textViewTitle = (TextView) findViewById(R.id.movie_title);
        textViewTitle.setText(this.movie.getTitle());

        TextView textViewReleaseDate = (TextView) findViewById(R.id.movie_release_date);
        textViewReleaseDate.setText(this.movie.getReleaseDateString());

        TextView textViewTypes = (TextView) findViewById(R.id.movie_types);
        textViewTypes.setText(this.movie.getTypes());

        TextView textViewAbstract = (TextView) findViewById(R.id.movie_abstract_content);
        textViewAbstract.setText(this.movie.getDescription());

        TextView textViewDirectors = (TextView) findViewById(R.id.movie_directors_content);
        textViewDirectors.setText(this.movie.getDirector());

        TextView textViewActors = (TextView) findViewById(R.id.movie_actors_content);
        textViewActors.setText(this.movie.getActors());

        TextView textViewAwards = (TextView) findViewById(R.id.movie_awards_content);
        textViewAwards.setText(this.movie.getAwards());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d("PIL", "MovieActivity: onDestroy()");

    }
}