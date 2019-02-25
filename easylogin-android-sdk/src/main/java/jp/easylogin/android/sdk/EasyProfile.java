package jp.easylogin.android.sdk;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class EasyProfile {

    private final Date createdAt;
    private final Date lastAuthorizedAt;
    private final Date linkedAt;
    private final int loginCount;
    private final String provider;
    private final long socialId;
    private final String userId;
    private final Map<String, String> attrs = new HashMap<>();

    public EasyProfile(Date createdAt, Date lastAuthorizedAt, Date linkedAt,
                       int loginCount, String provider, long socialId, String userId) {
        this.createdAt = createdAt;
        this.lastAuthorizedAt = lastAuthorizedAt;
        this.linkedAt = linkedAt;
        this.loginCount = loginCount;
        this.provider = provider;
        this.socialId = socialId;
        this.userId = userId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getLastAuthorizedAt() {
        return lastAuthorizedAt;
    }

    public Date getLinkedAt() {
        return linkedAt;
    }

    public int getLoginCount() {
        return loginCount;
    }

    public String getProviderName() {
        return null;
    }

    public String getProvider() {
        return provider;
    }

    public long getSocialId() {
        return socialId;
    }

    public String getUserId() {
        return userId;
    }

    public Map<String, String> getProfileAttributes() {
        return attrs;
    }
}
