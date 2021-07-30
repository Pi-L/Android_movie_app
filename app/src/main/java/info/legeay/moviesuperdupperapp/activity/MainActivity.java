package info.legeay.moviesuperdupperapp.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

import info.legeay.moviesuperdupperapp.R;
import info.legeay.moviesuperdupperapp.adapter.MainAdapter;
import info.legeay.moviesuperdupperapp.domain.Movie;
import info.legeay.moviesuperdupperapp.dto.MovieDTO;
import info.legeay.moviesuperdupperapp.dto.SearchDTO;

public class MainActivity extends AppCompatActivity {

    private TextView textViewFirstname;
    private TextView textViewButtonSearchMovies;

    private final List<Movie> popularMovieList = new ArrayList<>();

    private RecyclerView recyclerView;
    private MainAdapter adapter;

    private RequestQueue requestQueue;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("PIL", "MainActivity: onCreate()");

        setContentView(R.layout.activity_main);

        requestQueue = Volley.newRequestQueue(this);

        this.recyclerView = findViewById(R.id.main_recycler_view);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        setMovieList();

        this.textViewFirstname = (TextView) findViewById(R.id.text_view_firstname);
        this.textViewFirstname.setText("Pil");
        this.textViewButtonSearchMovies  = (TextView) findViewById(R.id.button_main_view_search_movies);


        this.textViewButtonSearchMovies.setOnClickListener(v -> {
            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
        });

        Toast.makeText(this, "Loading...", Toast.LENGTH_SHORT).show();
        showdialog();
    }

    private void setMovieList() {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                "https://www.omdbapi.com/?s=star+wars&apikey=bf4e1adb",
                null,
                response -> {
                    Log.d("PIL", String.format("response ok : %s", response));
                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        SearchDTO searchDTO = mapper.readValue(response.toString(), SearchDTO.class);
                        MovieDTO[] movieDTOArray = searchDTO.getSearch();

                        if(movieDTOArray != null && movieDTOArray.length > 0) {
                            for (MovieDTO movieDTO : movieDTOArray) {
                                MainActivity.this.popularMovieList.add(movieDTO.toMovie());
                            }

                            MainActivity.this.adapter = new MainAdapter(MainActivity.this, MainActivity.this.popularMovieList, LinearLayout.HORIZONTAL, 130);

                            MainActivity.this.recyclerView.setAdapter(MainActivity.this.adapter);
                            // MainActivity.this.adapter.notifyDataSetChanged();
                        }
                        else Log.d("PIL", "movieDTOArray == null");
                    } catch (JsonProcessingException e) {
                        Log.d("PIL", e.getMessage());
                    } finally {
                        Log.d("PIL", MainActivity.this.popularMovieList.toString());
                    }
                }
                , error -> { Log.d("PIL", error.getMessage()); }
        );

        requestQueue.add(jsonObjectRequest);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d("PIL", "MainActivity: onDestroy()");

    }

    public void onClickMovie(View view) {

        Intent intent = new Intent(this, MovieActivity.class);

        intent.putExtra("imdbID", view.getTag().toString());

        startActivity(intent);
    }

    protected void showdialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.input_firstname_label));

        // Set up the input
        EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setHint("Your name");
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", (dialog, which) -> {
                // Here you get get input text from the Edittext
            textViewFirstname.setText( input.getText() == null || input.getText().toString().isEmpty() ? "Pil" : input.getText());
        });
        // builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })
        builder.show();
    }
}