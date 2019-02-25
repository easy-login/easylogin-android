package jp.easylogin.android.sdk.api;

import jp.easylogin.android.sdk.EasyProfile;
import jp.easylogin.android.sdk.auth.InitAuthResponse;
import jp.easylogin.android.sdk.EasyAuthToken;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface EasyLoginService {

    @GET("/auth/{provider}?platform=and")
    Call<InitAuthResponse> authorize(@Path("provider") String provider,
                                     @Query("app_id") String appId,
                                     @Query("callback_uri") String callbackUri,
                                     @Query("code_challenge") String codeChallenge);

    @FormUrlEncoded
    @POST("/auth/{provider}/verify-token")
    Call<EasyAuthToken> verifyToken(@Path("provider") String provider,
                                    @Query("access_token") String accessToken,
                                    @Query("id_token") String idToken,
                                    @Query("state") String state);

    @FormUrlEncoded
    @POST("/auth/profles/authorized")
    Call<EasyProfile> getAuthorizedProfile(@Query("auth_token") String authToken,
                                           @Query("code_verifier") String codeVerifier);

    @FormUrlEncoded
    @POST("/auth/profles/authorized")
    Call<ResponseBody> activateProfile(@Query("auth_token") String authToken,
                                       @Query("code_verifier") String codeVerifier);
}
