package ar.edu.unlu.poker.vista.consola;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;
import ar.edu.unlu.poker.controlador.Controlador;
import ar.edu.unlu.poker.modelo.Carta;
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
    
    public void setEnableCampoEntrada(boolean valor) {
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
    		if (controlador.validarNombreNoRepetido(input)) {
    			nombreJugadorActual = input.trim();
            	if (!nombreJugadorActual.isEmpty()) {
            		areaSalida.append("Ingrese el fondo que desea\n");
            		this.estadoFlujo = Estados.SOLICITAR_FONDO_JUGADOR; 
            	} else {
                	areaSalida.append("Nombre no valido. Por favor, ingrese un nombre para comenzar:\n");
                	this.estadoFlujo = Estados.SOLICITAR_NOMBRE_JUGADOR;
            	}
            	return;
    		} else {
    			areaSalida.append("Nombre ya usado. Por favor, ingrese un nombre para comenzar:\n");
            	this.estadoFlujo = Estados.SOLICITAR_NOMBRE_JUGADOR;
    		}
    	}
    }
    
    public void solicitarFondo(String input){
    	//Validar que sea numero
    	if (esperandoEntrada) {
    		if (controlador.validarEntero(input)) {
    			jugadorActual = new Jugador(this.nombreJugadorActual, Integer.parseInt(input));
            	controlador.agregarJugador(jugadorActual);
            	controlador.setJugadorActual(jugadorActual);
            	setTitle("Poker - Jugador: " + this.nombreJugadorActual);
            	areaSalida.append("Bienvenido, " + this.nombreJugadorActual + "!\n");
            	esperandoEntrada = false;
            	mostrarOpcionesMenu();
    		} else {
    			this.notificarErrorIngreseUnEntero();
    			areaSalida.append("¡Error, ingrese un numero entero!\n");
    			areaSalida.append("Ingrese el fondo que desea\n");
            	this.estadoFlujo = Estados.SOLICITAR_FONDO_JUGADOR;
    		}
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
    			case "3":
    				mostrarFondos();
    				this.esperandoEntrada = false;
    				break;
    			case "4":
    				areaSalida.append("Ingrese los fondos.\n");
    				this.estadoFlujo = Estados.ESPERANDO_FONDOS;
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
    
    private void mostrarFondos() {
    	areaSalida.append("Fondos: " + controlador.getFondosJugador(this.jugadorActual) + ".\n");
		this.mostrarOpcionesMenu();
	}
    
    private void agregandoFondos(String input) {
    	controlador.incrementarFondos(this.jugadorActual, input);
    	this.notificarFondosAgregados();
    }

    @Override
	public void mostrarOpcionesMenu() {
        areaSalida.append("Seleccione una opcion:\n");
        areaSalida.append("1 - Ver Lista de Jugadores\n");
        areaSalida.append("2 - Comenzar Juego\n");
        areaSalida.append("3 - Mostrar Fondos\n");
        areaSalida.append("4 - Agregar Fondos\n");
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
        areaSalida.append("Seleccione una opcion:\n");
        areaSalida.append("1 - Seguir jugando\n");
        areaSalida.append("2 - Mostrar Jugadores\n");
        areaSalida.append("3 - Mostrar Fondos\n");
        areaSalida.append("4 - Agregar Fondos\n");
        areaSalida.append("0 - Salir\n");
        this.setEnableCampoEntrada(true);
        this.estadoFlujo = Estados.MENU_NUEVA_RONDA;
    }
    
    public void menuNuevaRonda(String input){
    	if (esperandoEntrada) {
    		switch (input.toLowerCase()) {
    			case "1":
    				this.esperandoEntrada = false;
    				controlador.iniciarGamePostPrimeraRonda();
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
    	//MODIFICAR ESTAS VALIDACIONES EN LA VISTA, SE DEBEN HACER CUANDO SE LLAMA AL mostrarMenuApuestas en el controlador
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
    				//ENVITAR
    				areaSalida.append("Ingrese el valor de la apuesta.\n");
    				this.estadoFlujo = Estados.ESPERANDO_ENVITE_SEGUNDA_RONDA;
    			break;
    			case "2":
    				//FICHAR
    				this.realizarFicheSegundaRonda();
    			break;
    			case "3":
    				//PASAR
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
    
    private void mostrarMenuApuestasDesigualesSegundaRonda(){
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
    				//JUGADOR DECIDE NO DESCARTAR
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
    	for (Carta c : cartasJugador) { //UNA FORMA DE MOSTRAR EL MENU CON LAS CARTAS QUE TIENE EL JUGADOR
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
    				areaSalida.append("Se selecciono " + cartasJugador.get(0).getValor() + " de " + cartasJugador.get(0).getPalo() + "\n");
    				controlador.cartaADescartar(0, this.jugadorActual);//AGREGA A LA COLA DE CARTAS A DESCARTAR INDICANDO LA POSICION y el jugador al que pertenecen
    				this.estadoFlujo = Estados.MENU_SELECCION_DESCARTES;
    			break;
    			case "2":
    				areaSalida.append("Se selecciono " + cartasJugador.get(1).getValor() + " de " + cartasJugador.get(1).getPalo() + "\n");//AGREGA A LA COLA DE CARTAS A DESCARTAR INDICANDO LA POSICION y el jugador al que pertenecen
    				controlador.cartaADescartar(1, this.jugadorActual);
    				this.estadoFlujo = Estados.MENU_SELECCION_DESCARTES;
    			break;
    			case "3":
    				areaSalida.append("Se selecciono " + cartasJugador.get(2).getValor() + " de " + cartasJugador.get(2).getPalo() + "\n");//AGREGA A LA COLA DE CARTAS A DESCARTAR INDICANDO LA POSICION y el jugador al que pertenecen
    				controlador.cartaADescartar(2, this.jugadorActual);
    				this.estadoFlujo = Estados.MENU_SELECCION_DESCARTES;
    			break;
    			case "4":
    				areaSalida.append("Se selecciono " + cartasJugador.get(3).getValor() + " de " + cartasJugador.get(3).getPalo() + "\n");//AGREGA A LA COLA DE CARTAS A DESCARTAR INDICANDO LA POSICION y el jugador al que pertenecen
    				controlador.cartaADescartar(3, this.jugadorActual);
    				this.estadoFlujo = Estados.MENU_SELECCION_DESCARTES;
    			break;
    			case "5":
    				areaSalida.append("Se selecciono " + cartasJugador.get(4).getValor() + " de " + cartasJugador.get(4).getPalo() + "\n");//AGREGA A LA COLA DE CARTAS A DESCARTAR INDICANDO LA POSICION y el jugador al que pertenecen
    				controlador.cartaADescartar(4, this.jugadorActual);
    				this.estadoFlujo = Estados.MENU_SELECCION_DESCARTES;
    			break;
    			case "6":
    				//CONTINUAR EL JUEGO
    				//ordenar al controlador que inicie el descarte
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
   }
   
   @Override
   public void informarFondosInsuficientes() {
	   if (this.jugadorActual.getNombre().equals(controlador.getJugadorTurno().getNombre())) {
		   areaSalida.append("Fondos insuficientes para realizar la apuesta.\n");
	   }
   }

   @Override
   public void notificarEnviteRealizado() {
	   areaSalida.append(controlador.getJugadorTurno().getNombre() + " realizo con exito su apuesta " + mostrarApostado());
   }
   
   private String mostrarApostado() {
	   for (Jugador j : controlador.getJugadoresMesa()) {
		   if (j.getNombre().equals(controlador.getJugadorTurno().getNombre())) {
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
   public void informarNoTurno() {
	   areaSalida.append("espera hasta que sea su turno.\n");
   }

   @Override
	public void informarTurnoApuestaOtroJugador() {
	   areaSalida.append("Esperando a que realicen su apuesta\n");
	}

   @Override
	public void notificarApuestasDesiguales() {
	   areaSalida.append("Hay desigualdad entre las apuestas, por favor iguales el valor de la apuesta maxima.\n");
	   menuApuestasDesiguales();
   }
   
   @Override
	public void notificarApuestasDesigualesSegundaRonda() {
	   areaSalida.append("Hay desigualdad entre las apuestas, por favor iguales el valor de la apuesta maxima.\n");
	   mostrarMenuApuestasDesigualesSegundaRonda();
	}

   @Override
   public void notificarJugadorIgualaApuesta() {
	   areaSalida.append("Se iguala la apuesta y sigue en el juego.\n");
   }

   @Override
   public void notificarJugadorPasaApuesta() {
	   areaSalida.append(controlador.getJugadorTurnoJugadoresMesa().getNombre() + " pasa y queda fuera del juego.\n");
   }

   @Override
   public void notificarEsperarJugadorIgualeApuesta() {
	   areaSalida.append("esperando a que se igualen las apuestas.\n");
   }

   @Override
   public void notificarRondaApuestaFinalizada() {
	   areaSalida.append("Apuestas igualadas.\n");
   }

   @Override
   public void notificarApuestaMenorALaAnterior() {
	   areaSalida.append("La apuesta no puede ser menor a la apuesta anterior.\n");
   }
	
	@Override
	public void notificarEsperarDescartes() {
		areaSalida.append("Esperando a que se realicen los descartes.\n");
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
	}

	private void notificarFondosAgregados() {
		areaSalida.append("¡Fondos agregados con exito!.\n");
		mostrarOpcionesMenu();
	}
	
	private void notificarFondosAgregados2() {
		areaSalida.append("¡Fondos agregados con exito!.\n");
		mostrarOpcionesMenuEmpezarOtraRonda();
	}

	@Override
	public void actualizarTablaJugadores(List<Jugador> jugadores) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mostrarNombreDelJugadorVentana() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void limpiarNotificaciones() {
		areaSalida.setText("");
	}

	@Override
	public void notificarErrorIngreseUnEnteroSegundaRonda() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notificarApuestaMenorALaAnteriorSegundaRonda() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void informarFondosInsuficientesSegundaRonda() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notificarErrorIngreseUnEnteroAgregandoNuevosFondos() {
		// TODO Auto-generated method stub
		
	}
    
}