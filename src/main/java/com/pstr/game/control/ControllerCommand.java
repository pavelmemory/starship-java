package com.pstr.game.control;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.pstr.game.object.attack.WeaponType;
import com.pstr.game.object.attack.strategy.FireStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.event.KeyEvent;
import java.util.Set;

public enum ControllerCommand {

    ATTACK(ImmutableSet.of(KeyEvent.VK_SPACE), ImmutableSet.of(' ')),

    MOVE(
        ImmutableSet.of(KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT),
        ImmutableSet.<Character>of()),

    START(Sets.newHashSet(KeyEvent.VK_ENTER), ImmutableSet.<Character>of()),

    STOP(ImmutableSet.of(KeyEvent.VK_ESCAPE), ImmutableSet.<Character>of()),

    WEAPON_CHANGED(ImmutableSet.<Integer>of(), WeaponType.charSet()),

    NONE(ImmutableSet.<Integer>of(), ImmutableSet.<Character>of()),

    ATTACK_STRATEGY_CHANGED(ImmutableSet.<Integer>of(), FireStrategy.Type.charSet());

    private static final Logger LOG = LoggerFactory.getLogger(ControllerCommand.class);
    public final Set<Integer> keyCodes;
    public final Set<Character> keyChars;

    
    private ControllerCommand(Set<Integer> keyCodes, Set<Character> keyChars) {
        this.keyCodes = keyCodes;
        this.keyChars = keyChars;
    }

    public static ControllerCommand getActionCommand(int keyCode) {
        for (ControllerCommand iCommand : values()) {
            if (iCommand.keyCodes.contains(keyCode)) return iCommand;
        }
        LOG.error("Not supported key-input command for keyCode[" + keyCode + "]");
        return null;
    }

    public static ControllerCommand getActionCommand(char keyChar) {
        for (ControllerCommand iCommand : values()) {
            if (iCommand.keyChars.contains(keyChar)) return iCommand;
        }
        LOG.error("Not supported key-input command for keyChar[" + keyChar + "]");
        return null;
    }

    public static ControllerCommand defineCommand(KeyEvent event) {
        ControllerCommand actionCommandCode = getActionCommand(event.getKeyCode());
        ControllerCommand actionCommandChar = getActionCommand(event.getKeyChar());
        return actionCommandCode != null ? actionCommandCode :
                actionCommandChar != null ? actionCommandChar : ControllerCommand.NONE;
    }
}
