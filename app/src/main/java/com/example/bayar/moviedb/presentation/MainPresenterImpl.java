package com.example.bayar.moviedb.presentation;

import com.example.bayar.moviedb.dagger2.App;
import com.example.bayar.moviedb.model.MovieModel;

import javax.inject.Inject;

public class MainPresenterImpl implements MainPresenter{

    @Inject
    MovieModel model;

    public MainPresenterImpl() {
        App.getComponent().inject(this);
    }
}
