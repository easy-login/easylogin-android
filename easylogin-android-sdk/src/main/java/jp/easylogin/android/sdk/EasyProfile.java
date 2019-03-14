package jp.easylogin.android.sdk;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class EasyProfile {

    @JsonProperty("created_at")
    private Date createdAt;

    @JsonProperty("modified_at")
    private Date modifiedAt;

    @JsonProperty("last_authorized_at")
    private Date lastAuthorizedAt;

    @JsonProperty("linked_at")
    private Date linkedAt;

    @JsonProperty("login_count")
    private int loginCount;

    private String provider;

    @JsonProperty("scope_id")
    private String scopeId;

    @JsonProperty("social_id")
    private long socialId;

    @JsonProperty("user_id")
    private String userId;

    private boolean verified;

    private Map<String, String> attrs = new HashMap<>();

    public EasyProfile() {
    }

    public EasyProfile(Date createdAt, Date modifiedAt, Date lastAuthorizedAt, Date linkedAt,
                       int loginCount, String provider, String scopeId, long socialId,
                       String userId, boolean verified, Map<String, String> attrs) {
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.lastAuthorizedAt = lastAuthorizedAt;
        this.linkedAt = linkedAt;
        this.loginCount = loginCount;
        this.provider = provider;
        this.scopeId = scopeId;
        this.socialId = socialId;
        this.userId = userId;
        this.verified = verified;
        this.attrs = attrs;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public Date getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Date modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public String getScopeId() {
        return scopeId;
    }

    public void setScopeId(String scopeId) {
        this.scopeId = scopeId;
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

    public String getProvider() {
        return provider;
    }

    public long getSocialId() {
        return socialId;
    }

    public String getUserId() {
        return userId;
    }

    @JsonProperty("attrs")
    public Map<String, String> getProfileAttributes() {
        return attrs;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setLastAuthorizedAt(Date lastAuthorizedAt) {
        this.lastAuthorizedAt = lastAuthorizedAt;
    }

    public void setLinkedAt(Date linkedAt) {
        this.linkedAt = linkedAt;
    }

    public void setLoginCount(int loginCount) {
        this.loginCount = loginCount;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public void setSocialId(long socialId) {
        this.socialId = socialId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setProfileAttributes(Map<String, String> attrs) {
        this.attrs = attrs;
    }
}
