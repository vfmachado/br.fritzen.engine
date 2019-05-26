package br.fritzen.engine.utils;

public class Pair<T, Q> {

	private T key;
	
	private Q value;
	
	
	public Pair(T key, Q value) {
		this.key = key;
		this.value = value;
	}


	public T getKey() {
		return key;
	}


	public void setKey(T key) {
		this.key = key;
	}


	public Q getValue() {
		return value;
	}


	public void setValue(Q value) {
		this.value = value;
	}
	
	
	
}
