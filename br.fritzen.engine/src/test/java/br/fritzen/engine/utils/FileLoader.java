package br.fritzen.engine.utils;

import java.io.IOException;

public class FileLoader {

	public static void main(String[] args) {
		
		try {
			
			System.out.println(EngineFiles.loadTextFile("shaders/simple/vertex.shader")); //small file
						
			long time = System.currentTimeMillis();
						
			EngineFiles.loadTextFile("models/obj/bunny.obj"); //7k lines
			
			EngineFiles.loadTextFile("models/obj/dragon.obj"); //34k lines
			
			EngineFiles.loadTextFile("models/obj/tyra.obj"); //400k lines
			
			System.out.println("All files loaded in " + (System.currentTimeMillis() - time) + " ms");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
