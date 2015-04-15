package com.pstr.game.object.attack;

import com.google.common.collect.ImmutableSet;

import java.util.Set;

public enum WeaponType {
    WHITE_BULLET('s'),
    YELLOW_BULLET('~');
//    ROCKET('r', RocketBuilder.class);

    private final char CHAR_CODE;

    public char getCharCode() {
        return CHAR_CODE;
    }

    WeaponType(char weaponCharCode) {
        this.CHAR_CODE = weaponCharCode;
    }

    public static WeaponType byCharCode(char keyChar) {
        for (WeaponType wt : values()) {
            if (wt.getCharCode() == keyChar) return wt;
        }
        throw new IllegalArgumentException();
    }

    public static Set<Character> charSet() {
        ImmutableSet.Builder<Character> builder = ImmutableSet.builder();
        for (WeaponType weaponType : values()) {
            builder.add(weaponType.CHAR_CODE);
        }
        return builder.build();
    }
}
