package com.pvirtech.pzpolice.http;


import com.pvirtech.pzpolice.http.convert.CustomGsonConverterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by pd on 2016/9/30.
 */

public class RetrofitHttp {
//        public static final String SERVER_URL = "http://192.168.30.4:8080/PMS/";
    public static  String SERVER_URL = "http://192.168.10.209:8080/PMS/";
//    public static final String SERVER_URL = "http://171.221.245.121:18080/PMS/";
    public final static int CONNECT_TIMEOUT = 10;
    public final static int READ_TIMEOUT = 10;
    public final static int WRITE_TIMEOUT = 10; //设置读取超时为100秒
    public static HttpApi httpApi;
    public static HttpApi httpApiFile;

    public static HttpApi provideClientApi() {
        if (null == httpApi) {
            synchronized (RetrofitHttp.class) {
                if (null == httpApi) {
                    Retrofit retrofit = new Retrofit.Builder()
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .addConverterFactory(CustomGsonConverterFactory.create())
                            .baseUrl(SERVER_URL)
//                .client(genericClient())
                            .client(genericClientWithTimeOut())
                            .build();
                    httpApi = retrofit.create(HttpApi.class);
                }
            }
        }
        return httpApi;
    }

    public static OkHttpClient genericClient() {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request()
                                .newBuilder()
                                .addHeader("Content-Type", "application/json;charset=UTF-8")
                                .build();
                        return chain.proceed(request);
                    }

                })
                .build();
        return httpClient;
    }

    public static OkHttpClient genericClientWithTimeOut() {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request()
                                .newBuilder()
                                .addHeader("Content-Type", "application/json;charset=UTF-8")
                                .build();
                        return chain.proceed(request);
                    }

                })
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)//设置写的超时时间
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)//设置连接超时时间
                .build();
        return httpClient;
    }


    /**
     * 文件服务器
     *
     * @return
     */
    public static HttpApi provideClientApiFile() {
        if (null == httpApiFile) {
            Retrofit retrofit = new Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(SERVER_URL)
//                .client(genericClient())
                    .client(genericClientWithTimeOutFile())
                    .build();
            httpApiFile = retrofit.create(HttpApi.class);
        }
        return httpApiFile;
    }

    public static OkHttpClient genericClientWithTimeOutFile() {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request()
                                .newBuilder()
                                .addHeader("Content-Type", "application/json;charset=UTF-8")
                                .build();
                        return chain.proceed(request);
                    }

                })
                .readTimeout(600, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(600, TimeUnit.SECONDS)//设置写的超时时间
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)//设置连接超时时间
                .build();
        return httpClient;
    }
}
