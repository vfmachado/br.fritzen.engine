package br.fritzen.engine.events;

interface Run {
	boolean run();
}

public class TesteMethodRef {

	public boolean runMethod() {
		if (Math.random() > 0.5)
			return true;
		
		return false;
	}
	
	
	public static void main(String[] args) {
		
		TesteMethodRef teste = new TesteMethodRef();
		
		Run run = teste::runMethod;
		
		System.out.println(run.run());
		
	}
	
}
