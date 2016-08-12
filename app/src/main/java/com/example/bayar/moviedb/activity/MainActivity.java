package com.example.bayar.moviedb.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.bayar.moviedb.R;
import com.example.bayar.moviedb.adapter.MoviesAdapter;
import com.example.bayar.moviedb.database.DatabaseHelper;
import com.example.bayar.moviedb.database.DatabaseManager;
import com.example.bayar.moviedb.model.Movie;
import com.example.bayar.moviedb.model.MovieResponse;
import com.example.bayar.moviedb.rest.ApiClient;
import com.example.bayar.moviedb.rest.ApiInterface;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static final String API_KEY = "21f91637045fc30ac59759b75acc9ca0";

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private List<Movie> mMovieList;
    private DatabaseManager mDatabaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        mDatabaseManager = new DatabaseManager(databaseHelper);

        if (mDatabaseManager.getRowCount() == 0) {
            fetchData();
        } else {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            mMovieList = mDatabaseManager.getMovies();
            mRecyclerView.setAdapter(new MoviesAdapter(this, mMovieList));
        }
    }

    private void fetchData() {
        Log.d(DatabaseManager.TAG, "fetchData(): ");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        // создание сервиса
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        // получение резултата GET-запроса
        Call<MovieResponse> call = apiService.getTopRatedMovies(API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                mMovieList = response.body().getResults();
                Log.d(DatabaseManager.TAG, "Number of movies received: " + mMovieList.size());
                mRecyclerView.setAdapter(new MoviesAdapter(MainActivity.this, mMovieList));
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e(DatabaseManager.TAG, t.toString());
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.d(DatabaseManager.TAG, "onStop(): ");
        mDatabaseManager.removeMoviesFromDatabase();
        mDatabaseManager.insertMovies(mMovieList);
    }
}
