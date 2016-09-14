package com.example.bayar.moviedb.model;

import android.content.Context;
import android.util.Log;

import com.example.bayar.moviedb.model.database.DatabaseManager;
import com.example.bayar.moviedb.model.rest.ApiClient;
import com.example.bayar.moviedb.model.rest.ApiInterface;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MovieModel {

    public static final String API_KEY = "21f91637045fc30ac59759b75acc9ca0";
    private static final String TAG = MovieModel.class.getSimpleName();

    private DatabaseManager databaseManager;
    private List<Movie> list;

    public MovieModel(Context context) {
        databaseManager = new DatabaseManager(context);
    }

    public List<Movie> getMoviesFromDatabase() {
        return databaseManager.getMovies();
    }

    public void insertMoviesToDatabase(List<Movie> movies) {
        databaseManager.insertMovies(movies);
    }

    public int getRowCount() {
        return databaseManager.getRowCount();
    }

    public void removeMoviesFromDatabase() {
        databaseManager.removeMoviesFromDatabase();
    }

    public List<Movie> getMoviesFromServer() {

        Log.d(TAG, "getMoviesFromServer: ");
//        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
//        Call<MovieResponse> call = apiService.getTopRatedMovies(API_KEY);
//        call.enqueue(new Callback<MovieResponse>() {
//            @Override
//            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
//                list = response.body().getResults();
//                Log.d(TAG, "onResponse: list = " + list);
//                Log.d(TAG, "onResponse: thread: " + Thread.currentThread().getName());
//            }
//
//            @Override
//            public void onFailure(Call<MovieResponse> call, Throwable t) {
//                list = Collections.emptyList();
//                Log.d(TAG, "onFailure: ");
//            }
//        });


        // TODO необходимо передавать Observable и выполнять его в презентере!
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Subscription apiSubscription =
                apiService.getTopRatedMoviesRx(API_KEY)
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
                        list.addAll(movieResponse.getResults());
                        Log.d(TAG, "onNext: list = " + list);
                    }
                });

        Log.d(TAG, "getMoviesFromServer: list = " + list);
        return list;
    }
}
