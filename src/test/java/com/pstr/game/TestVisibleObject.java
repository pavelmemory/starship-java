package com.pstr.game;

import com.pstr.game.object.VisibleObject;
import org.junit.Assert;
import org.junit.Test;

import java.awt.Point;

public class TestVisibleObject extends Assert {

    @Test(expected = IllegalArgumentException.class)
    public void visibleObjectMustHaveImage() {
        VisibleObject.create(null, new Point(12, 23), 10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void visibleObjectMustHaveImage2() {
        VisibleObject.create("test_fail", new Point(12, 23), 0);
    }

    @Test
    public void visibleObjectSuccessfullyCreated() {
        VisibleObject visibleObject = VisibleObject.create("images/ships.png", new Point(12, 23), -100);
        assertNotNull(visibleObject.scope());
    }
}
