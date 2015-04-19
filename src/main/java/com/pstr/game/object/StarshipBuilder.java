package com.pstr.game.object;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Sets;
import com.pstr.game.main.configs.GameConf;
import com.pstr.game.main.configs.GameObjectConf;
import com.pstr.game.main.configs.ObjectConfProvider;
import com.pstr.game.object.attack.Attacker;
import com.pstr.game.object.attack.AttackerFactory;
import com.pstr.game.object.attack.WeaponType;
import com.pstr.game.object.attack.strategy.FireStrategy;

import java.awt.Point;
import java.util.Set;

public class StarshipBuilder implements Builder {

    private final ObjectConfProvider provider;
    private final GameConf gameConf;
    private Point position;
    private Set<Integer> directions = Sets.newHashSetWithExpectedSize(2);
    private Integer speed;
    private String image;
    private FireStrategy.Type fireStrategyType;
    private WeaponType weaponType;
    private GameObjectType objectConfType;
    private Integer live;

    public StarshipBuilder(ObjectConfProvider provider, GameConf gameConf) {
        this.provider = provider;
        this.gameConf = gameConf;
    }

    @Override
    public Builder position(Point point) {
        this.position = point;
        return this;
    }

    @Override
    public Builder direction(int to) {
        directions.add(to);
        return this;
    }

    @Override
    public Builder direction(Set<Integer> to) {
        directions.addAll(to);
        return this;
    }

    @Override
    public Builder speed(Integer speed) {
        this.speed = speed;
        return this;
    }

    @Override
    public Builder live(Integer points) {
        this.live = points;
        return this;
    }

    @Override
    public Builder image(String image) {
        this.image = image;
        return this;
    }

    @Override
    public StarshipBuilder fireStrategyType(FireStrategy.Type strategyType) {
        this.fireStrategyType = strategyType;
        return this;
    }

    @Override
    public StarshipBuilder weaponType(WeaponType weaponType) {
        this.weaponType = weaponType;
        return this;
    }

    @Override
    public StarshipBuilder gameObjectConfType(GameObjectType objectConfType) {
        this.objectConfType = objectConfType;
        return this;
    }

    @Override
    public Starship build() {
        GameObjectConf conf = provider.get(objectConfType);
        Visible2DObjectModel visible2DObjectModel = Visible2DObjectModel.create(
                MoreObjects.firstNonNull(image, conf.getImage()),
                position,
                MoreObjects.firstNonNull(speed, conf.getSpeed()));
        Attacker attacker = AttackerFactory.create(fireStrategyType, gameConf, weaponType);
        Starship starship = new DefaultStarship(visible2DObjectModel, MoreObjects.firstNonNull(live, conf.getLive()), attacker);
        for (int direction : directions) {
            starship.updateDirection(direction, true);
        }
        return starship;
    }
}
