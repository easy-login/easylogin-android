package jp.easylogin.android.sdk.auth;

import android.content.Intent;
import android.support.annotation.NonNull;

import jp.easylogin.android.sdk.AuthDelegate;

public class AuthDelegateImpl implements AuthDelegate {

    private ProviderAuthHandler authHandler;

    @Override
    public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
        return authHandler != null && authHandler.onActivityResult(requestCode, resultCode, data);
    }

    public void setAuthHandler(@NonNull ProviderAuthHandler authHandler) {
        this.authHandler = authHandler;
    }
}
