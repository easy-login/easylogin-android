package jp.easylogin.sdk;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public interface AuthListener {

    void onAuthSuccess(String provider, @NonNull AuthResult result);

    void onAuthFailure(String provider, @Nullable Throwable throwable);
}
