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
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lucas Milagres on 15-May-16.
 */
public class GetTempStatus extends AsyncTask<String,Void,ArrayList<Map<String,Object>>>
{
    Exception exception;
    TemperatureActivity activity;

    public GetTempStatus(TemperatureActivity activity)
    {
        this.activity=activity;
    }

    protected ArrayList<Map<String,Object>> doInBackground(String... urls)
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
        ArrayList<Map<String,Object>> dataArray=new ArrayList<>();
        try
        {
            JSONObject json_data = new JSONObject(result);
            JSONArray jArray = json_data.getJSONArray("emparray");

            for(int i=jArray.length()-1;i>=jArray.length()-24;i--) {
                JSONObject row = new JSONObject(jArray.getJSONObject(i).getString("row"));
                Map<String,Object> userInfo=new HashMap<>();
                userInfo.put("Time", row.getString("Time"));
                userInfo.put("MinSet", row.getDouble("MinSet"));
                userInfo.put("MaxSet", row.getDouble("MaxSet"));
                userInfo.put("CurrentTemp", row.getDouble("CurrentTemp"));

                dataArray.add(userInfo);
            }
        }
        catch(JSONException e)
        {
            Log.e("log_tag", "Error parsing data " + e.toString());
        }

        assert urlConnection != null;
        urlConnection.disconnect();
        return dataArray;
    }

    protected void onPostExecute(ArrayList<Map<String,Object>> result)
    {
        super.onPostExecute(result);
        activity.RestoreUserTemperatureValues(result);
    }
}
