package ar.edu.unlu.poker.controlador;

import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;

import ar.edu.unlu.poker.comunes.Observado;
import ar.edu.unlu.poker.comunes.Observer;
import ar.edu.unlu.poker.modelo.IMesa;
import ar.edu.unlu.poker.modelo.Informe;
import ar.edu.unlu.poker.modelo.Jugador;
import ar.edu.unlu.poker.vista.IVista;
import ar.edu.unlu.rmimvc.cliente.IControladorRemoto;
import ar.edu.unlu.rmimvc.observer.IObservableRemoto;

public class Controlador implements IControladorRemoto{
	
	private IVista vista;
	private IMesa mesa;
	
	public Controlador(IVista vista/*, IMesa mesa*/) {
		this.vista = vista;
		//this.mesa = mesa;
	}

	/*@Override
	public void update(Observado observado, Informe informe) {
		switch(informe) {
			case JUGADOR_MANO:
				vista.mostrarJugadorMano(((IMesa)observado).obtenerJugadorMano());
			break;
			case CARTAS_REPARTIDAS:
				vista.mostrarCartasJugador(((IMesa)observado).getJugadoresMesa());
			break;
			case CANT_JUGADORES_INSUFICIENTES:
				vista.informarJugadoresInsuficientes();
			break;
			case CANT_JUGADORES_EXCEDIDOS:
				vista.informarCantJugadoresExcedidos();
			break;
			case DEVOLVER_GANADOR:
				vista.mostrarGanador(((IMesa)observado).devolverGanador());
			break;
		}
	}*/

	public void agregarJugador(Jugador j) {
		try {
			mesa.agregarJugador(j);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<Jugador> getJugadoresMesa(){
		try {
			return mesa.getJugadoresMesa();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Jugador> getListaOrdenadaJugadorMano() {
		try {
			return mesa.devolverJugadorEntregaCarta();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Jugador> obtenerJugadores(){
		try {
			return mesa.getJugadoresMesa();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public void iniciarGame() {
		try {
			mesa.iniciarJuego();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
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
	}
	}

	@Override
	public <T extends IObservableRemoto> void setModeloRemoto(T modeloRemoto) throws RemoteException {
		this.mesa = (IMesa) modeloRemoto;
	}
}
