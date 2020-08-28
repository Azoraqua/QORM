package com.azoraqua.qorm.com.azoraqua.qorm.hasher;

import java.nio.charset.StandardCharsets;

@Deprecated
public final class DefaultHasher implements Hasher {

    @Override
    public byte[] hash(String str) {
        return str.getBytes(StandardCharsets.UTF_8);
    }
}
