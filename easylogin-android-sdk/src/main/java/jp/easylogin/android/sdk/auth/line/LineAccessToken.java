package jp.easylogin.android.sdk.auth.line;

import jp.easylogin.android.sdk.ProviderAccessToken;

public class LineAccessToken implements ProviderAccessToken {

    private final com.linecorp.linesdk.LineAccessToken impl;

    public LineAccessToken(com.linecorp.linesdk.LineAccessToken impl) {
        this.impl = impl;
    }

    @Override
    public String getTokenString() {
        return this.impl.getTokenString();
    }

    @Override
    public long getExpiresInMillis() {
        return this.impl.getExpiresInMillis();
    }
}
