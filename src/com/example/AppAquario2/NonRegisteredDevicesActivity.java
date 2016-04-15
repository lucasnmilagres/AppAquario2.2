package com.example.AppAquario2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.content.BroadcastReceiver;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * Class: NonRegisteredDevicesActivity
 * Version: 1.0
 * Parameters: registeredDevicesList
 * Return: Activity result
 * Perform: All actions in non_registered_devices layout
 * Created: 13/04/16
 * Creator: Lucas Gabriel N. Milagres
 */
public class NonRegisteredDevicesActivity extends Activity
{
    // ListView
    ExpandableListView expandableList=null;
    MyExpandableAdapter adapter=null;

    // ArrayLists
    private List<ScanResult> scanResultList;
    private ArrayList<String> parentItems = new ArrayList<>();
    private ArrayList<Object> childItems = new ArrayList<>();
    private ArrayList<RegisteredDeviceItem> registeredDevicesList;
    private ArrayList<String> nonRegisteredDevices=new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.non_registered_devices);

        // Reads actual registered devices list
        Intent sendIntent= getIntent();
        registeredDevicesList = sendIntent.getParcelableArrayListExtra("registeredDevicesList");

        // Create ListView and set it's properties
        expandableList = (ExpandableListView) findViewById(R.id.non_registered_devices_expandableListView);
        expandableList.setGroupIndicator(null);

        // Set the Items of Parent
        setGroupParents();
        GetNonRegisteredDevices();
    }

    /**
     * Function: GetNonRegisteredDevices
     * Version: 1.0
     * Parameters: Void
     * Return: void
     * Perform: - Scans all Wifi Networks around;
     *          - Tests which Wifi Networks are produced by CREELED Devices and are not registered yet
     * Created: 13/04/16
     * Creator: Lucas Gabriel N. Milagres
     */
    private void GetNonRegisteredDevices()
    {
        // Scans all Wifi Networks around
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        final  WifiManager wifiManager = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent)
            {
                scanResultList=wifiManager.getScanResults();

                // Tests which Wifi Networks are produced by CREELED Devices and are not registered yet
                for (ScanResult scanResult:scanResultList)
                    for (RegisteredDeviceItem registeredDeviceItem:registeredDevicesList)
                        if((scanResult.SSID.contains(getResources().getString(R.string.network_id_tag)))&& (!scanResult.SSID.contains(registeredDeviceItem.getCode())))
                            nonRegisteredDevices.add(scanResult.SSID.replace(getResources().getString(R.string.network_id_tag), ""));

                SetExpandable();
            }
        }, filter);
        wifiManager.startScan();
    }

    /**
     * Function: SetExpandable
     * Version: 1.0
     * Parameters: Void
     * Return: void
     * Perform: Sets expandable values.
     * Created: 14/04/16
     * Creator: Lucas Gabriel N. Milagres
     */
    public void SetExpandable()
    {
        // Set The Child Data
        setChildData();

        // Create the Adapter
        adapter = new MyExpandableAdapter(parentItems, childItems);
        adapter.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE), this);

        // Set the Adapter to expandableList
        expandableList.setAdapter(adapter);

        // Expands all parents
        ExpandAllParents(expandableList);
    }

    /**
     * Function: setGroupParents
     * Version: 1.0
     * Parameters: Void
     * Return: void
     * Perform: Add parent Items
     * Created: 14/04/16
     * Creator: Lucas Gabriel N. Milagres
     */
    public void setGroupParents()
    {
        for (String parentString:getResources().getStringArray(R.array.registered_devices_code_list))
            parentItems.add(parentString);
    }

    /**
     * Function: setChildData
     * Version: 1.0
     * Parameters: Void
     * Return: void
     * Perform: - Reads actual registered devices list
     *          - Categorize correctly registered devices parent types
     *          - Add child Items
     * Created: 06/04/16
     * Creator: Lucas Gabriel N. Milagres
     */
    public void setChildData()
    {
        // Categorize correctly registered devices parent types
        ArrayList<String> prefixList=new ArrayList<>();
        ArrayList<String>[] children=new ArrayList[parentItems.size()];

        for (String prefix:getResources().getStringArray(R.array.registered_devices_code_prefix_list))
        {
            prefixList.add(prefix);
        }

        for (int count=0;count<parentItems.size();count++)
        {
            children[count]=new ArrayList<>();
        }

        for (int count=0;count<nonRegisteredDevices.size();count++)
            if(prefixList.contains(nonRegisteredDevices.get(count).substring(0,1)))
                children[prefixList.indexOf(nonRegisteredDevices.get(count).substring(0,1))].add(nonRegisteredDevices.get(count));

        // Add child Items
        for (ArrayList<String> child:children)
            childItems.add(child);
    }


    /**
     * Function: ExpandAllParents
     * Version: 1.0
     * Parameters: Void
     * Return: void
     * Perform: Expands parent groups
     * Created: 09/04/16
     * Creator: Lucas Gabriel N. Milagres
     */
    private void ExpandAllParents(ExpandableListView expandableList)
    {
        int parents=expandableList.getCount();
        for(int pos=0;pos<parents;pos++)
            expandableList.expandGroup(pos,false);
    }

    /**
     * Function: finishActivity
     * Version: 1.0
     * Parameters: "Cancel" button
     * Return: RESULT_CANCELED
     * Perform: - Returns null to the parent activity
     *          - Finishes the activity
     * Created: 27/03/16
     * Creator: Lucas Gabriel N. Milagres
     */
    public void finishActivity(View view)
    {
        //Returns null to the parent activity
        Intent resultIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, resultIntent);

        //finishes the activity
        finish();
    }
}