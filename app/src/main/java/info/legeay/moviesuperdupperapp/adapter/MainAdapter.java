package info.legeay.moviesuperdupperapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import info.legeay.moviesuperdupperapp.R;
import info.legeay.moviesuperdupperapp.activity.MainActivity;
import info.legeay.moviesuperdupperapp.activity.MovieActivity;
import info.legeay.moviesuperdupperapp.activity.SearchActivity;
import info.legeay.moviesuperdupperapp.domain.Movie;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder>{

    private final Context context;
    private final List<Movie> movieList;
    private final int orientation;
    private final int heightDP;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageViewMovieCardPoster; // main_movie_card_poster
        public TextView textViewMovieCardTitle;// main_movie_card_title
        public TextView textViewMovieCardYear;// main_movie_card_year
        public LinearLayout linearLayoutPosterContainer;

        public ViewHolder(View view) {
            super(view);
            Log.d("PIL", "ViewHolder: constructor");

            imageViewMovieCardPoster = view.findViewById(R.id.main_movie_card_poster);
            textViewMovieCardTitle = view.findViewById(R.id.main_movie_card_title);
            textViewMovieCardYear = view.findViewById(R.id.main_movie_card_year);
            linearLayoutPosterContainer = view.findViewById(R.id.movie_poster_container);

            view.setOnClickListener(v -> {

                Intent intent = new Intent(context, MovieActivity.class);
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation((Activity)context, imageViewMovieCardPoster, "poster");

                intent.putExtra("imdbID", view.getTag().toString());

                context.startActivity(intent, options.toBundle());
            });

            ((LinearLayout) view).setOrientation(MainAdapter.this.orientation);
            ViewGroup.LayoutParams params = view.getLayoutParams();
            params.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, MainAdapter.this.heightDP, context.getResources().getDisplayMetrics());
            view.setLayoutParams(params);

            if(LinearLayout.HORIZONTAL == MainAdapter.this.orientation) {

                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) linearLayoutPosterContainer.getLayoutParams();
                int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, context.getResources().getDisplayMetrics());
                layoutParams.setMargins(0, 0, margin, 0);
                linearLayoutPosterContainer.setLayoutParams(layoutParams);
            }


        }
    }

    public MainAdapter(Context context, List<Movie> movieList, int orientation, int heightDP) {
        this.context = context;
        this.movieList = movieList;
        this.orientation = orientation;
        this.heightDP = heightDP;
    }

    @NonNull
    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("PIL", "onCreateViewHolder ");
        View v = LayoutInflater.from(context).inflate(R.layout.item_movie_card, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.ViewHolder holder, int position) {
        Log.d("PIL", "onBindViewHolder  -- "+ position);

        Movie movie = this.movieList.get(position);

        holder.itemView.setTag(movie.getImdbID());

        Picasso.get()
                .load(movie.getImageUrl())
                .placeholder(R.drawable.ninja_patate)
                .into(holder.imageViewMovieCardPoster);

        holder.textViewMovieCardTitle.setText(movie.getTitle());
        holder.textViewMovieCardYear.setText(movie.getYearRelease());

        if(position == getItemCount() - 1 && context instanceof SearchActivity) {
            ((SearchActivity) context).getMoreMovie();
        }
    }

    @Override
    public int getItemCount() {
        return this.movieList == null ? 0 : this.movieList.size();
    }
}
