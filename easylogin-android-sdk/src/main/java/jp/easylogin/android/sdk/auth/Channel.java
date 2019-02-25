package jp.easylogin.android.sdk.auth;

import java.util.List;

public class Channel {

    private final String clientId;
    private final List<String> scopes;
    private final List<String> options;

    public Channel(String clientId, List<String> scopes, List<String> options) {
        this.clientId = clientId;
        this.scopes = scopes;
        this.options = options;
    }

    public String getClientId() {
        return clientId;
    }

    public List<String> getScopes() {
        return scopes;
    }

    public List<String> getOptions() {
        return options;
    }
}
