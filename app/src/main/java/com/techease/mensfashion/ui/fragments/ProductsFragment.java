package com.techease.mensfashion.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import com.techease.mensfashion.R;
import com.techease.mensfashion.communication.ApiFactory;
import com.techease.mensfashion.communication.WebServices;
import com.techease.mensfashion.ui.activities.HomeActivity;
import com.techease.mensfashion.ui.activities.Profile;
import com.techease.mensfashion.ui.adapters.ProductsAdapter;
import com.techease.mensfashion.ui.models.ProductsModel;
import com.techease.mensfashion.ui.models.facebookSignUp.FacebookSignUpResponseModel;
import com.techease.mensfashion.ui.models.productsModel.CollectionDataModel;
import com.techease.mensfashion.ui.models.productsModel.CollectionProductsResponseModel;
import com.techease.mensfashion.utils.AlertsUtils;
import com.techease.mensfashion.utils.Configuration;
import com.techease.mensfashion.utils.InternetUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;

import static com.techease.mensfashion.communication.BaseConfig.BASE_URL;


public class ProductsFragment extends AppCompatActivity {

    android.support.v7.app.AlertDialog alertDialog;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String email, user_id;
    ArrayList<CollectionDataModel> products_model_list;
    ProductsAdapter product_adapter;
    Unbinder unbinder;
    String collection_id;
    @BindView(R.id.rv_products)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_products);

        unbinder = ButterKnife.bind(this);
        customActionBar();

        sharedPreferences = getSharedPreferences(Configuration.MY_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        email = sharedPreferences.getString("email", "");
        user_id = sharedPreferences.getString("user_id", "");


        collection_id = getIntent().getExtras().getString("collection_id");

        if (InternetUtils.isNetworkConnected(ProductsFragment.this)) {

            recyclerView.setLayoutManager(new LinearLayoutManager(ProductsFragment.this));
            products_model_list = new ArrayList<>();
            collectionProductsApiCall();
            if (alertDialog == null)
                alertDialog = AlertsUtils.createProgressDialog(ProductsFragment.this);
            alertDialog.show();
            product_adapter = new ProductsAdapter(ProductsFragment.this, products_model_list);
            recyclerView.setAdapter(product_adapter);

        } else {
            Toast.makeText(ProductsFragment.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }


    }

    private void collectionProductsApiCall() {


        WebServices webServices = ApiFactory.create();

        Call<CollectionProductsResponseModel> call = webServices.collectionProducts(collection_id);
        call.enqueue(new Callback<CollectionProductsResponseModel>() {
            @Override
            public void onResponse(Call<CollectionProductsResponseModel> call, retrofit2.Response<CollectionProductsResponseModel> response) {

                if (alertDialog != null)
                    alertDialog.dismiss();

                if (response.body() != null) {
                    products_model_list.clear();
                    products_model_list.addAll(response.body().getCollection());
                    product_adapter.notifyDataSetChanged();

                } else {
                    //todo show failure message

                }
            }

            @Override
            public void onFailure(Call<CollectionProductsResponseModel> call, Throwable t) {
                //todo show failure message

                if (alertDialog != null)
                    alertDialog.dismiss();

                Log.d("errorrPorducts", String.valueOf(t));
            }
        });


    }


    public void customActionBar() {

        android.support.v7.app.ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        LayoutInflater mInflater = LayoutInflater.from(ProductsFragment.this);
        View mCustomView = mInflater.inflate(R.layout.custom_main_actionbar, null);
        ImageButton profile = (ImageButton) mCustomView.findViewById(R.id.profile);
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v7.app.ActionBar mActionBar = getSupportActionBar();
                startActivity(new Intent(ProductsFragment.this, Profile.class));
            }
        });

    }
}
