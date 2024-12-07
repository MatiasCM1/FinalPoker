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
	
	Jugador getJugadorMano() throws RemoteException;

	void sacarJugador(Jugador jugador) throws RemoteException;
	
	Jugador getJugadorTurnoApuesta() throws RemoteException;
	
	void realizarApuesta(Jugador jugador, int apuesta)  throws RemoteException;
	
	int getApuestaJugador(Jugador jugador) throws RemoteException;

	void jugadorFichaPostEnvite(Jugador jugador) throws RemoteException;
	
	void jugadorPasaPostEnvite(Jugador jugador) throws RemoteException;

	boolean perteneceJugadorApuestaMenor(Jugador jugador) throws RemoteException;
}