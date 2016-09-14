package com.example.bayar.moviedb.dagger2;

import android.content.Context;

import com.example.bayar.moviedb.presentation.MainPresenter;
import com.example.bayar.moviedb.presentation.MainPresenterImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class PresenterModule {

    @Provides
    @Singleton
    public MainPresenter provideMainPresenter(Context context) {
        return new MainPresenterImpl(context);
    }

}
