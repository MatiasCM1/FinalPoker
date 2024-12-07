package ar.edu.unlu.poker.modelo;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.Objects;

public class Jugador implements Serializable{

	private LinkedList<Carta> cartas = new LinkedList<Carta>();
	private String nombre;
	private Resultado resultadoValoresCartas;
	private int apuesta;
	private int fondo;
	private boolean enJuego;//Indica si el jugador sigue en juego o ha pasado
	private boolean haApostado;
	
	public Jugador(String nombre) {
		this.nombre = nombre;
		this.apuesta = 0;
		this.fondo = 1000;// Fondo inicial por el momento
		this.setEnJuego(true);
	}
	
	public Jugador(String nombre, int fondo) {
		this.nombre = nombre;
		this.fondo = fondo;
		this.apuesta = 0;
		this.setEnJuego(true);
	}
	

	

	@Override
	public int hashCode() {
		return Objects.hash(nombre);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Jugador other = (Jugador) obj;
		return Objects.equals(nombre, other.nombre);
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
		this.haApostado = true;
	}
	
	public boolean getHaApostado() {
		return this.haApostado;
	}
	
	public void setHaApostado(boolean aposto) {
		this.haApostado = aposto;
	}
	
	public void resetearTurno() {
        this.haApostado = false;
        this.enJuego = true;
    }
	
	public boolean comprobarFondosSuficientes(int cantidad) {
		return cantidad <= this.fondo;
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
