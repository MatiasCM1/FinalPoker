package ar.edu.unlu.poker.pruebas;

import java.util.LinkedList;
import java.util.Scanner;

import ar.edu.unlu.poker.modelo.Carta;
import ar.edu.unlu.poker.modelo.Dealer;
import ar.edu.unlu.poker.modelo.Jugador;
import ar.edu.unlu.poker.modelo.Resultado;

public class Main {
	
/**	private LinkedList<Jugador> jugadores = new LinkedList<Jugador>();
	private int cantJugadores = 0;
	private Dealer dealer = new Dealer();
	private Carta c;
	

	public static void main(String[] args) throws Exception {
		Main juego = new Main();
		juego.comenzarJuego();
	}
	
	private void comenzarJuego() throws Exception{
		
		int opcion = -1;
		System.out.println("------------------------------------------------------------------------------");
		System.out.println("--                              POKER                                       --");
		System.out.println("------------------------------------------------------------------------------");
		System.out.println("--                juegue bajo su propio riesgo                              --");
		System.out.println("------------------------------------------------------------------------------");
		while (opcion != 0) {
			opcion = mostrarMenuInicio();
			switch (opcion) {
				case 1:
					//AgregarJugador
					if (this.cantJugadores < 8) {
						this.jugadores.add(agregarJugador());
						cantJugadores++;
					} else {
						System.out.println("La cantidad de jugadores ya alcanzo su maximo");
						esperarEnter();
					}
					
				break;
				case 2:
					//MostrarListaDeJugadores
					mostrarJugadores();
					esperarEnter();
				break;
				case 3:
					//ComenzarPartida
					if (this.cantJugadores > 1) {
						jugar();
					} else {
						System.out.println("La cantidad de jugadores es insuficiente para comenzar");
						esperarEnter();
					}
					
				break;
				case 0:
					opcion = 0;
					this.jugadores = new LinkedList<Jugador>();
					this.cantJugadores = 0;
					System.out.println("Se salio del juego exitosamente saludos!");
				break;
			}
		}
		
	}

	private int mostrarMenuInicio() {
		int opcion = -1;
		while (opcion < 0 || opcion > 3) {
			
			System.out.println("");
			System.out.println("------------------------------------------------------------------------------");
			System.out.println("--                        Menú de Configuración                         --");
			System.out.println("------------------------------------------------------------------------------");
			System.out.println("--                                Opciones                                      --");
			System.out.println("------------------------------------------------------------------------------");
			System.out.println("-- 1 - Agregar jugador              --");
			System.out.println("-- 2 - Mostrar Lista Jugador                                                     --");
			System.out.println("-- 3 - Comenzar Partida                                                   --");
			System.out.println("------------------------------------------------------------------------------");
			System.out.println("-- 0 - Salir del Juego                                                        --");
			System.out.println("------------------------------------------------------------------------------");
			Scanner sc = new Scanner(System.in);
			opcion = sc.nextInt();
		}
		return opcion;
	}

	//--------------------------------------------------------------------------------------------------------------------------
	
	private Jugador agregarJugador() {
		String nombre = "";
		int fondoApuesta = -1;
		while (nombre.equals("") && fondoApuesta == -1) {
			System.out.println("Ingrese el nombre del jugador");
			Scanner sc = new Scanner(System.in);
			nombre = sc.nextLine();
			System.out.println("Ingrese el fondo para apostar del jugador");
			Scanner scc = new Scanner(System.in);
			fondoApuesta = scc.nextInt();
			if (nombre.equals("") && fondoApuesta == -1) {
				System.out.println("Error, ingrese correctamente el nombre del jugador y el fondo para apostar");
			}
		}
		Jugador jugador = new Jugador(nombre, fondoApuesta);
		return jugador;
	}
	
	private void mostrarJugadores() {
		System.out.println("------------------------------------------------------------------------------");
		System.out.println("--                            Lista de Jugadores                             --");
		System.out.println("------------------------------------------------------------------------------");
		int i = 1;
		for (Jugador j : jugadores) {
			System.out.println(i + " Nombre: " + j.getNombre() + " fondo apuestas: " + j.getApuestaDisponible());
			i++;
			System.out.println("");
		}
	}
	
	private void esperarEnter() {
		System.out.println("Presione ENTER para continuar...");
		Scanner sc = new Scanner(System.in);
		String pausa = sc.nextLine();
	}
	
	private void jugar() throws Exception{
		LinkedList<Jugador> jugadoresActuales = dealer.primerJugadorRepartir(this.jugadores);
		LinkedList<Jugador> jugadoresRonda = jugadoresActuales;
		dealer.setearCartasRonda();
		repartirCartas(jugadoresRonda);
		revisarCartasApuesta(jugadoresRonda);
		//RevisarQueTodosLosJugadoresTenganLasMismasApuestas_Despues_De_Volver_A_Apostar
		procesoDescarte(jugadoresRonda);
		revisarCartasApuesta(jugadoresRonda);
		//RevisarQueTodosLosJugadoresTenganLasMismasApuestas_Despues_De_Volver_A_Apostar
		Jugador jug = determinarGanador(jugadoresRonda);
		//HacerUnPosoDeApuestasParaAgregarTodoAlGanador
		System.out.println("El ganador es: " + jug.getNombre());
		System.out.println("con: " + jug.calcularValorCartas());
	}
	
	private Jugador determinarGanador(LinkedList<Jugador> jugadoresRonda) {
		int[] jugadas = new int[jugadoresRonda.size()];
		for (int i = 0; i < jugadoresRonda.size(); i++) {
			jugadas[i] = jugadoresRonda.get(i).calcularResultado();
		}
		int posMayor = buscarMayor(jugadas);
		if (seRepiteMayor(jugadas, posMayor)) {
			int resultadoRepetido = jugadas[posMayor];
			return verificarCartasMasAltas(jugadoresRonda, resultadoRepetido);
		} else {
			return jugadoresRonda.get(posMayor);
		}
	}
	
	private Jugador verificarCartasMasAltas(LinkedList<Jugador> jugs, int resultadoRepetido) {
		LinkedList<Jugador> jugadoresConResultadosEmpatados = new LinkedList<Jugador>();
		jugadoresConResultadosEmpatados = encontrarJugadoresConResultadosEmpatados(jugs, resultadoRepetido);
		String[] orden = c.getOrdenCartas();
		int cartaMayor = buscarPosicionOrden(jugadoresConResultadosEmpatados.getFirst().getCartas().getFirst().getValor(), orden);
		Jugador jugadorGanadorActual = jugadoresConResultadosEmpatados.getFirst();
		int valorCartaActual = -1;
		for (Jugador j : jugadoresConResultadosEmpatados) {
			valorCartaActual = buscarPosicionOrden(j.getCartas().getFirst().getValor(), orden);
			if (valorCartaActual > cartaMayor) {
				cartaMayor = valorCartaActual;
				jugadorGanadorActual = j;
			}
		}
		return jugadorGanadorActual;
	}
	
	private int buscarPosicionOrden(String valorCarta, String[] orden) {
		for (int i = 0; i < orden.length; i++) {
			if (orden[i].equals(valorCarta)) {
				return i;
			}
		}
		return -1;
	}
	
	
	private LinkedList<Jugador> encontrarJugadoresConResultadosEmpatados(LinkedList<Jugador> jugs, int resultadoRepetido){
		LinkedList<Jugador> resultado = new LinkedList<Jugador>();
		for (Jugador j : jugs) {
			if (j.calcularResultado() == resultadoRepetido) {
				resultado.add(j);
			}
		}
		return resultado;
	}
	
	private int buscarMayor(int[] jugadas) {
		int posMayor = 0;
		int mayor = jugadas[0];
		for (int i = 1; i < jugadas.length; i++) {
			if (jugadas[i] > mayor) {
				mayor = jugadas[i];
				posMayor = i;
			}
		}
		return posMayor;
	}
	
	private boolean seRepiteMayor(int[] jugadas, int posMayor) {
		int mayor = jugadas[posMayor];
		for (int i = 0; i < jugadas.length; i++) {
			if (mayor == jugadas[i]) {
				return true;
			}
		}
		return false;
	}
	
	private void procesoDescarte(LinkedList<Jugador> jugadoresRonda) {
		for (Jugador j : jugadoresRonda) {
			int opcion = -1;
			System.out.println("Jugador: " + j.getNombre());
			j.mostrarCartas();
			while (opcion != 2) {
				opcion = mostrarMenuDescarte();
				switch(opcion) {
					case 1 :
						realizarDescarte(j);
						System.out.println("Cartas nuevas");
						j.mostrarCartas();
					break;
					case 2 :
						opcion = 2;
					break;
				}
			}
			esperarEnter();
		}
	}
	
	private void realizarDescarte(Jugador j) {
		System.out.println("Cartas del jugador:" );
		j.mostrarCartas();
		System.out.println("Indique que cartas desea descartar");
		String op = "Y";
		String numero;
		String palo;
		int cantCartasEliminadas = 0;
		while (op.equals("Y") || cantCartasEliminadas == 5) {
			System.out.println("Ingrese el Numero de la carta que desea eliminar");
			Scanner sc = new Scanner(System.in);
			numero = sc.nextLine();
			System.out.println("Ingrese el palo de la carta que desea eliminar");
			Scanner scc = new Scanner(System.in);
			palo = scc.nextLine();
			Carta c = new Carta(numero, palo);
			if (j.descartarCarta(c)) {
				cantCartasEliminadas++;
				System.out.println("Carta descartada con exito");
			} else {
				System.out.println("Esta carta no se puede descartar");
			}
			System.out.println("Desea continuar descartando cartas (Y/N)");
			Scanner sccc = new Scanner(System.in);
			op = scc.nextLine();
		}
		j.recibirCarta(dealer.repartirCarta());
	}
	
	private int mostrarMenuDescarte() {
		int opcion = -1;
		while (opcion < 0 || opcion > 2) {
			System.out.println("");
			System.out.println("------------------------------------------------------------------------------");
			System.out.println("--                        Menú de Descarte                                  --");
			System.out.println("------------------------------------------------------------------------------");
			System.out.println("-- 1 - Realizar Descarte              --");
			System.out.println("-- 2 - Continuar con las cartas                                             --");
			System.out.println("------------------------------------------------------------------------------");
			Scanner sc = new Scanner(System.in);
			opcion = sc.nextInt();
		}
		return opcion;
	}
	
	private void repartirCartas(LinkedList<Jugador> jugadoresActuales) {
		dealer.setearCartasRonda();
		for (Jugador j : jugadoresActuales) { //RepartirHastaCartasCincoCartasACadaJugador
			while (j.getCartas().size() < 5) {
				j.recibirCarta(dealer.repartirCarta());
			}
		}
	}
	
	private void revisarCartasApuesta(LinkedList<Jugador> listaJugador) {
		int apuestaAnterior = -1;
		int cantidad = -1;
		for (Jugador j : listaJugador) {
			int opcion = -1;
			System.out.println("Jugador: " + j.getNombre());
			System.out.println("");
			while (opcion != 3) {
				opcion = mostrarMenuApuesta();
				switch (opcion) {
					case 1 :
						j.mostrarCartas();
						esperarEnter();
					break;
					case 2 :
						System.out.println("Fondo disponible para apostar: " + j.getApuestaDisponible());
						System.out.println("Ingrese la cantidad de desea apostar: ");
						Scanner sc = new Scanner(System.in);
						cantidad = sc.nextInt();
						while ((cantidad < apuestaAnterior) || (cantidad > j.getApuestaDisponible())) {
							System.out.println("El valor de la apuesta debe ser igual o superior a las demas apuestas, y se debe tener los fondos disponibles");
							Scanner scc = new Scanner(System.in);
							cantidad = scc.nextInt();
						}
						apuestaAnterior = cantidad;
						j.realizarApuesta(cantidad);
					break;
					case 3:
						opcion = 3;
						listaJugador.remove(j);
						System.out.println("El jugador ha decidido dejar la ronda");
						esperarEnter();
					break;
				}
			}
		}
		
		
	}
	
	private int mostrarMenuApuesta() {
		int opcion = -1;
		while (opcion < 0 || opcion > 3) {
			System.out.println("");
			System.out.println("------------------------------------------------------------------------------");
			System.out.println("--                        Menú de Apuestas                         --");
			System.out.println("------------------------------------------------------------------------------");
			System.out.println("-- 1 - Revisar Cartas              --");
			System.out.println("-- 2 - Realizar Apuesta                                                     --");
			System.out.println("-- 3 - PasarApuesta                                                  --");
			System.out.println("------------------------------------------------------------------------------");
			Scanner sc = new Scanner(System.in);
			opcion = sc.nextInt();
		}
		return opcion;
	}*/
	
}
