package com.bridgelabz.fundoo.common.ResponseModel;

import com.google.gson.annotations.SerializedName;

public class ResponseError extends Response
{
    @SerializedName("statusCode")
    private String statusCode;

    @SerializedName("name")
    private String name;

    @SerializedName("message")
    private String message;

    @SerializedName("details")
    private String details;

    @SerializedName("code")
    private String code;

    public ResponseError(String statusCode, String name, String message, String details) {
        this.statusCode = statusCode;
        this.name = name;
        this.message = message;
        this.details = details;
    }

    public String getName() {
        return name;
    }

    public String getDetails() {
        return details;
    }

    public String getStatusCode()
    {
        return getStatusCode();
    }

    public String getMessage()
    {
        return getMessage();
    }

    public String toString()
    {
        StringBuilder stringBuilder = new StringBuilder()
                .append("StatusCode :").append(statusCode).append("\n")
                .append("Name :").append(name).append("\n")
                .append("Message :").append(message).append("\n")
                .append("Context :").append(details).append("\n");
        return stringBuilder.toString();
    }


}
