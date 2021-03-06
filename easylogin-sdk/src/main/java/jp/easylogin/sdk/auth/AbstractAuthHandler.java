package jp.easylogin.sdk.auth;

import jp.easylogin.sdk.EasyLogin;
import jp.easylogin.sdk.EasyLoginException;

public abstract class AbstractAuthHandler implements ProviderAuthHandler {

    private EasyLoginImpl easyLoginAuth;
    private AuthSession authSession;

    @Override
    public void setEasyLogin(EasyLogin easyLogin) {
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

    protected EasyLoginImpl getEasyLogin() {
        return this.easyLoginAuth;
    }

    protected AuthSession getAuthSession() {
        return this.authSession;
    }
}
