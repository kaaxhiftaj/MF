package com.techease.mf.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.techease.mf.LikeListener;
import com.techease.mf.R;
import com.techease.mf.communication.ApiFactory;
import com.techease.mf.communication.WebServices;
import com.techease.mf.communication.response.BaseResponse;
import com.techease.mf.ui.fragments.ProductsFragment;
import com.techease.mf.ui.models.CollectionModel;
import com.techease.mf.utils.Configuration;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by kaxhiftaj on 2/27/18.
 */

public class NewAdapter extends RecyclerView.Adapter<NewAdapter.MyViewHolder> {

    ArrayList<CollectionModel> newArrayList;
    Context context;
    int collection_id;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String user_id;

    public NewAdapter(Context context, ArrayList<CollectionModel> newModels) {
        this.context = context;
        this.newArrayList = newModels;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_new, parent, false);

        sharedPreferences = context.getSharedPreferences(Configuration.MY_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        user_id = sharedPreferences.getString("user_id", "");


        return new MyViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final CollectionModel model = newArrayList.get(position);

        holder.noLikes.setText(model.getLikes() + " Likes");
        Glide.with(context).load(model.getImage()).into(holder.item_image);

        if (model.getLiked().equals("true")) {

            //   holder.like.setImageResource(R.drawable.like);
            holder.like.setBackgroundColor(Color.parseColor("#000000"));
            holder.likeLayout.setBackgroundColor(Color.parseColor("#000000"));
            holder.share.setBackgroundColor(Color.parseColor("#000000"));

        } else {
            //    holder.like.setImageResource(R.drawable.unlike);
            holder.like.setBackgroundColor(Color.parseColor("#535c68"));
            holder.likeLayout.setBackgroundColor(Color.parseColor("#535c68"));
            holder.share.setBackgroundColor(Color.parseColor("#535c68"));
        }

        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                collection_id = model.getId();
                if (!user_id.equals("")) {
                    holder.like.setBackgroundColor(Color.parseColor("#000000"));
                    holder.likeLayout.setBackgroundColor(Color.parseColor("#000000"));
                    holder.share.setBackgroundColor(Color.parseColor("#000000"));
                    model.setLiked("true");
                    LikeListener likeListener = (LikeListener) context;
                    likeListener.onLikePressed(model);
                    WebServices webServices = ApiFactory.create();

                    Call<BaseResponse> call = webServices.likeCollection(user_id, collection_id);
                    call.enqueue(new Callback<BaseResponse>() {
                        @Override
                        public void onResponse(Call<BaseResponse> call, retrofit2.Response<BaseResponse> response) {
                            if (response.body() != null) {
                                if (response.body().isSuccess()) {
                                    holder.like.setBackgroundColor(Color.parseColor("#000000"));
                                    holder.likeLayout.setBackgroundColor(Color.parseColor("#000000"));
                                    holder.share.setBackgroundColor(Color.parseColor("#000000"));
                                    model.setLiked("true");
                                } else {
                                    holder.like.setBackgroundColor(Color.parseColor("#535c68"));
                                    holder.likeLayout.setBackgroundColor(Color.parseColor("#535c68"));
                                    holder.share.setBackgroundColor(Color.parseColor("#535c68"));
                                    model.setLiked("false");
                                }
                            } else {
                                //todo show failure message
                            }
                        }

                        @Override
                        public void onFailure(Call<BaseResponse> call, Throwable t) {
                            //todo show failure message
                        }
                    });

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
                i.putExtra("collection_id", collection_id);
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

        return newArrayList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView noLikes;
        ImageView item_image;
        ImageButton share, like;
        LinearLayout likeLayout;


        public MyViewHolder(View itemView) {
            super(itemView);
            noLikes = itemView.findViewById(R.id.noLikes);
            share = itemView.findViewById(R.id.share);
            like = itemView.findViewById(R.id.like);
            item_image = itemView.findViewById(R.id.item_image);
            likeLayout = itemView.findViewById(R.id.likeLayout);

        }


    }

}

