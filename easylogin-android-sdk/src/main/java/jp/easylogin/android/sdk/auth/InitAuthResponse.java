package jp.easylogin.android.sdk.auth;

public class InitAuthResponse {

    private Channel channel;
    private String state;

    public InitAuthResponse() {
    }

    public InitAuthResponse(Channel channel, String state) {
        this.channel = channel;
        this.state = state;
    }

    public Channel getChannel() {
        return channel;
    }

    public String getState() {
        return state;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public void setState(String state) {
        this.state = state;
    }
}
