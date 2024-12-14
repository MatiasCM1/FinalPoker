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
	private  Queue<Jugador> rondaApuestaAux = new LinkedList<Jugador>();
	private HashMap<Jugador, LinkedList<Carta>> cartasADescartar = new HashMap<Jugador, LinkedList<Carta>>();
	private  List<Jugador> jugadoresApuestaInsuficiente = new LinkedList<Jugador>();
	private static final HashMap<String, Integer> valorCarta = new HashMap<String, Integer>();
	private HashMap<Jugador, Integer> mapa = new HashMap<Jugador, Integer>();
	private int apuestaMayor;
	private Jugador jugadorMano;
	private Jugador jugadorTurno;
	private Dealer dealer;
	private int pozo;

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
		
		if (this.jugadoresMesa.size() > 7) {
			this.notificarObservadores(Informe.CANT_JUGADORES_EXCEDIDOS);
			return;
		}

			this.jugadoresMesa.forEach(jugador -> jugador.setEnJuego(true));
			this.jugadoresMesa.forEach(jugador -> jugador.setApuesta(0));
			this.jugadoresMesa.forEach(jugador -> jugador.setHaApostado(false));
			this.jugadoresApuestaInsuficiente.clear();
			this.jugadoresMesa.forEach(Jugador::resetearCartas);//HAGO ESTO PARA QUE CADA RONDA TENGAN DIFERENTES CARTAS
			this.inicializarCartasADescartar();
			this.rondaApuesta.clear();
			this.rondaApuestaAux.clear();
			this.mapa.clear();
			this.pozo = 0;
			
			this.seleccionarJugadorRandom();
			this.jugadorMano = this.jugadoresMesa.poll();
			this.jugadorTurno = this.jugadorMano;
			this.jugadoresMesa.add(this.jugadorMano);
			
			this.apuestaMayor = 0;
			
			dealer = new Dealer();
			dealer.setearCartasRonda();
			
			this.notificarObservadores(Informe.JUGADOR_MANO);
			
			dealer.repartirCartasRonda(this.jugadoresMesa);
			
			this.notificarObservadores(Informe.CARTAS_REPARTIDAS);
			
			this.notificarObservadores(Informe.TURNO_APUESTA_JUGADOR);
		
			//this.calcularPozo();
			
	}

	private void calcularPozo() {
		for (Jugador j : this.jugadoresMesa) {
			this.pozo = this.pozo + j.getApuesta();
		}
	}
	
//---------------------------------------------------------------------------------------------------------------------------
//PRIMERA RONDA APUESTAS

	@Override
	public void realizarApuesta(Jugador jugador, int apuesta)  throws RemoteException{		
		if (esJugadorTurno(jugador)) {
			if (jugador.comprobarFondosSuficientes(apuesta)) {
				this.restarFondosAgregarApuestaJugador(this.jugadorTurno, apuesta);
				if (apuesta > this.apuestaMayor) {
					this.apuestaMayor = apuesta;
				}
				
				
				//TENGO QUE HACER ESTO, PQ EL JUGADOR QUE VIENE DE LA VISTA NO TIENE LAS CARTAS, ENTONCES SE LAS TENGO QUE SETEAR
				jugador.setListaCartas(buscarCartasCorrespondeJugador(jugador)); //BUSCAR OTRA MANERA NO ME GUSTA
				
				
				this.rondaApuesta.add(jugador);
				this.mapa.put(jugador, apuesta);
				
				this.notificarObservadores(Informe.APUESTA_REALIZADA);
				this.jugadorTurno = this.jugadoresMesa.poll();
				this.jugadoresMesa.add(this.jugadorTurno);
				
				
				if (this.comprobarFinalVueltaApuesta(this.jugadorTurno)) {
					if (!comprobarIgualdad()) {
						this.igualarApuestas(jugador);
					} else {
						this.jugadorTurno = this.rondaApuesta.poll();
						this.rondaApuestaAux.add(this.jugadorTurno);
						this.rondaApuesta.add(this.jugadorTurno);
						if (this.rondaApuesta.size() == 1) {
							this.notificarObservadores(Informe.RONDA_APUESTAS_TERMINADA);
						} else {
							this.notificarObservadores(Informe.TURNO_DESCARTE); //LLamo al menu de descarte
						}
						
					}
				} else { 
					this.notificarObservadores(Informe.TURNO_APUESTA_JUGADOR);
				}
			} else {
				this.notificarObservadores(Informe.FONDO_INSUFICIENTE);
				this.notificarObservadores(Informe.TURNO_APUESTA_JUGADOR);
			}
		} else {
			this.notificarObservadores(Informe.INFORMAR_NO_TURNO); //Informar quien debe hacer la apuesta, que espere su turno
		}
		
	}
	

	private LinkedList<Carta> buscarCartasCorrespondeJugador(Jugador jugador) {
		for (Jugador j : this.jugadoresMesa) {
			if (jugador.equals(j)) {
				return j.getCartas();
			}
		}
		return null;
	}

	public void igualarApuestas(Jugador jugador) throws RemoteException {
		this.determinarJugadoresConApuestaInsuficiente();
		this.notificarObservadores(Informe.APUESTAS_DESIGUALES);
	}

	private void devolverResultados() throws RemoteException {
		// TODO Auto-generated method stub
		this.notificarObservadores(Informe.DEVOLVER_GANADOR);
	}

	private void determinarJugadoresConApuestaInsuficiente() {
		mapa.entrySet().forEach(entry -> {if (entry.getValue() < this.apuestaMayor) { 
			this.jugadoresApuestaInsuficiente.add(entry.getKey());
		}});
	}
	
	@Override
	public boolean perteneceJugadorApuestaMenor(Jugador jugador) throws RemoteException{
		return this.jugadoresApuestaInsuficiente.contains(jugador);
	}

	private boolean comprobarIgualdad(){
		return mapa.values().stream().allMatch(apuesta -> apuesta == this.apuestaMayor);
	}

	private void restarFondosAgregarApuestaJugador(Jugador jugador, int apuesta) {
		jugador.realizarApuesta(apuesta);
	}

	private boolean comprobarFinalVueltaApuesta(Jugador jugador) {
		return mapa.containsKey(jugador);
	}

	private boolean esJugadorTurno(Jugador jugador) {
		return jugador.getNombre().equals(this.jugadorTurno.getNombre());
	}
	
	public void jugadorFichaPostEnvite(Jugador jugador) throws RemoteException{
		if (jugador.comprobarFondosSuficientes(this.apuestaMayor)) { //VERIFICAR SI TIENE FONDOS SUFICIENTES
			jugador.realizarApuesta(this.apuestaMayor);//Actualizo la apuesta del jugador
			mapa.put(jugador, this.apuestaMayor);//Actualizo la apuesta del jugador en el hash map
			this.jugadoresApuestaInsuficiente.remove(jugador);
			this.notificarObservadores(Informe.JUGADOR_IGUALA_APUESTA);
			if (this.jugadoresApuestaInsuficiente.isEmpty()) {
				if (this.rondaApuesta.size() == 1) {
					this.notificarObservadores(Informe.RONDA_APUESTAS_TERMINADA);
				} else {
					this.notificarObservadores(Informe.TURNO_DESCARTE); //LLamo al menu de descarte
				}
			}
		}
	}
	
	public void jugadorPasaPostEnvite(Jugador jugador) throws RemoteException{
		this.jugadoresApuestaInsuficiente.remove(jugador);
		jugador.pasar();
		this.mapa.remove(jugador);
		this.rondaApuestaAux.remove(jugador);
		
		//ESTO NO SE SI ESTA BIEN
		this.rondaApuesta.remove(jugador);
		
		this.notificarObservadores(Informe.JUGADOR_PASA_APUESTA);	
		if (this.jugadoresApuestaInsuficiente.isEmpty()) {
			if (this.rondaApuesta.size() == 1) {
				this.notificarObservadores(Informe.RONDA_APUESTAS_TERMINADA);
			} else {
				this.notificarObservadores(Informe.TURNO_DESCARTE); //LLamo al menu de descarte
			} 
		}
	}
	
	@Override
	public void jugadorPasa(Jugador jugador) throws RemoteException {
		jugador.pasar();
		this.notificarObservadores(Informe.JUGADOR_PASA_APUESTA);
		this.rondaApuesta.remove(jugador);
		this.mapa.remove(jugador);
		this.jugadorTurno = this.jugadoresMesa.poll();
		this.jugadoresMesa.add(this.jugadorTurno);
		
		
		if (this.comprobarFinalVueltaApuesta(this.jugadorTurno)) {
			if (!comprobarIgualdad()) {
				this.igualarApuestas(jugador);
			} else {
				if (this.rondaApuesta.size() == 1) {
					this.notificarObservadores(Informe.RONDA_APUESTAS_TERMINADA);
				} else {
					this.notificarObservadores(Informe.TURNO_DESCARTE); //LLamo al menu de descarte
				}
			}
		} else { 
			this.notificarObservadores(Informe.TURNO_APUESTA_JUGADOR);
		}	
	}
	
	
//-----------------------------------------------------------------------------------------------------------------------
//DESCARTES
	
	@Override
	public void realizarElDescarte(Jugador jugador) throws RemoteException{
		if (esJugadorTurno(jugador)) {
			
				
				
				//TENGO QUE HACER ESTO, PQ EL JUGADOR QUE VIENE DE LA VISTA NO TIENE LAS CARTAS, ENTONCES SE LAS TENGO QUE SETEAR
				jugador.setListaCartas(buscarCartasCorrespondeJugador(jugador)); //BUSCAR OTRA MANERA NO ME GUSTA
				
				this.descartarCartas(jugador);
				
				this.rondaApuestaAux.add(jugador);
				
				this.jugadorTurno = this.rondaApuesta.poll();
				this.rondaApuesta.add(this.jugadorTurno);
				
				
				if (this.comprobarFinalVueltaDescartes(this.jugadorTurno)) {//EN CASO DE QUE SEA EL FINAL SIGNIFICA QUE LA RONDA DE 
					
					//RONDA DE DESCARTE FINALIZADA MOSTRAR NUEVAS CARTAS E IR A LA SIGUIENTE APUESTA
					if (continuaEnJuego(jugador)) {
						this.notificarObservadores(Informe.CARTAS_REPARTIDAS);
					}
					
					//RESETEO LA rondaApuestasAux para utilizarla para la segunda ronda de apuestas
					this.rondaApuestaAux.clear();
					
					//RESETEO LA jugadoresApuestaInsuficiente para utilizarla para la segunda ronda de apuestas
					this.jugadoresApuestaInsuficiente.clear();
					
					//DEFINO EL PRIMER JUGADOR TURNO PARA LA SEGUNDA RONDA APUESTAS
					
					//this.jugadorTurno = this.rondaApuesta.poll();
					//this.rondaApuesta.add(this.jugadorTurno);
					
					if (this.rondaApuesta.size() == 1) {
						this.notificarObservadores(Informe.RONDA_APUESTAS_TERMINADA);
					} else {
						this.notificarObservadores(Informe.SEGUNDA_RONDA_APUESTAS);
					}
				} else { 
					this.notificarObservadores(Informe.TURNO_DESCARTE);//PASAR AL SIGUIENTE TURNO
				}
		} else {
			this.notificarObservadores(Informe.INFORMAR_NO_TURNO); //Informar quien debe hacer la apuesta, que espere su turno
		}
	}

	private boolean continuaEnJuego(Jugador jugador) {
		return this.rondaApuesta.contains(jugador);
	}

	private void descartarCartas(Jugador jugador) throws RemoteException{
		//DESCARTAR
		//TENGO QUE BUSCAR LAS CARTAS DEL JUGADOR DE jugadoresMesa Y BORRARLAS
		for (Jugador j : this.rondaApuesta) {
			if (j.equals(jugador)) {
				j.getCartas().removeAll(this.cartasADescartar.get(jugador));
			}
		}
		
		//REPONER CON NUEVAS CARTAS
		this.dealer.repartirCartasPostDescarte(this.rondaApuesta);
		
	}
	

	private void inicializarCartasADescartar() {
		this.cartasADescartar.clear();
		LinkedList<Carta> listaVacia = new LinkedList<Carta>();
		for (Jugador j : this.jugadoresMesa) {
			this.cartasADescartar.put(j, listaVacia);
		}
	}
	
	public void agregarCartasADescartar(int posicionCarta, Jugador jugador) throws RemoteException{
		LinkedList<Carta> cartas = this.cartasADescartar.get(jugador); //OBTENGO LAS CARTAS DESCARTADAS DEL JUGADOR DEL HASHMAP, SI NO TIENE CARTAS DEVUEVE UNA LISTA VACIA
		if (!isCartaDescartada(cartas, posicionCarta, jugador)) {//DEVUELVE TRUE SI ES UNA CARTA DESCARTADA Y FALSE SI NO LO ES
			cartas.add(buscarJugadorCoincidaCartas(jugador).get(posicionCarta));//AGREGO LA CARTA A DESCARTAR A LA LISTA
			this.cartasADescartar.put(jugador, cartas);//CARGO LA LISTA AL HASHMAP
			this.notificarObservadores(Informe.CARTA_DESCARTADA);
		} else {
			this.notificarObservadores(Informe.CARTA_YA_HABIA_SIDO_DESCARTADA);
		}
	}

	private boolean isCartaDescartada(LinkedList<Carta> cartas, int posicionCarta, Jugador jugador) {
		for (Carta c : cartas) {
			//COMPARO PARA VER SI EN LA LISTA DE LAS CARTAS A DESCARTAR EXISTE UNA CARTA IGUAL A LA QUE QUIERO AGREGAR
			if (this.igualdadDeCartas(c, buscarJugadorCoincidaCartas(jugador).get(posicionCarta))){
				return true;
			}
		}
		return false;
	}
	
	private boolean igualdadDeCartas(Carta c1, Carta c2){
		boolean flag = false;
		try {
			flag = (c1.getPalo().equals(c2.getPalo()) && c1.getValor().equals(c2.getValor()));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	private LinkedList<Carta> buscarJugadorCoincidaCartas(Jugador jugador) {
		for (Jugador j : this.jugadoresMesa) {
			if (j.equals(jugador)){
				return j.getCartas();
			}
		}
		return null;
	}
	
	private boolean comprobarFinalVueltaDescartes(Jugador jugador) {
		return this.rondaApuestaAux.contains(jugador);
	}
	
//-----------------------------------------------------------------------------------------------------------------------
//SEGUNDA RONDA DE APUESTAS
	
	@Override
	public void realizarSegundaRondaApuesta(Jugador jugador, int apuesta) throws RemoteException {
		
		if (this.rondaApuesta.size() == 1) {
			this.notificarObservadores(Informe.RONDA_APUESTAS_TERMINADA);
		}
		
		if (esJugadorTurno(jugador)) {
			if (jugador.comprobarFondosSuficientes(apuesta)) {
				this.restarFondosAgregarApuestaJugador(this.jugadorTurno, apuesta);
				
				
				if (apuesta > this.apuestaMayor) {
					this.apuestaMayor = apuesta;
				}
				
				
				//TENGO QUE HACER ESTO, PQ EL JUGADOR QUE VIENE DE LA VISTA NO TIENE LAS CARTAS, ENTONCES SE LAS TENGO QUE SETEAR
				jugador.setListaCartas(buscarCartasCorrespondeJugador(jugador)); //BUSCAR OTRA MANERA NO ME GUSTA
				
				
				this.rondaApuestaAux.add(jugador);
				
				
				this.mapa.put(jugador, this.jugadorTurno.getApuesta());
				
				this.notificarObservadores(Informe.APUESTA_REALIZADA);
				
				this.jugadorTurno = this.rondaApuesta.poll();
				this.rondaApuesta.add(this.jugadorTurno);
				
				
				if (this.comprobarFinalVueltaSegundaRondaApuesta(this.jugadorTurno)) {
					if (!comprobarIgualdad()) {
						this.igualarApuestasSegundaRonda(jugador);
					} else {
						this.jugadorTurno = this.rondaApuesta.poll();
						//this.rondaApuestaAux.add(this.jugadorTurno);//CREO QUE ESTO NO VA EN ESTE MEDOTO DE REALIZAR APUESTA SEGUNDA RONDA
						this.rondaApuesta.add(this.jugadorTurno);
						this.notificarObservadores(Informe.RONDA_APUESTAS_TERMINADA);
					}
				} else { 
					this.notificarObservadores(Informe.SEGUNDA_RONDA_APUESTAS);
				}
			} else {
				this.notificarObservadores(Informe.FONDO_INSUFICIENTE);
				this.notificarObservadores(Informe.SEGUNDA_RONDA_APUESTAS);
			}
		} else {
			this.notificarObservadores(Informe.INFORMAR_NO_TURNO); //Informar quien debe hacer la apuesta, que espere su turno
		}
	}
	
	@Override
	public void jugadorPasaSegundaRonda(Jugador jugador) throws RemoteException {
		jugador.pasar();
		this.notificarObservadores(Informe.JUGADOR_PASA_APUESTA);
		
		this.rondaApuestaAux.remove(jugador);
		this.mapa.remove(jugador);
		
		this.jugadorTurno = this.rondaApuesta.poll();
		this.rondaApuesta.add(this.jugadorTurno);
		
		if (this.comprobarFinalVueltaSegundaRondaApuesta(this.jugadorTurno)) {
			if (!comprobarIgualdad()) {
				this.igualarApuestasSegundaRonda(jugador);
			} else {
				this.notificarObservadores(Informe.RONDA_APUESTAS_TERMINADA);
			}
		} else {
			this.notificarObservadores(Informe.SEGUNDA_RONDA_APUESTAS);
		}
	}
	
	private void igualarApuestasSegundaRonda(Jugador jugador) throws RemoteException {
		this.determinarJugadoresConApuestaInsuficiente();
		this.notificarObservadores(Informe.APUESTAS_DESIGUALES_SEGUNDA_RONDA);
	}
	
	private boolean comprobarFinalVueltaSegundaRondaApuesta(Jugador jugador) {
		return this.rondaApuestaAux.contains(jugador);
	}
	
	public void jugadorFichaPostEnviteSegundaRonda(Jugador jugador) throws RemoteException{
		if (jugador.comprobarFondosSuficientes(this.apuestaMayor)) { //VERIFICAR SI TIENE FONDOS SUFICIENTES
			jugador.realizarApuesta(this.apuestaMayor);//Actualizo la apuesta del jugador
			mapa.put(jugador, this.apuestaMayor);//Actualizo la apuesta del jugador en el hash map
			this.jugadoresApuestaInsuficiente.remove(jugador);
			this.notificarObservadores(Informe.JUGADOR_IGUALA_APUESTA);
			if (this.jugadoresApuestaInsuficiente.isEmpty()) {
				this.notificarObservadores(Informe.RONDA_APUESTAS_TERMINADA);
			}
		}
	}
	
	public void jugadorPasaPostEnviteSegundaRonda(Jugador jugador) throws RemoteException{
		this.jugadoresApuestaInsuficiente.remove(jugador);
		jugador.pasar();
		this.mapa.remove(jugador);
		this.rondaApuestaAux.remove(jugador);
		this.notificarObservadores(Informe.JUGADOR_PASA_APUESTA);	
		if (this.jugadoresApuestaInsuficiente.isEmpty()) {
			this.notificarObservadores(Informe.RONDA_APUESTAS_TERMINADA);
		}
	}
	
//-----------------------------------------------------------------------------------------------------------------------
	
	@Override
	public void mirarSiDevolverResultados() throws RemoteException {
		if (this.comprobarIgualdad()) {
			this.devolverResultados();
		}
	}	
	
	public int getApuestaJugador(Jugador jugador) throws RemoteException{
		//Jugador juga = mapa.keySet().stream().filter(j -> j.equals(jugador)).findFirst().orElse(null);
		return this.mapa.get(jugador);
	}

	@Override
	public Jugador getJugadorMano() throws RemoteException {
		return this.jugadorMano;
	}
	
	public HashMap<Jugador, LinkedList<Carta>> getCartasADescartar(){
		return cartasADescartar;
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
	public Jugador getJugadorTurno() throws RemoteException {
		return jugadorTurno;
	}
	
	
	//------------------------------------------------------------------------------------------
	//RESULTADOS
	
	private void calcularResultadoJugadores() {
		this.rondaApuestaAux.forEach(t -> {
			try {
				t.calcularValorCartas();
			} catch (RemoteException e) {
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
	    Jugador jugadorGanador = this.rondaApuestaAux.peek();
	    ganadores.add(jugadorGanador);  // A�adir el primer jugador como ganador temporal

	    // Iterar sobre el resto de los jugadores en la cola
	    for (Jugador jugadorActual : this.rondaApuestaAux) {
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
	
	@Override
	public List<Jugador> getRondaApuesta() throws RemoteException{
		return List.copyOf(this.rondaApuesta);
	}

	@Override
	public void prepararColaParaSiguienteJugadorMano() {
		Jugador jugadorAux = this.jugadoresMesa.poll();
		this.jugadoresMesa.add(jugadorAux);
	}
	
}
