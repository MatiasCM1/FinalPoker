package ar.edu.unlu.poker.modelo;

import java.util.LinkedList;

public class Jugador {

	private LinkedList<Carta> cartas = new LinkedList<Carta>();
	private String nombre;
	private Resultado resultadoValoresCartas;
	private int apuesta;
	private int fondo;//Fondo disponible para apuestas
	private boolean hapasado;
	
	public Jugador(String nombre) {
		this.nombre = nombre;
		this.apuesta = 0;
		this.fondo = 1000;// Fondo inicial por el momento
		this.setHapasado(false);
	}
	
	public Jugador(String nombre, int fondo) {
		this.nombre = nombre;
		this.fondo = fondo;
		this.apuesta = 0;
		this.setHapasado(false);
	}
	
	public void resetearCartas() {
		this.cartas.clear();
	}

	public LinkedList<Carta> getCartas() {
		return cartas;
	}
	
	public boolean descartarCarta(Carta c) {
		return this.cartas.removeIf(carta -> carta.getValor().equals(c.getValor()) && carta.getPalo().equals(c.getPalo()));
	}

	public int getApuesta() {
		return apuesta;
	}

	public String getNombre() {
		return nombre;
	}
	
	public Informe realizarApuesta(int cantidad) {
		if (cantidad <= this.fondo) {
			this.apuesta += cantidad;
			this.fondo -= cantidad;
			this.setHapasado(false);
			return Informe.APUESTA_REALIZADA;
		} else {
			return Informe.FONDO_INSUFICIENTE;
		}
	}
	
	public void pasar() {
		this.setHapasado(true);
	}

	public void recibirCarta(Carta carta) {
		this.cartas.add(carta);
	}
	
	public void calcularValorCartas() {
		ResultadoJugadaJugador jugada = new ResultadoJugadaJugador();
        this.resultadoValoresCartas = jugada.devolverValor(new LinkedList<Carta>(this.cartas));
	}
	
	public LinkedList<Carta> getCartasOrdenadas(){
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

}
