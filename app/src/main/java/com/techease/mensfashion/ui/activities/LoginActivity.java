package com.techease.mensfashion.ui.activities;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.techease.mensfashion.R;
import com.techease.mensfashion.utils.AlertsUtils;
import com.techease.mensfashion.utils.Configuration;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class LoginActivity extends AppCompatActivity {


    android.support.v7.app.AlertDialog alertDialog;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    CallbackManager callbackManager;
    LoginButton loginButton;
    String email, strId;

    @BindView(R.id.anonymous)
    Button anonymous;
    private static final String EMAIL = "email";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityt_login);
        sharedPreferences = getSharedPreferences(Configuration.MY_PREF, Context.MODE_PRIVATE);
        Unbinder unbinder = ButterKnife.bind(this);

        editor = sharedPreferences.edit();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        callbackManager = CallbackManager.Factory.create();


        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList(EMAIL));
        // If you are using in a fragment, call loginButton.setFragment(this);


        anonymous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
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
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://116.203.128.245/index.php/restapi/signup"
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
                        editor.putString("email", email);
                        editor.putString("user_id", user_id);
                        editor.commit();
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } else {

                    try {
                        if (alertDialog != null)
                            alertDialog.dismiss();
                        JSONObject jsonObject = new JSONObject(response);
                        String message = jsonObject.getString("message");
                        AlertsUtils.showErrorDialog(LoginActivity.this, message);

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
                Log.d("error",String.valueOf(error.getMessage()));
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


                if (email == null) {
                    params.put("email", strId);
                } else {
                    params.put("email", email);
                }
                return params;
            }

        };
        RequestQueue mRequestQueue = Volley.newRequestQueue(LoginActivity.this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(stringRequest);
    }


    private Bundle getFacebookData(JSONObject object) {

        try {
            Bundle bundle = new Bundle();
            String id = object.getString("id");

            if (object.has("id")) {
                bundle.putString("email", object.getString("id"));
                strId = object.getString("id");
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
