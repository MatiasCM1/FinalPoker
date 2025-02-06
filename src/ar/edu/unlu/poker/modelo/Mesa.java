package ar.edu.unlu.poker.modelo;

import java.rmi.RemoteException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

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
	private Mazo mazo;
	private int pozo;
	private boolean primeraRonda;
	private Jugador jugadorQuePaso = new Jugador("");

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
			this.notificarObservadores(Informe.JUGADOR_NUEVO_AGREGADO);
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
		
			this.jugadorQuePaso = null;

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
			
			this.determinarJugadorMano();
			
			this.jugadorMano = this.jugadoresMesa.poll();
			this.jugadorTurno = this.jugadorMano;
			this.jugadoresMesa.add(this.jugadorMano);
			
			this.apuestaMayor = 0;
			
			mazo = new Mazo();
			mazo.setearCartasRonda();
			
			this.notificarObservadores(Informe.ESTABLECER_NOMBRE_VENTANA_JUGADOR);
			
			this.notificarObservadores(Informe.JUGADOR_MANO);
			
			mazo.repartirCartasRonda(this.jugadoresMesa);
			
			this.notificarObservadores(Informe.CARTAS_REPARTIDAS);
			
			this.notificarObservadores(Informe.TURNO_APUESTA_JUGADOR);
			
	}

	@Override
	public void darFondosGanador(Jugador jugador) throws RemoteException{
		for (Jugador j : this.jugadoresMesa) {
			if (j.equals(jugador)) {
				j.agregarFondos(this.pozo);
			}
		}
	}

//---------------------------------------------------------------------------------------------------------------------------
//PRIMERA RONDA APUESTAS

	@Override
	public void realizarApuesta(Jugador jugador, int apuesta)  throws RemoteException{		
		if (esJugadorTurno(jugador)) {
			if (comprobarFondos(jugador, apuesta)) {
				this.restarFondosAgregarApuestaJugador(this.jugadorTurno, apuesta);
				if (apuesta > this.apuestaMayor) {
					this.apuestaMayor = apuesta;
				}
				
				//AGREGO EL VALOR DE LA APUESTA AL POZO
				this.agregarAlPozo(apuesta);
				
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
	

	private boolean comprobarFondos(Jugador jugador, int apuesta) {
		for (Jugador j : this.jugadoresMesa) {
			if (j.equals(jugador)) {
				return j.comprobarFondosSuficientes(apuesta);
			}
		}
		return false;
	} 

	public int getPozo() {
		return pozo;
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
	
	private int buscarApuestaMayorEnElMapa() {
		int apuestaMayor = 0;
		int apuesta;
		for (Entry<Jugador, Integer> entry : this.mapa.entrySet()) {
			apuesta = entry.getValue();
			if (apuesta > apuestaMayor) {
				apuestaMayor = apuesta;
			}
		}
		return apuestaMayor;
	}

	private void determinarJugadoresConApuestaInsuficiente() {
		mapa.entrySet().forEach(entry -> {if (entry.getValue() < this.buscarApuestaMayorEnElMapa()) { 
			this.jugadoresApuestaInsuficiente.add(entry.getKey());
		}});
	}

	
	@Override
	public boolean perteneceJugadorApuestaMenor(Jugador jugador) throws RemoteException{
		return this.jugadoresApuestaInsuficiente.contains(jugador);
	}
	
	private boolean comprobarIgualdad(){
		int primerApuesta = mapa.values().iterator().next();
		return mapa.values().stream().allMatch(apuesta -> apuesta == primerApuesta);
	}

	private void restarFondosAgregarApuestaJugador(Jugador jugador, int apuesta) {
		for (Jugador j : this.jugadoresMesa) {
			if (j.equals(jugador)) {
				j.realizarApuesta(apuesta);
				this.mapa.put(j, (j.getApuesta()));
				
				if (apuesta > this.apuestaMayor) {
					this.apuestaMayor = apuesta;
				}
			}
		}
		
	}

	private boolean comprobarFinalVueltaApuesta(Jugador jugador) {
		return mapa.containsKey(jugador);
	}

	private boolean esJugadorTurno(Jugador jugador) {
		return jugador.getNombre().equals(this.jugadorTurno.getNombre());
	}
	
	public void jugadorFichaPostEnvite(Jugador jugador) throws RemoteException{
		if (comprobarFondos(jugador, this.apuestaMayor)) { //VERIFICAR SI TIENE FONDOS SUFICIENTES
			
			int apuestaParaFichar = this.calcularCuantoFaltaParaFichar(jugador);
			
			this.restarFondosAgregarApuestaJugador(jugador, /*this.apuestaMayor*/apuestaParaFichar);//Actualizo la apuesta del jugador
			
			this.agregarAlPozo(apuestaParaFichar);
			
			this.jugadoresApuestaInsuficiente.remove(jugador);
			
			this.notificarObservadores(Informe.JUGADOR_IGUALA_APUESTA);
			
			if (this.jugadoresApuestaInsuficiente.isEmpty()) {
				if (this.rondaApuesta.size() == 1) {
					this.notificarObservadores(Informe.RONDA_APUESTAS_TERMINADA);
				} else {
					this.rondaApuestaAux.clear();
					this.jugadorTurno = this.rondaApuesta.poll();
					this.rondaApuesta.add(this.jugadorTurno);
					this.notificarObservadores(Informe.TURNO_DESCARTE); //LLamo al menu de descarte
				}
			}
		} else {
			this.notificarObservadores(Informe.APUESTAS_DESIGUALES);
			this.notificarObservadores(Informe.FONDO_INSUFICIENTE);
		}
	}
	
	public void jugadorPasaPostEnvite(Jugador jugador) throws RemoteException{
		this.jugadoresApuestaInsuficiente.remove(jugador);
		jugador.pasar();
		this.mapa.remove(jugador);
		this.rondaApuestaAux.remove(jugador);
		
		//ESTO NO SE SI ESTA BIEN
		this.rondaApuesta.remove(jugador);
		
		this.jugadorQuePaso = jugador;
		
		this.notificarObservadores(Informe.JUGADOR_PASA_APUESTA);	
		if (this.jugadoresApuestaInsuficiente.isEmpty()) {
			if (this.rondaApuesta.size() == 1) {
				this.notificarObservadores(Informe.RONDA_APUESTAS_TERMINADA);
			} else {
				this.rondaApuestaAux.clear();
				this.jugadorTurno = this.rondaApuesta.poll();
				this.rondaApuesta.add(this.jugadorTurno);
				this.notificarObservadores(Informe.TURNO_DESCARTE); //LLamo al menu de descarte
			} 
		}
	}
	
	@Override
	public void jugadorPasa(Jugador jugador) throws RemoteException {
		jugador.pasar();
		
		this.jugadorQuePaso = jugador;
		
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
					this.rondaApuestaAux.clear();
					this.jugadorTurno = this.rondaApuesta.poll();
					this.rondaApuesta.add(this.jugadorTurno);
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
					
					//ESTABLEZCO LA APUESTAMAYOR EN 0 PARA QUE CUANDO FICHEN EN LA SEGFUNDA RONDA DE APUESTAS, NO TENGAN EN CUENTA ESTE VALOR
					this.apuestaMayor = 0;
					
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
		this.mazo.repartirCartasPostDescarte(this.rondaApuesta);
		
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
		
		/*if (this.rondaApuesta.size() == 1) {
			this.notificarObservadores(Informe.RONDA_APUESTAS_TERMINADA);
		}*/
		
		if (esJugadorTurno(jugador)) {
			if (comprobarFondos(jugador, apuesta)) {
				
				this.restarFondosAgregarApuestaJugador(this.jugadorTurno, apuesta);
				
				this.agregarAlPozo(apuesta);
				
				//TENGO QUE HACER ESTO, PQ EL JUGADOR QUE VIENE DE LA VISTA NO TIENE LAS CARTAS, ENTONCES SE LAS TENGO QUE SETEAR
				jugador.setListaCartas(buscarCartasCorrespondeJugador(jugador)); //BUSCAR OTRA MANERA NO ME GUSTA
				
				
				this.rondaApuestaAux.add(jugador);
				
				//this.mapa.put(jugador, this.jugadorTurno.getApuesta());
				
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
						
						this.notificarObservadores(Informe.RONDA_APUESTAS_TERMINADA_SEGUNDA_RONDA);
					}
				} else { 
					this.notificarObservadores(Informe.SEGUNDA_RONDA_APUESTAS);
				}
			} else {
				this.notificarObservadores(Informe.FONDO_INSUFICIENTE_SEGUNDA_RONDA);
				this.notificarObservadores(Informe.SEGUNDA_RONDA_APUESTAS);
			}
		} else {
			this.notificarObservadores(Informe.INFORMAR_NO_TURNO); //Informar quien debe hacer la apuesta, que espere su turno
		}
	}
	
	@Override
	public void jugadorPasaSegundaRonda(Jugador jugador) throws RemoteException {
		jugador.pasar();
		this.jugadorQuePaso = jugador;
		this.notificarObservadores(Informe.JUGADOR_PASA_APUESTA);
		
		this.rondaApuestaAux.remove(jugador);
		this.rondaApuesta.remove(jugador);
		this.mapa.remove(jugador);
		
		this.jugadorTurno = this.rondaApuesta.poll();
		this.rondaApuesta.add(this.jugadorTurno);
		
		if (this.comprobarFinalVueltaSegundaRondaApuesta(this.jugadorTurno)) {
			if (!comprobarIgualdad()) {
				this.igualarApuestasSegundaRonda(jugador);
			} else {
				this.notificarObservadores(Informe.RONDA_APUESTAS_TERMINADA_SEGUNDA_RONDA);
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
	
	@Override
	public void jugadorFichaPostEnviteSegundaRonda(Jugador jugador) throws RemoteException {
		if (comprobarFondos(jugador, this.apuestaMayor)) { //VERIFICAR SI TIENE FONDOS SUFICIENTES
			
			int apuestaParaFichar = this.calcularCuantoFaltaParaFichar(jugador);
			
			this.restarFondosAgregarApuestaJugador(jugador, apuestaParaFichar);//Actualizo la apuesta del jugador
			
			this.agregarAlPozo(apuestaParaFichar);
			
			this.jugadoresApuestaInsuficiente.remove(jugador);
			
			this.notificarObservadores(Informe.JUGADOR_IGUALA_APUESTA);
			
			
			if (this.jugadoresApuestaInsuficiente.isEmpty()) {
				this.notificarObservadores(Informe.RONDA_APUESTAS_TERMINADA_SEGUNDA_RONDA);
			}
		} else {
			this.notificarObservadores(Informe.FONDO_INSUFICIENTE);
			this.notificarObservadores(Informe.APUESTAS_DESIGUALES);
		}
	}

	
	private int buscarApuestaMayor() {
		int apuestaMayorMapa = mapa.entrySet().iterator().next().getValue(); //Agarro un jugador cualquiera del mapa y tomo su apuesta y la establezmo como mayor
		for (Entry<Jugador, Integer> entry : mapa.entrySet()) {
			if (entry.getValue() > apuestaMayorMapa) {
				apuestaMayorMapa = entry.getValue();
			}
		}
		return apuestaMayorMapa;
	}

	private int calcularCuantoFaltaParaFichar(Jugador jugador) { //HAGO UNA RESTA PARA SABER CUANTO LE FALTA AL JUGADOR PARA IGUALAR LA APUESTA MAXIMA
		return Math.abs(this.buscarApuestaMayor() - this.mapa.get(jugador));
	}

	@Override
	public void jugadorPasaPostEnviteSegundaRonda(Jugador jugador) throws RemoteException {
		this.jugadoresApuestaInsuficiente.remove(jugador);
		jugador.pasar();
		this.mapa.remove(jugador);
		this.rondaApuestaAux.remove(jugador);
		this.jugadorQuePaso = jugador;
		this.notificarObservadores(Informe.JUGADOR_PASA_APUESTA);	
		if (this.jugadoresApuestaInsuficiente.isEmpty()) {
			this.notificarObservadores(Informe.RONDA_APUESTAS_TERMINADA_SEGUNDA_RONDA);
		}
	}
	
//-----------------------------------------------------------------------------------------------------------------------
	
	@Override
	public void mirarSiDevolverResultados() throws RemoteException {
		if (this.comprobarIgualdad()) {
			this.devolverResultados();
		}
	}	
	
	@Override
	public void agregarNuevosFondos(Jugador jugador, int fondoAgregar) throws RemoteException {
		for (Jugador j : this.jugadoresMesa) {
			if (j.equals(jugador)) {
				j.agregarFondos(fondoAgregar);
			}
		}
		this.notificarObservadores(Informe.SE_AGREGAN_FONDOS);
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
	
	private void determinarJugadorMano() throws RemoteException {
		if (this.isPrimeraRonda()) {
			this.seleccionarJugadorRandom();
		} 
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
	
	/*@Override
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
	            // Si tienen el mismo resultado (CARTA_MAYOR, ESCALERA, COLOR, ESCALERA_COLOR), comparar la carta mayor
	        	Jugador jugadorConCartaMayor = buscarCartaMayor(jugadorGanador, jugadorActual);
	            if (jugadorConCartaMayor.equals(jugadorActual)) {
	                ganadores.clear();  // Limpiar si el nuevo jugador tiene la carta mayor
	                ganadores.add(jugadorActual);  // Agregar el nuevo ganador con la carta mayor
	                jugadorGanador = jugadorActual;  // Actualizar el jugador ganador
	            }
	        }
	    }
	    
	    return List.copyOf(ganadores); // Retornar la cola de ganadores
	}*/
	
	@Override
	public Jugador devolverGanador() throws RemoteException {
		
		calcularResultadoJugadores(); //Calcula el resultado de las cartas para cada jugador
		
		Queue<Jugador> ganadores = new LinkedList<Jugador>(); //Creo una lista donde voy poniendo el ganador o ganadores en caso de empate
		
		Jugador jugadorGanador = this.rondaApuestaAux.peek(); //Defino como ganador al primer jugador
		ganadores.add(jugadorGanador); //Defino como ganador al primer jugador
		
		for (Jugador jugadorActual : this.rondaApuestaAux) {
			
			if (jugadorActualMayorJugadorGanador(jugadorGanador, jugadorActual)) { //Comparo el resultado del jugador actual sobre el ganador, si el actual es mayor, lo pongo como el nuevo ganador
				
				ganadores.clear();
				ganadores.add(jugadorActual);
				jugadorGanador = jugadorActual;
			
			} else if (jugadorActualIgualJugadorGanador(jugadorGanador, jugadorActual)) { //Comparo resultado del jugador actal sobre el gnador, si el actual es igual, lo agrego a la lista de ganadores
				ganadores.add(jugadorActual);
			}
			
		}
		
		return determinarGanador(ganadores);
	}

	private Jugador determinarGanador(Queue<Jugador> ganadores) throws RemoteException {
		
		if (ganadores.size() == 1) {
			return ganadores.peek();
		}
		
		Resultado resultadoGanador = ganadores.peek().getResultadoValoresCartas();
		
		if (ResultadoEsCartaMayorColorEscaleraEscaleraColor(resultadoGanador)) { //Si el resultado es alguno de los mencionados el ganador seria el que tenga la carta mayor
			
			try {
				return buscarJugadorCartaMayor(ganadores);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} else if (resultadoGanador.equals(Resultado.PAR)) {
			
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
		return jugadorMano;
	}


	private boolean ResultadoEsCartaMayorColorEscaleraEscaleraColor(Resultado resultadoGanador) {
		return resultadoGanador.equals(Resultado.CARTA_MAYOR) || resultadoGanador.equals(Resultado.COLOR) || resultadoGanador.equals(Resultado.ESCALERA) || resultadoGanador.equals(Resultado.ESCALERA_COLOR);
	}

	private boolean jugadorActualIgualJugadorGanador(Jugador jugadorGanador, Jugador jugadorActual) {
		return jugadorActual.getResultadoValoresCartas().ordinal() == jugadorGanador.getResultadoValoresCartas().ordinal();
	}

	private boolean jugadorActualMayorJugadorGanador(Jugador jugadorGanador, Jugador jugadorActual) {
		return jugadorActual.getResultadoValoresCartas().ordinal() > jugadorGanador.getResultadoValoresCartas().ordinal();
	}
	
	
	/*private Jugador buscarCartaMayor(Jugador jugador1, Jugador jugador2) throws RemoteException {
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
	}*/
	
	private Jugador buscarJugadorCartaMayor(Queue<Jugador> ganadores) throws RemoteException {
		Carta cartaMayor = ganadores.peek().getCartasOrdenadas().getLast(); //Tomo la carta mayor del primer jugador;
		Jugador jugadorCartaMayor = ganadores.poll(); //Guardo el jugador que estableci como el que tiene la carta mayor
		
		
		for (Jugador jugadorActual : ganadores) {
			
			Carta cartaActual = jugadorActual.getCartasOrdenadas().getLast(); //Tomo la carta mayor del jugadorActual
			
			if (cartaActual.compareTo(cartaMayor) > 0) { //Comparo la carta acutal es mayor que la mayor
				cartaMayor = cartaActual;
				jugadorCartaMayor = jugadorActual;
			}
			
		}
		
		return jugadorCartaMayor;
		
	}
	
	private Jugador devolverGanadorResultadosCartasIguales(Queue<Jugador> ganadores) {
		Jugador ganador = ganadores.poll();
		
		for (Jugador jugadorActual : ganadores) {
			if (cartasIgualadasMayores(ganador, jugadorActual)) {
				ganador = jugadorActual;
			}
		}
		
		return ganador;
		
	}
	
	
	private boolean cartasIgualadasMayores(Jugador ganadorActual, Jugador candidadatoAGanador) {
		
		ResultadoJugadaJugador ordenadorResultados = new ResultadoJugadaJugador();
		
		//le paso un lista de las cartas que son iguales, esta lista se ordena de menor a mayor y tomo la ultima, ya que esta seria la mayor
		Carta cartaMayor1 = (ordenadorResultados.ordenarCartas(ganadorActual.determinarCartasIguales())).getLast();
		Carta cartaMayor2 = (ordenadorResultados.ordenarCartas(candidadatoAGanador.determinarCartasIguales())).getLast();
		
		//Devuelve true si la carta del resultado de candidatoAGanador es mayor a la del ganadorActual
		return cartaMayor2.compareTo(cartaMayor1) >= 0;
		
	}
	
	private Jugador devolverGanadorResultadosCartasFull(Queue<Jugador> ganadores) {
		
		Jugador ganador = ganadores.poll();
		
		for (Jugador jugadorActual : ganadores) {
			if (cartasIgualadasMayoresFull(ganador, jugadorActual)) {
				ganador = jugadorActual;
			}
		}
		
		return ganador;
	}

	private boolean cartasIgualadasMayoresFull(Jugador ganadorActual, Jugador candidadatoAGanador) {
		
		//le paso un lista de las cartas que son iguales y tomo el valor del trio
		Carta cartaMayor1 = obtenerValorTrio(ganadorActual.getCartas());
		Carta cartaMayor2 = obtenerValorTrio(candidadatoAGanador.getCartas());
		
		//Devuelve true si la carta del resultado de candidatoAGanador es mayor a la del ganadorActual
		return cartaMayor2.compareTo(cartaMayor1) >= 0;
	}
	
	public Carta obtenerValorTrio(LinkedList<Carta> cartas) {
		
		 Map<String, Long> conteoCartas = cartas.stream().collect(Collectors.groupingBy(t -> {
			try {
				return t.getValor();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}).findFirst().orElse(null);

	}


//-------------------------------------------------------------------------------------------------------------
	
	@Override
	public List<Jugador> getRondaApuesta() throws RemoteException{
		return List.copyOf(this.rondaApuesta);
	}
	
	@Override
	public List<Jugador> getRondaApuestaAux() throws RemoteException{
		return List.copyOf(this.rondaApuestaAux);
	}
	
	private void agregarAlPozo(int apuesta) {
		this.pozo += apuesta;
	}

	@Override
	public boolean isPrimeraRonda() throws RemoteException{
		return primeraRonda;
	}

	@Override
	public void setPrimeraRonda(boolean primeraRonda) throws RemoteException{
		this.primeraRonda = primeraRonda;
	}

	@Override
	public void removerJugadores(Jugador jugador) throws RemoteException{
		this.jugadoresMesa.remove(jugador);
		this.notificarObservadores(Informe.JUGADOR_SE_RETIRA);
	}

	@Override
	public void marcarComoListoParaIniciar(Jugador jugador) throws RemoteException {
		for (Jugador j : this.jugadoresMesa) {
			if (j.equals(jugador)) {
				j.setListoParaIniciar(true);
			}
		}
		
		
	}
	
	@Override 
	public void marcarComoNoListoParaIniciar(Jugador jugador) throws RemoteException{
		for (Jugador j : this.jugadoresMesa) {
			if (j.equals(jugador)) {
				j.setListoParaIniciar(false);
			}
		}
	}

	@Override
	public Jugador getJugadorPasa() throws RemoteException {
		return this.jugadorQuePaso;
	}
	
}
