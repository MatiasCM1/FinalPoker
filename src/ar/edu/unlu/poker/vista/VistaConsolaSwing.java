package ar.edu.unlu.poker.vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.List;
import ar.edu.unlu.poker.controlador.Controlador;
import ar.edu.unlu.poker.modelo.Jugador;

public class VistaConsolaSwing extends JFrame implements IVista {
    private JTextArea areaSalida;
    private JTextField campoEntrada;
    private Controlador controlador;
    private boolean esperandoNombreJugador = false;

    public VistaConsolaSwing() {
        setTitle("Simulador de Consola - Póker");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Crear área de texto para mostrar la salida
        areaSalida = new JTextArea();
        areaSalida.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(areaSalida);

        // Crear campo de texto para la entrada del usuario
        campoEntrada = new JTextField();
        campoEntrada.setPreferredSize(new Dimension(800, 30)); // Establecer tamaño preferido
        campoEntrada.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = campoEntrada.getText();
                procesarEntrada(input);
                campoEntrada.setText("");
            }
        });

        // Añadir componentes al JFrame
        add(scrollPane, BorderLayout.CENTER);
        add(campoEntrada, BorderLayout.SOUTH);

        // Mostrar mensaje inicial para verificar que la consola funciona
        areaSalida.append("Bienvenido al Simulador de Consola - Póker\n");
        areaSalida.append("Ingrese un comando para comenzar...\n");
        areaSalida.append("1 - Agregar jugador\n");
        areaSalida.append("2 - Mostrar Lista Jugador\n");
        areaSalida.append("3 - Comenzar Partida\n");
        areaSalida.append("0 - Salir\n");

        // Asegurarse de que el campo de entrada tenga el foco
        campoEntrada.requestFocusInWindow();

        setVisible(true); // Hacer visible el JFrame al final de la configuración
    }

    @Override
	public void setControlador(Controlador controlador) {
        this.controlador = controlador;
    }

    private void procesarEntrada(String input) {
        areaSalida.append("> " + input + "\n");

        // Si estamos esperando el nombre del jugador
        if (esperandoNombreJugador) {
            agregarJugador(input);
            esperandoNombreJugador = false;
            return;
        }

        // Procesar comandos de entrada del usuario
        if (controlador == null) {
            areaSalida.append("Error: Controlador no configurado.\n");
            return;
        }

        switch (input.toLowerCase()) {
            case "1":
                solicitarNombreJugador();
                break;
            case "2":
                mostrarJugadores();
                break;
            case "3":
                controlador.iniciarGame();
                break;
            case "0":
                areaSalida.append("Se salió del juego exitosamente. ¡Saludos!\n");
                System.exit(0);
                break;
            default:
                areaSalida.append("Comando no reconocido. Intente nuevamente.\n");
                break;
        }
    }

    private void solicitarNombreJugador() {
        esperandoNombreJugador = true;
        areaSalida.append("Ingrese el nombre del nuevo jugador:\n");
    }

    private void agregarJugador(String nombre) {
        if (nombre != null && !nombre.trim().isEmpty()) {
            Jugador jugador = new Jugador(nombre.trim());
            controlador.agregarJugador(jugador);
            areaSalida.append("Jugador " + nombre.trim() + " agregado.\n");
        } else {
            areaSalida.append("Nombre inválido. Intente nuevamente.\n");
            solicitarNombreJugador();
        }
    }

    private void mostrarJugadores() {
        List<Jugador> jugadores = controlador.obtenerJugadores();
        areaSalida.append("Lista de Jugadores:\n");
        for (Jugador jugador : jugadores) {
            areaSalida.append(" - " + jugador.getNombre() + "\n");
        }
    }







    @Override
	public void mostrarJugadorMano(Jugador jugador) {
        areaSalida.append("Jugador en mano: " + jugador.getNombre() + "\n");
    }

    @Override
	public void mostrarCartasJugador(List<Jugador> jugadores) {
        for (Jugador jugador : jugadores) {
            areaSalida.append("Cartas de " + jugador.getNombre() + ":\n");
            jugador.getCartas().forEach(carta -> 
                {
					try {
						areaSalida.append(carta.getValor() + " de " + carta.getPalo() + "\n");
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
            );
        }
    }

    @Override
	public void informarJugadoresInsuficientes() {
        areaSalida.append("La cantidad de jugadores es insuficiente para iniciar el juego.\n");
    }

    @Override
	public void informarCantJugadoresExcedidos() {
        areaSalida.append("La cantidad de jugadores excede el límite permitido.\n");
    }

    @Override
	public void mostrarGanador(List<Jugador> ganadores) {
        areaSalida.append("Ganador:\n");
        for (Jugador ganador : ganadores) {
            areaSalida.append(" - " + ganador.getNombre() + " con " + ganador.getResultadoValoresCartas() + "\n");
        }
    }

	@Override
	public void iniciar() {
		this.setVisible(true);
	}
}
