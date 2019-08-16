package com.bridgelabz.fundoo.LoginSignUp.ViewModel;

import android.content.Context;

import com.bridgelabz.fundoo.LoginSignUp.Model.User;
import com.bridgelabz.fundoo.LoginSignUp.database_manager.UserDatabaseManager;

public class UserViewModel
{
    private UserDatabaseManager userDatabaseManager;

    public UserViewModel(Context context)                   //Constructor of viewmodel class
    {
        userDatabaseManager = new UserDatabaseManager(context);

    }

    public boolean addUser(User user)
    {
        return userDatabaseManager.addUser(user);
    }

    public boolean checkUser(String emailid, String password)
    {
        return userDatabaseManager.checkUser(emailid,password);
    }

}
