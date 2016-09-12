package com.example.bayar.moviedb.dagger2;

import android.content.Context;

import com.example.bayar.moviedb.model.MovieModel;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ModelModule {

    @Provides
    @Singleton
    public MovieModel provideMovieModel(Context context) {
        return new MovieModel(context);
    }

}
