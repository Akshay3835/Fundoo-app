package com.bridgelabz.fundoo.LoginSignUp.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bridgelabz.fundoo.Dashboard.DashboardActivity;
import com.bridgelabz.fundoo.LoginSignUp.Model.UserLoginModel;
import com.bridgelabz.fundoo.LoginSignUp.Model.UserModel;
import com.bridgelabz.fundoo.LoginSignUp.ViewModel.UserApiViewModel;
import com.bridgelabz.fundoo.LoginSignUp.ViewModel.UserViewModel;
import com.bridgelabz.fundoo.LoginSignUp.database_manager.RestApiUserDataManager;
import com.bridgelabz.fundoo.R;
import com.bridgelabz.fundoo.common.ResponseModel.ResponseError;
import com.bridgelabz.fundoo.common.share_preference.SharePreferencesManager;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import static com.google.android.gms.auth.api.Auth.GOOGLE_SIGN_IN_API;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "LoginActivity.class";
    public static final String METHOD_GET = "GET";
    public static final String METHOD_PUT = "PUT";
    public static final String METHOD_DELETE = "DELETE";
    private static final String PERMISSION_EMAIL = "email";
    EditText mTextEmailId;
    EditText mTextPassword;
    Button mButtonLogin;
    TextView mTextViewRegister;
    private UserViewModel userViewModel;
    private UserApiViewModel userApiViewModel;
    private SignInButton mSignIn;
    private GoogleApiClient googleApiClient;
    private CallbackManager callbackManager;
    private LoginButton mLoginButton;
    private FirebaseAuth mAuth;
    private SharePreferencesManager sharedPreferences;
    public static final int REQ_CODE = 9001;


    ResponseError responseError;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        sharedPreferences = new SharePreferencesManager(this);


        findViews();
        onClickLogin();
        onClickRegister();
        onClickSignIn();
        onClickSignUp();

        //Google Login
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder
                (GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage
                (this, this).
                addApi(GOOGLE_SIGN_IN_API, googleSignInOptions).build();


        //facebook login
        callbackManager = CallbackManager.Factory.create();
        //noinspection deprecation
        mLoginButton.setReadPermissions(Arrays.asList(PERMISSION_EMAIL));
        mLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(LoginActivity.this, "Logined sucessfully..", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancel() {
                Toast.makeText(LoginActivity.this, "Loginned failure", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(LoginActivity.this, "Error in Login....", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void findViews() {
        userViewModel = new UserViewModel(this);
        mTextEmailId = findViewById(R.id.edittext_email_id);
        mTextPassword = findViewById(R.id.edittext_password);
        mButtonLogin = findViewById(R.id.button_login);
        mSignIn = findViewById(R.id.btn_sign_in);
        mLoginButton = findViewById(R.id.btn_fb_login);
        mTextViewRegister = findViewById(R.id.textview_register);
    }


    private void onClickRegister() {

        mTextViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
            }
        });
    }

    public void onClickLogin() {
        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String emailid = mTextEmailId.getText().toString().trim();
                final String pwd = mTextPassword.getText().toString().trim();


                final UserLoginModel loginModel = new UserLoginModel(emailid, pwd);
                RestApiUserDataManager userDataManager = new RestApiUserDataManager();
                userDataManager.logInUser(loginModel, new RestApiUserDataManager.LogInCallback() {
                    @Override
                    public void onResponse(UserModel userModel, ResponseError responseError) {

//                        if ()
                        Log.e(TAG, "onResponse :" + userModel.toString());
                        String token = userModel.getId();
                        sharedPreferences.setAccessToken(token);
                        Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                        intent.putExtra("email", emailid);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        if (throwable != null) {
                            Log.e(TAG, "" + throwable.toString());
                        }

                    }
                });
            }
        });
    }


    private void onClickSignUp() {
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void SignIn() {
        Log.e(TAG, REQ_CODE + " SignIn Button pressed!");
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent, REQ_CODE);

    }

    private void handleResult(GoogleSignInResult result) {
        Log.e(TAG, " inside handleResult method outer block" + result.getStatus());
        if (result.isSuccess()) {
            Log.e(TAG, REQ_CODE + " inside handleResult method!");

            GoogleSignInAccount account = result.getSignInAccount();
            String emailId = account.getEmail();
            String imageUrl = account.getPhotoUrl().toString();
//            String name = account.getDisplayName();
            mTextEmailId.setText(emailId);
            Log.e(TAG, "Login is successful");
            Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
            intent.putExtra("email", emailId);
            intent.putExtra("imageUrl", imageUrl);
            startActivity(intent);
            updateUI(true);

        } else {
            updateUI(false);
        }
    }

    private void updateUI(boolean isLogin) {
        if (isLogin) {

        } else {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE) {
            Log.e(TAG, REQ_CODE + " onActivityResult called!");
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleResult(result);
        }
    }


    private void onClickSignIn() {
        mSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_sign_in:
                        SignIn();
                        break;
                }

            }
        });
    }

    AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken,
                                                   AccessToken currentAccessToken) {
            if (currentAccessToken == null) {
                mTextEmailId.setText("");
                Toast.makeText(LoginActivity.this, "User is LoggedOut", Toast.LENGTH_SHORT).show();
            } else {
                loadUserProfile(currentAccessToken);
            }


        }
    };

    private void loadUserProfile(AccessToken accessToken) {

        GraphRequest request = GraphRequest.newMeRequest(accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {


                        try {
                            String firstName = object.getString("firstName");
                            String lastName = object.getString("lastName");
                            String email = object.getString("email");
                            String id = object.getString("id");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", PERMISSION_EMAIL);
        request.setParameters(parameters);
        request.executeAsync();
    }
}
