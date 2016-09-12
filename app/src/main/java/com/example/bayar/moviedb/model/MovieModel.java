package com.example.bayar.moviedb.model;

import android.content.Context;
import android.util.Log;

import com.example.bayar.moviedb.model.database.DatabaseManager;
import com.example.bayar.moviedb.model.rest.ApiClient;
import com.example.bayar.moviedb.model.rest.ApiInterface;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<MovieResponse> call = apiService.getTopRatedMovies(API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                list = response.body().getResults();
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                list = Collections.emptyList();
            }
        });

        return list;
    }
}
