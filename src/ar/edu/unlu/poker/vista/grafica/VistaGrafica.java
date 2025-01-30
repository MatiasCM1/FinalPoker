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
    private boolean esperandoEntrada;
    private Jugador jugadorActual;
    private Estados estadoFlujo;
    private VistaLogin vistaLogin;
    private VistaMenuPrincipal vistaMenuPrincipal;
    private VistaJuegoCartas vistaJuegoCartas;
    private VistaApuestas vistaApuestas;
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
    	
    	//QUITAR AL JUGADOR DE LA LISTA DE LOS DEMAS JUGADORES, DEL MENUPRINCIPAL
	    
    }
    
    public void seCierraLaVista() {
    	controlador.jugadorSeRetiraDelJuego(this.jugadorActual);
    }
    
    public void listoParaIniciarJuego() {
    	
    	this.vistaMenuPrincipal.setVisible(false);
    	
    	this.vistaJuegoCartas = new VistaJuegoCartas();
    	
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
			this.vistaMenuPrincipal.actualzarTabla(jugardoresSinActual);
		}
	}
	
	private List<Jugador> listaSinJugador(List<Jugador> jugadores){
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
		this.nombreJugadorMano = jugador.getNombre();
		this.vistaJuegoCartas.informarJugadorMano(jugador.getNombre());
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
		
		this.vistaApuestas = new VistaApuestas();
		
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
	
	@Override
	public void informarApuestaRealizada(String nombreJugadorAposto, int apuestaJugador) {
		if (this.jugadorActual.getNombre().equals(nombreJugadorAposto)) {
			this.vistaApuestas.setVisible(false);
			this.vistaJuegoCartas.setVisible(true);
		}
		this.vistaJuegoCartas.notificarApuestaJugador(nombreJugadorAposto, String.valueOf(apuestaJugador));
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
	public void notificarApuestaMenorALaAnterior() {
		this.vistaApuestas.mostrarMenuEnvite();
		this.vistaApuestas.mostrarErrorApuestaInsuficiente();
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
	public void mostrarGanador(Jugador ganador) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void informarFondosInsuficientes() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void informarNoTurno() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void informarTurnoApuestaOtroJugador() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notificarApuestasDesiguales() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notificarJugadorIgualaApuesta() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notificarJugadorPasaApuesta() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notificarEsperarJugadorIgualeApuesta() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notificarRondaApuestaFinalizada() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notificarEsperarDescartes() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mostrarMenuDescartes() {
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
	public void mostrarMenuSegundaRondaApuestas() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notificarGanador(String nombre) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notificarJugadorManoDebeApostar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notificarApuestasDesigualesSegundaRonda() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mostrarOpcionesMenuEmpezarOtraRonda() {
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
