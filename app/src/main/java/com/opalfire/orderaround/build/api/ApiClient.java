package com.opalfire.orderaround.build.api;

import android.util.Log;

import com.opalfire.orderaround.build.configure.BuildConfigure;
import com.opalfire.orderaround.helper.GlobalData;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.fabric.sdk.android.services.network.HttpRequest;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request.Builder;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit;

    public static Retrofit getRetrofit() {
        OkHttpClient client = getClient();
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BuildConfigure.BASE_URL).addConverterFactory(GsonConverterFactory.create()).client(client).build();
        }
        return retrofit;
    }

    private static OkHttpClient getClient() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(Level.BODY);
        OkHttpClient build = new OkHttpClient.Builder().addNetworkInterceptor(httpLoggingInterceptor).addNetworkInterceptor(new AddHeaderInterceptor()).connectTimeout(1, TimeUnit.MINUTES).readTimeout(1, TimeUnit.MINUTES).retryOnConnectionFailure(true).build();
        build.connectionPool().evictAll();
        return build;
    }

    public static class AddHeaderInterceptor implements Interceptor {
        public Response intercept(Chain chain) throws IOException {
            Builder newBuilder = chain.request().newBuilder();
            newBuilder.addHeader("X-Requested-With", "XMLHttpRequest");
            String str = HttpRequest.HEADER_AUTHORIZATION;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("");
            stringBuilder.append(GlobalData.accessToken);
            newBuilder.addHeader(str, stringBuilder.toString());
            Log.e("access_token", GlobalData.accessToken);
            return chain.proceed(newBuilder.build());
        }
    }
}
