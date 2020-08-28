package com.azoraqua.qorm.com.azoraqua.qorm.hasher;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class SHA256Hasher implements Hasher {
    private static final MessageDigest md;

    static {
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Algorithm not found", e);
        }
    }

    @Override
    public byte[] hash(String str) {
        final byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
        md.update(bytes);

        return bytes;
    }
}
