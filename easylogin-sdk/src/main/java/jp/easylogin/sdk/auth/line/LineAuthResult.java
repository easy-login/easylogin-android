package jp.easylogin.sdk.auth.line;

import com.linecorp.linesdk.LineCredential;
import com.linecorp.linesdk.auth.LineLoginResult;

import jp.easylogin.sdk.ProviderAccessToken;
import jp.easylogin.sdk.ProviderIdToken;
import jp.easylogin.sdk.auth.AbstractAuthResult;
import jp.easylogin.sdk.auth.AuthSession;

public class LineAuthResult extends AbstractAuthResult {

    private final LineLoginResult impl;

    public LineAuthResult(LineLoginResult impl, AuthSession session) {
        super(session);
        this.impl = impl;
    }

    @Override
    public ProviderAccessToken getProviderAccessToken() {
        LineCredential credential = impl.getLineCredential();
        return credential != null ? new LineAccessToken(credential.getAccessToken()) : null;
    }

    @Override
    public ProviderIdToken getProviderIdToken() {
        return new LineIdToken(impl.getLineIdToken());
    }

    @Override
    public boolean isOpenIdSupport() {
        return true;
    }
}
