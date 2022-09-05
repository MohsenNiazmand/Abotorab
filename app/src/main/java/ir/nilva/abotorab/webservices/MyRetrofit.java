package ir.nilva.abotorab.webservices;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import ir.nilva.abotorab.helper.CacheHelperKt;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyRetrofit {

    private static String BASE_URL = CacheHelperKt.defaultCache().getString("base_url",
            "https://depository.ceshora.ir/");
//    public static String BASE_URL = "http://37.32.27.176/";
    private static MyRetrofit instance;

    public static MyRetrofit getInstance() {
        if (instance == null) {
            instance = new MyRetrofit();
        }
        return instance;
    }

    public static void newInstance(){
        instance = new MyRetrofit();
    }

    WebserviceUrls urls;

    public WebserviceUrls getWebserviceUrls() {
        if (urls == null) {
            urls = getUrls();
        }

        return urls;
    }

    private WebserviceUrls getUrls() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(interceptor);
        addAuthHeader(builder);
        builder.connectTimeout(30, TimeUnit.SECONDS);
        builder.readTimeout(30, TimeUnit.SECONDS);
        builder.writeTimeout(30, TimeUnit.SECONDS);
        OkHttpClient client = builder.build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        return retrofit.create(WebserviceUrls.class);
    }


    private void addAuthHeader(OkHttpClient.Builder client) {
        final String token = CacheHelperKt.defaultCache().getString("token", "");
        final String depository = CacheHelperKt.defaultCache().getString("depository_code", "");
        if (token.isEmpty()) return;
        client.addInterceptor(chain -> {
            Request original = chain.request();
            Request request = original.newBuilder()
                    .addHeader("Authorization", "JWT " + token)
//                    .addHeader("DEPOSITORY-ID", Objects.requireNonNull(CacheHelperKt.defaultCache().getString("depository_code", "")))
                    .addHeader("DEPOSITORY-ID", depository)
                    .build();

            return chain.proceed(request);
        });
    }

    public static void setBaseUrl(String ip){
        CacheHelperKt.defaultCache().edit().putString("base_url", ip).apply();
        BASE_URL = ip;
        newInstance();
    }

    public static String getBaseUrl(){
        return BASE_URL;
    }

}
