package com.example.AppAquario2;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Lucas Milagres on 25-Mar-16.
 */
public class SettingActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
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
}