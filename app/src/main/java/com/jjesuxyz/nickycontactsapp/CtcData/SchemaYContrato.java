package com.jjesuxyz.nickycontactsapp.CtcData;

import android.provider.BaseColumns;


/**
 * This class is used on this project to define the Sqlite database, to define tables and some
 * Sqlite commands to query the database with create, insert, update, select, delete commands.
 */
final class SchemaYContrato {

    private SchemaYContrato(){}

    /**
     * Inner static class to able to modify or change the data structure and query commands using
     * only this inner class.
     */
    static class TableInfo implements BaseColumns {

        static final int    DB_VERSION = 1;
        static final String DB_NAME = "Contactos.db";

                             // Defining table ame
        static final String TABLE_CONTACT = "Ctcs";
                             // Defining table columns
        static final String COL_CONTACT_NAME = "Nombre";
        static final String COL_PHONE_NUMBER = "Number";
                             // Creating the contact list table
        static final String CREATE_TABLE_CONTACT = "CREATE TABLE " + TABLE_CONTACT + "(" +
                                                             _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                             COL_CONTACT_NAME + " TEXT, " +
                                                             COL_PHONE_NUMBER + " TEXT)";

                             // Defining table ame
        static final String TABLE_LOGS = "Logs";
                             // Defining table columns
        static final String COL_LOG_ID = "Log_Id";
        static final String COL_LOG_CONTACT_NAME = "LogName";
        static final String COL_LOG_DATE_TIME = "LogDateTime";
        static final String COL_LOG_PHONE_NUMBER = "LogPhoneNumber";
        static final String COL_LOG_INOUT = "InOut";
                             // Creating the log history list table
        static final String CREATE_TABLE_LOGS = "CREATE TABLE " + TABLE_LOGS + "(" +
                                                           COL_LOG_ID + " INTEGER, " +
                                                           COL_LOG_CONTACT_NAME + " TEXT, " +
                                                           COL_LOG_DATE_TIME + " TEXT, " +
                                                           COL_LOG_PHONE_NUMBER + " TEXT, " +
                                                           COL_LOG_INOUT + " TEXT)";


                             // Defiing some commands to query the database
        static final String DELETE_TABLE_CONTACT = "DROP TABLE IF EXISTS " + TABLE_CONTACT;

        static final String DELETE_TABLE_LOGS = "DROP TABLE IF EXIST " + TABLE_LOGS;

        static final String SELECT_ALL = "SELECT * FROM " + TABLE_CONTACT + " ORDER BY " + COL_CONTACT_NAME;

        static final String SELECT_CTC_LOGS = "SELECT " +
                                                     COL_LOG_ID + ", " +
                                                     COL_LOG_CONTACT_NAME + ", " +
                                                     COL_LOG_DATE_TIME + ", " +
                                                     COL_LOG_PHONE_NUMBER + ", " +
                                                     COL_LOG_INOUT +
                                                     " FROM " + TABLE_LOGS;

        static final String SELECT_ID = "SELECT _ID FROM " + TABLE_CONTACT + " WHERE " + COL_PHONE_NUMBER + "=?";




        static final String EXIST_RECORD = "SELECT _ID, " + COL_CONTACT_NAME + " FROM " + TABLE_CONTACT + " WHERE " + COL_PHONE_NUMBER + "=?";




    }   // End of TableInfo class

}   // End of SchemaYContrato class


/***********************************END OF FILE SchemaYContrato.java*******************************/