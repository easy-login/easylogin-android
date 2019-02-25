package jp.easylogin.android.sdk;

public interface ProviderAccessToken {

    String getTokenString();

    long getExpiresInMillis();
}
