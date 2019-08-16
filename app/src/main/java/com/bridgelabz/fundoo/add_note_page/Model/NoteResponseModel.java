package com.bridgelabz.fundoo.add_note_page.Model;

import java.util.List;

public class NoteResponseModel extends BaseNoteModel {



    private List<String> reminder;

    public NoteResponseModel(String title, String description, boolean isPinned, boolean isArchived,
                             boolean isDeleted, String createDate, String modifiedDate, String color,
                             String id, String userId,List<String> reminder) {

        super(title, description, isPinned, isArchived, isDeleted, createDate, modifiedDate, color,
                id, userId);

        this.reminder = reminder;

    }


    public List<String> getReminder() {
        return reminder;
    }

    public void setReminder(List<String> reminder) {
        this.reminder = reminder;
    }

    public String toString()
    {
        return " title : " + title + "\n"
                + " description : " + description + "\n"
                + " Color : " + color + "\n"
                + " isArchived : " + isArchived + "\n"
                + " isPinned : " + isPinned + "\n"
                + " isDeleted : " + isDeleted + "\n"
                + " reminder : " + reminder + "\n"
                + " createdDate : " + createDate + "\n"
                + " modifiedDate :" + modifiedDate + "\n";
    }




}

