package com.example.AppAquario2;

import android.app.Activity;
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
 * Created by Lucas Milagres on 15-May-16.
 */
public class GetDeviceData extends AsyncTask<String,Void,Map<String,String>>
{
    private Exception exception;
    Activity activity;

    public GetDeviceData(Activity activity)
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
            url=new URL(urls[0]+urls[1]);
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
        Map<String,String> deviceData=new HashMap<>();
        try
        {
            JSONObject json_data = new JSONObject(result);
            JSONArray jArray = json_data.getJSONArray("emparray");
            JSONObject row=new JSONObject(jArray.getJSONObject(0).getString("row"));

            deviceData.put("Index",row.getString("Index"));
            deviceData.put("DeviceCode",row.getString("DeviceCode"));
            deviceData.put("DeviceName",row.getString("DeviceName"));
            deviceData.put("WifiSSID",row.getString("WifiSSID"));
            deviceData.put("WifiPassword",row.getString("WifiPassword"));
            deviceData.put("LastUpdate",row.getString("LastUpdate"));
        }
        catch(JSONException e)
        {
            Log.e("log_tag", "Error parsing data " + e.toString());
        }

        assert urlConnection != null;
        urlConnection.disconnect();
        return deviceData;
    }

    protected void onPostExecute(Map<String,String> result)
    {
        super.onPostExecute(result);
        if(activity.getLocalClassName().equals("Menu"))
            ((Menu)activity).insertDeviceData(result);
    }
}

