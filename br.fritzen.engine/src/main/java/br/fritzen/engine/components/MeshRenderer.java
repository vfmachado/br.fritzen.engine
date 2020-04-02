package br.fritzen.engine.components;

import java.util.List;

import br.fritzen.engine.renderer.Material;
import br.fritzen.engine.utils.Pair;

public interface MeshRenderer extends GameComponent {

	public List<Pair<Mesh, Material>> getMeshMaterial();

	@Override
	public default GameComponentType getComponentType() {
		return GameComponentType.MESH_RENDERER;
	}
	
}
