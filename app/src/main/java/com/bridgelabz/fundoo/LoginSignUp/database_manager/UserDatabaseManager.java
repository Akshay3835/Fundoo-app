package com.bridgelabz.fundoo.LoginSignUp.database_manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.bridgelabz.fundoo.LoginSignUp.Model.User;
import com.bridgelabz.fundoo.common.sqllite_database.DatabaseHelper;

public class UserDatabaseManager 
{

    public static final String USER_TABLE_NAME = "USER_TABLE";

    public static final String USER_TABLE_USER_ID = "ID";
    public static final String USER_TABLE_FIRST_NAME ="FIRST_NAME";
    public static final String USER_TABLE_LAST_NAME = "LAST_NAME";
    public static final String USER_TABLE_USER_EMAIL = "EMAIL_ID";
    public static final String USER_TABLE_USER_PASS = "PASSWORD";
    public static final String USER_TABLE_USERNAME = "USERNAME";


    private static final String TAG = UserDatabaseManager.class.getName();
    private DatabaseHelper databaseHelper;

   public UserDatabaseManager(Context context)
   {
       databaseHelper =  DatabaseHelper.getInstance(context);
   }

   private static UserDatabaseManager databaseManagerInstance;

   public static synchronized UserDatabaseManager getInstance(Context context){
       if (databaseManagerInstance == null){
           databaseManagerInstance = new UserDatabaseManager(context);
       }
       return databaseManagerInstance;
   }

    public boolean addUser(User user)
    {
        SQLiteDatabase db = databaseHelper.openDb();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_TABLE_FIRST_NAME,user.getFirst_Name());
        contentValues.put(USER_TABLE_LAST_NAME,user.getLast_Name());
        contentValues.put(USER_TABLE_USER_EMAIL, user.getEmail_Id());
        contentValues.put(USER_TABLE_USER_PASS, user.getPassword());
        contentValues.put(USER_TABLE_USERNAME,user.getUsername());

        Long res = db.insert(USER_TABLE_NAME, null, contentValues);
        db.close();


        Log.e(TAG, " res is " + res);
        return res>0;
    }

    public boolean checkUser(String emailid, String password) {
        String[] columns = {USER_TABLE_USER_ID};

        SQLiteDatabase db = databaseHelper.getReadableDatabase();


        String selection = USER_TABLE_USER_EMAIL + " = ?" + " and "
                + USER_TABLE_USER_PASS + " = ?";
        String[] selectionArgs = {emailid, password};
        Cursor cursor = db.query(USER_TABLE_NAME, columns, selection, selectionArgs,
                null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        if (count > 0)
            return true;
        else
            return false;
    }
}
