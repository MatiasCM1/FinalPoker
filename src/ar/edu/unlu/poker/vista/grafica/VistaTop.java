package ar.edu.unlu.poker.vista.grafica;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import ar.edu.unlu.poker.serializacion.stats.EstadisticasJugador;

public class VistaTop extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private int xMouse;
	private int yMouse;
	private JLabel lblNombreJugador1;
	private JLabel lblNombreJugador2;
	private JLabel lblNombreJugador3;
	private JLabel lblNombreJugador4;
	private JLabel lblNombreJugador5;
	private JLabel lblNombreJugador6;
	private JLabel lblNombreJugador7;
	private JLabel lblGanadasJugador_1;
	private JLabel lblGanadasJugador_2;
	private JLabel lblGanadasJugador_3;
	private JLabel lblGanadasJugador_4;
	private JLabel lblGanadasJugador_5;
	private JLabel lblGanadasJugador_6;
	private JLabel lblGanadasJugador_7;
	private JPanel panelMostrarJugadores;
	private JLabel lblGanadasJugador_8;
	private JLabel lblNombreJugador8;
	private JLabel lblNombreJugador9;
	private JLabel lblGanadasJugador_10;
	private JLabel lblNombreJugador10;
	private JLabel lblGanadasJugador_9;

	public VistaTop() {
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
		btnSalir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSalir.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				VistaGrafica.getInstance().volverMenuPrincipal();

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

		panelMostrarJugadores = new JPanel();
		panelMostrarJugadores.setBounds(203, 150, 743, 428);
		contentPane.add(panelMostrarJugadores);
		panelMostrarJugadores.setLayout(null);
																								
		JPanel panelJugador_10 = new JPanel();
		panelJugador_10.setLayout(null);
		panelJugador_10.setOpaque(false);
		panelJugador_10.setBounds(0, 378, 743, 37);
		panelMostrarJugadores.add(panelJugador_10);

		lblNombreJugador10 = new JLabel("");
		lblNombreJugador10.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombreJugador10.setForeground(Color.WHITE);
		lblNombreJugador10.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));
		lblNombreJugador10.setBounds(60, 0, 222, 37);
		panelJugador_10.add(lblNombreJugador10);

		lblGanadasJugador_10 = new JLabel("");
		lblGanadasJugador_10.setHorizontalAlignment(SwingConstants.CENTER);
		lblGanadasJugador_10.setForeground(Color.WHITE);
		lblGanadasJugador_10.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));
		lblGanadasJugador_10.setBounds(456, 0, 222, 37);
		panelJugador_10.add(lblGanadasJugador_10);

		JSeparator separados_jugadores_10 = new JSeparator();
		separados_jugadores_10.setBounds(23, 35, 689, 2);
		panelJugador_10.add(separados_jugadores_10);

		JPanel panelJugador_9 = new JPanel();
		panelJugador_9.setLayout(null);
		panelJugador_9.setOpaque(false);
		panelJugador_9.setBounds(0, 340, 743, 37);
		panelMostrarJugadores.add(panelJugador_9);

		lblGanadasJugador_9 = new JLabel("");
		lblGanadasJugador_9.setHorizontalAlignment(SwingConstants.CENTER);
		lblGanadasJugador_9.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));
		lblGanadasJugador_9.setBounds(614, 10, 46, 14);
		panelJugador_9.add(lblGanadasJugador_9);

		lblNombreJugador9 = new JLabel("");
		lblNombreJugador9.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombreJugador9.setForeground(Color.WHITE);
		lblNombreJugador9.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));
		lblNombreJugador9.setBounds(60, 0, 222, 37);
		panelJugador_9.add(lblNombreJugador9);

		lblGanadasJugador_9 = new JLabel("");
		lblGanadasJugador_9.setHorizontalAlignment(SwingConstants.CENTER);
		lblGanadasJugador_9.setForeground(Color.WHITE);
		lblGanadasJugador_9.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));
		lblGanadasJugador_9.setBounds(456, 0, 222, 37);
		panelJugador_9.add(lblGanadasJugador_9);

		JSeparator separados_jugadores_9 = new JSeparator();
		separados_jugadores_9.setBounds(23, 35, 689, 2);
		panelJugador_9.add(separados_jugadores_9);

		JPanel panelJugador_8 = new JPanel();
		panelJugador_8.setLayout(null);
		panelJugador_8.setOpaque(false);
		panelJugador_8.setBounds(0, 303, 743, 37);
		panelMostrarJugadores.add(panelJugador_8);

		lblGanadasJugador_8 = new JLabel("");
		lblGanadasJugador_8.setHorizontalAlignment(SwingConstants.CENTER);
		lblGanadasJugador_8.setForeground(Color.WHITE);
		lblGanadasJugador_8.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));
		lblGanadasJugador_8.setBounds(456, 0, 222, 37);
		panelJugador_8.add(lblGanadasJugador_8);

		lblNombreJugador8 = new JLabel("");
		lblNombreJugador8.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombreJugador8.setForeground(Color.WHITE);
		lblNombreJugador8.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));
		lblNombreJugador8.setBounds(60, 0, 222, 37);
		panelJugador_8.add(lblNombreJugador8);

		JSeparator separados_jugadores_8 = new JSeparator();
		separados_jugadores_8.setBounds(23, 35, 689, 2);
		panelJugador_8.add(separados_jugadores_8);

		JPanel panelJugador_7 = new JPanel();
		panelJugador_7.setBounds(0, 268, 743, 37);
		panelMostrarJugadores.add(panelJugador_7);
		panelJugador_7.setLayout(null);
		panelJugador_7.setOpaque(false);

		lblGanadasJugador_7 = new JLabel("");
		lblGanadasJugador_7.setHorizontalAlignment(SwingConstants.CENTER);
		lblGanadasJugador_7.setForeground(Color.WHITE);
		lblGanadasJugador_7.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));
		lblGanadasJugador_7.setBounds(456, 0, 222, 37);
		panelJugador_7.add(lblGanadasJugador_7);

		lblNombreJugador7 = new JLabel("");
		lblNombreJugador7.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombreJugador7.setForeground(Color.WHITE);
		lblNombreJugador7.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));
		lblNombreJugador7.setBounds(60, 0, 222, 37);
		panelJugador_7.add(lblNombreJugador7);

		JSeparator separados_jugadores_7 = new JSeparator();
		separados_jugadores_7.setBounds(23, 35, 689, 2);
		panelJugador_7.add(separados_jugadores_7);

		JPanel panelJugador_6 = new JPanel();
		panelJugador_6.setBounds(0, 235, 743, 37);
		panelMostrarJugadores.add(panelJugador_6);
		panelJugador_6.setLayout(null);
		panelJugador_6.setOpaque(false);

		lblNombreJugador6 = new JLabel("");
		lblNombreJugador6.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombreJugador6.setForeground(Color.WHITE);
		lblNombreJugador6.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));
		lblNombreJugador6.setBounds(60, 0, 222, 37);
		panelJugador_6.add(lblNombreJugador6);

		lblGanadasJugador_6 = new JLabel("");
		lblGanadasJugador_6.setHorizontalAlignment(SwingConstants.CENTER);
		lblGanadasJugador_6.setForeground(Color.WHITE);
		lblGanadasJugador_6.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));
		lblGanadasJugador_6.setBounds(456, 0, 222, 37);
		panelJugador_6.add(lblGanadasJugador_6);

		JSeparator separados_jugadores_6 = new JSeparator();
		separados_jugadores_6.setBounds(23, 35, 689, 2);
		panelJugador_6.add(separados_jugadores_6);

		JPanel panelJugador_5 = new JPanel();
		panelJugador_5.setBounds(0, 200, 743, 37);
		panelMostrarJugadores.add(panelJugador_5);
		panelJugador_5.setLayout(null);
		panelJugador_5.setOpaque(false);

		lblNombreJugador5 = new JLabel("");
		lblNombreJugador5.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombreJugador5.setForeground(Color.WHITE);
		lblNombreJugador5.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));
		lblNombreJugador5.setBounds(60, 0, 222, 37);
		panelJugador_5.add(lblNombreJugador5);

		lblGanadasJugador_5 = new JLabel("");
		lblGanadasJugador_5.setHorizontalAlignment(SwingConstants.CENTER);
		lblGanadasJugador_5.setForeground(Color.WHITE);
		lblGanadasJugador_5.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));
		lblGanadasJugador_5.setBounds(456, 0, 222, 37);
		panelJugador_5.add(lblGanadasJugador_5);

		JSeparator separados_jugadores_5 = new JSeparator();
		separados_jugadores_5.setBounds(23, 35, 689, 2);
		panelJugador_5.add(separados_jugadores_5);

		JPanel panelJugador_4 = new JPanel();
		panelJugador_4.setBounds(0, 163, 743, 37);
		panelMostrarJugadores.add(panelJugador_4);
		panelJugador_4.setLayout(null);
		panelJugador_4.setOpaque(false);

		lblNombreJugador4 = new JLabel("");
		lblNombreJugador4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombreJugador4.setForeground(Color.WHITE);
		lblNombreJugador4.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));
		lblNombreJugador4.setBounds(60, 0, 222, 37);
		panelJugador_4.add(lblNombreJugador4);

		lblGanadasJugador_4 = new JLabel("");
		lblGanadasJugador_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblGanadasJugador_4.setForeground(Color.WHITE);
		lblGanadasJugador_4.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));
		lblGanadasJugador_4.setBounds(456, 0, 222, 37);
		panelJugador_4.add(lblGanadasJugador_4);

		JSeparator separados_jugadores_4 = new JSeparator();
		separados_jugadores_4.setBounds(23, 35, 689, 2);
		panelJugador_4.add(separados_jugadores_4);

		JPanel panelJugador_3 = new JPanel();
		panelJugador_3.setBounds(0, 126, 743, 37);
		panelMostrarJugadores.add(panelJugador_3);
		panelJugador_3.setLayout(null);
		panelJugador_3.setOpaque(false);

		lblNombreJugador3 = new JLabel("");
		lblNombreJugador3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombreJugador3.setForeground(Color.WHITE);
		lblNombreJugador3.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));
		lblNombreJugador3.setBounds(60, 0, 222, 37);
		panelJugador_3.add(lblNombreJugador3);

		lblGanadasJugador_3 = new JLabel("");
		lblGanadasJugador_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblGanadasJugador_3.setForeground(Color.WHITE);
		lblGanadasJugador_3.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));
		lblGanadasJugador_3.setBounds(456, 0, 222, 37);
		panelJugador_3.add(lblGanadasJugador_3);

		JSeparator separados_jugadores_3 = new JSeparator();
		separados_jugadores_3.setBounds(23, 35, 689, 2);
		panelJugador_3.add(separados_jugadores_3);

		JPanel panelJugador_2 = new JPanel();
		panelJugador_2.setBounds(0, 90, 743, 37);
		panelMostrarJugadores.add(panelJugador_2);
		panelJugador_2.setLayout(null);
		panelJugador_2.setOpaque(false);

		JSeparator separados_jugadores_2 = new JSeparator();
		separados_jugadores_2.setBounds(23, 35, 689, 2);
		panelJugador_2.add(separados_jugadores_2);

		lblNombreJugador2 = new JLabel("");
		lblNombreJugador2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombreJugador2.setForeground(Color.WHITE);
		lblNombreJugador2.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));
		lblNombreJugador2.setBounds(60, 0, 222, 37);
		panelJugador_2.add(lblNombreJugador2);

		lblGanadasJugador_2 = new JLabel("");
		lblGanadasJugador_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblGanadasJugador_2.setForeground(Color.WHITE);
		lblGanadasJugador_2.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));
		lblGanadasJugador_2.setBounds(456, 0, 222, 37);
		panelJugador_2.add(lblGanadasJugador_2);

		JPanel panelJugador_1 = new JPanel();
		panelJugador_1.setBounds(0, 55, 743, 37);
		panelMostrarJugadores.add(panelJugador_1);
		panelJugador_1.setOpaque(false);
		panelJugador_1.setLayout(null);

		lblNombreJugador1 = new JLabel("");
		lblNombreJugador1.setBounds(60, 0, 222, 37);
		panelJugador_1.add(lblNombreJugador1);
		lblNombreJugador1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombreJugador1.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));
		lblNombreJugador1.setForeground(new Color(255, 255, 255));

		lblGanadasJugador_1 = new JLabel("");
		lblGanadasJugador_1.setBounds(456, 0, 222, 37);
		panelJugador_1.add(lblGanadasJugador_1);
		lblGanadasJugador_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblGanadasJugador_1.setForeground(Color.WHITE);
		lblGanadasJugador_1.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));

		JSeparator separados_jugadores = new JSeparator();
		separados_jugadores.setBounds(23, 35, 689, 2);
		panelJugador_1.add(separados_jugadores);

		JLabel lblTituloVictoriasJguadores = new JLabel("Victorias");
		lblTituloVictoriasJguadores.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 30));
		lblTituloVictoriasJguadores.setForeground(new Color(255, 255, 255));
		lblTituloVictoriasJguadores.setHorizontalAlignment(SwingConstants.CENTER);
		lblTituloVictoriasJguadores.setBounds(456, 0, 222, 44);
		panelMostrarJugadores.add(lblTituloVictoriasJguadores);

		JLabel lblTituloJugadores = new JLabel("Jugadores");
		lblTituloJugadores.setBounds(60, 0, 222, 44);
		panelMostrarJugadores.add(lblTituloJugadores);
		lblTituloJugadores.setForeground(new Color(255, 255, 255));
		lblTituloJugadores.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 30));
		lblTituloJugadores.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel lblJugadoresFondoMadera = new JLabel("New label");
		lblJugadoresFondoMadera.setBounds(0, 0, 743, 427);
		panelMostrarJugadores.add(lblJugadoresFondoMadera);
		lblJugadoresFondoMadera.setIcon(new ImageIcon(getClass().getResource("/imagenMadera.jpg")));

		JLabel lblTituloTop10 = new JLabel("TOP 10");
		lblTituloTop10.setHorizontalAlignment(SwingConstants.CENTER);

		lblTituloTop10.setForeground(Color.WHITE);
		lblTituloTop10.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 80));
		lblTituloTop10.setBounds(10, 32, 1101, 107);
		contentPane.add(lblTituloTop10);

		JLabel lblImagenFondoVerde = new JLabel("Fondo");
		lblImagenFondoVerde.setBounds(0, 0, 1121, 612);
		lblImagenFondoVerde.setIcon(new ImageIcon(getClass().getResource("/FondoVerdeInicio.jpg")));
		contentPane.add(lblImagenFondoVerde);
	}

	public void actualizarTabla(List<EstadisticasJugador> listaJugadores) {

		JLabel[] lblNombreJugador = { lblNombreJugador1, lblNombreJugador2, lblNombreJugador3, lblNombreJugador4,
				lblNombreJugador5, lblNombreJugador6, lblNombreJugador7, lblNombreJugador8, lblNombreJugador9,
				lblNombreJugador10 };

		JLabel[] lblVictoriasJugador = { lblGanadasJugador_1, lblGanadasJugador_2, lblGanadasJugador_3,
				lblGanadasJugador_4, lblGanadasJugador_5, lblGanadasJugador_6, lblGanadasJugador_7, lblGanadasJugador_8,
				lblGanadasJugador_9, lblGanadasJugador_10 };

		// Limpiar labels
		for (int i = 0; i < 10; i++) {
			lblNombreJugador[i].setText("");
			lblVictoriasJugador[i].setText("");
		}

		// Lleno la tabla
		for (int i = 0; i < listaJugadores.size() && i < 10; i++) {
			lblNombreJugador[i].setText(listaJugadores.get(i).getNombreJugador());
			lblVictoriasJugador[i].setText(String.valueOf(listaJugadores.get(i).getCantPartidasGanadas()));
		}

	}
}
