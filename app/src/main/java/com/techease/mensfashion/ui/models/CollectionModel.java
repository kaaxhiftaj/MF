package com.techease.mensfashion.ui.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by k.zahid on 3/28/18.
 */

public class CollectionModel {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("facebook")
    @Expose
    private String facebook;
    @SerializedName("likes")
    @Expose
    private int likes;
    @SerializedName("liked")
    @Expose
    private String liked;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getLiked() {
        return liked;
    }

    public void setLiked(String liked) {
        this.liked = liked;
    }
}
