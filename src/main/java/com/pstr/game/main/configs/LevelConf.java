package com.pstr.game.main.configs;

import com.google.common.base.MoreObjects;

import java.util.Iterator;
import java.util.List;

public class LevelConf implements Iterable<LevelFrame> {

    private List<LevelFrame> frames;

    public void setFrames(List<LevelFrame> frames) {
        this.frames = frames;
    }

    public List<LevelFrame> getFrames() {
        return frames;
    }

    @Override
    public Iterator<LevelFrame> iterator() {
        return frames.iterator();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("frames", frames).toString();
    }
}
