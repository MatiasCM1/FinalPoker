package ar.edu.unlu.poker.controlador;

import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import ar.edu.unlu.poker.modelo.Carta;
import ar.edu.unlu.poker.modelo.IMesa;
import ar.edu.unlu.poker.modelo.Informe;
import ar.edu.unlu.poker.modelo.Jugador;
import ar.edu.unlu.poker.vista.IVista;
import ar.edu.unlu.poker.vista.consola.Estados;
import ar.edu.unlu.rmimvc.cliente.IControladorRemoto;
import ar.edu.unlu.rmimvc.observer.IObservableRemoto;

public class Controlador implements IControladorRemoto{
	
	private IVista vista;
	private IMesa mesa;
	private Jugador jugadorActual;
	private Jugador jugadorVistaApuestas;

	@Override
	public void actualizar(IObservableRemoto modelo, Object cambio) throws RemoteException {
		switch((Informe) cambio) {
			case JUGADOR_MANO:
				vista.mostrarJugadorMano((getJugadorMano()));
				break;
			case CARTAS_REPARTIDAS:
				vista.mostrarCartasJugador(((IMesa)modelo).getJugadoresMesa());
				break;
			case CANT_JUGADORES_INSUFICIENTES:
				vista.informarJugadoresInsuficientes();
				break;
			case CANT_JUGADORES_EXCEDIDOS:
				vista.informarCantJugadoresExcedidos();
				break;
			case DEVOLVER_GANADOR:
				vista.mostrarGanador(((IMesa)modelo).devolverGanador());
				break;
			case TURNO_APUESTA_JUGADOR:
				if (this.jugadorActual.getNombre().equals(this.getJugadorTurno().getNombre())) {
					vista.mostrarMenuApuestas();
				} else {
					vista.informarTurnoApuestaOtroJugador();
				}
				break;
			case FONDO_INSUFICIENTE:
				vista.informarFondosInsuficientes();
				break;
			case APUESTA_REALIZADA:
				vista.informarApuestaRealizada(this.getJugadorTurno().getNombre(), mesa.getApuestaJugador(this.getJugadorTurno()));
				break;
			case INFORMAR_NO_TURNO:
				vista.informarNoTurno();
				break;
			case APUESTAS_DESIGUALES:
				if (mesa.perteneceJugadorApuestaMenor(this.jugadorActual)) { //Comprueba que el nombre del jugadorActuañ forme parte de la cola de jugadores con  apuesta menor a la mayor
					vista.notificarApuestasDesiguales();
				} else {
					vista.notificarEsperarJugadorIgualeApuesta();
				}
				break;
			case JUGADOR_IGUALA_APUESTA:
				//if (this.jugadorActual.equals(this.getJugadorTurnoParaAposter())) {
				vista.notificarJugadorIgualaApuesta();
				//mesa.mirarSiDevolverResultados();
				//}
				break;
			case JUGADOR_PASA_APUESTA:
				//if (this.jugadorActual.equals(this.getJugadorTurnoParaAposter())) {
				vista.notificarJugadorPasaApuesta();
				//mesa.mirarSiDevolverResultados();
				
				break;
			case TURNO_DESCARTE:
				//vista.notificarRondaApuestaFinalizada();
				if (isJugadorTurno()) {
					vista.mostrarMenuDescartes();//LLAMO AL MENU DE DESCARTE
				} else {
					vista.notificarEsperarDescartes();
				}
				break;
			case RONDA_APUESTAS_TERMINADA:
				if (this.isJugadorTurno()) {
					vista.notificarRondaApuestaFinalizada();
					mesa.mirarSiDevolverResultados();
				}
				break;
			case CARTA_DESCARTADA:
				if (isJugadorTurno()) {
					vista.notificarCartaDescartadaConExito();
				}
				break;
			case CARTA_YA_HABIA_SIDO_DESCARTADA:
				if (isJugadorTurno()) {
					vista.notificarErrorIntentarDescarteEnUnaCartaYaDescartada();
				}
				break;
			case SEGUNDA_RONDA_APUESTAS:
				
				break;
		}
		
	}
	
	private boolean isJugadorTurno() throws RemoteException {
		return this.jugadorActual.equals(this.getJugadorTurno());
	}

	public void iniciarGame() {
		try {
			mesa.iniciarJuego();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public Controlador(IVista vista) {
		this.vista = vista;
	}

	public void agregarJugador(Jugador j) {
		try {
			mesa.agregarJugador(j);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public List<Jugador> getJugadoresMesa(){
		try {
			return mesa.getJugadoresMesa();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Jugador> getListaOrdenadaJugadorMano() {
		try {
			return mesa.getJugadoresMesa();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Jugador> obtenerJugadores(){
		try {
			return mesa.getJugadoresMesa();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public <T extends IObservableRemoto> void setModeloRemoto(T modeloRemoto) throws RemoteException {
		this.mesa = (IMesa) modeloRemoto;
	}


	public Jugador getJugadorActual() {
		return jugadorActual;
	}

	public void setJugadorActual(Jugador jugadorActual) {
		this.jugadorActual = jugadorActual;
	}
	
	public Jugador getJugadorMano() throws RemoteException {
		return mesa.getJugadorMano();
	}
	
	public void jugadorSeRetiraDelJuego(Jugador jugador) throws RemoteException {
		mesa.sacarJugador(jugador);
	}
	
	public Jugador getJugadorTurno() {
		try {
			return mesa.getJugadorTurno();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void realizarLasApuestas(Jugador jugador, int apuesta) {
		try {
			if (apuesta >= mesa.getApuestaMayor()) {
				mesa.realizarApuesta(jugador, apuesta);
			} else {
				if (this.jugadorActual.getNombre().equals(this.getJugadorTurno().getNombre())) {
					vista.notificarApuestaMenorALaAnterior();
					vista.mostrarMenuApuestas();
				} else {
					vista.informarTurnoApuestaOtroJugador();
				}
				
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public void realizarLasApuestas(Jugador jugador) {
		try {
			mesa.realizarApuesta(jugador, mesa.getApuestaMayor());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public void realizarLosPases(Jugador jugador) {
		try {
			mesa.jugadorPasa(jugador);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void realizarFicha(Jugador jugador) {
		try {
			this.jugadorVistaApuestas = jugador;
			mesa.jugadorFichaPostEnvite(jugador);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void realizarPasar(Jugador jugador) {
		try {
			this.jugadorVistaApuestas = jugador;
			mesa.jugadorPasaPostEnvite(jugador);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public LinkedList<Carta> obtenerCartasJugador(Jugador jugador) {
		try {
			for (Jugador j : mesa.getJugadoresMesa()) {
				if (j.equals(jugador)) {
					return j.getCartas();
				}
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void cartaADescartar(int posicionCarta, Jugador jugador) {
		try {
			mesa.agregarCartasADescartar(posicionCarta, jugador);//AGREGO EL JUGADOR Y LA CARTA EN UN HASHMAP
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void continuarJuegoPostDescarte(Jugador jugador) {
		try {
			mesa.realizarElDescarte(jugador);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}