package com.bridgelabz.fundoo.add_label_page.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.bridgelabz.fundoo.common.sqllite_database.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class LabelDatabaseManager
{


    public static final String LABEL_TABLE_NAME = "LABELTABLE";


    public static final String LABEL_TABLE_COLUMN_ID = "ID";
    public static final String LABEL_TABLE_COLUMN_NAME = "LABELNAME";
    public static final String LABEL_TABLE_COLUMN_USERID = "LABELUSERID";

    private DatabaseHelper databaseHelper;

    public LabelDatabaseManager(Context context)
    {
       databaseHelper = new DatabaseHelper(context);
    }

    public static final String TAG = "DatabaseHelper.class";

    public boolean addLabel(Label label)
    {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
//        contentValues.put(LABEL_TABLE_COLUMN_ID, label.getLabelId());
        contentValues.put(LABEL_TABLE_COLUMN_NAME , label.getLabelName());
//        contentValues.put(LABEL_TABLE_COLUMN_USERID , label.getUserId());

        long res = db.insert(LABEL_TABLE_NAME, null, contentValues);
        db.close();

        Log.e(TAG, "res is" + res);

        return res > 0;
    }


    public List<Label> getLabelData() {
        List<Label> labelsdata = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.openDb();
        Cursor cursor = db.rawQuery("Select * from " + LABEL_TABLE_NAME + ";", null);

       Label label= null;


        while (cursor.moveToNext()) {
            int labelId = cursor.getInt(cursor.getColumnIndexOrThrow(LABEL_TABLE_COLUMN_ID));
            String labelName = cursor.getString(cursor.getColumnIndexOrThrow(LABEL_TABLE_COLUMN_NAME));

            label  = new Label(labelId,labelName);

            labelsdata.add(label);
        }
        cursor.close();
        return labelsdata;
    }


    public boolean updateLabels(Label labelToEdit)
    {
        SQLiteDatabase sqLiteDatabase = databaseHelper.openDb();
        ContentValues contentValues =new ContentValues();
        contentValues.put(LABEL_TABLE_COLUMN_NAME, labelToEdit.getLabelName());
        contentValues.put(LABEL_TABLE_COLUMN_USERID , labelToEdit.getUserId());


        long results = sqLiteDatabase.update(LABEL_TABLE_NAME, contentValues,
                DatabaseHelper.LABEL_TABLE_COLUMN_ID + " =? ",
                new String[]{labelToEdit.getLabelId() + ""});
        databaseHelper.closeDb();
        Log.e(TAG, "results is " + results);

        return results > 0;
    }

    public boolean deleteLabel(Label label)
    {
        SQLiteDatabase sqLiteDatabase = databaseHelper.openDb();
        long results =  sqLiteDatabase.delete(LABEL_TABLE_NAME, LABEL_TABLE_COLUMN_NAME + " = ?",
                new String[] {String.valueOf(label.getLabelId())});

        Log.e(TAG,"item deleted");
        return results > 0;
    }

    public boolean deleteAllLabels()
    {

        SQLiteDatabase sqLiteDatabase = databaseHelper.openDb();
        Cursor cursor = sqLiteDatabase.rawQuery("DELETE FROM " + LABEL_TABLE_NAME + ";", null);

        boolean isDeleted = cursor.getCount() >0;
        cursor.close();
        return isDeleted;
    }
}
