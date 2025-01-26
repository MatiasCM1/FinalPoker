package ar.edu.unlu.poker.modelo;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import ar.edu.unlu.rmimvc.observer.IObservableRemoto;

public interface IMesa extends IObservableRemoto {

	int getApuestaMayor() throws RemoteException;

	void setApuestaMayor(int apuestaMayor) throws RemoteException;

	void agregarJugador(Jugador jugador) throws RemoteException;

	void iniciarJuego() throws RemoteException;

	List<Jugador> getJugadoresMesa() throws RemoteException;

	//List<Jugador> devolverGanador() throws RemoteException;
	
	Jugador devolverGanador() throws RemoteException;
	
	Jugador getJugadorMano() throws RemoteException;

	void sacarJugador(Jugador jugador) throws RemoteException;
	
	Jugador getJugadorTurno() throws RemoteException;
	
	void realizarApuesta(Jugador jugador, int apuesta)  throws RemoteException;
	
	int getApuestaJugador(Jugador jugador) throws RemoteException;

	void jugadorFichaPostEnvite(Jugador jugador) throws RemoteException;
	
	void jugadorPasaPostEnvite(Jugador jugador) throws RemoteException;

	boolean perteneceJugadorApuestaMenor(Jugador jugador) throws RemoteException;

	void mirarSiDevolverResultados() throws RemoteException;

	void jugadorPasa(Jugador jugador) throws RemoteException;
	
	public void agregarCartasADescartar(int posicionCarta, Jugador jugador) throws RemoteException;

	void realizarElDescarte(Jugador jugador) throws RemoteException;

	List<Jugador> getRondaApuesta() throws RemoteException;

	void realizarSegundaRondaApuesta(Jugador jugador, int apuesta) throws RemoteException;

	void jugadorPasaSegundaRonda(Jugador jugador) throws RemoteException;

	void jugadorFichaPostEnviteSegundaRonda(Jugador jugador) throws RemoteException;

	void jugadorPasaPostEnviteSegundaRonda(Jugador jugador) throws RemoteException;

	List<Jugador> getRondaApuestaAux() throws RemoteException;
	
	void darFondosGanador(Jugador jugador) throws RemoteException;

	void agregarNuevosFondos(Jugador jugador, int fondoAgregar) throws RemoteException;

	boolean isPrimeraRonda() throws RemoteException;

	void setPrimeraRonda(boolean primeraRonda) throws RemoteException;

	void removerJugadores(Jugador jugador) throws RemoteException;
	
}