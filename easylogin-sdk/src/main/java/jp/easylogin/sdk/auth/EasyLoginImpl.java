package jp.easylogin.sdk.auth;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import jp.easylogin.sdk.AuthDelegate;
import jp.easylogin.sdk.AuthListener;
import jp.easylogin.sdk.AuthResult;
import jp.easylogin.sdk.EasyAuthToken;
import jp.easylogin.sdk.EasyLogin;
import jp.easylogin.sdk.EasyLoginException;
import jp.easylogin.sdk.api.EasyLoginService;
import jp.easylogin.sdk.api.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("ConstantConditions")
public class EasyLoginImpl implements EasyLogin {

    private static final String CALLBACK_URI = "and://00100";

    private AuthSession session;
    private EasyLoginService service;
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
        initAuthorizeSession(provider);
    }

    @Override
    public void setAuthDelegate(AuthDelegate delegate) {
        if (!(delegate instanceof AuthDelegateImpl)) {
            throw new EasyLoginException("Unexpected AuthDelegate," +
                    " please use the provided Factory to create the instance");
        }
        this.delegate = delegate;
    }

    private void initAuthorizeSession(String provider) {
        session = new AuthSession(provider);
        Call<InitAuthResponse> call = service.authorize(
                provider, appId,
                CALLBACK_URI,
                session.getCodeVerifier().getHashed());
        call.enqueue(new Callback<InitAuthResponse>() {
            @Override
            public void onResponse(@NonNull Call<InitAuthResponse> call,
                                   @NonNull Response<InitAuthResponse> response) {
                if (!response.isSuccessful()) {
                    onAuthFailure(provider, new EasyLoginException(
                            "Init authorization failed. Status code: " + response.code()));
                    return;
                }
                Log.d("EasyLogin", "Init authorize session success");
                onInitAuthorizeSuccess(provider, response.body());
            }

            @Override
            public void onFailure(@NonNull Call<InitAuthResponse> call, @NonNull Throwable t) {
                onAuthFailure(provider, t);
            }
        });
    }

    private void onInitAuthorizeSuccess(String provider, InitAuthResponse response) {
        session.setChannel(response.getChannel());
        session.setState(response.getState());

        ProviderAuthHandler authHandler = ProviderAuthHandler.Factory.create(provider);
        ((AuthDelegateImpl) this.delegate).setAuthHandler(authHandler);

        authHandler.setEasyLogin(this);
        authHandler.setAuthSession(session);
        authHandler.performAuthorize(activity);
    }

    public void onProviderAuthSuccess(String provider, AuthResult result) {
        final String accessToken = result.getProviderAccessToken().getTokenString();
        final String idToken = result.getProviderIdToken().getTokenString();

        Call<EasyAuthToken> call = service.verifyToken(
                provider, accessToken, idToken, session.getState());
        call.enqueue(new Callback<EasyAuthToken>() {
            @Override
            public void onResponse(@NonNull Call<EasyAuthToken> call,
                                   @NonNull Response<EasyAuthToken> response) {
                if (!response.isSuccessful()) {
                    onAuthFailure(provider, new EasyLoginException(
                            "Verify access token failed. Status code: " + response.code()));
                    return;
                }
                Log.d("EasyLoginSDK", "Verify token success");
                session.setAuthToken(response.body());
                onAuthSuccess(provider, result);
            }

            @Override
            public void onFailure(@NonNull Call<EasyAuthToken> call, @NonNull Throwable t) {
                onAuthFailure(provider, t);
            }
        });
    }

    public void onProviderAuthFailure(String provider, Throwable throwable) {
        this.onAuthFailure(provider, throwable);
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
