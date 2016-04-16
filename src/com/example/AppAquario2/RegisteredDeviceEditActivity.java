package com.example.AppAquario2;

import android.app.Activity;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;

/**
 * Class: RegisteredDeviceEditActivity
 * Version: 1.0
 * Parameters: registeredDeviceItem
 * Return: - Activity result
 *         - registeredDeviceItem
 * Perform: All actions in registered_devices_edit layout
 * Created: 16/04/16
 * Creator: Lucas Gabriel N. Milagres
 */
public class RegisteredDeviceEditActivity extends Activity
{
    // Views
    ExpandableListView expandableList=null;

    // Objects
    RegisteredDeviceItem registeredDeviceItem;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registered_devices_edit);

        // Gets registeredDeviceItem
        Intent sendIntent=getIntent();
        registeredDeviceItem=sendIntent.getParcelableExtra("registeredDeviceItem");

        VariablesInitialize();
        SpinnerInitialize();
    }

    /**
     * Function: VariablesInitialize
     * Version: 1.0
     * Parameters: Void
     * Return: void
     * Perform: - Sets Code
     *          - Sets Name
     *          - Sets Password
     *          - Sets SSID
     * Created: 16/04/16
     * Creator: Lucas Gabriel N. Milagres
     */
    private void VariablesInitialize()
    {
        // Sets Code
        TextView code=(TextView)findViewById(R.id.registered_devices_edit_code);
        if(registeredDeviceItem.getCode()!=null)
            code.setText(registeredDeviceItem.getCode());

        // Sets Name
        EditText name=(EditText)findViewById(R.id.registered_devices_edit_name_EditText);
        if(registeredDeviceItem.getName()!=null)
            name.setText(registeredDeviceItem.getName());

        // Sets Password
        EditText password=(EditText)findViewById(R.id.registered_devices_edit_password_EditText);
        if(registeredDeviceItem.getPassword()!=null)
            password.setText(registeredDeviceItem.getPassword());

        // Sets SSID
        EditText ssid=(EditText)findViewById(R.id.registered_devices_edit_ssid_EditText);
        if(registeredDeviceItem.getSsid()!=null)
            ssid.setText(registeredDeviceItem.getSsid());
        else
        {
            WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            if(wifiInfo.getSSID()!=null)
                ssid.setText(wifiInfo.getSSID().replace("\"",""));
        }
    }

    /**
     * Function: SpinnerInitialize
     * Version: 1.0
     * Parameters: Void
     * Return: void
     * Perform: - Reads parentsTypeList
     *          - Sets spinner' dropdown to actual values (if already set or obvious)
     *          - Selects spinner item
     * Created: 16/04/16
     * Creator: Lucas Gabriel N. Milagres
     */
    private void SpinnerInitialize()
    {
        // Creates Codes Prefix ArrayList
        ArrayList<String> registeredDevicesPrefixList=new ArrayList<>();
        String[] registeredDevicesPrefixArray=getResources().getStringArray(R.array.registered_devices_code_prefix_list);
        for (String registeredDevicesPrefix:registeredDevicesPrefixArray)
        {
            registeredDevicesPrefixList.add(registeredDevicesPrefix);
        }

        // Creates Codes ArrayList
        ArrayList<String> registeredDevicesCodeList=new ArrayList<>();
        String[] registeredDevicesCodeArray=getResources().getStringArray(R.array.registered_devices_code_list);
        for (String registeredDevicesCode:registeredDevicesCodeArray)
        {
            registeredDevicesCodeList.add(registeredDevicesCode);
        }

        //Sets spinners adapter (layout and strings settings)
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, registeredDevicesCodeList);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        //Finds the spinner in the activity
        Spinner spinner=(Spinner) findViewById(R.id.registered_devices_edit_spinner);

        //Sets adapter
        spinner.setAdapter(adapter);

        //Selects spinner item
        if(registeredDeviceItem.getParentType()!=null)
            spinner.setSelection(registeredDevicesCodeList.indexOf(registeredDeviceItem.getParentType()));
        else if((registeredDeviceItem.getCode()!=null)&&(!registeredDeviceItem.getCode().contains("DO")))
            spinner.setSelection(registeredDevicesPrefixList.indexOf(registeredDeviceItem.getCode().substring(0,1)));
        else
            spinner.setSelection(registeredDevicesPrefixList.indexOf("OT"));
    }

    /**
     * Function: acceptChanges
     * Version: 1.0
     * Parameters: "Ok" button
     * Return: - RESULT_OK
     *         - registeredDeviceItem
     * Perform: - Updates registeredDeviceItem values
     *          - Returns registeredDeviceItem to the parent activity
     *          - Finishes the activity
     * Created: 16/04/16
     * Creator: Lucas Gabriel N. Milagres
     */
    public void acceptChanges(View view)
    {
        // Updates registeredDeviceItem values
        registeredDeviceItem.setName(((EditText)findViewById(R.id.registered_devices_edit_name_EditText)).getText().toString());
        registeredDeviceItem.setSsid(((EditText)findViewById(R.id.registered_devices_edit_ssid_EditText)).getText().toString());
        registeredDeviceItem.setPassword(((EditText)findViewById(R.id.registered_devices_edit_password_EditText)).getText().toString());
        registeredDeviceItem.setParentType(((Spinner)findViewById(R.id.registered_devices_edit_spinner)).getSelectedItem().toString());

        // Returns registeredDeviceItem to the parent activity
        Intent resultIntent = new Intent();
        setResult(Activity.RESULT_OK, resultIntent);
        resultIntent.putExtra("registeredDeviceItem",registeredDeviceItem);

        //finishes the activity
        finish();
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


