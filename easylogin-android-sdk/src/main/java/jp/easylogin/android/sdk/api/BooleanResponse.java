package jp.easylogin.android.sdk.api;

public class BooleanResponse {

    private final boolean success;

    public BooleanResponse(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }
}
