package jp.easylogin.android.sdk;

public interface AuthResult {

    int getResponseCode();

    EasyAuthToken getAuthToken();

    ProviderAccessToken getProviderAccessToken();

    ProviderIdToken getProviderIdToken();

    boolean isOpenIdSupport();

    EasyProfile getProfile();

    boolean activateProfile();
}
