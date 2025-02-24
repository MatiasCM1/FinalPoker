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

	void mostrarGanador(Jugador ganador);

	void iniciar();

	void mostrarMenuApuestas();

	void informarFondosInsuficientes();

	void informarApuestaRealizada(String nombre, int apuestaJugador);

	void informarTurnoApuestaOtroJugador(String nombreJugador);

	void notificarApuestasDesiguales();

	void notificarJugadorIgualaApuesta();

	void notificarJugadorPasaApuesta(String nombreJugador);

	void notificarEsperarJugadorIgualeApuesta();

	void notificarRondaApuestaFinalizada();

	void notificarApuestaMenorALaAnterior();

	void notificarEsperarDescartes(String nombreJugador);

	void mostrarMenuDescartes();

	void notificarErrorIntentarDescarteEnUnaCartaYaDescartada();

	void notificarCartaDescartadaConExito();

	void mostrarMenuSegundaRondaApuestas();

	void notificarGanadorUnicoEnMesa(String nombre);

	void notificarJugadorManoDebeApostar();

	void notificarApuestasDesigualesSegundaRonda();

	void notificarErrorIngreseUnEntero();

	void mostrarOpcionesMenuEmpezarOtraRonda();

	void actualizarTablaJugadores(List<Jugador> jugadores);

	void mostrarNombreDelJugadorVentana();

	void limpiarNotificaciones();

	void notificarErrorIngreseUnEnteroSegundaRonda();

	void notificarApuestaMenorALaAnteriorSegundaRonda();

	void informarFondosInsuficientesSegundaRonda();

	void jugadorPasaQuedaFuera();

	void mostrarFondosInsuficientesParaComenzar();

	void mostrarFondosInsuficientesParaComenzarPostPrimerPartido();

	void informarJugadoresInsuficientesPostPrimerPartido();

	void mostrarErrorJugadoresInsuficientes();

	void mostrarMenuPrincipal();

	void mostrarErrorSalidaJugador();

	void notificarErrorMaximaLongitudFondos();

	void mostrarCartasJugadorAntGanador(List<Jugador> rondaApuestaAux);

	void notificarPartidaComenzada();

	void pasarVistaMenu();

	void notificarErrorNombre();

}