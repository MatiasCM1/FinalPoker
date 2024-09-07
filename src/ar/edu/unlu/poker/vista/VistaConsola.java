package ar.edu.unlu.poker.vista;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import ar.edu.unlu.poker.controlador.Controlador;
import ar.edu.unlu.poker.modelo.Carta;
import ar.edu.unlu.poker.modelo.Jugador;
import ar.edu.unlu.poker.modelo.Mesa;

public class VistaConsola {
	
	private Controlador controlador;
	
	public void inicioJuego() {
		int opcion = -1;
		mostrarBannerInicio();
		while (opcion != 0) {
			opcion = mostrarMenuInicio();
			procesarOpcionMenuInicio(opcion);
		}
	}
	
	private void procesarOpcionMenuInicio (int opcion) {
		switch (opcion) {
			case 1:
				crearJugador();
			break;
			
			case 2:
				mostrarJugadores();
				esperarEnter();
			break;
			
			case 3:
				controlador.iniciarGame();
				esperarEnter();
			break;
			
			case 0:
				opcion = 0;
				System.out.println("Se salio del juego exitosamente saludos!");
			break;
		}
	}
	
	private void mostrarBannerInicio() {
		System.out.println("------------------------------------------------------------------------------");
		System.out.println("--                              POKER                                       --");
		System.out.println("------------------------------------------------------------------------------");
		System.out.println("--                juegue bajo su propio riesgo                              --");
		System.out.println("------------------------------------------------------------------------------");
	}
	
	private void mostrarJugadores() {
		List<Jugador> lista = controlador.obtenerJugadores();
		System.out.println("------------------------------------");
		System.out.println("Lista de jugadores");
		System.out.println("------------------------------------");
		for (Jugador j : lista) {
			System.out.println(" ");
			System.out.println("Nombre: " + j.getNombre());
		}
	}
	
	private void crearJugador() {
		System.out.println("Ingrese el nombre de jugador:");
		Scanner sc = new Scanner(System.in);
		String nombre = sc.nextLine();
		Jugador jugador = new Jugador(nombre);
		controlador.agregarJugador(jugador);
	}
	
	private int mostrarMenuInicio() {
		int opcion = -1;
		while (opcion < 0 || opcion > 3) {
			mostrarOpcionesMenuInicio();
			Scanner sc = new Scanner(System.in);
			opcion = sc.nextInt();
		}
		return opcion;
	}

	private void mostrarOpcionesMenuInicio() {
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
	}
	
	
	
	public void setControlador(Controlador controlador) {
		this.controlador = controlador;
	}
	

	public void mostrarJugadorMano(Jugador obtenerJugadorMano) {
		System.out.println("Jugador mano: " + obtenerJugadorMano.getNombre());
	}

	public void mostrarCartasJugador(List<Jugador> list) {
		for (Jugador j : list) {
			System.out.println("");
			System.out.println("Jugador: " + j.getNombre());
			System.out.println("");
			for (Carta c : j.getCartas()) {
				System.out.println(c.getValor() + " de " + c.getPalo());
			}
		}
	}

	public void informarJugadoresInsuficientes() {
		System.out.println("La cantidad de jugadores es insuficiente para iniciar el juego");
	}

	public void informarCantJugadoresExcedidos() {
		System.out.println("La cantidad de jugadores excede el limite maximo que permite el juego");
	}
	
	private void mostrarCartasJugador(Jugador j) {
		System.out.println("Jugador: " + j.getNombre());
		for (Carta c : j.getCartas()) {
			System.out.println(c.getValor() + " de " + c.getPalo());
		}
	}
	
	private void esperarEnter() {
		System.out.println("Presione ENTER para continuar...");
		Scanner sc = new Scanner(System.in);
		String pausa = sc.nextLine();
	}

	public void mostrarGanador(List<Jugador> list) {
		for(Jugador j : list) {
			System.out.println("Ganador: " + j.getNombre() + " con " + j.getResultadoValoresCartas());
			System.out.println("");
		}
	}
	
	/*public int pedirApuesta(Jugador jugador) {
        System.out.println("Turno de " + jugador.getNombre() + ":");
        System.out.println("1. Fichar");
        System.out.println("2. Envidar");
        System.out.println("3. Pasar");

        Scanner sc = new Scanner(System.in);
        int opcion = sc.nextInt();
        int cantidad = 0;

        switch (opcion) {
            case 1:
                cantidad = controlador.apuestaMinima();
                break;
            case 2:
                System.out.println("Ingrese la cantidad para envidar:");
                cantidad = sc.nextInt();
                break;
            case 3:
                cantidad = 0;
                break;
        }

        return cantidad;
    }*/

	/*public void solicitarApuestas() {
		System.out.println("1- Apostar");
		System.out.println("2- Pasar");
		Scanner sc = new Scanner(System.in);
		int opcion = sc.nextInt();
		controlador.consultarApuestas(opcion);	
	}
	
	public void solicitarApuestaJugador(Jugador jugador) {
		System.out.println(jugador.getNombre() + ", ingrese su apuesta, esta debera ser mayor o igual a la apuesta mayor");
		Scanner sc = new Scanner(System.in);
		int apuesta = sc.nextInt();
		controlador.setearApuestaJugador(apuesta, jugador);
	}
	
	public void informarApuestaInsuficiente() {
		System.out.println("La apuesta ingresada debe ser mayor o igual a la apuesta mayor");
	}
	
	public void informarFondosInsuficientes() {
		System.out.println("La apuesta ingresada es maoyr a los fondos que dispone");
	}*/
		
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

