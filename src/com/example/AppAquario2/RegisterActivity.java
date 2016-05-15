package com.example.AppAquario2;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;

public class RegisterActivity extends Activity {

    //Variables
    String email;
    String password;
    String name;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set View to register.xml
        setContentView(R.layout.register);
    }

    /**
     * Function: ConnectToDatabase
     * Version: 1.0
     * Parameters: view
     * Return: void
     * Perform: - Creates connection
     * Created: 14/05/16
     * Creator: Lucas Gabriel N. Milagres
     */
    public void ConnectToDatabase(View view) {
        name=((EditText) findViewById(R.id.reg_fullname)).getText().toString();
        email = ((EditText) findViewById(R.id.reg_email)).getText().toString();
        password= ((EditText) findViewById(R.id.reg_password)).getText().toString();

        if ((!name.equals(""))&&(!email.equals(""))&&(!password.equals(""))) {
            //Creates connection
            String[] connectionData = new String[4];
            connectionData[0] = getResources().getString(R.string.server_register_new_user);
            connectionData[1]="?Name="+name;
            connectionData[2] = "&Email="+email;
            connectionData[3]="&Password="+password;
            new RegisterNewUser(this).execute(connectionData);
        }
        else
            Toast.makeText(this,getResources().getString(R.string.reg_empty_field),Toast.LENGTH_SHORT).show();
    }

    /**
     * Function: finishActivity
     * Version: 1.0
     * Parameters: view
     * Return: Void
     * Perform: Finishes activity
     * Created: 15/05/16
     * Creator: Lucas Gabriel N. Milagres
     */
    public void finishActivity(View view)
    {
        finish();
    }

    /**
     * Function: finishActivity
     * Version: 1.0
     * Parameters: result_msg
     * Return: Void
     * Perform: Finishes activity
     * Created: 15/05/16
     * Creator: Lucas Gabriel N. Milagres
     */
    public void finishActivity(String result_msg)
    {
        try {
            Toast.makeText(this,result_msg,Toast.LENGTH_SHORT).show();
            finish();
        }
        catch (Exception e)
        {
            Log.e("TAG:",e.getMessage());
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
}