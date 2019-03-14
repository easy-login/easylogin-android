package jp.easylogin.sdk.auth.line;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import jp.easylogin.sdk.ProviderIdToken;


public class LineIdToken implements ProviderIdToken {

    private final com.linecorp.linesdk.LineIdToken impl;

    public LineIdToken(com.linecorp.linesdk.LineIdToken impl) {
        this.impl = impl;
    }

    @Override
    public String getIssuer() {
        return this.impl.getIssuer();
    }

    @Override
    public String getSubject() {
        return this.impl.getSubject();
    }

    @Override
    public String getAudience() {
        return this.impl.getAudience();
    }

    @Override
    public Date getExpiresAt() {
        return this.impl.getExpiresAt();
    }

    @Override
    public Date getIssuedAt() {
        return this.impl.getIssuedAt();
    }

    @Override
    public Map<String, Object> getProfileAttributes() {
        Map<String, Object> map = new TreeMap<>();
        map.put("name", impl.getName());
        map.put("picture", impl.getPicture());
        map.put("email", impl.getEmail());
        return map;
    }

    @Override
    public String getTokenString() {
        return "";
    }
}
