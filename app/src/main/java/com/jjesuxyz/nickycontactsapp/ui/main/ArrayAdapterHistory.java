package com.jjesuxyz.nickycontactsapp.ui.main;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.jjesuxyz.nickycontactsapp.R;
import java.util.ArrayList;




/**
 * This ArrayAdapter is used to set the widget text that are part of each ListView row.  Each row
 * has three widgets one for contact name, one for date and time, and one for the phone number.
 * It also manages the ListView rows background.
 */
public class ArrayAdapterHistory extends BaseAdapter {

    private ArrayList<String> arrLstIdNameDTPhone;
    private Cursor cursorLogData;
    private LayoutInflater inflater;
    private Activity activity;
    private OnFragmentInteractionListener myListener;



    /**
     * Class constructor. This constructor receives a Cursor object holding the database phone
     * call list(incoming and outgoing.) It transfers that list into an ArrayList that is used
     * in this class.
     *
     * @param parActivity Activity
     * @param parCursorLogData Cursor
     */
    public ArrayAdapterHistory(Activity parActivity, Cursor parCursorLogData){
        super();

        cursorLogData = parCursorLogData;
        arrLstIdNameDTPhone = new ArrayList<>();
        String strRowIdNameDTPhone;
                             // Transferring phone call log data from Cursor to an ArrayList.
        while (cursorLogData.moveToNext()) {
                             // Log id.
            strRowIdNameDTPhone =   cursorLogData.getString(0) + "&" +
                             // Contact name
                                cursorLogData.getString(1) + "&" +
                             // Date the call was made or received.
                                cursorLogData.getString(2) + "&" +
                             // Phone number
                                cursorLogData.getString(3) + "&" +
                                cursorLogData.getString(4);

            arrLstIdNameDTPhone.add(strRowIdNameDTPhone);
        }
                             // Initializing other needed objects
        activity = parActivity;
        inflater = activity.getLayoutInflater();
        myListener = (OnFragmentInteractionListener) activity;

    }   // End of ArrayAdapterHistory function.



    /**
     * This function returns the numbers of phone call records to be shown on the ListView widget.
     *
     * @return int
     */
    @Override
    public int getCount() {
        return arrLstIdNameDTPhone.size();
    }



    /**
     * This function is used to get the phone call log data for each row on the ListView widget.
     * This data is held in an ArrayList.
     *
     * @param position int
     * @return Object
     */
    @Override
    public Object getItem(int position) {
        return arrLstIdNameDTPhone.get(position);
    }


    /**
     * This function is not really used by me on this project.
     *
     * @param position int
     * @return long
     */
    @Override
    public long getItemId(int position) {
        return 0;
    }



    /**
     * This function is used to inflate the layout file used to create each row on the ListView.
     *
     * @param position int
     * @param convertView View
     * @param parent ViewGroup
     * @return View
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final TextView txtvwName;
        final TextView txtvwDateTime;
        final TextView txtvwPhone;
        final TextView txtvwInOut;

                             // Getting access to the layout file to create each row of the
                             // ListView widget. Each row layout is set by the log_row file.
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.log_row, parent, false);
        }
                             // Getting the phone call information to fill each row on the ListView.
        final String strRowId = arrLstIdNameDTPhone.get(position).split("&")[0];
        final String strRowName = arrLstIdNameDTPhone.get(position).split("&")[1];
        final String strRowDateTime = arrLstIdNameDTPhone.get(position).split("&")[2];
        final String strRowPhone = arrLstIdNameDTPhone.get(position).split("&")[3];
        final String strRowInOut = arrLstIdNameDTPhone.get(position).split("&")[4];

                             // Accessing the TextView to set the contact name.
        txtvwName =(TextView) convertView.findViewById(R.id.txtvwLogRowNameId);
        txtvwName.setText(strRowName);
                             // Accessing the TextView to set the date and time when the phone
                             // call was made or received.
        txtvwDateTime = (TextView) convertView.findViewById(R.id.txtvwLogRowDateTimeId);
        txtvwDateTime.setText(strRowDateTime);
                             // Accessing the TextView to set the phone number.
        txtvwPhone = (TextView) convertView.findViewById(R.id.txtvwLogRowPhoneId);
        txtvwPhone.setText(strRowPhone);

        txtvwInOut = (TextView) convertView.findViewById(R.id.txtvwLogRowInOutId);
        txtvwInOut.setText(strRowInOut);

                             // Setting TextView click listener
        txtvwName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                             // Changing tab view programmatically.
                TabLayout tabs = (TabLayout) activity.findViewById(R.id.tabs);
                tabs.getTabAt(1).select();
                             // Accessing the MainActivity class to set values on the FragAdd Fragment.
                myListener.onFragmentInteraction(1, strRowId, strRowName, strRowPhone);
            }
        });                  //End of click listener.

                             // Setting TextView click listener
        txtvwPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strRowId = "-1";
                             // Changing tab view programmatically.
                TabLayout tabs = (TabLayout) activity.findViewById(R.id.tabs);
                tabs.getTabAt(2).select();
                             // Accessing the MainActivity class to set values on the FragAdd Fragment.
                myListener.onFragmentInteraction(2, strRowId, strRowName, strRowPhone);
            }
        });                  //End of click listener.


                             //Getting the primary color
        int myColor = activity.getResources().getColor(R.color.colorPrimary);
        int r = Color.red(myColor);
        int g = Color.green(myColor);
        int b = Color.blue(myColor);
                             // Setting the background color of each row. It alternates colors.
        if (position % 2 == 0) {
            txtvwDateTime.setBackgroundColor(Color.rgb(r, g, b));//10, 57, 143));
            txtvwName.setBackgroundColor(Color.rgb(r, g, b));//10, 57, 143));
            txtvwPhone.setBackgroundColor(Color.rgb(r, g, b));//10, 57, 143));
            txtvwInOut.setBackgroundColor(Color.rgb(r, g, b));//10, 57, 143));
        }
                             // Returning the new row.
        return convertView;

    }   // End of getView function



    /**
     * This interface must be implemented by the MainActivity class to allow interaction with
     * this adapter class and to establish communication with other fragments that are used in this
     * application.
     *
     * It was created by AS.
     */
    public interface OnFragmentInteractionListener {

        /**
         * This function is implemented in the MainActivity class. It is used to send data to the
         * MainActivity class, and then this data may be sent to other fragments using other medium.
         *
         * @param tabIndex int
         * @param strId String
         * @param strName String
         * @param strPhone String
         */
        void onFragmentInteraction(int tabIndex, String strId, String strName, String strPhone);

    }   // End of OnFragmentInteractionListener interface.



}// End of ArrayAdapterHistory class


/********************************END OF FILE ArrayAdapterHistory.java******************************/

