package org.patsimas.chat.enums;

import lombok.Getter;

@Getter
public enum ActiveStatus {

    ACTIVE(((short) 1)),
    INACTIVE(((short) 0));

    private final short code;
    
    ActiveStatus(short v) {
        code = v;
    }

    public short code() {
        return code;
    }

    public static ActiveStatus fromValue(short v) {
        for (ActiveStatus c: ActiveStatus.values()) {
            if (c.code == v) {
                return c;
            }
        }
        throw new IllegalArgumentException(String.valueOf(v));
    }
}
