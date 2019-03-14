package jp.easylogin.sdk.auth;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import jp.easylogin.sdk.common.Utils;

class CodeVerifier {

    private final String code;
    private final String hashed;

    CodeVerifier() {
        this.code = generateSecureCode();
        this.hashed = sha256ToString(code);
    }

    String getRaw() {
        return this.code;
    }

    String getHashed() {
        return this.hashed;
    }

    private static String generateSecureCode() {
        SecureRandom sr = new SecureRandom();
        byte[] b = new byte[64];
        sr.nextBytes(b);
        return Utils.byteArrayToHex(b);
    }

    private static String sha256ToString(String code) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(code.getBytes(Charset.defaultCharset()));
            return Utils.byteArrayToHex(encodedhash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private static String byteArrayToHex(byte[] a) {
        StringBuilder sb = new StringBuilder(a.length * 2);
        for(byte b: a)
            sb.append(String.format("%02x", b));
        return sb.toString();
    }
}
