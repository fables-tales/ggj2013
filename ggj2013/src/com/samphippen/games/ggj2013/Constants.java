package com.samphippen.games.ggj2013;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;

public class Constants {

    public static HashMap<String, Double> sConstants = new HashMap<String, Double>();

    public static void setConstants() {
        File constantsFile = Gdx.files.internal("bin/data/constants.txt")
                .file();
        System.out.println(constantsFile.getAbsolutePath());
        FileInputStream fileStream;
        try {
            fileStream = new FileInputStream(constantsFile);
            ConstantsReader constantsReader = new ConstantsReader();
            constantsReader.readConstants(fileStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
