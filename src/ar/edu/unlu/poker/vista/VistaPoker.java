package ar.edu.unlu.poker.vista;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import ar.edu.unlu.poker.controlador.Controlador;
import ar.edu.unlu.poker.modelo.Jugador;

import javax.swing.JButton;

public class VistaPoker extends JFrame {
	 private Controlador controlador;
	    private JTextArea areaJugadores;
	    private JButton btnAgregarJugador;
	    private JButton btnMostrarJugadores;
	    private JButton btnComenzarPartida;
	    
	    public VistaPoker() {
	        setTitle("Juego de Poker");
	        setSize(400, 300);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setLayout(null);

	        btnAgregarJugador = new JButton("Agregar Jugador");
	        btnAgregarJugador.setBounds(10, 10, 150, 30);
	        add(btnAgregarJugador);

	        btnMostrarJugadores = new JButton("Mostrar Jugadores");
	        btnMostrarJugadores.setBounds(10, 50, 150, 30);
	        add(btnMostrarJugadores);

	        btnComenzarPartida = new JButton("Comenzar Partida");
	        btnComenzarPartida.setBounds(10, 90, 150, 30);
	        add(btnComenzarPartida);

	        areaJugadores = new JTextArea();
	        areaJugadores.setBounds(200, 10, 180, 200);
	        add(areaJugadores);

	        // Acciones de los botones
	        btnAgregarJugador.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                String nombre = JOptionPane.showInputDialog("Ingrese el nombre del jugador:");
	                if (nombre != null && !nombre.isEmpty()) {
	                    Jugador jugador = new Jugador(nombre);
	                    controlador.agregarJugador(jugador);
	                }
	            }
	        });

	        btnMostrarJugadores.addActionListener((ActionListener) new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                mostrarJugadores();
	            }

				
	        });

	        btnComenzarPartida.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                controlador.iniciarGame();
	            }
	        });
	    }

	    public void setControlador(Controlador controlador) {
	        this.controlador = controlador;
	    }

	    public void mostrarJugadores() {
	        areaJugadores.setText("");
	        for (Jugador j : controlador.obtenerJugadores()) {
	            areaJugadores.append("Nombre: " + j.getNombre() + "\n");
	        }
	    }
}
