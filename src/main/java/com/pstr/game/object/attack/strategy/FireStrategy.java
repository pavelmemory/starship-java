package com.pstr.game.object.attack.strategy;

import com.google.common.collect.ImmutableSet;
import com.pstr.game.object.attack.WeaponType;
import com.pstr.game.object.attack.damage.Ammo;

import java.awt.Rectangle;
import java.util.Set;

public interface FireStrategy {

    public Set<Ammo> fire(Rectangle scope, int direction);

    int getAttackSpeed();

    void setWeaponType(WeaponType weaponType);

    WeaponType getWeaponType();

    void setDamage(int damage);


    Type getType();

    public enum Type {
        SINGLE('1'), DOUBLE('2'), TRIPLE('3');

        private Type(char charKey) {
            CHAR_KEY = charKey;
        }

        public final char CHAR_KEY;

        public static Type byCharCode(char keyChar) {
            for (Type type : values()) {
                if (type.CHAR_KEY == keyChar) return type;
            }
            throw new IllegalArgumentException();
        }

        public static Set<Character> charSet() {
            ImmutableSet.Builder<Character> builder = ImmutableSet.builder();
            for(Type type : values()) {
                builder.add(type.CHAR_KEY);
            }
            return builder.build();
        }
    }
}
