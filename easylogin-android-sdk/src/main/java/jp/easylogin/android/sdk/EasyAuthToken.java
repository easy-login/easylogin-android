package jp.easylogin.android.sdk;

import java.util.Date;

public class EasyAuthToken {

    private final String authToken;
    private final Date expireAt;

    public EasyAuthToken(String authToken, Date expireAt) {
        this.authToken = authToken;
        this.expireAt = expireAt;
    }

    public String getTokenString() {
        return authToken;
    }

    public Date getExpireAt() {
        return expireAt;
    }
}
