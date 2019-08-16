package com.bridgelabz.fundoo.LoginSignUp.ViewModel;

import android.content.Context;

import com.bridgelabz.fundoo.LoginSignUp.database_manager.RestApiUserDataManager;
import com.bridgelabz.fundoo.LoginSignUp.Model.UserLoginModel;

public class UserApiViewModel
{
    RestApiUserDataManager apiUserDataManager;

    public UserApiViewModel(Context context)
    {
        apiUserDataManager = new RestApiUserDataManager();
    }

    public boolean checkUser(UserLoginModel userLoginModel, RestApiUserDataManager.LogInCallback logInCallback)
    {
        return apiUserDataManager.logInUser(userLoginModel, logInCallback);
    }
}
