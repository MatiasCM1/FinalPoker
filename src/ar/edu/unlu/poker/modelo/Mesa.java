package ar.edu.unlu.poker.modelo;

import java.rmi.RemoteException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import ar.edu.unlu.poker.comunes.Observado;
import ar.edu.unlu.poker.comunes.Observer;
import ar.edu.unlu.rmimvc.observer.ObservableRemoto;

public class Mesa extends ObservableRemoto implements IMesa{
	
	private List<Jugador> jugadoresMesa = new LinkedList<Jugador>();
	private static final HashMap<String, Integer> valorCarta = new HashMap<String, Integer>();
	private int posJugadorMano;
	private int apuestaMayor;
	
	private List<Jugador> jugadoresEnJuego;
	
	@Override
	public int getApuestaMayor() throws RemoteException{
		return apuestaMayor;
	}
	@Override
	public void setApuestaMayor(int apuestaMayor) throws RemoteException{
		this.apuestaMayor = apuestaMayor;
	}

	static {
		valorCarta.put("2", 2);
		valorCarta.put("3", 3);
		valorCarta.put("4", 4);
		valorCarta.put("5", 5);
		valorCarta.put("6", 6);
		valorCarta.put("7", 7);
		valorCarta.put("8", 8);
		valorCarta.put("9", 9);
		valorCarta.put("10", 10);
		valorCarta.put("J", 11);
		valorCarta.put("Q", 12);
		valorCarta.put("K", 13);
		valorCarta.put("AS", 14);
	}
	
	@Override
	public void agregarJugador(Jugador jugador) throws RemoteException {
		if (this.jugadoresMesa.size() < 8) {
			this.jugadoresMesa.add(jugador);
		} else {
			this.notificarObservadores(Informe.CANT_JUGADORES_EXCEDIDOS);
		}
	}
	
	@Override
	public Jugador obtenerJugadorMano() throws RemoteException{
		return this.jugadoresMesa.get(this.posJugadorMano);
	}
	
	@Override
	public void iniciarJuego() throws RemoteException {
		
		if (this.jugadoresMesa.size() < 2) {
			this.notificarObservadores(Informe.CANT_JUGADORES_INSUFICIENTES);
			return;
		}
	
		
		//while (this.jugadoresMesa.size() >= 2 && this.jugadoresMesa.size() <= 7) {

			this.jugadoresMesa.forEach(jugador -> jugador.setHapasado(false));
			this.jugadoresMesa.forEach(jugador -> jugador.setApuesta(0));
			this.apuestaMayor = 0;
			Dealer dealer = new Dealer();
			dealer.setearCartasRonda();
			this.posJugadorMano = this.seleccionarJugadorRandom();
			this.notificarObservadores(Informe.JUGADOR_MANO);
			dealer.repartirCartasRonda(this.jugadoresMesa, this.posJugadorMano);
			this.notificarObservadores(Informe.CARTAS_REPARTIDAS);
		
			this.notificarObservadores(Informe.REALIZAR_APUESTAS);
			
			//igualar apuestas
			
			//descartes
			//apuestas
		
			this.notificarObservadores(Informe.DEVOLVER_GANADOR);
			this.jugadoresMesa.forEach(Jugador::resetearCartas);
			//pasar al siguiente jguador mano
			
			//deea seguir jugando]?
		//}
	}
	
	public void gestionarApuestas(Jugador jugador, int apuesta) throws RemoteException {
		if (!jugador.comprobarFondosSuficientes(apuesta)) {
			this.notificarObservadores(Informe.FONDO_INSUFICIENTE);
		}
		if (apuesta >= this.apuestaMayor) {
			jugador.realizarApuesta(apuesta);
			this.notificarObservadores(Informe.APUESTA_REALIZADA);
		} else {
			this.notificarObservadores(Informe.APUESTA_INSUFICIENTE);
		}
	}
	
	@Override
	public void jugadorIgualaApuesta(Jugador jugador) throws RemoteException {
		if (!jugador.comprobarFondosSuficientes(this.apuestaMayor)) {
			this.notificarObservadores(Informe.FONDO_INSUFICIENTE);
		} else {
			jugador.realizarApuesta(apuestaMayor);
			this.notificarObservadores(Informe.APUESTA_REALIZADA);
		}
	}
	
	/*public void iniciarRondaApuestas() {
		this.apuestaMaxima = 0;
		this.jugadoresEnJuego = new LinkedList<Jugador>(this.jugadoresMesa);
	}
	
	public void procesarApuesta(Jugador jugador, int cantidad) {
		if (cantidad > 0) {
			jugador.envidar(cantidad);
			this.apuestaMaxima = Math.max(this.apuestaMaxima, cantidad);
		} else {
			jugador.pasar();
			this.jugadoresEnJuego.remove(jugador);
		}
		notificarObservers(Informe.APUESTA_REALIZADA);
	}
	
	public boolean rondaApuestasTerminada() {
		for (Jugador jugador : jugadoresEnJuego) {
			if (jugador.isEnJuego() && jugador.getApuesta() < this.apuestaMaxima) {
				return false;
			}
		}
		return true;
	}
	
	public void finalizarRonda() {
		if (this.jugadoresEnJuego.size() == 1) {
			notificarObservers(Informe.DEVOLVER_GANADOR);
		}
	}*/
	
	/*public void comenzarAGestionarApuestas() {
		this.setApuestaMayor(0);
		this.notificarObservers(Informe.REALIZAR_APUESTAS);
	}
	
	public void igualarApuestas(int opcion) {
		boolean apuestasIgualadas;
		do{
			apuestasIgualadas = true;
			for (Jugador jugador : jugadoresMesa){
				if (jugador.isHapasado() || jugador.getApuesta() == apuestaMayor) {
					continue; //No se le solicita la apuesta si el jugador ha pasado
				}
				switch(opcion) {
					case 1:	
						
						this.notificarObservers(Informe.JUGADOR_DECIDE_APOSTAR, jugador);
						
					break;
					case 2:
						
						jugador.pasar();
						
					break;
				}
				//Verificar que las apuestas esten igualadas
				for (Jugador j : jugadoresMesa) {
					if (!j.isHapasado() && j.getApuesta() != apuestaMayor) {
						apuestasIgualadas = false;
						break;
					}
				}
			}
		} while (!apuestasIgualadas);
	}
	
	
	public void jugadorDecideApostar(int apuesta, Jugador jugador) {
		if (apuesta > jugador.getFondo() || apuesta < this.getApuestaMayor()) { //Como se a que jugador le tengo que asignar
			this.notificarObservers(Informe.APUESTA_INSUFICIENTE);
		}
	}
	
	
	public void obtenerApuesta(int apuesta, Jugador jugador) {
		if (apuesta > jugador.getFondo() || apuesta < this.getApuestaMayor()) {
			
			this.notificarObservers(Informe.FONDO_INSUFICIENTE);
			
		} else if (apuesta < this.getApuestaMayor()) {
			
			this.notificarObservers(Informe.APUESTA_INSUFICIENTE);
			
			} else {
				
				jugador.setApuesta(apuesta);
				if (apuesta > this.getApuestaMayor()) {
					this.setApuestaMayor(apuesta);
				}
				
		}
	}*/
	
	@Override
	public List<Jugador> getJugadoresMesa() throws RemoteException{
		return jugadoresMesa;
	}
	
	private int seleccionarJugadorRandom() throws RemoteException{
		Random random = new Random();
		return random.nextInt(this.jugadoresMesa.size());
	}
	
	
	@Override
	public int getPosJugadorMano() throws RemoteException{
		return posJugadorMano;
	}
	
	@Override
	public List<Jugador> devolverJugadorEntregaCarta() throws RemoteException{
		
		List<Jugador> listaOrdenada = new LinkedList<Jugador>();
		int jugManoAux = this.posJugadorMano;
		for (int i = 0; i < this.jugadoresMesa.size(); i++) {
			listaOrdenada.add(this.jugadoresMesa.get(jugManoAux));
			jugManoAux = (jugManoAux + 1) % this.jugadoresMesa.size();
		}
		return listaOrdenada;		
	}
	
	private void calcularResultadoJugadores() {
		this.jugadoresMesa.forEach(t -> {
			try {
				t.calcularValorCartas();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
	
	@Override
	public List<Jugador> devolverGanador() throws RemoteException{
		calcularResultadoJugadores();
		List<Jugador> ganador = new LinkedList<Jugador>();
		ganador.add(this.jugadoresMesa.get(0));
		for (int i = 1; i < this.jugadoresMesa.size(); i++) {
			Jugador jugadorActual = this.jugadoresMesa.get(i);
			Jugador jugadorGanador = ganador.get(0);
			if (jugadorActual.getResultadoValoresCartas().ordinal() > jugadorGanador.getResultadoValoresCartas().ordinal()) {
				ganador.clear();
				ganador.add(jugadorActual);
			} else if (jugadorActual.getResultadoValoresCartas().ordinal() == jugadorGanador.getResultadoValoresCartas().ordinal()) {
				Jugador jugadorConCartaMayor = buscarCartaMayor(jugadorGanador, jugadorActual);
				if (jugadorConCartaMayor.equals(jugadorActual)) {
					ganador.clear();
					ganador.add(jugadorActual);
				} else if (jugadorConCartaMayor.equals(jugadorGanador)) {
					ganador.clear();
					ganador.add(jugadorGanador);
				}
			}
		}
		return ganador;
	}
	
	private Jugador buscarCartaMayor(Jugador jugador1, Jugador jugador2) throws RemoteException {
		Carta carta1 = jugador1.getCartasOrdenadas().getLast();
		Carta carta2 = jugador2.getCartasOrdenadas().getLast();
		LinkedList <Carta> cartasAlta = new LinkedList<Carta>();
		cartasAlta.add(carta1);
		cartasAlta.add(carta2);
		ResultadoJugadaJugador resultado = new ResultadoJugadaJugador();
		Carta cartaMasAlta = resultado.cartaMasAlta(cartasAlta);
		if (cartaMasAlta.equals(carta1)) {
			return jugador1;
		} else if (cartaMasAlta.equals(carta2)){
			return jugador2;
		} 
		return null;
	}
	
	public void sacarJugador(Jugador jugador) {
		this.jugadoresMesa.remove(jugador);
	}

}
