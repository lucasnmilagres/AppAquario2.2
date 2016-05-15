package com.example.AppAquario2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Lucas Milagres on 25-Mar-16.
 */
public class TemperatureActivity extends Activity {
    //Views
    LineChart lineChart;

    //Lists
    ArrayList<Entry> chartPoints; //Array list full with the chart points
    ArrayList<String> chartXLabels; //Array list full with the X-Axis labels

    //Variables
    double minTemp;
    double maxTemp;
    float currentTemp;
    String deviceCode;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temperature);

        deviceCode=getIntent().getStringExtra("deviceCode");

        AskForTempData();
    }

    /**
     * Function: AskForTempData
     * Version: 1.0
     * Parameters: Void
     * Returns: Void
     * Performs: Starts communication with database to get data
     * Created: 25/03/16
     * Creator: Lucas Gabriel N. Milagres
     */
    private void AskForTempData()
    {
        //Creates connection
        String[] connectionData = new String[2];
        connectionData[0] = getResources().getString(R.string.server_get_temp_status);
        connectionData[1] = "?DeviceCode="+deviceCode;
        new GetTempStatus(this).execute(connectionData);
    }

    /**
     * Function: RestoreTemperatureValues
     * Version: 1.0
     * Parameters: result
     * Returns: Void
     * Performs: Restores actual values for min and max temperatures, according to database
     * Created: 25/03/16
     * Creator: Lucas Gabriel N. Milagres
     */
    public void RestoreUserTemperatureValues(ArrayList<Map<String,Object>> result)
    {
        try {
            minTemp = (double) result.get(0).get("MinSet");
            maxTemp = (double) result.get(0).get("MaxSet");

            EditText minTempField = (EditText) findViewById(R.id.temperature_minTemp_EditText);
            minTempField.setText(Double.toString(minTemp));

            EditText maxTempField = (EditText) findViewById(R.id.temperature_maxTemp_EditText);
            maxTempField.setText(Double.toString(maxTemp));

            getChartPoints(result);
        }
        catch (Exception e)
        {
            Log.e("TAG:",e.getMessage());
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Function: getCharPoints
     * Version: 1.0
     * Parameters: result
     * Returns: Void
     * Performs: - Initializes lineChart object
     *          - Get the last 24 temperature data registered in the database and add to the array "chartPoints"
     *          - Sets lineChart labels and entries
     *          - Sets lineChart layout settings
     * Created: 25/03/16
     * Creator: Lucas Gabriel N. Milagres
     */
    private void getChartPoints(ArrayList<Map<String,Object>> result) {
        lineChart = (LineChart) findViewById(R.id.temperature_chart);

        chartPoints = new ArrayList<>();
        chartXLabels = new ArrayList<>();

        for (int i = result.size()-1; i >=0; i--) {
            chartPoints.add(new Entry((float) ((double)result.get(i).get("CurrentTemp")), result.size()-1-i));
            chartXLabels.add(result.get(i).get("Time").toString());
        }

        LineDataSet dataSet = new LineDataSet(chartPoints, getResources().getString(R.string.temperature_screen_chartLineLabel));
        dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        dataSet.setColor(R.color.temperature_chart_line_color);
        dataSet.setCircleColor(R.color.temperature_chart_circle_color);

        LineData data = new LineData(chartXLabels, dataSet);
        lineChart.setData(data);

        //Chart interaction settings
        lineChart.setTouchEnabled(true);

        //Chart layout settings
        lineChart.setDrawGridBackground(false);
        lineChart.setBackgroundColor(getResources().getColor(R.color.temperature_chart_background_color));

        lineChart.getXAxis().setEnabled(true);
        lineChart.getXAxis().setDrawGridLines(true);
        lineChart.getXAxis().setTextColor(getResources().getColor(R.color.temperature_chart_XLabels_color));
        lineChart.getXAxis().setGridColor(getResources().getColor(R.color.temperature_chart_XGrid_color));
        lineChart.getXAxis().setAxisLineColor(getResources().getColor(R.color.temperature_chart_XAxis_color));
        lineChart.getXAxis().setTextSize(getResources().getDimensionPixelSize(R.dimen.temperature_chart_XAxis_labels_dimension));
        lineChart.getXAxis().setGridLineWidth(getResources().getDimension(R.dimen.temperature_chart_XAxis_GridWidth_dimension));
        lineChart.getXAxis().setAxisLineWidth(getResources().getDimension(R.dimen.temperature_chart_XAxis_AxisWidth_dimension));
        lineChart.getAxisLeft().setEnabled(true);
        lineChart.getAxisLeft().setDrawGridLines(true);
        lineChart.getAxisLeft().setTextColor(getResources().getColor(R.color.temperature_chart_YLabels_color));
        lineChart.getAxisLeft().setGridColor(getResources().getColor(R.color.temperature_chart_YGrid_color));
        lineChart.getAxisLeft().setAxisLineColor(getResources().getColor(R.color.temperature_chart_YAxis_color));
        lineChart.getAxisLeft().setTextSize(getResources().getDimensionPixelSize(R.dimen.temperature_chart_YAxis_labels_dimension));
        lineChart.getAxisLeft().setGridLineWidth(getResources().getDimension(R.dimen.temperature_chart_YAxis_GridWidth_dimension));
        lineChart.getAxisLeft().setAxisLineWidth(getResources().getDimension(R.dimen.temperature_chart_YAxis_AxisWidth_dimension));
        lineChart.getAxisRight().setEnabled(false);

        LimitLine minLimit = new LimitLine((float) minTemp, getResources().getString(R.string.temperature_screen_chartMinLimit));
        minLimit.setLineColor(getResources().getColor(R.color.temperature_chart_minLimit_color));
        minLimit.setTextColor(getResources().getColor(R.color.temperature_chart_minLimit_label_color));
        minLimit.setLineWidth(getResources().getDimensionPixelSize(R.dimen.temperature_chart_minLimit_width_dimension));
        minLimit.setTextSize(getResources().getDimensionPixelSize(R.dimen.temperature_chart_minLimit_label_dimension));
        LimitLine maxLimit=new LimitLine((float) maxTemp,getResources().getString(R.string.temperature_screen_chartMaxLimit));
        maxLimit.setLineColor(getResources().getColor(R.color.temperature_chart_maxLimit_color));
        maxLimit.setTextColor(getResources().getColor(R.color.temperature_chart_maxLimit_label_color));
        maxLimit.setLineWidth(getResources().getDimensionPixelSize(R.dimen.temperature_chart_maxLimit_width_dimension));
        maxLimit.setTextSize(getResources().getDimensionPixelSize(R.dimen.temperature_chart_maxLimit_label_dimension));
        lineChart.getAxisLeft().addLimitLine(minLimit);
        lineChart.getAxisLeft().addLimitLine(maxLimit);

        //Refreshes chart
        lineChart.invalidate();

        //Gets the most recent temperature registered
        TextView currentTempTextView = (TextView)findViewById(R.id.temperature_currentTemp_TextView);
        currentTemp=chartPoints.get(chartPoints.size()-1).getVal();
        currentTempTextView.setText(Float.toString(currentTemp)+"ºC");

        //Gets the higher and lower temperatures registered
        float higherTemp=-900;
        float lowerTemp=900;
        for (Entry entry:chartPoints)
        {
            if(entry.getVal()>higherTemp)
                higherTemp=entry.getVal();
            if(entry.getVal()<lowerTemp)
                lowerTemp=entry.getVal();
        }
        TextView lowerTempTextView = (TextView)findViewById(R.id.temperature_minTemp2_TextView);
        lowerTempTextView.setText(Float.toString(lowerTemp)+"ºC");
        TextView higherTempTextView = (TextView)findViewById(R.id.temperature_maxTemp2_TextView);
        higherTempTextView.setText(Float.toString(higherTemp)+"ºC");
    }

        /**
         * Function: CommitTempData
         * Version: 1.0
         * Parameters: view
         * Returns: Void
         * Performs: Starts communication with database to commit data
         * Created: 15/05/16
         * Creator: Lucas Gabriel N. Milagres
         */
    public void CommitTempData(View view)
    {
        //Data validation
        String minTempField = ((EditText) findViewById(R.id.temperature_minTemp_EditText)).getText().toString();
        String maxTempField = ((EditText) findViewById(R.id.temperature_maxTemp_EditText)).getText().toString();

        if((!minTempField.equals(""))&&(!maxTempField.equals(""))) {
            //Creates connection
            String[] connectionData = new String[5];
            connectionData[0] = getResources().getString(R.string.server_set_temp_data);
            connectionData[1] = "?DeviceCode=" + deviceCode;
            connectionData[2] = "&MinSet=" + minTempField;
            connectionData[3] = "&MaxSet=" + maxTempField;
            connectionData[4]="&CurrentTemp="+Float.toString(currentTemp);
            new SetTempData(this).execute(connectionData);
        }
        else
        {
            Toast.makeText(this,getResources().getString(R.string.temp_empty_field),Toast.LENGTH_SHORT).show();
        }
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