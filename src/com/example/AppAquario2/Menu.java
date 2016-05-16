package com.example.AppAquario2;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Menu extends Activity {

    //Objects
    ArrayList<String> channelNames;
    ArrayList<Integer> colorsList;
    ArrayList<Integer> lightLastValueList;
    ArrayList<String> lightModeList;
    ArrayList<String> moonList;
    ArrayList<String> locationList;
    ArrayList<String> seasonList;
    ArrayList<String> routineList;
    ArrayList<String> randomList;
    ArrayList<String> lightProgramList;
    boolean[] cloudEnabledArray={true, false, true, false, true};
    boolean[] thunderEnabledArray={true, false, true, false, true};
    ArrayList<RegisteredDeviceItem> registeredDevicesList;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        InitializeObjects();
    }

    private void InitializeObjects()
    {
        channelNames = new ArrayList<>();
        channelNames.add("A");
        channelNames.add("B");
        channelNames.add("C");
        channelNames.add("D");
        channelNames.add("E");

        colorsList = new ArrayList<>();
        colorsList.add(0xffff0000);
        colorsList.add(0xffffff00);
        colorsList.add(0xffff00ff);
        colorsList.add(0xff00ffff);
        colorsList.add(0xff00ff00);

        lightLastValueList = new ArrayList<>();
        lightLastValueList.add(0);
        lightLastValueList.add(25);
        lightLastValueList.add(50);
        lightLastValueList.add(75);
        lightLastValueList.add(100);

        lightModeList = new ArrayList<>();
        lightModeList.add("RandomMode");
        lightModeList.add("FreeMode");
        lightModeList.add("SeasonMode");
        lightModeList.add("LocationMode");
        lightModeList.add("RandomMode");

        moonList = new ArrayList<>();
        moonList.add(getResources().getString(R.string.Unset));
        moonList.add("Rising Moon");
        moonList.add("Full Moon");
        moonList.add("Midnight");
        moonList.add("No Moon");
        moonList.add("Lake Moon");

        locationList = new ArrayList<>();
        locationList.add(getResources().getString(R.string.Unset));
        locationList.add("Japão");
        locationList.add("Miami");
        locationList.add("Jakarta");
        locationList.add("Recife");
        locationList.add("Seu Cu");

        seasonList = new ArrayList<>();
        seasonList.add(getResources().getString(R.string.Unset));
        seasonList.add("Verão");
        seasonList.add("Primavera");
        seasonList.add("Outono");
        seasonList.add("Inverno");
        seasonList.add("Deserto");

        routineList = new ArrayList<>();
        routineList.add(getResources().getString(R.string.Unset));
        routineList.add("Techno1");
        routineList.add("Psycodelic");
        routineList.add("Chill");
        routineList.add("Hot");
        routineList.add("Dirty Venus");

        randomList = new ArrayList<>();
        randomList.add(getResources().getString(R.string.Unset));
        randomList.add("Standard Random");
        randomList.add("Long Duration");
        randomList.add("Short Duration");
        randomList.add("Blinking");

        lightProgramList = new ArrayList<>();
        lightProgramList.add("Deserto");
        lightProgramList.add("Recife");
        lightProgramList.add("kajs");
        lightProgramList.add("Blinking");
        lightProgramList.add("Long Duration");

        registeredDevicesList=new ArrayList<>();
        registeredDevicesList.add(new RegisteredDeviceItem("TM001","Sensor Temp. Marinho","Temperature Devices",null,null));
        registeredDevicesList.add(new RegisteredDeviceItem("TM002","Sensor Temp. Plantado1","Temperature Devices",null,null));
        registeredDevicesList.add(new RegisteredDeviceItem("TM003","Sensor Temp. Plantado2","Temperature Devices",null,null));
        registeredDevicesList.add(new RegisteredDeviceItem("LT001","Luz 1","Light Devices","WifiA","123456"));
        registeredDevicesList.add(new RegisteredDeviceItem("LT002","Luz 2","Light Devices","WifiB","654321"));

        SharedPreferences sharedPreferences = getSharedPreferences(getResources().getString(R.string.login_data_file), 0);
        GlobalObjects.aquariumItem=new AquariumItem(sharedPreferences.getString("aquariumCode", "null"),null,new ArrayList<>());
        askAquariumData();
    }

    private void askAquariumData()
    {
        //Creates connection
        if(!GlobalObjects.aquariumItem.getCode().equals("null")) {
            String[] connectionData = new String[2];
            connectionData[0] = getResources().getString(R.string.server_get_aquarium_data);
            connectionData[1] = "?DeviceCode=" + GlobalObjects.aquariumItem.getCode();
            new GetAquariumData(this).execute(connectionData);
        }
        else
        {
            GlobalObjects.aquariumItem.setCode(null);
        }
    }

    public void askDevicesData(String deviceCode)
    {
        try {
            //Creates connection
            if (deviceCode != null) {
                String[] connectionData = new String[2];
                connectionData[0] = getResources().getString(R.string.server_get_device_data);
                connectionData[1] = "?DeviceCode=" + deviceCode;
                new GetDeviceData(this).execute(connectionData);
            }
        }
        catch (Exception e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("TAG",e.getMessage());
        }
    }

    public void insertDeviceData(Map<String,String> result)
    {
        try
        {
            if(!result.get("DeviceCode").contains("AQ")) {
                RegisteredDeviceItem registeredDeviceItem=new RegisteredDeviceItem(null,null,null,null,null);
                registeredDeviceItem.setCode(result.get("DeviceCode"));
                registeredDeviceItem.setName(result.get("DeviceName"));
                registeredDeviceItem.setSsid(result.get("WifiSSID"));
                registeredDeviceItem.setPassword(result.get("WifiPassword"));

                String deviceCodePrefix=result.get("DeviceCode").substring(0,2);
                ArrayList<String> codePrefixList=new ArrayList<>();
                for (String prefix:getResources().getStringArray(R.array.registered_devices_code_prefix_list)) {
                    codePrefixList.add(prefix);
                }
                ArrayList<String> parentTypeList=new ArrayList<>();
                for (String parentType:getResources().getStringArray(R.array.registered_devices_code_list)) {
                    parentTypeList.add(parentType);
                }
                registeredDeviceItem.setParentType(parentTypeList.get(codePrefixList.indexOf(deviceCodePrefix)));

                GlobalObjects.aquariumItem.insertRegisteredDeviceItem(registeredDeviceItem);
            }
            else if(!result.get("DeviceCode").contains("AQ"))
            {
                GlobalObjects.aquariumItem.setName(result.get("DeviceName"));
            }
        }
        catch (Exception e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void SendLightningActivity(View view) {
        Intent intent = new Intent(this, LightningActivity.class);
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
        startActivity(intent);
    }

    public void SendTemperatureActivity(View view) {
        Intent intent = new Intent(this, TemperatureActivity.class);
        intent.putExtra("deviceCode",GlobalObjects.aquariumItem.getCode());
        startActivity(intent);
    }

    public void SendSettingsActivity(View view) {
        Intent intent = new Intent(this, SettingActivity.class);
        intent.putExtra("channelsNamesList", channelNames);
        intent.putExtra("colorsList", colorsList);
        startActivityForResult(intent,8);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        //SettingsActivity
        if(requestCode==8)
        {
            if(resultCode==2) {
                // Finishes Activity
                finish();
            }
        }
    }
}
