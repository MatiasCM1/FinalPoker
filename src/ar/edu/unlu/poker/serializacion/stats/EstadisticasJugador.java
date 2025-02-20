package ar.edu.unlu.poker.serializacion.stats;

import java.io.Serializable;

public class EstadisticasJugador implements Serializable {

	private static final long serialVersionUID = 1L;
	private String nombreJugador;
	private int cantPartidasGanadas;

	public EstadisticasJugador(String nombre) {
		this.nombreJugador = nombre;
		this.cantPartidasGanadas = 0;
	}

	public String getNombreJugador() {
		return nombreJugador;
	}

	public int getCantPartidasGanadas() {
		return cantPartidasGanadas;
	}

	public void incrementarCantidadPartidasGanadas() {
		this.cantPartidasGanadas++;
	}

}
