package com.example.AppAquario2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.Spinner;

import java.util.ArrayList;

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
        // Objects
        AquariumItem aquariumItem;

        // ArrayLists
        private ArrayList<RegisteredDeviceItem> registeredDevicesList;

        @Override
        public void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.aquarium_assembly_add);

            // Reads actual registered devices list
            Intent sendIntent= getIntent();
            registeredDevicesList = sendIntent.getParcelableArrayListExtra("registeredDevicesList");
            aquariumItem=getIntent().getParcelableExtra("aquariumItem");

            // Set the Items of ListView
            SpinnerInitialize();
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
        private void SpinnerInitialize()
        {
            // Add child Items
            ArrayList<String> childItems=new ArrayList<>();
            childItems.add(getResources().getString(R.string.Unset));
            for (int i=0;i<registeredDevicesList.size();i++)
            {
                boolean contains=false;

                for (int j = 0; j < aquariumItem.getRegisteredDeviceItemList().size(); j++)
                    if (aquariumItem.getRegisteredDeviceItemList().get(j).getCode().equals(registeredDevicesList.get(i).getCode()))
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

            if(spinner.getSelectedItem()!=null)
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
                Intent resultIntent = new Intent();
                setResult(Activity.RESULT_OK, resultIntent);
                resultIntent.putExtra("registeredDeviceItem",registeredDeviceItem);

                // Finishes the activity
                finish();
            }
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
