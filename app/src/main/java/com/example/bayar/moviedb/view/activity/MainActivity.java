package com.example.bayar.moviedb.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.bayar.moviedb.R;
import com.example.bayar.moviedb.dagger2.App;
import com.example.bayar.moviedb.model.Movie;
import com.example.bayar.moviedb.presentation.MainPresenter;
import com.example.bayar.moviedb.view.adapter.MoviesAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity implements MainView{

    @Inject
    MainPresenter presenter;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private List<Movie> mMovieList;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        App.getComponent().inject(this);
        unbinder = ButterKnife.bind(this);
        presenter.setView(this);

        mMovieList = presenter.initMovieList();

        if (mMovieList != null) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mRecyclerView.setAdapter(new MoviesAdapter(this, mMovieList));
        } else {
            Toast.makeText(this, "mMovieList is null", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public Context getMainViewContext() {
        return this;
    }

    @Override
    public void showNoConnectionNoDatabaseMessage() {
        Toast.makeText(this, "Please, check your internet connection", Toast.LENGTH_LONG).show();
    }
}
