package com.example.bayar.moviedb.dagger2;

import android.app.Application;

public class App extends Application {

    private static AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerAppComponent
                .builder()
                .build();
    }

    public static AppComponent getComponent() {
        return component;
    }
}
