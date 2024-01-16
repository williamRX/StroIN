package com.stroin.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DesktopLauncher {
    public static void main (String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setForegroundFPS(60);
        config.setTitle("StroIN");

        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("config.properties"));
            int width = Integer.parseInt(properties.getProperty("width", "2560")); // default value is 2560
            int height = Integer.parseInt(properties.getProperty("height", "1440")); // default value is 1440
            config.setWindowedMode(width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }

        new Lwjgl3Application(new StroIN(), config);
    }
}