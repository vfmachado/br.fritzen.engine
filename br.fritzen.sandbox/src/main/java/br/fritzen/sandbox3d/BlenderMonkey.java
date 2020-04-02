package br.fritzen.sandbox3d;

import java.util.ArrayList;
import java.util.List;

import org.joml.Matrix4f;

import br.fritzen.engine.components.Mesh;
import br.fritzen.engine.components.MeshRenderer;
import br.fritzen.engine.components.Model;
import br.fritzen.engine.renderer.Material;
import br.fritzen.engine.scenegraph.GameObject;
import br.fritzen.engine.scenegraph.GameObjectType;
import br.fritzen.engine.utils.Pair;
import lombok.Getter;

public class BlenderMonkey extends GameObject {

	private Model model;

	@Getter
	private Matrix4f transform;


	List<Pair<Mesh, Material>> meshToRender;
	
	
	public BlenderMonkey() {
		
		setName("Gold Blender Monkey");
		
		this.model = new Model("models/monkey.obj");
		this.transform = new Matrix4f();
		
		meshToRender= new ArrayList<Pair<Mesh, Material>>();
		for (Pair<Mesh, Integer> m : model.getMeshes()) {
			meshToRender.add(new Pair<Mesh, Material>(m.getKey(), getMaterial()));
		}
		
		this.addComponent(new MeshRenderer() {
			
			@Override
			public List<Pair<Mesh, Material>> getMeshMaterial() {
				return meshToRender;		
			}
			
		});
	}

	public Mesh getMesh() {
		return this.model.getMeshes().get(0).getKey();
	}

	
	public Material getMaterial() {
		return Material.BRASS;
	}


	@Override
	protected GameObjectType getType() {
		return GameObjectType.SIMPLE;
	}

	
	
}
