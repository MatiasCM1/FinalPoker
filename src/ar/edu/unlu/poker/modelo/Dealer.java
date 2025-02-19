package ar.edu.unlu.poker.modelo;

import java.rmi.RemoteException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;

public class Dealer {
	
	private IMesa mesa;
	private Queue<Jugador> rondaApuestaAux;
	
	public Dealer(IMesa mesa) {
		this.mesa = mesa;
	}
	
	public void determinarJugadorMano() throws RemoteException {
		if (this.isPrimeraRonda()) {
			this.seleccionarJugadorRandom();
		}
	}

	private boolean isPrimeraRonda() throws RemoteException {
		return mesa.getPrimeraRonda();
	}

	private void seleccionarJugadorRandom() throws RemoteException {
		List<Jugador> jugadoresMezclados = new LinkedList<Jugador>(mesa.getJugadoresMesa());
		Collections.shuffle(jugadoresMezclados);
		mesa.borrarJugadoresMesa();
		mesa.setearJugadoresMezclados(jugadoresMezclados);

	}

	public Jugador devolverGanador(Queue<Jugador> ronda) throws RemoteException {
		
		this.rondaApuestaAux = new LinkedList<Jugador>();
		this.rondaApuestaAux = ronda;
		
		calcularResultadoJugadores(); // Calcula el resultado de las cartas para cada jugador

		Queue<Jugador> ganadores = new LinkedList<Jugador>(); // Creo una lista donde voy poniendo el ganador o ganadores en caso de empate

		Jugador jugadorGanador = this.rondaApuestaAux.peek(); // Defino como ganador al primer jugador
		ganadores.add(jugadorGanador);

		for (Jugador jugadorActual : this.rondaApuestaAux) {
			
			//Comparo el resultado del jugador actual sobre el ganador
			if (jugadorActualMayorJugadorGanador(jugadorGanador, jugadorActual)) { //Si el actual es mayor, lo pongo como el nuevo ganador

				ganadores.clear();
				ganadores.add(jugadorActual);
				jugadorGanador = jugadorActual;

			} else if (jugadorActualIgualJugadorGanador(jugadorGanador, jugadorActual)) { // si el actual es igual, lo agrego a la lista de ganadores
				
				ganadores.add(jugadorActual);
				
			}

		}

		return determinarGanador(ganadores);
		
	}
	
	
//METODOS DE DEVOLVER GANADOR
//-------------------------------------------------------------------------------------
	private void calcularResultadoJugadores() {
		this.rondaApuestaAux.forEach(t -> {
			try {
				t.calcularValorCartas();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		});
	}
	
	
	private boolean jugadorActualMayorJugadorGanador(Jugador jugadorGanador, Jugador jugadorActual) {
		
		return jugadorActual.getResultadoValoresCartas().ordinal() > jugadorGanador.getResultadoValoresCartas().ordinal();
		
	}
	
	
	private boolean jugadorActualIgualJugadorGanador(Jugador jugadorGanador, Jugador jugadorActual) {
		
		return jugadorActual.getResultadoValoresCartas().ordinal() == jugadorGanador.getResultadoValoresCartas().ordinal();
		
	}
	
	
	
//METODOS DE DETERMINAR GANADOR
//-------------------------------------------------------------------------------------
	
	private Jugador determinarGanador(Queue<Jugador> ganadores) throws RemoteException {

		//Si no hay empate, se devuelve directamente al unico jugador
		if (ganadores.size() == 1) {
			return ganadores.peek();
		}

		Resultado resultadoGanador = ganadores.peek().getResultadoValoresCartas();

		//Si el resultado empatado es carta mayor, color, escalera o escalera a color se busca la carta mayor
		if (resultadoEsCartaMayorColorEscaleraEscaleraColor(resultadoGanador)) { 
			
			return buscarJugadorCartaMayor(ganadores);

		} else 
			//Si el resultado empatado es par, gana el que tiene el par mayor
			if (resultadoGanador.equals(Resultado.PAR)) {

			return devolverGanadorResultadosCartasIguales(ganadores);

		} else if (resultadoGanador.equals(Resultado.DOBLE_PAR)) {

			return devolverGanadorResultadosCartasIguales(ganadores);

		} else if (resultadoGanador.equals(Resultado.FULL)) {

			return devolverGanadorResultadosCartasFull(ganadores);

		} else if (resultadoGanador.equals(Resultado.TRIO)) {

			return devolverGanadorResultadosCartasIguales(ganadores);

		} else if (resultadoGanador.equals(Resultado.POKER)) {

			return devolverGanadorResultadosCartasIguales(ganadores);

		}
		return mesa.getJugadorMano();
	}
	
	
	private boolean resultadoEsCartaMayorColorEscaleraEscaleraColor(Resultado resultadoGanador) {
		return resultadoGanador.equals(Resultado.CARTA_MAYOR) || resultadoGanador.equals(Resultado.COLOR)
				|| resultadoGanador.equals(Resultado.ESCALERA) || resultadoGanador.equals(Resultado.ESCALERA_COLOR);
	}
	
	
	private Jugador buscarJugadorCartaMayor(Queue<Jugador> ganadores) {
		
		Carta cartaMayor = null;
		try {
			
			cartaMayor = ganadores.peek().getCartasOrdenadas().getLast(); //Tomo la carta mayor del primer jugador
			
		} catch (RemoteException e) {
			
			e.printStackTrace();
			
		}
		
		Jugador jugadorCartaMayor = ganadores.poll(); //Guardo el jugador que estableci como el que tiene la carta mayor

		for (Jugador jugadorActual : ganadores) {

			Carta cartaActual = null;
			try {
				
				cartaActual = jugadorActual.getCartasOrdenadas().getLast(); //Tomo la carta mayor del jugador actual
				
			} catch (RemoteException e) {
				
				e.printStackTrace();
				
			}

			if (cartaActual.compareTo(cartaMayor) > 0) { //Si la carta mayor del jugador actual es mayor a la carta mayor, la reemplazo
				
				cartaMayor = cartaActual;
				jugadorCartaMayor = jugadorActual;
				
			}

		}

		return jugadorCartaMayor;

	}
	
	//Cartas con el mismo numero, por ejemplo, par, doble par, trio, poker
	private Jugador devolverGanadorResultadosCartasIguales(Queue<Jugador> ganadores) {
		
		Jugador ganador = ganadores.poll(); //Tomo al primer jugador y lo establezco como ganador

		for (Jugador jugadorActual : ganadores) {
			if (cartasIgualadasMayores(ganador, jugadorActual)) { //Si las cartas igualadas del jugador actual son mayores, entonces pasa a ser el ganador
				ganador = jugadorActual;
			}
		}

		return ganador;

	}
	
	private boolean cartasIgualadasMayores(Jugador ganadorActual, Jugador candidadatoAGanador) {

		ResultadoJugadaJugador ordenadorResultados = new ResultadoJugadaJugador();

		//Paso una lista con las cartas que son iguales de cada jugador, esta lista se ordena de menor a mayor y tomo la ultima, ya que esta seria la mayor
		Carta cartaMayor1 = (ganadorActual.ordenarCartas(ganadorActual.determinarCartasIguales())).getLast();
		Carta cartaMayor2 = (candidadatoAGanador.ordenarCartas(candidadatoAGanador.determinarCartasIguales())).getLast();

		// Devuelve true si la carta (que forma parte del resultado de cartas igualadas) de candidatoAGanador es mayor a la del ganadorActual
		return cartaMayor2.compareTo(cartaMayor1) >= 0;

	}
	
	
	//Dos cartas con el mismo numero, tres cartas con el mismo numero pero distinto de las dos
	private Jugador devolverGanadorResultadosCartasFull(Queue<Jugador> ganadores) {

		Jugador ganador = ganadores.poll(); //Tomo al primer jugador y lo establezco como ganador

		for (Jugador jugadorActual : ganadores) {
			if (cartasIgualadasMayoresFull(ganador, jugadorActual)) { //Si el trio de cartas del full del jugador actual son mayores, entonces pasa a ser el ganador
				ganador = jugadorActual;
			}
		}

		return ganador;
	}
	
	private boolean cartasIgualadasMayoresFull(Jugador ganadorActual, Jugador candidadatoAGanador) {

		//Le paso las cartas de los jugadores y obtiene el trio del full
		Carta cartaMayor1 = obtenerValorTrio(ganadorActual.getCartas());
		Carta cartaMayor2 = obtenerValorTrio(candidadatoAGanador.getCartas());

		// Devuelve true si la carta del resultado de candidatoAGanador es mayor a la del ganadorActual
		return cartaMayor2.compareTo(cartaMayor1) >= 0;
		
	}
	
	public Carta obtenerValorTrio(LinkedList<Carta> cartas) {

		Map<String, Long> conteoCartas = cartas.stream().collect(Collectors.groupingBy(t -> {
			try {
				return t.getValor();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			return null;
		}, Collectors.counting()));

		// Encuentra el valor que aparece 3 veces
		String valorTrio = conteoCartas.entrySet().stream().filter(entry -> entry.getValue() == 3).map(Map.Entry::getKey).findFirst().orElse(null);

		return cartas.stream().filter(carta -> {
			try {
				return carta.getValor().equals(valorTrio);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			return false;
		}).findFirst().orElse(null);

	}

}
