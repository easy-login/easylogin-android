package jp.easylogin.sdk.api;

public interface ApiCallback<R> {

    void onResponse(ApiResponse<R> response);
}
