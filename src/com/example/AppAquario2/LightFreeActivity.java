package com.example.AppAquario2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;

/**
 * Class: LightFreeActivity
 * Version: 1.0
 * Parameters: - colorsList
 *             - channelsNamesList
 *             - lightLastValueList
 *             - lightModeList
 * Return: - Activity result
 *         - lightUpdateList
 * Perform: All actions in light_free layout
 * Created: 27/03/16
 * Creator: Lucas Gabriel N. Milagres
 */
public class LightFreeActivity extends Activity
{
    //Intents
    Intent sendIntent;

    //Array Lists
    ArrayList<Integer> lightLastValueList;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.light_free);

        sendIntent = getIntent();
        ChannelNamesInitialize();
        ChannelSeekBarInitialize();
        ChannelEnabledInitialize();
    }

    /**
     * Function: ChannelNamesInitialize
     * Version: 1.0
     * Parameters: Void
     * Return: void
     * Perform: - Reads actual channel names list
     *          - Writes actual channel names in TextView fields
     * Created: 28/03/16
     * Creator: Lucas Gabriel N. Milagres
     */
    private void ChannelNamesInitialize()
    {
        //Reads actual channel names list
        ArrayList<String> channelsNamesList = sendIntent.getStringArrayListExtra("channelsNamesList");

        //Writes actual channel names in EditText fields.If channelNamesList is null, keep default channels names
        if((channelsNamesList != null)&&(channelsNamesList.size()==5))
        {
            //Finds all channel name fields in the activity
            TextView channelName1Field = (TextView)findViewById(R.id.light_free_channel1_name);
            TextView channelName2Field = (TextView)findViewById(R.id.light_free_channel2_name);
            TextView channelName3Field = (TextView)findViewById(R.id.light_free_channel3_name);
            TextView channelName4Field = (TextView)findViewById(R.id.light_free_channel4_name);
            TextView channelName5Field = (TextView)findViewById(R.id.light_free_channel5_name);

            //Sets actual channel names text
            channelName1Field.setText(channelsNamesList.get(0));
            channelName2Field.setText(channelsNamesList.get(1));
            channelName3Field.setText(channelsNamesList.get(2));
            channelName4Field.setText(channelsNamesList.get(3));
            channelName5Field.setText(channelsNamesList.get(4));
        }
    }

    /**
     * Function: ChannelSeekBarInitialize
     * Version: 1.0
     * Parameters: Void
     * Return: void
     * Perform:- Reads actual channel values list
     *          - Sets seekbars' progress to actual channel values
     * Created: 28/03/16
     * Creator: Lucas Gabriel N. Milagres
     */
    private void ChannelSeekBarInitialize() {
        //Finds all seekbars in the activity
        ArrayList<SeekBar> channelSeekBarList=new ArrayList<>();
        channelSeekBarList.add((SeekBar) findViewById(R.id.light_free_seekBar1));
        channelSeekBarList.add((SeekBar) findViewById(R.id.light_free_seekBar2));
        channelSeekBarList.add((SeekBar) findViewById(R.id.light_free_seekBar3));
        channelSeekBarList.add((SeekBar) findViewById(R.id.light_free_seekBar4));
        channelSeekBarList.add((SeekBar) findViewById(R.id.light_free_seekBar5));

        //Reads actual channel values list
        lightLastValueList = sendIntent.getIntegerArrayListExtra("lightLastValueList");

        //Sets progress to actual values
        if ((lightLastValueList != null) && (lightLastValueList.size() == 5))
            //Sets seekBars' actual progress
            for(int count=0; count<=4;count++)
                channelSeekBarList.get(count).setProgress(lightLastValueList.get(count));
    }

    /**
     * Function: ChannelEnabledInitialize
     * Version: 1.0
     * Parameters: Void
     * Return: void
     * Perform: - Reads actual channel mode list
     *          - Identify FreeMode channels
     * Created: 28/03/16
     * Creator: Lucas Gabriel N. Milagres
     */
    private void ChannelEnabledInitialize()
    {
        //Reads actual channel mode list
        ArrayList<String> lightModeList = sendIntent.getStringArrayListExtra("lightModeList");

        //Sets actual enabled channels
        if ((lightModeList != null) && (lightModeList.size() == 5))
        {
            //Seeks FreeMode channels
            for (int count=0;count<=4;count++)
            {
                if(lightModeList.get(count).equals(getResources().getString(R.string.FreeMode)))
                    changeEnabledState(count, true);
                else
                    changeEnabledState(count, false);
            }
        }
    }

    /**
     * Function: changeEnabledState
     * Version: 1.0
     * Parameters: - channelIndex
     *             - checkBoxStatus
     * Return: Void
     * Perform: - Reads actual channel colors list
     *          - Changes selected CheckBox checked status
     *          - Changes enabled state of seekBar
     *          - Sets seekbars' colors to actual channel colors
     * Created: 28/03/16
     * Creator: Lucas Gabriel N. Milagres
     */
    private void changeEnabledState(int channelIndex, boolean checkBoxStatus)
    {
        //Reads actual channel colors list
        ArrayList<Integer> colorsList = sendIntent.getIntegerArrayListExtra("colorsList");

        //Finds all checkBoxes in the activity
        ArrayList<CheckBox> channelCheckBoxList = new ArrayList<>();
        channelCheckBoxList.add((CheckBox) findViewById(R.id.light_free_checkBox1));
        channelCheckBoxList.add((CheckBox) findViewById(R.id.light_free_checkBox2));
        channelCheckBoxList.add((CheckBox) findViewById(R.id.light_free_checkBox3));
        channelCheckBoxList.add((CheckBox) findViewById(R.id.light_free_checkBox4));
        channelCheckBoxList.add((CheckBox) findViewById(R.id.light_free_checkBox5));

        //Finds all seekbars in the activity
        ArrayList<SeekBar> channelSeekBarList=new ArrayList<>();
        channelSeekBarList.add((SeekBar) findViewById(R.id.light_free_seekBar1));
        channelSeekBarList.add((SeekBar) findViewById(R.id.light_free_seekBar2));
        channelSeekBarList.add((SeekBar) findViewById(R.id.light_free_seekBar3));
        channelSeekBarList.add((SeekBar) findViewById(R.id.light_free_seekBar4));
        channelSeekBarList.add((SeekBar) findViewById(R.id.light_free_seekBar5));

        //Sets CheckBox status
        channelCheckBoxList.get(channelIndex).setChecked(checkBoxStatus);

        //Sets enabled status
        channelSeekBarList.get(channelIndex).setEnabled(checkBoxStatus);

        //Sets seekbar to disabled or enabled actual channel colors.If colorList is null, keep default colors
        if(!checkBoxStatus)
            changeSeekbarColor(channelSeekBarList.get(channelIndex), getResources().getColor(R.color.progressBar_progress_disabled_color));
        else if ((colorsList != null) && (colorsList.size() == 5))
            changeSeekbarColor(channelSeekBarList.get(channelIndex), colorsList.get(channelIndex));
    }

    /**
     * Function: changeSeekbarColor
     * Version: 2.0
     * Updated Date: 28/03/16
     * Updated By: Lucas Gabriel N. Milagres
     * Parameters: - Seekbar
     *             - Gradient endColor
     * Return: Void
     * Perform: Changes seekBar default color
     * Created: 30/09/15
     * Creator: Dilroop Singh
     */
        private void changeSeekbarColor(SeekBar s, int endColor)
        {
            PorterDuff.Mode mMode = PorterDuff.Mode.SRC_ATOP;

            LayerDrawable layerDrawable = (LayerDrawable) s.getProgressDrawable();
            Drawable progress = layerDrawable.findDrawableByLayerId(android.R.id.progress);
            Drawable secondary = layerDrawable.findDrawableByLayerId(android.R.id.secondaryProgress);
            Drawable th = s.getThumb();

            // Setting colors
            progress.setColorFilter(endColor,mMode);
            secondary.setColorFilter(endColor,mMode);
            th.setColorFilter(endColor,mMode);

            // Applying Tinted Drawables
            layerDrawable.setDrawableByLayerId(android.R.id.progress, progress);
            layerDrawable.setDrawableByLayerId(android.R.id.secondaryProgress, secondary);
        }

    /**
     * Function: changeChecked
     * Version: 1.0
     * Parameters: One of the CheckBoxes
     * Return: Void
     * Perform: Calls changeEnabledState
     * Created: 28/03/16
     * Creator: Lucas Gabriel N. Milagres
     */
    public void changeChecked(View view)
    {
        changeEnabledState(Integer.parseInt(view.getTag().toString()), ((CheckBox)view).isChecked());
    }

    /**
     * Function: finishActivity
     * Version: 1.0
     * Parameters: "Cancel" button
     * Return: RESULT_CANCELED
     * Perform: - Returns null to the parent activity
     *          - Finishes the activity
     * Created: 27/03/16
     * Creator: Lucas Gabriel N. Milagres
     */
    public void finishActivity(View view)
    {
        //Returns null to the parent activity
        Intent resultIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, resultIntent);

        //finishes the activity
        finish();
    }
}
