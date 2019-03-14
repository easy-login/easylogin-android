package jp.easylogin.android.sdk.api;

public interface ApiCallback<R> {

    void onResponse(ApiResponse<R> response);
}
