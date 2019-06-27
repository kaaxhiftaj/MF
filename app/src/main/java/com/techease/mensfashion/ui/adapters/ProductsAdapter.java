package com.techease.mensfashion.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.techease.mensfashion.R;
import com.techease.mensfashion.ui.activities.WebviewActivity;
import com.techease.mensfashion.ui.models.productsModel.CollectionDataModel;
import com.techease.mensfashion.utils.Configuration;

import java.util.ArrayList;

/**
 * Created by kaxhiftaj on 3/6/18.
 */

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.MyViewHolder> {

    ArrayList<CollectionDataModel> productArrayList;
    Context context;
    String collection_id;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String user_id;

    public ProductsAdapter(Context context, ArrayList<CollectionDataModel> productModels) {
        this.context = context;
        this.productArrayList = productModels;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_products, parent, false);

        sharedPreferences = context.getSharedPreferences(Configuration.MY_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        user_id = sharedPreferences.getString("user_id", "");

        return new MyViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final CollectionDataModel model = productArrayList.get(position);

        holder.price.setText(model.getPrice());
        Glide.with(context).load(model.getImage()).into(holder.image);

        holder.amzaon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String collection_id = String.valueOf(model.getId());

                Intent i = new Intent(context, WebviewActivity.class);
                i.putExtra("link", model.getLink());
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {

        return productArrayList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView price;
        ImageView image;
        ImageButton amzaon;


        public MyViewHolder(View itemView) {
            super(itemView);
            price = (TextView) itemView.findViewById(R.id.product_price);
            amzaon = (ImageButton) itemView.findViewById(R.id.amazon);
            image = (ImageView) itemView.findViewById(R.id.product_image);

        }


    }


}

