package com.bridgelabz.fundoo.add_note_page.Model;

import com.google.gson.annotations.SerializedName;

public class AddNoteModel extends BaseNoteModel {


    @SerializedName("reminder")
    private String reminder;

    public AddNoteModel(String title, String description, boolean isPinned, boolean isArchived,
                        boolean isDeleted, String createDate, String modifiedDate, String color,
                        String id, String userId, String reminder) {
        super(title, description, isPinned, isArchived, isDeleted, createDate, modifiedDate, color,
                id, userId);

        this.reminder = reminder;
    }


    public String getReminder() {
        return reminder;
    }

    public void setReminder(String reminder) {
        this.reminder = reminder;
    }

    public String toString() {
        return "title :" + title + "\n"
                + "description :" + description + "\n"
                + "Color :" + color + "\n"
                + "isArchive :" + isArchived + "\n"
                + "isPinned : " + isPinned + "\n"
                + "isDeleted : " + isDeleted + "\n"
                + "reminder : " + reminder + "\n"
                + "createdDate :" + createDate + "\n"
                + "modifiedDate :" + modifiedDate + "\n";

    }

    public static AddNoteModel getNoteFromResponse(NoteResponseModel noteResponseModel) {
        return new AddNoteModel(noteResponseModel.getTitle(),
                noteResponseModel.getDescription(),
                noteResponseModel.isPinned(),
                noteResponseModel.isArchived(),
                noteResponseModel.isDeleted(),
                noteResponseModel.getCreateDate(),
                noteResponseModel.getModifiedDate(),
                noteResponseModel.getColor(),
                noteResponseModel.getId(),
                noteResponseModel.getUserId(),
                noteResponseModel.getReminder().isEmpty() ? ""
                        : noteResponseModel.getReminder().get(0));
    }


}
