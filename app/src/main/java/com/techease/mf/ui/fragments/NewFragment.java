package com.techease.mf.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.techease.mf.R;
import com.techease.mf.communication.ApiFactory;
import com.techease.mf.communication.WebServices;
import com.techease.mf.communication.response.CollectionResponse;
import com.techease.mf.ui.activities.Profile;
import com.techease.mf.ui.adapters.NewAdapter;
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

public class NewFragment extends Fragment {

    android.support.v7.app.AlertDialog alertDialog;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String email, user_id;
    ArrayList<CollectionModel> new_model_list = new ArrayList<>();
    NewAdapter new_adapter;
    private boolean _hasLoadedOnce = false;
    Unbinder unbinder;

    @BindView(R.id.rv_new)
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_new, container, false);
        unbinder = ButterKnife.bind(this, v);

        sharedPreferences = getActivity().getSharedPreferences(Configuration.MY_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        email = sharedPreferences.getString("email", "");
        user_id = sharedPreferences.getString("user_id", "");
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        new_adapter = new NewAdapter(getActivity(), new_model_list);
        recyclerView.setAdapter(new_adapter);
        if (InternetUtils.isNetworkConnected(getActivity())) {
            getNewCollections();
            if (alertDialog == null)
                alertDialog = AlertsUtils.createProgressDialog(getActivity());
            alertDialog.show();


        } else {
            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
        customActionBar();
        return v;
    }


    private void getNewCollections() {
        WebServices webServices = ApiFactory.create();
        Call<CollectionResponse> call = webServices.getCollection(user_id);
        call.enqueue(new Callback<CollectionResponse>() {
            @Override
            public void onResponse(Call<CollectionResponse> call, retrofit2.Response<CollectionResponse> response) {
                if (alertDialog != null)
                    alertDialog.dismiss();
                if (response.body() != null) {
                    if (response.body().isSuccess()) {
                        new_model_list.addAll(response.body().getCollection());
                        new_adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CollectionResponse> call, Throwable t) {
                if (alertDialog != null)
                    alertDialog.dismiss();
            }
        });
    }

    public void customActionBar() {
        android.support.v7.app.ActionBar mActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(false);
        mActionBar.setHomeButtonEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        mActionBar.setDisplayShowCustomEnabled(true);
        LayoutInflater mInflater = LayoutInflater.from(getActivity());
        View mCustomView = mInflater.inflate(R.layout.custom_main_actionbar, null);
        ImageView ivMF = mCustomView.findViewById(R.id.iv_mf);
        ImageButton profile = (ImageButton) mCustomView.findViewById(R.id.profile);
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Profile.class));
            }
        });

    }

    public void updateLikeFragment(CollectionModel model) {
        boolean hasCollection = false;
        for (int i = 0; i < new_model_list.size(); i++) {
            if (new_model_list.get(i).getId() == model.getId()) {
                new_model_list.get(i).setLiked(model.getLiked());
                hasCollection = true;
                break;
            }
        }
        if (hasCollection) {
            if (new_adapter != null)
                new_adapter.notifyDataSetChanged();
        }
    }
}
