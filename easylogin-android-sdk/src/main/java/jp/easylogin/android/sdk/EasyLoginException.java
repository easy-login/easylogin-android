package jp.easylogin.android.sdk;

public class EasyLoginException extends RuntimeException {

    public EasyLoginException() {
    }

    public EasyLoginException(String message) {
        super(message);
    }

    public EasyLoginException(String message, Throwable cause) {
        super(message, cause);
    }

    public EasyLoginException(Throwable cause) {
        super(cause);
    }
}
