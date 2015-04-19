package com.pstr.game.main.configs;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;

public class YamlLevelConfProvider implements LevelConfProvider {

    private final File[] files;
    private LevelConf currentLevelConf;
    private int indx = 0;

    public YamlLevelConfProvider(String folderPath) {
        File file = new File("target/classes/" + folderPath);
        Preconditions.checkState(file.exists(), "Folder '%s' with levels does not exist", file.getAbsolutePath());
        Preconditions.checkState(file.isDirectory(), "You must specify folder name");
        files = file.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith("yaml");
            }
        });
        Preconditions.checkState(files != null && files.length > 0, "No files with level config was found (*.yaml config)");
    }

    @Override
    public LevelConf next() {
        try {
            return currentLevelConf = new Yaml().loadAs(new FileInputStream(files[indx]), LevelConf.class);
        } catch (FileNotFoundException e) {
            Throwables.propagate(e);
        } return null;
    }

    @Override
    public LevelConf current() {
        return currentLevelConf;
    }

}
