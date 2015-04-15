package com.pstr.game.utils;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import com.google.common.collect.Maps;
import com.google.common.io.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

public final class Files {

    private static Logger LOG = LoggerFactory.getLogger(Files.class);
    private static Map<String, BufferedImage> cache = Maps.newHashMap();

    public static String asString(String path) {
        Preconditions.checkState(!Strings.isNullOrEmpty(path));
        try {
            return Resources.toString(Resources.getResource(path), Charsets.UTF_8);
        } catch (IOException e) {
            LOG.error("Error on text file loading", e);
            Throwables.propagate(e);
        } return null;
    }

    public static BufferedImage asBufferedImage(String path) {
        Preconditions.checkState(!Strings.isNullOrEmpty(path));
        if (!cache.containsKey(path)) {
            try {
                URL resource = Resources.getResource(path);
                return ImageIO.read(resource);
            } catch (IOException e) {
                LOG.error("Error on image file loading: " + path, e);
                Throwables.propagate(e);
            }
        }
        return cache.get(path);
    }
}
