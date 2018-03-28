package com.techease.mf.ui.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.techease.mf.R;
import com.techease.mf.communication.ApiFactory;
import com.techease.mf.communication.WebServices;
import com.techease.mf.communication.response.CollectionResponse;
import com.techease.mf.ui.adapters.AllTimeAdapter;
import com.techease.mf.ui.models.CollectionModel;
import com.techease.mf.utils.AlertsUtils;
import com.techease.mf.utils.Configuration;
import com.techease.mf.utils.InternetUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;


public class ThisWeek extends Fragment {


    android.support.v7.app.AlertDialog alertDialog;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String email, user_id;
    ArrayList<CollectionModel> all_model_list = new ArrayList<>();
    AllTimeAdapter all_adapter;
    Unbinder unbinder;
    @BindView(R.id.rv_thisweek)
    RecyclerView recyclerView;
    private boolean _hasLoadedOnce = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_this_week, container, false);
        unbinder = ButterKnife.bind(this, v);

        sharedPreferences = getActivity().getSharedPreferences(Configuration.MY_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        email = sharedPreferences.getString("email", "");
        user_id = sharedPreferences.getString("user_id", "");


        if (InternetUtils.isNetworkConnected(getActivity())) {

            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            all_adapter = new AllTimeAdapter(getActivity(), all_model_list);
            recyclerView.setAdapter(all_adapter);
            getCollectionByWeek();
            if (alertDialog == null)
                alertDialog = AlertsUtils.createProgressDialog(getActivity());
            alertDialog.show();


        } else {
            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }


        return v;
    }


    private void getCollectionByWeek() {
        WebServices webServices = ApiFactory.create();
        Call<CollectionResponse> call = webServices.getCollectionByWeek(user_id);
        call.enqueue(new Callback<CollectionResponse>() {
            @Override
            public void onResponse(Call<CollectionResponse> call, retrofit2.Response<CollectionResponse> response) {
                if (alertDialog != null)
                    alertDialog.dismiss();
                if (response.body() != null) {
                    if (response.body().isSuccess()) {
                        all_model_list.addAll(response.body().getCollection());
                        all_adapter.notifyDataSetChanged();


                    } else {
                        //todo show error message
                    }
                } else {
                    //todo show error message
                }

            }

            @Override
            public void onFailure(Call<CollectionResponse> call, Throwable t) {
                if (alertDialog != null)
                    alertDialog.dismiss();

                //todo show error message
            }
        });
    }
    public void updateLikeFragment(CollectionModel model) {
        boolean hasCollection = false;
        for (int i = 0; i < all_model_list.size(); i++) {
            if (all_model_list.get(i).getId() == model.getId()) {
                all_model_list.get(i).setLiked(model.getLiked());
                hasCollection = true;
                break;
            }
        }
        if (hasCollection) {
            if (all_adapter != null)
                all_adapter.notifyDataSetChanged();
        }
    }

}