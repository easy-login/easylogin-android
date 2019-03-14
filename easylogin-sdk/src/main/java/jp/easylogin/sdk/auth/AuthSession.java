package jp.easylogin.sdk.auth;

import jp.easylogin.sdk.EasyAuthToken;

public class AuthSession {

    private final String provider;
    private Channel channel;
    private CodeVerifier codeVerifier;
    private String state;
    private String authStatus;
    private EasyAuthToken authToken;

    public String getProvider() {
        return provider;
    }

    public EasyAuthToken getAuthToken() {
        return authToken;
    }

    public void setAuthToken(EasyAuthToken authToken) {
        this.authToken = authToken;
    }

    public AuthSession(String provider) {
        this.codeVerifier = new CodeVerifier();
        this.authStatus = AuthStatus.UNKNOWN;
        this.provider = provider;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public CodeVerifier getCodeVerifier() {
        return codeVerifier;
    }

    public void setCodeVerifier(CodeVerifier codeVerifier) {
        this.codeVerifier = codeVerifier;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(String authStatus) {
        this.authStatus = authStatus;
    }
}
