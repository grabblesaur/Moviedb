package com.example.bayar.moviedb.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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

public class MainActivity extends AppCompatActivity {

    @Inject
    MainPresenter presenter;

    public static final String API_KEY = "21f91637045fc30ac59759b75acc9ca0";

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


        // need to init mMovieList
        // если бд пуста, то фетчим с сервера
        // если бд есть, но есть инет, то фетчим с сервера
        // если бд есть, но нет интернета, то фетчим с бд


        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new MoviesAdapter(this, mMovieList));
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.onPause(mMovieList);
    }

    @Override
    protected void onResume() {
        super.onResume(); // always call a superclass method first!
        presenter.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
