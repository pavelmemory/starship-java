package com.pstr.game.com.pstr.game.draw;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.pstr.game.object.GameObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;

public class Drawer extends JPanel {
    private static Logger LOG = LoggerFactory.getLogger(Drawer.class);
    private List<GameObject> objects = Lists.newLinkedList();

    public Drawer(JFrame frame) {
        frame.add(this);
    }

    @Override
    public void paint(Graphics g) {
        g.clearRect(getX(), getY(), getWidth(), getHeight());
        super.paint(g);
        for (GameObject object : objects) {
            object.draw((Graphics2D) g);
        }
    }

    public void setObjects(List<GameObject> objects) {
        Preconditions.checkNotNull(objects);
        this.objects = objects;
    }

    public void action(DrawerCommand drawerCommand) {
        Preconditions.checkNotNull(drawerCommand, "Unknown command for Drawer object");
//        LOG.info("New action: " + drawerCommand);
        drawerCommand.action(this);
    }
}
