package com.example.bayar.moviedb.presentation;

import com.example.bayar.moviedb.dagger2.App;
import com.example.bayar.moviedb.model.Movie;
import com.example.bayar.moviedb.model.MovieModel;

import java.util.List;

import javax.inject.Inject;

public class MainPresenterImpl implements MainPresenter{

    @Inject
    MovieModel model;

    public MainPresenterImpl() {
        App.getComponent().inject(this);
    }

    @Override
    public List<Movie> getMoviesFromDatabase() {
        return null;
    }

    @Override
    public List<Movie> getMoviesFormServer() {
        return null;
    }

    @Override
    public void onPause(List<Movie> mMovieList) {
        model.removeMoviesFromDatabase();
        model.insertMoviesToDatabase(mMovieList);
    }

    @Override
    public void onResume() {

    }
}
