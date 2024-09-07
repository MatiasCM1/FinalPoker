package ar.edu.unlu.poker.controlador;

import java.util.LinkedList;
import java.util.List;

import ar.edu.unlu.poker.comunes.Observado;
import ar.edu.unlu.poker.comunes.Observer;
import ar.edu.unlu.poker.modelo.Informe;
import ar.edu.unlu.poker.modelo.Jugador;
import ar.edu.unlu.poker.modelo.Mesa;
import ar.edu.unlu.poker.vista.VistaConsola;
import ar.edu.unlu.poker.vista.VistaConsolaSwing;

public class Controlador implements Observer{
	
	private VistaConsolaSwing vista;
	private Mesa mesa;
	
	public Controlador(VistaConsolaSwing vista, Mesa mesa) {
		this.vista = vista;
		this.mesa = mesa;
	}

	@Override
	public void update(Observado observado, Informe informe) {
		switch(informe) {
			case JUGADOR_MANO:
				vista.mostrarJugadorMano(((Mesa)observado).obtenerJugadorMano());
			break;
			case CARTAS_REPARTIDAS:
				vista.mostrarCartasJugador(((Mesa)observado).getJugadoresMesa());
			break;
			case CANT_JUGADORES_INSUFICIENTES:
				vista.informarJugadoresInsuficientes();
			break;
			case CANT_JUGADORES_EXCEDIDOS:
				vista.informarCantJugadoresExcedidos();
			break;
			/*case REALIZAR_APUESTAS:
				vista.solicitarApuestas();
			break;
			case JUGADOR_DECIDE_APOSTAR:
				//vista.solicitarApuestaJugador();
			break;
			case FONDO_INSUFICIENTE:
				vista.informarFondosInsuficientes();
			break;
			case APUESTA_INSUFICIENTE:
				vista.informarApuestaInsuficiente();
			break;*/
			case DEVOLVER_GANADOR:
				vista.mostrarGanador(((Mesa)observado).devolverGanador());
			break;
		}
	}
	
	/*@Override
	public void update(Observado observado, Informe informe, Jugador jugador) {
		switch(informe) {
			case JUGADOR_MANO:
				vista.mostrarJugadorMano(((Mesa)observado).obtenerJugadorMano());
			break;
			case CARTAS_REPARTIDAS:
				vista.mostrarCartasJugador(((Mesa)observado).getJugadoresMesa());
			break;
			case CANT_JUGADORES_INSUFICIENTES:
				vista.informarJugadoresInsuficientes();
			break;
			case CANT_JUGADORES_EXCEDIDOS:
				vista.informarCantJugadoresExcedidos();
			break;
			case REALIZAR_APUESTAS:
				vista.solicitarApuestas();
			break;
			case JUGADOR_DECIDE_APOSTAR:
				vista.solicitarApuestaJugador(jugador);
			break;
			case FONDO_INSUFICIENTE:
				vista.informarFondosInsuficientes();
			break;
			case APUESTA_INSUFICIENTE:
				vista.informarApuestaInsuficiente();
			break;
			case DEVOLVER_GANADOR:
				vista.mostrarGanador(((Mesa)observado).devolverGanador());
			break;
		}
	}*/

	public void agregarJugador(Jugador j) {
		mesa.agregarJugador(j);
	}
	
	public List<Jugador> getJugadoresMesa(){
		return mesa.getJugadoresMesa();
	}
	
	public List<Jugador> getListaOrdenadaJugadorMano() {
		return mesa.devolverJugadorEntregaCarta();
	}
	
	public List<Jugador> obtenerJugadores(){
		return mesa.getJugadoresMesa();
	}
	
	public void iniciarGame() {
		mesa.iniciarJuego();
	}
	
	/*public void consultarApuestas(int opcion) {
		mesa.igualarApuestas(opcion);
	}
	
	public void setearApuestaJugador(int apuesta, Jugador jugador) {
		mesa.jugadorDecideApostar(apuesta, jugador);
	}*/
	
	/*public void iniciarApuestas(){
		mesa.iniciarRondaApuestas();
		while (!mesa.rondaApuestasTerminada()) {
			for (Jugador jugador : mesa.getJugadoresMesa()) {
				if (jugador.isEnJuego()) {
					int cantidad = vista.pedirApuesta(jugador);
					mesa.procesarApuesta(jugador, cantidad);
				}
			}
		}
		mesa.finalizarRonda();
	}
	public int apuestaMinima() {
		return mesa.getApuestaMayor();
	}*/
}
