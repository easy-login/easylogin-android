package jp.easylogin.sdk;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface AuthListener {

    void onAuthSuccess(String provider, @NonNull AuthResult result);

    void onAuthFailure(String provider, @Nullable Throwable throwable);
}
