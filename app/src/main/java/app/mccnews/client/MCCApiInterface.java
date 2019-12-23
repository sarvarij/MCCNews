package app.mccnews.client;

import java.util.List;

import app.mccnews.models.NewsItemModel;
import retrofit2.Call;
import retrofit2.http.GET;

public interface MCCApiInterface {
    @GET("article_types/5729fc387fdea7e267fa9761")
    Call<List<NewsItemModel>> getNewsList();
}
