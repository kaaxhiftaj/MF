package com.techease.mf.ui.adapters;

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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.techease.mf.R;
import com.techease.mf.ui.fragments.ProductsFragment;
import com.techease.mf.ui.models.CollectionModel;
import com.techease.mf.utils.Configuration;

import java.util.ArrayList;

/**
 * Created by kaxhiftaj on 3/6/18.
 */

public class MyLikesAdapter extends RecyclerView.Adapter<MyLikesAdapter.MyViewHolder> {

    ArrayList<CollectionModel> myLikesArrayList;
    Context context;
    int collection_id;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String user_id;

    public MyLikesAdapter(Context context, ArrayList<CollectionModel> myLikesModels) {
        this.context = context;
        this.myLikesArrayList = myLikesModels;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_mylikes, parent, false);

        sharedPreferences = context.getSharedPreferences(Configuration.MY_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        user_id = sharedPreferences.getString("user_id", "");

        return new MyViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final CollectionModel model = myLikesArrayList.get(position);

        holder.noLikes.setText(model.getLikes() + " Likes");
        Glide.with(context).load(model.getImage()).into(holder.item_image);


        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                collection_id = model.getId();
                if (!user_id.equals("")) {
                    holder.like.setImageResource(R.drawable.like);
                } else {
                    Toast.makeText(context, "Please Login First", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.item_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int collection_id = model.getId();

                Intent i = new Intent(context, ProductsFragment.class);
                i.putExtra("collection_id", String.valueOf(collection_id));
                context.startActivity(i);

            }
        });

        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = model.getFacebook();
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT, message);
                context.startActivity(Intent.createChooser(share, "Share"));
            }
        });

    }

    @Override
    public int getItemCount() {

        return myLikesArrayList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView noLikes;
        ImageView item_image;
        ImageButton share, like;


        public MyViewHolder(View itemView) {
            super(itemView);
            noLikes = (TextView) itemView.findViewById(R.id.noLikes);
            share = (ImageButton) itemView.findViewById(R.id.share);
            like = (ImageButton) itemView.findViewById(R.id.like);
            item_image = (ImageView) itemView.findViewById(R.id.item_image);

        }


    }

}

