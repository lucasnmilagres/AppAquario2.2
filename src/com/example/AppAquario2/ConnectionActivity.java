package com.example.AppAquario2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;
import java.util.ArrayList;

/**
 * Class: ConnectionActivity
 * Version: 1.0
 * Parameters: aquariumItem
 * Return: Activity result
 * Perform: All actions in connection layout
 * Created: 01/04/16
 * Creator: Lucas Gabriel N. Milagres
 */
public class ConnectionActivity extends Activity
{
    // Views
    ExpandableListView expandableList=null;

    // Objects
    MyExpandableAdapter adapter=null;

    // Create ArrayList to hold parent Items and Child Items
    private ArrayList<String> parentItems = new ArrayList<>();
    private ArrayList<Object> childItems = new ArrayList<>();
    private AquariumItem aquariumItem=null;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connection);

        // Create Expandable List and set it's properties
        expandableList = (ExpandableListView) findViewById(R.id.connection_expandableListView);
        expandableList.setGroupIndicator(null);

        SetExpandable();
    }

    /**
     * Function: SetExpandable
     * Version: 1.0
     * Parameters: Void
     * Return: void
     * Perform: Sets expandable values.
     * Created: 10/04/16
     * Creator: Lucas Gabriel N. Milagres
     */
    public void SetExpandable()
    {
        // Set the Items of Parent
        setGroupParents();
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
     * Perform: - Reads actual registered devices list
     *          - Add parent Items
     * Created: 06/04/16
     * Creator: Lucas Gabriel N. Milagres
     */
    public void setGroupParents()
    {
        // Reads actual registered devices list
        Intent sendIntent= getIntent();
        aquariumItem = sendIntent.getParcelableExtra("aquariumItem");

        // Add parent Items
        parentItems.add("Local Data");
        for (RegisteredDeviceItem registeredDeviceItem:aquariumItem.getRegisteredDeviceItemList())
            if(!parentItems.contains(registeredDeviceItem.getParentType()))
                parentItems.add(registeredDeviceItem.getParentType());
    }

    /**
     * Function: setChildData
     * Version: 1.0
     * Parameters: Void
     * Return: void
     * Perform: - Reads actual registered devices list
     *          - Categorize correctly registered devices parent types
     *          - Checks user connection
     *          - Add child Items
     * Created: 06/04/16
     * Creator: Lucas Gabriel N. Milagres
     */
    public void setChildData()
    {
        // Categorize correctly registered devices parent types
        ArrayList<String>[] children=new ArrayList[parentItems.size()+1];

        for (int count=0;count<=parentItems.size();count++)
        {
            children[count]=new ArrayList<>();
        }

        for (int count=0;count<aquariumItem.getRegisteredDeviceItemList().size();count++)
            if(parentItems.contains(aquariumItem.getRegisteredDeviceItemList().get(count).getParentType()))
            {
                children[parentItems.indexOf(aquariumItem.getRegisteredDeviceItemList().get(count).getParentType())].add(aquariumItem.getRegisteredDeviceItemList().get(count).getCode()+" - "+aquariumItem.getRegisteredDeviceItemList().get(count).getName());
            }

        // Checks user connection
        String connectionStatus;
        final ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected())
            connectionStatus="Online";
        else
            connectionStatus="Offline";
        children[0].add("Your Device: "+connectionStatus);

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
     * Parameters: "OK" button
     * Return: void
     * Perform: - Sends OK result to parent;
     *          - Finishes Activity.
     * Created: 16/04/16
     * Creator: Lucas Gabriel N. Milagres
     */
    public void finishActivity(View view)
    {
        // Sends Cancel result to parent
        Intent intent =new Intent();
        setResult(RESULT_OK,intent);

        // Finishes Activity
        finish();
    }
}

