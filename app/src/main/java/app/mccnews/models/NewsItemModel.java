package app.mccnews.models;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class NewsItemModel {
    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("subtitle")
    private String subtitle;

    @SerializedName("external_link")
    private String externalLink;

    @SerializedName("created")
    private String created;

    @SerializedName("thumbnail")
    private String thumbnail;
}
