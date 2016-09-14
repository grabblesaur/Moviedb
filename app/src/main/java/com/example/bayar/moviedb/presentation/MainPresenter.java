package com.example.bayar.moviedb.presentation;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.example.bayar.moviedb.dagger2.App;
import com.example.bayar.moviedb.model.Movie;
import com.example.bayar.moviedb.model.MovieModel;
import com.example.bayar.moviedb.model.MovieResponse;
import com.example.bayar.moviedb.view.activity.MainView;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainPresenter {

    private static final String TAG = MainPresenter.class.getSimpleName();
    @Inject
    MovieModel model;

    private Subscription apiSubscription;
    private List<Movie> movieList;
    private Context appContext;
    private MainView mainView;

    public MainPresenter(Context context) {
        App.getComponent().inject(this);
        appContext = context;
    }

    public List<Movie> getMoviesFromDatabase() {
        return model.getMoviesFromDatabase();
    }

    public void getMoviesFormServer() {
        Log.d(TAG, "getMoviesFormServer: ");

        apiSubscription = model.getMovieListObservableFromServer()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MovieResponse>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e);
                    }

                    @Override
                    public void onNext(MovieResponse movieResponse) {
                        Log.d(TAG, "onNext: ");
                        movieList = movieResponse.getResults();
                        mainView.setAdapter(movieList);
                        mainView.setLayoutManager();
                        Log.d(TAG, "onNext: movieList = " + movieList);
                    }
                });
    }

    public void initMovieList() {
        // need to init mMovieList
        // если бд пуста, то фетчим с сервера
        // если бд есть, но есть инет, то фетчим с сервера
        // если бд есть, но нет интернета, то фетчим с бд
        // если бд пуста и нет интернета, то вывести соответствующее сообщение
        if (isConnected(appContext)) {
            Log.d(TAG, "initMovieList: case 1");
            getMoviesFormServer();
        } else {
            if (model.getRowCount() == 0) {
                Log.d(TAG, "initMovieList: case 2");
                movieList = getMoviesFromDatabase();
                mainView.setAdapter(movieList);
                mainView.setLayoutManager();
            } else {
                Log.d(TAG, "initMovieList: case 3");
                showNoConnectionNoDatabaseMessage();
            }
        }
    }

    private boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if (activeNetwork == null) {
            return false;
        }

        return activeNetwork.isConnectedOrConnecting();
    }

    public void onDestroy() {
        // Если apiSubscription не ссылает на значение null и подписана на что-либо, то необходимо
        // произвести отписку. (Зачем?)
        // Подписка удерживает в памяти ресурсы, с которыми связана.
        // Эти ресурсы не будут автоматически освобождены при выходе объекта Subscription из области видимости.
        // Если после вызова метода subscribe проигнорировать возвращаемое значение,
        // то существует риск потерять единственную возможность отписаться.
        // Подписка будет существовать далее, в то время как доступ к ней будет потерян,
        // что может привести к утечке памяти и нежелательным действиям.
        if (apiSubscription != null && apiSubscription.isUnsubscribed()) {
            apiSubscription.unsubscribe();
        }
    }

    public void setView(MainView view) {
        mainView = view;
    }

    private void showNoConnectionNoDatabaseMessage() {
        mainView.showNoConnectionNoDatabaseMessage();
    }

    public void onPause() {
        if (movieList != null) {
            model.removeMoviesFromDatabase();
            model.insertMoviesToDatabase(movieList);
        }
    }

}
