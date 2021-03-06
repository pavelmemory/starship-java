package com.pstr.game.com.pstr.game.draw;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import com.pstr.game.object.GameObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Set;

public class Drawer extends JPanel {
    private static Logger LOG = LoggerFactory.getLogger(Drawer.class);
    private Set<GameObject> objects = Sets.newHashSet();

    public Drawer(JFrame frame) {
        frame.add(this);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.BLACK);
        g.fillRect(getX(), getY(), getWidth(), getHeight());
        g.setColor(Color.WHITE);
        for (GameObject object : objects) {
            object.draw((Graphics2D) g);
        }
    }

    public void setObjects(Set<GameObject> objects) {
        Preconditions.checkNotNull(objects);
        this.objects = objects;
    }

    public void action(DrawerCommand drawerCommand) {
        Preconditions.checkNotNull(drawerCommand, "Unknown command for Drawer object");
        drawerCommand.action(this);
    }
}
