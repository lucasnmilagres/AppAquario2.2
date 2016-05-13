package com.example.AppAquario2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Class: RatingActivity
 * Version: 1.0
 * Parameters: void
 * Return: Activity result
 * Perform: All actions in rating layout
 * Created: 16/04/16
 * Creator: Lucas Gabriel N. Milagres
 */
public class RatingActivity extends Activity
{
    LinearLayout parentLayout;

    // Variables
    boolean clicked=false;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.rating);
            parentLayout=(LinearLayout)findViewById(R.id.rating_parent_layout);
        }
        catch (Exception e)
        {
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
            finish();
        }
    }

    /**
     * Function: cleanText
     * Version: 1.0
     * Parameters: EditText
     * Return: void
     * Perform: Cleans default text
     * Created: 16/04/16
     * Creator: Lucas Gabriel N. Milagres
     */
    public void cleanText(View view)
    {
        if(!clicked)
        {
            ((EditText)view).setText("");
            clicked=true;
        }
    }

    /**
     * Function: SendRating
     * Version: 1.0
     * Parameters: "Cancel" button
     * Return: void
     * Perform: - Sends rating to server;
     *          - Shows thanks Toast;
     *          - Sends Ok result to parent;
     *          - Finishes Activity.
     * Created: 10/04/16
     * Creator: Lucas Gabriel N. Milagres
     */
    public void SendRating(View view)
    {
        // Sends rating to server(missing)

        // Shows thanks Toast
        Toast.makeText(getApplicationContext(), getResources().getString(R.string.RatingThanksMsg), Toast.LENGTH_SHORT).show();

        // Sends Ok result to parent
        Intent intent =new Intent();
        setResult(RESULT_OK,intent);

        // Finishes Activity
        parentLayout.removeAllViews();
        finish();
    }

    /**
     * Function: Cancel
     * Version: 1.0
     * Parameters: "Cancel" button
     * Return: void
     * Perform: - Sends Cancel result to parent;
     *          - Finishes Activity.
     * Created: 10/04/16
     * Creator: Lucas Gabriel N. Milagres
     */
    public void Cancel(View view)
    {
        // Sends Cancel result to parent
        Intent intent =new Intent();
        setResult(RESULT_CANCELED,intent);

        // Finishes Activity
        parentLayout.removeAllViews();
        finish();
    }
}
