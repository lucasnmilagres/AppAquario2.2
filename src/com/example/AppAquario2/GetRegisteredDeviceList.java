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
import java.util.ArrayList;

/**
 * Created by Lucas Milagres on 16-May-16.
 */
public class GetRegisteredDeviceList extends AsyncTask<String,Void,ArrayList<String>>
{
    private Exception exception;
    Activity activity;

    public GetRegisteredDeviceList(Activity activity)
    {
        this.activity=activity;
    }

    protected ArrayList<String> doInBackground(String... urls)
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
        ArrayList<String> aquariumData=new ArrayList<>();
        try
        {
            JSONObject json_data = new JSONObject(result);
            JSONArray jArray = json_data.getJSONArray("emparray");

            for(int i=0;i<jArray.length();i++)
            {
                JSONObject row = new JSONObject(jArray.getJSONObject(i).getString("row"));
                aquariumData.add(row.getString("DeviceCode"));
            }
        }
        catch(JSONException e)
        {
            Log.e("log_tag", "Error parsing data " + e.toString());
        }

        assert urlConnection != null;
        urlConnection.disconnect();
        return aquariumData;
    }

    protected void onPostExecute(ArrayList<String> result)
    {
        super.onPostExecute(result);

        try {
            if(activity.getLocalClassName().equals("AquariumAssemblyAddActivity"))
                ((AquariumAssemblyAddActivity)activity).getFlagValue(result.size());

            for (String deviceCode : result)
            {
                if(activity.getLocalClassName().equals("AquariumAssemblyAddActivity"))
                    ((AquariumAssemblyAddActivity)activity).askDevicesData(deviceCode);
            }
        }
        catch(Exception e)
        {
            Log.e("log_tag", "Error parsing data " + e.toString());
        }
    }
}
