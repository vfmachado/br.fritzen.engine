package br.fritzen.engine.utils;

import br.fritzen.engine.renderer.TextureFormat;

public class TextureFormatTest {

	public static void main(String[] args) {
		
		for (TextureFormat tf : TextureFormat.values()) {
			System.out.println(tf.name() + " : " + tf.value());
		}
		
	}

}
