package com.example.AppAquario2;

import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lucas Milagres on 14-May-16.
 */
public class RegisterNewUser extends AsyncTask<String,Void,String>
{
    Exception exception;
    RegisterActivity registerActivity;

    public RegisterNewUser(RegisterActivity registerActivity)
    {
        this.registerActivity=registerActivity;
    }

    @Override
    protected String doInBackground(String... urls)
    {
        //the email data to send
        String result = "";

        //http post
        URL url;
        HttpURLConnection urlConnection=null;
        InputStream is=null;
        try{
            url=new URL(urls[0]+urls[1]+urls[2]+urls[3]);
            urlConnection=(HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(5000);
            urlConnection.connect();
            is=urlConnection.getInputStream();
        }catch(Exception e){
            Log.e("log_tag", "Error in http connection "+e.toString());
        }

        //convert response to string
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();

            result=sb.toString();
        }catch(Exception e){
            Log.e("log_tag", "Error converting result "+e.toString());
        }

        //parse json data
        String result_msg=null;
        try
        {
            JSONObject json_data = new JSONObject(result);
            result_msg=json_data.getString("emparray");
        }
        catch(JSONException e)
        {
            Log.e("log_tag", "Error parsing data " + e.toString());
        }

        assert urlConnection != null;
        urlConnection.disconnect();
        return result_msg;
    }

    protected void onPostExecute(String result_msg)
    {
        super.onPostExecute(result_msg);
        registerActivity.finishActivity(result_msg);
    }
}
