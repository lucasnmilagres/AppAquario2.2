package com.example.AppAquario2;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by Lucas Milagres on 27-Mar-16.
 */
public class AboutActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    LinearLayout parentLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.about);
            parentLayout=(LinearLayout)findViewById(R.id.about_parent_layout);
        }
        catch (Exception e)
        {
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
            finish();
        }
    }
}
