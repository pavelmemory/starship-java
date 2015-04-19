package com.pstr.game.object;

import com.pstr.game.object.attack.Attacker;
import com.pstr.game.object.attack.WeaponType;
import com.pstr.game.object.attack.damage.Ammo;
import com.pstr.game.object.attack.strategy.FireStrategy;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Set;

public class DefaultStarship implements Starship {

    private Visible2DObjectModel visible2DObjectModel;
    private Attacker attacker;
    private int live;

    public DefaultStarship(Visible2DObjectModel visible2DObjectModel, int live, Attacker attacker) {
        this.visible2DObjectModel = visible2DObjectModel;
        this.live = live;
        this.attacker = attacker;
    }

    @Override
    public Set<Ammo> attack(int direction) {
        return attacker.attack(scope(), direction);
    }

    @Override
    public Set<Ammo> attack(Rectangle scope, int direction) {
        throw new RuntimeException("DO NOT USE!");
    }

    @Override
    public boolean isInAttackState() {
        return attacker.isInAttackState();
    }

    @Override
    public void updateAttackState(boolean isAttackPerformed) {
        attacker.updateAttackState(isAttackPerformed);
    }

    @Override
    public FireStrategy getStrategy() {
        return attacker.getStrategy();
    }

    @Override
    public void setStrategy(FireStrategy weapon) {
        WeaponType weaponType = attacker.getWeaponType();
        attacker.setStrategy(weapon);
        attacker.setWeaponType(weaponType);
    }

    @Override
    public WeaponType getWeaponType() {
        return attacker.getWeaponType();
    }

    @Override
    public void setWeaponType(WeaponType weaponType) {
        attacker.setWeaponType(weaponType);
    }

    @Override
    public void setAttacker(Attacker attacker) {
        this.attacker = attacker;
    }

    @Override
    public void damageOn(int damageAmount) {
        this.live -= damageAmount;
    }

    @Override
    public void draw(Graphics2D g2d) {
        visible2DObjectModel.draw(g2d);
    }

    @Override
    public void updateDirection(int direction, boolean pressed) {
        visible2DObjectModel.setDirection(direction, pressed);
    }

    @Override
    public void move() {
        visible2DObjectModel.move();
    }

    @Override
    public boolean isAlive() {
        return live > 0;
    }

    @Override
    public void destroy() {
        live = Integer.MIN_VALUE;
    }

    @Override
    public Point center() {
        return visible2DObjectModel.center();
    }

    @Override
    public void center(Point center) {
        visible2DObjectModel.center(center);
    }

    @Override
    public Rectangle scope() {
        return visible2DObjectModel.scope();
    }

    @Override
    public boolean intersect(GameObject object) {
        return visible2DObjectModel.intersect(object.scope());
    }
}
