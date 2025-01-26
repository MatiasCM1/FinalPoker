package ar.edu.unlu.poker.vista.grafica;

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
	    this.vistaApuestas = new VistaApuestas();
	    
	    this.vistaLogin.setVisible(true);
	    this.vistaApuestas.setVisible(false);
	    
	    
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
    
    public void pasarVistaJuegoCartas() {
    	this.vistaMenuPrincipal.setVisible(false);
    	this.vistaJuegoCartas = new VistaJuegoCartas();
	    this.vistaJuegoCartas.setVisible(true);
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
    
    public void iniciarElJuego() {
    	controlador.iniciarGame();
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
		List<Jugador> jugardoresSinActual = this.listaSinJugador(jugadores);
		this.vistaMenuPrincipal.actualzarTabla(jugardoresSinActual);
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mostrarCartasJugador(List<Jugador> jugadores) {
		// TODO Auto-generated method stub
		
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
	public void mostrarMenuApuestas() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void informarFondosInsuficientes() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notificarEnviteRealizado() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void informarApuestaRealizada(String nombre, int apuestaJugador) {
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
	public void notificarApuestaMenorALaAnterior() {
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
	public void notificarErrorIngreseUnEntero() {
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
