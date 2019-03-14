package jp.easylogin.android.sdk.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Channel {

    @JsonProperty("client_id")
    private String clientId;
    private List<String> scopes;
    private List<String> options;

    public Channel() {
    }

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

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setScopes(List<String> scopes) {
        this.scopes = scopes;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }
}
