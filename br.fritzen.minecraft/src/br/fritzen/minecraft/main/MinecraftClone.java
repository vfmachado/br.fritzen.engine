package br.fritzen.minecraft.main;

import br.fritzen.engine.Application;

public class MinecraftClone {

	public static void main(String[] args) {
		Application app = Application.create("Minecraft Clone", 1280, 720);
		app.addLayer(new SceneLayer("Main Scene"));
		app.run();
	}
}
