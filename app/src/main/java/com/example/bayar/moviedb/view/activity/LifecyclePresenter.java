package com.example.bayar.moviedb.view.activity;

import com.example.bayar.moviedb.model.Movie;

import java.util.List;

public interface LifecyclePresenter {
    void onPause(List<Movie> mMovieList);
    void onResume();
}
