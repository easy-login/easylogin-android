package jp.easylogin.android.sdk;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EasyAuthToken {

    @JsonProperty("auth_token")
    private String authToken;

    @JsonProperty("expires_in")
    private long expiresIn;

    public EasyAuthToken() {
    }

    public EasyAuthToken(String authToken, long expiresIn) {
        this.authToken = authToken;
        this.expiresIn = expiresIn;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getTokenString() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
