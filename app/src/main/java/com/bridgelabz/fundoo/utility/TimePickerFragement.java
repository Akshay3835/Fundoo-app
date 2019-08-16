package com.bridgelabz.fundoo.utility;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import android.text.format.DateFormat;

import java.util.Calendar;

public class TimePickerFragement extends DialogFragment
{
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState)
    {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(calendar.HOUR_OF_DAY);
        int min = calendar.get(calendar.MINUTE);
        return new TimePickerDialog(getActivity() ,(TimePickerDialog.OnTimeSetListener)getActivity()
                ,hour,min, DateFormat.is24HourFormat(getActivity()));
    }
}


///    String title_1 = mTextTitle.getText().toString().trim();
//                String description_1 = mTextDescription.getText().toString().trim();
//                Note note_1 = new Note(title_1, description_1, noteColor, false,
//                        true, reminderBuilder.toString(), isTrashed);
//    addNoteToDb(note_1);

//    String title = mTextTitle.getText().toString().trim();
//                String description = mTextDescription.getText().toString().trim();
//                Note note = new Note(title, description, noteColor,false,
//                        false, reminderBuilder.toString(),isTrashed);

//                addNoteToDb(note);


//    private void addArchiveNotes(Note note)
//    {
//        BaseNoteModel noteModel = new BaseNoteModel(note.getTitle(),note.getDescription(),note.getColor(),
//                note.isArchive() + " ",note.isPinned() + " ",note.getIfReminder(),
//                note.isTrashed() + " ","","");
//        RestApiNoteViewModel apiNoteViewModel = new RestApiNoteViewModel(this);
//        apiNoteViewModel.getArchiveNotes(noteModel);
//    }