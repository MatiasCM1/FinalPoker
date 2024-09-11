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
    private boolean esperandoNombreJugador = true;
    private String nombreJugadorActual;

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
        campoEntrada.setPreferredSize(new Dimension(800, 30));
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

        // Mostrar mensaje inicial para pedir el nombre del jugador
        areaSalida.append("Ingrese su nombre para comenzar:\n");
        campoEntrada.requestFocusInWindow();

        setVisible(true);
    }

    @Override
    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
    }

    private void procesarEntrada(String input) {
        areaSalida.append("> " + input + "\n");

        // Solicitar el nombre del jugador al inicio
        if (esperandoNombreJugador) {
            nombreJugadorActual = input.trim();
            if (!nombreJugadorActual.isEmpty()) {
                Jugador nuevoJugador = new Jugador(nombreJugadorActual);
                controlador.agregarJugador(nuevoJugador);  // Agregar jugador al controlador
                setTitle("Simulador de Consola - Póker: " + nombreJugadorActual);
                areaSalida.append("Bienvenido, " + nombreJugadorActual + "!\n");
                mostrarOpciones();
                esperandoNombreJugador = false;
            } else {
                areaSalida.append("Nombre no válido. Por favor, ingrese un nombre para comenzar:\n");
            }
            return;
        }

        if (controlador == null) {
            areaSalida.append("Error: Controlador no configurado.\n");
            return;
        }

        switch (input.toLowerCase()) {
            case "1":
                mostrarJugadores();
                break;
            case "2":
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

    private void mostrarOpciones() {
        areaSalida.append("Seleccione una opción:\n");
        areaSalida.append("1 - Ver Lista de Jugadores\n");
        areaSalida.append("2 - Comenzar Juego\n");
        areaSalida.append("0 - Salir\n");
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
            if (jugador.getNombre().equals(nombreJugadorActual)) { // Mostrar solo cartas del jugador actual
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

