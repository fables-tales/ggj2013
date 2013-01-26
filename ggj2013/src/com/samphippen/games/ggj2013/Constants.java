package com.samphippen.games.ggj2013;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;

public class Constants {
    public static int QUITE_A_LOT = 400000000;

    public static Map<String, Double> sConstants = new HashMap<String, Double>();

    public static void setConstants() {
        File constantsFile = Gdx.files.internal("bin/data/constants.txt")
                .file();
        System.out.println(constantsFile.getAbsolutePath());
        try {
            ConstantsReader.readConstants(new FileInputStream(constantsFile));
        } catch (FileNotFoundException missingFileException) {
            missingFileException.printStackTrace();
        }
    }

    public static double getDouble(String key) {
        return sConstants.get(key);
    }

    public static float getFloat(String key) {
        return (float) getDouble(key);
    }
}
