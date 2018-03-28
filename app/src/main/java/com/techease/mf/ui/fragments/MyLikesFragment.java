package com.techease.mf.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.techease.mf.R;
import com.techease.mf.communication.ApiFactory;
import com.techease.mf.communication.WebServices;
import com.techease.mf.communication.response.CollectionResponse;
import com.techease.mf.ui.activities.Profile;
import com.techease.mf.ui.adapters.MyLikesAdapter;
import com.techease.mf.ui.models.CollectionModel;
import com.techease.mf.utils.AlertsUtils;
import com.techease.mf.utils.Configuration;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;


public class MyLikesFragment extends Fragment {

    android.support.v7.app.AlertDialog alertDialog;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String email, user_id;
    ArrayList<CollectionModel> myLikes_model_list = new ArrayList<>();
    MyLikesAdapter myLikes_adapter;
    Unbinder unbinder;
    boolean _areLecturesLoaded = false;
    @BindView(R.id.tv_empty_list)
    TextView tvEmptyList;

    @BindView(R.id.rv_myLikes)
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_my_likes, container, false);

        unbinder = ButterKnife.bind(this, v);

        sharedPreferences = getActivity().getSharedPreferences(Configuration.MY_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        email = sharedPreferences.getString("email", "");
        user_id = sharedPreferences.getString("user_id", "");
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myLikes_adapter = new MyLikesAdapter(getActivity(), myLikes_model_list);
        recyclerView.setAdapter(myLikes_adapter);
        if (alertDialog == null)
            alertDialog = AlertsUtils.createProgressDialog(getActivity());
        alertDialog.show();
        getUserFavouriteCollections();
        return v;
    }


    private void getUserFavouriteCollections() {
        WebServices webServices = ApiFactory.create();

        Call<CollectionResponse> call = webServices.getUserFavouriteCollection(user_id);
        call.enqueue(new Callback<CollectionResponse>() {
            @Override
            public void onResponse(Call<CollectionResponse> call, retrofit2.Response<CollectionResponse> response) {
                if (alertDialog != null)
                    alertDialog.dismiss();
                if (response.body() != null) {
                    if (response.body().isSuccess()) {
                        tvEmptyList.setVisibility(View.GONE);
                        if (response.body().getMessage().equals("No Favorites")) {
                            tvEmptyList.setVisibility(View.VISIBLE);
                        } else {
                            myLikes_model_list.addAll(response.body().getCollection());
                            myLikes_adapter.notifyDataSetChanged();
                        }


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
        int position = -1;
        for (int i = 0; i < myLikes_model_list.size(); i++) {
            if (myLikes_model_list.get(i).getId() == model.getId()) {
                myLikes_model_list.get(i).setLiked(model.getLiked());
                position = i;
                hasCollection = true;
                break;
            }
        }
        if (!hasCollection) {
            myLikes_model_list.add(model);
            if (myLikes_adapter != null)
                myLikes_adapter.notifyDataSetChanged();
        } else {
            if (position > -1) {
                myLikes_model_list.remove(position);
                myLikes_adapter.notifyItemRemoved(position);
            }
        }

    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (menuVisible) {
            if (user_id.isEmpty()) {
                startActivity(new Intent(getActivity(), Profile.class));
            }
        }
    }
}
