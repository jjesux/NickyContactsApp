package com.jjesuxyz.nickycontactsapp.ui.main;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.jjesuxyz.nickycontactsapp.CtcData.DbSQLHelper;
import com.jjesuxyz.nickycontactsapp.R;

import java.util.ArrayList;



/**
 * This fragment is used to create a ListView to show all the phone calls (incoming and outgoing)
 * history that are in the database Logs table.
 * This class contains an interface to communicate with the MainActivity class. So the MainActivity
 * class must implement that interface.
 * It also uses an external ArrayAdapter class to create each row in the ListView widget. It also
 * uses the fragment factory method to create objects of this class. In this project these objects
 * are created in the class SectionPagerAdapter.
 * The arguments that it gets are not really used for something useful.
 */
public class FragHistory extends ListFragment implements AdapterView.OnItemClickListener {

    private OnFragmentInteractionListener mListener;
    private DbSQLHelper dbSQLHelper;
    private ArrayList<String> arrayListName;
    private ArrayList<String> arrayListPhone;




    /**
     * Class constructor. It does nothing.
     */
    public FragHistory() {
        // Required empty public constructor
    }



    /**
     * Use this factory method to create a new instance of this fragment using the provided
     * parameters.
     *
     * @return A new instance of fragment FragHistory.
     */
    public static FragHistory newInstance(String parFragName) {
        FragHistory fragment = new FragHistory();
        Bundle args = new Bundle();
                             // This argument is not used after the fragment is created.
        args.putString("FRAG INFO", parFragName);
        fragment.setArguments(args);
        return fragment;
    }



    /**
     * This function is used to create two ArrayList to hold contact names and phone numbers.
     *
     * @param savedInstanceState Bundle
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        arrayListName = new ArrayList<>();
        arrayListPhone = new ArrayList<>();
    }



    /**
     * This function is used to inflate the layout file for this fragment.
     *
     * @param inflater LayoutInflater
     * @param container ViewGroup
     * @param savedInstanceState Bundle
     * @return View
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
                             // Inflate the layout file for this fragment.
        return inflater.inflate(R.layout.frag_history, container, false);
    }



    /**
     * This function is used to create the database if it does not exist yet, and to get the list
     * of all contacts from the Contacts table. The cursor returned from the database query is
     * passed to the ArrayAdapter to create the ListView used in this fragment.
     *
     * @param savedInstanceState Bundle
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
                             // Accessing database to get all contacts from Contacts table
        dbSQLHelper = new DbSQLHelper(getContext());
        Cursor cursorListaCtcs = dbSQLHelper.getAllCallLogsData();
                             // Creating the ArrayAdapter to create the ListView
        ArrayAdapterHistory arrayAdapterHistory = new ArrayAdapterHistory(getActivity(), cursorListaCtcs);
                             // Setting the ArrayAdapter for this fragment
        setListAdapter(arrayAdapterHistory);
        getListView().setOnItemClickListener(this);
    }



    /**
     * This function was created by AS.
     *
     * @param view View
     * @param savedInstanceState Bundle
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }



    /**
     * This function was created by AS.
     */
    @Override
    public void onResume() {
        super.onResume();
    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(3, "0", "Name", "Phone");
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
     * This callback function is not used yet in this project.
     *
     * @param parent AdapterView
     * @param view View
     * @param position int
     * @param id int
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }



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


}   //  End of FragHistory class


/************************************END OF FILE FragHistory.java**********************************/


