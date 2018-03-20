package com.techease.mf.ui.models;

/**
 * Created by kaxhiftaj on 3/6/18.
 */

public class AllTimeModel {


    String id;
    String name;
    String image;
    String noLikes;
    String facebook;
    String liked ;

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getNoLikes() {
        return noLikes;
    }

    public void setNoLikes(String noLikes) {
        this.noLikes = noLikes;
    }

    public String getLiked() {
        return liked;
    }

    public void setLiked(String liked) {
        this.liked = liked;
    }
}
