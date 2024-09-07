package ar.edu.unlu.poker.vista;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import ar.edu.unlu.poker.controlador.Controlador;
import ar.edu.unlu.poker.modelo.Jugador;

public class VistaConsolaSwing extends JFrame{
	
	private JTextArea areaSalida;
	private JTextField campoEntrada;
    private JButton btnAgregarJugador, btnMostrarJugadores, btnComenzarPartida;
    private Controlador controlador;
    
    public VistaConsolaSwing() {
    	
    	setTitle("Simulador de Consola - Póker");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Área de texto para la salida de la consola
        areaSalida = new JTextArea();
        areaSalida.setEditable(false); // No editable para simular salida de consola
        JScrollPane scrollPane = new JScrollPane(areaSalida);

        // Campo de texto para la entrada del usuario
        campoEntrada = new JTextField();
        campoEntrada.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = campoEntrada.getText();
                procesarEntrada(input);
                campoEntrada.setText("");
            }
        });

        // Panel para los botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(1, 3));

        btnAgregarJugador = new JButton("Agregar Jugador");
        btnAgregarJugador.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crearJugador();
            }
        });

        btnMostrarJugadores = new JButton("Mostrar Jugadores");
        btnMostrarJugadores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarJugadores();
            }
        });

        btnComenzarPartida = new JButton("Comenzar Partida");
        btnComenzarPartida.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlador.iniciarGame();
            }
        });

        panelBotones.add(btnAgregarJugador);
        panelBotones.add(btnMostrarJugadores);
        panelBotones.add(btnComenzarPartida);

        // Añadir componentes al JFrame
        add(scrollPane, BorderLayout.CENTER);
        add(campoEntrada, BorderLayout.SOUTH);
        add(panelBotones, BorderLayout.NORTH);
        
    }
    
    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
    }

    private void procesarEntrada(String input) {
        // Procesar la entrada de usuario desde el campo de texto
        areaSalida.append("> " + input + "\n");
    }

    private void crearJugador() {
        String nombre = JOptionPane.showInputDialog(this, "Ingrese el nombre del jugador:");
        if (nombre != null && !nombre.trim().isEmpty()) {
            Jugador jugador = new Jugador(nombre);
            controlador.agregarJugador(jugador);
            areaSalida.append("Jugador " + nombre + " agregado.\n");
        }
    }

    private void mostrarJugadores() {
        List<Jugador> jugadores = controlador.obtenerJugadores();
        areaSalida.append("Lista de Jugadores:\n");
        for (Jugador jugador : jugadores) {
            areaSalida.append(" - " + jugador.getNombre() + "\n");
        }
    }

    public void mostrarJugadorMano(Jugador jugador) {
        areaSalida.append("Jugador en mano: " + jugador.getNombre() + "\n");
    }

    public void mostrarCartasJugador(List<Jugador> jugadores) {
        for (Jugador jugador : jugadores) {
            areaSalida.append("Cartas de " + jugador.getNombre() + ":\n");
            jugador.getCartas().forEach(carta -> 
                areaSalida.append(carta.getValor() + " de " + carta.getPalo() + "\n")
            );
        }
    }
    
    public void informarJugadoresInsuficientes() {
        areaSalida.append("La cantidad de jugadores es insuficiente para iniciar el juego.\n");
    }

    public void informarCantJugadoresExcedidos() {
        areaSalida.append("La cantidad de jugadores excede el límite permitido.\n");
    }

    public void mostrarGanador(List<Jugador> ganadores) {
        areaSalida.append("Ganador:\n");
        for (Jugador ganador : ganadores) {
            areaSalida.append(" - " + ganador.getNombre() + " con " + ganador.getResultadoValoresCartas() + "\n");
        }
    }

}
