package com.bridgelabz.fundoo.LoginSignUp.Model;

import com.google.gson.annotations.SerializedName;

public class UserLoginModel
{
    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

   public UserLoginModel(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }


    public String toString()
    {
        StringBuilder stringBuilder =new StringBuilder()
                .append("Email :").append(email).append("\n")
                .append("Password :").append(password).append("\n");
        return stringBuilder.toString();
    }
}
