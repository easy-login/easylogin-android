package jp.easylogin.android.sdk.auth;

import android.util.Base64;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class CodeVerifier {

    private final String code;
    private final String hashed;

    public CodeVerifier() {
        this.code = generateSecureCode();
        this.hashed = sha256ToString(code);
    }

    public String getRaw() {
        return this.code;
    }

    public String getHashed() {
        return this.hashed;
    }

    private static String generateSecureCode() {
        SecureRandom sr = new SecureRandom();
        byte[] b = new byte[64];
        sr.nextBytes(b);
        return Base64.encodeToString(b, Base64.NO_PADDING | Base64.URL_SAFE);
    }

    private static String sha256ToString(String code) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(
                    code.getBytes(Charset.defaultCharset()));
            return Base64.encodeToString(encodedhash, Base64.URL_SAFE);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
