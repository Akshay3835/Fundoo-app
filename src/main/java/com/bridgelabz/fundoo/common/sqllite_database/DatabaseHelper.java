package com.bridgelabz.fundoo.common.sqllite_database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.bridgelabz.fundoo.add_label_page.model.LabelDatabaseManager.LABEL_TABLE_COLUMN_NAME;
import static com.bridgelabz.fundoo.add_label_page.model.LabelDatabaseManager.LABEL_TABLE_COLUMN_USERID;
import static com.bridgelabz.fundoo.add_label_page.model.LabelDatabaseManager.LABEL_TABLE_NAME;
import static com.bridgelabz.fundoo.add_note_page.data_manager.NoteDatabaseManager.NOTE_TABLE_COLUMN_ARCHIVE;
import static com.bridgelabz.fundoo.add_note_page.data_manager.NoteDatabaseManager.NOTE_TABLE_COLUMN_PINED;
import static com.bridgelabz.fundoo.add_note_page.data_manager.NoteDatabaseManager.NOTE_TABLE_COLUMN_REMINDER;
import static com.bridgelabz.fundoo.add_note_page.data_manager.NoteDatabaseManager.NOTE_TABLE_COLUMN_COLOR;
import static com.bridgelabz.fundoo.add_note_page.data_manager.NoteDatabaseManager.NOTE_TABLE_COLUMN_DES;
import static com.bridgelabz.fundoo.add_note_page.data_manager.NoteDatabaseManager.NOTE_TABLE_COLUMN_TITLE;
import static com.bridgelabz.fundoo.add_note_page.data_manager.NoteDatabaseManager.NOTE_TABLE_NAME;
import static com.bridgelabz.fundoo.add_note_page.data_manager.NoteDatabaseManager.NOTE_TABLE_NOTE_USER_ID;
import static com.bridgelabz.fundoo.LoginSignUp.database_manager.UserDatabaseManager.USER_TABLE_FIRST_NAME;
import static com.bridgelabz.fundoo.LoginSignUp.database_manager.UserDatabaseManager.USER_TABLE_LAST_NAME;
import static com.bridgelabz.fundoo.LoginSignUp.database_manager.UserDatabaseManager.USER_TABLE_NAME;
import static com.bridgelabz.fundoo.LoginSignUp.database_manager.UserDatabaseManager.USER_TABLE_USER_EMAIL;
import static com.bridgelabz.fundoo.LoginSignUp.database_manager.UserDatabaseManager.USER_TABLE_USER_ID;
import static com.bridgelabz.fundoo.LoginSignUp.database_manager.UserDatabaseManager.USER_TABLE_USERNAME;
import static com.bridgelabz.fundoo.LoginSignUp.database_manager.UserDatabaseManager.USER_TABLE_USER_PASS;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = DatabaseHelper.class.getName();
    public static final String DATABASE_NAME = "FUNDOOAPP.db";
    public static final String NOTE_TABLE_COLUMN_ID = "ID" ;
    public static final String LABEL_TABLE_COLUMN_ID = "ID";
    private static final int DATABASE_VERSION = 1;



    private SQLiteDatabase sqLiteDatabase;

    private static final String CREATE_USER_TABLE_QUERY =
            "CREATE TABLE " + USER_TABLE_NAME
                    + "(" + USER_TABLE_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + USER_TABLE_FIRST_NAME + " TEXT,"
                    + USER_TABLE_LAST_NAME + " TEXT,"
                    + USER_TABLE_USER_EMAIL + " TEXT, "
                    + USER_TABLE_USER_PASS + " TEXT ,"
                    + USER_TABLE_USERNAME + " TEXT "
                     + ")";

    private static final String CREATE_NOTE_TABLE_QUERY =
            " CREATE TABLE " + NOTE_TABLE_NAME
                    + "(" + NOTE_TABLE_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + NOTE_TABLE_COLUMN_TITLE + " TEXT,"
                    + NOTE_TABLE_COLUMN_DES + " TEXT,"
                    + NOTE_TABLE_NOTE_USER_ID + " TEXT,"
                    + NOTE_TABLE_COLUMN_COLOR + " TEXT,"
                    + NOTE_TABLE_COLUMN_ARCHIVE + " INTEGER,"
                    + NOTE_TABLE_COLUMN_REMINDER + " TEXT,"
                    + NOTE_TABLE_COLUMN_PINED + " INTEGER" + ")";

    public static final String CREATE_LABEL_TABLE_QUERY = " CREATE TABLE " + LABEL_TABLE_NAME
            + "(" + LABEL_TABLE_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
            + LABEL_TABLE_COLUMN_NAME + " TEXT,"
            + LABEL_TABLE_COLUMN_USERID + " TEXT" + ")";


    private static DatabaseHelper databaseHelperInstance;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    public static DatabaseHelper getInstance(Context context) {

        if (databaseHelperInstance == null) {
            synchronized (DatabaseHelper.class) {
                if (databaseHelperInstance == null)
                    databaseHelperInstance = new DatabaseHelper(context);
            }
        }
        return databaseHelperInstance;
    }


    public SQLiteDatabase openDb() {
        sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase;
    }


    public void closeDb() {
        sqLiteDatabase.close();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE_QUERY);
        db.execSQL(CREATE_NOTE_TABLE_QUERY);
        db.execSQL(CREATE_LABEL_TABLE_QUERY);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + USER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + NOTE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + LABEL_TABLE_NAME);
        onCreate(db);

    }


}





