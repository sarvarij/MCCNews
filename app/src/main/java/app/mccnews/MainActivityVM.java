package app.mccnews;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import java.util.List;

import app.mccnews.client.MCCRetrofitClient;
import app.mccnews.models.NewsItemModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityVM extends ViewModel {

    private MutableLiveData<List<NewsItemModel>> newsLiveData = new MutableLiveData<>();
    private MutableLiveData<String> pageTitleLiveData = new MutableLiveData<>();

    public MainActivityVM(){
        pageTitleLiveData.postValue("");
    }

    public void requestNews() {
        MCCRetrofitClient.getAPIInstance().getNewsList().enqueue(new Callback<List<NewsItemModel>>() {
            @Override
            public void onResponse(Call<List<NewsItemModel>> call, Response<List<NewsItemModel>> response) {
                newsLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<NewsItemModel>> call, Throwable t) {
                newsLiveData.setValue(null);
            }
        });
    }

    public void observeNewsList(LifecycleOwner lifecycleOwner, Observer<? super List<NewsItemModel>> observer){
        newsLiveData.observe(lifecycleOwner, observer);
    }

    public void setPageTitle(String pageTitle){
        pageTitleLiveData.setValue(pageTitle);
    }

    public void observePageTitle(LifecycleOwner lifecycleOwner, Observer<? super String> observer){
        pageTitleLiveData.observe(lifecycleOwner, observer);
    }
}
