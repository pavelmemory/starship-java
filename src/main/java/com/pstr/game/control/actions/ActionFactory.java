package com.pstr.game.control.actions;

import com.google.common.collect.Maps;
import com.pstr.game.control.ControllerCommand;
import com.pstr.game.control.actions.attack.AttackActionFactory;
import com.pstr.game.control.actions.move.MoveActionFactory;
import com.pstr.game.control.actions.none.NoneActionFactory;
import com.pstr.game.control.actions.start.StartActionFactory;

import java.awt.event.KeyEvent;
import java.util.Map;

public abstract class ActionFactory {

    private static Map<ControllerCommand, ActionFactory> factoryMap = Maps.newHashMap();
    static {
        factoryMap.put(ControllerCommand.START, new StartActionFactory());
        factoryMap.put(ControllerCommand.MOVE, new MoveActionFactory());
        factoryMap.put(ControllerCommand.NONE, new NoneActionFactory());
        factoryMap.put(ControllerCommand.ATTACK, new AttackActionFactory());
//        factoryMap.put(ControllerCommand.STOP, new StopActionFactory());
    }

    public abstract Action create(KeyEvent event, ActionPressEvent pressEvent);

    public static ActionFactory getFor(ControllerCommand command) {
        return factoryMap.get(command);
    }

}
