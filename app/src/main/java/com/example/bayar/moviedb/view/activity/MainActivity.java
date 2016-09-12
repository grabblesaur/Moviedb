package com.example.bayar.moviedb.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.bayar.moviedb.R;
import com.example.bayar.moviedb.dagger2.App;
import com.example.bayar.moviedb.view.adapter.MoviesAdapter;
import com.example.bayar.moviedb.model.database.DatabaseManager;
import com.example.bayar.moviedb.model.Movie;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    public static final String API_KEY = "21f91637045fc30ac59759b75acc9ca0";

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private List<Movie> mMovieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        App.getComponent().inject(this);
        ButterKnife.bind(this);


        // need to init mMovieList
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new MoviesAdapter(this, mMovieList));
    }

    @Override
    protected void onStop() {
        Log.d(DatabaseManager.TAG, "onStop(): ");
        super.onStop();

//        mDatabaseManager.removeMoviesFromDatabase();
//        mDatabaseManager.insertMovies(mMovieList);
    }
}
