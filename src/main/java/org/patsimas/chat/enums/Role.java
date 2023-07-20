package org.patsimas.chat.enums;

public enum Role {

    ROLE_USER(((short) 1)),
    ROLE_PROFESSIONAL(((short) 2)),
    ROLE_ADMIN(((short) 3)),

    ROLE_PREMIUM_PROFESSIONAL(((short) 4));

    private final short code;

    Role(short v) {
        code = v;
    }

    public short code() {
        return code;
    }

    public static Role fromValue(short v) {
        for (Role c: Role.values()) {
            if (c.code == v) {
                return c;
            }
        }
        throw new IllegalArgumentException(String.valueOf(v));
    }
}
