package com.example.bayar.moviedb.dagger2;

import com.example.bayar.moviedb.presentation.MainPresenter;
import com.example.bayar.moviedb.view.activity.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, ModelModule.class, PresenterModule.class})
public interface AppComponent {

    void inject(MainActivity target);
    void inject(MainPresenter target);

}
