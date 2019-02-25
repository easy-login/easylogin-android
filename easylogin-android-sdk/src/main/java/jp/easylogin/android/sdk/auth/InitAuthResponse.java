package jp.easylogin.android.sdk.auth;

import android.support.annotation.NonNull;

public class InitAuthResponse {

    @NonNull
    private final Channel channel;
    @NonNull
    private final String state;

    public InitAuthResponse(@NonNull Channel channel, @NonNull String state) {
        this.channel = channel;
        this.state = state;
    }

    @NonNull
    public Channel getChannel() {
        return channel;
    }

    @NonNull
    public String getState() {
        return state;
    }
}
