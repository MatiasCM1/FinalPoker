package ar.edu.unlu.poker.vista;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.List;

import ar.edu.unlu.poker.controlador.Controlador;
import ar.edu.unlu.poker.modelo.Jugador;

public interface IVista {

	void setControlador(Controlador controlador);

	void mostrarJugadorMano(Jugador jugador);

	void mostrarCartasJugador(List<Jugador> jugadores);

	void informarJugadoresInsuficientes();

	void informarCantJugadoresExcedidos();

	void mostrarGanador(List<Jugador> ganadores);

	void iniciar();
	
	void mostrarJugadoresTurnos(Jugador jugadorTurno);
	
	void notificarFondosInsuficientes();
	
	void mostrarOpcionesApuestas(Jugador jugador);
	
	void notificarFichaRealizada();

	void rondaApuestasFinalizada();

	void notificarEnviteRealizado();

	void notificarJugadorHaPasado();

	void realizandoEnvite(String cantidad);

}