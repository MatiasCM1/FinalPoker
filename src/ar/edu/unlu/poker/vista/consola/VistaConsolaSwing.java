package ar.edu.unlu.poker.vista.consola;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;
import ar.edu.unlu.poker.controlador.Controlador;
import ar.edu.unlu.poker.modelo.Jugador;
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
    private int apuestaJugadorActual = 0;

    public VistaConsolaSwing() {
    	this.estadoFlujo = Estados.SOLICITAR_NOMBRE_JUGADOR;
        setTitle("Poker");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        add(scrollPane, BorderLayout.CENTER);
        add(campoEntrada, BorderLayout.SOUTH);
        setVisible(true);

		areaSalida.append("Ingrese su nombre\n");
    }

    @Override
    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
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
        }
    }
    
  //----------------------------------------------------------------------------------------------------------
  //JUEGO PRINCIPAL
  //----------------------------------------------------------------------------------------------------------
    
    public void solicitarNombre(String input) {
    	if (esperandoEntrada) {
            nombreJugadorActual = input.trim();
            if (!nombreJugadorActual.isEmpty()) {
            	areaSalida.append("Ingrese el fondo que desea\n");
            	this.estadoFlujo = Estados.SOLICITAR_FONDO_JUGADOR; 
            } else {
                areaSalida.append("Nombre no valido. Por favor, ingrese un nombre para comenzar:\n");
            }
            return;
        }
    }
    
    public void solicitarFondo(String input){
    	//Validar que sea numero
    	if (esperandoEntrada) {
    		jugadorActual = new Jugador(this.nombreJugadorActual, Integer.parseInt(input));
            controlador.agregarJugador(jugadorActual);
            controlador.setJugadorActual(jugadorActual);
            setTitle("Poker - Jugador: " + this.nombreJugadorActual);
            areaSalida.append("Bienvenido, " + this.nombreJugadorActual + "!\n");
            esperandoEntrada = false;
            mostrarOpcionesMenu();
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
    				controlador.iniciarGame();
    				break;
    			case "0":
    				areaSalida.append("Se salio del juego exitosamente. Saludos!\n");
    				try {
    					controlador.jugadorSeRetiraDelJuego(jugadorActual);
    				} catch (RemoteException e) {
    					e.printStackTrace();
    				}
    				System.exit(0);
    				break;
    			default:
    				areaSalida.append("Comando no reconocido. Intente nuevamente.\n");
    				break;
    		}
    	}
    }
    
    private void mostrarOpcionesMenu() {
        areaSalida.append("Seleccione una opcion:\n");
        areaSalida.append("1 - Ver Lista de Jugadores\n");
        areaSalida.append("2 - Comenzar Juego\n");
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
    public void mostrarGanador(List<Jugador> ganadores) {
        areaSalida.append("Ganador:\n");
        for (Jugador ganador : ganadores) {
            areaSalida.append(" - " + ganador.getNombre() + " con " + ganador.getResultadoValoresCartas() + "\n");
        }
        mostrarOpcionesMenu();
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
    	if (this.jugadorActual.getNombre().equals(controlador.getJugadorTurnoParaAposter().getNombre())) {
    		areaSalida.append("Seleccione una opcion:\n");
    		areaSalida.append("1 - Envitar\n");
    		this.estadoFlujo = Estados.MENU_APUESTAS;
    	} else {
    		areaSalida.append("Esperando a que " + controlador.getJugadorTurnoParaAposter().getNombre() + " realice su apuesta\n");
    	}
    }
    
    public void menuApuestas(String input) {	
    	if (this.jugadorActual.getNombre().equals(controlador.getJugadorTurnoParaAposter().getNombre())) {
    		if (esperandoEntrada) {
    			switch (input.toLowerCase()) {
    				case "1":
    					areaSalida.append("Ingrese el valor de la apuesta.\n");
    					this.estadoFlujo = Estados.ESPERANDO_ENVITE;
    					break;
    				default:
    					areaSalida.append("Comando no reconocido. Intente nuevamente.\n");
    					break;
    			}
    		}
    	}
    }
    
    public void realizarEnvite(String input) {
    	if (this.jugadorActual.getNombre().equals(controlador.getJugadorTurnoParaAposter().getNombre())) {
    		this.apuestaJugadorActual = Integer.parseInt(input);
    		controlador.realizarLasApuestas(this.jugadorActual, this.apuestaJugadorActual);
    		//controlador.realizarEnvite(Integer.parseInt(input));
    		this.esperandoEntrada = false;
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
   }
   
   @Override
   public void informarFondosInsuficientes() {
	   if (this.jugadorActual.getNombre().equals(controlador.getJugadorTurnoParaAposter().getNombre())) {
		   areaSalida.append("Fondos insuficientes para realizar la apuesta.\n");
		   this.apuestaJugadorActual = 0;
	   }
   }

   @Override
   public void notificarEnviteRealizado() {
	   areaSalida.append(controlador.getJugadorTurnoParaAposter().getNombre() + " realizo con exito su apuesta " + mostrarApostado());
   }
   
   private String mostrarApostado() {
	   for (Jugador j : controlador.getJugadoresMesa()) {
		   if (j.getNombre().equals(controlador.getJugadorTurnoParaAposter().getNombre())) {
			   return String.valueOf(j.getApuesta());
		   }
	   }
	   return "_";
   }

   @Override
   public void informarApuestaRealizada(String nombre, int apuestaJugador) {
	   areaSalida.append(nombre + " ha realizado su apuesta: " + apuestaJugador + ".\n");
   }

   @Override
   public void informarNoTurno(String nombre) {
	   areaSalida.append("esperando a que" + nombre + " realice su apuesta.\n");
   }
 
    
}