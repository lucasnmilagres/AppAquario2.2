package com.example.AppAquario2;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import java.util.ArrayList;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getSharedPreferences(getResources().getString(R.string.login_data_file), 0);
        String email = sharedPreferences.getString("email", null);

        Intent intent;
        if(email==null)
            intent = new Intent(this, LoginActivity.class);
        else
            intent = new Intent(this, Menu.class);

        startActivity(intent);
        finish();

    }
}