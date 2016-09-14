package com.example.bayar.moviedb.view.activity;

import com.example.bayar.moviedb.model.Movie;

import java.util.List;

public interface MainView {
    void showNoConnectionNoDatabaseMessage();
    void setAdapter(List<Movie> list);
    void setLayoutManager();
}
