package ar.edu.unlu.poker.modelo;

import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;

import ar.edu.unlu.rmimvc.observer.IObservableRemoto;

public interface IMesa extends IObservableRemoto {

	int getApuestaMayor() throws RemoteException;

	void setApuestaMayor(int apuestaMayor) throws RemoteException;

	void agregarJugador(Jugador jugador) throws RemoteException;

	void iniciarJuego() throws RemoteException;

	List<Jugador> getJugadoresMesa() throws RemoteException;

	String devolverGanador() throws RemoteException;

	String getJugadorMano() throws RemoteException;

	void sacarJugador(Jugador jugador) throws RemoteException;

	String getJugadorTurno() throws RemoteException;

	void realizarApuesta(Jugador jugador, int apuesta) throws RemoteException;

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

	void darFondosGanador(String jugador) throws RemoteException;

	void agregarNuevosFondos(Jugador jugador, int fondoAgregar) throws RemoteException;

	boolean getPrimeraRonda() throws RemoteException;

	void setPrimeraRonda(boolean primeraRonda) throws RemoteException;

	void removerJugadores(Jugador jugador) throws RemoteException;

	void marcarComoListoParaIniciar(Jugador jugador) throws RemoteException;

	void marcarComoNoListoParaIniciar(Jugador jugador) throws RemoteException;

	int buscarApuestaMayorEnElMapa() throws RemoteException;

	String getJugadorPasa() throws RemoteException;

	void setComenzoPartida(boolean comenzo) throws RemoteException;

	boolean getComenzoPartida() throws RemoteException;

	void devolverFondos() throws RemoteException;

	void removerJugadorSeRetiraEnJuego(Jugador jugador) throws RemoteException;

	void borrarJugadoresMesa() throws RemoteException;

	void setearJugadoresMezclados(List<Jugador> jugadoresMezclados) throws RemoteException;

	int getfondosJugador(Jugador jugador) throws RemoteException;

	String getNombreUltimoJugadorIntentaAgregar() throws RemoteException;

	int getIDUltimoJugadorIntentaAgregar() throws RemoteException;

	int getIDJugadorIntentaIncrementarFondos() throws RemoteException;

	boolean esJugadorConFondosInsuficientesParaComenzar(Jugador jugador) throws RemoteException;

	boolean getTodosLosJugadoresEstanListosParaIniciar() throws RemoteException;

	LinkedList<Carta> getCartasJugador(Jugador jugadorActual) throws RemoteException;

	boolean comprobarJugadorSigueEnJuego(Jugador jugadorActual) throws RemoteException;

	int getApuestaJugadorTurno() throws RemoteException;

	boolean soloQuedaUnJugadorEnJuego() throws RemoteException;

	String obtenerNombreUnicoJugadorEnJuego() throws RemoteException;

	Resultado getResultadoJugador(String jugador) throws RemoteException;

}