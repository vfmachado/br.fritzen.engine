package br.fritzen.engine.gameobject;

import br.fritzen.engine.renderer.shader.Shader;

public abstract class GameObject {

	public abstract void updateUniforms(Shader shader);
	
	
	public abstract void draw();
	
}
