package info.legeay.moviesuperdupperapp.activity;

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

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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

    private RequestQueue requestQueue;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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
                String.format("http://www.omdbapi.com/?i=%s&apikey=bf4e1adb", imdbID),
                null,
                response -> {
                    Log.d("PIL", String.format("response ok : %s", response));
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

        toolBarLayout.setTitle(this.movie.getTitle());

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

    private void init() {
        this.movie = new Movie();

        requestQueue = Volley.newRequestQueue(this);

        setSupportActionBar(toolbar);

        toolbar = findViewById(R.id.toolbar);
        fab = findViewById(R.id.fab);
        toolBarLayout = findViewById(R.id.toolbar_layout);
        imageViewLoader = findViewById(R.id.loader);
        imageViewNoInternet = findViewById(R.id.no_internet);

        linearLayoutMovieContainer = findViewById(R.id.movie_container);
        imageViewthumb = findViewById(R.id.movie_poster_thumb);
        textViewTitle = (TextView) findViewById(R.id.movie_title);
        textViewReleaseDate = (TextView) findViewById(R.id.movie_release_date);
        textViewTypes = (TextView) findViewById(R.id.movie_types);
        textViewAbstract = (TextView) findViewById(R.id.movie_abstract_content);
        textViewDirectors = (TextView) findViewById(R.id.movie_directors_content);
        textViewActors = (TextView) findViewById(R.id.movie_actors_content);
        textViewAwards = (TextView) findViewById(R.id.movie_awards_content);
        textViewViewMore = (TextView) findViewById(R.id.movie_abstract_see_more);

        fab.setOnClickListener(view -> Snackbar
                .make(view, "Loved it", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());

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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d("PIL", "MovieActivity: onDestroy()");

    }
}