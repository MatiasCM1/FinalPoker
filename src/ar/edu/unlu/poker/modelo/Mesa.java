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
	private  Queue<Jugador> rondaApuesta = new LinkedList<Jugador>();
	private  Queue<Jugador> jugadoresApuestaInsuficiente = new LinkedList<Jugador>();
	private static final HashMap<String, Integer> valorCarta = new HashMap<String, Integer>();
	private HashMap<Jugador, Integer> mapa = new HashMap<Jugador, Integer>();
	private int apuestaMayor;
	private Jugador jugadorMano;
	private Jugador jugadorTurnoApuesta;

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
		
		
		//while (this.jugadoresMesa.size() >= 2 && this.jugadoresMesa.size() <= 7) {

			this.jugadoresMesa.forEach(jugador -> jugador.setEnJuego(true));
			this.jugadoresMesa.forEach(jugador -> jugador.setApuesta(0));
			this.jugadoresMesa.forEach(jugador -> jugador.setHaApostado(false));
			this.jugadoresApuestaInsuficiente.clear();
			
			this.seleccionarJugadorRandom();
			this.jugadorMano = this.jugadoresMesa.poll();
			this.jugadorTurnoApuesta = this.jugadorMano;
			this.jugadoresMesa.add(this.jugadorMano);
			
			this.apuestaMayor = 0;
			Dealer dealer = new Dealer();
			dealer.setearCartasRonda();
			
			this.notificarObservadores(Informe.JUGADOR_MANO);
			
			dealer.repartirCartasRonda(this.jugadoresMesa);
			
			this.notificarObservadores(Informe.CARTAS_REPARTIDAS);
			
			this.notificarObservadores(Informe.TURNO_APUESTA_JUGADOR);
			
			//descartes
			//apuestas
		
			//this.notificarObservadores(Informe.DEVOLVER_GANADOR);
			//this.jugadoresMesa.forEach(Jugador::resetearCartas);
			//pasar al siguiente jguador mano
			//jugadorAux = this.jugadoresMesa.poll();
			//this.jugadoresMesa.add(jugadorAux); //Agrego al anterior jugador mano al final de la cola
			
			//deea seguir jugando]?
		//}
	}
	
	@Override
	public void realizarApuesta(Jugador jugador, int apuesta)  throws RemoteException{
		if (comprobarFinalVueltaApuesta(jugador)) { //Termino la ronda de apuesta
			//Comprobar que todos sean iguales
			//ACA VOY A USAR EL RONDAAPUESTA
			if (!comprobarIgualdad()) {
				this.determinarJugadoresConApuestaInsuficiente();
				this.notificarObservadores(Informe.APUESTAS_DESIGUALES);
			}
			return;
		}
		if (esJugadorTurnoApuesta(jugador)) {
			if (jugador.comprobarFondosSuficientes(apuesta)) {
				this.restarFondosAgregarApuestaJugador(this.jugadorTurnoApuesta, apuesta);
				if (apuesta > this.apuestaMayor) {
					this.apuestaMayor = apuesta;
				}
				this.rondaApuesta.add(jugador);
				this.mapa.put(jugador, apuesta);
				
				this.notificarObservadores(Informe.APUESTA_REALIZADA);
				this.jugadorTurnoApuesta = this.jugadoresMesa.poll();
				this.jugadoresMesa.add(this.jugadorTurnoApuesta);
				this.notificarObservadores(Informe.TURNO_APUESTA_JUGADOR);
			} else {
				this.notificarObservadores(Informe.FONDO_INSUFICIENTE);
				this.notificarObservadores(Informe.TURNO_APUESTA_JUGADOR);
			}
		} else {
			this.notificarObservadores(Informe.INFORMAR_NO_TURNO_APUESTA); //Informar quien debe hacer la apuesta, que espere su turno
		}
		
	}

	private void determinarJugadoresConApuestaInsuficiente() {
		for (Jugador j : this.rondaApuesta) {
			if (j.getApuesta() < this.apuestaMayor) {
				this.jugadoresApuestaInsuficiente.add(j);
			}
		}
	}
	
	@Override
	public boolean perteneceJugadorApuestaMenor(String nombre) throws RemoteException{
		for (Jugador j : this.jugadoresApuestaInsuficiente) {
			if (j.getNombre().equals(nombre)) {
				return true;
			}
		}
		return false;
	}

	private boolean comprobarIgualdad() {
		return mapa.values().stream().allMatch(apuesta -> apuesta == this.apuestaMayor);
	}

	private void restarFondosAgregarApuestaJugador(Jugador jugador, int apuesta) {
		jugador.realizarApuesta(apuesta);
	}

	private boolean comprobarFinalVueltaApuesta(Jugador jugador) {
		return mapa.containsKey(jugador);
	}

	private boolean esJugadorTurnoApuesta(Jugador jugador) {
		return jugador.getNombre().equals(this.jugadorTurnoApuesta.getNombre());
	}
	
	public void jugadorFicha(Jugador jugador) throws RemoteException{
		//COMPROBAR QUE JUGADOR ES
		//VERIFICAR SI TIENE FONDOS SUFICIENTES
		//HACER LA APUESTA MAYOR Y MODIFICAR EN LA EL JUGADOR Y EN EL HASHMAP
		//this.notificarObservadores(Informe.JUGADOR_IGUALA_APUESTA);
		//SACAR DE LA COLA DE jugadoresApuestaInsuficiente
	}
	
	public void jugadorPasa(Jugador jugador) throws RemoteException{
		//COMPROBAR QUE JUGADOR ES
		//SACAR DE LA COLA DE APUESTA INSUFICIENTE
		//SACAR DEL HASHMAP
		//RETIRAR DE LA COLA RONDAAPUESTA
	}
	
	public int getApuestaJugador(Jugador jugador) throws RemoteException{
		return this.mapa.get(jugador);
	}

	@Override
	public Jugador getJugadorMano() throws RemoteException {
		return this.jugadorMano;
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
	
	public void sacarJugador(Jugador jugador) throws RemoteException{
		this.jugadoresMesa.remove(jugador);
	}
	
	@Override
	public Jugador getJugadorTurnoApuesta() throws RemoteException {
		return jugadorTurnoApuesta;
	}
	
	
	//------------------------------------------------------------------------------------------
	//RESULTADOS
	
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
	
}
