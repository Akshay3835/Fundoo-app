package com.bridgelabz.fundoo.BroadCastReciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.bridgelabz.fundoo.add_note_page.Model.NoteResponseModel;
import com.bridgelabz.fundoo.add_note_page.View.note_adapter.NotesAdapter;

import java.util.ArrayList;
import java.util.List;

public class LocalBroadcastManager {

    public static final String TAG = "LocalBroadcastManager";
    NotesAdapter notesAdapter;
    private List<NoteResponseModel> noteResponseList = new ArrayList<>();


    private BroadcastReceiver getNoteBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e(TAG, "  LocalBroadcast is working");

            if (intent.hasExtra("NoteResponseList")) {
                Log.e(TAG, "onReceive: Check is coming");
                noteResponseList = (List<NoteResponseModel>) intent.
                        getSerializableExtra("NoteResponseList");
                notesAdapter.setNoteModelArrayList(noteResponseList);
                notesAdapter.notifyDataSetChanged();
            }
        }
    };

}

