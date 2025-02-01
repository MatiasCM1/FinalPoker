package ar.edu.unlu.poker.controlador;

import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import ar.edu.unlu.poker.modelo.Carta;
import ar.edu.unlu.poker.modelo.IMesa;
import ar.edu.unlu.poker.modelo.Informe;
import ar.edu.unlu.poker.modelo.Jugador;
import ar.edu.unlu.poker.vista.IVista;
import ar.edu.unlu.poker.vista.consola.Estados;
import ar.edu.unlu.rmimvc.cliente.IControladorRemoto;
import ar.edu.unlu.rmimvc.observer.IObservableRemoto;

public class Controlador implements IControladorRemoto{
	
	private IVista vista;
	private IMesa mesa;
	private Jugador jugadorActual;

	@Override
	public void actualizar(IObservableRemoto modelo, Object cambio) throws RemoteException {
		switch((Informe) cambio) {
			case JUGADOR_NUEVO_AGREGADO:
				vista.actualizarTablaJugadores(this.getJugadoresMesa());
				break;
			case ESTABLECER_NOMBRE_VENTANA_JUGADOR:
				vista.mostrarNombreDelJugadorVentana();
				break;
			case JUGADOR_MANO:
				vista.mostrarJugadorMano((getJugadorMano()));
				break;
			case CARTAS_REPARTIDAS:
				vista.mostrarCartasJugador(((IMesa)modelo).getJugadoresMesa());
				break;
			case CANT_JUGADORES_INSUFICIENTES:
				vista.informarJugadoresInsuficientes();
				break;
			case CANT_JUGADORES_EXCEDIDOS:
				vista.informarCantJugadoresExcedidos();
				break;
			case DEVOLVER_GANADOR:
				Jugador ganador = ((IMesa)modelo).devolverGanador(); 
				if (this.isJugadorTurno()) {
					mesa.darFondosGanador(ganador);
				}
				vista.mostrarGanador(ganador);
				vista.mostrarOpcionesMenuEmpezarOtraRonda();
				break;
			case TURNO_APUESTA_JUGADOR:
				if (this.isJugadorTurno()) {
					vista.setEnableCampoEntrada(true);
					vista.mostrarMenuApuestas();
				} else {
					vista.setEnableCampoEntrada(false);
					vista.informarTurnoApuestaOtroJugador();
				}
				break;
			case FONDO_INSUFICIENTE:
				if (this.isJugadorTurno()) {
					vista.informarFondosInsuficientes();
				}
				break;
			case APUESTA_REALIZADA:
				vista.informarApuestaRealizada(this.getJugadorTurno().getNombre(), getJugadorTurnoJugadoresMesa().getApuesta());
				break;
			case INFORMAR_NO_TURNO:
				vista.informarNoTurno();
				break;
			case APUESTAS_DESIGUALES:
				if (mesa.perteneceJugadorApuestaMenor(this.jugadorActual)) { //Comprueba que el nombre del jugadorActuañ forme parte de la cola de jugadores con  apuesta menor a la mayor
					vista.setEnableCampoEntrada(true); //ESTO SE PUEDE PONER DENTRO DEL NOTIFICAR APUESTAS DESIGUALES DE LA VISTA CONSOLA
					vista.notificarApuestasDesiguales();
				} else {
					vista.setEnableCampoEntrada(false);
					vista.notificarEsperarJugadorIgualeApuesta();
				}
				break;
			case APUESTAS_DESIGUALES_SEGUNDA_RONDA:
				if (mesa.perteneceJugadorApuestaMenor(this.jugadorActual)) { //Comprueba que el nombre del jugadorActuañ forme parte de la cola de jugadores con  apuesta menor a la mayor
					vista.setEnableCampoEntrada(true);
					vista.notificarApuestasDesigualesSegundaRonda();
				} else {
					vista.setEnableCampoEntrada(false);
					vista.notificarEsperarJugadorIgualeApuesta();
				}
				break;
			case JUGADOR_IGUALA_APUESTA:
				vista.notificarJugadorIgualaApuesta();
				break;
			case JUGADOR_PASA_APUESTA:
				vista.notificarJugadorPasaApuesta(); //DEBERIA DECIR EL NOMBRE DEL JUGADOR
				break;
			case TURNO_DESCARTE:
				if (isJugadorTurno()) {
					vista.setEnableCampoEntrada(true);
					vista.mostrarMenuDescartes();//LLAMO AL MENU DE DESCARTE
				} else {
					vista.setEnableCampoEntrada(false);
					vista.notificarEsperarDescartes();
				}
				break;
			case RONDA_APUESTAS_TERMINADA:
				if (mesa.getRondaApuesta().size() == 1) {
					vista.notificarGanador(mesa.getRondaApuesta().getFirst().getNombre());
					if (this.isJugadorTurno()) {
						mesa.darFondosGanador(mesa.getRondaApuesta().getFirst());
					}
					vista.setEnableCampoEntrada(true);
					vista.mostrarOpcionesMenuEmpezarOtraRonda();
				} else if (this.isJugadorTurno()) {
					vista.setEnableCampoEntrada(true);
					vista.notificarRondaApuestaFinalizada();
					mesa.mirarSiDevolverResultados();
				}
				break;
			case RONDA_APUESTAS_TERMINADA_SEGUNDA_RONDA:
				if (mesa.getRondaApuestaAux().size() == 1) {
					vista.notificarGanador(mesa.getRondaApuestaAux().getFirst().getNombre());
					if (this.isJugadorTurno()) {
						mesa.darFondosGanador(mesa.getRondaApuesta().getFirst());
					}
					vista.setEnableCampoEntrada(true);
					vista.mostrarOpcionesMenuEmpezarOtraRonda();
				} else if (this.isJugadorTurno()) {
					vista.setEnableCampoEntrada(true);
					vista.notificarRondaApuestaFinalizada();
					mesa.mirarSiDevolverResultados();
				}
				break;
			case CARTA_DESCARTADA:
				if (isJugadorTurno()) {
					vista.notificarCartaDescartadaConExito();
				}
				break;
			case CARTA_YA_HABIA_SIDO_DESCARTADA:
				if (isJugadorTurno()) {
					vista.notificarErrorIntentarDescarteEnUnaCartaYaDescartada();
				}
				break;
			case SEGUNDA_RONDA_APUESTAS:
				if (mesa.getRondaApuesta().size() == 1) {
					vista.notificarGanador(mesa.getRondaApuesta().getFirst().getNombre());
					if (this.isJugadorTurno()) {
						mesa.darFondosGanador(mesa.getRondaApuesta().getFirst());
					}
					vista.setEnableCampoEntrada(true);
					vista.mostrarOpcionesMenuEmpezarOtraRonda();
				} else {
					if (this.isJugadorTurno()) {
						vista.setEnableCampoEntrada(true);
						vista.mostrarMenuSegundaRondaApuestas();
					} else {
						vista.setEnableCampoEntrada(false);
						vista.informarTurnoApuestaOtroJugador();
					}
				}
				break;
			case JUGADOR_SE_RETIRA:
				vista.actualizarTablaJugadores(this.getJugadoresMesa());
				break;
		}
		
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
		try {
			mesa.agregarJugador(j);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public List<Jugador> getJugadoresMesa(){
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
	
	public List<Jugador> obtenerJugadores(){
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
	
	public void jugadorSeRetiraDelJuego(Jugador jugador) {
		try {
			mesa.removerObservador(this);
			
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
			if (this.validarEntero(input)) {
				int apuesta = Integer.parseInt(input);
				if (this.jugadorManoEnvita(jugador, apuesta)) {//OBLIGA AL JUGADOR MANO A REALIZAR UNA APUESTA, IMPIDIENDO QUE FICHE O PASE
					if (apuesta >= mesa.getApuestaMayor()) {
						mesa.realizarApuesta(jugador, apuesta);
					} else {
						if (this.jugadorActual.getNombre().equals(this.getJugadorTurno().getNombre())) {
							vista.notificarApuestaMenorALaAnterior();
							//vista.mostrarMenuApuestas(); //ESTO NO ME SIRVE PARA LA VISTA GRAFICA
						} else {
							vista.informarTurnoApuestaOtroJugador();
						}
					}
				} else {
					vista.notificarJugadorManoDebeApostar();
					//vista.mostrarMenuApuestas(); //ESTO NO ME SIRVE PARA LA VISTA GRAFICA
				}
			} else {
				vista.notificarErrorIngreseUnEntero();
				//vista.mostrarMenuApuestas(); //ESTO NO ME SIRVE PARA LA VISTA GRAFICA
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	private boolean jugadorManoEnvita(Jugador jugador, int apuesta) throws RemoteException {
		if (jugador.equals(mesa.getJugadorMano()) && apuesta == 0) {//OBLIGA AL JUGADOR MANO A REALIZAR UNA APUESTA, IMPIDIENDO QUE FICHE O PASE
			return false;
		}
		return true;
	}
	
	public void realizarLasApuestas(Jugador jugador) {
		try {
			if (this.jugadorManoEnvita(jugador, mesa.getApuestaMayor())) {//OBLIGA AL JUGADOR MANO A REALIZAR UNA APUESTA, IMPIDIENDO QUE FICHE O PASE
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
				vista.mostrarMenuApuestas();//SE PUEDE PONER ESTE METODO DENTRO DEL NOTIFICAR EN LA VISTA CONSOLA
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
			mesa.agregarCartasADescartar(posicionCarta, jugador);//AGREGO EL JUGADOR Y LA CARTA EN UN HASHMAP
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
			if (this.validarEntero(input)) {
				int apuesta = Integer.parseInt(input);
				if ((apuesta + getJugadorMesa(jugador).getApuesta()) >= (mesa.getApuestaMayor())) {
					mesa.realizarSegundaRondaApuesta(jugador, apuesta);
				} else {
					if (this.jugadorActual.getNombre().equals(this.getJugadorTurno().getNombre())) {
						vista.notificarApuestaMenorALaAnterior();
						vista.mostrarMenuSegundaRondaApuestas();
					} else {
						vista.informarTurnoApuestaOtroJugador();
					}
				}
			} else {
				vista.notificarErrorIngreseUnEntero();
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

	public boolean validarEntero(String input) {
		try {
			Integer.parseInt(input);
			return true;
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
		if (this.validarEntero(input)) {
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
		if (this.validarEntero(input)) {
			int fondoAgregar = Integer.parseInt(input);
			try {
				mesa.agregarNuevosFondos(jugador, fondoAgregar);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			vista.notificarErrorIngreseUnEntero();
			vista.mostrarOpcionesMenuEmpezarOtraRonda();
		}
	}
	
	//---------------------------------
	
	public boolean validarTextoNombre(String textoNombre) {
		if (textoNombre.equals("Ingrese su nombre de usuario") || textoNombre.trim().isEmpty() || !validarNombreNoRepetido(textoNombre)){
			return false;
		}
		return true;
	}

	public boolean validarTextoFondos(String textoFondos) {
		if (!validarEntero(textoFondos)) {
			return false;
		}
		return true;
	}

	public void iniciarSiEstaListo(Jugador jugadorActual) {
		try {
			mesa.marcarComoListoParaIniciar(jugadorActual);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		if (todosListo()) {
			this.iniciarGame();
		}
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

	public boolean verificarCantidadDeJugadores() {
		if (this.getJugadoresMesa().size() > 1) {
			return true;
		} else {
			return false;
		}
	}
	
}