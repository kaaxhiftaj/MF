package com.techease.mf.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.techease.mf.R;
import com.techease.mf.ui.fragments.ProductsFragment;
import com.techease.mf.utils.AlertsUtils;
import com.techease.mf.utils.Configuration;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Profile extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Unbinder unbinder;
    @BindView(R.id.logout)
    ImageView logout ;

    @BindView(R.id.logoutText)
    TextView logoutText;

    @BindView(R.id.mfLogo)
            ImageView mfLogo;

    URL profile_pic ;
    CallbackManager callbackManager;
    LoginButton loginButton;
    String email, token, pic;

    private static final String EMAIL = "email";
    android.support.v7.app.AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        unbinder = ButterKnife.bind(this);


        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_actionbar);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        callbackManager = CallbackManager.Factory.create();


        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList(EMAIL));
        sharedPreferences = Profile.this.getSharedPreferences(Configuration.MY_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        token = sharedPreferences.getString("token","");
        pic = sharedPreferences.getString("pic","");

        if (token.equals("")) {

            //Login na dy

            logout.setVisibility(View.INVISIBLE);


        }else {
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
                        apicall();


                    }
                });
                Bundle parameters = new Bundle();

                parameters.putString("fields", "id, email"); // Parámetros que pedimos a facebook
                request.setParameters(parameters);

                request.executeAsync();
                apicall();


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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void apicall() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://menfashion.techeasesol.com/restapi/signup"
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.contains("true")) {
                    if (alertDialog != null)
                        alertDialog.dismiss();

                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(response).getJSONObject("user");
                        String token = jsonObject.getString("token");
                        String user_id = jsonObject.getString("user_id");
                        String email = jsonObject.getString("email");

                        editor.putString("token", token);
                        editor.putString("email",email );
                        editor.putString("user_id", user_id);
                        editor.putString("pic", String.valueOf(profile_pic));
                        editor.commit();
                        startActivity(new Intent(Profile.this, HomeActivity.class));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } else {

                    try {
                        if (alertDialog != null)
                            alertDialog.dismiss();
                        JSONObject jsonObject = new JSONObject(response);
                        String message = jsonObject.getString("message");
                        AlertsUtils.showErrorDialog(Profile.this, message);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        if (alertDialog != null)
                            alertDialog.dismiss();
                    }
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (alertDialog != null)
                    alertDialog.dismiss();

            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded;charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                return params;
            }

        };
        RequestQueue mRequestQueue = Volley.newRequestQueue(Profile.this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(stringRequest);
    }


    private Bundle getFacebookData(JSONObject object) {

        try {
            Bundle bundle = new Bundle();
            String id = object.getString("id");

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
