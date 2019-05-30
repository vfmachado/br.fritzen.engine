package br.fritzen.engine.utils;

import java.io.IOException;

public class FileLoader {

	public static void main(String[] args) {
		
		try {
			System.out.println(EngineFiles.loadTextFile("shaders/simple/vertex.shader"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
