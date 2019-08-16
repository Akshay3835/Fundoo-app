package com.bridgelabz.fundoo.add_note_page.ViewModel;


import android.content.Context;

import com.bridgelabz.fundoo.add_note_page.Model.AddNoteModel;
import com.bridgelabz.fundoo.add_note_page.Model.Note;
import com.bridgelabz.fundoo.add_note_page.data_manager.NoteDatabaseManager;
import com.bridgelabz.fundoo.common.ObserverPattern.Observable;

import java.util.List;

public class NoteViewModel {
    private NoteDatabaseManager noteDatabaseManager;


     private Observable<List<Note>> observableNotes;


    public NoteViewModel(Context context) {
        noteDatabaseManager = new NoteDatabaseManager(context);

        this.observableNotes = noteDatabaseManager.getAllObservableNotes();
    }

    public boolean addNote(Note note) {
        return noteDatabaseManager.addNote(note);
    }

    public List<Note> getAllNotes() {
        return noteDatabaseManager.getAllNoteData();
    }

    public List<Note> getArchiveNotes()
    {
        return noteDatabaseManager.getArchives();

    }
    public List<Note> getPinnedNotes()
    {
        return noteDatabaseManager.getPined();
    }
    public List<Note> getReminderNotes()
    {
        return  noteDatabaseManager.getReminders();
    }

    public boolean deleteNote(Note note)
    {
        return noteDatabaseManager.deleteNote(note);
    }

    public boolean updateNote(AddNoteModel noteToEdit)
    {
        return noteDatabaseManager.updateNotes(noteToEdit);
    }

    public void deleteAllNotes()
{
    noteDatabaseManager.deleteAllNotes();
}

public Observable<List<Note>> fetchAllNotes()
{
    return observableNotes;
}
}