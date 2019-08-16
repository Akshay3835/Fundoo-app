package com.bridgelabz.fundoo.LoginSignUp.View;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.bridgelabz.fundoo.common.ResponseModel.ResponseData;
import com.bridgelabz.fundoo.LoginSignUp.Model.UserModel;
import com.bridgelabz.fundoo.LoginSignUp.Model.UserLoginModel;
import com.bridgelabz.fundoo.R;
import com.bridgelabz.fundoo.common.RetrofitRestApiConnection;
import com.bridgelabz.fundoo.LoginSignUp.database_manager.apis.UserRestApiService;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TestActivity extends AppCompatActivity
{
    public static final String TAG ="RetrofitUSerActivity";
    UserRestApiService userRestApiService;
    Retrofit retrofit;
    Retrofit retrofitRestApiConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit_user);


        getUser();
        createUser();
        checkUser();
    }


    private void getUser() {
        Call<List<UserModel>> call = userRestApiService.getUsers();

        call.enqueue(new Callback<List<UserModel>>() {
            @Override
            public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                if (!response.isSuccessful())
                {
                    Log.e(TAG,"CODE :" + response.code());
                    return;
                }

                List<UserModel> user = response.body();
                for (UserModel userModel : user) {
                    String content = "";
//                    content += "ID :" + userModel.getId() + "\n";
//                    content += "UserId :" + userModel.getUserId() + "\n";
                    content += "FirstName : " + userModel.getFirstName() + "\n";
                    content += "LastName : " + userModel.getLastName() + "\n";
                    content += "Password : " + userModel.getPassword() + "\n";
                    content += "Role : " + userModel.getRole() + "\n";
                    content += "PhoneNumber : " + userModel.getPhoneNumber() + "\n";
                    content += "ImageUrl : " + userModel.getImageUrl() + "\n";
                    content += "Service : " + userModel.getService() + "\n";
                    content += "UserName : " + userModel.getUserName() + "\n";
                    content += "Email :" + userModel.getEmail() + "\n";

                    StringBuilder stringBuilder = new StringBuilder();

                   stringBuilder.append(content);
                }
            }

            @Override
            public void onFailure (Call <List<UserModel>> call, Throwable t)
            {

                Log.e(TAG,t.getLocalizedMessage());
            }
        });

    }
    private void createUser()
    {
        retrofitRestApiConnection = RetrofitRestApiConnection.openRetrofitConnection();

        UserModel user = new UserModel("akshay","kumar",
                "user","984855880","","advance","akshaykumar",
                "","akshaykumar@gmail.com","akshay123456");
        final UserRestApiService userRestApiService = retrofit.create(UserRestApiService.class);

        Call<Map<String,ResponseData>> call = userRestApiService.SignUpUser(user);
        call.enqueue(new Callback<Map<String, ResponseData>> () {
            @Override
            public void onResponse(Call<Map<String,ResponseData>> call, Response<Map<String,ResponseData>> response)
            {
                Map<String, ResponseData> responseData = response.body();
                if (response.isSuccessful() && responseData != null)
                {
                    Log.e(TAG,"response is "+ responseData.toString());
                }
                else
                {
                    Log.e(TAG,"response is " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Map<String,ResponseData>> call, Throwable throwable)
            {
                Log.e(TAG,throwable.getLocalizedMessage());

            }
        });
    }

    private void checkUser()
    {
        retrofitRestApiConnection = RetrofitRestApiConnection.openRetrofitConnection();
        final UserLoginModel loginModel = new UserLoginModel
                ("Akshaykumar@gmail.com", "akshay123456");
        UserRestApiService userRestApiService = retrofit.create(UserRestApiService.class);
        Call<UserModel> call = userRestApiService.LoginInUser(loginModel);
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                UserModel login = response.body();
                if (response.isSuccessful() && login != null) {
                    Log.e(TAG, "response is :" + loginModel.toString());
                } else {
                    Log.e(TAG, "response is " + response.code());
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Log.e(TAG, t.getLocalizedMessage());
            }

        });


    }

}
