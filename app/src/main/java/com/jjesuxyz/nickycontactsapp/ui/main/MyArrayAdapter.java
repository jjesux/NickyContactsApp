package com.jjesuxyz.nickycontactsapp.ui.main;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import com.google.android.material.tabs.TabLayout;
import com.jjesuxyz.nickycontactsapp.R;


/**
 * This ArrayAdapter is used to set the widget text that are part of each ListView row.  Each row
 * has two widgets one for contact name, one for the phone number. It also manages the ListView
 * rows background.
 */
public class MyArrayAdapter extends BaseAdapter {

    private ArrayList<String> arrLstIdNamePhone;
    private Cursor cursorCtcData;
    private LayoutInflater inflater;
    private Activity activity;
    private OnFragmentInteractionListener myListener;


    /**
     * Class constructor. This constructor receives a Cursor object holding the database contacts
     * list. It transfers that list into an ArrayList that is used in this class.
     *
     * @param parActivity Activity
     * @param parCursorCtcData Cursor
     */
    MyArrayAdapter(Activity parActivity, Cursor parCursorCtcData){
        super();

        cursorCtcData = parCursorCtcData;
        arrLstIdNamePhone = new ArrayList<>();
        String strIdNamePhoneRow;
                             // Tranferring contact data from Cursor to an ArrayList.
        while (cursorCtcData.moveToNext()) {
                             // Contact Id.
            strIdNamePhoneRow = String.valueOf(cursorCtcData.getInt(0)) + "&" +
                             // Contact name.
                                               cursorCtcData.getString(1) + "&" +
                             // Contact phone number.
                                               cursorCtcData.getString(2);
            arrLstIdNamePhone.add(strIdNamePhoneRow);
        }
                             // Initializing other needed objects
        activity = parActivity;
        inflater = activity.getLayoutInflater();
        myListener = (OnFragmentInteractionListener) activity;

    }   // End of MyArrayAdapter constructor.



    /**
     * This function returns the numbers of contacts to be shown on the ListView widget.
     *
     * @return int
     */
    @Override
    public int getCount() {
        return arrLstIdNamePhone.size();
    }



    /**
     * This function is used to get the contact data for each row on the ListView widget. This data
     * is held in an ArrayList.
     *
     * @param position int
     * @return Object
     */
    @Override
    public Object getItem(int position) {
        return arrLstIdNamePhone.get(position);
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
        final TextView txtvwPhone;

                             // Getting access to the layout file to create each row of the
                             // ListView widget.
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_row, parent, false);
        }
                             // Getting the contact information to fill each row on the ListView.
        final String strRowId = arrLstIdNamePhone.get(position).split("&")[0];
        final String strName = arrLstIdNamePhone.get(position).split("&")[1];
        final String strPhone = arrLstIdNamePhone.get(position).split("&")[2];
                             // Accessing the TextView to set the contact name.
        txtvwName = convertView.findViewById(R.id.txtVwRowNameId);
        txtvwName.setText(strName);
                             // Accessing the TextView to set the contact phone number.
        txtvwPhone = convertView.findViewById(R.id.txtVwRowPhoneId);
        txtvwPhone.setText(strPhone);
                             // Setting the row click listener.
        txtvwName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                             // Accessing the MainActivity class to set values on the FragAdd Fragment.
                myListener.onFragmentInteraction(1, strRowId, strName, strPhone);
                             // Changing tha tab view programmatically.
                TabLayout tabs = (TabLayout) activity.findViewById(R.id.tabs);
                tabs.getTabAt(1).select();
            }
        });                  //End of click listener.


        txtvwPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                             // Changing tab view programmatically.
                TabLayout tabs = (TabLayout) activity.findViewById(R.id.tabs);
                tabs.getTabAt(2).select();
                             // Accessing the MainActivity class to set values on the FragAdd Fragment.
                myListener.onFragmentInteraction(2, strRowId, strName, strPhone);
            }
        });                  //End of click listener.

                             //Getting the primary color
        int myColor = activity.getResources().getColor(R.color.colorPrimary);
        int r = Color.red(myColor);
        int g = Color.green(myColor);
        int b = Color.blue(myColor);
                             // Setting the background color of each row. It alternates colors.
        if (position % 2 == 0) {
            txtvwName.setBackgroundColor(Color.rgb(r, g, b));//10, 57, 143));
            txtvwPhone.setBackgroundColor(Color.rgb(r, g, b));//10, 57, 143));
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



}// End of MyArrayAdapter class


/***********************************END OF FILE MyArrayAdapter.java********************************/

