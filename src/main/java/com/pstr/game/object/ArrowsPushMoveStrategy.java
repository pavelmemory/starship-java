package com.pstr.game.object;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public class ArrowsPushMoveStrategy implements MoveStrategy {
    private static final Logger LOG = LoggerFactory.getLogger(ArrowsPushMoveStrategy.class);
    private Set<Integer> direction;



    public void setDirection(Set<Integer> direction) {
        Preconditions.checkArgument(direction != null && direction.size() <= 2);
        this.direction = direction;
    }

    @Override
    public GameObject move(GameObject object) {

        object.center();

        return null;
    }
}
