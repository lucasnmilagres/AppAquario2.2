package com.example.AppAquario2;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by Lucas Milagres on 25-Mar-16.
 */
public class SettingActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    // Objects
    private AquariumItem aquariumItem;
    private ArrayList<RegisteredDeviceItem> registeredDevicesList;
    private ArrayList<String> channelNames;
    private ArrayList<Integer> colorsList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        Intent sendIntent=getIntent();
        channelNames = sendIntent.getStringArrayListExtra("channelsNamesList");
        colorsList = sendIntent.getIntegerArrayListExtra("colorsList");
        aquariumItem=sendIntent.getParcelableExtra("aquariumItem");
        registeredDevicesList=sendIntent.getParcelableArrayListExtra("registeredDevicesList");
    }

    /**
     * Function: sendMyAccountActivity
     * Version: 1.0
     * Parameters: button "MyAccount"
     * Returns: Void
     * Performs: Opens MyAccountActivity
     * Created: 16/04/16
     * Creator: Lucas Gabriel N. Milagres
     */
    public void sendMyAccountActivity(View view)
    {
        Intent myAccountIntent = new Intent(this, MyAccountActivity.class);
        startActivityForResult(myAccountIntent, 0);
    }

    /**
     * Function: sendSettingsLightActivity
     * Version: 1.0
     * Parameters: button "SettingsLight"
     * Returns: Void
     * Performs: Opens SettingsLightActivity
     * Created: 09/05/16
     * Creator: Lucas Gabriel N. Milagres
     */
    public void sendSettingsLightActivity(View view)
    {
        Intent settingsLightIntent = new Intent(this, SettingsLightActivity.class);
        settingsLightIntent.putExtra("colorsList",colorsList);
        settingsLightIntent.putExtra("channelsNamesList",channelNames);
        startActivity(settingsLightIntent);
    }

    /**
     * Function: openWebsite
     * Version: 1.0
     * Parameters: button "Website"
     * Returns: Void
     * Performs: Opens Website
     * Created: 27/03/16
     * Creator: Lucas Gabriel N. Milagres
     */
    public void openWebsite(View view)
    {
        Intent websiteIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.website)));
        startActivity(websiteIntent);
    }

    /**
     * Function: openHelpPage
     * Version: 1.0
     * Parameters: button "Help"
     * Returns: Void
     * Performs: Opens Help website
     * Created: 27/03/16
     * Creator: Lucas Gabriel N. Milagres
     */
    public void openHelpPage(View view)
    {
        Intent helpPageIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.helpPage)));
        startActivity(helpPageIntent);
    }

    /**
     * Function: sendRatingtActivity
     * Version: 1.0
     * Parameters: button "Rating"
     * Returns: Void
     * Performs: Opens RatingActivity
     * Created: 27/03/16
     * Creator: Lucas Gabriel N. Milagres
     */
    public void sendRatingActivity(View view)
    {
        Intent ratingIntent = new Intent(this, RatingActivity.class);
        startActivity(ratingIntent);
    }

    /**
     * Function: sendConnectionActivity
     * Version: 1.0
     * Parameters: button "Connection"
     * Returns: Void
     * Performs: Opens ConnectionActivity
     * Created: 16/04/16
     * Creator: Lucas Gabriel N. Milagres
     */
    public void sendConnectionActivity(View view)
    {
        Intent connectionIntent = new Intent(this, ConnectionActivity.class);
        connectionIntent.putExtra("aquariumItem",aquariumItem);
        startActivity(connectionIntent);
    }

    /**
     * Function: sendAquariumAssemblyActivity
     * Version: 1.0
     * Parameters: button "Aquarium Assembly"
     * Returns: Void
     * Performs: Opens AquariumAssemblyActivity
     * Created: 26/04/16
     * Creator: Lucas Gabriel N. Milagres
     */
    public void sendAquariumAssemblyActivity(View view)
    {
        Intent aquariumAssemblyIntent = new Intent(this, AquariumAssemblyActivity.class);
        aquariumAssemblyIntent.putExtra("aquariumItem",aquariumItem);
        aquariumAssemblyIntent.putExtra("registeredDevicesList",registeredDevicesList);
        startActivity(aquariumAssemblyIntent);
    }

    /**
     * Function: sendAboutActivity
     * Version: 1.0
     * Parameters: button "About"
     * Returns: Void
     * Performs: Opens AboutActivity
     * Created: 27/03/16
     * Creator: Lucas Gabriel N. Milagres
     */
    public void sendAboutActivity(View view)
    {
        Intent aboutIntent = new Intent(this, AboutActivity.class);
        startActivity(aboutIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==0)
        {
            if(resultCode==2) {
                // Sends Cancel result to parent
                Intent intent =new Intent();
                setResult(2,intent);

                // Finishes Activity
                finish();
            }
        }
    }
}