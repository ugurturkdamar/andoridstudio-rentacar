package com.turkishvein.tekturk;

import android.app.Application;

import com.parse.Parse;

public class ParseStarterActivity extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG);

        Parse.initialize(new Parse.Configuration.Builder(this)
        .applicationId("6l6yxAQRZPBWud47mkKt6MBbm3q0Ly8xomIOAzvr")
        .clientKey("98nsXmTf73OT0JxXouDJEFliOHiJAyhuVi1Ich0G")
        .server("https://parseapi.back4app.com/")
        .build()
        );
    }
}
