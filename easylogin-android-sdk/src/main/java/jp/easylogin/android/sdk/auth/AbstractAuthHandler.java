package jp.easylogin.android.sdk.auth;

import jp.easylogin.android.sdk.EasyLogin;
import jp.easylogin.android.sdk.EasyLoginException;

public abstract class AbstractAuthHandler implements ProviderAuthHandler {

    private EasyLoginImpl easyLoginAuth;
    private AuthSession authSession;

    @Override
    public void setEasyLoginAuth(EasyLogin easyLogin) {
        if (!(easyLogin instanceof EasyLoginImpl)) {
            throw new EasyLoginException("Unexpected EasyLogin," +
                    " please use the provided Factory to create the instance");
        }
        this.easyLoginAuth = (EasyLoginImpl) easyLogin;
    }

    @Override
    public void setAuthSession(AuthSession session) {
        this.authSession = session;
    }

    protected EasyLoginImpl getEasyLoginAuth() {
        return this.easyLoginAuth;
    }

    protected AuthSession getAuthSession() {
        return this.authSession;
    }
}
