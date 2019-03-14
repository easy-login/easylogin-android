package jp.easylogin.android.sdk.auth;

import android.app.Activity;

import com.linecorp.linesdk.LoginDelegate;

import jp.easylogin.android.sdk.EasyLogin;
import jp.easylogin.android.sdk.auth.line.LineAuthHandler;

public interface ProviderAuthHandler extends LoginDelegate {

    void setEasyLogin(EasyLogin easyLogin);

    void setAuthSession(AuthSession authSession);

    void performAuthorize(Activity activity);

    String getProviderName();

    class Factory {
        static ProviderAuthHandler create(String provider) {
            switch (provider) {
                case "line":
                    return new LineAuthHandler();
                default:
                    throw new RuntimeException("Invalid provider");
            }
        }
    }
}
