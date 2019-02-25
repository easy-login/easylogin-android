package jp.easylogin.android.sdk.auth;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jp.easylogin.android.sdk.AuthDelegate;
import jp.easylogin.android.sdk.AuthListener;
import jp.easylogin.android.sdk.AuthResult;
import jp.easylogin.android.sdk.EasyAuthToken;
import jp.easylogin.android.sdk.EasyLogin;
import jp.easylogin.android.sdk.EasyLoginException;
import jp.easylogin.android.sdk.api.EasyLoginService;
import jp.easylogin.android.sdk.api.ServiceGenerator;
import retrofit2.Response;

@SuppressWarnings("ConstantConditions")
public class EasyLoginImpl implements EasyLogin {

    private static final String CALLBACK_URI = "and://00100";

    private AuthSession session;
    private EasyLoginService service;
    private ProviderAuthHandler authHandler;
    private AuthDelegate delegate;
    private final List<AuthListener> listeners = new ArrayList<>();

    private Activity activity;
    private String appId;

    public EasyLoginImpl(Activity activity, String appId) {
        this.activity = activity;
        this.appId = appId;
        this.service = ServiceGenerator.createService();
    }

    @Override
    public void authorize(String provider) {
        if (this.delegate == null) {
            throw new EasyLoginException("You must set AuthDelegate through setAuthDelegate() first");
        }
        try {
            initAuthSession(provider);
            authHandler = ProviderAuthHandler.Factory.create(provider);
            authHandler.setEasyLogin(this);
            authHandler.setAuthSession(session);
            authHandler.performAuthorize(activity, session.getChannel());
        } catch (IOException e) {
            throw new EasyLoginException(e);
        }
    }

    @Override
    public void setAuthDelegate(AuthDelegate delegate) {
        if (!(delegate instanceof AuthDelegateImpl)) {
            throw new EasyLoginException("Unexpected AuthDelegate," +
                    " please use the provided Factory to create the instance");
        }
        this.delegate = delegate;
        ((AuthDelegateImpl) this.delegate).setAuthHandler(authHandler);
    }

    public void onProviderAuthSuccess(String provider, AuthResult result) {
        try {
            String accessToken = result.getProviderAccessToken().getTokenString();
            String idToken = result.getProviderIdToken().getTokenString();
            this.verifyAccessToken(provider, accessToken, idToken);
            this.onAuthSuccess(provider, result);
        } catch (Exception e) {
            this.onAuthFailure(provider, e);
        }
    }

    public void onProviderAuthFailure(String provider, Throwable throwable) {
        this.onAuthFailure(provider, throwable);
    }

    private void initAuthSession(String provider) throws IOException {
        this.session = new AuthSession(provider);
        Response<InitAuthResponse> response = service.authorize(provider, appId, CALLBACK_URI,
                session.getCodeVerifier().getHashed()).execute();
        if (!response.isSuccessful())
            throw new EasyLoginException("Init authorization failed. Status code: " + response.code());

        InitAuthResponse authResponse = response.body();
        session.setChannel(authResponse.getChannel());
        session.setState(authResponse.getState());
    }

    private void verifyAccessToken(String provider, String accessToken, String idToken) throws IOException {
        Response<EasyAuthToken> response = service.verifyToken(
                provider, accessToken, idToken, session.getState()).execute();
        if (!response.isSuccessful())
            throw new EasyLoginException("Verify access token failed. Status code: " + response.code());

        EasyAuthToken authToken = response.body();
        session.setAuthToken(authToken);
    }

    @Override
    public void addAuthListener(@NonNull AuthListener authListener) {
        listeners.add(authListener);
    }

    @Override
    public void removeAuthListener(@NonNull AuthListener authListener) {
        listeners.remove(authListener);
    }

    private void onAuthFailure(@NonNull String provider, @Nullable Throwable throwable) {
        for (AuthListener authListener : listeners) {
            authListener.onAuthFailure(provider, throwable);
        }
    }

    private void onAuthSuccess(@NonNull String provider, @NonNull AuthResult result) {
        for (AuthListener authListener : listeners) {
            authListener.onAuthSuccess(provider, result);
        }
    }
}
