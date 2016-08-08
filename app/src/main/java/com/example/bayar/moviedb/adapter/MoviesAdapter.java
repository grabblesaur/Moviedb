package com.example.bayar.moviedb.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bayar.moviedb.R;
import com.example.bayar.moviedb.activity.DetailActivity;
import com.example.bayar.moviedb.model.Movie;

import java.util.List;

import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>{

    private Context mContext;
    private List<Movie> mMovieList;

    public MoviesAdapter(Context context, List<Movie> movies) {
        mContext = context;
        mMovieList = movies;
    }

    @Override
    public MoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.list_item_movie, parent, false);
        return new MoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MoviesViewHolder holder, int position) {
        Movie currentMovie = mMovieList.get(position);
        holder.setUpData(currentMovie, position);
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    public class MoviesViewHolder extends RecyclerView.ViewHolder {

        private static final String MOVIE_POSITION = "position";

        @BindViews({R.id.item_title, R.id.item_subtitle, R.id.item_desc, R.id.item_rating})
        List<TextView> moviesTextViews;

        Movie mMovie;
        int mPosition;

        public MoviesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setUpData(Movie currentMovie, int position) {
            moviesTextViews.get(0).setText(currentMovie.getTitle());
            moviesTextViews.get(1).setText(currentMovie.getReleaseDate());
            moviesTextViews.get(2).setText(currentMovie.getOverview());
            moviesTextViews.get(3).setText(String.valueOf(currentMovie.getVoteAverage()));
            mMovie = currentMovie;
            mPosition = position;
        }

        @OnClick(R.id.movies_layout) void onItemClick(View view) {
            Snackbar.make(view, moviesTextViews.get(0).getText(), Snackbar.LENGTH_SHORT).show();

            Intent intent = new Intent(mContext, DetailActivity.class);

            intent.putExtra("title", mMovie.getTitle());
            intent.putExtra("year", mMovie.getReleaseDate());
            intent.putExtra("rating", mMovie.getVoteAverage());
            intent.putExtra("popularity", mMovie.getPopularity());
            intent.putExtra("poster_path", mMovie.getPosterPath());
            intent.putExtra("overview", mMovie.getOverview());

            mContext.startActivity(intent);
        }
    }
}
