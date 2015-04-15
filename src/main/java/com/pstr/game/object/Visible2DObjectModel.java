package com.pstr.game.object;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.pstr.game.utils.Files;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

public class Visible2DObjectModel extends ObjectModel {
    private static final Logger LOG = LoggerFactory.getLogger(Visible2DObjectModel.class);

    protected BufferedImage image;

    public static Visible2DObjectModel create(String picturePath, Point center, int speed) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(picturePath), "Path to image not specified");
        return new Visible2DObjectModel(Files.asBufferedImage(picturePath), center, speed);
    }

    protected Visible2DObjectModel(BufferedImage image, Point center, int speed) {
        super(center, image.getWidth(), image.getHeight(), speed);
        this.image = image;
    }

    public void draw(Graphics2D g2d) {
        g2d.drawImage(image, scope().x, scope().y, null);
    }

    public String toString() {
        return super.toString();
    }
}
