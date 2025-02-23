package ar.edu.unlu.poker.modelo;

public class IDGenerator {
	
	private static IDGenerator instancia;
	private int contadorID = 1;
	
	private IDGenerator() {
		
	}
	
	public static IDGenerator getInstance() {
		if (instancia == null) {
			instancia = new IDGenerator();
		}
		return instancia;
	}
	
	public int getSiguienteID() {
		return contadorID++;
	}

}
