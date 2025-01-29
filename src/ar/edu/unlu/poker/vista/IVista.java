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

	void mostrarGanador(Jugador ganador);

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

	void notificarEsperarDescartes();

	void mostrarMenuDescartes();

	void notificarErrorIntentarDescarteEnUnaCartaYaDescartada();

	void notificarCartaDescartadaConExito();

	void mostrarMenuSegundaRondaApuestas();

	void notificarGanador(String nombre);

	void notificarJugadorManoDebeApostar();

	void notificarApuestasDesigualesSegundaRonda();
	
	void notificarErrorIngreseUnEntero();
	
	void mostrarOpcionesMenuEmpezarOtraRonda() ;
	
	void setEnableCampoEntrada(boolean h);
	
	void mostrarOpcionesMenu();

	void actualizarTablaJugadores(List<Jugador> jugadores);

	void mostrarNombreDelJugadorVentana();

}