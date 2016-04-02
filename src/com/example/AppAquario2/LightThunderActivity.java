package com.example.AppAquario2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Class: LightThunderActivity
 * Version: 1.0
 * Parameters: - channelsNamesList
 *             - thunderEnabledArray
 * Return: - Activity result
 *         - thunderEnabledUpdateList
 * Perform: All actions in light_thunder layout
 * Created: 01/04/16
 * Creator: Lucas Gabriel N. Milagres
 */
public class LightThunderActivity extends Activity
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
        setContentView(R.layout.light_thunder);

        sendIntent = getIntent();
        ChannelNamesInitialize();
        CheckBoxesInitialize();
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
            TextView channelName1Field = (TextView)findViewById(R.id.light_thunder_channel1_name);
            TextView channelName2Field = (TextView)findViewById(R.id.light_thunder_channel2_name);
            TextView channelName3Field = (TextView)findViewById(R.id.light_thunder_channel3_name);
            TextView channelName4Field = (TextView)findViewById(R.id.light_thunder_channel4_name);
            TextView channelName5Field = (TextView)findViewById(R.id.light_thunder_channel5_name);

            //Sets actual channel names text
            channelName1Field.setText(channelsNamesList.get(0));
            channelName2Field.setText(channelsNamesList.get(1));
            channelName3Field.setText(channelsNamesList.get(2));
            channelName4Field.setText(channelsNamesList.get(3));
            channelName5Field.setText(channelsNamesList.get(4));
        }
    }

    /**
     * Function: CheckBoxesInitialize
     * Version: 1.0
     * Parameters: Void
     * Return: void
     * Perform: - Reads actual thunderEnabledArray
     *          - Identify thunder enabled channels
     * Created: 01/04/16
     * Creator: Lucas Gabriel N. Milagres
     */
    private void CheckBoxesInitialize()
    {
        //Reads actual channel mode list
        boolean[] thunderEnabledArray = sendIntent.getBooleanArrayExtra("thunderEnabledArray");

        //Sets actual enabled channels
        if ((thunderEnabledArray != null) && (thunderEnabledArray.length == 5))
        {
            //Seeks enabled thunder channels
            for (int count=0;count<=4;count++)
            {
                if(thunderEnabledArray[count])
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
     * Created: 01/04/16
     * Creator: Lucas Gabriel N. Milagres
     */
    private void changeEnabledState(int channelIndex, boolean checkBoxStatus)
    {
        //Finds all checkBoxes in the activity
        ArrayList<CheckBox> channelCheckBoxList = new ArrayList<>();
        channelCheckBoxList.add((CheckBox) findViewById(R.id.light_thunder_checkBox1));
        channelCheckBoxList.add((CheckBox) findViewById(R.id.light_thunder_checkBox2));
        channelCheckBoxList.add((CheckBox) findViewById(R.id.light_thunder_checkBox3));
        channelCheckBoxList.add((CheckBox) findViewById(R.id.light_thunder_checkBox4));
        channelCheckBoxList.add((CheckBox) findViewById(R.id.light_thunder_checkBox5));

        //Sets CheckBox status
        channelCheckBoxList.get(channelIndex).setChecked(checkBoxStatus);
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
