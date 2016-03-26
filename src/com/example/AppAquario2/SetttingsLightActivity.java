package com.example.AppAquario2;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import petrov.kristiyan.colorpicker.ColorPicker;

import java.util.ArrayList;

/**
 * Class: SettingsLightActivity
 * Version: 1.0
 * Parameters: - colorsList
 *             - channelsNamesList
 * Return: - Activity result
 *         - colorList updated
 *         - channelNamesList updated
 * Perform: All actions in settings_edit_channel_labels layout
 * Created: 25/03/16
 * Creator: Lucas Gabriel N. Milagres
 */
public class SetttingsLightActivity extends Activity
{
    //Array Lists
    ArrayList<Integer> colorsList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_edit_channel_labels);

        ChannelNamesInitialize();
        ChannelColorsInitialize();
    }

    /**
     * Function: ChannelNamesInitialize
     * Version: 1.0
     * Parameters: Void
     * Return: void
     * Perform: - Reads actual channel names list
     *          - Writes actual channel names in EditText fields
     * Created: 25/03/16
     * Creator: Lucas Gabriel N. Milagres
     */
    private void ChannelNamesInitialize()
    {
        //Reads actual channel names list
        Intent sendIntent = getIntent();
        ArrayList<String> channelsNamesList = sendIntent.getStringArrayListExtra("channelsNamesList");

        //Writes actual channel names in EditText fields.If channelNamesList is null, keep default channels names
        if((channelsNamesList != null)&&(channelsNamesList.size()==5))
        {
            //Finds all channel name fields in the activity
            EditText channelName1Field = (EditText)findViewById(R.id.settings_light_channel1_name);
            EditText channelName2Field = (EditText)findViewById(R.id.settings_light_channel2_name);
            EditText channelName3Field = (EditText)findViewById(R.id.settings_light_channel3_name);
            EditText channelName4Field = (EditText)findViewById(R.id.settings_light_channel4_name);
            EditText channelName5Field = (EditText)findViewById(R.id.settings_light_channel5_name);

            //Sets actual channel names text
            channelName1Field.setText(channelsNamesList.get(0));
            channelName2Field.setText(channelsNamesList.get(1));
            channelName3Field.setText(channelsNamesList.get(2));
            channelName4Field.setText(channelsNamesList.get(3));
            channelName5Field.setText(channelsNamesList.get(4));
        }
    }

    /**
     * Function: ChannelColorsInitialize
     * Version: 1.0
     * Parameters: Void
     * Return: void
     * Perform: - Reads actual channel colors list
     *          - Sets backgrounds to actual channel colors
     * Created: 25/03/16
     * Creator: Lucas Gabriel N. Milagres
     */
    private void ChannelColorsInitialize()
    {
        //Reads actual channel colors list
        Intent sendIntent = getIntent();
        colorsList = sendIntent.getIntegerArrayListExtra("colorsList");

        //Sets backgrounds to actual channel colors.If colorList is null, keep default channels backgrounds
        if((colorsList != null)&&(colorsList.size()==5))
        {
            //Finds all channel name fields in the activity
            ImageButton channelColor1Field = (ImageButton)findViewById(R.id.settings_light_color_button1);
            ImageButton channelColor2Field = (ImageButton)findViewById(R.id.settings_light_color_button2);
            ImageButton channelColor3Field = (ImageButton)findViewById(R.id.settings_light_color_button3);
            ImageButton channelColor4Field = (ImageButton)findViewById(R.id.settings_light_color_button4);
            ImageButton channelColor5Field = (ImageButton)findViewById(R.id.settings_light_color_button5);

            //Sets actual channel names text
            channelColor1Field.setBackgroundColor(colorsList.get(0));
            channelColor2Field.setBackgroundColor(colorsList.get(1));
            channelColor3Field.setBackgroundColor(colorsList.get(2));
            channelColor4Field.setBackgroundColor(colorsList.get(3));
            channelColor5Field.setBackgroundColor(colorsList.get(4));
        }
        else //Adds default background color to colorList
        {
            colorsList.clear();
            colorsList.add(getResources().getColor(R.color.channel1_color));
            colorsList.add(getResources().getColor(R.color.channel2_color));
            colorsList.add(getResources().getColor(R.color.channel3_color));
            colorsList.add(getResources().getColor(R.color.channel4_color));
            colorsList.add(getResources().getColor(R.color.channel5_color));
        }
    }

    /**
     * Function: colorChange
     * Version: 1.0
     * Parameters: one of the "settings_light_color_button" buttons
     * Return: void
     * Perform: - Sends color index to showColorPicker function
     *          - Calls showColorPicker function
     * Created: 26/03/16
     * Creator: Lucas Gabriel N. Milagres
     */
    public void colorChange(View view)
    {
        showColorPicker(Integer.parseInt(view.getTag().toString()), view.getId());
    }

    /**
     * Function: showColorPicker
     * Version: 1.0
     * Parameters: - Changed color index
     *             - Changed color id
     * Return: void
     * Perform: - Sets ColorPickerDialog settings
     *          - Updates colorList to actual values
     *          - Updates colorButton background to actual value
     *          - Shows ColorPickerDialog
     * Created: 26/03/16
     * Creator: Lucas Gabriel N. Milagres
     */
    private void showColorPicker(int colorIndex, int id)
    {
        //Sets ColorPickerDialog settings
        final ColorPicker colorPicker = new ColorPicker(this);
        colorPicker.setTitle(getResources().getString(R.string.settings_light_colorPicker_title));
        colorPicker.setRoundButton(true);
        colorPicker.setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
            @Override
            public void onChooseColor(int position,int color) {
                //Updates colorList to actual values
                colorsList.set(colorIndex, color);

                //Updates colorButton background to actual value
                ImageButton imageButton = (ImageButton)findViewById(id);
                imageButton.setBackgroundColor(color);
            }
        });

        //Shows ColorPickerDialog
        colorPicker.show();
    }

    /**
     * Function: acceptChanges
     * Version: 1.0
     * Parameters: "Ok" button
     * Return: - RESULT_OK
     *         - New channel names
     *         - New channel colors
     * Perform: - Writes changes done by the user inside the database
     *          - Returns new strings and colors values to the parent activity
     *          - Finishes Activity
     * Created: 25/03/16
     * Creator: Lucas Gabriel N. Milagres
     */
    public void acceptChanges(View view)
    {
        //Missing to write changes in the database

        //Returns new strings and colors values to the parent activity
        //Finds all channel name fields in the activity
        EditText channelName1Field = (EditText)findViewById(R.id.settings_light_channel1_name);
        EditText channelName2Field = (EditText)findViewById(R.id.settings_light_channel2_name);
        EditText channelName3Field = (EditText)findViewById(R.id.settings_light_channel3_name);
        EditText channelName4Field = (EditText)findViewById(R.id.settings_light_channel4_name);
        EditText channelName5Field = (EditText)findViewById(R.id.settings_light_channel5_name);

        //Creates stringsList
        ArrayList<String> channelsNamesList = new ArrayList<>();
        channelsNamesList.add(channelName1Field.getText().toString());
        channelsNamesList.add(channelName2Field.getText().toString());
        channelsNamesList.add(channelName3Field.getText().toString());
        channelsNamesList.add(channelName4Field.getText().toString());
        channelsNamesList.add(channelName5Field.getText().toString());

        //Adds results to the intent
        Intent resultIntent = new Intent();
        resultIntent.putExtra("channelsNamesList", channelsNamesList);
        resultIntent.putExtra("colorsList", colorsList);
        setResult(Activity.RESULT_OK, resultIntent);

        //Finishes activity
        finish();
    }

    /**
     * Function: finishActivity
     * Version: 1.0
     * Parameters: "Cancel" button
     * Return: RESULT_CANCELED
     * Perform: - Returns null to the parent activity
     *          - Finishes the activity
     * Created: 25/03/16
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
