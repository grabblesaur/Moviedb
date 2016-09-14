package com.example.bayar.moviedb.presentation;

import com.example.bayar.moviedb.model.Movie;
import com.example.bayar.moviedb.view.activity.MainView;

import java.util.List;

public interface MainPresenter {
    List<Movie> getMoviesFromDatabase();
    List<Movie> getMoviesFormServer();
    List<Movie> initMovieList();

    void setView(MainView view);
}
