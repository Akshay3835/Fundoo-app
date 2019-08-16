package com.bridgelabz.fundoo.common.ResponseModel;

import com.bridgelabz.fundoo.add_note_page.Model.NoteResponseModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseData extends Response
{
    @SerializedName("success")
    private String success;

    @SerializedName("message")
    private String message;

    @SerializedName("details")
    private Object details;


    @SerializedName("data")
    private List<NoteResponseModel> noteModelList;

    //Constructor
    public ResponseData(String success, String message) {
        this.success = success;
        this.message = message;
    }


    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<NoteResponseModel> getNoteModelList() {
        return noteModelList;
    }

    public String toString()
    {
        StringBuilder stringBuilder = new StringBuilder()
                .append("Success  :").append(success).append("\n")
                .append(" Message :").append(message).append("\n");
        return  stringBuilder.toString();
    }



}
