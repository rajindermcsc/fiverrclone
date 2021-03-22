package dreamguys.in.co.gigs.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static dreamguys.in.co.gigs.utils.AppConstants.ApiURL;

public class ApiClient {

    //    private static final String BASE_URL = "https://dreamguys.co.in/thegigs/api/";
//    private static final String BASE_URL = "https://dreamguys.co.in/demo/gigs/api/";
//    private static final String BASE_URL = "http://172.16.1.225:8080/gigsrtl/api/";
    private static Retrofit retrofit = null;


    public static Retrofit getClient() {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor()
                        .setLevel(HttpLoggingInterceptor.Level.BODY))
                .connectTimeout(1000, TimeUnit.SECONDS)
                .readTimeout(1000, TimeUnit.SECONDS)
                .writeTimeout(1000, TimeUnit.SECONDS);
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(ApiURL)
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}