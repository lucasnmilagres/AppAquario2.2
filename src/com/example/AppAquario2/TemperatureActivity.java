package com.example.AppAquario2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

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
    int minTemp;
    int maxTemp;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temperature);

        RestoreUserTemperatureValues();
        getChartPoints();
    }

    /**
     * Function: RestoreTemperatureValues
     * Version: 1.0
     * Parameters: Void
     * Returns: Void
     * Performs: Restores actual values for min and max temperatures, according to database
     * Created: 25/03/16
     * Creator: Lucas Gabriel N. Milagres
     */
    private void RestoreUserTemperatureValues()
    {
        minTemp=16;
        maxTemp=10;

        EditText minTempField=(EditText)findViewById(R.id.temperature_minTemp_EditText);
        minTempField.setText(Integer.toString(minTemp));

        EditText maxTempField=(EditText)findViewById(R.id.temperature_maxTemp_EditText);
        maxTempField.setText(Integer.toString(maxTemp));
    }

    /**
     * Function: getCharPoints
     * Version: 1.0
     * Parameters: Void
     * Returns: Void
     * Performs: - Initializes lineChart object
     *          - Get the last 24 temperature data registered in the database and add to the array "chartPoints"
     *          - Sets lineChart labels and entries
     *          - Sets lineChart layout settings
     * Created: 25/03/16
     * Creator: Lucas Gabriel N. Milagres
     */
    private void getChartPoints()
    {
        lineChart=(LineChart)findViewById(R.id.temperature_chart);

        chartPoints=new ArrayList<Entry>();
        chartPoints.add(new Entry(4f,0));
        chartPoints.add(new Entry(8f,1));
        chartPoints.add(new Entry(6f,2));
        chartPoints.add(new Entry(12f,3));
        chartPoints.add(new Entry(18f,4));
        chartPoints.add(new Entry(9f,5));

        chartXLabels=new ArrayList<String>();
        chartXLabels.add("January");
        chartXLabels.add("February");
        chartXLabels.add("March");
        chartXLabels.add("April");
        chartXLabels.add("May");
        chartXLabels.add("June");

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

        LimitLine minLimit = new LimitLine(minTemp, getResources().getString(R.string.temperature_screen_chartMinLimit));
        minLimit.setLineColor(getResources().getColor(R.color.temperature_chart_minLimit_color));
        minLimit.setTextColor(getResources().getColor(R.color.temperature_chart_minLimit_label_color));
        minLimit.setLineWidth(getResources().getDimensionPixelSize(R.dimen.temperature_chart_minLimit_width_dimension));
        minLimit.setTextSize(getResources().getDimensionPixelSize(R.dimen.temperature_chart_minLimit_label_dimension));
        LimitLine maxLimit=new LimitLine(maxTemp,getResources().getString(R.string.temperature_screen_chartMaxLimit));
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
        float currentTemp=chartPoints.get(chartPoints.size()-1).getVal();
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
     * Function: getSettingsChange
     * Version: 1.0
     * Parameters: Void
     * Return: change
     * Perform: Tests if there was some change in the minTemp or maxTemp
     * Created: 25/03/16
     * Creator: Lucas Gabriel N. Milagres
     */
    private boolean getSettingsChange()
    {
        EditText minTempField = (EditText) findViewById(R.id.temperature_minTemp_EditText);
        EditText maxTempField = (EditText) findViewById(R.id.temperature_maxTemp_EditText);

        if(!minTempField.getText().equals(Integer.toString(minTemp)))
            return true;
        if(!maxTempField.getText().equals(Integer.toString(maxTemp)))
            return true;

        return false;
    }

    /**
     * Function: changesSavingDialog
     * Version: 1.0
     * Parameters: Void
     * Return: void
     * Perform: Asks the user if he is sure about the changes he made
     * Created: 25/03/16
     * Creator: Lucas Gabriel N. Milagres
     */
    private void changesSavingDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.temperature_screen_dialogTitle));
        builder.setMessage(getResources().getString(R.string.temperature_screen_dialogMessage));
        builder.setPositiveButton(getResources().getString(R.string.temperature_screen_dialogPositiveButton), new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton(getResources().getString(R.string.temperature_screen_dialogNegativeButton), new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    /**
     * Function: onBackPressed
     * Version: 1.0
     * Parameters: Void
     * Return: Void
     * Perform: Finishes activity
     * Created: 25/03/16
     * Creator: Lucas Gabriel N. Milagres
     */
    @Override
    public void onBackPressed()
    {
        if(getSettingsChange())
            changesSavingDialog();

        super.onBackPressed();
        finish();
    }
}