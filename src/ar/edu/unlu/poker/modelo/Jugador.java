package ar.edu.unlu.poker.modelo;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.LinkedList;

public class Jugador implements Serializable{

	private LinkedList<Carta> cartas = new LinkedList<Carta>();
	private String nombre;
	private Resultado resultadoValoresCartas;
	private int apuesta;
	private int fondo;//Fondo disponible para apuestas
	private boolean hapasado;
	private boolean enJuego;//Indica si el jugador sigue en juego o ha pasado
	
	public Jugador(String nombre) {
		this.nombre = nombre;
		this.apuesta = 0;
		this.fondo = 1000;// Fondo inicial por el momento
		this.setHapasado(false);
		this.setEnJuego(true);
	}
	
	public Jugador(String nombre, int fondo) {
		this.nombre = nombre;
		this.fondo = fondo;
		this.apuesta = 0;
		this.setHapasado(false);
		this.setEnJuego(true);
	}
	
	public void resetearCartas() {
		this.cartas.clear();
	}

	public LinkedList<Carta> getCartas() {
		return cartas;
	}

	public int getApuesta() {
		return apuesta;
	}
	
	public void setApuesta(int apuesta) {
		this.apuesta = apuesta;
	}

	public String getNombre() {
		return nombre;
	}
	
	public void realizarApuesta(int cantidad) {
		this.apuesta += cantidad;
		this.fondo -= cantidad;
	}
	
	public boolean comprobarFondosSuficientes(int cantidad) {
		if (cantidad > this.fondo) {
			return false;
		}
		return true;
	}
	

	public void recibirCarta(Carta carta) {
		this.cartas.add(carta);
	}
	
	public void calcularValorCartas() throws RemoteException {
		ResultadoJugadaJugador jugada = new ResultadoJugadaJugador();
        this.resultadoValoresCartas = jugada.devolverValor(new LinkedList<Carta>(this.cartas));
	}
	
	public LinkedList<Carta> getCartasOrdenadas() throws RemoteException{
		ResultadoJugadaJugador r = new ResultadoJugadaJugador();
		return r.ordenarCartas(new LinkedList<Carta>(this.cartas));
	}
	

	public Resultado getResultadoValoresCartas() {
		return resultadoValoresCartas;
	}

	public boolean isHapasado() {
		return hapasado;
	}

	public void setHapasado(boolean hapasado) {
		this.hapasado = hapasado;
	}
	
	public int getFondo() {
		return this.fondo;
	}
	
	public void pasar() {
		this.setEnJuego(false);
	}

	public boolean isEnJuego() {
		return enJuego;
	}

	public void setEnJuego(boolean enJuego) {
		this.enJuego = enJuego;
	}

}
