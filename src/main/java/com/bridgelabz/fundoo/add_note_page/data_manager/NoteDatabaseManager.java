package com.bridgelabz.fundoo.add_note_page.data_manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.bridgelabz.fundoo.add_note_page.Model.AddNoteModel;
import com.bridgelabz.fundoo.add_note_page.Model.Note;
import com.bridgelabz.fundoo.common.sqllite_database.DatabaseHelper;
import com.bridgelabz.fundoo.common.ObserverPattern.Observable;
import com.bridgelabz.fundoo.common.ObserverPattern.ObservableNotes;

import java.util.ArrayList;
import java.util.List;

public class NoteDatabaseManager {
   
    private DatabaseHelper databaseHelper;
    private boolean isTrashed;

    public NoteDatabaseManager(Context context) {

        databaseHelper = new DatabaseHelper(context);
    }



    public static final String NOTE_TABLE_NAME = "NOTETABLE";

    public static final String NOTE_TABLE_COLUMN_ID = "ID";
    public static final String NOTE_TABLE_COLUMN_TITLE = "TITLE";
    public static final String NOTE_TABLE_COLUMN_DES = "DESCRIPTION";
    public static final String NOTE_TABLE_NOTE_USER_ID = "USERID";
    public static final String NOTE_TABLE_COLUMN_COLOR = "COLOR";
    public static final String NOTE_TABLE_COLUMN_ARCHIVE = "ISARCHIEVE";
    public static final String NOTE_TABLE_COLUMN_REMINDER = "IFREMINDER";
    public static final String NOTE_TABLE_COLUMN_PINED = "ISPINNED";
    private static final String NOTE_TABLE_COLUMN_TRASHED = "ISTRASHED";


    private static final String TAG = "DatabaseHelper.class";

    public boolean addNote(Note note)
    {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NOTE_TABLE_COLUMN_TITLE, note.getTitle());
        contentValues.put(NOTE_TABLE_COLUMN_DES, note.getDescription());
        contentValues.put(NOTE_TABLE_COLUMN_COLOR, note.getColor());
        contentValues.put(NOTE_TABLE_COLUMN_REMINDER,note.getIfReminder());

        long res = db.insert(NOTE_TABLE_NAME, null, contentValues);
        db.close();

        Log.e(TAG, "res is" + res);

        return res > 0;
    }


    public List<Note> getAllNoteData() {
        List<Note> notesData = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.openDb();
        Cursor cursor = db.rawQuery("Select * from " + NOTE_TABLE_NAME + ";", null);

        Note note = null;


        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(NOTE_TABLE_COLUMN_ID));
            String title = cursor.getString(cursor.getColumnIndexOrThrow("TITLE"));
            String description = cursor.getString(cursor.getColumnIndexOrThrow("DESCRIPTION"));
            String color = cursor.getString(cursor.getColumnIndexOrThrow("COLOR"));

            boolean isArchived = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndexOrThrow
                    (NOTE_TABLE_COLUMN_ARCHIVE)));
            boolean isPinned = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndexOrThrow
                    (NOTE_TABLE_COLUMN_PINED)));
            String ifReminder = cursor.getString(cursor.getColumnIndexOrThrow
                    (NOTE_TABLE_COLUMN_REMINDER));

            note = new Note(title,description,color,isArchived,isPinned,ifReminder, isTrashed);
            note.setId(id);
            notesData.add(note);
        }
        cursor.close();
        return notesData;
    }

    public List<Note> getArchives()
    {
        List<Note> noteList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = databaseHelper.openDb();
        Cursor cursor = sqLiteDatabase.rawQuery( "SELECT * FROM " +  NOTE_TABLE_NAME
                + " WHERE " + NOTE_TABLE_COLUMN_ARCHIVE + " =1 ",null);


        Note note = null;
        while(cursor.moveToNext())
        {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(NOTE_TABLE_COLUMN_ID));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(NOTE_TABLE_COLUMN_TITLE));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(NOTE_TABLE_COLUMN_DES));
            String color = cursor.getString(cursor.getColumnIndexOrThrow(NOTE_TABLE_COLUMN_COLOR));
            boolean isArchived = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndexOrThrow
                    (NOTE_TABLE_COLUMN_ARCHIVE)));
            boolean isPinned = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndexOrThrow
                    (NOTE_TABLE_COLUMN_PINED)));
            String ifReminder = cursor.getString(cursor.getColumnIndexOrThrow
                    (NOTE_TABLE_COLUMN_REMINDER));

            note = new Note(title,description,color,isArchived,isPinned,ifReminder, isTrashed);
            note.setId(id);
            noteList.add(note);

            for(Note newNote : noteList)
            {
                System.out.println(newNote.toString());
            }

        }
        cursor.close();
        Log.e(TAG, "getArchives: " + noteList.toString() );
        return noteList;
    }

    public List<Note> getPined()
    {
        List<Note> noteList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = databaseHelper.openDb();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " +  NOTE_TABLE_NAME
                + " WHERE " + NOTE_TABLE_COLUMN_PINED + " =1 ",null);

        Note note = null;
        while(cursor.moveToNext())
        {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(NOTE_TABLE_COLUMN_ID));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(NOTE_TABLE_COLUMN_TITLE));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(NOTE_TABLE_COLUMN_DES));
            String color = cursor.getString(cursor.getColumnIndexOrThrow(NOTE_TABLE_COLUMN_COLOR));
            boolean isArchieved = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndexOrThrow(NOTE_TABLE_COLUMN_ARCHIVE)));
            boolean isPinned = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndexOrThrow(NOTE_TABLE_COLUMN_PINED)));
            String ifReminder = cursor.getString(cursor.getColumnIndexOrThrow(NOTE_TABLE_COLUMN_REMINDER));

            note = new Note(title,description,color,isArchieved,isPinned,ifReminder, isTrashed);
            note.setId(id);
            noteList.add(note);

            for(Note newNote : noteList)
            {
                System.out.println(newNote.toString());
            }

        }
        cursor.close();
        return noteList;
    }


    public List<Note> getReminders()
    {
        List<Note> noteList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = databaseHelper.openDb();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " +  NOTE_TABLE_NAME
                + " WHERE " + NOTE_TABLE_COLUMN_REMINDER + " != NULL ",null);

        Note note = null;
        while(cursor.moveToNext())
        {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(NOTE_TABLE_COLUMN_ID));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(NOTE_TABLE_COLUMN_TITLE));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(NOTE_TABLE_COLUMN_DES));
            String color = cursor.getString(cursor.getColumnIndexOrThrow(NOTE_TABLE_COLUMN_COLOR));
            boolean isArchieved = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndexOrThrow(NOTE_TABLE_COLUMN_ARCHIVE)));
            boolean isPinned = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndexOrThrow(NOTE_TABLE_COLUMN_PINED)));
            String ifReminder = cursor.getString(cursor.getColumnIndexOrThrow(NOTE_TABLE_COLUMN_REMINDER));

            note = new Note(title,description,color,isArchieved,isPinned,ifReminder, isTrashed);
            note.setId(id);
            noteList.add(note);

            for(Note newNote : noteList)
            {
                System.out.println(newNote.toString());
            }

        }
        cursor.close();
        return noteList;
    }


    public boolean deleteNote(Note note)
    {
        SQLiteDatabase sqLiteDatabase = databaseHelper.openDb();
        long results =  sqLiteDatabase.delete(NOTE_TABLE_NAME, NOTE_TABLE_COLUMN_DES + " = ?",
                new String[] {String.valueOf(note.getId())});

        Log.e(TAG,"item deleted");
        return results > 0;
    }

    public boolean deleteAllNotes()
    {

        SQLiteDatabase sqLiteDatabase = databaseHelper.openDb();
        Cursor cursor = sqLiteDatabase.rawQuery("DELETE FROM " + NOTE_TABLE_NAME + ";", null);

        boolean isDeleted = cursor.getCount()>0;
        cursor.close();
        return isDeleted;

    }
    public List<Note> getTrashedNotes(){
        List<Note> noteList = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.openDb();
        Cursor cursor = db.rawQuery("SELECT * FROM " + NOTE_TABLE_NAME +
                " WHERE " + NOTE_TABLE_COLUMN_TRASHED + " = 1", null);

        Note note = null;
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(NOTE_TABLE_COLUMN_ID));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(NOTE_TABLE_COLUMN_TITLE));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(NOTE_TABLE_COLUMN_DES));
            String color = cursor.getString(cursor.getColumnIndexOrThrow(NOTE_TABLE_COLUMN_COLOR));
            boolean isArchived = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndexOrThrow(NOTE_TABLE_COLUMN_ARCHIVE)));
            boolean isPinned = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndexOrThrow(NOTE_TABLE_COLUMN_PINED)));
            boolean isTrashed = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndexOrThrow(NOTE_TABLE_COLUMN_TRASHED)));
            String ifReminder = cursor.getString(cursor.getColumnIndexOrThrow(NOTE_TABLE_COLUMN_REMINDER));
            note = new Note(title,description,color,isArchived,isPinned,ifReminder,isTrashed);
            note.setId(id);
            noteList.add(note);

            for (Note newNote : noteList){
                System.out.println(newNote.toString());
            }

        }
        cursor.close();
        return  noteList;
    }


    public boolean updateNotes(AddNoteModel noteToEdit)
    {
        SQLiteDatabase sqLiteDatabase = databaseHelper.openDb();
        ContentValues contentValues =new ContentValues();
        contentValues.put(NOTE_TABLE_COLUMN_TITLE, noteToEdit.getTitle());
        contentValues.put(NOTE_TABLE_COLUMN_DES , noteToEdit.getDescription());
        contentValues.put(NOTE_TABLE_COLUMN_COLOR , noteToEdit.getColor());
//        contentValues.put(NOTE_TABLE_COLUMN_REMINDER,noteToEdit.getIfReminder());

        long results = sqLiteDatabase.update(NOTE_TABLE_NAME, contentValues,
                DatabaseHelper.NOTE_TABLE_COLUMN_ID + " =? ",
                new String[]{noteToEdit.getId() + ""});
        databaseHelper.closeDb();
        Log.e(TAG, "results is " + results);

        return results > 0;
    }

    public Observable<List<Note>> getAllObservableNotes(){
        return new ObservableNotes(getAllNoteData());
    }


}





