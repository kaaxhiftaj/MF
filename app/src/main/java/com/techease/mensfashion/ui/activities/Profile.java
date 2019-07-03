package com.techease.mensfashion.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.techease.mensfashion.R;
import com.techease.mensfashion.communication.ApiFactory;
import com.techease.mensfashion.communication.WebServices;
import com.techease.mensfashion.ui.models.facebookSignUp.FacebookSignUpResponseModel;
import com.techease.mensfashion.utils.Configuration;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;

public class Profile extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Unbinder unbinder;
    @BindView(R.id.logout)
    ImageView logout;

    @BindView(R.id.logoutText)
    TextView logoutText;

    @BindView(R.id.mfLogo)
    ImageView mfLogo;

    URL profile_pic;
    CallbackManager callbackManager;
    LoginButton loginButton;
    String email, token, pic, strId;

    private static final String EMAIL = "email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        unbinder = ButterKnife.bind(this);


        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_actionbar);

        FacebookSdk.setAutoInitEnabled(true);
        FacebookSdk.fullyInitialize();
        callbackManager = CallbackManager.Factory.create();


        loginButton = findViewById(R.id.login_button);
        sharedPreferences = Profile.this.getSharedPreferences(Configuration.MY_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        token = sharedPreferences.getString("token", "");
        pic = sharedPreferences.getString("pic", "");

        if (token.equals("")) {
            //Login na dy
            logout.setVisibility(View.INVISIBLE);

        } else {
            //Login dyy

            loginButton.setVisibility(View.INVISIBLE);
            Glide.with(this).load(pic).into(mfLogo);

        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoginManager.getInstance().logOut();
                editor.clear().commit();
                startActivity(new Intent(Profile.this, SplashActivity.class));

            }
        });


        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {


                String accessToken = loginResult.getAccessToken().getToken();

                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {

                        Log.i("LoginActivity", response.toString());
                        // Get facebook data from login
                        Bundle bFacebookData = getFacebookData(object);

                        if (email == null) {
                            email = strId;
                        }

                        facebookSignUpApiCall();

                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, email"); // Parámetros que pedimos a facebook
                request.setParameters(parameters);
                request.executeAsync();

            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });


    }

    private void facebookSignUpApiCall() {
        WebServices webServices = ApiFactory.create();

        Call<FacebookSignUpResponseModel> call = webServices.facebooSignUp(email);
        call.enqueue(new Callback<FacebookSignUpResponseModel>() {
            @Override
            public void onResponse(Call<FacebookSignUpResponseModel> call, retrofit2.Response<FacebookSignUpResponseModel> response) {

                if (response.body() != null) {
                    //like and unlike done in backend
                    editor.putString("token", response.body().getUser().getToken()).commit();
                    editor.putString("email", response.body().getUser().getEmail()).commit();
                    editor.putString("user_id", response.body().getUser().getUserId()).commit();
                    editor.putString("pic", String.valueOf(profile_pic)).commit();
                    finish();
                    startActivity(new Intent(Profile.this, HomeActivity.class));

                } else {
                    //todo show failure message

                }
            }

            @Override
            public void onFailure(Call<FacebookSignUpResponseModel> call, Throwable t) {
                //todo show failure message
                Toast.makeText(Profile.this, String.valueOf(t.getMessage()), Toast.LENGTH_SHORT).show();

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }



    private Bundle getFacebookData(JSONObject object) {

        try {
            Bundle bundle = new Bundle();
            String id = object.getString("id");
            strId = id;


            try {
                profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?width=160&height=160");

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }

            if (object.has("email"))

                bundle.putString("email", object.getString("email"));
            email = object.getString("email");


            return bundle;
        } catch (JSONException e) {
            Log.d("Error", "Error parsing JSON");
        }
        return null;
    }

}
