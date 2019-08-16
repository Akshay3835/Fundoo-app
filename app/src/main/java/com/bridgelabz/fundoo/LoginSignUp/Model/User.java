package com.bridgelabz.fundoo.LoginSignUp.Model;

public class User
{

    private String first_Name;
    private String last_Name;
    private String email_Id;
    private String password;
    private String username;



    public User(String first_Name, String last_Name, String emailid, String password, String username) {
        this.first_Name = first_Name;
        this.last_Name = last_Name;
        this.email_Id = emailid;
        this.password = password;
        this.username = username;
    }


    public String getFirst_Name() {
        return first_Name;
    }

    public String getLast_Name() {
        return last_Name;
    }

    public String getEmail_Id()
    {
        return email_Id;
    }

    public String getPassword()
    {
        return password;
    }

    public String getUsername()
    {
        return username;
    }

}