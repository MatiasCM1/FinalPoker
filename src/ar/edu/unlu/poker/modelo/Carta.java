package ar.edu.unlu.poker.modelo;

public class Carta {

	private String valor;
	private String palo;
	private String [] ordenCartas = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "AS"};
	private String[] palosValidos = {"PICA", "CORAZON", "TREBOL", "DIAMANTE"};
	
	public Carta(String valor, String palo) {
		this.setPalo(palo);
		this.setValor(valor); 
	}

	public String getValor() {
		return valor;
	}
	

	private void setValor(String valor) {
		this.valor = valor;
	}

	public String getPalo() {
		return palo;
	}

	private void setPalo(String palo) {
		if (!verificarPaloValido(palo)) {
			throw new IllegalArgumentException("Palo inválido:" + palo);
		} else {
			this.palo = palo;
		}
	}

	public String [] getOrdenCartas() {
		return ordenCartas;
	}
	
	private boolean verificarPaloValido(String palo) {
		for (String p : palosValidos) {
			if (p.equals(palo)) {
				return true;
			}
		}
		return false;
	}

}
