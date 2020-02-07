package com.jjesuxyz.nickycontactsapp.ui.main;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.jjesuxyz.nickycontactsapp.CtcData.DbSQLHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ListenInCallReceiver extends BroadcastReceiver {


    Context context;
    int counter = 0;
    private static String lastState = "IDLE";

    @Override
    public void onReceive(Context context, Intent intent) {

        this.context = context;
        counter = counter + 1;
        int rowLogId = -1;


        String contactName = "Unknown";
        String strPhoneState = intent.getExtras().getString(TelephonyManager.EXTRA_STATE).toString();
        String strPhoneNumber = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER).toString();

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yy/MM/dd-HH:mm:ss");
        String dateTime = formatter.format(date);

        if (strPhoneState.equals(TelephonyManager.EXTRA_STATE_OFFHOOK) && lastState.equals("IDLE")) {
            Log.d("NICKY", "ES OUTGOING CALL" );
            Log.d("NICKY", "Phone State: " + strPhoneState + "  -  Phone Number: " + strPhoneNumber);
            lastState = TelephonyManager.EXTRA_STATE_OFFHOOK;
        }
        else if (strPhoneState.equals(TelephonyManager.EXTRA_STATE_RINGING) && lastState.equals("IDLE")) {
            Log.d("NICKY", "ES UNA INCOMING CALL");
            Log.d("NICKY", "Phone State: " + strPhoneState + "  -  Phone Number: " + strPhoneNumber);
            lastState = TelephonyManager.EXTRA_STATE_RINGING;
            DbSQLHelper db = new DbSQLHelper(context);
            Cursor cursor = db.checkIfRecordExist(strPhoneNumber);
            if (cursor != null && cursor.getCount() >= 1) {
                Log.d("NICKY", "Row Count: " + String.valueOf(cursor.getCount()));
                cursor.moveToFirst();
                rowLogId = cursor.getInt(0);
                contactName = cursor.getString(1);
                cursor.close();
                Log.d("NICKY", "Contact Name: " + contactName);
            }
            else {
                contactName = "Unknown";
                rowLogId = -1;
                Log.d("NICKY", "Incoming call Phone number does not existe in contac list." );
                Log.d("NICKY", "Incoming call from " + contactName );
            }

            db.insertLogDateTime(rowLogId, contactName, dateTime, strPhoneNumber, "In");
        }
        else if (strPhoneState.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
            Log.d("NICKY", "PHONE CALL ENDED");
            lastState = TelephonyManager.EXTRA_STATE_IDLE;
        }


        Toast.makeText(context, "Number: " + strPhoneNumber + "  -  State: " + strPhoneState, Toast.LENGTH_LONG).show();
/*
        DbSQLHelper db = new DbSQLHelper(context);
        Cursor cursor = db.checkIfRecordExist(strPhoneNumber);
        if (cursor != null && cursor.getCount() >= 1) {
            Log.d("NICKY", "Row Count: " + String.valueOf(cursor.getCount()));
            cursor.moveToFirst();
            contactName = cursor.getString(0);
            cursor.close();
            Log.d("NICKY", "Contact Name: " + contactName);
        }
        else {
            Log.d("NICKY", "Incoming call Phone number does not existe in contac list." );
        }*/

    }
}
