package com.bridgelabz.fundoo.add_note_page.Model;

import java.io.Serializable;

public class Note implements Serializable {


    private  String ifReminder;
    private  boolean isArchive;
    private boolean isPinned;
    private int id;
    private String title;
    private String description;
    private String color;



    private boolean isTrashed;
    private int userId;


    public Note(String title, String description, String color, boolean isArchived, boolean isPinned,
                String ifReminder, boolean isTrashed) {
        this.title = title;
        this.description = description;
        this.color = color;
        this.isArchive = isArchived;
        this.ifReminder = ifReminder;
        this.isPinned = isPinned;
        this.isTrashed = isTrashed;
    }
    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getTitle()
    {
        return title;
    }

    public String getDescription()
    {
        return description;

    }

    public String getColor()
    {
        return color;
    }
    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public void setColor(String color)
    {
        this.color = color;
    }

    public String getIfReminder()
    {
        return ifReminder;
    }

    public void setIfReminder(String ifReminder) {
        this.ifReminder = ifReminder;
    }

    public boolean isArchive() {
        return isArchive;
    }

    public void setArchive(boolean archive) {
        isArchive = archive;
    }

    public boolean isPinned() {
        return isPinned;
    }

    public void setPinned(boolean pinned) {
        isPinned = pinned;
    }

    public boolean isTrashed() {
        return isTrashed;
    }

    public void setTrashed(boolean trashed) {
        isTrashed = trashed;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Title : ").append(title).append("\n")
                .append("Description : ").append(description).append("\n")
                .append("colour : ").append(color).append("\n")
                .append("Archive : ").append(isArchive).append("\n")
                .append("Reminder : ").append(ifReminder).append("\n")
                .append("Pinned : ").append(isPinned).append("\n")
                .append("Trashed : ").append(isTrashed).append("\n")
                .append("Id :").append(id).append("\n")
                .append("userId :").append(userId).append("\n");


        return stringBuilder.toString();
    }
}



