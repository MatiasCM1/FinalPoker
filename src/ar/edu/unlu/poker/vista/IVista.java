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
	
	void mostrarMenuApuestas();
	
	void informarFondosInsuficientes();

	void notificarEnviteRealizado();

	void informarApuestaRealizada(String nombre, int apuestaJugador);

	void informarNoTurno();

	void informarTurnoApuestaOtroJugador();

	void notificarApuestasDesiguales();

	void notificarJugadorIgualaApuesta();

	void notificarJugadorPasaApuesta();

	void notificarEsperarJugadorIgualeApuesta();

	void notificarRondaApuestaFinalizada();

	void notificarApuestaMenorALaAnterior();

}