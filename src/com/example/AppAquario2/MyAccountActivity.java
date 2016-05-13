package com.example.AppAquario2;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Class: MyAccountActivity
 * Version: 1.0
 * Parameters: void
 * Return: Activity result
 * Perform: All actions in my_account layout
 * Created: 04/04/16
 * Creator: Lucas Gabriel N. Milagres
 */
public class MyAccountActivity  extends Activity
    {
        // Create ArrayList to hold parent Items and Child Items
        private ArrayList<String> parentItems = new ArrayList<>();
        private ArrayList<Object> childItems = new ArrayList<>();

        LinearLayout parentLayout;

        @Override
        public void onCreate(Bundle savedInstanceState)
        {
            try {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.my_account);
                parentLayout=(LinearLayout)findViewById(R.id.my_account_parent_layout);
                // Create Expandable List and set it's properties
                ExpandableListView expandableList = (ExpandableListView) findViewById(R.id.my_account_expandableListView);
                expandableList.setGroupIndicator(null);
                // Set the Items of Parent
                setGroupParents();
                // Set The Child Data
                setChildData();

                // Create the Adapter
                MyExpandableAdapter adapter = new MyExpandableAdapter(parentItems, childItems);
                adapter.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE), this);

                // Set the Adapter to expandableList
                expandableList.setAdapter(adapter);

                // Expands all parents
                ExpandAllParents(expandableList);
            }
            catch (Exception e)
            {
                Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
                finish();
            }
        }

        /**
         * Function: setGroupParents
         * Version: 1.0
         * Parameters: Void
         * Return: void
         * Perform: Add parent Items
         * Created: 04/04/16
         * Creator: Lucas Gabriel N. Milagres
         */
        public void setGroupParents()
        {
            for (String parentString:getResources().getStringArray(R.array.my_account_expandableListView_parents_list))
            {
                parentItems.add(parentString);
            }
        }

        /**
         * Function: setChildData
         * Version: 1.0
         * Parameters: Void
         * Return: void
         * Perform: Add child Items
         * Created: 04/04/16
         * Creator: Lucas Gabriel N. Milagres
         */
        public void setChildData()
        {
            // Restore preferences
            SharedPreferences sharedPreferences = getSharedPreferences(getResources().getString(R.string.login_data_file),0);
            String username = sharedPreferences.getString("username", null);
            String accountType = sharedPreferences.getString("accountType", null);
            String selectedAquarium = sharedPreferences.getString("selectedAquarium", null);

            if((username==null)||(accountType==null))
            {
                Logout(getResources().getString(R.string.login_data_error_msg), 1);
            }

            // Add Child Items for My Account
            ArrayList<String> child = new ArrayList<String>();
            child.add(username);
            child.add(getResources().getString(R.string.my_account_account_type)+accountType);
            child.add(getResources().getString(R.string.my_account_logout));

            childItems.add(child);

            // Add Child Items for Aquariums
            child = new ArrayList<String>();
            child.add(getResources().getString(R.string.my_account_selected_aquarium)+selectedAquarium);
            child.add(getResources().getString(R.string.my_account_assembled_aquariums));

            childItems.add(child);

            // Add Child Items for Devices
            child = new ArrayList<String>();
            child.add(getResources().getString(R.string.my_account_registered_devices));

            childItems.add(child);
        }


        /**
         * Function: ExpandAllParents
         * Version: 1.0
         * Parameters: Void
         * Return: void
         * Perform: Expands parent groups
         * Created: 04/04/16
         * Creator: Lucas Gabriel N. Milagres
         */
        private void ExpandAllParents(ExpandableListView expandableList)
        {
            int parents=expandableList.getCount();
            for(int pos=0;pos<parents;pos++)
                expandableList.expandGroup(pos);
        }


        /**
         * Function: Logout
         * Version: 1.0
         * Parameters: Void
         * Return: void
         * Perform: Logout
         * Created: 04/04/16
         * Creator: Lucas Gabriel N. Milagres
         */
        private void Logout(String error_msg, int logout_status)
        {

        }

    }

