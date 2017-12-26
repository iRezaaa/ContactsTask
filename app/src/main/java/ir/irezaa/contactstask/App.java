package ir.irezaa.contactstask;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by rezapilehvar on 26/12/2017 AD.
 */

public class App extends Application {
    private static App instance;

    public static App getInstance() {
        return instance;
    }

    private OkHttpClient okHttpClientInstance;
    private Retrofit retrofitInstance;
    private Gson gsonInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public Retrofit getRetrofitInstance() {
        if (retrofitInstance == null) {
            retrofitInstance = new Retrofit.Builder()
                    .baseUrl("http://hkns.ir/")
                    .client(getOkHttpClientInstance())
                    .addConverterFactory(GsonConverterFactory.create(getGsonInstance()))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }

        return retrofitInstance;
    }

    public OkHttpClient getOkHttpClientInstance() {
        if (okHttpClientInstance == null) {
            okHttpClientInstance = new OkHttpClient.Builder().build();
        }

        return okHttpClientInstance;
    }

    public Gson getGsonInstance() {
        if (gsonInstance == null) {
            gsonInstance = new GsonBuilder().serializeNulls().create();
        }

        return gsonInstance;
    }
}
