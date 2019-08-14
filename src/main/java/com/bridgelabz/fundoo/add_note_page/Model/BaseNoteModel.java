package com.bridgelabz.fundoo.add_note_page.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class BaseNoteModel implements Serializable {
    @SerializedName("title")
    protected String title;

    @SerializedName("description")
    protected String description;

    @SerializedName("isPinned")
    protected boolean isPinned;

    @SerializedName("isArchived")
    protected boolean isArchived;

    @SerializedName("isDeleted")
    protected boolean isDeleted;
//
//    @SerializedName("reminder")
//    private String reminder;

    @SerializedName("createdDate")
    protected String createDate;

    @SerializedName("modifiedDate")
    protected String modifiedDate;

    @SerializedName("color")
    protected String color;

    @SerializedName("label")
    protected List<String> label;

    @SerializedName("imageUrl")
    protected String imageUrl;

    @SerializedName("id")
    protected String id;

    @SerializedName("userId")
    protected String userId;

    public BaseNoteModel(String title, String description, boolean isPinned, boolean isArchived,
                         boolean isDeleted, String createDate,String modifiedDate, String color,
                         String id, String userId) {
        this.title = title;
        this.description = description;
        this.isPinned = isPinned;
        this.isArchived = isArchived;
        this.isDeleted = isDeleted;
//        this.reminder = reminder;
//        this.createDate = createDate;
//        this.modifiedDate = modifiedDate;
        this.color = color;
//        this.label = label;
//        this.imageUrl = imageUrl;
        this.id = id;
        this.userId = userId;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPinned() {
        return isPinned;
    }

    public void setPinned(boolean pinned) {
        isPinned = pinned;
    }

    public boolean isArchived() {
        return isArchived;
    }

    public void setArchived(boolean archived) {
        isArchived = archived;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder()
                .append("Title : ").append(title).append("\n")
                .append("Description :").append(description).append("\n")
                .append("isPinned :").append(isPinned).append("\n")
                .append("isArchived :").append(isArchived).append("\n")
                .append("isDeleted :").append(isDeleted).append("\n")
//                .append("reminder :").append(reminder).append("\n")
                .append("createDate :").append(createDate).append("\n")
                .append("modifiedDate :").append(modifiedDate).append("\n")
                .append("Color :").append(color).append("\n")
                .append("Label :").append(label).append("\n")
                .append("ImageUrl :").append(imageUrl).append("\n")
                .append("Id :").append(id).append("\n")
                .append("UserId :").append(userId).append("\n");
        return stringBuilder.toString();

    }
}
