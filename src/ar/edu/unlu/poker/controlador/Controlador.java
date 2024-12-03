package ar.edu.unlu.poker.controlador;

import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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
	
	public int getApuestaMayor() throws RemoteException {
		return mesa.getApuestaMayor();
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
	
	public void jugadorSeRetiraDelJuego(Jugador jugador) throws RemoteException {
		mesa.sacarJugador(jugador);
	}
	
	public void iniciarGame() {
		try {
			mesa.iniciarJuego();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

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
		case JUGADOR_DECIDE_APOSTAR:
			Jugador jugadorTurno = mesa.getJugadorTurnoApuesta();
			vista.mostrarJugadoresTurnos(jugadorTurno);
		break;
		case TURNO_JUGADOR:
			vista.mostrarOpcionesApuestas(mesa.getJugadorTurnoApuesta());
		break;
		case FONDO_INSUFICIENTE:
			vista.notificarFondosInsuficientes();
		break;
		case FICHA_REALIZADA:
			vista.notificarFichaRealizada();
		break;
		case RONDA_TURNO_TERMINADA:
			vista.rondaApuestasFinalizada();
		break;
		case ENVITE_REALIZADO:
			vista.notificarEnviteRealizado();
		break;
		case PASAR_REALIZADO:
			vista.notificarJugadorHaPasado();
		break;
		}
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
		return mesa.getJugadoresMesa().get(0);
	}
	
	public void jugadorFicha(Jugador jugador) throws RemoteException{
		mesa.fichar(jugador);
	}
	
	public void jugadorEnvita(Jugador jugador, int apuesta) {
		try {
			mesa.envitar(jugador, apuesta);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void jugadorPasa(Jugador jugador) {
		try {
			mesa.pasar(jugador);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Jugador jugadorTurno(){
		try {
			return mesa.getJugadorTurnoApuesta();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void avanzarTurno() {
		try {
			mesa.determinarTurnoApuesta();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
}