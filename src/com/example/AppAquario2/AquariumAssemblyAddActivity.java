package com.example.AppAquario2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;

/**
 * Class: AquariumAssemblyActivity
 * Version: 1.0
 * Parameters: - registeredDevicesList
 *             - aquariumItem
 * Return: - Activity result
 *         - registeredDeviceItem
 * Perform: All actions in aquarium_assembly layout
 * Created: 18/04/16
 * Creator: Lucas Gabriel N. Milagres
 */
public class AquariumAssemblyAddActivity  extends Activity
    {
        // Variables
        private int SpinnerInitializeFlag;
        private int SpinnerInitializeCounter;

        // ArrayLists
        private ArrayList<RegisteredDeviceItem> registeredDevicesList;

        @Override
        public void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.aquarium_assembly_add);

            AskRegisteredDeviceList();
        }

        private void AskRegisteredDeviceList()
        {
            SharedPreferences sharedPreferences = getSharedPreferences(getResources().getString(R.string.login_data_file), 0);
            String userCode=sharedPreferences.getString("userCode", "null");

            registeredDevicesList=new ArrayList<>();
            SpinnerInitializeCounter = 0;

            //Creates connection
            if(!userCode.equals("null")) {
                String[] connectionData = new String[2];
                connectionData[0] = getResources().getString(R.string.server_get_registered_device_list);
                connectionData[1] = "?Usercode=" + userCode;
                new GetRegisteredDeviceList(this).execute(connectionData);
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

        public boolean insertDeviceData(Map<String,String> result)
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

                    registeredDevicesList.add(registeredDeviceItem);
                    SpinnerInitializeCounter++;
                }
            }
            catch (Exception e)
            {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }

            return SpinnerInitializeFlag == SpinnerInitializeCounter;
        }

        public void getFlagValue(int listSize)
        {
            SpinnerInitializeFlag=listSize-1;
        }

        /**
         * Function: SpinnerInitialize
         * Version: 1.0
         * Parameters: Void
         * Return: void
         * Perform: - Add child Items
         *          - Sets spinner' dropdown to actual values (if already set or obvious)
         *          - Selects spinner item
         * Created: 16/04/16
         * Creator: Lucas Gabriel N. Milagres
         */
        public void SpinnerInitialize()
        {
            // Add child Items
            ArrayList<String> childItems=new ArrayList<>();
            childItems.add(getResources().getString(R.string.Unset));
            for (int i=0;i<registeredDevicesList.size();i++)
            {
                boolean contains=false;

                for (int j = 0; j < GlobalObjects.aquariumItem.getRegisteredDeviceItemList().size(); j++)
                    if (GlobalObjects.aquariumItem.getRegisteredDeviceItemList().get(j).getCode().equals(registeredDevicesList.get(i).getCode()))
                        contains=true;

                if(!contains)
                    childItems.add(registeredDevicesList.get(i).getCode()+" - "+registeredDevicesList.get(i).getName());
            }


            //Sets spinners adapter (layout and strings settings)
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, childItems);
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

            //Finds the spinner in the activity
            Spinner spinner=(Spinner) findViewById(R.id.aquarium_assembly_add_spinner);

            //Sets adapter
            spinner.setAdapter(adapter);

            //Selects spinner item
            spinner.setSelection(0);
        }

        /**
         * Function: callAdd
         * Version: 1.0
         * Parameters: Void
         * Return: void
         * Perform: - Finds registeredDeviceItem;
         *          - Returns registeredDeviceItem to the parent activity;
         *          - Finishes the activity.
         * Created: 25/04/16
         * Creator: Lucas Gabriel N. Milagres
         */
        public void callAdd(View view)
        {
            Spinner spinner=(Spinner)findViewById(R.id.aquarium_assembly_add_spinner);
            Intent resultIntent = new Intent();

            if((spinner.getSelectedItem()!=null)&&(!spinner.getSelectedItem().toString().equals("<Unset>")))
            {
                // Finds registeredDeviceItem
                RegisteredDeviceItem registeredDeviceItem=new RegisteredDeviceItem(null,null,null,null,null);
                String name=spinner.getSelectedItem().toString();
                String code=name.substring(0,name.indexOf(" - "));
                for (int count = 0; count < registeredDevicesList.size(); count++)
                    if (registeredDevicesList.get(count).getCode().equals(code))
                    {
                        registeredDeviceItem=registeredDevicesList.get(count);
                        count=registeredDevicesList.size();
                    }

                // Returns registeredDeviceItem to the parent activity
                setResult(Activity.RESULT_OK, resultIntent);
                resultIntent.putExtra("registeredDeviceItem",registeredDeviceItem);
            }
            else
                setResult(Activity.RESULT_CANCELED, resultIntent);
            // Finishes the activity
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
