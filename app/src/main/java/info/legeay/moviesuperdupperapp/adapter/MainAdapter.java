package info.legeay.moviesuperdupperapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import info.legeay.moviesuperdupperapp.R;
import info.legeay.moviesuperdupperapp.domain.Movie;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder>{

    private Context context;
    private List<Movie> movieList;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageViewMovieCardPoster; // main_movie_card_poster
        public TextView textViewMovieCardTitle;// main_movie_card_title
        public TextView textViewMovieCardYear;// main_movie_card_year

        public ViewHolder(View view) {
            super(view);

            imageViewMovieCardPoster = view.findViewById(R.id.main_movie_card_poster);
            textViewMovieCardTitle = view.findViewById(R.id.main_movie_card_title);
            textViewMovieCardYear = view.findViewById(R.id.main_movie_card_year);
        }
    }

    public MainAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
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
    }

    @Override
    public int getItemCount() {
        return this.movieList == null ? 0 : this.movieList.size();
    }
}
