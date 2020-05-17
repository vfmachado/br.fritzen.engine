package br.fritzen.engine.utils;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Pair<T, Q> {

	@Getter
	@Setter
	private T key;
	
	@Getter
	@Setter
	private Q value;
	
	@Override
	public String toString() {
		return "KEY: " + key + " --> VALUE: " + value;
	}
}
