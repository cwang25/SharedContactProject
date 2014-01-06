package com.chihan.LsshClassmate;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;
 
/**
* This activity will be invoked by SmsManager via Pending intents defined in MainActivity
*/
public class SmsStatus extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 
        /** Getting bundle object attached to the intent */
        Bundle data = getIntent().getExtras();
 
        /** Getting the data "status" from the bundle object */
        int status = data.getInt("status");
 
        /** Getting the data "number" from the bundle object */
        String number = data.getString("number");
 
        if(status==MainActivity.SENT){    /** Detecting which intent invoked this execution of activity */
            Toast.makeText(getBaseContext(), "Message successfully Sent to " + number, Toast.LENGTH_SHORT).show();
        }else if(status==MainActivity.DELIVERED){    /** Detecting which intent invoked this execution of activity */
            Toast.makeText(getBaseContext(), "Message successfully Delivered to " + number, Toast.LENGTH_SHORT).show();
        }
 
        /** Finishes the execution of this activity */
        finish();
    }
}