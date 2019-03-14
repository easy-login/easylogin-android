package jp.easylogin.android.sdk.api;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class ServiceGenerator {

    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.easy-login.jp/")
//            .baseUrl("http://192.168.1.55:5000/")
            .client(createOkHttpClient())
            .addConverterFactory(JacksonConverterFactory.create())
            .build();

    public static EasyLoginService createService() {
        return retrofit.create(EasyLoginService.class);
    }

    private static OkHttpClient createOkHttpClient() {
        try {
            final TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {}

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {}

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            return new OkHttpClient.Builder()
                    .sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) trustAllCerts[0])
//                    .sslSocketFactory(sslContext.getSocketFactory())
                    .hostnameVerifier((hostname, session) -> true)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
