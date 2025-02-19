package ar.edu.unlu.poker.modelo;

import java.io.Serializable;
import java.rmi.RemoteException;

public class Carta implements Serializable, Comparable<Carta> {

	
	private static final long serialVersionUID = 1L;
	private String valor;
	private String palo;
	private static final String[] ordenCartas = { "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "AS" };

	public Carta(String valor, String palo) {
		this.setPalo(palo);
		this.setValor(valor);
	}

	private void setPalo(String paloo) {
		this.palo = paloo;
	}

	public String getValor() throws RemoteException {
		return valor;
	}

	private void setValor(String valor) {
		this.valor = valor;
	}

	public String getPalo() throws RemoteException {
		return palo;
	}

	public String[] getOrdenCartas() throws RemoteException {
		return ordenCartas;
	}

	@Override
	public int compareTo(Carta otraCarta) {
		int indiceValorCartaActual = this.getIndiceValor(this.valor);
		int indiceValorOtraCarta = 0;
		try {
			indiceValorOtraCarta = this.getIndiceValor(otraCarta.getValor());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Integer.compare(indiceValorCartaActual, indiceValorOtraCarta);
	}

	private int getIndiceValor(String valor) {
		for (int i = 0; i < ordenCartas.length; i++) {
			if (ordenCartas[i].equals(valor)) {
				return i;
			}
		}
		throw new IllegalArgumentException("Valor no encontrado en el orden: " + valor);
	}

	@Override
	public String toString() {
		return this.valor + " de " + this.palo;
	}

}
