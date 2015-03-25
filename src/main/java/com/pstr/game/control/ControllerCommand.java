package com.pstr.game.control;

import com.google.common.collect.Sets;
import com.pstr.game.control.actions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.event.KeyEvent;
import java.util.Set;

public enum ControllerCommand implements ActionCommand {

    ATTACK(Sets.newHashSet(KeyEvent.VK_SPACE), ControllerCommand.ANY_CHAR, new AttackCommand()),

    MOVE(Sets.newHashSet(KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT), ControllerCommand.ANY_CHAR, new MoveCommand()),

    START(Sets.newHashSet(KeyEvent.VK_ENTER), ControllerCommand.ANY_CHAR, new StartGameCommand()),

    STOP(Sets.newHashSet(KeyEvent.VK_ESCAPE), ControllerCommand.ANY_CHAR, new StopGameCommand());

    private static final char ANY_CHAR = '*';
    private static final Logger LOG = LoggerFactory.getLogger(ControllerCommand.class);
    private final Set<Integer> keyCodes;
    private final char keyChar;
    private ActionCommand command;

    
    private ControllerCommand(Set<Integer> keyCodes, char keyChar, ActionCommand command) {
        this.keyCodes = keyCodes;
        this.keyChar = keyChar;
        this.command = command;
    }

    public static ControllerCommand getActionCommand(int keyCode) {
        for (ControllerCommand iCommand : values()) {
            if (iCommand.keyCodes.contains(keyCode)) return iCommand;
        }
        LOG.error("Not supported key-input command");
        return null;
    }

    @Override
    public void actionPress(Controller controller, int pushedButtons) {
        command.actionPress(controller, pushedButtons);
    }
    @Override
    public void actionReleased(Controller controller, int pushedButtons) {
        command.actionReleased(controller, pushedButtons);
    }

    @Override
    public void actionTyped(Controller controller) {
        command.actionTyped(controller);
    }
}
