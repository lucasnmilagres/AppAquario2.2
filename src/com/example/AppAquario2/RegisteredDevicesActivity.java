package com.example.AppAquario2;

import android.app.Activity;
import android.app.ExpandableListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Class: RegisteredDevicesActivity
 * Version: 1.0
 * Parameters: registeredDevicesList
 * Return: Activity result
 * Perform: All actions in registered_devices layout
 * Created: 04/04/16
 * Creator: Lucas Gabriel N. Milagres
 */
public class RegisteredDevicesActivity extends Activity
{
    // Views
    ExpandableListView expandableList=null;

    // Objects
    MyExpandableAdapter adapter=null;
    Bundle budle=null;

    // Create ArrayList to hold parent Items and Child Items
    private ArrayList<String> parentItems = new ArrayList<>();
    private ArrayList<Object> childItems = new ArrayList<>();
    private ArrayList<RegisteredDeviceItem> registeredDevicesList;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        budle=savedInstanceState;
        setContentView(R.layout.registered_devices);

        // Create Expandable List and set it's properties
        expandableList = (ExpandableListView) findViewById(R.id.registered_devices_expandableListView);
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
     * Perform: Add parent Items
     * Created: 06/04/16
     * Creator: Lucas Gabriel N. Milagres
     */
    public void setGroupParents()
    {
        for (String parentString:getResources().getStringArray(R.array.registered_devices_expandableListView_parents_list))
        {
            parentItems.add(parentString);
        }
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
        // Reads actual registered devices list
        Intent sendIntent= getIntent();
        registeredDevicesList = sendIntent.getParcelableArrayListExtra("registeredDevicesList");

        // Categorize correctly registered devices parent types
        ArrayList<String>[] children=new ArrayList[parentItems.size()];

        for (int count=0;count<parentItems.size();count++)
        {
            children[count]=new ArrayList<>();
        }

        for (int count=0;count<registeredDevicesList.size();count++)
            if(parentItems.contains(registeredDevicesList.get(count).getParentType()))
            {
                children[parentItems.indexOf(registeredDevicesList.get(count).getParentType())].add(registeredDevicesList.get(count).getCode()+" - "+registeredDevicesList.get(count).getName());
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

        // Calls remove dialog
            Intent sendIntent = new Intent(this,RegisteredDevicesRemoveActivity.class);
            sendIntent.putExtra("name", name);
            startActivityForResult(sendIntent, 0);
        }
    }

    /**
     * Function: onActivityResult
     * Version: 1.0
     * Parameters: - requestCode;
     *             - resultCode;
     *             - data.
     * Return: void
     * Perform: - Treatment of RemoveActivity result;
     * Created: 10/04/16
     * Creator: Lucas Gabriel N. Milagres
     */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        // Treatment of RemoveActivity result
        if(requestCode==0)
        {
            // Treatment of "OK" result
            if(resultCode==RESULT_OK)
            {
                String code=data.getStringExtra("code");
                if(code!=null)
                {
                    // Removes item from registeredDevicesList
                    for (int count = 0; count < registeredDevicesList.size(); count++)
                        if (registeredDevicesList.get(count).getCode().equals(code))
                        {
                            registeredDevicesList.remove(registeredDevicesList.get(count));
                            count=registeredDevicesList.size();

                            // Refresh ExpandableListView
                            SetExpandable();
                            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                        }
                }
                else
                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.RegisteredDeviceRemoveNullCodeError),Toast.LENGTH_SHORT).show();
            }
        }
    }
}

