package br.fritzen.engine.terrain;

import br.fritzen.engine.scenegraph.GameObject;
import br.fritzen.engine.scenegraph.GameObjectType;

public abstract class Terrain extends GameObject {

	@Override
	protected GameObjectType getType() {
		return GameObjectType.TERRAIN;
	}
	
}
