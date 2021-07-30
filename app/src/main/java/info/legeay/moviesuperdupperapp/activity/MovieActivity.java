package info.legeay.moviesuperdupperapp.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Map;

import info.legeay.moviesuperdupperapp.R;
import info.legeay.moviesuperdupperapp.domain.Movie;
import info.legeay.moviesuperdupperapp.dto.MovieDTO;
import info.legeay.moviesuperdupperapp.util.Network;

// import info.legeay.moviesuperdupperapp.databinding.ActivityMovieBinding;

public class MovieActivity extends AppCompatActivity {

    //private ActivityMovieBinding binding;
    private Movie movie;
    private String imdbID;

    private Toolbar toolbar;
    private TextView textViewToolbarTitle;
    private CollapsingToolbarLayout toolBarLayout;
    private FloatingActionButton fab;
    private ImageView imageViewLoader;
    private ImageView imageViewNoInternet;

    private LinearLayout linearLayoutMovieContainer;
    private ImageView imageViewthumb;
    private TextView textViewTitle;
    private TextView textViewReleaseDate;
    private TextView textViewTypes;
    private TextView textViewAbstract;
    private TextView textViewDirectors;
    private TextView textViewActors;
    private TextView textViewAwards;
    private TextView textViewViewMore;

    private boolean isReadMoreClicked;
    private boolean isFaved;
    private SharedPreferences preferences;

    private RequestQueue requestQueue;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("PIL", "MovieActivity: onCreate()");

        setContentView(R.layout.activity_movie);

        Bundle extras = getIntent().getExtras();
        imdbID = extras.getString("imdbID");
        init();

        Glide.with(this).load(R.drawable.cat_loader)
                .into(imageViewLoader);
        Glide.with(this).load(R.drawable.internet_down)
                .into(imageViewNoInternet);

        if(Network.isInternetAvailable(this)) {
            imageViewLoader.setVisibility(View.VISIBLE);
            imageViewNoInternet.setVisibility(View.GONE);
            setMovie();

        } else {
            imageViewLoader.setVisibility(View.GONE);
            imageViewNoInternet.setVisibility(View.VISIBLE);

            Snackbar.make(linearLayoutMovieContainer, R.string.no_internet, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

    }


    private void setMovie() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                String.format("https://www.omdbapi.com/?i=%s&plot=full&apikey=bf4e1adb", imdbID),
                null,
                response -> {
                    ObjectMapper mapper = new ObjectMapper();

                    try {
                        MovieDTO movieDTO = mapper.readValue(response.toString(), MovieDTO.class);

                        if(movieDTO != null) {
                            MovieActivity.this.movie = movieDTO.toMovie();
                            updateUi();
                        }
                        else Log.d("PIL", "movieDTO == null");

                    } catch (JsonProcessingException e) {
                        Log.d("PIL", e.getMessage());
                    } finally {
                       imageViewLoader.setVisibility(View.GONE);
                    }
                }
                , error -> {
                    imageViewLoader.setVisibility(View.GONE);
                    Log.d("PIL", error.getMessage());
                }
        );

        requestQueue.add(jsonObjectRequest);
    }

    private void updateUi() {

        textViewToolbarTitle.setText(this.movie.getTitle());

        Picasso.get()
                .load(this.movie.getImageUrl())
                .placeholder(R.drawable.ninja_patate)
                .into(imageViewthumb);

        textViewTitle.setText(this.movie.getTitle());

        textViewReleaseDate.setText(this.movie.getReleaseDateString());

        textViewTypes.setText(this.movie.getTypes());

        textViewAbstract.setText(this.movie.getDescription());

        textViewDirectors.setText(this.movie.getDirector());

        textViewActors.setText(this.movie.getActors());

        textViewAwards.setText(this.movie.getAwards());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void init() {
        this.movie = new Movie();

        preferences = getSharedPreferences("loved_movies",
                Context.MODE_PRIVATE);
        isFaved = preferences.contains(imdbID);

        Log.d("PIL", "movie activi init size: " + preferences.getAll().size());

        requestQueue = Volley.newRequestQueue(this);

        toolbar = findViewById(R.id.toolbar);
        textViewToolbarTitle = findViewById(R.id.toolbar_title);
        fab = findViewById(R.id.fab);
        toolBarLayout = findViewById(R.id.toolbar_layout);
        imageViewLoader = findViewById(R.id.loader);
        imageViewNoInternet = findViewById(R.id.no_internet);

        linearLayoutMovieContainer = findViewById(R.id.movie_container);
        imageViewthumb = findViewById(R.id.movie_poster_thumb);
        textViewTitle = findViewById(R.id.movie_title);
        textViewReleaseDate = findViewById(R.id.movie_release_date);
        textViewTypes = findViewById(R.id.movie_types);
        textViewAbstract = findViewById(R.id.movie_abstract_content);
        textViewDirectors = findViewById(R.id.movie_directors_content);
        textViewActors = findViewById(R.id.movie_actors_content);
        textViewAwards = findViewById(R.id.movie_awards_content);
        textViewViewMore = findViewById(R.id.movie_abstract_see_more);

        if(isFaved) fab.setImageResource(R.drawable.ic_baseline_favorite_24);
        else fab.setImageResource(R.drawable.ic_heart_icon);

        fab.setOnClickListener(view -> {

            SharedPreferences.Editor edit = MovieActivity.this.preferences.edit();

            if(MovieActivity.this.isFaved) {
                edit.remove(MovieActivity.this.imdbID);
                fab.setImageResource(R.drawable.ic_heart_icon);
            }
            else {
                edit.putString(MovieActivity.this.imdbID, MovieActivity.this.imdbID);
                fab.setImageResource(R.drawable.ic_baseline_favorite_24);
            }

            MovieActivity.this.isFaved = !MovieActivity.this.isFaved;

            edit.commit();

            Snackbar.make(view, MovieActivity.this.isFaved ? "Loved it" : "Sadly rejected", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        });

        textViewViewMore.setOnClickListener(v -> {
            if(isReadMoreClicked) {
                textViewAbstract.setMaxLines(3);
                textViewViewMore.setText(R.string.read_more_title);
            } else {
                textViewAbstract.setMaxLines(Integer.MAX_VALUE);
                textViewViewMore.setText(R.string.read_less_title);
            }
            isReadMoreClicked = !isReadMoreClicked;
        });

        setSupportActionBar(toolbar);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d("PIL", "MovieActivity: onDestroy()");

    }
}