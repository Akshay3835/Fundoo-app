package com.bridgelabz.fundoo.LoginSignUp.database_manager.apis;

import com.bridgelabz.fundoo.common.ResponseModel.ResponseData;
import com.bridgelabz.fundoo.LoginSignUp.Model.UserModel;
import com.bridgelabz.fundoo.LoginSignUp.Model.UserLoginModel;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserRestApiService
{
    @GET("user")
    Call<List<UserModel>>  getUsers();

    @POST("user/userSignUp")
    Call<Map<String,ResponseData>> SignUpUser(@Body UserModel user);

    @POST("user/login")
    Call<UserModel> LoginInUser(@Body UserLoginModel loginModel);
}
