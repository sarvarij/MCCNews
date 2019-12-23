package app.mccnews.client;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MCCRetrofitClient {
    private static final String BASE_URL = "https://mcc.hu/api/app/";


    private static MCCApiInterface mccApiInterface;

    public static MCCApiInterface getAPIInstance(){
        if (mccApiInterface == null){
            mccApiInterface = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(MCCApiInterface.class);
        }

        return mccApiInterface;
    }
}


