package com.example.AppAquario2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Class: RegisteredDevicesRemoveActivity
 * Version: 1.0
 * Parameters: registeredDeviceItemName
 * Return: Activity result
 * Perform: All actions in registered_devices_remove layout
 * Created: 10/04/16
 * Creator: Lucas Gabriel N. Milagres
 */
public class RegisteredDevicesRemoveActivity extends Activity
{
    // Global Variables
    String name=null;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registered_devices_remove);

        setBodyText();
    }

    /**
     * Function: setBodyText
     * Version: 1.0
     * Parameters: Void
     * Return: void
     * Perform: - Gets registeredDeviceItem name;
     *          - Sets TextView text.
     * Created: 10/04/16
     * Creator: Lucas Gabriel N. Milagres
     */
    private void setBodyText()
    {
        // Gets registeredDeviceItem name
        Intent sendIntent=getIntent();
        name=sendIntent.getStringExtra("name");

        // Sets TextView text
        if(name!=null)
        {
           TextView textView = (TextView) findViewById(R.id.registered_devices_remove_body);
            textView.setText(getResources().getString(R.string.registered_devices_remove_body) + " "+name + "?");
        }
        else
            Cancel(null);
    }

    /**
     * Function: Remove
     * Version: 1.0
     * Parameters: "Ok" button
     * Return: void
     * Perform: - Sends code to parent;
     *          - Sends "OK" result to parent;
     *          - Finishes Activity.
     * Created: 10/04/16
     * Creator: Lucas Gabriel N. Milagres
     */
    public void Remove(View view)
    {
        // Sends code to parent;
        Intent intent =new Intent();
        String code=name.substring(0,name.indexOf(" - "));
        intent.putExtra("code",code);

        // Sends OK result to parent
        setResult(RESULT_OK,intent);

        // Finishes Activity
        finish();
    }

    /**
     * Function: Cancel
     * Version: 1.0
     * Parameters: "Cancel" button
     * Return: void
     * Perform: - Sends Cancel result to parent;
     *          - Finishes Activity.
     * Created: 10/04/16
     * Creator: Lucas Gabriel N. Milagres
     */
    public void Cancel(View view)
    {
        // Sends Cancel result to parent
        Intent intent =new Intent();
        setResult(RESULT_CANCELED,intent);

        // Finishes Activity
        finish();
    }
}
