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
        lightModeList.add("RandomMode");
        lightModeList.add("FreeMode");
        lightModeList.add("SeasonMode");
        lightModeList.add("LocationMode");
        lightModeList.add("RandomMode");
        ArrayList<String> moonList = new ArrayList<>();
        moonList.add(getResources().getString(R.string.Unset));
        moonList.add("Rising Moon");
        moonList.add("Full Moon");
        moonList.add("Midnight");
        moonList.add("No Moon");
        moonList.add("Lake Moon");
        ArrayList<String> locationList = new ArrayList<>();
        locationList.add(getResources().getString(R.string.Unset));
        locationList.add("Japão");
        locationList.add("Miami");
        locationList.add("Jakarta");
        locationList.add("Recife");
        locationList.add("Seu Cu");
        ArrayList<String> seasonList = new ArrayList<>();
        seasonList.add(getResources().getString(R.string.Unset));
        seasonList.add("Verão");
        seasonList.add("Primavera");
        seasonList.add("Outono");
        seasonList.add("Inverno");
        seasonList.add("Deserto");
        ArrayList<String> routineList = new ArrayList<>();
        routineList.add(getResources().getString(R.string.Unset));
        routineList.add("Techno1");
        routineList.add("Psycodelic");
        routineList.add("Chill");
        routineList.add("Hot");
        routineList.add("Dirty Venus");
        ArrayList<String> randomList = new ArrayList<>();
        randomList.add(getResources().getString(R.string.Unset));
        randomList.add("Standard Random");
        randomList.add("Long Duration");
        randomList.add("Short Duration");
        randomList.add("Blinking");
        ArrayList<String> lightProgramList = new ArrayList<>();
        lightProgramList.add("Deserto");
        lightProgramList.add("Recife");
        lightProgramList.add("kajs");
        lightProgramList.add("Blinking");
        lightProgramList.add("Long Duration");
        boolean[] cloudEnabledArray = {true, false, true, false, true};
        boolean[] thunderEnabledArray = {true, false, true, false, true};
        ArrayList<RegisteredDeviceItem> registeredDevicesList=new ArrayList<>();
        registeredDevicesList.add(new RegisteredDeviceItem("TM001","Sensor Temp. Marinho","Temperature Devices",null,null));
        registeredDevicesList.add(new RegisteredDeviceItem("TM002","Sensor Temp. Plantado1","Temperature Devices",null,null));
        registeredDevicesList.add(new RegisteredDeviceItem("TM003","Sensor Temp. Plantado2","Temperature Devices",null,null));
        registeredDevicesList.add(new RegisteredDeviceItem("LT001","Luz 1","Light Devices","WifiA","123456"));
        registeredDevicesList.add(new RegisteredDeviceItem("LT002","Luz 2","Light Devices","WifiB","654321"));

        Intent intent = new Intent(this, SettingActivity.class);
        intent.putExtra("channelsNamesList", channelNames);
        intent.putExtra("colorsList", colorsList);
        intent.putExtra("lightLastValueList", lightLastValueList);
        intent.putExtra("lightModeList", lightModeList);
        intent.putExtra("moonList", moonList);
        intent.putExtra("locationList", locationList);
        intent.putExtra("seasonList", seasonList);
        intent.putExtra("routineList", routineList);
        intent.putExtra("randomList", randomList);
        intent.putExtra("lightProgramList", lightProgramList);
        intent.putExtra("cloudEnabledArray", cloudEnabledArray);
        intent.putExtra("thunderEnabledArray", thunderEnabledArray);
        intent.putExtra("registeredDevicesList",registeredDevicesList);
        startActivity(intent);
        finish();
    }
}