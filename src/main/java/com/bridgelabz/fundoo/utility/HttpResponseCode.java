package com.bridgelabz.fundoo.utility;

public enum HttpResponseCode
{
    SUCCESS(200,"Success"),
    NOT_FOUND(404,"NOT_FOUND_ERROR"),
    INTERNAL_SERVICE_ERROR(500,"Internal_Service_error");

    private int errorCode;
    private  String localizedDescrpition;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
    public String getLocalizedDescrpition()
    {
        return localizedDescrpition;
    }


    HttpResponseCode(int errorCode, String localizedDesc)
    {
        this.errorCode = errorCode;
        this.localizedDescrpition = localizedDesc;
    }
}
