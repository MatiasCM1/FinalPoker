package ar.edu.unlu.poker.vista.consola;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import ar.edu.unlu.poker.controlador.Controlador;
import ar.edu.unlu.poker.modelo.Carta;
import ar.edu.unlu.poker.modelo.Jugador;
import ar.edu.unlu.poker.serializacion.stats.EstadisticasJugador;
import ar.edu.unlu.poker.vista.IVista;

public class VistaConsolaSwing extends JFrame implements IVista {

	private static final long serialVersionUID = 1L;
	private JTextArea areaSalida;
	private JTextField campoEntrada;
	private Controlador controlador;
	private boolean esperandoEntrada;
	private String nombreJugadorActual;
	private Jugador jugadorActual;
	private Estados estadoFlujo;

	public VistaConsolaSwing() {
		this.estadoFlujo = Estados.SOLICITAR_NOMBRE_JUGADOR;
		setTitle("Poker");
		setSize(800, 600);

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent e) {
				//jugadorSaleDelJuegoPostPrimeraPartida();
				System.exit(0);
			}
		});

		setLayout(new BorderLayout());
		areaSalida = new JTextArea();
		areaSalida.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(areaSalida);
		campoEntrada = new JTextField();
		campoEntrada.setPreferredSize(new Dimension(800, 30));
		campoEntrada.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String input = campoEntrada.getText();
				try {
					if (!input.trim().contentEquals("")) {
						procesarEntrada(input);
					}
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
				campoEntrada.setText("");
			}
		});

		campoEntrada.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (campoEntrada.getText().length() >= 6) {
					e.consume();
				}
			}
		});
		add(scrollPane, BorderLayout.CENTER);
		add(campoEntrada, BorderLayout.SOUTH);
		setVisible(true);

		areaSalida.append("Ingrese su nombre\n");
	}

	/*private void jugadorSaleDelJuegoPostPrimeraPartida() {

		if (!this.comprobarPartidaComenzada()) {
			controlador.jugadorSeRetiraDelJuego(this.jugadorActual);
		} else {
			controlador.jugadorSeRetiraConJuegoComenzado(this.jugadorActual);
		}

	}

	private boolean comprobarPartidaComenzada() {
		return this.controlador.comenzoPartida();
	}*/

	@Override
	public void setControlador(Controlador controlador) {
		this.controlador = controlador;
	}

	private void setEnableCampoEntrada(boolean valor) {
		this.campoEntrada.setEnabled(valor);
	}

	private void procesarEntrada(String input) throws RemoteException {

		if (controlador == null) {
			areaSalida.append("Error: Controlador no configurado.\n");
			return;
		}

		switch (estadoFlujo) {
		case SOLICITAR_NOMBRE_JUGADOR:
			esperandoEntrada = true;
			solicitarNombre(input);
			break;
		case SOLICITAR_FONDO_JUGADOR:
			esperandoEntrada = true;
			solicitarFondo(input);
			break;
		case MENU_PRINCIPAL:
			esperandoEntrada = true;
			menu(input);
			break;
		case MENU_APUESTAS:
			esperandoEntrada = true;
			menuApuestas(input);
			break;
		case ESPERANDO_ENVITE:
			esperandoEntrada = true;
			realizarEnvite(input);
			break;
		case MENU_APUESTAS_DESIGUALES:
			esperandoEntrada = true;
			menuApuestasDesiguales(input);
			break;
		case MENU_OPCION_DESCARTES:
			esperandoEntrada = true;
			menuOpcionDescarte(input);
			break;
		case MENU_SELECCION_DESCARTES:
			esperandoEntrada = true;
			menuEsperandoDescarte(input);
			break;
		case MENU_SEGUNDA_RONDA_APUESTAS:
			esperandoEntrada = true;
			menuSegundasApuestas(input);
			break;
		case ESPERANDO_ENVITE_SEGUNDA_RONDA:
			esperandoEntrada = true;
			relizarEnviteSegundaRonda(input);
			break;
		case MENU_NUEVA_RONDA:
			esperandoEntrada = true;
			menuNuevaRonda(input);
			break;
		case MENU_APUESTAS_DESIGUALES_SEGUNDA_RONDA:
			esperandoEntrada = true;
			menuApuestasDesigualesSegundaRonda(input);
			break;
		case ESPERANDO_FONDOS:
			esperandoEntrada = true;
			agregandoFondos(input);
			break;
		case ESPERANDO_FONDOS_2:
			esperandoEntrada = true;
			agregandoFondos2(input);
			break;
		}
	}

//----------------------------------------------------------------------------------------------------------
//JUEGO PRINCIPAL
//----------------------------------------------------------------------------------------------------------

	public void solicitarNombre(String input) {
		if (esperandoEntrada) {
				/*if (controlador.validarTextoNombre(input)) {*/
					nombreJugadorActual = input.trim();
					areaSalida.append("Ingrese el fondo que desea\n");
					this.estadoFlujo = Estados.SOLICITAR_FONDO_JUGADOR;
				/*} else {
					areaSalida.append("¡Nombre no valido! Por favor, ingrese un nombre para comenzar:\n");
					this.estadoFlujo = Estados.SOLICITAR_NOMBRE_JUGADOR;
				}
				return;*/
		}
	}
	


	public void solicitarFondo(String input) {
		if (esperandoEntrada) {
			//if (!this.comprobarPartidaComenzada()) {
				/*if (controlador.validarTextoFondos(input)) {*/
					jugadorActual = new Jugador(this.nombreJugadorActual, Integer.parseInt(input));

					controlador.setJugadorActual(jugadorActual);
					controlador.agregarJugador(jugadorActual);
					
					/*if (!controlador.agregarJugador(jugadorActual)) {
						return;
					}
					controlador.setJugadorActual(jugadorActual);*/

				/*} else {
					areaSalida.append("¡Numero no valido! ¡Ingrese un numero entero!\n");
					areaSalida.append("Ingrese el fondo que desea\n");
					this.estadoFlujo = Estados.SOLICITAR_FONDO_JUGADOR;
				}*/
			//} else {
				//areaSalida.append("Error, la partida ya comenzo.\n");
			//	this.estadoFlujo = Estados.SOLICITAR_NOMBRE_JUGADOR;
		//	}
		}

	}

	public void menu(String input) {

		if (esperandoEntrada) {
			switch (input.toLowerCase()) {
			case "1":
				mostrarJugadores();
				this.esperandoEntrada = false;
				break;
			case "2":
				this.esperandoEntrada = false;
				controlador.iniciarSiEstaListo(this.jugadorActual);
				break;
			case "3":
				mostrarFondos();
				this.esperandoEntrada = false;
				break;
			case "4":
				areaSalida.append("Ingrese los fondos.\n");
				this.estadoFlujo = Estados.ESPERANDO_FONDOS;
				break;
			case "5":
				this.mostrarTopJugadores(this.controlador.obtenerTopJugadores());
				break;
			case "0":
				areaSalida.append("Se salio del juego exitosamente. Saludos!\n");
				controlador.jugadorSeRetiraDelJuego(this.jugadorActual);
				System.exit(0);
				break;
			default:
				areaSalida.append("Comando no reconocido. Intente nuevamente.\n");
				break;
			}
		}
	}

	private void mostrarTopJugadores(List<EstadisticasJugador> listaTopJugadores) {

		int posicion = 1;
		for (EstadisticasJugador j : listaTopJugadores) {
			areaSalida.append(posicion + " - Nombre: " + j.getNombreJugador() + "             Partidas ganadas: "
					+ j.getCantPartidasGanadas() + ".\n");
			posicion++;
		}

		this.mostrarOpcionesMenu();
	}

	private void mostrarFondos() {
		areaSalida.append("Fondos: " + controlador.getFondosJugador(this.jugadorActual) + ".\n");
		this.mostrarOpcionesMenu();
	}

	private void agregandoFondos(String input) {
		controlador.incrementarFondos(this.jugadorActual, input);
		this.notificarFondosAgregados();
	}

	private void mostrarOpcionesMenu() {
		this.controlador.setEstoyEnVistaLogin(false);
		this.setEnableCampoEntrada(true);
		areaSalida.append("Seleccione una opcion:\n");
		areaSalida.append("1 - Ver Lista de Jugadores\n");
		areaSalida.append("2 - Comenzar Juego\n");
		areaSalida.append("3 - Mostrar Fondos\n");
		areaSalida.append("4 - Agregar Fondos\n");
		areaSalida.append("5 - Mostrar TOP de jugadores\n");
		areaSalida.append("0 - Salir\n");
		this.estadoFlujo = Estados.MENU_PRINCIPAL;
	}

	private void mostrarJugadores() {
		var jugadores = controlador.obtenerJugadores();
		areaSalida.append("Lista de Jugadores:\n");
		for (Jugador jugador : jugadores) {
			areaSalida.append(" - " + jugador.getNombre() + "\n");
		}
		mostrarOpcionesMenu();
	}

	@Override
	public void mostrarJugadorMano(Jugador jugador) {
		areaSalida.append("Jugador en mano: " + jugador.getNombre() + "\n");
	}

	@Override
	public void mostrarCartasJugador(List<Jugador> jugadores) {
		for (Jugador jugador : jugadores) {
			if (jugador.equals(jugadorActual)) { // Mostrar solo cartas del jugador actual
				areaSalida.append("Tus cartas:\n");
				jugador.getCartas().forEach(carta -> {
					try {
						areaSalida.append(carta.getValor() + " de " + carta.getPalo() + "\n");
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				});
			}
		}
	}

	@Override
	public void mostrarGanador(Jugador ganador) {
		areaSalida.append("Ganador:\n");
		areaSalida.append(" - " + ganador.getNombre() + " con " + ganador.getResultadoValoresCartas() + "\n");
	}

	@Override
	public void mostrarOpcionesMenuEmpezarOtraRonda() {
		controlador.establecerJugadorComoNoListo(this.jugadorActual);
		this.setEnableCampoEntrada(true);
		areaSalida.append("Seleccione una opcion:\n");
		areaSalida.append("1 - Seguir jugando\n");
		areaSalida.append("2 - Mostrar Jugadores\n");
		areaSalida.append("3 - Mostrar Fondos\n");
		areaSalida.append("4 - Agregar Fondos\n");
		areaSalida.append("5 - Mostrar TOP de jugadores\n");
		areaSalida.append("0 - Salir\n");
		this.setEnableCampoEntrada(true);
		this.estadoFlujo = Estados.MENU_NUEVA_RONDA;
	}

	public void menuNuevaRonda(String input) {
		if (esperandoEntrada) {
			switch (input.toLowerCase()) {
			case "1":
				this.esperandoEntrada = false;
				controlador.iniciarSiEstaListoPostPrimeraRonda(this.jugadorActual);
				break;
			case "2":
				this.mostrarJugadores2();
				this.esperandoEntrada = false;
				break;
			case "3":
				this.mostrarFondos2();
				this.esperandoEntrada = false;
				break;
			case "4":
				areaSalida.append("Ingrese los fondos.\n");
				this.estadoFlujo = Estados.ESPERANDO_FONDOS_2;
				break;
			case "5":
				this.mostrarTopJugadoresPostRonda(this.controlador.obtenerTopJugadores());
				break;
			case "0":
				areaSalida.append("Se salio del juego exitosamente. Saludos!\n");
				controlador.jugadorSeRetiraDelJuego(jugadorActual);
				System.exit(0);
				break;
			default:
				areaSalida.append("Comando no reconocido. Intente nuevamente.\n");
				break;
			}
		}
	}

	private void mostrarTopJugadoresPostRonda(List<EstadisticasJugador> listaTopJugadores) {

		int posicion = 1;
		for (EstadisticasJugador j : listaTopJugadores) {
			areaSalida.append(posicion + " - Nombre: " + j.getNombreJugador() + "             Partidas ganadas: "
					+ j.getCantPartidasGanadas() + ".\n");
			posicion++;
		}

		this.mostrarOpcionesMenuEmpezarOtraRonda();
	}

	private void mostrarFondos2() {
		areaSalida.append("Fondos: " + controlador.getFondosJugador(this.jugadorActual) + ".\n");
		this.mostrarOpcionesMenuEmpezarOtraRonda();
	}

	private void mostrarJugadores2() {
		var jugadores = controlador.obtenerJugadores();
		areaSalida.append("Lista de Jugadores:\n");
		for (Jugador jugador : jugadores) {
			areaSalida.append(" - " + jugador.getNombre() + "\n");
		}
		this.mostrarOpcionesMenuEmpezarOtraRonda();
	}

	private void agregandoFondos2(String input) {
		controlador.incrementarFondos2(this.jugadorActual, input);
		this.notificarFondosAgregados2();
	}

	@Override
	public void iniciar() {
		this.setVisible(true);
	}

//----------------------------------------------------------------------------------------------------------
//APUESTAS
//----------------------------------------------------------------------------------------------------------

	@Override
	public void mostrarMenuApuestas() {

		this.setEnableCampoEntrada(true);

		areaSalida.append("Seleccione una opcion:\n");
		areaSalida.append("1 - Envitar\n");
		areaSalida.append("2 - Fichar\n");
		areaSalida.append("3 - Pasar\n");
		this.estadoFlujo = Estados.MENU_APUESTAS;
	}

	public void menuApuestas(String input) {
		if (esperandoEntrada) {
			switch (input.toLowerCase()) {
			case "1":
				areaSalida.append("Ingrese el valor de la apuesta.\n");
				this.estadoFlujo = Estados.ESPERANDO_ENVITE;
				break;
			case "2":
				this.realizarFiche();
				break;
			case "3":
				this.realizarPase();
				break;
			default:
				areaSalida.append("Comando no reconocido. Intente nuevamente.\n");
				break;
			}
		}
	}

	private void realizarPase() {
		if (this.jugadorActual.getNombre().equals(controlador.getJugadorTurno().getNombre())) {
			controlador.realizarLosPases(this.jugadorActual);
			this.esperandoEntrada = false;
		}
	}

	public void realizarEnvite(String input) {
		if (this.jugadorActual.getNombre().equals(controlador.getJugadorTurno().getNombre())) {
			controlador.realizarLasApuestas(this.jugadorActual, input);
			this.esperandoEntrada = false;
		}
	}

	private void realizarFiche() {
		if (this.jugadorActual.getNombre().equals(controlador.getJugadorTurno().getNombre())) {
			controlador.realizarLasApuestas(this.jugadorActual);
			this.esperandoEntrada = false;
		}
	}

	private void menuApuestasDesiguales() {
		areaSalida.append("Seleccione una opcion:\n");
		areaSalida.append("1 - Fichar\n");
		areaSalida.append("2 - Pasar\n");
		this.estadoFlujo = Estados.MENU_APUESTAS_DESIGUALES;
	}

	public void menuApuestasDesiguales(String input) {
		if (esperandoEntrada) {
			switch (input.toLowerCase()) {
			case "1":
				areaSalida.append("Ha seleccionado la opcion de fichar\n");
				controlador.realizarFichaPostEnvite(this.jugadorActual);
				this.esperandoEntrada = false;
				break;
			case "2":
				areaSalida.append("Ha seleccionado la opcion de pasar\n");
				controlador.realizarPasarPostEnvite(this.jugadorActual);
				this.esperandoEntrada = false;
				break;
			default:
				areaSalida.append("Comando no reconocido. Intente nuevamente.\n");
				break;
			}
		}
	}

//----------------------------------------------------------------------------------------------------------
//SEGUNDA RONDA APUESTAS
//----------------------------------------------------------------------------------------------------------

	@Override
	public void mostrarMenuSegundaRondaApuestas() {
		this.setEnableCampoEntrada(true);
		areaSalida.append("Seleccione una opcion:\n");
		areaSalida.append("1 - Envitar\n");
		areaSalida.append("2 - Fichar\n");
		areaSalida.append("3 - Pasar\n");
		this.estadoFlujo = Estados.MENU_SEGUNDA_RONDA_APUESTAS;
	}

	private void menuSegundasApuestas(String input) {
		if (esperandoEntrada) {
			switch (input.toLowerCase()) {
			case "1":
				// ENVITAR
				areaSalida.append("Ingrese el valor de la apuesta.\n");
				this.estadoFlujo = Estados.ESPERANDO_ENVITE_SEGUNDA_RONDA;
				break;
			case "2":
				// FICHAR
				this.realizarFicheSegundaRonda();
				break;
			case "3":
				// PASAR
				this.realizarPaseSegundaRonda();
				break;
			default:
				areaSalida.append("Comando no reconocido. Intente nuevamente.\n");
				break;
			}
		}
	}

	private void realizarPaseSegundaRonda() {
		if (this.jugadorActual.getNombre().equals(controlador.getJugadorTurno().getNombre())) {
			controlador.realizarLosPasesSegundaRonda(this.jugadorActual);
			this.esperandoEntrada = false;
		}
	}

	private void relizarEnviteSegundaRonda(String input) {
		controlador.realizarLasApuestasSegundaRonda(this.jugadorActual, input);
		this.esperandoEntrada = false;
	}

	private void realizarFicheSegundaRonda() {
		if (this.jugadorActual.getNombre().equals(controlador.getJugadorTurno().getNombre())) {
			controlador.realizarLasApuestasSegundaRonda(this.jugadorActual);
			this.esperandoEntrada = false;
		}
	}

	private void mostrarMenuApuestasDesigualesSegundaRonda() {
		areaSalida.append("Seleccione una opcion:\n");
		areaSalida.append("1 - Fichar\n");
		areaSalida.append("2 - Pasar\n");
		this.estadoFlujo = Estados.MENU_APUESTAS_DESIGUALES_SEGUNDA_RONDA;
	}

	public void menuApuestasDesigualesSegundaRonda(String input) {
		if (esperandoEntrada) {
			switch (input.toLowerCase()) {
			case "1":
				areaSalida.append("Ha seleccionado la opcion de fichar\n");
				controlador.realizarFicharPostEnviteSegundaRonda(this.jugadorActual);
				this.esperandoEntrada = false;
				break;
			case "2":
				areaSalida.append("Ha seleccionado la opcion de pasar\n");
				controlador.realizarPasarPostEnviteSegundaRonda(this.jugadorActual);
				this.esperandoEntrada = false;
				break;
			default:
				areaSalida.append("Comando no reconocido. Intente nuevamente.\n");
				break;
			}
		}
	}

//----------------------------------------------------------------------------------------------------------
//DESCARTES
//----------------------------------------------------------------------------------------------------------

	@Override
	public void mostrarMenuDescartes() {
		this.setEnableCampoEntrada(true);
		areaSalida.append("Seleccione una opcion:\n");
		areaSalida.append("1 - Descartar\n");
		areaSalida.append("2 - No descartar\n");
		this.estadoFlujo = Estados.MENU_OPCION_DESCARTES;
	}

	public void menuOpcionDescarte(String input) throws RemoteException {
		if (esperandoEntrada) {
			switch (input.toLowerCase()) {
			case "1":
				mostrarMenuCartasDescartes();
				break;
			case "2":
				// JUGADOR DECIDE NO DESCARTAR
				controlador.continuarJuegoPostDescarte(this.jugadorActual);
				break;
			default:
				areaSalida.append("Comando no reconocido. Intente nuevamente.\n");
				break;
			}
		}
	}

	private void mostrarMenuCartasDescartes() throws RemoteException {
		LinkedList<Carta> cartasJugador = controlador.obtenerCartasJugador(this.jugadorActual);
		areaSalida.append("Seleccione las cartas a descartar.\n");
		int contador = 1;
		for (Carta c : cartasJugador) { 
			areaSalida.append(contador + "- " + c.getValor() + " de " + c.getPalo() + "\n");
			contador = contador + 1;
		}
		areaSalida.append("6- Dejar de descartar\n");
		this.estadoFlujo = Estados.MENU_SELECCION_DESCARTES;
	}

	private void menuEsperandoDescarte(String input) throws RemoteException {
		if (esperandoEntrada) {
			LinkedList<Carta> cartasJugador = controlador.obtenerCartasJugador(this.jugadorActual);
			switch (input.toLowerCase()) {

			case "1":
				areaSalida.append("Se selecciono " + cartasJugador.get(0).getValor() + " de "
						+ cartasJugador.get(0).getPalo() + "\n");
				controlador.cartaADescartar(0, this.jugadorActual);// AGREGA A LA COLA DE CARTAS A DESCARTAR INDICANDO
																	// LA POSICION y el jugador al que pertenecen
				this.estadoFlujo = Estados.MENU_SELECCION_DESCARTES;
				break;
			case "2":
				areaSalida.append("Se selecciono " + cartasJugador.get(1).getValor() + " de "
						+ cartasJugador.get(1).getPalo() + "\n");// AGREGA A LA COLA DE CARTAS A DESCARTAR INDICANDO LA
																	// POSICION y el jugador al que pertenecen
				controlador.cartaADescartar(1, this.jugadorActual);
				this.estadoFlujo = Estados.MENU_SELECCION_DESCARTES;
				break;
			case "3":
				areaSalida.append("Se selecciono " + cartasJugador.get(2).getValor() + " de "
						+ cartasJugador.get(2).getPalo() + "\n");// AGREGA A LA COLA DE CARTAS A DESCARTAR INDICANDO LA
																	// POSICION y el jugador al que pertenecen
				controlador.cartaADescartar(2, this.jugadorActual);
				this.estadoFlujo = Estados.MENU_SELECCION_DESCARTES;
				break;
			case "4":
				areaSalida.append("Se selecciono " + cartasJugador.get(3).getValor() + " de "
						+ cartasJugador.get(3).getPalo() + "\n");// AGREGA A LA COLA DE CARTAS A DESCARTAR INDICANDO LA
																	// POSICION y el jugador al que pertenecen
				controlador.cartaADescartar(3, this.jugadorActual);
				this.estadoFlujo = Estados.MENU_SELECCION_DESCARTES;
				break;
			case "5":
				areaSalida.append("Se selecciono " + cartasJugador.get(4).getValor() + " de "
						+ cartasJugador.get(4).getPalo() + "\n");// AGREGA A LA COLA DE CARTAS A DESCARTAR INDICANDO LA
																	// POSICION y el jugador al que pertenecen
				controlador.cartaADescartar(4, this.jugadorActual);
				this.estadoFlujo = Estados.MENU_SELECCION_DESCARTES;
				break;
			case "6":
				// CONTINUAR EL JUEGO
				controlador.continuarJuegoPostDescarte(this.jugadorActual);
				this.esperandoEntrada = false;
				break;
			default:
				areaSalida.append("Comando no reconocido. Intente nuevamente.\n");
				break;
			}
		}
	}

//----------------------------------------------------------------------------------------------------------
//NOTIFICACIONES
//----------------------------------------------------------------------------------------------------------

	@Override
	public void informarJugadoresInsuficientes() {
		areaSalida.append("La cantidad de jugadores es insuficiente para iniciar el juego.\n");
	}

	@Override
	public void informarCantJugadoresExcedidos() {
		areaSalida.append("La cantidad de jugadores excede el limite permitido.\n");
		areaSalida.append("Ingrese su nombre\n");
		this.estadoFlujo = Estados.SOLICITAR_NOMBRE_JUGADOR;
	}

	@Override
	public void informarFondosInsuficientes() {
		if (this.jugadorActual.getNombre().equals(controlador.getJugadorTurno().getNombre())) {
			areaSalida.append("Fondos insuficientes para realizar la apuesta.\n");
		}
	}

	@Override
	public void informarApuestaRealizada(String nombre, int apuestaJugador) {
		areaSalida.append(nombre + " ha realizado su apuesta: " + apuestaJugador + ".\n");
	}

	@Override
	public void informarTurnoApuestaOtroJugador(String nombreJugadorTurno) {
		this.setEnableCampoEntrada(false);
		areaSalida.append("Esperando a que " + nombreJugadorTurno + " realice su apuesta\n");
	}

	@Override
	public void notificarApuestasDesiguales() {
		this.setEnableCampoEntrada(true);
		areaSalida.append("Hay desigualdad entre las apuestas, por favor iguales el valor de la apuesta maxima.\n");
		menuApuestasDesiguales();
	}

	@Override
	public void notificarApuestasDesigualesSegundaRonda() {
		this.setEnableCampoEntrada(true);
		areaSalida.append("Hay desigualdad entre las apuestas, por favor iguales el valor de la apuesta maxima.\n");
		mostrarMenuApuestasDesigualesSegundaRonda();
	}

	@Override
	public void notificarJugadorIgualaApuesta() {
		areaSalida.append("Jugador iguala la apuesta y sigue en el juego.\n");
	}

	@Override
	public void notificarJugadorPasaApuesta(String nombreJugadorPasaApuesta) {

		areaSalida.append(nombreJugadorPasaApuesta + " pasa y queda fuera del juego.\n");
	}

	@Override
	public void notificarEsperarJugadorIgualeApuesta() {
		this.setEnableCampoEntrada(false);
		areaSalida.append("esperando a que se igualen las apuestas.\n");
	}

	@Override
	public void notificarRondaApuestaFinalizada() {
		this.setEnableCampoEntrada(true);
		areaSalida.append("Apuestas igualadas.\n");
	}

	@Override
	public void notificarApuestaMenorALaAnterior() {
		areaSalida.append("La apuesta no puede ser menor a la apuesta anterior.\n");
		this.mostrarMenuApuestas();
	}

	@Override
	public void notificarEsperarDescartes(String nombreJugadorTurnoDescarte) {
		this.setEnableCampoEntrada(false);
		areaSalida.append("Esperando a que " + nombreJugadorTurnoDescarte + " realice los descartes.\n");
	}

	@Override
	public void notificarErrorIntentarDescarteEnUnaCartaYaDescartada() {
		areaSalida.append("No se puede descartar una carta previamente descartada.\n");
	}

	@Override
	public void notificarCartaDescartadaConExito() {
		areaSalida.append("Carta descartada con exito.\n");
	}

	@Override
	public void notificarGanadorUnicoEnMesa(String nombre) {
		areaSalida.append(nombre + " es el ganador debido a que todos se rindieron.\n");
	}

	@Override
	public void notificarJugadorManoDebeApostar() {
		areaSalida.append("El jugador mano debe apostar obligatoriamente.\n");
	}

	@Override
	public void notificarErrorIngreseUnEntero() {
		areaSalida.append("¡Error, ingrese un numero entero!.\n");
		if (this.estadoFlujo.equals(Estados.ESPERANDO_ENVITE)) {
			mostrarMenuApuestas();
		} else {
			mostrarOpcionesMenu();
		}
	}

	private void notificarFondosAgregados() {
		mostrarOpcionesMenu();
	}

	private void notificarFondosAgregados2() {
		mostrarOpcionesMenuEmpezarOtraRonda();
	}

	@Override
	public void actualizarTablaJugadores(List<Jugador> jugadores) {
		areaSalida.append("\n");
		areaSalida.append("Lista de jugadores y fondos actualizados: \n");
		for (Jugador j : jugadores) {
			areaSalida.append("- Nombre - " + j.getNombre() + " - Fondos - " + j.getFondo() + "\n");
		}
		areaSalida.append("\n");
	}

	@Override
	public void mostrarNombreDelJugadorVentana() {
		setTitle("Poker - " + this.jugadorActual.getNombre());
	}

	@Override
	public void limpiarNotificaciones() {
		areaSalida.setText("");
	}

	@Override
	public void notificarErrorIngreseUnEnteroSegundaRonda() {
		areaSalida.append("Error, ingrese un numero entero positivo\n");
	}

	@Override
	public void notificarApuestaMenorALaAnteriorSegundaRonda() {
		areaSalida.append("Error, la apuesta no puede ser menor a la anterior.\n");
	}

	@Override
	public void informarFondosInsuficientesSegundaRonda() {
		areaSalida.append("Error, fondos insuficientes para realzar la apuesta.\n");
	}

	@Override
	public void notificarErrorIngreseUnEnteroAgregandoNuevosFondos() {
		areaSalida.append("¡Error, ingrese un numero entero!.\n");
	}

	@Override
	public void jugadorPasaQuedaFuera() {
		areaSalida.append("Espere a que termine el juego.\n");
	}

	@Override
	public void mostrarFondosInsuficientesParaComenzar() {
		areaSalida.append("Error, fondos insuficientes para comenzar una partida.\n");
	}

	@Override
	public void mostrarFondosInsuficientesParaComenzarPostPrimerPartido() {
		areaSalida.append("Error, fondos insuficientes para comenzar una partida.\n");
	}

	@Override
	public void informarJugadoresInsuficientesPostPrimerPartido() {
		areaSalida.append("Error, jugadores insuficientes para comenzar una partida.\n");
	}

	@Override
	public void mostrarErrorJugadoresInsuficientes() {
		areaSalida.append("Error, jugadores insuficientes para comenzar una partida.\n");
	}

	@Override
	public void mostrarMenuPrincipal() {
		this.mostrarOpcionesMenu();
	}

	@Override
	public void mostrarErrorSalidaJugador() {
		areaSalida.append("Error, jugador sale de la partida.\n");
	}

	@Override
	public void notificarErrorMaximaLongitudFondos() {
		areaSalida.append("Error, los fondos del jugador no pueden superar los seis digitos.\n");
	}

	@Override
	public void mostrarCartasJugadorAntGanador(List<Jugador> finalistas) {
		for (Jugador j : finalistas) {
			areaSalida.append(j.getNombre() + ", Cartas: \n");
			for (Carta c : j.getCartas()) {
				areaSalida.append("       " + c.toString() + "\n");
			}
		}
	}

	@Override
	public void notificarPartidaComenzada() {
		areaSalida.append("Error, partida ya comenzada\n");
		areaSalida.append("Ingrese su nombre\n");
		this.estadoFlujo = Estados.SOLICITAR_NOMBRE_JUGADOR;
	}

	@Override
	public void pasarVistaMenu() {
		setTitle("Poker");
		areaSalida.append("¡Bienvenido, " + this.nombreJugadorActual + "!\n");
		esperandoEntrada = false;
		mostrarOpcionesMenu();
	}

	@Override
	public void notificarErrorNombre() {
		// TODO Auto-generated method stub
		
	}

}