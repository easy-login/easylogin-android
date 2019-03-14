package jp.easylogin.sdk;

import android.app.Activity;
import android.support.annotation.NonNull;

import jp.easylogin.sdk.auth.EasyLoginImpl;

public interface EasyLogin {

    void authorize(String provider);

    void setAuthDelegate(AuthDelegate delegate);

    void addAuthListener(@NonNull AuthListener authListener);

    void removeAuthListener(@NonNull AuthListener authListener);

    class Factory {
        public static EasyLogin create(Activity activity, String appId) {
            return new EasyLoginImpl(activity, appId);
        }
    }
}
