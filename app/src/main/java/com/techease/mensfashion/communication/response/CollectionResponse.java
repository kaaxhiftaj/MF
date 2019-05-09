package com.techease.mensfashion.communication.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.techease.mensfashion.ui.models.CollectionModel;

import java.util.List;

/**
 * Created by k.zahid on 3/28/18.
 */

public class CollectionResponse extends BaseResponse {
    @SerializedName("collection")
    @Expose
    private List<CollectionModel> collection = null;

    public List<CollectionModel> getCollection() {
        return collection;
    }

    public void setCollection(List<CollectionModel> collection) {
        this.collection = collection;
    }
}
