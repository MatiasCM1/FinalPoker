package ar.edu.unlu.poker.vista.grafica;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.Cursor;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class VistaMenuPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private int xMouse;
	private int yMouse;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VistaMenuPrincipal frame = new VistaMenuPrincipal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VistaMenuPrincipal() {
		setUndecorated(true);
		setResizable(false);
		setLocationByPlatform(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1121, 612);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panelBarraSuperior = new JPanel();
		panelBarraSuperior.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int x = e.getXOnScreen();
				int y = e.getYOnScreen();
				setLocation(x - xMouse, y - yMouse);
			}
		});
		panelBarraSuperior.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				xMouse = e.getX();
				yMouse = e.getY();
			}
		});
		panelBarraSuperior.setLayout(null);
		panelBarraSuperior.setBounds(0, 0, 1121, 21);
		contentPane.add(panelBarraSuperior);
		
		JButton btnSalir = new JButton("X");
		btnSalir.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnSalir.setOpaque(true);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnSalir.setOpaque(false);
			}
		});
		btnSalir.setOpaque(false);
		btnSalir.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 10));
		btnSalir.setBorder(null);
		btnSalir.setBackground(Color.RED);
		btnSalir.setBounds(1082, 1, 39, 19);
		panelBarraSuperior.add(btnSalir);
		
		JLabel lblFondoMaderaBarra = new JLabel("New label");
		lblFondoMaderaBarra.setIcon(new ImageIcon(getClass().getResource("/imagenMadera.jpg")));
		lblFondoMaderaBarra.setBounds(0, 0, 1121, 21);
		panelBarraSuperior.add(lblFondoMaderaBarra);
		
		JLabel lblTxtFondosDinamico = new JLabel("Fondos");
		lblTxtFondosDinamico.setHorizontalAlignment(SwingConstants.CENTER);
		lblTxtFondosDinamico.setForeground(Color.WHITE);
		lblTxtFondosDinamico.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));
		lblTxtFondosDinamico.setBounds(860, 136, 223, 43);
		contentPane.add(lblTxtFondosDinamico);
		
		JLabel lblTxtNombreDinamico = new JLabel("Nombre");
		lblTxtNombreDinamico.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));
		lblTxtNombreDinamico.setForeground(new Color(255, 255, 255));
		lblTxtNombreDinamico.setHorizontalAlignment(SwingConstants.CENTER);
		lblTxtNombreDinamico.setBounds(609, 138, 223, 43);
		contentPane.add(lblTxtNombreDinamico);
		
		JSeparator separator_fondos = new JSeparator();
		separator_fondos.setBounds(881, 125, 176, 2);
		contentPane.add(separator_fondos);
		
		JSeparator separator_nombre = new JSeparator();
		separator_nombre.setBounds(634, 125, 176, 2);
		contentPane.add(separator_nombre);
		
		JLabel lblFondosJugador = new JLabel("Fondos");
		lblFondosJugador.setHorizontalAlignment(SwingConstants.CENTER);
		lblFondosJugador.setForeground(Color.WHITE);
		lblFondosJugador.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 30));
		lblFondosJugador.setBounds(860, 81, 222, 46);
		contentPane.add(lblFondosJugador);
		
		JLabel lblNombreJudador = new JLabel("Nombre");
		lblNombreJudador.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombreJudador.setForeground(Color.WHITE);
		lblNombreJudador.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 30));
		lblNombreJudador.setBounds(610, 81, 222, 46);
		contentPane.add(lblNombreJudador);
		
		JButton btnSalirGrande = new JButton("Salir");
		btnSalirGrande.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnSalirGrande.setForeground(Color.black);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				btnSalirGrande.setForeground(Color.white);
			}
		});
		btnSalirGrande.setContentAreaFilled(false);
		btnSalirGrande.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSalirGrande.setOpaque(false);
		btnSalirGrande.setForeground(Color.WHITE);
		btnSalirGrande.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 30));
		btnSalirGrande.setBorder(null);
		btnSalirGrande.setBounds(25, 361, 374, 75);
		contentPane.add(btnSalirGrande);
		
		JButton btnComenzar = new JButton("Comenzar");
		btnComenzar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnComenzar.setForeground(Color.black);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				btnComenzar.setForeground(Color.white);
			}
		});
		btnComenzar.setContentAreaFilled(false);
		btnComenzar.setBorder(null);
		btnComenzar.setOpaque(false);
		btnComenzar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnComenzar.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 30));
		btnComenzar.setForeground(new Color(255, 255, 255));
		btnComenzar.setBounds(25, 229, 374, 75);
		contentPane.add(btnComenzar);
		
		JPanel panelMostrarJugadores = new JPanel();
		panelMostrarJugadores.setBounds(610, 209, 472, 328);
		contentPane.add(panelMostrarJugadores);
		panelMostrarJugadores.setLayout(null);
		
		JPanel panelJugador_7 = new JPanel();
		panelJugador_7.setBounds(0, 268, 472, 37);
		panelMostrarJugadores.add(panelJugador_7);
		panelJugador_7.setLayout(null);
		panelJugador_7.setOpaque(false);
		
		JLabel lblNombreJugador_7 = new JLabel("Jugador 7");
		lblNombreJugador_7.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombreJugador_7.setForeground(Color.WHITE);
		lblNombreJugador_7.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));
		lblNombreJugador_7.setBounds(10, 0, 222, 37);
		panelJugador_7.add(lblNombreJugador_7);
		
		JLabel lblFondosJugador_7 = new JLabel("Fondos Jugador 7");
		lblFondosJugador_7.setHorizontalAlignment(SwingConstants.CENTER);
		lblFondosJugador_7.setForeground(Color.WHITE);
		lblFondosJugador_7.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));
		lblFondosJugador_7.setBounds(240, 0, 222, 37);
		panelJugador_7.add(lblFondosJugador_7);
		
		JSeparator separados_jugadores_7 = new JSeparator();
		separados_jugadores_7.setBounds(22, 35, 428, 2);
		panelJugador_7.add(separados_jugadores_7);
		
		JPanel panelJugador_6 = new JPanel();
		panelJugador_6.setBounds(0, 235, 472, 37);
		panelMostrarJugadores.add(panelJugador_6);
		panelJugador_6.setLayout(null);
		panelJugador_6.setOpaque(false);
		
		JLabel lblNombreJugador_6 = new JLabel("Jugador 6");
		lblNombreJugador_6.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombreJugador_6.setForeground(Color.WHITE);
		lblNombreJugador_6.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));
		lblNombreJugador_6.setBounds(10, 0, 222, 37);
		panelJugador_6.add(lblNombreJugador_6);
		
		JLabel lblFondosJugador_6 = new JLabel("Fondos Jugador 6");
		lblFondosJugador_6.setHorizontalAlignment(SwingConstants.CENTER);
		lblFondosJugador_6.setForeground(Color.WHITE);
		lblFondosJugador_6.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));
		lblFondosJugador_6.setBounds(240, 0, 222, 37);
		panelJugador_6.add(lblFondosJugador_6);
		
		JSeparator separados_jugadores_6 = new JSeparator();
		separados_jugadores_6.setBounds(22, 35, 428, 2);
		panelJugador_6.add(separados_jugadores_6);
		
		JPanel panelJugador_5 = new JPanel();
		panelJugador_5.setBounds(0, 200, 472, 37);
		panelMostrarJugadores.add(panelJugador_5);
		panelJugador_5.setLayout(null);
		panelJugador_5.setOpaque(false);
		
		JLabel lblNombreJugador_5 = new JLabel("Jugador 5");
		lblNombreJugador_5.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombreJugador_5.setForeground(Color.WHITE);
		lblNombreJugador_5.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));
		lblNombreJugador_5.setBounds(10, 0, 222, 37);
		panelJugador_5.add(lblNombreJugador_5);
		
		JLabel lblFondosJugador_5 = new JLabel("Fondos Jugador 5");
		lblFondosJugador_5.setHorizontalAlignment(SwingConstants.CENTER);
		lblFondosJugador_5.setForeground(Color.WHITE);
		lblFondosJugador_5.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));
		lblFondosJugador_5.setBounds(240, 0, 222, 37);
		panelJugador_5.add(lblFondosJugador_5);
		
		JSeparator separados_jugadores_5 = new JSeparator();
		separados_jugadores_5.setBounds(22, 35, 428, 2);
		panelJugador_5.add(separados_jugadores_5);
		
		JPanel panelJugador_4 = new JPanel();
		panelJugador_4.setBounds(0, 163, 472, 37);
		panelMostrarJugadores.add(panelJugador_4);
		panelJugador_4.setLayout(null);
		panelJugador_4.setOpaque(false);
		
		JLabel lblNombreJugador_4 = new JLabel("Jugador 4");
		lblNombreJugador_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombreJugador_4.setForeground(Color.WHITE);
		lblNombreJugador_4.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));
		lblNombreJugador_4.setBounds(10, 0, 222, 37);
		panelJugador_4.add(lblNombreJugador_4);
		
		JLabel lblFondosJugador_4 = new JLabel("Fondos Jugador 4");
		lblFondosJugador_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblFondosJugador_4.setForeground(Color.WHITE);
		lblFondosJugador_4.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));
		lblFondosJugador_4.setBounds(240, 0, 222, 37);
		panelJugador_4.add(lblFondosJugador_4);
		
		JSeparator separados_jugadores_4 = new JSeparator();
		separados_jugadores_4.setBounds(22, 35, 428, 2);
		panelJugador_4.add(separados_jugadores_4);
		
		JPanel panelJugador_3 = new JPanel();
		panelJugador_3.setBounds(0, 126, 472, 37);
		panelMostrarJugadores.add(panelJugador_3);
		panelJugador_3.setLayout(null);
		panelJugador_3.setOpaque(false);
		
		JLabel lblNombreJugador_3 = new JLabel("Jugador 3");
		lblNombreJugador_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombreJugador_3.setForeground(Color.WHITE);
		lblNombreJugador_3.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));
		lblNombreJugador_3.setBounds(10, 0, 222, 37);
		panelJugador_3.add(lblNombreJugador_3);
		
		JLabel lblFondosJugador_3 = new JLabel("Fondos Jugador 3");
		lblFondosJugador_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblFondosJugador_3.setForeground(Color.WHITE);
		lblFondosJugador_3.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));
		lblFondosJugador_3.setBounds(240, 0, 222, 37);
		panelJugador_3.add(lblFondosJugador_3);
		
		JSeparator separados_jugadores_3 = new JSeparator();
		separados_jugadores_3.setBounds(22, 35, 428, 2);
		panelJugador_3.add(separados_jugadores_3);
		
		JPanel panelJugador_2 = new JPanel();
		panelJugador_2.setBounds(0, 90, 472, 37);
		panelMostrarJugadores.add(panelJugador_2);
		panelJugador_2.setLayout(null);
		panelJugador_2.setOpaque(false);
		
		JLabel lblNombreJugador_2 = new JLabel("Jugador 2");
		lblNombreJugador_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombreJugador_2.setForeground(Color.WHITE);
		lblNombreJugador_2.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));
		lblNombreJugador_2.setBounds(10, 0, 222, 37);
		panelJugador_2.add(lblNombreJugador_2);
		
		JLabel lblFondosJugador_2 = new JLabel("Fondos Jugador 2");
		lblFondosJugador_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblFondosJugador_2.setForeground(Color.WHITE);
		lblFondosJugador_2.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));
		lblFondosJugador_2.setBounds(240, 0, 222, 37);
		panelJugador_2.add(lblFondosJugador_2);
		
		JSeparator separados_jugadores_2 = new JSeparator();
		separados_jugadores_2.setBounds(23, 35, 428, 2);
		panelJugador_2.add(separados_jugadores_2);
		
		JPanel panelJugador_1 = new JPanel();
		panelJugador_1.setBounds(0, 35, 472, 55);
		panelMostrarJugadores.add(panelJugador_1);
		panelJugador_1.setOpaque(false);
		panelJugador_1.setLayout(null);
		
		JLabel lblNombreJugador_1 = new JLabel("Jugador 1");
		lblNombreJugador_1.setBounds(10, 11, 222, 43);
		panelJugador_1.add(lblNombreJugador_1);
		lblNombreJugador_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombreJugador_1.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));
		lblNombreJugador_1.setForeground(new Color(255, 255, 255));
		
		JLabel lblFondosJugador_1 = new JLabel("Fondos Jugador 1");
		lblFondosJugador_1.setBounds(240, 11, 222, 43);
		panelJugador_1.add(lblFondosJugador_1);
		lblFondosJugador_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblFondosJugador_1.setForeground(Color.WHITE);
		lblFondosJugador_1.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));
		
		JSeparator separados_jugadores = new JSeparator();
		separados_jugadores.setBounds(23, 52, 428, 2);
		panelJugador_1.add(separados_jugadores);
		
		JLabel lblTituloJugadores = new JLabel("Jugadores");
		lblTituloJugadores.setBounds(0, 0, 472, 44);
		panelMostrarJugadores.add(lblTituloJugadores);
		lblTituloJugadores.setForeground(new Color(255, 255, 255));
		lblTituloJugadores.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 30));
		lblTituloJugadores.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblJugadoresFondoMadera = new JLabel("New label");
		lblJugadoresFondoMadera.setBounds(0, 0, 472, 328);
		panelMostrarJugadores.add(lblJugadoresFondoMadera);
		lblJugadoresFondoMadera.setIcon(new ImageIcon(getClass().getResource("/imagenMadera.jpg")));
		
		JLabel lblFondosJugadorFondomadera_1 = new JLabel("New label");
		lblFondosJugadorFondomadera_1.setIcon(new ImageIcon(getClass().getResource("/imagenMadera.jpg")));
		lblFondosJugadorFondomadera_1.setBounds(860, 136, 222, 43);
		contentPane.add(lblFondosJugadorFondomadera_1);
		
		JLabel lblNombreJugadorFondomadera = new JLabel("New label");
		lblNombreJugadorFondomadera.setIcon(new ImageIcon(getClass().getResource("/imagenMadera.jpg")));
		lblNombreJugadorFondomadera.setBounds(609, 136, 222, 43);
		contentPane.add(lblNombreJugadorFondomadera);
		
		JLabel lblBtnSalirFondomadera = new JLabel("New label");
		lblBtnSalirFondomadera.setIcon(new ImageIcon(getClass().getResource("/imagenMadera.jpg")));
		lblBtnSalirFondomadera.setBounds(25, 361, 374, 75);
		contentPane.add(lblBtnSalirFondomadera);
		
		JLabel lblBtnComenzarFondomadera = new JLabel("New label");
		lblBtnComenzarFondomadera.setIcon(new ImageIcon(getClass().getResource("/imagenMadera.jpg")));
		lblBtnComenzarFondomadera.setBounds(25, 229, 374, 75);
		contentPane.add(lblBtnComenzarFondomadera);
		
		JLabel lblNombreJuegoPoker = new JLabel("POKER");
		lblNombreJuegoPoker.setForeground(Color.WHITE);
		lblNombreJuegoPoker.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 80));
		lblNombreJuegoPoker.setBounds(71, 56, 292, 107);
		contentPane.add(lblNombreJuegoPoker);
		
		JLabel lblImagenFondoVerde = new JLabel("Fondo");
		lblImagenFondoVerde.setBounds(0, 0, 1121, 612);
		lblImagenFondoVerde.setIcon(new ImageIcon(getClass().getResource("/FondoVerdeInicio.jpg")));
		contentPane.add(lblImagenFondoVerde);
	}
}
