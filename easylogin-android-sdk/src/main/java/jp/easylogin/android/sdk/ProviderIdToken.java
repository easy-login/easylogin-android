package jp.easylogin.android.sdk;

import java.util.Date;
import java.util.Map;

public interface ProviderIdToken {

    String getIssuer();

    String getSubject();

    String getAudience();

    Date getExpiresAt();

    Date getIssuedAt();

    Map<String, Object> getProfileAttributes();

    String getTokenString();
}
