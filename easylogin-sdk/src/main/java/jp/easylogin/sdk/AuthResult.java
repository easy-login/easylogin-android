package jp.easylogin.sdk;

import jp.easylogin.sdk.api.ApiCallback;

public interface AuthResult {

    int getResponseCode();

    EasyAuthToken getAuthToken();

    ProviderAccessToken getProviderAccessToken();

    ProviderIdToken getProviderIdToken();

    boolean isOpenIdSupport();

    void getProfileAsync(ApiCallback<EasyProfile> callback);

    void activateProfileAsync(ApiCallback<Boolean> callback);
}
