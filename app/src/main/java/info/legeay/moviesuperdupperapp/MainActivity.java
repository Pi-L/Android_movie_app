package info.legeay.moviesuperdupperapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import info.legeay.moviesuperdupperapp.dto.MovieDTO;

public class MainActivity extends AppCompatActivity {

    private TextView textViewFirstname;
    private TextView textViewButtonSearchMovies;

    private List<MovieDTO> movieDTOList;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("PIL", "MainActivity: onCreate()");

        setContentView(R.layout.activity_main);

        this.textViewFirstname = (TextView) findViewById(R.id.text_view_firstname);
        this.textViewFirstname.setText("Kitty");
        this.textViewButtonSearchMovies  = (TextView) findViewById(R.id.button_main_view_search_movies);


        this.textViewButtonSearchMovies.setOnClickListener(v -> Log.d("pil", "pliiiiffff"));

        Toast.makeText(this, "Loading...", Toast.LENGTH_SHORT).show();
        showdialog();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d("PIL", "MainActivity: onDestroy()");

    }

    private void insertMovies(List<MovieDTO> movieDTOList) {
        LinearLayout movieListView = (LinearLayout) findViewById(R.id.main_movie_list);

//        for (MovieDTO movieDTO: movieDTOList) {
//
//            LinearLayout movieContainerLL = new LinearLayout(this);
//            movieContainerLL.setOrientation(LinearLayout.HORIZONTAL);
//
//            ImageView movieImageView = new ImageView(this);
//            movieImageView.setImageDrawable(movieDTO.getDrawable());
//
//            LinearLayout movieDescriptionContainerLL = new LinearLayout(this);
//            movieDescriptionContainerLL.setOrientation(LinearLayout.VERTICAL);
//
//            movieContainerLL.addView(movieImageView);
//            movieContainerLL.addView(movieDescriptionContainerLL);
//
//            TextView movieTitleView = new TextView(this);
//            movieTitleView.setText(movieDTO.getTitle());
//
//            TextView movieReleaseDateView = new TextView(this);
//            movieReleaseDateView.setText(movieDTO.getReleaseDateString());
//
//            movieDescriptionContainerLL.addView(movieTitleView);
//            movieDescriptionContainerLL.addView(movieReleaseDateView);
//
//
//            movieListView.addView(movieContainerLL);
//        }
    }

    public void onClickMovie(View view) {
        Toast.makeText(this, "Movie "+view.getTag()+"...", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MovieActivity.class);
        String movieKeyString = String.format("title_movie_%s", view.getTag().toString());
        int movieTitleIdRessource = getResources().getIdentifier(movieKeyString, "string", getPackageName());
        intent.putExtra("filmTitle", getResources().getString(movieTitleIdRessource));
        startActivity(intent);
    }

    protected void showdialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.input_firstname_label));

        // Set up the input
        EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setHint("Toto");
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", (dialog, which) -> {
                // Here you get get input text from the Edittext
            textViewFirstname.setText( input.getText() == null || input.getText().toString().isEmpty() ? "Kitty" : input.getText());
        });
        // builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })
        builder.show();
    }
}