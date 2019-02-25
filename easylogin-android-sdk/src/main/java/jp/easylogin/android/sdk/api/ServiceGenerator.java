package jp.easylogin.android.sdk.api;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class ServiceGenerator {

    private static final Retrofit retrofit = new Retrofit.Builder()
//            .baseUrl("https://api.easy-login.jp/")
            .baseUrl("http://localhost:5000/")
            .addConverterFactory(JacksonConverterFactory.create())
            .build();

    public static EasyLoginService createService() {
        return retrofit.create(EasyLoginService.class);
    }
}
