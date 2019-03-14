package jp.easylogin.sdk;

public interface ProviderAccessToken {

    String getTokenString();

    long getExpiresInMillis();
}
