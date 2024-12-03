package ar.edu.unlu.poker.modelo;

import java.rmi.RemoteException;
import java.util.List;

import ar.edu.unlu.rmimvc.observer.IObservableRemoto;

public interface IMesa extends IObservableRemoto {

	int getApuestaMayor() throws RemoteException;

	void setApuestaMayor(int apuestaMayor) throws RemoteException;

	void agregarJugador(Jugador jugador) throws RemoteException;

	void iniciarJuego() throws RemoteException;

	List<Jugador> getJugadoresMesa() throws RemoteException;

	List<Jugador> devolverGanador() throws RemoteException;

	void sacarJugador(Jugador jugador) throws RemoteException;

	void gestionarApuestas(Jugador jugador, int apuesta) throws RemoteException;
	
	void determinarTurnoApuesta() throws RemoteException;
	
	Jugador getJugadorTurnoApuesta() throws RemoteException;
	
	Jugador getJugadorMano() throws RemoteException;
	
	void fichar(Jugador jugador) throws RemoteException;

	void envitar(Jugador jugador, int apuesta) throws RemoteException;

	void pasar(Jugador jugador) throws RemoteException;

}