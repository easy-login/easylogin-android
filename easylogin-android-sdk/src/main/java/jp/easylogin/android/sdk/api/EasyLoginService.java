package jp.easylogin.android.sdk.api;

import jp.easylogin.android.sdk.EasyAuthToken;
import jp.easylogin.android.sdk.EasyProfile;
import jp.easylogin.android.sdk.auth.InitAuthResponse;
import retrofit2.Call;
import retrofit2.http.Field;
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
                                    @Field("access_token") String accessToken,
                                    @Field("id_token") String idToken,
                                    @Field("state") String state);

    @FormUrlEncoded
    @POST("/auth/profiles/authorized")
    Call<EasyProfile> getAuthorizedProfile(@Field("auth_token") String authToken,
                                           @Field("code_verifier") String codeVerifier);

    @FormUrlEncoded
    @POST("/auth/profiles/activate")
    Call<BooleanResponse> activateProfile(@Field("auth_token") String authToken,
                                          @Field("code_verifier") String codeVerifier);
}
