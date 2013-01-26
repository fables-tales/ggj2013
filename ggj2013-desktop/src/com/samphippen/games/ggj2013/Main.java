package com.samphippen.games.ggj2013;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "ggj2013";
		cfg.useGL20 = true;
		cfg.width = 800;
		cfg.height = 600;
		
		new LwjglApplication(new GameHolder(), cfg);
		
		setConstants();
	}
	
	private static void setConstants(){
		File constantsFile = Gdx.files.internal("bin/data/constants.txt").file();
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
