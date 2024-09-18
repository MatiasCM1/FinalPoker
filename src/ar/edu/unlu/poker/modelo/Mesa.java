package ar.edu.unlu.poker.modelo;

import java.rmi.RemoteException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;

import ar.edu.unlu.rmimvc.observer.ObservableRemoto;

public class Mesa extends ObservableRemoto implements IMesa{
	
	private Queue<Jugador> jugadoresMesa = new LinkedList<Jugador>();
	private static final HashMap<String, Integer> valorCarta = new HashMap<String, Integer>();
	private int apuestaMayor;
	private Jugador jugadorMano;

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
	public void iniciarJuego() throws RemoteException {
		
		if (this.jugadoresMesa.size() < 2) {
			this.notificarObservadores(Informe.CANT_JUGADORES_INSUFICIENTES);
			return;
		}
		
		Jugador jugadorAux;
		
		this.seleccionarJugadorRandom();
		this.jugadorMano = this.jugadoresMesa.peek();
		
		//while (this.jugadoresMesa.size() >= 2 && this.jugadoresMesa.size() <= 7) {

			this.jugadoresMesa.forEach(jugador -> jugador.setEnJuego(true));
			this.jugadoresMesa.forEach(jugador -> jugador.setApuesta(0));
			this.jugadorMano = this.jugadoresMesa.peek(); //Guardo el jugador mano de la ronda
			//this.jugadoresMesa.add(jugadorMano);//pongo el jugador mano en ultimo lugar
			this.apuestaMayor = 0;
			Dealer dealer = new Dealer();
			dealer.setearCartasRonda();
			//this.posJugadorMano = this.seleccionarJugadorRandom();
			this.notificarObservadores(Informe.JUGADOR_MANO);
			dealer.repartirCartasRonda(this.jugadoresMesa);
			this.notificarObservadores(Informe.CARTAS_REPARTIDAS);
		
			this.gestionarTurnosApuestas();
			
			esperarQueTodosApuesten();
			
			//igualar apuestas
			
			//descartes
			//apuestas
		
			this.notificarObservadores(Informe.DEVOLVER_GANADOR);
			this.jugadoresMesa.forEach(Jugador::resetearCartas);
			//pasar al siguiente jguador mano
			jugadorAux = this.jugadoresMesa.poll();
			this.jugadoresMesa.add(jugadorAux); //Agrego al anterior jugador mano al final de la cola
			
			//deea seguir jugando]?
		//}
	}
	
	public void esperarQueTodosApuesten(){
	    boolean todosHanApostadoOPasado = false;
	    while (!todosHanApostadoOPasado) {
	        todosHanApostadoOPasado = true;
	        for (Jugador jugador : jugadoresMesa) {
	            if (!jugador.getHaApostado() || jugador.isEnJuego()) {
	                todosHanApostadoOPasado = false;
	            }
	        }
	    }
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
	
	
	@Override
	public List<Jugador> getJugadoresMesa() throws RemoteException{
		return List.copyOf(this.jugadoresMesa);
	}
	
	private void seleccionarJugadorRandom() throws RemoteException{
		List<Jugador> jugadoresMezclados = new LinkedList<Jugador>(this.jugadoresMesa);
		Collections.shuffle(jugadoresMezclados);
		this.jugadoresMesa.clear();
		this.jugadoresMesa.addAll(jugadoresMezclados);
		
	}
	
	
	@Override
	public int getApuestaMayor() throws RemoteException{
		return apuestaMayor;
	}
	@Override
	public void setApuestaMayor(int apuestaMayor) throws RemoteException{
		this.apuestaMayor = apuestaMayor;
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
	public List<Jugador> devolverGanador() throws RemoteException {
	    // Calcular el resultado de cada jugador antes de determinar al ganador
	    calcularResultadoJugadores();

	    // Usar una cola en lugar de una lista para almacenar al ganador o ganadores
	    Queue<Jugador> ganadores = new LinkedList<>();
	    
	    // Iniciar con el primer jugador en la cola de jugadoresMesa
	    Jugador jugadorGanador = this.jugadoresMesa.peek();
	    ganadores.add(jugadorGanador);  // A�adir el primer jugador como ganador temporal

	    // Iterar sobre el resto de los jugadores en la cola
	    for (Jugador jugadorActual : this.jugadoresMesa) {
	        // Comparar el resultado del jugador actual con el jugador ganador
	        if (jugadorActualMayorJugadorGanador(jugadorGanador, jugadorActual)) {
	            ganadores.clear();  // Si hay un nuevo ganador, vaciar la cola de ganadores anteriores
	            ganadores.add(jugadorActual);  // Agregar el nuevo ganador
	            jugadorGanador = jugadorActual;  // Actualizar el jugador ganador
	        } else if (jugadorActualIgualJugadorGanador(jugadorGanador, jugadorActual)) {
	            // Si tienen el mismo resultado, comparar la carta mayor
	            Jugador jugadorConCartaMayor = buscarCartaMayor(jugadorGanador, jugadorActual);
	            if (jugadorConCartaMayor.equals(jugadorActual)) {
	                ganadores.clear();  // Limpiar si el nuevo jugador tiene la carta mayor
	                ganadores.add(jugadorActual);  // Agregar el nuevo ganador con la carta mayor
	                jugadorGanador = jugadorActual;  // Actualizar el jugador ganador
	            }
	        }
	    }
	    
	    return List.copyOf(ganadores); // Retornar la cola de ganadores
	}

	private boolean jugadorActualIgualJugadorGanador(Jugador jugadorGanador, Jugador jugadorActual) {
		return jugadorActual.getResultadoValoresCartas().ordinal() == jugadorGanador.getResultadoValoresCartas().ordinal();
	}

	private boolean jugadorActualMayorJugadorGanador(Jugador jugadorGanador, Jugador jugadorActual) {
		return jugadorActual.getResultadoValoresCartas().ordinal() > jugadorGanador.getResultadoValoresCartas().ordinal();
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
	
	private void gestionarTurnosApuestas() throws RemoteException {
		 LinkedList<Jugador> colaAux = new LinkedList<Jugador>();
		 Jugador jugador;
		 while (!this.jugadoresMesa.isEmpty()) {
			 jugador = this.jugadoresMesa.poll();
			 this.notificarObservadores(Informe.REALIZAR_APUESTAS);
			 esperarQueJugadorApueste(jugador);
			 colaAux.add(jugador);
		 }
		 
		 for (Jugador j : colaAux) {
			 this.jugadoresMesa.add(j);
		 }
	}
	
	
	public void esperarQueJugadorApueste(Jugador jugador){
	    boolean jugadorHaApostadoOPasado = false;
	    while (!jugadorHaApostadoOPasado) {
	        if (jugador.getHaApostado() || !jugador.isEnJuego()) {
	        	jugadorHaApostadoOPasado = true;
	        }
	    }
	}
	
	
	public Jugador getJugadorMano() {
		return this.jugadorMano;
	}

}
