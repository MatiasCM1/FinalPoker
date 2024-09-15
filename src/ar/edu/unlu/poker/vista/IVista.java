package ar.edu.unlu.poker.vista;

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
	
<<<<<<< HEAD
	void solicitarApuestas(String input);
	
	void mostrarOpcionesApuestas();
=======
	void mostrarApuestas(String input);
>>>>>>> ca55cfb84d248f710db8c2c9e2aa9fd1e1b7f5d9

	void notificarFondosInsuficientes();

	void notificarApuestaInsuficiente();

	void notificarApuestaRealizada();

	void mostrarOpcionesApuestas();

}