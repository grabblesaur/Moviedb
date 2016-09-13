package com.example.bayar.moviedb.presentation;

import com.example.bayar.moviedb.model.Movie;
import com.example.bayar.moviedb.view.activity.LifecyclePresenter;

import java.util.List;

public interface MainPresenter extends LifecyclePresenter {
    List<Movie> getMoviesFromDatabase();
    List<Movie> getMoviesFormServer();
}
