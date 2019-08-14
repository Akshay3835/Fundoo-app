package com.bridgelabz.fundoo.LoginSignUp.database_manager;

import android.util.Log;

import com.bridgelabz.fundoo.LoginSignUp.Model.UserModel;
import com.bridgelabz.fundoo.common.ResponseModel.ResponseData;
import com.bridgelabz.fundoo.common.ResponseModel.ResponseError;
import com.bridgelabz.fundoo.LoginSignUp.Model.UserLoginModel;
import com.bridgelabz.fundoo.common.RetrofitRestApiConnection;
import com.bridgelabz.fundoo.LoginSignUp.database_manager.apis.UserRestApiService;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RestApiUserDataManager {
    public static final String TAG = "RestApiUserDataManager";
    public static LogInCallback logInCallback;
    private RestApiUserDataManager restApiUserDataManager;
    UserRestApiService userRestApiService;

    public RestApiUserDataManager() {
        Retrofit retrofit = RetrofitRestApiConnection.openRetrofitConnection();
        userRestApiService = retrofit.create(UserRestApiService.class);
    }

    public void createUser(UserModel userModel, final SignUpCallback signUpCallback) {


        Call<Map<String, ResponseData>> call = userRestApiService.SignUpUser(userModel);
        call.enqueue(new Callback<Map<String, ResponseData>>() {
            @Override
            public void onResponse(Call<Map<String, ResponseData>> call,
                                   Response<Map<String, ResponseData>> response) {

                if (response.isSuccessful()) {
                    ResponseData responseData = response.body().get("data");
                    Log.e(TAG, "response is " + response.body().get("data") + "");
                    signUpCallback.onResponse(responseData, null);

                } else {
                    Log.e(TAG, "response errorbody is" + response.errorBody());
                    signUpCallback.onResponse(null, new ResponseError(
                            response.code() + "", null, null, null));
                }
            }

            @Override
            public void onFailure(Call<Map<String, ResponseData>> call, Throwable throwable) {
                signUpCallback.onFailure(throwable);
            }
        });
    }

    public boolean logInUser(UserLoginModel userLoginModel, final LogInCallback logInCallback) {
        Call<UserModel> call = userRestApiService.LoginInUser(userLoginModel); // if you got error
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                UserModel userModel1 = response.body();
                if (response.isSuccessful()) {
                    Log.e(TAG, "response is:" + userModel1.toString());
                    logInCallback.onResponse(userModel1, null);

                } else {
                    Log.e(TAG, "response code is" + response.code());


                }

            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable throwable) {
                Log.e(TAG, throwable.getLocalizedMessage());
                logInCallback.onFailure(throwable);

            }
        });
        return true;
    }

    public interface SignUpCallback {
        void onResponse(ResponseData responseData, ResponseError responseError);

        void onFailure(Throwable throwable);
    }

    public interface LogInCallback {
        void onResponse(UserModel userModel, ResponseError responseError);

        void onFailure(Throwable throwable);
    }

}


