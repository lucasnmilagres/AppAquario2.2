package com.example.AppAquario2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.Toast;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Map;

/**
 * Class: MyAccountActivity
 * Version: 1.0
 * Parameters: void
 * Return: Activity result
 * Perform: All actions in my_account layout
 * Created: 04/04/16
 * Creator: Lucas Gabriel N. Milagres
 */
public class MyAccountActivity  extends MyExpandableActivity {
    // Create ArrayList to hold parent Items and Child Items
    private ArrayList<String> parentItems = new ArrayList<>();
    private ArrayList<Object> childItems = new ArrayList<>();

    LinearLayout parentLayout;
    ExpandableListView expandableList;

    String email;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.my_account);
            parentLayout = (LinearLayout) findViewById(R.id.my_account_parent_layout);
            // Create Expandable List and set it's properties
            expandableList = (ExpandableListView) findViewById(R.id.my_account_expandableListView);
            expandableList.setGroupIndicator(null);

            // Set the Items of Parent
            setGroupParents();
            // Read Database
            ReadDatabase();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
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
    public void setGroupParents() {
        for (String parentString : getResources().getStringArray(R.array.my_account_expandableListView_parents_list)) {
            parentItems.add(parentString);
        }
    }

    /**
     * Function: ReadDatabase
     * Version: 1.0
     * Parameters: Void
     * Return: void
     * Perform: - Restore preferences
     *          - Creates connection
     * Created: 14/05/16
     * Creator: Lucas Gabriel N. Milagres
     */
    public void ReadDatabase() {
        // Restore preferences
        SharedPreferences sharedPreferences = getSharedPreferences(getResources().getString(R.string.login_data_file), 0);
        email = sharedPreferences.getString("email", null);

        if (email == null) {
            Logout(getResources().getString(R.string.login_data_error_msg));
        }

        //Creates connection
        String[] connectionData = new String[2];
        connectionData[0] = getResources().getString(R.string.server_get_user_info);
        connectionData[1] = email;
        new GetUserInfo(this).execute(connectionData);
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
    public void setChildData(Map<String,String> userInfo) {
            try {
                // Add Child Items for My Account
                ArrayList<String> child = new ArrayList<>();
                child.add(userInfo.get("Name"));
                child.add(email);
                child.add(getResources().getString(R.string.my_account_account_type) + userInfo.get("AccountType"));
                child.add(getResources().getString(R.string.my_account_logout));

                childItems.add(child);

                // Add Child Items for Aquariums
                child = new ArrayList<>();
                child.add(getResources().getString(R.string.my_account_selected_aquarium) + userInfo.get("SelectedAquarium"));
                child.add(getResources().getString(R.string.my_account_assembled_aquariums));

                childItems.add(child);

                // Add Child Items for Devices
                child = new ArrayList<String>();
                child.add(getResources().getString(R.string.my_account_registered_devices));

                childItems.add(child);

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
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("TAG",e.getMessage());
                finish();
            }
    }

    @Override
    public void callOnChildClick(String selectedItem) {
        super.callOnChildClick(selectedItem);
        if(selectedItem.equals(getResources().getString(R.string.my_account_logout)))
        {
            Logout(null);
        }
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
        private void Logout(String error_msg)
        {
            //Error message
            if(error_msg!=null)
                Toast.makeText(this,error_msg,Toast.LENGTH_SHORT).show();

            // Clear SharedPreferences
            SharedPreferences sharedPreferences = getSharedPreferences(getResources().getString(R.string.login_data_file), 0);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("email", null);
            editor.apply();

            // Send LoginActivity
            Intent sendLoginActivity= new Intent(this, LoginActivity.class);
            startActivity(sendLoginActivity);

            // Sends Cancel result to parent
            Intent intent =new Intent();
            setResult(2,intent);

            // Finishes Activity
            finish();
        }

    }

