package com.techease.mf.ui.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.techease.mf.R;
import com.techease.mf.ui.models.MyLikesModel;
import com.techease.mf.ui.models.NewModel;
import com.techease.mf.utils.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kaxhiftaj on 3/6/18.
 */

public class MyLikesAdapter extends RecyclerView.Adapter<MyLikesAdapter.MyViewHolder> {

    ArrayList<MyLikesModel> myLikesArrayList;
    Context context;
    String collection_id;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String user_id ;

    public MyLikesAdapter(Context context, ArrayList<MyLikesModel> myLikesModels) {
        this.context=context;
        this.myLikesArrayList = myLikesModels ;
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
        final MyLikesModel model= myLikesArrayList.get(position);

        holder.noLikes.setText(model.getNoLikes()+ " Likes");
        Glide.with(context).load(model.getImage()).into(holder.item_image);

        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                collection_id = model.getId();
                apicall();
            }
        });

    }

    @Override
    public int getItemCount() {

        return myLikesArrayList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView noLikes;
        ImageView item_image ;
        ImageButton share, like;


        public MyViewHolder(View itemView) {
            super(itemView);
            noLikes =(TextView)itemView.findViewById(R.id.noLikes);
            share=(ImageButton) itemView.findViewById(R.id.share);
            like = (ImageButton) itemView.findViewById(R.id.like);
            item_image = (ImageView) itemView.findViewById(R.id.item_image);

        }


    }




    private void apicall() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://menfashion.techeasesol.com/restapi/userliked"
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.contains("true")) {

                    Toast.makeText(context, "Liked", Toast.LENGTH_SHORT).show();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded;charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", "user_id");
                params.put("collection_id", collection_id);
                return params;
            }

        };
        RequestQueue mRequestQueue = Volley.newRequestQueue(context);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(stringRequest);
    }
}

