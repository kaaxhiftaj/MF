package com.techease.mf.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.techease.mf.R;
import com.techease.mf.ui.activities.Profile;
import com.techease.mf.ui.adapters.MyLikesAdapter;
import com.techease.mf.ui.adapters.NewAdapter;
import com.techease.mf.ui.models.MyLikesModel;
import com.techease.mf.ui.models.NewModel;
import com.techease.mf.utils.AlertsUtils;
import com.techease.mf.utils.Configuration;
import com.techease.mf.utils.InternetUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class MyLikesFragment extends Fragment {

    android.support.v7.app.AlertDialog alertDialog;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String email, user_id;
    ArrayList<MyLikesModel> myLikes_model_list;
    MyLikesAdapter myLikes_adapter;
    Unbinder unbinder;

    @BindView(R.id.rv_myLikes)
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_my_likes, container, false);

        unbinder = ButterKnife.bind(this, v);

        sharedPreferences = getActivity().getSharedPreferences(Configuration.MY_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        email = sharedPreferences.getString("email", "");
        user_id = sharedPreferences.getString("user_id", "");



        return v;
    }
//ao zma pa khyal, kho pa my likes bande hm bel ta rawan de

    private void apicall() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://menfashion.techeasesol.com/restapi/userFavorites"
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.contains("true")) {
                    try {
                        if (alertDialog != null)
                            alertDialog.dismiss();
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArr = jsonObject.getJSONArray("collection");
                        for (int i = 0; i < jsonArr.length(); i++) {
                            JSONObject temp = jsonArr.getJSONObject(i);

                            MyLikesModel model = new MyLikesModel();
                            String id = temp.getString("id");
                            String name = temp.getString("name");
                            String image = temp.getString("image");
                            String like = temp.getString("likes");
                            String facebook = temp.getString("facebook");
                            model.setId(id);
                            model.setName(name);
                            model.setImage(image);
                            model.setNoLikes(like);
                            model.setFacebook(facebook);
                            myLikes_model_list.add(model);


                        }
                        myLikes_adapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        if (alertDialog != null)
                            alertDialog.dismiss();
                    }
                } else {

                    try {
                        if (alertDialog != null)
                            alertDialog.dismiss();
                        startActivity(new Intent(getActivity(), Profile.class));


                        JSONObject jsonObject = new JSONObject(response);
                        String message = jsonObject.getString("message");
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

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
                params.put("user_id", user_id);
                return params;
            }

        };
        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(stringRequest);
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if(menuVisible)
        if (InternetUtils.isNetworkConnected(getActivity())) {

            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            myLikes_model_list = new ArrayList<>();
            apicall();
            if (alertDialog == null)
                alertDialog = AlertsUtils.createProgressDialog(getActivity());
            alertDialog.show();
            myLikes_adapter = new MyLikesAdapter(getActivity(), myLikes_model_list);
            recyclerView.setAdapter(myLikes_adapter);
        }

        else {
            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }
}
