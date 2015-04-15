package com.pstr.game;

import com.pstr.game.object.Visible2DObjectModel;
import org.junit.Assert;
import org.junit.Test;

import java.awt.Point;

public class TestVisibleObject extends Assert {

    @Test(expected = IllegalArgumentException.class)
    public void visibleObjectMustHaveImage() {
        Visible2DObjectModel.create(null, new Point(12, 23), 10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void visibleObjectMustHaveImage2() {
        Visible2DObjectModel.create("test_fail", new Point(12, 23), 0);
    }

    @Test
    public void visibleObjectSuccessfullyCreated() {
        Visible2DObjectModel visibleObject = Visible2DObjectModel.create("images/ships.png", new Point(12, 23), -100);
        assertNotNull(visibleObject.scope());
    }
}
