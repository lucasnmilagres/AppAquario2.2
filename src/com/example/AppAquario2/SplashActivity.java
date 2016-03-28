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
        channelNames.add("E");
        ArrayList<Integer> colorsList = new ArrayList<>();
        colorsList.add(0xffff0000);
        colorsList.add(0xffffff00);
        colorsList.add(0xffff00ff);
        colorsList.add(0xff00ffff);
        colorsList.add(0xff00ff00);
        ArrayList<Integer> lightLastValueList = new ArrayList<>();
        lightLastValueList.add(0);
        lightLastValueList.add(25);
        lightLastValueList.add(50);
        lightLastValueList.add(75);
        lightLastValueList.add(100);
        ArrayList<String> lightModeList = new ArrayList<>();
        lightModeList.add("FreeMode");
        lightModeList.add("B");
        lightModeList.add("FreeMode");
        lightModeList.add("D");
        lightModeList.add("FreeMode");

        Intent intent = new Intent(this, LightFreeActivity.class);
        intent.putExtra("channelsNamesList", channelNames);
        intent.putExtra("colorsList", colorsList);
        intent.putExtra("lightLastValueList", lightLastValueList);
        intent.putExtra("lightModeList", lightModeList);
        startActivity(intent);
        finish();
    }
}