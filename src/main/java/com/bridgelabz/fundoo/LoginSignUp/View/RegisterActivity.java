package com.bridgelabz.fundoo.LoginSignUp.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bridgelabz.fundoo.LoginSignUp.Model.UserModel;
import com.bridgelabz.fundoo.LoginSignUp.Model.User;
import com.bridgelabz.fundoo.LoginSignUp.ViewModel.UserViewModel;
import com.bridgelabz.fundoo.LoginSignUp.database_manager.RestApiUserDataManager;
import com.bridgelabz.fundoo.R;
import com.bridgelabz.fundoo.common.ResponseModel.ResponseData;
import com.bridgelabz.fundoo.common.ResponseModel.ResponseError;
import com.bridgelabz.fundoo.utility.ValidationHelper;
import com.google.firebase.auth.FirebaseAuth;


public class RegisterActivity extends AppCompatActivity {

    public static final String TAG = "RegisterActivity";
    private UserViewModel userViewModel;
    EditText mTextEmailid;
    EditText mTextFirstName;
    EditText mTextLastName;
    EditText mTextUsername;
    EditText mTextPassword;
    EditText mTextCnfPassword;
    Button mButtonRegister;
    TextView mTextViewLogin;
    private FirebaseAuth mFirebaseAuth;
    private User user;

    ResponseError responseError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mFirebaseAuth = FirebaseAuth.getInstance();


        findViews();
        setClickToLoginTView();
        setClickToRegisterBtn();

//        doFirebaseSignUp();
    }


    private void setClickToLoginTView() {
        mTextViewLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(loginIntent);
            }
        });
    }

    private void findViews() {
        mTextFirstName = findViewById(R.id.edittext_firstname);
        mTextLastName = findViewById(R.id.edittext_lastname);
        mTextEmailid = findViewById(R.id.edittext_email_id);
        mTextUsername = findViewById(R.id.edittext_username);
        mTextPassword = findViewById(R.id.edittext_password);
        mTextCnfPassword = findViewById(R.id.edittext_cnf_password);
        mButtonRegister = findViewById(R.id.button_register);
        mTextViewLogin = findViewById(R.id.textview_login);

    }

    private void setClickToRegisterBtn() {
        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstname = mTextFirstName.getText().toString().trim();
                String lastname = mTextLastName.getText().toString().trim();
                String emailid = mTextEmailid.getText().toString().trim();
                String password = mTextPassword.getText().toString().trim();
                String username = mTextUsername.getText().toString().trim();
                String cnf_pwd = mTextCnfPassword.getText().toString().trim();


                UserModel userModel = new UserModel(firstname, lastname,
                        "user", "989898999890", "", "advance",
                        username, "", emailid, password);
                user = new User(firstname, lastname, emailid, password, username);

                if (ValidationHelper.Validate_Email(emailid)) {
                    if (ValidationHelper.Validate_Password(password)) {
                        processRegister(userModel);
                        makeToast("Enter valid password");
                    }
                } else {
                    makeToast("Enter valid Email");
                }
            }

        });
    }


    private void makeToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }


    private void processRegister(UserModel userModel) {
        userViewModel = new UserViewModel(this);

        RestApiUserDataManager restApiUserDataManager = new RestApiUserDataManager();
        restApiUserDataManager.createUser(userModel, new RestApiUserDataManager.SignUpCallback() {
            @Override
            public void onResponse(ResponseData responseData, ResponseError responseError)
            {

            }

            @Override
            public void onFailure(Throwable throwable)
            {

            }
        });

        boolean val = userViewModel.addUser(user);
        if (val) {
            Toast.makeText(RegisterActivity.this, "Registered...", Toast.LENGTH_LONG).show();
            Intent moveToLogin = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(moveToLogin);
            finish();
        } else {
            Toast.makeText(RegisterActivity.this, "Registration Error...", Toast.LENGTH_LONG).show();

        }
    }
//
//    {
//        Toast.makeText(RegisterActivity.this, "Password not matching...", Toast.LENGTH_LONG).show();
//    }


}



