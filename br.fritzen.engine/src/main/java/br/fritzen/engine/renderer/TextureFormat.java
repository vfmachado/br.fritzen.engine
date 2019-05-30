package br.fritzen.engine.renderer;

public enum TextureFormat {

	None(0),
	RGB,
	RGBA,
	F16,
	F32,
	Depth;
	
	
	private int value;
	
	
	private static class Counter {
		private static int nextValue = 0;
	};
	
	
	private TextureFormat() {
	
		this.value = Counter.nextValue;
		Counter.nextValue++;
	
	}
	
	
	private TextureFormat(int value) {
	
		this.value = value;
		Counter.nextValue = value + 1;
	
	}
	
	
	public int value() {
		return this.value;
	}
	
}
