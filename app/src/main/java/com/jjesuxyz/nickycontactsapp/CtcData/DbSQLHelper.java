package com.jjesuxyz.nickycontactsapp.CtcData;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;



/*
 * This class is used to manage the sqlite database used in this application. It allows other
 * classes or fragments to create, upgrade, delete the database. It also allows the fragments to
 * insert, update and delete records from tha database tables.
 */
public class DbSQLHelper extends SQLiteOpenHelper {

    private Context context;


    /**
     * Class constructor used to get a reference to the context object.
     *
     * @param context Context
     */
    public DbSQLHelper(@Nullable Context context) {
        super(context, SchemaYContrato.TableInfo.DB_NAME, null, SchemaYContrato.TableInfo.DB_VERSION);
        this.context = context;
    }



    /**
     * Function to create database tables.
     *
     * @param db SQLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SchemaYContrato.TableInfo.CREATE_TABLE_CONTACT);
        db.execSQL((SchemaYContrato.TableInfo.CREATE_TABLE_LOGS));
    }



    //

    /**
     * Function to upgrade to a new version of the databse by deleting the databse tables and then
     * creating them again.
     *
     * @param db SQLiteDatabase
     * @param oldVersion int
     * @param newVersion int
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SchemaYContrato.TableInfo.DELETE_TABLE_CONTACT);
        db.execSQL(SchemaYContrato.TableInfo.DELETE_TABLE_LOGS);
        onCreate(db);
    }



    /**
     * This function is not used so far in this project.
     *
     * @param db SQLiteDatabase
     * @param oldVersion int
     * @param newVersion int
     */
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }




    /**
     * This function is used to insert or update records in the databse. It gets a reference to
     * access the database and then it insertes or update records accordingly. This function may
     * insert records into the Contacts table but also can update records in the Logs table.
     *
     * @param rowId int
     * @param name String
     * @param phoneNumber String
     * @return boolean
     */
    public boolean insertContactData(int rowId, String name, String phoneNumber) {
                             // Getting database reference in write mode
        SQLiteDatabase db = this.getWritableDatabase();
                             // Setting values to insert into the contacts database table
        ContentValues contactValues = new ContentValues();
        contactValues.put(SchemaYContrato.TableInfo.COL_CONTACT_NAME, name);
        contactValues.put(SchemaYContrato.TableInfo.COL_PHONE_NUMBER, phoneNumber);

        long inserUpdateResult = -1;
                             // Insertion is performed when the rowId received as a parameter is
                             // negative.
        if (rowId < 0) {
            inserUpdateResult = db.insert(SchemaYContrato.TableInfo.TABLE_CONTACT, null, contactValues);

            if (inserUpdateResult >= 0 && rowId <= -10) {
                             // Accessing the database to get the contact id from the contact table
                             // to update a records with that id on the logs table.
                Cursor cursor = db.rawQuery(SchemaYContrato.TableInfo.SELECT_ID, new String[]{phoneNumber});
                cursor.moveToFirst();
                             // Getting the contact row id from the last record inserted into the
                             // contact list table.
                int myID = cursor.getInt(0);
                cursor.close();
                             // Setting the values to update a record/row on the log table.
                ContentValues logValues = new ContentValues();
                logValues.put(SchemaYContrato.TableInfo.COL_LOG_ID, myID);
                logValues.put(SchemaYContrato.TableInfo.COL_LOG_CONTACT_NAME, name);
                logValues.put(SchemaYContrato.TableInfo.COL_LOG_PHONE_NUMBER, phoneNumber);
                             // Updating the log table with data from the contact list table.
                inserUpdateResult = db.update(SchemaYContrato.TableInfo.TABLE_LOGS,
                                               logValues,
                                               SchemaYContrato.TableInfo.COL_LOG_PHONE_NUMBER + "=?",
                                               new String[]{phoneNumber});
            }
        }
                             // Updating is performed when the rowId is grater than zero. It means
                             // that that record exist in the database table.
        else {
            inserUpdateResult = db.update(SchemaYContrato.TableInfo.TABLE_CONTACT,
                                           contactValues,
                                "_id=?",
                                           new String[]{String.valueOf(rowId)});
            if (inserUpdateResult <= 0) {
                Toast.makeText(context, "There is an ERROR updating Contacts table", Toast.LENGTH_SHORT).show();
            }
                             // Only the name column is going to be updated on the log table
            ContentValues logValues = new ContentValues();
            logValues.put(SchemaYContrato.TableInfo.COL_LOG_CONTACT_NAME, name);
            inserUpdateResult = db.update(SchemaYContrato.TableInfo.TABLE_LOGS,
                                           logValues,
                                           SchemaYContrato.TableInfo.COL_LOG_ID + "=?",
                                            new String[]{String.valueOf(rowId)});
            if (inserUpdateResult <= 0) {
                Toast.makeText(context, "There is an ERROR updating Logs table", Toast.LENGTH_SHORT).show();
            }
        }
                             // Finally checking if there was any error during the insert or update
                             // process.
        if (inserUpdateResult <= -1) {
            return false;
        }
        else {
            return true;
        }
    }   // End of insertContactData function





    /**
     * This function is used to insert records into the database Logs table. It gets a reference to
     * access the database and then it inserts the records accordingly. This function insert records
     * only into the Logs table.
     *
     * @param ctcRowId int
     * @param contactName String
     * @param dateTime String
     * @param phoneNumber String
     * @return boolean
     */
    public boolean insertLogDateTime(int ctcRowId, String contactName, String dateTime, String phoneNumber, String incomingOutgoing){
                             // Getting a reference to the database.
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
                             // Setting values to be inserted into the Log table.
        cv.put(SchemaYContrato.TableInfo.COL_LOG_ID, ctcRowId);
        cv.put(SchemaYContrato.TableInfo.COL_LOG_CONTACT_NAME, contactName);
        cv.put(SchemaYContrato.TableInfo.COL_LOG_DATE_TIME, dateTime);
        cv.put(SchemaYContrato.TableInfo.COL_LOG_PHONE_NUMBER, phoneNumber);
        cv.put(SchemaYContrato.TableInfo.COL_LOG_INOUT, incomingOutgoing);
                             // Inserting new record into the Logs table.
        long insertLogResult =  db.insert(SchemaYContrato.TableInfo.TABLE_LOGS, null, cv);
                             // Checking if the was any error during the insertion process.
        if (insertLogResult <= 0) {
            return false;
        }
        else {
            return true;
        }
    }   // End of insertLogDateTime function



    /**
     * This function access the database to get all records from the Contact list table. It returns
     * a cursor with all the records it retrieved.
     *
     * @return Cursor
     */
    public Cursor getAllContactsData() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery(SchemaYContrato.TableInfo.SELECT_ALL, null);
    }


    /**
     * This function access the database to get all records from the Logs list table. It returns
     * a cursor with all the records it retrieved.
     *
     * @return Cursor
     */
    public Cursor getAllCallLogsData() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(SchemaYContrato.TableInfo.SELECT_CTC_LOGS, null);
    }



    public Cursor checkIfRecordExist(String phoneNumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SchemaYContrato.TableInfo.EXIST_RECORD, new String[]{phoneNumber});
        return cursor;
    }


}   // End of DbSQLHelper class


/************************************END OF FILE DbSQLHelper.java**********************************/




