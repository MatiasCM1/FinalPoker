package ar.edu.unlu.poker.modelo;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Queue;

import ar.edu.unlu.rmimvc.observer.IObservableRemoto;

public interface IMesa extends IObservableRemoto {

	int getApuestaMayor() throws RemoteException;

	void setApuestaMayor(int apuestaMayor) throws RemoteException;

	void agregarJugador(Jugador jugador) throws RemoteException;

	Jugador obtenerJugadorMano() throws RemoteException;

	void iniciarJuego() throws RemoteException;

	List<Jugador> getJugadoresMesa() throws RemoteException;

	List<Jugador> devolverGanador() throws RemoteException;

	void sacarJugador(Jugador jugador) throws RemoteException;

	void gestionarApuestas(Jugador jugador, int apuesta) throws RemoteException;

	void jugadorIgualaApuesta(Jugador jugador) throws RemoteException;

}