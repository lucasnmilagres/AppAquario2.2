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

public class LoginActivity extends Activity {

    //Variables
    String email;
    String password;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
    }

    /**
     * Function: ReadDatabase
     * Version: 1.0
     * Parameters: view
     * Return: void
     * Perform: - Creates connection
     * Created: 14/05/16
     * Creator: Lucas Gabriel N. Milagres
     */
    public void ReadDatabase(View view) {
        email = ((EditText) findViewById(R.id.login_email)).getText().toString();
        password= ((EditText) findViewById(R.id.login_password)).getText().toString();

        if ((!email.equals(""))&&(!password.equals(""))) {
            //Creates connection
            String[] connectionData = new String[2];
            connectionData[0] = getResources().getString(R.string.server_get_user_info);
            connectionData[1] = email;
            new GetUserInfo(this).execute(connectionData);
        }
        else if(email.equals(""))
            Toast.makeText(this,getResources().getString(R.string.login_null_email),Toast.LENGTH_SHORT).show();
        else if(password.equals(""))
            Toast.makeText(this,getResources().getString(R.string.login_null_password),Toast.LENGTH_SHORT).show();
    }

    public void PasswordValidation(Map<String,String> userInfo)
    {
        try {
            if(userInfo.get("Email").equals(email)&& userInfo.get("Password").equals(password))
            {
                SharedPreferences sharedPreferences = getSharedPreferences(getResources().getString(R.string.login_data_file), 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("email", email);
                editor.putString("userCode", userInfo.get("Usercode"));
                editor.putString("aquariumCode",userInfo.get("SelectedAquarium"));
                editor.apply();

                Intent intent = new Intent(this, Menu.class);
                startActivity(intent);
                finish();
            }
            else
            {
                Toast.makeText(this,getResources().getString(R.string.login_validation_error),Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e)
        {
            if(userInfo.size()==0)
                Toast.makeText(this,getResources().getString(R.string.login_null_non_registered_email),Toast.LENGTH_SHORT).show();
            else
            {
                Log.e("TAG:",e.getMessage());
                Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void sendRegisterActivity(View v) {
        // Switching to Register screen
        Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(i);
    }
}