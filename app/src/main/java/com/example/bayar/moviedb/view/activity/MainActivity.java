package com.example.bayar.moviedb.view.activity;

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

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        App.getComponent().inject(this);
        presenter.setView(this);
        unbinder = ButterKnife.bind(this);
        presenter.initMovieList();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        presenter.onDestroy();
    }

    @Override
    public void showNoConnectionNoDatabaseMessage() {
        Toast.makeText(this, "Please, check your internet connection", Toast.LENGTH_LONG).show();
    }

    @Override
    public void setAdapter(List<Movie> list) {
        mRecyclerView.setAdapter(new MoviesAdapter(this, list));
    }

    @Override
    public void setLayoutManager() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
