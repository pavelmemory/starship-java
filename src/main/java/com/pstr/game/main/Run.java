package com.pstr.game.main;

import com.pstr.game.com.pstr.game.draw.Drawer;
import com.pstr.game.control.Controller;
import com.pstr.game.control.SimpleController;
import com.pstr.game.main.configs.GameConf;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.io.IOException;

public class Run extends JFrame {

    public Run(GameConf gameConf) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(gameConf.getWindow().width, gameConf.getWindow().height);
        setResizable(gameConf.getWindow().resizable);
        Drawer drawer = new Drawer(this);
        Controller controller = new SimpleController(this, gameConf, drawer);
        controller.start();
    }

    public static void main(String[] args) throws IOException {
        final GameConf gameConf = GameConf.load("game.yaml");
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Run(gameConf);
            }
        });

    }
}
