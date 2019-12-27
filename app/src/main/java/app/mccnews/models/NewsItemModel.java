package app.mccnews.models;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import lombok.Data;

@Data
@Parcel
public class NewsItemModel {
    @SerializedName("id")
    protected String id;

    @SerializedName("name")
    protected String name;

    @SerializedName("subtitle")
    protected String subtitle;

    @SerializedName("external_link")
    protected String externalLink;

    @SerializedName("created")
    protected String created;

    @SerializedName("thumbnail")
    protected String thumbnail;
}
