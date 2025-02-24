package ar.edu.unlu.poker.vista.grafica;

import java.util.LinkedList;
import java.util.List;

import ar.edu.unlu.poker.controlador.Controlador;
import ar.edu.unlu.poker.modelo.Jugador;
import ar.edu.unlu.poker.vista.IVista;

public class VistaGrafica implements IVista {

	private Controlador controlador;
	private Jugador jugadorActual;
	private VistaLogin vistaLogin;
	private VistaMenuPrincipal vistaMenuPrincipal;
	private VistaJuegoCartas vistaJuegoCartas;
	private VistaApuestas vistaApuestas;
	private VistaApuestas2 vistaApuestas2;
	private VistaTop vistaTop;
	private String nombreJugadorMano;
	private static VistaGrafica instancia;

	private VistaGrafica() {

	}

	public static VistaGrafica getInstance() {
		if (instancia == null) {
			instancia = new VistaGrafica();
		}
		return instancia;
	}

	@Override
	public void iniciar() {

		this.vistaLogin = new VistaLogin();

		this.vistaLogin.setVisible(true);

	}

	public void setJugador(String nombre, int fondos) {
		this.jugadorActual = new Jugador(nombre, fondos);
	}
	
	public void agregarJugador() {
		this.controlador.setJugadorActual(jugadorActual);
		this.controlador.agregarJugador(jugadorActual);
	}
	
	public boolean validarTextoNombre(String textoNombre) {
		if (textoNombre.equals("Ingrese su nombre de usuario") || textoNombre.trim().isEmpty()) {
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

	@Override
	public void pasarVistaMenu() {

		this.controlador.setEstoyEnVistaLogin(false);
		this.vistaLogin.setVisible(false);
		this.vistaMenuPrincipal = new VistaMenuPrincipal();
		this.vistaMenuPrincipal.setVisible(true);

		this.actualizarTablaJugadores(getJugadoresMesa());

	}

	@Override
	public void informarCantJugadoresExcedidos() {
		if (this.vistaMenuPrincipal != null) {
			this.vistaMenuPrincipal.setVisible(false);
		}
		this.vistaLogin.setVisible(true);
		this.vistaLogin.mostrarErrorCantidadMaximaJugadores();
	}

	@Override
	public void mostrarErrorJugadoresInsuficientes() {
		if (this.vistaJuegoCartas != null) {
			this.vistaJuegoCartas.setVisible(false);
		}

		if (this.vistaApuestas != null) {
			this.vistaApuestas.setVisible(false);
		}

		if (this.vistaApuestas2 != null) {
			this.vistaApuestas2.setVisible(false);
		}

		this.vistaMenuPrincipal.setVisible(true);

		this.vistaMenuPrincipal.mostrarErrorJugadoresInsuficientes();

	}

	public void volverPantallaLogin() {

		this.controlador.setEstoyEnVistaLogin(true);

		this.vistaMenuPrincipal.setVisible(false);
		this.vistaLogin.setVisible(true);

		controlador.jugadorCierraSesion(this.jugadorActual);

		controlador.iniciarSiEstaListo();

	}

	public void jugadorSaleDelJuego() {
		controlador.jugadorSeRetiraDelJuego(this.jugadorActual);
		controlador.iniciarSiEstaListo();
	}

	public void jugadorSaleDelJuegoPostPrimeraPartida() {
		//if (!this.comprobarPartidaComenzada()) {
			controlador.jugadorSeRetiraDelJuego(this.jugadorActual);
		//} else {
			controlador.jugadorSeRetiraConJuegoComenzado(this.jugadorActual);
		//}
	}

	@Override
	public void mostrarMenuPrincipal() {

		if (this.jugadorActual == null || !controlador.getJugadoresMesa().contains(this.jugadorActual)) {
			return;
		}

		if (this.vistaJuegoCartas != null) {
			this.vistaJuegoCartas.setVisible(false);
			this.vistaJuegoCartas = new VistaJuegoCartas();
		}

		if (this.vistaApuestas != null) {
			this.vistaApuestas.setVisible(false);
			this.vistaApuestas = new VistaApuestas();
		}

		if (this.vistaApuestas2 != null) {
			this.vistaApuestas2.setVisible(false);
			this.vistaApuestas2 = new VistaApuestas2();
		}

		if (this.vistaTop != null) {
			this.vistaTop.setVisible(false);
			this.vistaTop = new VistaTop();
		}

		this.vistaMenuPrincipal.setVisible(true);

	}

	@Override
	public void mostrarErrorSalidaJugador() {
		this.vistaMenuPrincipal.mostrarErrorJugadorSaleDeLaJuego();
	}

	public void listoParaIniciarJuego() {

		this.vistaMenuPrincipal.setVisible(false);

		if (this.vistaJuegoCartas == null) {
			this.vistaJuegoCartas = new VistaJuegoCartas();
		}

		this.vistaJuegoCartas.setVisible(true);

		controlador.iniciarSiEstaListo(this.jugadorActual);

	}

	@Override
	public void mostrarFondosInsuficientesParaComenzar() {
		if (this.vistaMenuPrincipal != null) {
			this.vistaMenuPrincipal.setVisible(true);
			if (this.vistaJuegoCartas != null) {
				this.vistaJuegoCartas.setVisible(false);
			}
			this.vistaMenuPrincipal.mostrarErrorFondosInsuficientesParaComenzar();
		}

	}

	public void listoParaIniciarJuegoPostPrimerPartido() {

		this.vistaMenuPrincipal.setVisible(false);

		if (this.vistaJuegoCartas == null) {
			this.vistaJuegoCartas = new VistaJuegoCartas();
		}

		this.vistaJuegoCartas.setVisible(true);

		controlador.iniciarSiEstaListoPostPrimeraRonda(this.jugadorActual);

	}

	@Override
	public void mostrarFondosInsuficientesParaComenzarPostPrimerPartido() {
		if (this.vistaJuegoCartas != null) {
			this.vistaJuegoCartas.mostrarErrorFondosInsuficientesParaSeguirJugando();
		}
	}

	public Jugador getJugadorActual() {
		return this.jugadorActual;
	}

	@Override
	public void setControlador(Controlador controlador) {
		this.controlador = controlador;
	}

	public List<Jugador> getJugadoresMesa() {
		return controlador.getJugadoresMesa();
	}

	@Override
	public void actualizarTablaJugadores(List<Jugador> jugadores) {
		if (controlador.verificarCantidadDeJugadores()) {
			this.actualizarJugadorActual(jugadores);
			List<Jugador> jugardoresSinActual = this.listaSinJugadorActualizarTabla(jugadores);
			this.vistaMenuPrincipal.actualizarTabla(jugardoresSinActual);
		} else {
			this.vistaMenuPrincipal.limpiarTablaJugadores();
			this.actualizarJugadorActual(jugadores);
		}
	}

	private void actualizarJugadorActual(List<Jugador> jugadores) {
		for (Jugador j : jugadores) {
			if (j.getNombre().equals(this.jugadorActual.getNombre())) {
				this.vistaMenuPrincipal.actualizarJugadorVista(j);
			}
		}
	}

	private List<Jugador> listaSinJugadorActualizarTabla(List<Jugador> jugadores) {
		List<Jugador> listaResultado = new LinkedList<Jugador>();
		for (Jugador j : jugadores) {
			if (!j.getNombre().equals(this.jugadorActual.getNombre())) {
				listaResultado.add(j);
			}
		}
		return listaResultado;
	}

	@Override
	public void mostrarJugadorMano(Jugador jugador) {

		if (this.vistaJuegoCartas != null) {
			this.vistaJuegoCartas.escribirNotificacion("Jugador mano: " + jugador.getNombre());
		}

	}

	@Override
	public void mostrarCartasJugador(List<Jugador> jugadores) {

		if (this.vistaJuegoCartas != null) {
			for (Jugador jugador : jugadores) {
				if (jugador.equals(jugadorActual)) {
					this.jugadorActual.setListaCartas(jugador.getCartas());
					this.vistaJuegoCartas.mostrarCartas(jugador.getCartas());
				}
			}
		}

	}

	@Override
	public void mostrarMenuApuestas() {

		this.vistaJuegoCartas.setVisible(false);

		if (this.vistaApuestas == null) {
			this.vistaApuestas = new VistaApuestas();
		}

		this.vistaApuestas.setVisible(true);

		this.vistaApuestas.informarJugadorMano(this.nombreJugadorMano);

		this.mostrarNombreDelJugadorVentanaApuestas();

		this.vistaApuestas.escribirNotificaciones(this.vistaJuegoCartas.getNotificaciones());

		this.vistaApuestas.mostrarCartas(jugadorActual.getCartas());

	}

	public void realizarEnvite(String apuesta) {
		controlador.realizarLasApuestas(jugadorActual, apuesta);
	}

	public void realizarPase() {
		controlador.realizarLosPases(this.jugadorActual);
	}

	public void realizarFiche() {
		controlador.realizarLasApuestas(this.jugadorActual);
	}

	@Override
	public void informarApuestaRealizada(String nombreJugadorAposto, int apuestaJugador) {

		if (this.jugadorActual.getNombre().equals(nombreJugadorAposto)) {
			this.vistaApuestas.setVisible(false);
			if (this.vistaApuestas2 != null) {
				this.vistaApuestas2.setVisible(false);
			}
			this.vistaJuegoCartas.setVisible(true);

		}
		this.vistaJuegoCartas.escribirNotificacion(
				nombreJugadorAposto + " realizo una apuesta de " + String.valueOf(apuestaJugador));
	}

	@Override
	public void mostrarNombreDelJugadorVentana() {
		this.vistaJuegoCartas.setearNombreEnLaBarra(this.jugadorActual.getNombre());
	}

	private void mostrarNombreDelJugadorVentanaApuestas() {
		this.vistaApuestas.setearNombreEnLaBarra(this.jugadorActual.getNombre());
	}

	@Override
	public void notificarErrorIngreseUnEntero() {
		this.vistaApuestas.mostrarMenuEnvite();
		this.vistaApuestas.mostrarErrorNumeroEntero();
	}

	@Override
	public void notificarErrorMaximaLongitudFondos() {
		this.vistaMenuPrincipal.mostrarErrorMaximaLogitudNumeroFondos();
	}

	@Override
	public void notificarApuestaMenorALaAnterior() {
		this.vistaApuestas.mostrarMenuEnvite();
		this.vistaApuestas.mostrarErrorApuestaInsuficiente();
	}

	@Override
	public void informarFondosInsuficientes() {

		this.vistaApuestas.setVisible(true);
		this.vistaApuestas.mostrarErrorFondosInsuficientes();

	}

	@Override
	public void notificarApuestasDesiguales() {

		this.vistaJuegoCartas.escribirNotificacion(
				"Hay desigualdad entre las apuestas, por favor iguales el valor de la apuesta maxima");

		this.vistaJuegoCartas.setVisible(false);

		this.vistaApuestas.setVisible(true);

		this.vistaApuestas.escribirNotificaciones(this.vistaJuegoCartas.getNotificaciones());

		this.vistaApuestas.mostrarMenuApuestaDesigual();

	}

	@Override
	public void notificarEsperarJugadorIgualeApuesta() {

		this.vistaJuegoCartas.escribirNotificacion("Esperando a que se igualen las apuestas");

	}

	public void realizarFichePostEnvite() {

		this.controlador.realizarFichaPostEnvite(this.jugadorActual);

	}

	@Override
	public void notificarJugadorIgualaApuesta() {

		this.vistaJuegoCartas.escribirNotificacion("Jugador iguala la apuesta y sigue en el juego");

	}

	public void realizarPasarPostEnvite() {

		this.controlador.realizarPasarPostEnvite(this.jugadorActual);

	}

	@Override
	public void notificarJugadorManoDebeApostar() {
		this.vistaApuestas.setVisible(true);
		this.vistaApuestas.mostrarErrorJugadorManoDebeEnvitar();
	}

	@Override
	public void notificarJugadorPasaApuesta(String nombreJugadorQuePaso) {
		this.vistaJuegoCartas.escribirNotificacion(nombreJugadorQuePaso + " pasa y queda fuera del juego");
	}

	@Override
	public void jugadorPasaQuedaFuera() {
		this.vistaApuestas.setVisible(false);
		if (this.vistaApuestas2 != null) {
			this.vistaApuestas2.setVisible(false);
		}
		this.vistaJuegoCartas.setVisible(true);
	}

	@Override
	public void notificarGanadorUnicoEnMesa(String nombre) {

		this.vistaApuestas.setVisible(false);
		if (this.vistaApuestas2 != null) {
			this.vistaApuestas2.setVisible(false);
		}
		this.vistaJuegoCartas.setVisible(true);
		this.vistaJuegoCartas.escribirNotificacion("El ganador es " + nombre + " debido a que todos se retiraron");

	}

	@Override
	public void mostrarOpcionesMenuEmpezarOtraRonda() {
		if (this.vistaApuestas != null) {
			this.vistaApuestas.mostrarBotonesApuesta();
		}
		if (this.vistaApuestas2 != null) {
			this.vistaApuestas2.mostrarBotonesApuesta();
		}
		controlador.establecerJugadorComoNoListo(this.jugadorActual);
		this.vistaJuegoCartas.mostrarOpcionesParaNuevaRondaJuego();
	}

	public void volverMenuPrincipal() {

		if (this.vistaJuegoCartas != null) {
			this.vistaJuegoCartas.setVisible(false);
		}

		if (this.vistaTop != null) {
			this.vistaTop.setVisible(false);
		}

		controlador.establecerJugadorComoNoListo(this.jugadorActual);
		this.actualizarTablaJugadores(getJugadoresMesa());

		this.vistaMenuPrincipal.setVisible(true);

	}

	@Override
	public void limpiarNotificaciones() {
		this.vistaJuegoCartas.limpiarPantalla();
	}

	@Override
	public void mostrarMenuDescartes() {
		this.vistaApuestas.setVisible(false);
		this.vistaApuestas.mostrarBotonesApuesta();
		this.vistaJuegoCartas.setVisible(true);
		this.vistaJuegoCartas.mostrarMenuDescartes();
	}

	@Override
	public void notificarEsperarDescartes(String nombreJugadorTurnoDescarte) {
		this.vistaJuegoCartas
				.escribirNotificacion("Esperando a que " + nombreJugadorTurnoDescarte + " realice los descartes");
	}

	public void cartaADescartarSeleccionada(int posicionCarta) {
		this.controlador.cartaADescartar(posicionCarta, jugadorActual);
	}

	public void finalizarFaseDescarte() {
		this.controlador.continuarJuegoPostDescarte(this.jugadorActual);
	}

	@Override
	public void informarTurnoApuestaOtroJugador(String nombreJugadorTurno) {
		this.vistaJuegoCartas.escribirNotificacion("Esperando a que " + nombreJugadorTurno + " realice su apuesta");
	}

	@Override
	public void mostrarMenuSegundaRondaApuestas() {
		this.vistaJuegoCartas.setVisible(false);

		if (this.vistaApuestas2 == null) {
			this.vistaApuestas2 = new VistaApuestas2();
		}

		this.vistaApuestas2.setVisible(true);

		this.mostrarNombreDelJugadorVentanaApuestas2();

		this.vistaApuestas2.escribirNotificaciones(this.vistaJuegoCartas.getNotificaciones());

		this.vistaApuestas2.mostrarCartas(jugadorActual.getCartas());
	}

	private void mostrarNombreDelJugadorVentanaApuestas2() {
		this.vistaApuestas2.setearNombreEnLaBarra(this.jugadorActual.getNombre());
	}

	public void realizarEnvitarSegundaRonda(String apuesta) {
		this.controlador.realizarLasApuestasSegundaRonda(this.jugadorActual, apuesta);
	}

	public void realizarFicharSegundaRonda() {
		this.controlador.realizarLasApuestasSegundaRonda(this.jugadorActual);
	}

	public void realizarPasarSegundaRonda() {
		this.controlador.realizarLosPasesSegundaRonda(this.jugadorActual);
	}

	@Override
	public void mostrarGanador(Jugador ganador) {
		this.vistaApuestas.setVisible(false);
		if (this.vistaApuestas2 != null) {
			this.vistaApuestas2.setVisible(false);
		}
		this.vistaJuegoCartas.setVisible(true);
		this.vistaJuegoCartas.escribirNotificacion(
				"Ganador: " + ganador.getNombre() + " con " + ganador.getResultadoValoresCartas());
	}

	@Override
	public void notificarErrorIngreseUnEnteroSegundaRonda() {
		this.vistaApuestas2.mostrarMenuEnvite();
		this.vistaApuestas2.mostrarErrorNumeroEntero();
	}

	@Override
	public void notificarApuestaMenorALaAnteriorSegundaRonda() {
		this.vistaApuestas2.mostrarMenuEnvite();
		this.vistaApuestas2.mostrarErrorApuestaInsuficiente();
	}

	@Override
	public void informarFondosInsuficientesSegundaRonda() {

		this.vistaApuestas2.setVisible(true);
		this.vistaApuestas2.mostrarErrorFondosInsuficientes();

	}

	@Override
	public void notificarApuestasDesigualesSegundaRonda() {

		this.vistaJuegoCartas.escribirNotificacion(
				"Hay desigualdad entre las apuestas, por favor iguales el valor de la apuesta maxima");

		this.vistaJuegoCartas.setVisible(false);

		this.vistaApuestas2.setVisible(true);

		this.vistaApuestas2.escribirNotificaciones(this.vistaJuegoCartas.getNotificaciones());

		this.vistaApuestas2.mostrarMenuApuestaDesigual();

	}

	public void realizarFicharPostEnviteSegundaRonda() {
		this.controlador.realizarFicharPostEnviteSegundaRonda(this.jugadorActual);
	}

	public void realizarPasePostEnviteSegundaRonda() {
		this.controlador.realizarPasarPostEnviteSegundaRonda(this.jugadorActual);
	}

	public void agregarNuevosFondos(String nuevosFondos) {
		this.controlador.incrementarFondos2(this.jugadorActual, nuevosFondos);
	}

	/*public boolean comprobarPartidaComenzada() {
		return this.controlador.comenzoPartida();
	}*/

	@Override
	public void informarJugadoresInsuficientes() {
		if (this.vistaJuegoCartas != null) {
			this.vistaJuegoCartas.setVisible(false);
		}

		this.vistaMenuPrincipal.setVisible(true);

		this.vistaMenuPrincipal.mostrarErrorJugadoresInsuficientes();
	}

	@Override
	public void informarJugadoresInsuficientesPostPrimerPartido() {
		if (this.vistaJuegoCartas != null) {
			this.vistaJuegoCartas.setVisible(true);
		}

		this.vistaJuegoCartas.mostrarErrorJugadoresInsuficientes();
	}

	public void pasarVistaTop() {
		this.vistaMenuPrincipal.setVisible(false);
		if (this.vistaTop == null) {
			this.vistaTop = new VistaTop();
		}

		this.vistaTop.actualizarTabla(this.controlador.obtenerTopJugadores());

		this.vistaTop.setVisible(true);
	}

	@Override
	public void notificarCartaDescartadaConExito() {
		this.vistaJuegoCartas.escribirNotificacion("Descarte exitoso");
	}

	@Override
	public void notificarRondaApuestaFinalizada() {
		this.vistaJuegoCartas.escribirNotificacion("Apuestas igualadas");
	}

	@Override
	public void notificarErrorIntentarDescarteEnUnaCartaYaDescartada() {
		this.vistaJuegoCartas.escribirNotificacion("No se puede descartas una carta ya descartada");
	}

	public void jugadorNoListo() {
		this.controlador.establecerJugadorComoNoListo(this.jugadorActual);
	}

	@Override
	public void mostrarCartasJugadorAntGanador(List<Jugador> finalistas) {
		if (this.vistaJuegoCartas != null) {
			this.vistaJuegoCartas.mostrarCartasFinalistasRonda(finalistas);
		}
	}

	@Override
	public void notificarPartidaComenzada() {
		if (this.vistaLogin != null) {
			this.vistaLogin.setVisible(true);
			this.vistaLogin.mostrarErrorPartidaComenzada();
		}
	}
	
	@Override
	public void notificarErrorNombre() {
		this.vistaLogin.mostrarErrorNombre();
	}

}
