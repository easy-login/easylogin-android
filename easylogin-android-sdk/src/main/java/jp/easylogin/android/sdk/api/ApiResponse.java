package jp.easylogin.android.sdk.api;

public class ApiResponse<R> {

    private final R response;
    private final int code;
    private final Throwable throwable;

    public static <T> ApiResponse<T> buildAsSuccess(int code, T response) {
        return new ApiResponse<>(code, response, null);
    }

    public static <T> ApiResponse<T> buildAsError(int code, Throwable t) {
        return new ApiResponse<>(code, null, t);
    }

    private ApiResponse(int code, R response, Throwable throwable) {
        this.response = response;
        this.code = code;
        this.throwable = throwable;
    }

    public boolean isSuccess() {
        return this.code == ApiResponseCode.SUCCESS || this.code == ApiResponseCode.CREATED;
    }

    public boolean isServerError() {
        return this.code == ApiResponseCode.SERVER_ERROR;
    }

    public R getResponseData() {
        return response;
    }

    public int getCode() {
        return code;
    }

    public Throwable getError() {
        return throwable;
    }
}
