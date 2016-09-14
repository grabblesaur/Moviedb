package com.example.bayar.moviedb.presentation;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.example.bayar.moviedb.dagger2.App;
import com.example.bayar.moviedb.model.Movie;
import com.example.bayar.moviedb.model.MovieModel;
import com.example.bayar.moviedb.view.activity.MainView;

import java.util.List;

import javax.inject.Inject;

public class MainPresenterImpl implements MainPresenter{

    private static final String TAG = MainPresenterImpl.class.getSimpleName();
    @Inject
    MovieModel model;

    private Context appContext;
    private MainView mainView;

    public MainPresenterImpl(Context context) {
        App.getComponent().inject(this);
        appContext = context;
    }

    @Override
    public List<Movie> getMoviesFromDatabase() {
        return model.getMoviesFromDatabase();
    }

    @Override
    public List<Movie> getMoviesFormServer() {
        Log.d(TAG, "getMoviesFormServer: ");
        return model.getMoviesFromServer();
    }

    @Override
    public List<Movie> initMovieList() {
        // need to init mMovieList
        // если бд пуста, то фетчим с сервера
        // если бд есть, но есть инет, то фетчим с сервера
        // если бд есть, но нет интернета, то фетчим с бд
        // если бд пуста и нет интернета, то вывести соответствующее сообщение
        List<Movie> list;

        // need to figure out about checking connectivity
        ConnectivityManager cm = (ConnectivityManager)appContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork.isConnectedOrConnecting();

        if (isConnected) {
            Log.d(TAG, "initMovieList: isConnected is true");
            list = getMoviesFormServer();
        } else {
            Log.d(TAG, "initMovieList: isConnected is false");
            if (model.getRowCount() == 0) {
                showNoConnectionNoDatabaseMessage();
                list = null;
            } else {
                list = getMoviesFromDatabase();
            }
        }

        return list;
    }

    @Override
    public void setView(MainView view) {
        mainView = view;
    }

    private void showNoConnectionNoDatabaseMessage() {
        mainView.showNoConnectionNoDatabaseMessage();
    }
}
