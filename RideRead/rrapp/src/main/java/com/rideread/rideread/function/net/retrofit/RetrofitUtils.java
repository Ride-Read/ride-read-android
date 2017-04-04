package com.rideread.rideread.function.net.retrofit;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rideread.rideread.BuildConfig;
import com.rideread.rideread.common.util.NetworkUtils;
import com.rideread.rideread.common.util.UserUtils;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.CacheControl;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by SkyXiao on 2017/1/20.
 */

public class RetrofitUtils {
    private final static int TIMEOUT_SECONDS = 10;
    private static Retrofit retrofit;
    private static final String BaseUrl = "http://121.42.195.113/rideread/";


    public static Retrofit getInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BaseUrl).addConverterFactory(GsonConverterFactory.create(getGson())).client(configClient()).build();
        }
        return retrofit;
    }

    private static OkHttpClient configClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS).readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS).writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS);
        //                .retryOnConnectionFailure(true);
        try {
            final X509TrustManager tm = new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }

                @Override
                public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                    // Not implemented
                }

                @Override
                public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                    // Not implemented
                }
            };

            final SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{tm}, new java.security.SecureRandom());
            builder.sslSocketFactory(sc.getSocketFactory(), tm).hostnameVerifier((hostname, session) -> hostname.contains(".195."));
            if (BuildConfig.DEBUG) {
                // Log信息拦截器
                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                //设置 Debug Log 模式
                builder.addInterceptor(loggingInterceptor);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return builder.build();
    }

    /**
     * 设置公共参数
     */
    private static Interceptor addQueryParameterInterceptor() {
        Interceptor addQueryParameterInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Request request;
                HttpUrl modifiedUrl = originalRequest.url().newBuilder()
                        .addQueryParameter("", Integer.toString(UserUtils.getUid())).addQueryParameter("token", UserUtils.getToken()).build();;
                if (0!= UserUtils.getUid()){
                     modifiedUrl = originalRequest.url().newBuilder()
                            // Provide your custom parameter here
                            .addQueryParameter("uid", Integer.toString(UserUtils.getUid())).addQueryParameter("token", UserUtils.getToken()).build();
                }

                request = originalRequest.newBuilder().url(modifiedUrl).build();
                return chain.proceed(request);
            }
        };
        return addQueryParameterInterceptor;
    }

    /**
     * 设置头
     */
    private static Interceptor addHeaderInterceptor() {
        Interceptor headerInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Request.Builder requestBuilder = originalRequest.newBuilder()
                        // Provide your custom header here
                        .header("AppType", "TPOS").header("Accept", "application/json").method(originalRequest.method(), originalRequest.body());
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        };
        return headerInterceptor;
    }

    /**
     * 设置缓存
     */
    private static Interceptor addCacheInterceptor() {
        Interceptor cacheInterceptor = chain -> {
            Request request = chain.request();
            if (!NetworkUtils.isConnected()) {
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
            }
            Response response = chain.proceed(request);
            if (NetworkUtils.isConnected()) {
                int maxAge = 0;
                // 有网络时 设置缓存超时时间0个小时
                response.newBuilder().header("Cache-Control", "public, max-age=" + maxAge).removeHeader("Retrofit")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                        .build();
            } else {
                // 无网络时，设置超时为4周
                int maxStale = 60 * 60 * 24 * 28;
                response.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale).removeHeader("nyn").build();
            }
            return response;
        };
        return cacheInterceptor;
    }


    public static Gson getGson() {
        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        return gson;
    }

}
