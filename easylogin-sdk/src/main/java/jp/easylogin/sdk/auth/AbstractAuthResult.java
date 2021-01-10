package jp.easylogin.sdk.auth;

import androidx.annotation.NonNull;

import jp.easylogin.sdk.AuthResult;
import jp.easylogin.sdk.EasyAuthToken;
import jp.easylogin.sdk.EasyLoginException;
import jp.easylogin.sdk.EasyProfile;
import jp.easylogin.sdk.api.ApiCallback;
import jp.easylogin.sdk.api.ApiResponse;
import jp.easylogin.sdk.api.ApiResponseCode;
import jp.easylogin.sdk.api.BooleanResponse;
import jp.easylogin.sdk.api.EasyLoginService;
import jp.easylogin.sdk.api.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class AbstractAuthResult implements AuthResult {

    private final EasyLoginService service;
    private final AuthSession session;

    public AbstractAuthResult(AuthSession session) {
        this.service = ServiceGenerator.createService();
        this.session = session;
    }

    @Override
    public int getResponseCode() {
        return ApiResponseCode.SUCCESS;
    }

    @Override
    public EasyAuthToken getAuthToken() {
        return session.getAuthToken();
    }

    @Override
    public void getProfileAsync(ApiCallback<EasyProfile> callback) {
        Call<EasyProfile> call = service.getAuthorizedProfile(
                session.getAuthToken().getTokenString(),
                session.getCodeVerifier().getRaw());
        call.enqueue(new Callback<EasyProfile>() {
            @Override
            public void onResponse(@NonNull Call<EasyProfile> call,
                                   @NonNull Response<EasyProfile> response) {
                final int code = response.code();
                if (!response.isSuccessful()) {
                    callback.onResponse(ApiResponse.buildAsError(code,
                            new EasyLoginException("Server response with status code: " + code)));
                    return;
                }
                callback.onResponse(ApiResponse.buildAsSuccess(code, response.body()));
            }

            @Override
            public void onFailure(@NonNull Call<EasyProfile> call, @NonNull Throwable t) {
                callback.onResponse(ApiResponse.buildAsError(ApiResponseCode.SERVER_ERROR, t));
            }
        });
    }

    @Override
    public void activateProfileAsync(ApiCallback<Boolean> callback) {
        Call<BooleanResponse> call = service.activateProfile(
                session.getAuthToken().getTokenString(),
                session.getCodeVerifier().getRaw());
        call.enqueue(new Callback<BooleanResponse>() {
            @Override
            public void onResponse(@NonNull Call<BooleanResponse> call,
                                   @NonNull Response<BooleanResponse> response) {
                final int code = response.code();
                if (!response.isSuccessful()) {
                    callback.onResponse(ApiResponse.buildAsError(code,
                            new EasyLoginException("Server response with status code: " + code)));
                    return;
                }
                callback.onResponse(ApiResponse.buildAsSuccess(code,
                        response.body() != null && response.body().isSuccess()));
            }

            @Override
            public void onFailure(@NonNull Call<BooleanResponse> call, @NonNull Throwable t) {
                callback.onResponse(ApiResponse.buildAsError(ApiResponseCode.SERVER_ERROR, t));
            }
        });
    }
}
