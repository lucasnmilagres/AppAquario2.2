package com.example.AppAquario2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;
import java.util.ArrayList;

/**
 * Class: AquariumAssemblyActivity
 * Version: 1.0
 * Parameters: - registeredDevicesList
 *             - aquariumItem
 * Return: - Activity result
 *         - aquariumItem
 * Perform: All actions in aquarium_assembly layout
 * Created: 18/04/16
 * Creator: Lucas Gabriel N. Milagres
 */
public class AquariumAssemblyActivity extends MyExpandableActivity
{
    // Views
    private ExpandableListView expandableList=null;

    // Objects
    private MyExpandableAdapter adapter=null;
    private AquariumItem aquariumItem=null;

    // ArrayLists
    private ArrayList<String> parentItems = new ArrayList<>();
    private ArrayList<Object> childItems = new ArrayList<>();
    private ArrayList<RegisteredDeviceItem> registeredDevicesList=null;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aquarium_assembly);

        // Create Expandable List and set it's properties
        expandableList = (ExpandableListView) findViewById(R.id.aquarium_assembly_expandableListView);
        expandableList.setGroupIndicator(null);

        // Reads actual registeredSevicesList and aquariumItem
        Intent sendIntent= getIntent();
        registeredDevicesList=sendIntent.getParcelableArrayListExtra("registeredDevicesList");
        aquariumItem = sendIntent.getParcelableExtra("aquariumItem");

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
     * Function: refreshExpandableListView
     * Version: 1.0
     * Parameters: Void
     * Return: void
     * Perform: Refresh ExpandableListView
     * Created: 18/04/16
     * Creator: Lucas Gabriel N. Milagres
     */
    private void refreshExpandableListView()
    {
        SetExpandable();
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    /**
     * Function: setGroupParents
     * Version: 1.0
     * Parameters: Void
     * Return: void
     * Perform: Add parent Items
     * Created: 06/04/16
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
     * Perform: - Reads actual aquariumItem
     *          - Categorize correctly registered devices parent types
     *          - Add child Items
     * Created: 06/04/16
     * Creator: Lucas Gabriel N. Milagres
     */
    public void setChildData()
    {
        // Categorize correctly registered devices parent types
        ArrayList<String>[] children=new ArrayList[parentItems.size()];

        for (int count=0;count<parentItems.size();count++)
        {
            children[count]=new ArrayList<>();
        }

        for (int count=0;count<aquariumItem.getRegisteredDeviceItemList().size();count++)
            if(parentItems.contains(aquariumItem.getRegisteredDeviceItemList().get(count).getParentType()))
            {
                children[parentItems.indexOf(aquariumItem.getRegisteredDeviceItemList().get(count).getParentType())].add(aquariumItem.getRegisteredDeviceItemList().get(count).getCode()+" - "+aquariumItem.getRegisteredDeviceItemList().get(count).getName());
            }

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
     * Function: callRemove
     * Version: 1.0
     * Parameters: Void
     * Return: void
     * Perform: - Finds child name;
     *          - Calls remove dialog.
     * Created: 10/04/16
     * Creator: Lucas Gabriel N. Milagres
     */
    public void callRemove(View view)
    {
        if(adapter.getSelectedItem()!=null)
        {
            // Finds child name
            String name=adapter.getSelectedItem();

            // Calls remove dialog (request 0)
            Intent sendIntent = new Intent(this,RegisteredDevicesRemoveActivity.class);
            sendIntent.putExtra("name", name);
            startActivityForResult(sendIntent, 0);
        }
    }

    /**
     * Function: callAddDevice
     * Version: 1.0
     * Parameters: Void
     * Return: void
     * Perform: Calls add device dialog.
     * Created: 15/04/16
     * Creator: Lucas Gabriel N. Milagres
     */
    public void callAddDevice(View view)
    {
        // Calls add device dialog (request 2)
        Intent sendIntent = new Intent(this,AquariumAssemblyAddActivity.class);
        sendIntent.putExtra("registeredDevicesList",registeredDevicesList);
        sendIntent.putExtra("aquariumItem",aquariumItem);
        startActivityForResult(sendIntent, 2);
    }

    /**
     * Function: onActivityResult
     * Version: 1.0
     * Parameters: - requestCode;
     *             - resultCode;
     *             - data.
     * Return: void
     * Perform: - Treatment of RemoveActivity result;
     *          - Treatment of AddActivity result;
     * Created: 18/04/16
     * Creator: Lucas Gabriel N. Milagres
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        // Treatment of RemoveActivity result
        if(requestCode==0) {
            // Treatment of "OK" result
            if (resultCode == RESULT_OK) {
                String code = data.getStringExtra("code");
                if (code != null) {
                    // Removes item from aquariumItem
                    for (int count = 0; count < aquariumItem.getRegisteredDeviceItemList().size(); count++)
                        if (aquariumItem.getRegisteredDeviceItemList().get(count).getCode().equals(code)) {
                            aquariumItem.getRegisteredDeviceItemList().remove(aquariumItem.getRegisteredDeviceItemList().get(count));
                            count = aquariumItem.getRegisteredDeviceItemList().size();

                            // Refresh ExpandableListView
                            refreshExpandableListView();
                        }
                } else
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.RegisteredDeviceRemoveNullCodeError), Toast.LENGTH_SHORT).show();
            }
        }

        // Treatment of AddActivity results
        else if(requestCode==2)
        {
            // Treatment of "OK" result
            if (resultCode == RESULT_OK)
            {
                RegisteredDeviceItem registeredDeviceItem = data.getParcelableExtra("registeredDeviceItem");
                if (registeredDeviceItem != null) {
                    aquariumItem.getRegisteredDeviceItemList().add(registeredDeviceItem);
                    refreshExpandableListView();
                } else
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.RegisteredDeviceEditNullCodeError), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
