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
 * Class: LightRoutineActivity
 * Version: 1.0
 * Parameters: - routineList
 *             - channelsNamesList
 *             - lightModeList
 *             - lightProgramList
 * Return: - Activity result
 *         - lightUpdateList
 * Perform: All actions in light_routine layout
 * Created: 29/03/16
 * Creator: Lucas Gabriel N. Milagres
 */
public class LightRoutineActivity extends Activity
{
    //Intents
    Intent sendIntent;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.light_routine);

        sendIntent = getIntent();
        ChannelNamesInitialize();
        ChannelSpinnerInitialize();
        ChannelEnabledInitialize();
    }

    /**
     * Function: ChannelNamesInitialize
     * Version: 1.0
     * Parameters: Void
     * Return: void
     * Perform: - Reads actual channel names list
     *          - Writes actual channel names in TextView fields
     * Created: 29/03/16
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
            TextView channelName1Field = (TextView)findViewById(R.id.light_routine_channel1_name);
            TextView channelName2Field = (TextView)findViewById(R.id.light_routine_channel2_name);
            TextView channelName3Field = (TextView)findViewById(R.id.light_routine_channel3_name);
            TextView channelName4Field = (TextView)findViewById(R.id.light_routine_channel4_name);
            TextView channelName5Field = (TextView)findViewById(R.id.light_routine_channel5_name);

            //Sets actual channel names text
            channelName1Field.setText(channelsNamesList.get(0));
            channelName2Field.setText(channelsNamesList.get(1));
            channelName3Field.setText(channelsNamesList.get(2));
            channelName4Field.setText(channelsNamesList.get(3));
            channelName5Field.setText(channelsNamesList.get(4));
        }
    }

    /**
     * Function: ChannelSpinnerInitialize
     * Version: 1.0
     * Parameters: Void
     * Return: void
     * Perform: - Reads routineList
     *          - Sets spinner' dropdown to actual routineList values
     *          - Avoids using obsolete programs
     *          - Selects spinner item
     * Created: 29/03/16
     * Creator: Lucas Gabriel N. Milagres
     */
    private void ChannelSpinnerInitialize()
    {
        //Reads routineList
        ArrayList<String> routineList=sendIntent.getStringArrayListExtra("routineList");

        //Reads lightProgramList
        ArrayList<String> lightProgramList=sendIntent.getStringArrayListExtra("lightProgramList");

        //Sets spinners adapter (layout and strings settings)
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, routineList);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        //Finds all spinners in the activity
        ArrayList<Spinner> channelSpinnerList=new ArrayList<>();
        channelSpinnerList.add((Spinner) findViewById(R.id.light_routine_spinner1));
        channelSpinnerList.add((Spinner) findViewById(R.id.light_routine_spinner2));
        channelSpinnerList.add((Spinner) findViewById(R.id.light_routine_spinner3));
        channelSpinnerList.add((Spinner) findViewById(R.id.light_routine_spinner4));
        channelSpinnerList.add((Spinner) findViewById(R.id.light_routine_spinner5));

        for(int count=0; count<=4;count++)
        {
            //Sets adapter
            channelSpinnerList.get(count).setAdapter(adapter);

            //Avoids using obsolete programs
            if(!routineList.contains(lightProgramList.get(count)))
                lightProgramList.set(count,getResources().getString(R.string.Unset));

            //Selects spinner item
            if(channelSpinnerList.size()==5)
                channelSpinnerList.get(count).setSelection(routineList.indexOf(lightProgramList.get(count)));
            else
                channelSpinnerList.get(count).setSelection(0);
        }
    }

    /**
     * Function: ChannelEnabledInitialize
     * Version: 1.0
     * Parameters: Void
     * Return: void
     * Perform: - Reads actual channel mode list
     *          - Identify RoutineMode channels
     * Created: 29/03/16
     * Creator: Lucas Gabriel N. Milagres
     */
    private void ChannelEnabledInitialize()
    {
        //Reads actual channel mode list
        ArrayList<String> lightModeList = sendIntent.getStringArrayListExtra("lightModeList");

        //Sets actual enabled channels
        if ((lightModeList != null) && (lightModeList.size() == 5))
        {
            //Seeks RoutineMode channels
            for (int count=0;count<=4;count++)
            {
                if(lightModeList.get(count).equals(getResources().getString(R.string.RoutineMode)))
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
     * Perform: - Changes selected CheckBox checked status
     *          - Changes enabled state of spinner
     *          - Reads lightProgramList
     *          - Sets selection to <Unset>
     * Created: 29/03/16
     * Creator: Lucas Gabriel N. Milagres
     */
    private void changeEnabledState(int channelIndex, boolean checkBoxStatus)
    {
        //Finds all checkBoxes in the activity
        ArrayList<CheckBox> channelCheckBoxList = new ArrayList<>();
        channelCheckBoxList.add((CheckBox) findViewById(R.id.light_routine_checkBox1));
        channelCheckBoxList.add((CheckBox) findViewById(R.id.light_routine_checkBox2));
        channelCheckBoxList.add((CheckBox) findViewById(R.id.light_routine_checkBox3));
        channelCheckBoxList.add((CheckBox) findViewById(R.id.light_routine_checkBox4));
        channelCheckBoxList.add((CheckBox) findViewById(R.id.light_routine_checkBox5));

        //Finds all spinners in the activity
        ArrayList<Spinner> channelSpinnerList=new ArrayList<>();
        channelSpinnerList.add((Spinner) findViewById(R.id.light_routine_spinner1));
        channelSpinnerList.add((Spinner) findViewById(R.id.light_routine_spinner2));
        channelSpinnerList.add((Spinner) findViewById(R.id.light_routine_spinner3));
        channelSpinnerList.add((Spinner) findViewById(R.id.light_routine_spinner4));
        channelSpinnerList.add((Spinner) findViewById(R.id.light_routine_spinner5));

        //Sets CheckBox status
        channelCheckBoxList.get(channelIndex).setChecked(checkBoxStatus);

        //Sets enabled status
        channelSpinnerList.get(channelIndex).setEnabled(checkBoxStatus);

        //Sets selection to <Unset>
        if(checkBoxStatus==false)
            channelSpinnerList.get(channelIndex).setSelection(0);
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
