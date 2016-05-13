package com.example.AppAquario2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lucas Milagres on 24-Mar-16.
 */
public class LightningActivity extends Activity {

    Intent sendIntent;
    ArrayList<String> channelsNamesList;
    ArrayList<String> lightModeList;
    ArrayList<String> lightProgramList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lightning);

        sendIntent=getIntent();
        channelsNamesList=sendIntent.getStringArrayListExtra("channelsNamesList");
        lightModeList=sendIntent.getStringArrayListExtra("lightModeList");
        lightProgramList=sendIntent.getStringArrayListExtra("lightProgramList");
    }

    public void sendLightCloud(View view)
    {
        Intent sendLightCloud=new Intent(this, LightCloudActivity.class);
        sendLightCloud.putExtra("channelsNamesList", channelsNamesList);
        sendLightCloud.putExtra("cloudEnabledArray", sendIntent.getBooleanArrayExtra("cloudEnabledArray"));
        startActivity(sendLightCloud);
    }

    public void sendLightThunder(View view)
    {
        Intent sendLightThunder=new Intent(this, LightThunderActivity.class);
        sendLightThunder.putExtra("channelsNamesList", channelsNamesList);
        sendLightThunder.putExtra("thunderEnabledArray", sendIntent.getBooleanArrayExtra("thunderEnabledArray"));
        startActivity(sendLightThunder);
    }

    public void sendLightLocation(View view)
    {
        Intent sendLightLocation=new Intent(this, LightLocationActivity.class);
        sendLightLocation.putExtra("channelsNamesList", channelsNamesList);
        sendLightLocation.putExtra("lightModeList",lightModeList);
        sendLightLocation.putExtra("lightProgramList",lightProgramList);
        sendLightLocation.putExtra("locationList", sendIntent.getBooleanArrayExtra("locationList"));
        startActivity(sendLightLocation);
    }

    public void sendLightMoon(View view)
    {
        Intent sendLightMoon=new Intent(this, LightMoonActivity.class);
        sendLightMoon.putExtra("channelsNamesList", channelsNamesList);
        sendLightMoon.putExtra("lightModeList",lightModeList);
        sendLightMoon.putExtra("lightProgramList",lightProgramList);
        sendLightMoon.putExtra("moonList", sendIntent.getBooleanArrayExtra("moonList"));
        startActivity(sendLightMoon);
    }

    public void sendLightRandom(View view)
    {
        Intent sendLightRandom=new Intent(this, LightRandomActivity.class);
        sendLightRandom.putExtra("channelsNamesList", channelsNamesList);
        sendLightRandom.putExtra("lightModeList",lightModeList);
        sendLightRandom.putExtra("lightProgramList",lightProgramList);
        sendLightRandom.putExtra("randomList", sendIntent.getBooleanArrayExtra("randomList"));
        startActivity(sendLightRandom);
    }

    public void sendLightRoutine(View view)
    {
        Intent sendLightRoutine=new Intent(this, LightRoutineActivity.class);
        sendLightRoutine.putExtra("channelsNamesList", channelsNamesList);
        sendLightRoutine.putExtra("lightModeList",lightModeList);
        sendLightRoutine.putExtra("lightProgramList",lightProgramList);
        sendLightRoutine.putExtra("routineList", sendIntent.getBooleanArrayExtra("routineList"));
        startActivity(sendLightRoutine);
    }

    public void sendLightSeason(View view)
    {
        Intent sendLightSeason=new Intent(this, LightSeasonActivity.class);
        sendLightSeason.putExtra("channelsNamesList", channelsNamesList);
        sendLightSeason.putExtra("lightModeList",lightModeList);
        sendLightSeason.putExtra("lightProgramList",lightProgramList);
        sendLightSeason.putExtra("seasonList", sendIntent.getBooleanArrayExtra("seasonList"));
        startActivity(sendLightSeason);
    }
}
