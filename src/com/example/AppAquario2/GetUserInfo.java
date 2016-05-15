package com.example.AppAquario2;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
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

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Function: GetUserInfo
 * Version: 1.0
 * Parameters: email
 * Return: userInfo
 * Perform: - Connects to database;
 * - Gets user info;
 * - Parse JSON data.
 * Created: 14/05/16
 * Creator: Lucas Gabriel N. Milagres
 */
public class GetUserInfo extends AsyncTask <String,Void,Map<String,String>>
{
    private Exception exception;
    Activity activity;

    public GetUserInfo(Activity activity)
    {
        this.activity=activity;
    }

    protected Map<String,String> doInBackground(String... urls)
    {
        //the email data to send
        String result = "";

        //http post
        URL url;
        HttpURLConnection urlConnection=null;
        InputStream is=null;
        try{
            url=new URL(urls[0]+"?Email="+urls[1]);
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
        Map<String,String> userInfo=new HashMap<>();
        try
        {
            JSONObject json_data = new JSONObject(result);
            JSONArray jArray = json_data.getJSONArray("emparray");
            JSONObject row=new JSONObject(jArray.getJSONObject(0).getString("row"));

            userInfo.put("Index",row.getString("Index"));
            userInfo.put("Usercode",row.getString("Usercode"));
            userInfo.put("Name",row.getString("Name"));
            userInfo.put("Email",row.getString("Email"));
            userInfo.put("Password",row.getString("Password"));
            userInfo.put("AccountType",row.getString("AccountType"));
            userInfo.put("IncomeDate",row.getString("IncomeDate"));
            userInfo.put("SelectedAquarium",row.getString("SelectedAquarium"));
        }
        catch(JSONException e)
        {
            Log.e("log_tag", "Error parsing data " + e.toString());
        }

        assert urlConnection != null;
        urlConnection.disconnect();
        return userInfo;
    }

    protected void onPostExecute(Map<String,String> result)
    {
        super.onPostExecute(result);
        if(activity.getLocalClassName().equals("MyAccountActivity"))
            ((MyAccountActivity)activity).setChildData(result);
        if(activity.getLocalClassName().equals("LoginActivity"))
            ((LoginActivity)activity).PasswordValidation(result);
    }
}
