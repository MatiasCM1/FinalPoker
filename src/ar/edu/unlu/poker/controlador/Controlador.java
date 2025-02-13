package ar.edu.unlu.poker.controlador;

import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import ar.edu.unlu.poker.modelo.Carta;
import ar.edu.unlu.poker.modelo.IMesa;
import ar.edu.unlu.poker.modelo.Informe;
import ar.edu.unlu.poker.modelo.Jugador;
import ar.edu.unlu.poker.serializacion.Serializador;
import ar.edu.unlu.poker.serializacion.stats.EstadisticasJugador;
import ar.edu.unlu.poker.vista.IVista;
import ar.edu.unlu.poker.vista.consola.Estados;
import ar.edu.unlu.rmimvc.cliente.IControladorRemoto;
import ar.edu.unlu.rmimvc.observer.IObservableRemoto;

public class Controlador implements IControladorRemoto {

	private IVista vista;
	private IMesa mesa;
	private Jugador jugadorActual;
	private boolean estoyEnVistaLogin = true;

	@Override
	public void actualizar(IObservableRemoto modelo, Object cambio) throws RemoteException {
		switch ((Informe) cambio) {
		case JUGADOR_NUEVO_AGREGADO:
			if (this.jugadorActual != null) {
				vista.actualizarTablaJugadores(this.getJugadoresMesa());
			}
			break;
		case ESTABLECER_NOMBRE_VENTANA_JUGADOR:
			if (!this.estoyEnVistaLogin) {
				vista.mostrarNombreDelJugadorVentana();
			}
			break;
		case JUGADOR_MANO:
			if (!this.estoyEnVistaLogin) {
				vista.mostrarJugadorMano((getJugadorMano()));
			}
			break;
		case CARTAS_REPARTIDAS:
			if (!this.estoyEnVistaLogin) {
				vista.mostrarCartasJugador(((IMesa) modelo).getJugadoresMesa());
			}
			break;
		case CANT_JUGADORES_INSUFICIENTES:
			if (!this.estoyEnVistaLogin) {
				vista.mostrarErrorJugadoresInsuficientes();
			}
			break;
		case CANT_JUGADORES_EXCEDIDOS:
			if (!this.estoyEnVistaLogin) {
				vista.informarCantJugadoresExcedidos();
			}
			break;
		case DEVOLVER_GANADOR:
			if (!this.estoyEnVistaLogin) {
				Jugador ganador = ((IMesa) modelo).devolverGanador();
				if (this.isJugadorTurno()) {
					this.registrarVictoria(ganador);
					mesa.darFondosGanador(ganador);
				}
				
				mesa.setComenzoPartida(false);
				
				vista.mostrarGanador(ganador);
				vista.mostrarOpcionesMenuEmpezarOtraRonda();
			}
			break;
		case TURNO_APUESTA_JUGADOR:
			if (!this.estoyEnVistaLogin) {
				if (this.jugadorSigueEnJuego(this.jugadorActual)) {
					if (this.isJugadorTurno()) {
						vista.setEnableCampoEntrada(true);
						vista.mostrarMenuApuestas();
					} else {
						vista.setEnableCampoEntrada(false);
						vista.informarTurnoApuestaOtroJugador(this.getJugadorTurno().getNombre());
					}
				}
			}
			break;
		case FONDO_INSUFICIENTE:
			if (!this.estoyEnVistaLogin) {
				if (this.isJugadorTurno()) {
					vista.informarFondosInsuficientes();
				}
			}
			break;
		case FONDO_INSUFICIENTE_SEGUNDA_RONDA:
			if (!this.estoyEnVistaLogin) {
				if (this.isJugadorTurno()) {
					vista.informarFondosInsuficientesSegundaRonda();
				}
			}
			break;
		case APUESTA_REALIZADA:
			if (!this.estoyEnVistaLogin) {
				vista.informarApuestaRealizada(this.getJugadorTurno().getNombre(), getJugadorTurnoJugadoresMesa().getApuesta());
			}
			break;
		case INFORMAR_NO_TURNO:
			if (!this.estoyEnVistaLogin) {
				vista.informarNoTurno(); // EST QUEDO OBSOLETO???
			}
			break;
		case APUESTAS_DESIGUALES:
			if (!this.estoyEnVistaLogin) {
				if (mesa.perteneceJugadorApuestaMenor(this.jugadorActual)) { // Comprueba que el nombre del jugadorActuañ
																			// forme parte de la cola de jugadores con apuesta menor a la mayor
					vista.setEnableCampoEntrada(true); // ESTO SE PUEDE PONER DENTRO DEL NOTIFICAR APUESTAS DESIGUALES DE LA VISTA CONSOLA
					
					
					vista.notificarApuestasDesiguales();
				} else {
					vista.setEnableCampoEntrada(false);
					vista.notificarEsperarJugadorIgualeApuesta();
				}
			}
			break;
		case APUESTAS_DESIGUALES_SEGUNDA_RONDA:
			if (!this.estoyEnVistaLogin) {
				if (mesa.perteneceJugadorApuestaMenor(this.jugadorActual)) { // Comprueba que el nombre del jugadorActual
																			// forme parte de la cola de jugadores con
																			// apuesta menor a la mayor
					vista.setEnableCampoEntrada(true);

					vista.notificarApuestasDesigualesSegundaRonda();
				} else {
					vista.setEnableCampoEntrada(false);
					vista.notificarEsperarJugadorIgualeApuesta();
				}
			}
			break;
		case JUGADOR_IGUALA_APUESTA:
			if (!this.estoyEnVistaLogin) {
				vista.notificarJugadorIgualaApuesta();
			}
			break;
		case JUGADOR_PASA_APUESTA:
			if (!this.estoyEnVistaLogin) {
				if (this.jugadorActual.getNombre().equals(this.getJugadorQuePaso().getNombre())) {
					vista.jugadorPasaQuedaFuera();
				}
				vista.notificarJugadorPasaApuesta(this.getJugadorQuePaso().getNombre());
			}
			break;
		case TURNO_DESCARTE:
			if (!this.estoyEnVistaLogin) {
				if (this.jugadorSigueEnJuego(this.jugadorActual)) {
					if (isJugadorTurno()) {
						vista.setEnableCampoEntrada(true);
						vista.mostrarMenuDescartes();// LLAMO AL MENU DE DESCARTE
					} else {
						vista.setEnableCampoEntrada(false);
						vista.notificarEsperarDescartes(this.getJugadorTurno().getNombre());
					}
				}
			}
			break;
		case RONDA_APUESTAS_TERMINADA:
			if (!this.estoyEnVistaLogin) {
				if (mesa.getRondaApuesta().size() == 1) {
					Jugador ganador = mesa.getRondaApuesta().getFirst();
					vista.notificarGanadorUnicoEnMesa(ganador.getNombre());
					if (this.isJugadorTurno()) {
						this.registrarVictoria(ganador);
						mesa.darFondosGanador(mesa.getRondaApuesta().getFirst());
					}
					
					mesa.setComenzoPartida(false);
					
					vista.setEnableCampoEntrada(true);

					vista.actualizarTablaJugadores(this.getJugadoresMesa());

					vista.mostrarOpcionesMenuEmpezarOtraRonda();
				

				} else if (this.isJugadorTurno()) {
					vista.setEnableCampoEntrada(true);
					vista.notificarRondaApuestaFinalizada();
					mesa.mirarSiDevolverResultados();
				}
			}
			break;
		case RONDA_APUESTAS_TERMINADA_SEGUNDA_RONDA:
			if (!this.estoyEnVistaLogin) {
				if (mesa.getRondaApuestaAux().size() == 1) {
					Jugador ganador = mesa.getRondaApuesta().getFirst();
					vista.notificarGanadorUnicoEnMesa(ganador.getNombre());
					if (this.isJugadorTurno()) {
						this.registrarVictoria(ganador);
						mesa.darFondosGanador(mesa.getRondaApuesta().getFirst());
					}
					
					mesa.setComenzoPartida(false);
					
					vista.setEnableCampoEntrada(true);
					vista.mostrarOpcionesMenuEmpezarOtraRonda();
				} else if (this.isJugadorTurno()) {
					vista.setEnableCampoEntrada(true);
					vista.notificarRondaApuestaFinalizada();
					mesa.mirarSiDevolverResultados();
				}
			}	
			break;
		case CARTA_DESCARTADA:
			if (!this.estoyEnVistaLogin) {
				if (isJugadorTurno()) {
					vista.notificarCartaDescartadaConExito(); //ESTO SE OUEDE QUITAR
				}
			}
			break;
		case CARTA_YA_HABIA_SIDO_DESCARTADA:
			if (!this.estoyEnVistaLogin) {
				if (isJugadorTurno()) {
					vista.notificarErrorIntentarDescarteEnUnaCartaYaDescartada(); //ESTO SE PUEDE QUITAR SI SACO LA OPCION DE LA CARTA DESCARTADA DEL MENU
				}
			}
			break;
		case SEGUNDA_RONDA_APUESTAS:
			if (!this.estoyEnVistaLogin) {
				if (mesa.getRondaApuesta().size() == 1) {
					Jugador ganador = mesa.getRondaApuesta().getFirst();
					vista.notificarGanadorUnicoEnMesa(ganador.getNombre());
					if (this.isJugadorTurno()) {
						this.registrarVictoria(ganador);
						mesa.darFondosGanador(mesa.getRondaApuesta().getFirst());
					}	
					
					mesa.setComenzoPartida(false);
					
					vista.setEnableCampoEntrada(true);
					vista.mostrarOpcionesMenuEmpezarOtraRonda();
				
				} else {
					if (this.jugadorSigueEnJuego(this.jugadorActual)) {
						if (this.isJugadorTurno()) {
							vista.setEnableCampoEntrada(true);
							vista.mostrarMenuSegundaRondaApuestas();
						} else {
							vista.setEnableCampoEntrada(false);
							vista.informarTurnoApuestaOtroJugador(this.getJugadorTurno().getNombre());
						}
					}
				}
			}
			break;
		case JUGADOR_SE_RETIRA:
			if (!this.estoyEnVistaLogin) {
				vista.actualizarTablaJugadores(this.getJugadoresMesa());
			}
			break;
		case JUGADOR_SE_RETIRA_EN_PARTIDA:
			if (!this.estoyEnVistaLogin) {
				vista.mostrarMenuPrincipal();
				vista.mostrarErrorSalidaJugador();
				vista.actualizarTablaJugadores(this.getJugadoresMesa());
			}
			break;
		case SE_AGREGAN_FONDOS:
			if (!this.estoyEnVistaLogin) {
				vista.actualizarTablaJugadores(this.getJugadoresMesa());
			}
			break;
		}

	}

	private boolean jugadorSigueEnJuego(Jugador jugador) {
		boolean flag = false;
		for (Jugador j : this.getJugadoresMesa()) {
			if (j.getNombre().equals(jugador.getNombre())) {
				flag = j.isEnJuego();
			}
		}
		return flag;
	}

	public Jugador getJugadorTurnoJugadoresMesa() {
		for (Jugador j : this.getJugadoresMesa()) {
			if (j.equals(this.getJugadorTurno())) {
				return j;
			}
		}
		return null;
	}

	private boolean isJugadorTurno() throws RemoteException {
		return this.jugadorActual.equals(this.getJugadorTurno());
	}

	public void iniciarGame() {
		try {
			mesa.setPrimeraRonda(true);
			mesa.iniciarJuego();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void iniciarGamePostPrimeraRonda() {
		try {
			mesa.setPrimeraRonda(false);
			mesa.iniciarJuego();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Controlador(IVista vista) {
		this.vista = vista;
	}

	public void agregarJugador(Jugador j) {
		if (this.getJugadoresMesa().size() < 7) {
			try {
				mesa.agregarJugador(j);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		} else {
			vista.informarCantJugadoresExcedidos();
		}
	}

	public List<Jugador> getJugadoresMesa() {
		try {
			return mesa.getJugadoresMesa();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Jugador> getListaOrdenadaJugadorMano() {
		try {
			return mesa.getJugadoresMesa();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Jugador> obtenerJugadores() {
		try {
			return mesa.getJugadoresMesa();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public <T extends IObservableRemoto> void setModeloRemoto(T modeloRemoto) throws RemoteException {
		this.mesa = (IMesa) modeloRemoto;
	}

	public Jugador getJugadorActual() {
		return jugadorActual;
	}

	public void setJugadorActual(Jugador jugadorActual) {
		this.jugadorActual = jugadorActual;
	}

	public Jugador getJugadorMano() throws RemoteException {
		return mesa.getJugadorMano();
	}

	public void jugadorCierraSesion(Jugador jugador) {
		try {
			mesa.removerJugadores(jugador);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			mesa.sacarJugador(jugador);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void jugadorSeRetiraDelJuego(Jugador jugador) {
		
		try {
			mesa.removerObservador(this);

			mesa.removerJugadores(jugador);
			

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void jugadorSeRetiraEnJuego(Jugador jugador) {
		try {
			mesa.removerObservador(this);
			mesa.removerJugadorSeRetiraEnJuego(jugador);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public Jugador getJugadorTurno() {
		try {
			return mesa.getJugadorTurno();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void realizarLasApuestas(Jugador jugador, String input) {
		try {
			if (this.validarEnteroPositivo(input)) {
				int apuesta = Integer.parseInt(input);
				if (this.jugadorManoEnvita(jugador, apuesta)) {// OBLIGA AL JUGADOR MANO A REALIZAR UNA APUESTA,
																// IMPIDIENDO QUE FICHE O PASE
					if (apuesta >= mesa.getApuestaMayor()) {
						mesa.realizarApuesta(jugador, apuesta);
					} else {
						if (this.jugadorActual.getNombre().equals(this.getJugadorTurno().getNombre())) {
							vista.notificarApuestaMenorALaAnterior();
							// vista.mostrarMenuApuestas(); //ESTO NO ME SIRVE PARA LA VISTA GRAFICA
						} else {
							vista.informarTurnoApuestaOtroJugador(this.getJugadorTurno().getNombre());
						}
					}
				} else {
					vista.notificarJugadorManoDebeApostar();
					// vista.mostrarMenuApuestas(); //ESTO NO ME SIRVE PARA LA VISTA GRAFICA
				}
			} else {
				vista.notificarErrorIngreseUnEntero();
				// vista.mostrarMenuApuestas(); //ESTO NO ME SIRVE PARA LA VISTA GRAFICA
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	private boolean jugadorManoEnvita(Jugador jugador, int apuesta) throws RemoteException {
		if (jugador.equals(mesa.getJugadorMano()) && apuesta == 0) {// OBLIGA AL JUGADOR MANO A REALIZAR UNA APUESTA,
																	// IMPIDIENDO QUE FICHE O PASE
			return false;
		}
		return true;
	}

	public void realizarLasApuestas(Jugador jugador) {
		try {
			if (this.jugadorManoEnvita(jugador, mesa.getApuestaMayor())) {// OBLIGA AL JUGADOR MANO A REALIZAR UNA
																			// APUESTA, IMPIDIENDO QUE FICHE O PASE
				mesa.realizarApuesta(jugador, mesa.getApuestaMayor());
			} else {
				vista.notificarJugadorManoDebeApostar();
				vista.mostrarMenuApuestas();
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void realizarLosPases(Jugador jugador) {
		try {
			if (!jugador.equals(mesa.getJugadorMano())) {
				mesa.jugadorPasa(jugador);
			} else {
				vista.notificarJugadorManoDebeApostar();
				vista.mostrarMenuApuestas();// SE PUEDE PONER ESTE METODO DENTRO DEL NOTIFICAR EN LA VISTA CONSOLA
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void realizarFichaPostEnvite(Jugador jugador) {
		try {
			mesa.jugadorFichaPostEnvite(jugador);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void realizarPasarPostEnvite(Jugador jugador) {
		try {
			mesa.jugadorPasaPostEnvite(jugador);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void realizarFicharPostEnviteSegundaRonda(Jugador jugador) {
		try {
			mesa.jugadorFichaPostEnviteSegundaRonda(jugador);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void realizarPasarPostEnviteSegundaRonda(Jugador jugador) {
		try {
			mesa.jugadorPasaPostEnviteSegundaRonda(jugador);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public LinkedList<Carta> obtenerCartasJugador(Jugador jugador) {
		try {
			for (Jugador j : mesa.getJugadoresMesa()) {
				if (j.equals(jugador)) {
					return j.getCartas();
				}
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void cartaADescartar(int posicionCarta, Jugador jugador) {
		try {
			mesa.agregarCartasADescartar(posicionCarta, jugador);// AGREGO EL JUGADOR Y LA CARTA EN UN HASHMAP
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void continuarJuegoPostDescarte(Jugador jugador) {
		try {
			mesa.realizarElDescarte(jugador);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Jugador getJugadorMesa(Jugador jugador) {
		for (Jugador j : this.getJugadoresMesa()) {
			if (j.equals(jugador)) {
				return j;
			}
		}
		return null;
	}

	public void realizarLasApuestasSegundaRonda(Jugador jugador, String input) {
		try {
			if (this.validarEnteroPositivo(input)) {
				int apuesta = Integer.parseInt(input);
				if ((apuesta + getJugadorMesa(jugador).getApuesta()) >= (mesa.buscarApuestaMayorEnElMapa())) {
					mesa.realizarSegundaRondaApuesta(jugador, apuesta);
				} else {
					if (this.jugadorActual.getNombre().equals(this.getJugadorTurno().getNombre())) {
						vista.notificarApuestaMenorALaAnteriorSegundaRonda();
						vista.mostrarMenuSegundaRondaApuestas();
					} else {
						vista.informarTurnoApuestaOtroJugador(this.getJugadorTurno().getNombre());
					}
				}
			} else {
				vista.notificarErrorIngreseUnEnteroSegundaRonda();
				vista.mostrarMenuSegundaRondaApuestas();
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void realizarLasApuestasSegundaRonda(Jugador jugador) {
		try {
			mesa.realizarSegundaRondaApuesta(jugador, mesa.getApuestaMayor());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void realizarLosPasesSegundaRonda(Jugador jugador) {
		try {
			mesa.jugadorPasaSegundaRonda(jugador);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean validarEnteroPositivo(String input) {
		try {
			Integer.parseInt(input);
			if (Integer.parseInt(input) > 0) {
				return true;
			} else {
				return false;
			}
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public boolean validarNombreNoRepetido(String input) {
		for (Jugador j : this.getJugadoresMesa()) {
			if (j.getNombre().equals(input)) {
				return false;
			}
		}
		return true;
	}

	public int getFondosJugador(Jugador jugador) {
		for (Jugador j : this.getJugadoresMesa()) {
			if (j.equals(jugador)) {
				return j.getFondo();
			}
		}
		return 0;
	}

	public void incrementarFondos(Jugador jugador, String input) {
		if (this.validarEnteroPositivo(input)) {
			int fondoAgregar = Integer.parseInt(input);
			try {
				mesa.agregarNuevosFondos(jugador, fondoAgregar);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			vista.notificarErrorIngreseUnEntero();
			vista.mostrarOpcionesMenu();
		}
	}

	public void incrementarFondos2(Jugador jugador, String input) {
		if (this.validarEnteroPositivo(input)) {
			int fondoAgregar = Integer.parseInt(input);
			try {
				mesa.agregarNuevosFondos(jugador, fondoAgregar);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			vista.notificarErrorIngreseUnEnteroAgregandoNuevosFondos();
			// vista.mostrarOpcionesMenuEmpezarOtraRonda();
		}
	}

	// ---------------------------------

	public boolean validarTextoNombre(String textoNombre) {
		if (textoNombre.equals("Ingrese su nombre de usuario") || textoNombre.trim().isEmpty()
				|| !validarNombreNoRepetido(textoNombre)) {
			return false;
		}
		return true;
	}

	public boolean validarTextoFondos(String textoFondos) {
		if (!validarEnteroPositivo(textoFondos)) {
			return false;
		}
		return true;
	}

	public void iniciarSiEstaListo(Jugador jugadorActual) {
		
		if (this.comprobarJugadoresSuficientes()) {
			if (this.tieneFondosSuficientes(jugadorActual)) {
				try {
					mesa.marcarComoListoParaIniciar(jugadorActual);
				} catch (RemoteException e) {
					e.printStackTrace();
				}

				vista.limpiarNotificaciones();

				if (todosListo()) {
					try {
						if (!mesa.isPrimeraRonda()) {
							this.iniciarGamePostPrimeraRonda();
						} else {
							this.iniciarGame();
						}
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				} 
			} else {
				vista.mostrarFondosInsuficientesParaComenzar();
			}
		} else {
			vista.informarJugadoresInsuficientes();
		}
	}
	
	public void iniciarSiEstaListo() {
		
		if (todosListo()) {
			try {
				if (!mesa.isPrimeraRonda()) {
					this.iniciarGamePostPrimeraRonda();
				} else {
					this.iniciarGame();
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void iniciarSiEstaListoPostPrimeraRonda() {
		
		if (todosListo()) {
			try {
				if (!mesa.isPrimeraRonda()) {
					this.iniciarGamePostPrimeraRonda();
				} else {
					this.iniciarGame();
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		} 
		
	}
	
	public void iniciarSiEstaListoPostPrimeraRonda(Jugador jugadorActual) {
		
		if (this.comprobarJugadoresSuficientes()) {
			if (this.tieneFondosSuficientes(jugadorActual)) {
				try {
					mesa.marcarComoListoParaIniciar(jugadorActual);
				} catch (RemoteException e) {
					e.printStackTrace();
				}

				vista.limpiarNotificaciones();

				if (todosListo()) {
					try {
						if (!mesa.isPrimeraRonda()) {
							this.iniciarGamePostPrimeraRonda();
						} else {
							this.iniciarGame();
						}
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				} 
			} else {
				vista.mostrarFondosInsuficientesParaComenzarPostPrimerPartido();
			}
		} else {
			vista.informarJugadoresInsuficientesPostPrimerPartido();
		}
	}
	
	private boolean comprobarJugadoresSuficientes() {
		return this.getJugadoresMesa().size() > 1;
	}

	public boolean tieneFondosSuficientes(Jugador jugador) {
		if (this.getFondosJugador(jugador) >= 1) {
			return true;
		}
		return false;
	}

	private boolean todosListo() {
		boolean flag = true;
		for (Jugador j : this.getJugadoresMesa()) {
			if (!j.getListoParaIniciar()) {
				flag = false;
			}
		}
		return flag;
	}
	
	public boolean comenzoPartida() {
		try {
			return mesa.getComenzoPartida();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public boolean verificarCantidadDeJugadores() {
		if (this.getJugadoresMesa().size() > 1) {
			return true;
		} else {
			return false;
		}
	}

	public void establecerJugadorComoNoListo(Jugador jugador) {
		try {
			mesa.marcarComoNoListoParaIniciar(jugador);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Jugador getJugadorQuePaso() {
		try {
			return mesa.getJugadorPasa();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jugadorActual;
	}
	
	private Jugador getJugadorActualMesa() {
		for (Jugador j : getJugadoresMesa()) {
			if (j.getNombre().equals(this.jugadorActual.getNombre())) {
				return j;
			}
		}
		return jugadorActual;
	}

	public void setEstoyEnVistaLogin(boolean estoyEnVistaLogin) {
		this.estoyEnVistaLogin = estoyEnVistaLogin;
	}
	
	public void registrarVictoria(Jugador jugadorGanador) {
		
		//Cargo estadisticas anteriores
		List<EstadisticasJugador> jugadores = Serializador.cargarEstadisticas();
		
		//Incremento o creo una estadistica para el ganador
		boolean flag = false;
		for (EstadisticasJugador j : jugadores) {
			if (j.getNombreJugador().equals(jugadorGanador.getNombre())) {
				j.incrementarCantidadPartidasGanadas();
				flag = true;
				break;
			}
		}
		//Si no se encuentra al jugador, creo una nueva estadisticas para este jugador
		if (!flag) {
			EstadisticasJugador nuevoJugador = new EstadisticasJugador(jugadorGanador.getNombre());
			nuevoJugador.incrementarCantidadPartidasGanadas();
			jugadores.add(nuevoJugador);
		}
		
		//Persistir los cambios
		Serializador.guardarEstadisticas(jugadores);
		
	}
	
	public List<EstadisticasJugador> obtenerTopJugadores(){
		
		List<EstadisticasJugador> jugadores = Serializador.cargarEstadisticas();
		
		//Ordenar lista
		jugadores.sort((a, b) -> Integer.compare(b.getCantPartidasGanadas(), a.getCantPartidasGanadas()));
		
		return jugadores.subList(0, Math.min(jugadores.size(), 10));
		
	}

}