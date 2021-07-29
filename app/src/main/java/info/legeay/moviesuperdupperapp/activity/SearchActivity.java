package info.legeay.moviesuperdupperapp.activity;

import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

import info.legeay.moviesuperdupperapp.R;
import info.legeay.moviesuperdupperapp.adapter.MainAdapter;
import info.legeay.moviesuperdupperapp.domain.Movie;

public class SearchActivity extends AppCompatActivity {

    private List<Movie> foundMovieList = new ArrayList<>();

    private RecyclerView recyclerView;
    private MainAdapter adapter;

    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search);

        Toolbar toolbar = findViewById(R.id.search_toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = findViewById(R.id.search_toolbar_layout);
        toolBarLayout.setTitle(getString(R.string.search));
    }
}