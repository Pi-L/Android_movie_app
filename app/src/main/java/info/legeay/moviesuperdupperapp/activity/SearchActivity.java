package info.legeay.moviesuperdupperapp.activity;

import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import info.legeay.moviesuperdupperapp.R;
import info.legeay.moviesuperdupperapp.adapter.MainAdapter;
import info.legeay.moviesuperdupperapp.domain.Movie;
import info.legeay.moviesuperdupperapp.dto.MovieDTO;
import info.legeay.moviesuperdupperapp.dto.SearchDTO;

public class SearchActivity extends AppCompatActivity {

    private final List<Movie> foundMovieList = new ArrayList<>();

    private RecyclerView recyclerView;
    private MainAdapter adapter;

    private RequestQueue requestQueue;

    private Button searchButton;
    private EditText searchInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search);

        Toolbar toolbar = findViewById(R.id.search_toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = findViewById(R.id.search_toolbar_layout);
        toolBarLayout.setTitle(getString(R.string.search));

        this.init();

        requestQueue = Volley.newRequestQueue(this);

        this.recyclerView = findViewById(R.id.search_recycler_view);
        this.recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        SearchActivity.this.adapter = new MainAdapter(SearchActivity.this, SearchActivity.this.foundMovieList,
                                                                LinearLayout.VERTICAL, 300);
        SearchActivity.this.recyclerView.setAdapter(SearchActivity.this.adapter);

        this.searchButton.setOnClickListener(view -> {

            if(this.searchInput.getText() == null || this.searchInput.getText().toString().isEmpty()) {
                Log.d("PIL", "onCreate - setOnClickListener : searchInput.getText() null or empty" );

            } else {
                SearchActivity.this.setMovieList(this.searchInput.getText().toString());
            }

        });
    }

    private void setMovieList(String searchedString) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                String.format("https://www.omdbapi.com/?s=%s&apikey=bf4e1adb", searchedString),
                null,
                response -> {
                    Log.d("PIL", String.format("response ok : %s", response));
                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        SearchDTO searchDTO = mapper.readValue(response.toString(), SearchDTO.class);
                        MovieDTO[] movieDTOArray = searchDTO.getSearch();
                        SearchActivity.this.foundMovieList.clear();

                        if(movieDTOArray != null && movieDTOArray.length > 0) {

                            for (MovieDTO movieDTO : movieDTOArray) {
                                SearchActivity.this.foundMovieList.add(movieDTO.toMovie());
                            }
                        }
                        else Log.d("PIL", "movieDTOArray == null");

                    } catch (JsonProcessingException e) {
                        Log.d("PIL", e.getMessage());
                    } finally {
                        Log.d("PIL", SearchActivity.this.foundMovieList.toString());
                        SearchActivity.this.adapter.notifyDataSetChanged();
                    }
                }
                , error -> { Log.d("PIL", error.getMessage()); }
        );

        requestQueue.add(jsonObjectRequest);
    }

    private void init() {
        this.searchButton = findViewById(R.id.search_button);
        this.searchInput = findViewById(R.id.search_input);
    }

    public void onClickMovie(View view) {

        Intent intent = new Intent(this, MovieActivity.class);

        intent.putExtra("imdbID", view.getTag().toString());

        startActivity(intent);
    }
}