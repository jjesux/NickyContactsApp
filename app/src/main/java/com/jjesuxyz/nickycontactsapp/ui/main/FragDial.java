package com.jjesuxyz.nickycontactsapp.ui.main;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.jjesuxyz.nickycontactsapp.CtcData.DbSQLHelper;
import com.jjesuxyz.nickycontactsapp.R;

import java.text.SimpleDateFormat;
import java.util.Date;




/**
 * This fragment is used to create the user interface to allow users to call people, and
 * to save the dialed data in the database Logs table. Dialed data can be entered directly by the
 * user; in this case the people to be called is defined as unknown. This data can also come from
 * the contact ListView or from Logs ListView clicking on the phone number field.
 *
 * After clicking the make phone call button the data is inserted into the database Logs table.
 *
 * This class contains an interface to communicate with the MainActivity class. So the MainActivity
 * class must implement that interface.
 *
 * It also uses the fragment factory method to create objects of this class. In this project
 * objects of this type are created in the class SectionPagerAdapter.
 *
 * The arguments that it gets are not really used for something useful.
 */
public class FragDial extends Fragment {

    private OnFragmentInteractionListener mListener;

    private EditText edtxtNumber;
    private TextView txtvwCallingTo;
    // This variable is used to know if the user to be called is already a contact or an unknown
    // contact.
    // -1 means new contact.
    // > 0 means the contact to call is already a contact.
    private int rowCtcId = -1;




    /**
     * Class constructor. It does nothing.
     */
    public FragDial() {
        // Required empty public constructor
    }



    /**
     * Use this factory method to create a new instance of this fragment using the provided
     * parameters.
     *
     * @return A new instance of fragment FragHistory.
     */
    public static FragDial newInstance(String parFragName) {
        FragDial fragment = new FragDial();
        Bundle args = new Bundle();
        args.putString("FRAG INFO", parFragName);
        fragment.setArguments(args);
        return fragment;
    }



    /**
     * This function is used to initialize the global rowId variable to -1. This variable is used
     * to know if the user is trying to created a new contact or to edit a contact that is already
     * in the list of contact.
     * If rowId is equal to negative one then the user is trying to create a new contact.
     * If rowId is greater that zero then the user is trying to edit a contact data.
     *
     * @param savedInstanceState Bundle
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rowCtcId = -1;
    }


    /**
     * This function is used to inflate the layout file to create the UI for this fragment.
     *
     * @param inflater LayoutInflater
     * @param container ViewGroup
     * @param savedInstanceState Bundle
     * @return View
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
                             // Inflating the layout for this fragment
        return inflater.inflate(R.layout.frag_dial, container, false);
    }


    /**
     *
     *
     * @param view View
     * @param savedInstanceState Bundle
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

                             // Button used to make a call by clicking on it
        Button btnMakeCall = view.findViewById(R.id.btnAppCallId);
        btnMakeCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Getting person name and phone number to call.
                String numberToCall = edtxtNumber.getText().toString();
                String nameToCall = txtvwCallingTo.getText().toString();
                String incomingOutgoing = "In";

                if (!numberToCall.equals("") && numberToCall.length() >= 2) {

                    // Just for debugging for now.
                    Toast.makeText(getContext(),
                            "Calling number: " + numberToCall,
                            Toast.LENGTH_SHORT).show();
                    // Setting the intent to start device application to make the call.
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + numberToCall));
                    // Starting device application to make the call.
                    startActivity(intent);
                    // Getting date and time the phone call was make.
                    Date date = new Date();
                    SimpleDateFormat formatter = new SimpleDateFormat("yy/MM/dd-HH:mm:ss");
                    String dateTime = formatter.format(date);
                    // Inserting phone call data into the database Logs table.
                    DbSQLHelper db = new DbSQLHelper(getContext());
                    boolean insertResult = db.insertLogDateTime(rowCtcId, nameToCall, dateTime, numberToCall, "Out");
                    if (!insertResult) {
                        Toast.makeText(getContext(),
                                "There was an error inserting phone call data",
                                Toast.LENGTH_SHORT).show();
                    }
                    // Resetting UI widgets to default values.
                    edtxtNumber.setText("");
                    txtvwCallingTo.setText(getResources().getString(R.string.str_person_to_call));
                    rowCtcId = -1;
                }
                else {
                    Log.d("NICKY", "You HAVE TO ENTER A NUMBER TO CALL");
                    Toast.makeText(getContext(),
                            "PLEASE ENTER A NUMBER TO CALL. " + numberToCall,
                            Toast.LENGTH_SHORT).show();
                }
            }

        });                  // End of the button onClickListener

                             // Button to cancel the make a call action.
        Button btnCancel = view.findViewById(R.id.btnCancelDialId);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                             // Setting phone number EditText widget to default value.
                edtxtNumber.setText("");
                             // Switching the make phone call tab to contact list tab.
                TabLayout tabs = (TabLayout) getActivity().findViewById(R.id.tabs);
                tabs.getTabAt(0).select();
            }
        });                  // End of click listener
                             // Setting the EditText widget to default value when clicking on it.
        edtxtNumber = (EditText) view.findViewById(R.id.edtxtGetPhoneNumId);
        edtxtNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                edtxtNumber.setText("");
            }
        });
                             // Setting the person to call TextView to default value.
        txtvwCallingTo = (TextView) view.findViewById(R.id.txtvwCallWhoId);

    }   // End of onViewCreated function.



    /**
     * This function is not implemented in this project yet.
     *
     * @param uri Uri
     */
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(2, "0", "Name", "Phone");
        }
    }



    /**
     * This function is used to initialize the listener object to access the MainActivity class.
     * It was created by AS.
     *
     * @param context Context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }



    /**
     * This function is used only to destroy the object listener to access the MainActivity class.
     * it was created by AS.
     */
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    /**
     * This function is used to receive data from other fragments. This communication is through
     * the MainActivity class.
     * In this project the data row id, contact name and contact phone number come from the FragCtc
     * and FragHistory Fragment.
     * Fragments send data first to the MainActivity and from that class are transferred to this
     * fragment function.
     *
     * @param parRowId int
     * @param parName String
     * @param parPhone String
     */
    public void setNamePhoneFuncion(int parRowId, String parName, String parPhone) {

        String errorWarning = "Error @ FragAdd->setNamePhoneFuncion->EditView:null";
                             // This global variable is used in other functions to know if the
                             // person to be called is already a contact or an unknown person.
        rowCtcId = parRowId;
                             // Setting contact name to call to.
        if (txtvwCallingTo != null) {
            txtvwCallingTo.setText(parName);
        }
        else {
            Toast.makeText(getContext(), errorWarning, Toast.LENGTH_SHORT).show();
        }
                             // Setting the phone number to be dialed
        if (edtxtNumber != null) {
            edtxtNumber.setText(parPhone);
        }
        else {
            Toast.makeText(getContext(), errorWarning, Toast.LENGTH_SHORT).show();
        }

    }   // End of setNamePhoneFuncion function



    /**
     * This interface must be implemented by activities that contain this fragment to allow an
     * interaction in this fragment to be communicated to the activity and potentially other
     * fragments contained in that activity.
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
         * @param parName String
         * @param parPhone String
         */
        void onFragmentInteraction(int tabIndex, String strId, String parName, String parPhone);
    }


}   //  End of FragDial class


/**************************************END OF FILE FragDial.java***********************************/


