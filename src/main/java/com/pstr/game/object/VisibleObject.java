package com.pstr.game.object;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.pstr.game.utils.Files;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

public class VisibleObject extends GObject {
    private static final Logger LOG = LoggerFactory.getLogger(VisibleObject.class);

    private BufferedImage image;

    public static VisibleObject create(String picturePath, Point center, int speed) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(picturePath), "Path to image not specified");
        Preconditions.checkNotNull(center, "Initial position not specified");
        return new VisibleObject(Files.asBufferedImage(picturePath), center, speed);
    }

    private VisibleObject(BufferedImage image, Point center, int speed) {
        super(center, image.getWidth(), image.getHeight(), speed);
        this.image = image;
    }

    public void draw(Graphics2D g2d) {
        g2d.drawImage(image, scope().x, scope().y, null);
    }
}
