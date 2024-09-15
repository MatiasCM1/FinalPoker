package ar.edu.unlu.poker.controlador;

import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;

import ar.edu.unlu.poker.modelo.IMesa;
import ar.edu.unlu.poker.modelo.Informe;
import ar.edu.unlu.poker.modelo.Jugador;
import ar.edu.unlu.poker.vista.IVista;
import ar.edu.unlu.rmimvc.cliente.IControladorRemoto;
import ar.edu.unlu.rmimvc.observer.IObservableRemoto;

public class Controlador implements IControladorRemoto{
	
	private IVista vista;
	private IMesa mesa;
	
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
			return mesa.devolverJugadorEntregaCarta();
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
	
	public void realizarApuesta(Jugador jugador, int apuesta) throws RemoteException {
		mesa.gestionarApuestas(jugador, apuesta);
	}
	
	public void igualarApuesta(Jugador jugador) throws RemoteException {
		mesa.jugadorIgualaApuesta(jugador);
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
			vista.mostrarJugadorMano(((IMesa)modelo).obtenerJugadorMano());
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
		case FONDO_INSUFICIENTE:
			vista.notificarFondosInsuficientes();
		break;
		case APUESTA_INSUFICIENTE:
			vista.notificarApuestaInsuficiente();
		break;
		case APUESTA_REALIZADA:
			vista.notificarApuestaRealizada();
		break;
		case REALIZAR_APUESTAS:
<<<<<<< HEAD
=======
			//vista.mostrarApuestas();
>>>>>>> ca55cfb84d248f710db8c2c9e2aa9fd1e1b7f5d9
			vista.mostrarOpcionesApuestas();
		break;
		
	}
	}

	@Override
	public <T extends IObservableRemoto> void setModeloRemoto(T modeloRemoto) throws RemoteException {
		this.mesa = (IMesa) modeloRemoto;
	}

	
}
