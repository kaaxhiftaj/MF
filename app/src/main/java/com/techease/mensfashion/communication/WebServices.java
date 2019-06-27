package com.techease.mensfashion.communication;

import com.techease.mensfashion.communication.response.BaseResponse;
import com.techease.mensfashion.communication.response.CollectionResponse;
import com.techease.mensfashion.ui.models.facebookSignUp.FacebookSignUpResponseModel;
import com.techease.mensfashion.ui.models.productsModel.CollectionProductsResponseModel;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by k.zahid on 3/28/18.
 */

public interface WebServices {
    OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build();

    WebServices SERVICE = new Retrofit.Builder()
            .baseUrl("http://116.203.128.245/index.php/restapi/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(WebServices.class);


    @FormUrlEncoded
    @POST("userFavorites")
    Call<CollectionResponse> getUserFavouriteCollection(@Field("user_id") String userId);

    @FormUrlEncoded
    @POST("collection")
    Call<CollectionResponse> getCollection(@Field("user_id") String userId);

    @FormUrlEncoded
    @POST("collectionTrending")
    Call<CollectionResponse> getTrendingCollection(@Field("user_id") String userId);

    @FormUrlEncoded
    @POST("collectionByWeek")
    Call<CollectionResponse> getCollectionByWeek(@Field("user_id") String userId);

    @FormUrlEncoded
    @POST("collectionByMonth")
    Call<CollectionResponse> getCollectionByMonth(@Field("user_id") String userId);

    @FormUrlEncoded
    @POST("userliked")
    Call<BaseResponse> likeCollection(@Field("user_id") String userId, @Field("collection_id") int collectionId);


    @FormUrlEncoded
    @POST("signup")
    Call<FacebookSignUpResponseModel> facebooSignUp(@Field("email") String email);


    @FormUrlEncoded
    @POST("collectionProducts")
    Call<CollectionProductsResponseModel> collectionProducts(@Field("collection_id") String collection_id);


}
