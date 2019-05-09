package com.techease.mensfashion.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
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
import com.techease.mensfashion.LikeListener;
import com.techease.mensfashion.R;
import com.techease.mensfashion.communication.ApiFactory;
import com.techease.mensfashion.communication.WebServices;
import com.techease.mensfashion.communication.response.BaseResponse;
import com.techease.mensfashion.ui.fragments.ProductsFragment;
import com.techease.mensfashion.ui.models.CollectionModel;
import com.techease.mensfashion.utils.Configuration;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by kaxhiftaj on 3/6/18.
 */

public class AllTimeAdapter extends RecyclerView.Adapter<AllTimeAdapter.MyViewHolder> {

    ArrayList<CollectionModel> allArrayList;
    Context context;
    int collection_id;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String user_id;

    public AllTimeAdapter(Context context, ArrayList<CollectionModel> allLikesModels) {
        this.context = context;
        this.allArrayList = allLikesModels;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_alltime, parent, false);

        sharedPreferences = context.getSharedPreferences(Configuration.MY_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        user_id = sharedPreferences.getString("user_id", "");

        return new MyViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final CollectionModel model = allArrayList.get(position);

        holder.noLikes.setText(model.getLikes() + " Likes");
        Glide.with(context).load(model.getImage()).into(holder.item_image);

        if (model.getLiked().equals("true")) {

            model.setLikes(model.getLikes()-1);
            //   holder.like.setImageResource(R.drawable.like);
            holder.like.setBackgroundColor(Color.parseColor("#000000"));
            holder.like.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.like));
            holder.likeLayout.setBackgroundColor(Color.parseColor("#000000"));
            holder.share.setBackgroundColor(Color.parseColor("#000000"));


        } else {

            model.setLikes(model.getLikes()+1);
            //    holder.like.setImageResource(R.drawable.unlike);
            holder.like.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.unlike));
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

                    if (model.getLiked().equals("true")) {
                        model.setLiked("false");
                        holder.like.setBackgroundColor(Color.parseColor("#535c68"));
                        holder.likeLayout.setBackgroundColor(Color.parseColor("#535c68"));
                        holder.share.setBackgroundColor(Color.parseColor("#535c68"));


                    } else {
                        model.setLiked("true");
                        holder.like.setBackgroundColor(Color.parseColor("#000000"));
                        holder.likeLayout.setBackgroundColor(Color.parseColor("#000000"));
                        holder.share.setBackgroundColor(Color.parseColor("#000000"));


                    }
                    LikeListener likeListener = (LikeListener) context;
                    likeListener.onLikePressed(model);
                    WebServices webServices = ApiFactory.create();

                    Call<BaseResponse> call = webServices.likeCollection(user_id, collection_id);
                    call.enqueue(new Callback<BaseResponse>() {
                        @Override
                        public void onResponse(Call<BaseResponse> call, retrofit2.Response<BaseResponse> response) {
                            if (response.body() != null) {
                                //like and unlike done in backend


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

                String collection_id = String.valueOf(model.getId());
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

        return allArrayList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView noLikes;
        ImageView item_image;
        ImageButton share, like;
        LinearLayout likeLayout;


        public MyViewHolder(View itemView) {
            super(itemView);
            noLikes = (TextView) itemView.findViewById(R.id.noLikes);
            share = (ImageButton) itemView.findViewById(R.id.share);
            like = (ImageButton) itemView.findViewById(R.id.like);
            item_image = (ImageView) itemView.findViewById(R.id.item_image);
            likeLayout = (LinearLayout) itemView.findViewById(R.id.likeLayout);

        }


    }


}

