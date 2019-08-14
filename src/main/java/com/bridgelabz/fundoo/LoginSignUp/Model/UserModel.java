package com.bridgelabz.fundoo.LoginSignUp.Model;

import com.google.gson.annotations.SerializedName;

public class UserModel
{
//    private String userId;
    @SerializedName("firstName")
    private String firstName;

    @SerializedName("lastName")
    private String lastName;

    @SerializedName("role")
    private String role;

    @SerializedName("phoneNumber")
    private String phoneNumber;

    @SerializedName("imageUrl")
    private String imageUrl;

    @SerializedName("service")
    private String service;

    @SerializedName("userName")
    private String userName;

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    @SerializedName("id")
    private String id;

    @SerializedName("ttl")
    private String ttl;


    public UserModel(String firstName, String lastName, String role,
                     String phoneNumber, String imageUrl, String service, String userName,String id,
                     String email, String password) {
//        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.phoneNumber = phoneNumber;
        this.imageUrl = imageUrl;
        this.service = service;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.id = id;
    }





    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getRole() {
        return role;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getService() {
        return service;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

//    public String getUserId() {
//        return userId;
//    }
//
    public String getId() {
        return id;
    }

    public String getPassword()
    {
        return password;
    }

    public String toString()
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("FirstName : ").append(firstName).append("\n")
                .append("LastName :").append(lastName).append("\n")
                .append("Role :").append(role).append("\n")
                .append("PhoneNumber :").append(phoneNumber).append("\n")
                .append("ImageUrl : ").append(imageUrl).append("\n")
                .append("Service :").append(service).append("\n")
                .append("UserName : ").append(userName).append("\n")
                .append("Email : ").append(email).append("\n")
                .append("Password :").append(password).append("\n")
                .append("Id : ").append(id).append("\n")
                .append("ttl : ").append(ttl).append("\n");



        return stringBuilder.toString();
    }

}
