package jp.easylogin.android.sdk.auth;

import java.io.IOException;

import jp.easylogin.android.sdk.ApiResponseCode;
import jp.easylogin.android.sdk.AuthResult;
import jp.easylogin.android.sdk.EasyAuthToken;
import jp.easylogin.android.sdk.EasyProfile;
import jp.easylogin.android.sdk.api.EasyLoginService;
import jp.easylogin.android.sdk.api.ServiceGenerator;
import okhttp3.ResponseBody;
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
    public EasyProfile getProfile() {
        try {
            Response<EasyProfile> response = service.getAuthorizedProfile(
                    session.getAuthToken().getTokenString(),
                    session.getCodeVerifier().getRaw()).execute();
            return response.isSuccessful() ? response.body() : null;
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public boolean activateProfile() {
        try {
            Response<ResponseBody> response = service.activateProfile(
                    session.getAuthToken().getTokenString(),
                    session.getCodeVerifier().getRaw()).execute();
            return response.isSuccessful();
        } catch (IOException e) {
            return false;
        }
    }
}
