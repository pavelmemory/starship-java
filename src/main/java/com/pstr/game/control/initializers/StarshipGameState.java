package com.pstr.game.control.initializers;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import com.pstr.game.control.ControllerCommand;
import com.pstr.game.control.actions.Action;
import com.pstr.game.main.configs.GameConf;
import com.pstr.game.main.configs.YamlObjectConfProvider;
import com.pstr.game.object.GameObject;
import com.pstr.game.object.GameObjectType;
import com.pstr.game.object.Starship;
import com.pstr.game.object.StarshipBuilder;
import com.pstr.game.object.attack.WeaponType;
import com.pstr.game.object.attack.damage.Ammo;
import com.pstr.game.object.attack.strategy.FireStrategy;
import com.pstr.game.object.attack.strategy.FireStrategyFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.OperationNotSupportedException;
import java.awt.Point;
import java.util.Map;
import java.util.Set;


public class StarshipGameState implements GameState {
    private static final Logger LOG = LoggerFactory.getLogger(StarshipGameState.class);
    private boolean initialized = false;
    private final GameConf gameConf;

    private Starship player = null;
    private Set<Starship> enemies = Sets.newHashSet();
    private Set<Ammo> ammos = Sets.newHashSet();
    private Set<GameObject> objects = Sets.newHashSet();

    public StarshipGameState(GameConf gameConf) {
        this.gameConf = gameConf;
    }

    @Override
    public void init() {
        LOG.info("Initialization start");
        if (!initialized) {

            player = new StarshipBuilder(new YamlObjectConfProvider(gameConf), gameConf)
                    .fireStrategyType(FireStrategy.Type.SINGLE)
                    .weaponType(WeaponType.WHITE_BULLET)
                    .position(new Point(gameConf.getGameInitialConf().getStartX(), gameConf.getGameInitialConf().getStartY()))
                    .gameObjectConfType(GameObjectType.STARSHIP)
                    .build();
        }
        initialized = true;
        LOG.info("Initialization successfully completed");
    }

    @Override
    public Set<GameObject> getAllGameObjects() {
        if (!initialized) return ImmutableSet.of();
        return ImmutableSet.<GameObject>builder().addAll(enemies).addAll(ammos).addAll(objects).add(player).build();
    }

    @Override
    public Set<Starship> getEnemies() {
        return enemies;
    }

    @Override
    public Set<Ammo> getAmmos() {
        return ammos;
    }

    @Override
    public Set<GameObject> getObjects() {
        return objects;
    }

    @Override
    public void addEnemy(Starship enemy) {
        enemies.add(enemy);
    }

    @Override
    public void addAllEnemy(Iterable<Starship> enemies) {
        Iterables.addAll(this.enemies, enemies);
    }

    @Override
    public void addAmmo(Ammo ammo) {
        ammos.add(ammo);
    }

    @Override
    public void addAllAmmo(Iterable<Ammo> ammos) {
        Iterables.addAll(this.ammos, ammos);
    }

    @Override
    public void addObject(GameObject object) {
        objects.add(object);
    }

    @Override
    public void addAllObject(Iterable<GameObject> objects) {
        Iterables.addAll(this.objects, objects);
    }

    @Override
    public Starship getPlayer() {
        return player;
    }

    @Override
    public void changeBy(Action action) {
        if (!initialized && !action.getControllerCommand().equals(ControllerCommand.START)) return;
        actionCases.get(action.getControllerCommand()).action(action);
    }

    @Override
    public boolean isOnAir() {
        return initialized;
    }

    private interface ActionCase {

        void action(Action action);

    }

    private Map<ControllerCommand, ActionCase> actionCases = ImmutableMap.<ControllerCommand, ActionCase>builder()
            .put(ControllerCommand.ATTACK, new AttackCase())
            .put(ControllerCommand.MOVE, new MoveCase())
            .put(ControllerCommand.START, new StartCase())
            .put(ControllerCommand.STOP, new StopCase())
            .put(ControllerCommand.NONE, new NoneCase())
            .put(ControllerCommand.WEAPON_CHANGED, new WeaponChangedCase())
            .put(ControllerCommand.ATTACK_STRATEGY_CHANGED, new AttackStrategyChangedCase())
            .build();

    private class AttackCase implements ActionCase {
        @Override
        public void action(Action action) {
            switch (action.getPressEvent()) {
                case PRESSED:
                    getPlayer().updateAttackState(true);
                    break;
                case RELEASED:
                    getPlayer().updateAttackState(false);
                    break;
                case TYPED: break;
                default:
                    throw new RuntimeException(new OperationNotSupportedException());
            }
        }
    }

    private class MoveCase implements ActionCase {
        @Override
        public void action(Action action) {
            switch (action.getPressEvent()) {
                case PRESSED:
                    getPlayer().updateDirection(action.event().getKeyCode(), true);
                    break;
                case RELEASED:
                    getPlayer().updateDirection(action.event().getKeyCode(), false);
                    break;
                case TYPED: break;
                default:
                    throw new RuntimeException(new OperationNotSupportedException());
            }
        }
    }

    private class StartCase implements ActionCase {
        @Override
        public void action(Action action) {
            StarshipGameState.this.init();
        }
    }

    private class NoneCase implements ActionCase {
        @Override
        public void action(Action action) {
            LOG.warn("State not changed because of undefined action type: " + action);
        }
    }

    private class StopCase implements ActionCase {
        @Override
        public void action(Action action) {
            initialized = false;
        }
    }

    private class WeaponChangedCase implements ActionCase {
        @Override
        public void action(Action action) {
            WeaponType weaponType = WeaponType.byCharCode(action.event().getKeyChar());
            getPlayer().getStrategy().setWeaponType(weaponType);
            LOG.info("Weapon changed to " + weaponType);
        }
    }

    private class AttackStrategyChangedCase implements ActionCase {
        @Override
        public void action(Action action) {
            FireStrategy fireStrategy = FireStrategyFactory.create(
                    FireStrategy.Type.byCharCode(action.event().getKeyChar()), gameConf, getPlayer().getStrategy().getWeaponType());
            getPlayer().setStrategy(fireStrategy);
            LOG.info("Attack strategy changed to: " + getPlayer().getStrategy());
        }
    }

}
