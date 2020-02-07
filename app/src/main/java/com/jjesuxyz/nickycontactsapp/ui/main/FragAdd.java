package com.jjesuxyz.nickycontactsapp.ui.main;

import android.content.Context;
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




/**
 * This fragment is used to create the user interface to allow users to create new contacts, and
 * to save the new contact data in the database. Contacts can also be edited changing the name or
 * phone number. This change of contact name or phone number can also be replicate on the
 * records held in the phone call log table.
 *
 * This class contains an interface to communicate with the MainActivity class. So the MainActivity
 * class must implement that interface.
 *
 * It also uses the fragment factory method to create objects of this class. In this project
 * objects of this type are created in the class SectionPagerAdapter.
 *
 * The arguments that it gets are not really used for something useful.
 */
public class FragAdd extends Fragment {

    private OnFragmentInteractionListener mListener;


    // This variable is used to know if the user is creating a new contact or editing contact data.
    // -1 means new contact.
    // > 0 means edit contact data.
    private int rowId = -1;

    private TextView txtvwNuevoEdit;
    private EditText edtxtName;
    private EditText edtxtNumber;




    /**
     * Class constructor. It does nothing.
     */
    public FragAdd() {
        // Required empty public constructor
    }



    /**
     * Use this factory method to create a new instance of this fragment using the provided
     * parameters.
     *
     * @return A new instance of fragment FragHistory.
     */
    public static FragAdd newInstance(String parFragName) {
        FragAdd fragment = new FragAdd();
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

        rowId = -1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frag_add, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

                             // Initializing EditText with references to the UI EditText widgets.
        txtvwNuevoEdit = view.findViewById(R.id.txtvwDlFrgNmId);
        edtxtName = view.findViewById(R.id.edtxtCtcNameId);
        edtxtNumber = view.findViewById(R.id.edtxtPhNumId);
                             // Initializing button with references to the UI buttons
        Button btnCancelar = view.findViewById(R.id.btnCancelarSaveId);
        Button btnSaveCtcData = view.findViewById(R.id.btnSaveAddId);

                             // Setting button click listener to save contact data.
        btnSaveCtcData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                             // Getting access to the local database.
                DbSQLHelper dbhelper = new DbSQLHelper(getContext());
                             // Getting text from the EdtText widgets.
                String ctcName = edtxtName.getText().toString();
                String ctcPhone = edtxtNumber.getText().toString();
                             // Checking that name and phone fields are not empty.
                if (ctcName.length() <= 0 || ctcPhone.length() <= 0) {
                    Toast.makeText(getContext(), "Name OR Phone Number cannot be Empty.",
                                                  Toast.LENGTH_SHORT).show();
                }
                             // Block to remove any non digit character from the phone number field.
                else {
                             //Removing white spaces from phone number entered.
                    String strNoWhiteSpace = ctcPhone.replaceAll("\\s", "");
                    char[] arrNumber = new char[10];
                    int counter = 0;
                             //Removing any non-digit character from phone number entered
                    if (strNoWhiteSpace.length() >= 10) {
                        for (int i = 0; i < strNoWhiteSpace.length(); i++) {
                            //Exiting for loop when 10 digit characters have been accepted
                            if (counter > 9) {
                                break;
                            }
                            else {
                                //Populating char array with digit chars only. These digit chars
                                // will be inserted into the database.
                                if (Character.isDigit(strNoWhiteSpace.charAt(i))) {
                                    arrNumber[counter] = strNoWhiteSpace.charAt(i);
                                    counter = counter + 1;
                                }
                            }
                        }

                            //Log.d("NICKY", "PARECE SER que phone number is OK: " + String.valueOf(counter));
                            // Converting the array holding the phone number into a String.
                        String strPhoneNumberNuevo = new String(arrNumber);
                             // Warning user about the phone number being too short or too large.
                        if (counter - 1 != 9) {
                            Toast.makeText(getContext(), "Phone number seems to be too short or too large.",
                                                              Toast.LENGTH_SHORT).show();
                        }
                             // In this else-block the new contact record is inserted into the Db.
                        else {
                             // Inserting contact data into the database.
                            boolean result = dbhelper.insertContactData(rowId, ctcName, strPhoneNumberNuevo);
                             // Checking if database insertion was OK
                            if (result == true) {
                                Toast.makeText(getContext(), "DB Insercion-Update OK", Toast.LENGTH_SHORT).show();
                                // Cleaning EditText fields.
                                edtxtName.setText("");
                                edtxtNumber.setText("");
                                txtvwNuevoEdit.setText("Agregar Contacto Nuevo");
                            }
                            else {
                                Toast.makeText(getContext(), "DB Insertion-Update Failed.", Toast.LENGTH_SHORT).show();
                            }
                             // Setting rowId to its original value.
                            rowId = -1;
                        }
                    }
                    else {
                             // Letting know user the phone number is too short.
                        Toast.makeText(getContext(), "Phone number is too short", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });                  // End of button save click listener

                             // Setting button click listener to cancel contact creation or update.
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mListener.onFragmentInteraction(1, "0", "", "");
                             // Setting everything to original values.
                rowId = -1;
                txtvwNuevoEdit.setText("Agregar Contacto Nuevo");
                edtxtName.setText("");
                edtxtNumber.setText("");

                             //Swapping tab from tab Add to List of Contacts tab.
                TabLayout tabs = (TabLayout) getActivity().findViewById(R.id.tabs);
                tabs.getTabAt(0).select();
            }
        });                  // End of button cancel click listener


    }   // End of onViewCreated function.



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(1, "0", "Name", "Phone");
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
     * In this project the data come from the FragCtc Fragment of from FragHistory Fragment.
     *
     * @param parRowId int
     * @param parName String
     * @param parPhone String
     */
    public void setNamePhoneFuncion(int parRowId, String parName, String parPhone) {

        String errorWarning = "Error @ FragAdd->setNamePhoneFuncion->EditView:null";
        // Setting rowId to know that new contact insertion can from dial fragment.
        if (parName.equals(getResources().getString(R.string.str_person_to_call))) {
            rowId = -20;
        }
        else {
            rowId = parRowId;
        }
                             // Setting the text to let user kno if it is new contact creation
                             // of update.
        txtvwNuevoEdit.setText(getResources().getText(R.string.str_edit_ctc));

        if (edtxtName != null) {
            edtxtName.setText(parName);
        }
        else {
            Toast.makeText(getContext(), errorWarning, Toast.LENGTH_SHORT).show();
        }

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

}   //  End of FragAdd class


/**************************************END OF FILE FragAdd.java************************************/



