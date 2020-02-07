
/*
 * This application is to allow me to save my contacts and to invoke the android device app to
 * make a call. It also records all incoming and outgoing phone calls.
 */


package com.jjesuxyz.nickycontactsapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Toast;

import com.jjesuxyz.nickycontactsapp.ui.main.ArrayAdapterHistory;
import com.jjesuxyz.nickycontactsapp.ui.main.FragAdd;
import com.jjesuxyz.nickycontactsapp.ui.main.FragCtcList;
import com.jjesuxyz.nickycontactsapp.ui.main.FragDial;
import com.jjesuxyz.nickycontactsapp.ui.main.FragHistory;
import com.jjesuxyz.nickycontactsapp.ui.main.MyArrayAdapter;
import com.jjesuxyz.nickycontactsapp.ui.main.SectionsPagerAdapter;


/**
 * MainActivity class is the entry point of this app. It is used to create the main UI, ask
 * for permissions to make a call and to access the phone state data.
 * Many of this code was created by Android Studio.
 *
 */
public class MainActivity extends AppCompatActivity  implements
                                                        FragAdd.OnFragmentInteractionListener,
                                                        FragCtcList.OnFragmentInteractionListener,
                                                        FragDial.OnFragmentInteractionListener,
                                                        FragHistory.OnFragmentInteractionListener,
                                                        MyArrayAdapter.OnFragmentInteractionListener,
                                                        ArrayAdapterHistory.OnFragmentInteractionListener {

    /**
     * This function is called to initialize adapter, tabs, and to ask user permission to make
     * phone calls and to read the phone state to detect incoming calls.
     *
     * It was created by AS and modified by me.
     *
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

                             //Code created by A. S. to set this application like a tabbed app.
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());

        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);

                             // Getting the tab widget
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

                             // Finding the floating widget using its id
        FloatingActionButton fab = findViewById(R.id.fab);
                             // Making this app to react when the floating widget is clicked
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


                             // Asking permission to access phone state.
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE)) {
                Toast.makeText(this, "Explaining WHY Permission To Read Phone State Is Needed", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, "Really Asking Permission To Read Phone States Changes", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 0);
            }
        }

                             // Asking permission to make a phone calls.
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)) {
                Toast.makeText(this, "Explaining WHY Permission To Make Phone Calls Is Needed", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, "Realyy Asking Permission to Make Phone Calls", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 0);
            }
        }




    }   // End of oonCreate function



    /**
     * This function is used to allow the communication between fragments. All fragments can
     * communicate between them throughout the MainActivity class. This communication is mainly to
     * set and get text on/from EditText and TextView widgets, and pass some data to synchronize
     * the flow of data between them, and the Sqlite database.
     *
     * @param tabIndex int
     * @param strId String
     * @param parName String
     * @param parPhone String
     */
    @Override
    public void onFragmentInteraction(int tabIndex, String strId, String parName, String parPhone){

        int rowId = Integer.parseInt(strId);
                             // Defining strings to get reference to the fragments that are active
                             // (displaying UI.)
        String tagAdd = "android:switcher:" + R.id.view_pager + ":" + 1;
        String tagCall = "android:switcher:" + R.id.view_pager + ":" + 2;

                             // Switch to allow the flow of data from one fragment to another fragment.
        switch (tabIndex) {
                             // Sending data to the add new contact fragment
            case 1:
                             // Getting a reference to access the add contact fragment
                FragAdd fAdd = (FragAdd) getSupportFragmentManager().findFragmentByTag(tagAdd);
                if (fAdd != null) {
                    fAdd.setNamePhoneFuncion(rowId, parName, parPhone);
                }
                break;
                             // Sending data to the dial fragment to make a call
            case 2:
                             // Getting a reference to access the dial fragment to make a call
                FragDial fDial = (FragDial) getSupportFragmentManager().findFragmentByTag(tagCall);
                if (fDial != null) {
                    fDial.setNamePhoneFuncion(rowId, parName, parPhone);
                }
                else
                    Toast.makeText(this, "NULNULNULNULLLL", Toast.LENGTH_SHORT).show();
                break;
        }

    }   //  End of onFragmentInteraction function


}   //  End of MainActivity class


/***********************************END OF FILE MainActivity.java**********************************/