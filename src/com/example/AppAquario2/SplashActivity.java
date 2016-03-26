package com.example.AppAquario2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayList<String> channelNames = new ArrayList<>();
        channelNames.add("A");
        channelNames.add("B");
        channelNames.add("C");
        channelNames.add("D");
        //channelNames.add("E");
        ArrayList<Integer> colorsList = new ArrayList<>();
        colorsList.add(0xffff0000);
        colorsList.add(0xffffff00);
        colorsList.add(0xffff00ff);
        colorsList.add(0xff00ffff);
        //colorsList.add(0xff00ff00);

        Intent intent = new Intent(this, SetttingsLightActivity.class);
        intent.putExtra("channelsNamesList", channelNames);
        intent.putExtra("colorsList", colorsList);
        startActivity(intent);
        finish();
    }
}