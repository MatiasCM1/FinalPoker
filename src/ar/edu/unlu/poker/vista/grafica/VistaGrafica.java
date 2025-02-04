package ar.edu.unlu.poker.vista.grafica;

import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;

import ar.edu.unlu.poker.controlador.Controlador;
import ar.edu.unlu.poker.modelo.Jugador;
import ar.edu.unlu.poker.vista.IVista;
import ar.edu.unlu.poker.vista.consola.Estados;

public class VistaGrafica implements IVista{

	private Controlador controlador;
    private Jugador jugadorActual;
    private VistaLogin vistaLogin;
    private VistaMenuPrincipal vistaMenuPrincipal;
    private VistaJuegoCartas vistaJuegoCartas;
    private VistaApuestas vistaApuestas;
    private VistaApuestas2 vistaApuestas2;
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
    	
		this.vistaLogin = new VistaLogin(this.controlador);
		
	    this.vistaLogin.setVisible(true);
	    
	}
    
    public void setJugador(String nombre, int fondos) {
    	this.jugadorActual = new Jugador(nombre, fondos);
    }
    
    public void pasarVistaMenu() {
    	//CAMBIAR DE VISTA
    	this.vistaLogin.setVisible(false);
    	this.vistaMenuPrincipal = new VistaMenuPrincipal();
	    this.vistaMenuPrincipal.setVisible(true);
	    
	    //AGREGAR JUGADOR AL MODELO
	    controlador.agregarJugador(jugadorActual);
	    controlador.setJugadorActual(jugadorActual);
	    
    }
    
    
    
    public void volverPantallaLogin() {
    	//CAMBIAR DE VISTA
    	this.vistaMenuPrincipal.setVisible(false);
    	this.vistaLogin.setVisible(true);
    	
    	//QUITAR AL JUGADOR
    	controlador.jugadorSeRetiraDelJuego(this.jugadorActual);
    	
    	//NOTIFICAR QUE JGUADOR SE RETIRA DEL JUEGO
    	
    	//QUITAR AL JUGADOR DE LA LISTA DE LOS DEMAS JUGADORES, DEL MENUPRINCIPAL
	    
    }
    
    public void jugadorSaleDelJuego() {
    	controlador.jugadorSeRetiraDelJuego(this.jugadorActual);
    	//NOTIFICAR QUE JGUADOR SE RETIRA DEL JUEGO
    	
    	//QUITAR AL JUGADOR DE LA LISTA DE LOS DEMAS JUGADORES, DEL MENUPRINCIPAL
    }
    
    public void listoParaIniciarJuego() {
    	
    	this.vistaMenuPrincipal.setVisible(false);
    	
    	if (this.vistaJuegoCartas == null) {
    		this.vistaJuegoCartas = new VistaJuegoCartas();
    	}
    	
	    this.vistaJuegoCartas.setVisible(true);
	    
	    controlador.iniciarSiEstaListo(this.jugadorActual);
    	
    }

	public Jugador getJugadorActual() {
    	return this.jugadorActual;
    }
	
	@Override
	public void setControlador(Controlador controlador) {
		this.controlador = controlador;
	}
	
	public List<Jugador> getJugadoresMesa(){
		return controlador.getJugadoresMesa();
	}
	
	@Override
	public void actualizarTablaJugadores(List<Jugador> jugadores) {
		if (controlador.verificarCantidadDeJugadores()) {
			List<Jugador> jugardoresSinActual = this.listaSinJugador(jugadores);
			this.vistaMenuPrincipal.actualizarTabla(jugardoresSinActual);
		}
	}
	
	private List<Jugador> listaSinJugador(List<Jugador> jugadores){
		List<Jugador> listaResultado = new LinkedList<Jugador>();
		for (Jugador j : jugadores) {
			if (!j.getNombre().equals(this.jugadorActual.getNombre())) {
				listaResultado.add(j);
			} else {
				this.vistaMenuPrincipal.actualizarJugadorVista(j);
			}
		}
		return listaResultado;
	}

	@Override
	public void mostrarJugadorMano(Jugador jugador) {
		//this.nombreJugadorMano = jugador.getNombre();
		//this.vistaJuegoCartas.informarJugadorMano(jugador.getNombre());
		
		this.vistaJuegoCartas.escribirNotificacion("Jugador mano: " + jugador.getNombre());
	
	}

	@Override
	public void mostrarCartasJugador(List<Jugador> jugadores) {
		
		for (Jugador jugador : jugadores) {
            if (jugador.equals(jugadorActual)) { // Mostrar solo cartas del jugador actual
            	this.jugadorActual.setListaCartas(jugador.getCartas());
            	this.vistaJuegoCartas.mostrarCartas(jugador.getCartas());
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
		
		
		//ESTO SE PUEDE SACAR?
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
		//this.vistaJuegoCartas.notificarApuestaJugador(nombreJugadorAposto, String.valueOf(apuestaJugador));
		this.vistaJuegoCartas.escribirNotificacion(nombreJugadorAposto + " realizo una apuesta de " + String.valueOf(apuestaJugador));
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
		//this.vistaApuestas.setVisible(true);
		this.vistaApuestas.mostrarMenuEnvite();
		this.vistaApuestas.mostrarErrorNumeroEntero();
	}
	
	@Override
	public void notificarApuestaMenorALaAnterior() {
		//this.vistaApuestas.setVisible(true);
		this.vistaApuestas.mostrarMenuEnvite();
		this.vistaApuestas.mostrarErrorApuestaInsuficiente();
	}
	
	@Override
	public void informarFondosInsuficientes() {
		
		this.vistaApuestas.setVisible(true);
		//this.vistaApuestas.mostrarMenuEnvite();
		this.vistaApuestas.mostrarErrorFondosInsuficientes();
	
	}
	
	@Override
	public void notificarApuestasDesiguales() {
		
		this.vistaJuegoCartas.escribirNotificacion("Hay desigualdad entre las apuestas, por favor iguales el valor de la apuesta maxima");
		
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
	public void notificarJugadorPasaApuesta() {
		this.vistaJuegoCartas.escribirNotificacion(controlador.getJugadorTurnoJugadoresMesa().getNombre() + " pasa y queda fuera del juego");
	}
	
	@Override
	public void notificarGanadorUnicoEnMesa(String nombre) {
	
		this.vistaApuestas.setVisible(false);
		this.vistaJuegoCartas.setVisible(true);
		this.vistaJuegoCartas.escribirNotificacion("El ganador es " + nombre + " debido a que todos se retiraron");
		
	}
	
	@Override
	public void mostrarOpcionesMenuEmpezarOtraRonda() {
		controlador.establecerJugadorComoNoListo(this.jugadorActual);
		this.vistaJuegoCartas.mostrarOpcionesParaNuevaRondaJuego();
	}
	
	public void volverMenuPrincipal() {
		
		this.vistaJuegoCartas.setVisible(false);
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
		this.vistaJuegoCartas.mostrarMenuDescartes();
	}
	
	@Override
	public void notificarEsperarDescartes() {
		this.vistaJuegoCartas.escribirNotificacion("Esperando a que se realicen los descartes");
	}
	
	public void cartaADescartarSeleccionada(int posicionCarta) {
		this.controlador.cartaADescartar(posicionCarta, jugadorActual);
	}
	
	public void finalizarFaseDescarte() {
		this.controlador.continuarJuegoPostDescarte(this.jugadorActual);
	}
	
	@Override
	public void informarTurnoApuestaOtroJugador() {
		this.vistaJuegoCartas.escribirNotificacion("Esperando a que realicen su apuesta");
	}
	
	@Override
	public void mostrarMenuSegundaRondaApuestas() {
		this.vistaJuegoCartas.setVisible(false);
		
		if (this.vistaApuestas2 == null) {
			this.vistaApuestas2 = new VistaApuestas2();
		}
		
		this.vistaApuestas2.setVisible(true);
		
		
		//ESTO SE PUEDE SACAR?
		//this.vistaApuestas2.informarJugadorMano(this.nombreJugadorMano);
		
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
	
	@Override
	public void mostrarGanador(Jugador ganador) {
		this.vistaApuestas.setVisible(false);
		this.vistaApuestas2.setVisible(false);
		this.vistaJuegoCartas.escribirNotificacion("Ganador: " + ganador.getNombre() + " con " + ganador.getResultadoValoresCartas());
	}
	
	@Override
	public void notificarEnviteRealizado() {
		
	}

	@Override
	public void informarJugadoresInsuficientes() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void informarCantJugadoresExcedidos() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void informarNoTurno() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notificarRondaApuestaFinalizada() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notificarErrorIntentarDescarteEnUnaCartaYaDescartada() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notificarCartaDescartadaConExito() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notificarApuestasDesigualesSegundaRonda() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setEnableCampoEntrada(boolean h) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mostrarOpcionesMenu() {
		// TODO Auto-generated method stub
		
	}

	
}
