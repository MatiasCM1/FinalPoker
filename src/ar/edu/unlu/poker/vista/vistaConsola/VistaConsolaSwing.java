package ar.edu.unlu.poker.vista.vistaConsola;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.List;
import ar.edu.unlu.poker.controlador.Controlador;
import ar.edu.unlu.poker.modelo.Jugador;
import ar.edu.unlu.poker.vista.IVista;

public class VistaConsolaSwing extends JFrame implements IVista {
    private JTextArea areaSalida;
    private JTextField campoEntrada;
    private Controlador controlador;
    private boolean esperandoEntrada;
    private String nombreJugadorActual;
    private Jugador jugadorActual;
<<<<<<< HEAD:src/ar/edu/unlu/poker/vista/vistaConsola/VistaConsolaSwing.java
    private Estados estadoFlujo;

    public VistaConsolaSwing() {
    	this.estadoFlujo = Estados.SOLICITAR_NOMBRE_JUGADOR;
        setTitle("Poker");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
=======
    private EstadoFlujo estadoFlujo;
    

    public VistaConsolaSwing() {
        setTitle("Simulador de Consola - P�ker");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        this.estadoFlujo = EstadoFlujo.INGRESAR_NOMBRE;
        
        // Crear �rea de texto para mostrar la salida
>>>>>>> ca55cfb84d248f710db8c2c9e2aa9fd1e1b7f5d9:src/ar/edu/unlu/poker/vista/VistaConsolaSwing.java
        areaSalida = new JTextArea();
        areaSalida.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(areaSalida);
        campoEntrada = new JTextField();
        campoEntrada.setPreferredSize(new Dimension(800, 30));
        campoEntrada.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = campoEntrada.getText();
<<<<<<< HEAD:src/ar/edu/unlu/poker/vista/vistaConsola/VistaConsolaSwing.java
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
=======
                
                switch(estadoFlujo) {
                	case INGRESAR_NOMBRE:
                		solicitarNombre(input);
                	break;
                	case MENU_INICIO:
                		manejarMenuInicio(input);
                	break;
                	case MENU_APUESTAS:
                		mostrarApuestas(input);
                	break;
                	case SOLICITAR_APUESTA:
                		solicitarApuesta(input);
                	break;
                }
					
				
                campoEntrada.setText("");
            }
        });

        // A�adir componentes al JFrame
>>>>>>> ca55cfb84d248f710db8c2c9e2aa9fd1e1b7f5d9:src/ar/edu/unlu/poker/vista/VistaConsolaSwing.java
        add(scrollPane, BorderLayout.CENTER);
        add(campoEntrada, BorderLayout.SOUTH);
        setVisible(true);

		areaSalida.append("Ingrese su nombre\n");
    }

    @Override
    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
    }

<<<<<<< HEAD:src/ar/edu/unlu/poker/vista/vistaConsola/VistaConsolaSwing.java
    private void procesarEntrada(String input) throws RemoteException {
        
    	if (controlador == null) {
            areaSalida.append("Error: Controlador no configurado.\n");
            return;
        }
=======
    private void solicitarNombre(String input){
        areaSalida.append("> " + input + "\n");
>>>>>>> ca55cfb84d248f710db8c2c9e2aa9fd1e1b7f5d9:src/ar/edu/unlu/poker/vista/VistaConsolaSwing.java

        switch (estadoFlujo) {
        	case SOLICITAR_NOMBRE_JUGADOR:
        		esperandoEntrada = true;
        		solicitarNombre(input);
        	break;
        	case MENU_PRINCIPAL:
        		esperandoEntrada = true;
        		menu(input);
        	break;
        	case SOLICITAR_APUESTAS:
        		esperandoEntrada = true;
        		solicitarApuestas(input);
        	break;
        }
    }
    
    public void solicitarNombre(String input) {
    	if (esperandoEntrada) {
            nombreJugadorActual = input.trim();
            if (!nombreJugadorActual.isEmpty()) {
                jugadorActual = new Jugador(nombreJugadorActual);
<<<<<<< HEAD:src/ar/edu/unlu/poker/vista/vistaConsola/VistaConsolaSwing.java
                controlador.agregarJugador(jugadorActual);
                setTitle("Poker - Jugador: " + nombreJugadorActual);
                areaSalida.append("Bienvenido, " + nombreJugadorActual + "!\n");
                esperandoEntrada = false;
                mostrarOpcionesMenu(); 
=======
                controlador.agregarJugador(jugadorActual);  // Agregar jugador al controlador
                setTitle("Simulador de Consola - Poker: " + nombreJugadorActual);
                areaSalida.append("Bienvenido, " + nombreJugadorActual + "!\n");
                //mostrarOpciones();
                this.estadoFlujo = EstadoFlujo.MENU_INICIO;
                mostrarOpciones();
                esperandoNombreJugador = false;
>>>>>>> ca55cfb84d248f710db8c2c9e2aa9fd1e1b7f5d9:src/ar/edu/unlu/poker/vista/VistaConsolaSwing.java
            } else {
                areaSalida.append("Nombre no valido. Por favor, ingrese un nombre para comenzar:\n");
            }
            return;
        }
<<<<<<< HEAD:src/ar/edu/unlu/poker/vista/vistaConsola/VistaConsolaSwing.java
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
    				this.estadoFlujo = Estados.SOLICITAR_APUESTAS;
    				controlador.iniciarGame();
    				break;
    			case "0":
    				areaSalida.append("Se sali� del juego exitosamente. �Saludos!\n");
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
    
    public void solicitarApuestas(String input) {
        if (esperandoEntrada) {
        	switch (input.toLowerCase()) {
        		case "1":
        			try {
        				//this.estadoFlujo = Informes.ESPERAR_DESCARTES
        				controlador.igualarApuesta(jugadorActual);
        				areaSalida.append(this.jugadorActual.getNombre() + " ha igualado la apuesta mayor\n");
        				this.esperandoEntrada = false;
        			} catch (RemoteException e1) {
        				e1.printStackTrace();
        			};
        			break;
        		case "2":
        				//this.estadoFlujo = Informes.ESPERAR_DESCARTES
        				this.esperandoEntrada = false;
        				this.estadoFlujo = Estados.ESPERANDO_INGRESO_APUESTA;
        				solicitarMontoApuesta();
        			break;
        		case "3":
        			jugadorActual.pasar();
        			areaSalida.append(this.jugadorActual.getNombre() + " ha pasado la ronda\n");
        			break;
        		default:
        			areaSalida.append("Comando no reconocido. Intente nuevamente.\n");
        			break;
        	}
        }
    }
    
    
    
    
    
    
    
    
 private void solicitarMontoApuesta() {
	 areaSalida.append("Ingrese una suma igual o mayor como apuesta\n");
	 String apuestaInput = campoEntrada.getText();
	 try {
		controlador.realizarApuesta(jugadorActual, Integer.parseInt(apuestaInput));
	} catch (NumberFormatException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (RemoteException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
 }
    
    
    
    
    
    
    
    

    private void mostrarOpcionesMenu() {
        areaSalida.append("Seleccione una opci�n:\n");
=======

        if (controlador == null) {
            areaSalida.append("Error: Controlador no configurado.\n");
            return;
        }
        
        campoEntrada.setText("");
    }
    
    private void manejarMenuInicio(String input) {
    	
    	switch (input.toLowerCase()) {
        case "1":
            mostrarJugadores();
            break;
        case "2":
            controlador.iniciarGame();
            break;
        case "0":
            areaSalida.append("Se sali� del juego exitosamente. �Saludos!\n");
		try {
			controlador.jugadorSeRetiraDelJuego(jugadorActual);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
            System.exit(0);
            break;
        default:
            areaSalida.append("Comando no reconocido. Intente nuevamente.\n");
            break;
    	}
    	campoEntrada.setText("");
    }
    

    private void mostrarOpciones() {
        areaSalida.append("Seleccione una opci�n:\n");
>>>>>>> ca55cfb84d248f710db8c2c9e2aa9fd1e1b7f5d9:src/ar/edu/unlu/poker/vista/VistaConsolaSwing.java
        areaSalida.append("1 - Ver Lista de Jugadores\n");
        areaSalida.append("2 - Comenzar Juego\n");
        areaSalida.append("0 - Salir\n");
        this.estadoFlujo = Estados.MENU_PRINCIPAL;
    }

    private void mostrarJugadores() {
        List<Jugador> jugadores = controlador.obtenerJugadores();
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
    public void informarJugadoresInsuficientes() {
        areaSalida.append("La cantidad de jugadores es insuficiente para iniciar el juego.\n");
    }

    @Override
    public void informarCantJugadoresExcedidos() {
        areaSalida.append("La cantidad de jugadores excede el l�mite permitido.\n");
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
    
    public void mostrarOpcionesApuestas() {
<<<<<<< HEAD:src/ar/edu/unlu/poker/vista/vistaConsola/VistaConsolaSwing.java
        areaSalida.append("Seleccione una opci�n:\n");
        areaSalida.append("1 - Fichar\n");
        areaSalida.append("2 - Envitar\n");
        areaSalida.append("3 - Pasar\n");
        this.estadoFlujo = Estados.SOLICITAR_APUESTAS;
=======
        areaSalida.append("Seleccione una opci�n:\n");
        areaSalida.append("1 - Fichar\n");
        areaSalida.append("2 - Envitar\n");
        areaSalida.append("3 - Pasar\n");
    }
    
    public void mostrarApuestas(String input) {
    	
    	switch (input.toLowerCase()) {
        	case "1":
        		try {
					controlador.igualarApuesta(jugadorActual);
				} catch (RemoteException e1) {
					e1.printStackTrace();
				};
			break;
            case "2":
            	
            	this.estadoFlujo = EstadoFlujo.SOLICITAR_APUESTA;
            	
			break;
            case "3":
                    
            	//JUGADOR PASA
                	
                	
            break;
            default:
            	areaSalida.append("Comando no reconocido. Intente nuevamente.\n");
            break;
           }
           campoEntrada.setText("");
    }
    
    private void solicitarApuesta(String input) {
    	areaSalida.append("Ingrese una suma igual o mayor como apuesta");
    	try {
			controlador.realizarApuesta(jugadorActual, Integer.parseInt(input));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	campoEntrada.setText("");
>>>>>>> ca55cfb84d248f710db8c2c9e2aa9fd1e1b7f5d9:src/ar/edu/unlu/poker/vista/VistaConsolaSwing.java
    }
    
    public void notificarFondosInsuficientes() {
    	areaSalida.append("Fondos insuficientes para realizar dicha apuesta\n");
    }

	@Override
	public void notificarApuestaInsuficiente() {
		areaSalida.append("Apuesta insuficiente\n");
	}

	@Override
	public void notificarApuestaRealizada() {
		areaSalida.append("Apuesta de " + jugadorActual.getNombre() + ": " + jugadorActual.getApuesta() + "\n");
	}

    
}
