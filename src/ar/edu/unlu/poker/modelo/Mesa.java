package ar.edu.unlu.poker.modelo;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Queue;

import ar.edu.unlu.rmimvc.observer.ObservableRemoto;

public class Mesa extends ObservableRemoto implements IMesa {

	private Queue<Jugador> jugadoresMesa = new LinkedList<Jugador>();
	private Queue<Jugador> rondaApuesta = new LinkedList<Jugador>();
	private Queue<Jugador> rondaApuestaAux = new LinkedList<Jugador>();
	private HashMap<Jugador, LinkedList<Carta>> cartasADescartar = new HashMap<Jugador, LinkedList<Carta>>();
	private List<Jugador> jugadoresApuestaInsuficiente = new LinkedList<Jugador>();
	private static final HashMap<String, Integer> valorCarta = new HashMap<String, Integer>();
	private HashMap<Jugador, Integer> mapa = new HashMap<Jugador, Integer>();
	private int apuestaMayor;
	private Jugador jugadorMano;
	private Jugador jugadorTurno;
	private int pozo;
	private boolean primeraRonda;
	private Jugador jugadorQuePaso = new Jugador("");
	private boolean comenzoPartida = false;
	private Dealer dealer;
	private Jugador ultimoJugadorIntentaAgregar;
	private Jugador jugadorIncrementoFondos;

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

		jugador.setID();
		this.ultimoJugadorIntentaAgregar =  new Jugador("");
		this.ultimoJugadorIntentaAgregar = jugador;
		this.notificarObservadores(Informe.ID_ESTABLECIDO);
		
		if (this.cantidadJugadoresExcedidos()) {
			if (!this.comenzoPartida) {
				if (!this.nombreRepetido(jugador)) {
					this.jugadoresMesa.add(jugador);
					this.notificarObservadores(Informe.JUGADOR_NUEVO_AGREGADO);
				} else {
					this.notificarObservadores(Informe.NOMBRE_REPETIDO);
				}
			} else {
				this.notificarObservadores(Informe.PARTIDA_COMENZADA);
			}
		} else {
			this.notificarObservadores(Informe.CANT_JUGADORES_EXCEDIDOS);
		}
		
	}
	
	private boolean cantidadJugadoresExcedidos() {
		return this.jugadoresMesa.size() < 7;
	}
	
	private boolean nombreRepetido(Jugador jugador) {
		return jugadoresMesa.stream().anyMatch(j -> j.getNombre().equals(jugador.getNombre()));
	}

	@Override
	public void iniciarJuego() throws RemoteException {

		if (this.jugadoresMesa.size() < 2) {
			this.setearJugadorComoNoListo();
			this.notificarObservadores(Informe.CANT_JUGADORES_INSUFICIENTES);
			return;
		}

		if (this.jugadoresMesa.size() > 7) {
			this.notificarObservadores(Informe.CANT_JUGADORES_EXCEDIDOS);
			return;
		}

		this.prepararMesaParaIniciarPartida();

		this.notificarObservadores(Informe.ESTABLECER_NOMBRE_VENTANA_JUGADOR);

		this.notificarObservadores(Informe.JUGADOR_MANO);

		dealer.repartirCartasParaLaRonda(this.jugadoresMesa);

		this.notificarObservadores(Informe.CARTAS_REPARTIDAS);

		this.notificarObservadores(Informe.TURNO_APUESTA_JUGADOR);

	}

	@Override
	public void darFondosGanador(Jugador jugador) throws RemoteException {
		for (Jugador j : this.jugadoresMesa) {
			if (j.equals(jugador)) {
				j.agregarFondos(this.pozo);
			}
		}
	}

	private void setearJugadorComoNoListo() {
		if (!this.jugadoresMesa.isEmpty()) {
			this.jugadoresMesa.peek().setListoParaIniciar(false);
		}
	}

	private void prepararMesaParaIniciarPartida() throws RemoteException {

		this.jugadorQuePaso = null;

		this.comenzoPartida = true;

		this.jugadoresMesa.forEach(jugador -> jugador.setEnJuego(true));
		this.jugadoresMesa.forEach(jugador -> jugador.setApuesta(0));
		this.jugadoresMesa.forEach(jugador -> jugador.setHaApostado(false));
		this.jugadoresApuestaInsuficiente.clear();
		this.jugadoresMesa.forEach(Jugador::resetearCartas);// HAGO ESTO PARA QUE CADA RONDA TENGAN DIFERENTES CARTAS
		this.inicializarCartasADescartar();
		this.rondaApuesta.clear();
		this.rondaApuestaAux.clear();
		this.mapa.clear();
		this.dealer = new Dealer(this);
		this.pozo = 0;

		this.dealer.determinarJugadorMano();

		this.jugadorMano = this.jugadoresMesa.poll();
		this.jugadorTurno = this.jugadorMano;
		this.jugadoresMesa.add(this.jugadorMano);

		this.apuestaMayor = 0;

		dealer.resetearMazo();

	}

//---------------------------------------------------------------------------------------------------------------------------
//PRIMERA RONDA APUESTAS

	@Override
	public void realizarApuesta(Jugador jugador, int apuesta) throws RemoteException {

		if (esJugadorTurno(jugador)) {

			if (comprobarFondos(jugador, apuesta)) {
				this.restarFondosAgregarApuestaJugador(this.jugadorTurno, apuesta);
				if (apuesta > this.apuestaMayor) {
					this.apuestaMayor = apuesta;
				}

				// AGREGO EL VALOR DE LA APUESTA AL POZO
				this.agregarAlPozo(apuesta);

				// TENGO QUE HACER ESTO, PQ EL JUGADOR QUE VIENE DE LA VISTA NO TIENE LAS
				// CARTAS, ENTONCES SE LAS TENGO QUE SETEAR
				jugador.setListaCartas(buscarCartasCorrespondeJugador(jugador));

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
							this.notificarObservadores(Informe.TURNO_DESCARTE); // LLamo al menu de descarte
						}

					}
				} else {
					this.notificarObservadores(Informe.TURNO_APUESTA_JUGADOR);
				}
			} else {
				this.notificarObservadores(Informe.FONDO_INSUFICIENTE);
				this.notificarObservadores(Informe.TURNO_APUESTA_JUGADOR);
			}
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
		this.notificarObservadores(Informe.DEVOLVER_GANADOR);
	}

	@Override
	public int buscarApuestaMayorEnElMapa() throws RemoteException {
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
		mapa.entrySet().forEach(entry -> {
			try {
				if (entry.getValue() < this.buscarApuestaMayorEnElMapa()) {
					this.jugadoresApuestaInsuficiente.add(entry.getKey());
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		});
	}

	@Override
	public boolean perteneceJugadorApuestaMenor(Jugador jugador) throws RemoteException {
		return this.jugadoresApuestaInsuficiente.contains(jugador);
	}

	private boolean comprobarIgualdad() {
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

	public void jugadorFichaPostEnvite(Jugador jugador) throws RemoteException {

		int apuestaParaFichar = this.calcularCuantoFaltaParaFichar(jugador);

		if (comprobarFondos(jugador, apuestaParaFichar)) { // VERIFICAR SI TIENE FONDOS SUFICIENTES

			this.restarFondosAgregarApuestaJugador(jugador, apuestaParaFichar);// Actualizo la apuesta del jugador

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
					this.notificarObservadores(Informe.TURNO_DESCARTE); // LLamo al menu de descarte
				}
			}
		} else {
			this.notificarObservadores(Informe.APUESTAS_DESIGUALES);
			this.notificarObservadores(Informe.FONDO_INSUFICIENTE);
		}
	}

	public void jugadorPasaPostEnvite(Jugador jugador) throws RemoteException {
		this.jugadoresApuestaInsuficiente.remove(jugador);
		jugador.pasar();
		this.mapa.remove(jugador);
		this.rondaApuestaAux.remove(jugador);

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
				this.notificarObservadores(Informe.TURNO_DESCARTE); // LLamo al menu de descarte
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
					this.notificarObservadores(Informe.TURNO_DESCARTE); // LLamo al menu de descarte
				}
			}
		} else {
			this.notificarObservadores(Informe.TURNO_APUESTA_JUGADOR);
		}
	}

//-----------------------------------------------------------------------------------------------------------------------
//DESCARTES

	@Override
	public void realizarElDescarte(Jugador jugador) throws RemoteException {

		if (esJugadorTurno(jugador)) {

			// TENGO QUE HACER ESTO, PQ EL JUGADOR QUE VIENE DE LA VISTA NO TIENE LAS
			// CARTAS, ENTONCES SE LAS TENGO QUE SETEAR
			jugador.setListaCartas(buscarCartasCorrespondeJugador(jugador)); 

			this.descartarCartas(jugador);

			this.rondaApuestaAux.add(jugador);

			this.jugadorTurno = this.rondaApuesta.poll();
			this.rondaApuesta.add(this.jugadorTurno);

			if (this.comprobarFinalVueltaDescartes(this.jugadorTurno)) {// EN CASO DE QUE SEA EL FINAL SIGNIFICA QUE LA
																		// RONDA DE DESCARTE FINALIZA MOSTRAR NUEVAS
																		// CARTAS E IR A LA SIGUIENTE

				if (continuaEnJuego(jugador)) {
					this.notificarObservadores(Informe.CARTAS_REPARTIDAS);
				}

				// RESETEO LA rondaApuestasAux para utilizarla para la segunda ronda de apuestas
				this.rondaApuestaAux.clear();

				// RESETEO LA jugadoresApuestaInsuficiente para utilizarla para la segunda ronda
				// de apuestas
				this.jugadoresApuestaInsuficiente.clear();

				// ESTABLEZCO LA APUESTAMAYOR EN 0 PARA QUE CUANDO FICHEN EN LA SEGUNDA RONDA DE
				// APUESTAS, NO TENGAN EN CUENTA ESTE VALOR
				this.apuestaMayor = 0;

				if (this.rondaApuesta.size() == 1) {
					this.notificarObservadores(Informe.RONDA_APUESTAS_TERMINADA);
				} else {
					this.notificarObservadores(Informe.SEGUNDA_RONDA_APUESTAS);
				}
			} else {

				this.notificarObservadores(Informe.TURNO_DESCARTE);// PASAR AL SIGUIENTE TURNO
			}
		}
	}

	private boolean continuaEnJuego(Jugador jugador) {
		return this.rondaApuesta.contains(jugador);
	}

	private void descartarCartas(Jugador jugador) throws RemoteException {
		// TENGO QUE BUSCAR LAS CARTAS DEL JUGADOR DE jugadoresMesa Y BORRARLAS
		for (Jugador j : this.rondaApuesta) {
			if (j.equals(jugador)) {
				j.getCartas().removeAll(this.cartasADescartar.get(jugador));
			}
		}

		// REPONER CON NUEVAS CARTAS
		this.dealer.repartirCartasDespuesDelDescarte(this.rondaApuesta);

	}

	private void inicializarCartasADescartar() {
		this.cartasADescartar.clear();
		LinkedList<Carta> listaVacia = new LinkedList<Carta>();
		for (Jugador j : this.jugadoresMesa) {
			this.cartasADescartar.put(j, listaVacia);
		}
	}

	public void agregarCartasADescartar(int posicionCarta, Jugador jugador) throws RemoteException {
		LinkedList<Carta> cartas = this.cartasADescartar.get(jugador); // OBTENGO LAS CARTAS DESCARTADAS DEL JUGADOR DEL
																		// HASHMAP, SI NO TIENE CARTAS DEVUEVE UNA LISTA
																		// VACIA

		if (!isCartaDescartada(cartas, posicionCarta, jugador)) {// DEVUELVE TRUE SI ES UNA CARTA DESCARTADA Y FALSE SI
																	// NO LO ES

			cartas.add(buscarJugadorCoincidaCartas(jugador).get(posicionCarta));// AGREGO LA CARTA A DESCARTAR A LA
																				// LISTA

			this.cartasADescartar.put(jugador, cartas);// CARGO LA LISTA AL HASHMAP
			this.notificarObservadores(Informe.CARTA_DESCARTADA);
		} else {
			this.notificarObservadores(Informe.CARTA_YA_HABIA_SIDO_DESCARTADA);
		}
	}

	private boolean isCartaDescartada(LinkedList<Carta> cartas, int posicionCarta, Jugador jugador) {
		for (Carta c : cartas) {
			// COMPARO PARA VER SI EN LA LISTA DE LAS CARTAS A DESCARTAR EXISTE UNA CARTA
			// IGUAL A LA QUE QUIERO AGREGAR
			if (this.igualdadDeCartas(c, buscarJugadorCoincidaCartas(jugador).get(posicionCarta))) {
				return true;
			}
		}
		return false;
	}

	private boolean igualdadDeCartas(Carta c1, Carta c2) {

		boolean flag = false;
		try {
			flag = (c1.getPalo().equals(c2.getPalo()) && c1.getValor().equals(c2.getValor()));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return flag;

	}

	private LinkedList<Carta> buscarJugadorCoincidaCartas(Jugador jugador) {
		for (Jugador j : this.jugadoresMesa) {
			if (j.equals(jugador)) {
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

		if (esJugadorTurno(jugador)) {
			if (comprobarFondos(jugador, apuesta)) {

				this.restarFondosAgregarApuestaJugador(this.jugadorTurno, apuesta);

				this.agregarAlPozo(apuesta);

				// TENGO QUE HACER ESTO, PQ EL JUGADOR QUE VIENE DE LA VISTA NO TIENE LAS
				// CARTAS, ENTONCES SE LAS TENGO QUE SETEAR
				jugador.setListaCartas(buscarCartasCorrespondeJugador(jugador));

				this.rondaApuestaAux.add(jugador);

				this.notificarObservadores(Informe.APUESTA_REALIZADA);

				this.jugadorTurno = this.rondaApuesta.poll();
				this.rondaApuesta.add(this.jugadorTurno);

				if (this.comprobarFinalVueltaSegundaRondaApuesta(this.jugadorTurno)) {
					if (!comprobarIgualdad()) {
						this.igualarApuestasSegundaRonda(jugador);
					} else {
						this.jugadorTurno = this.rondaApuesta.poll();
						// MEDOTO DE REALIZAR APUESTA SEGUNDA RONDA
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

		int apuestaParaFichar = this.calcularCuantoFaltaParaFichar(jugador);

		if (comprobarFondos(jugador, apuestaParaFichar)) { // VERIFICAR SI TIENE FONDOS SUFICIENTES

			this.restarFondosAgregarApuestaJugador(jugador, apuestaParaFichar);// Actualizo la apuesta del jugador

			this.agregarAlPozo(apuestaParaFichar);

			this.jugadoresApuestaInsuficiente.remove(jugador);

			this.notificarObservadores(Informe.JUGADOR_IGUALA_APUESTA);

			if (this.jugadoresApuestaInsuficiente.isEmpty()) {
				this.notificarObservadores(Informe.RONDA_APUESTAS_TERMINADA_SEGUNDA_RONDA);
			}
		} else {
			this.notificarObservadores(Informe.APUESTAS_DESIGUALES_SEGUNDA_RONDA);
			this.notificarObservadores(Informe.FONDO_INSUFICIENTE_SEGUNDA_RONDA);
		}
	}

	private int buscarApuestaMayor() {
		int apuestaMayorMapa = mapa.entrySet().iterator().next().getValue(); // Agarro un jugador cualquiera del mapa y
																				// tomo su apuesta y la establezmo como
																				// mayor
		for (Entry<Jugador, Integer> entry : mapa.entrySet()) {
			if (entry.getValue() > apuestaMayorMapa) {
				apuestaMayorMapa = entry.getValue();
			}
		}
		return apuestaMayorMapa;
	}

	private int calcularCuantoFaltaParaFichar(Jugador jugador) { // HAGO UNA RESTA PARA SABER CUANTO LE FALTA AL JUGADOR
																	// PARA IGUALAR LA APUESTA MAXIMA
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
		this.jugadorIncrementoFondos = jugador;
		for (Jugador j : this.jugadoresMesa) {
			if (j.equals(jugador)) {
				if (validarLongitudNumero(j.getFondo() + fondoAgregar)) {
					j.agregarFondos(fondoAgregar);
					this.notificarObservadores(Informe.SE_AGREGAN_FONDOS);
				} else {
					this.notificarObservadores(Informe.LONGITUD_MAXIMA_ALCANZADA_FONDOS);
				}
			}
		}
	}
	
	private boolean validarLongitudNumero(int fondos) {
		return ((String.valueOf(fondos)).length() <= 6);
	}

	public int getApuestaJugador(Jugador jugador) throws RemoteException {
		return this.mapa.get(jugador);
	}

	@Override
	public Jugador getJugadorMano() throws RemoteException {
		return this.jugadorMano;
	}

	public HashMap<Jugador, LinkedList<Carta>> getCartasADescartar() {
		return cartasADescartar;
	}

	@Override
	public List<Jugador> getJugadoresMesa() throws RemoteException {
		return List.copyOf(this.jugadoresMesa);
	}

	@Override
	public int getApuestaMayor() throws RemoteException {
		return apuestaMayor;
	}

	@Override
	public void setApuestaMayor(int apuestaMayor) throws RemoteException {
		this.apuestaMayor = apuestaMayor;
	}

	public void sacarJugador(Jugador jugador) throws RemoteException {
		this.jugadoresMesa.remove(jugador);
	}

	@Override
	public Jugador getJugadorTurno() throws RemoteException {
		return jugadorTurno;
	}

	@Override
	public void borrarJugadoresMesa() throws RemoteException {
		this.jugadoresMesa.clear();
	}

	@Override
	public void setearJugadoresMezclados(List<Jugador> jugadoresMezclados) throws RemoteException {
		this.jugadoresMesa.addAll(jugadoresMezclados);
	}

//-------------------------------------------------------------------------------------------------------------
	@Override
	public Jugador devolverGanador() throws RemoteException {
		return this.dealer.devolverGanador(this.rondaApuestaAux);
	}

	@Override
	public List<Jugador> getRondaApuesta() throws RemoteException {
		return List.copyOf(this.rondaApuesta);
	}

	@Override
	public List<Jugador> getRondaApuestaAux() throws RemoteException {
		return List.copyOf(this.rondaApuestaAux);
	}

	private void agregarAlPozo(int apuesta) {
		this.pozo += apuesta;
	}

	@Override
	public boolean getPrimeraRonda() throws RemoteException {
		return primeraRonda;
	}

	@Override
	public void setPrimeraRonda(boolean primeraRonda) throws RemoteException {
		this.primeraRonda = primeraRonda;
	}

	@Override
	public void removerJugadores(Jugador jugador) throws RemoteException {
		this.jugadoresMesa.remove(jugador);
		this.notificarObservadores(Informe.JUGADOR_SE_RETIRA);
	}

	@Override
	public void removerJugadorSeRetiraEnJuego(Jugador jugador) throws RemoteException {
		this.jugadoresMesa.remove(jugador);
		this.mapa.remove(jugador);
		this.setComenzoPartida(false);
		this.devolverFondos();
		this.notificarObservadores(Informe.JUGADOR_SE_RETIRA_EN_PARTIDA);
	}

	@Override
	public void marcarComoListoParaIniciar(Jugador jugador) throws RemoteException {
		if (this.comprobarJugadoresSuficientes()) {
			if (this.tieneFondosSuficientesParaIniciarPartida(jugador)) {
				jugadoresMesa.stream().filter(j -> j.equals(jugador)).findFirst().ifPresent(j -> j.setListoParaIniciar(true));
			} else {
				this.notificarObservadores(Informe.FONDOS_INSUFICIENTES_COMENZAR_PARTIDA);
			}
		} else {
			this.notificarObservadores(Informe.CANT_JUGADORES_INSUFICIENTES);
		}
	}
	
	@Override
	public boolean getTodosLosJugadoresEstanListosParaIniciar() throws RemoteException{
		return jugadoresMesa.stream().allMatch(Jugador::getListoParaIniciar);
	}
	
	private boolean comprobarJugadoresSuficientes() {
		return this.jugadoresMesa.size() > 1;
	}
	
	private boolean tieneFondosSuficientesParaIniciarPartida(Jugador jugador) {
		return jugadoresMesa.stream().anyMatch(j -> j.getNombre().equals(jugador.getNombre()) && j.getFondo() >= 1);
	}
	
	@Override
	public boolean esJugadorConFondosInsuficientesParaComenzar(Jugador jugador) throws RemoteException {
		return !(this.tieneFondosSuficientesParaIniciarPartida(jugador));
	}

	@Override
	public void marcarComoNoListoParaIniciar(Jugador jugador) throws RemoteException {
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

	@Override
	public boolean getComenzoPartida() throws RemoteException {
		return comenzoPartida;
	}

	@Override
	public void setComenzoPartida(boolean comenzo) throws RemoteException {
		this.comenzoPartida = comenzo;
	}

	@Override
	public void devolverFondos() throws RemoteException {

		if (!this.mapa.isEmpty()) {
			for (Jugador j : this.jugadoresMesa) {
				if (estaEnElMapa(j)) {
					j.agregarFondos(this.mapa.get(j));
				}
			}
		}

	}

	private boolean estaEnElMapa(Jugador jugador) {
		return this.mapa.containsKey(jugador);
	}

	@Override
	public int getfondosJugador(Jugador jugador) throws RemoteException {
		for (Jugador j : this.jugadoresMesa) {
			if (j.getNombre().equals(jugador.getNombre())) {
				return j.getFondo();
			}
		}
		return 0;
	}

	@Override
	public String getNombreUltimoJugadorIntentaAgregar() throws RemoteException{
		return ultimoJugadorIntentaAgregar.getNombre();
	}
	
	@Override 
	public int getIDUltimoJugadorIntentaAgregar() throws RemoteException{
		return ultimoJugadorIntentaAgregar.getID();
	}
	
	@Override
	public int getIDJugadorIntentaIncrementarFondos() throws RemoteException{
		return this.jugadorIncrementoFondos.getID();
	}

}
