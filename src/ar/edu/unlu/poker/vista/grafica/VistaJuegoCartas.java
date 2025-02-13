package ar.edu.unlu.poker.vista.grafica;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import ar.edu.unlu.poker.modelo.Carta;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import java.awt.Toolkit;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.List;
import java.awt.Cursor;
import javax.swing.ScrollPaneConstants;

public class VistaJuegoCartas extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private int xMouse;
	private int yMouse;
	private JTextArea textAreaNotificaciones;
	private JLabel lblCarta1;
	private JLabel lblCarta2;
	private JLabel lblCarta3;
	private JLabel lblCarta4;
	private JLabel lblCarta5;
	private JLabel lblNombreJugadorVentana;
	private JPanel panelOpcionesSiguienteRondaJuego;
	private JPanel panelDescartarCartas;
	private JButton btnDescartarCarta1;
	private JButton btnDescartarCarta2;
	private JButton btnDescartarCarta3;
	private JButton btnDescartarCarta4;
	private JButton btnDescartarCarta5;
	private JLabel lblFondoDescartarBtnC1;
	private JLabel lblFondoDescartarBtnC2;
	private JLabel lblFondoDescartarBtnC3;
	private JLabel lblFondoDescartarBtnC4;
	private JLabel lblFondoDescartarBtnC5;
	private JPanel panelErrores;
	private JLabel lblErrorFondosInsuficientes;
	private JLabel lblErrorJugadoresInsuficientes;

	/*
	 public static void main(String[] args) { EventQueue.invokeLater(new
	  Runnable() { public void run() { try { VistaJuegoCartas frame = new
	  VistaJuegoCartas(); frame.setVisible(true);
	  
	  } catch (Exception e) { e.printStackTrace(); } } }); }
	 */

	public VistaJuegoCartas() {
		setUndecorated(true);
		setForeground(new Color(255, 255, 255));
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

		panelOpcionesSiguienteRondaJuego = new JPanel();
		panelOpcionesSiguienteRondaJuego.setVisible(false);

		panelDescartarCartas = new JPanel();
		panelDescartarCartas.setVisible(false);
		
		panelErrores = new JPanel();
		panelErrores.setVisible(false);
		panelErrores.setBounds(143, 46, 459, 222);
		contentPane.add(panelErrores);
		panelErrores.setLayout(null);
		
		JButton btnSalirPanelErrores = new JButton("X");
		btnSalirPanelErrores.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnSalirPanelErrores.setForeground(Color.red);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnSalirPanelErrores.setForeground(Color.white);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				panelOpcionesSiguienteRondaJuego.setVisible(true);
				lblErrorFondosInsuficientes.setVisible(false);
				lblErrorJugadoresInsuficientes.setVisible(false);
				panelErrores.setVisible(false);
			}
		});
		
		lblErrorFondosInsuficientes = new JLabel("Fondos insuficientes");
		lblErrorFondosInsuficientes.setVisible(false);
		
		lblErrorJugadoresInsuficientes = new JLabel("Jugadores insuficientes");
		lblErrorJugadoresInsuficientes.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));
		lblErrorJugadoresInsuficientes.setForeground(new Color(255, 0, 0));
		lblErrorJugadoresInsuficientes.setHorizontalAlignment(SwingConstants.CENTER);
		lblErrorJugadoresInsuficientes.setVisible(false);
		lblErrorJugadoresInsuficientes.setBounds(10, 106, 439, 40);
		panelErrores.add(lblErrorJugadoresInsuficientes);
		lblErrorFondosInsuficientes.setHorizontalAlignment(SwingConstants.CENTER);
		lblErrorFondosInsuficientes.setForeground(new Color(255, 0, 0));
		lblErrorFondosInsuficientes.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));
		lblErrorFondosInsuficientes.setBounds(10, 106, 439, 40);
		panelErrores.add(lblErrorFondosInsuficientes);
		
		JLabel lblFondoNegroMensajeError = new JLabel("");
		lblFondoNegroMensajeError.setIcon(new ImageIcon("C:\\Users\\Colo\\eclipse-workspace\\FinalPoker\\resources\\fondoNegro.jpg"));
		lblFondoNegroMensajeError.setBounds(10, 106, 439, 40);
		panelErrores.add(lblFondoNegroMensajeError);
		btnSalirPanelErrores.setForeground(new Color(255, 255, 255));
		btnSalirPanelErrores.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSalirPanelErrores.setContentAreaFilled(false);
		btnSalirPanelErrores.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
		btnSalirPanelErrores.setBounds(410, 0, 49, 40);
		panelErrores.add(btnSalirPanelErrores);
		
		JLabel lblErroresTitulo = new JLabel("Error");
		lblErroresTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblErroresTitulo.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 30));
		lblErroresTitulo.setForeground(new Color(255, 255, 255));
		lblErroresTitulo.setBounds(10, 11, 439, 47);
		panelErrores.add(lblErroresTitulo);
		
		JLabel lblFondoMaderaErrores = new JLabel("");
		lblFondoMaderaErrores.setIcon(new ImageIcon(getClass().getResource("/imagenMadera.jpg")));
		lblFondoMaderaErrores.setBounds(0, 0, 459, 223);
		panelErrores.add(lblFondoMaderaErrores);
		panelDescartarCartas.setOpaque(false);
		panelDescartarCartas.setBounds(12, 458, 1099, 154);
		contentPane.add(panelDescartarCartas);
		panelDescartarCartas.setLayout(null);

		btnDescartarCarta1 = new JButton("Descartar");
		btnDescartarCarta1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnDescartarCarta1.setForeground(Color.black);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnDescartarCarta1.setForeground(Color.white);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				btnDescartarCarta1.setVisible(false);
				lblFondoDescartarBtnC1.setVisible(false);
				VistaGrafica.getInstance().cartaADescartarSeleccionada(0);
			}
		});

		btnDescartarCarta2 = new JButton("Descartar");
		btnDescartarCarta2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnDescartarCarta2.setForeground(Color.black);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnDescartarCarta2.setForeground(Color.white);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				btnDescartarCarta2.setVisible(false);
				lblFondoDescartarBtnC2.setVisible(false);
				VistaGrafica.getInstance().cartaADescartarSeleccionada(1);
			}
		});

		btnDescartarCarta3 = new JButton("Descartar");
		btnDescartarCarta3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnDescartarCarta3.setForeground(Color.black);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnDescartarCarta3.setForeground(Color.white);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				btnDescartarCarta3.setVisible(false);
				lblFondoDescartarBtnC3.setVisible(false);
				VistaGrafica.getInstance().cartaADescartarSeleccionada(2);
			}
		});

		btnDescartarCarta4 = new JButton("Descartar");
		btnDescartarCarta4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnDescartarCarta4.setForeground(Color.black);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnDescartarCarta4.setForeground(Color.white);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				btnDescartarCarta4.setVisible(false);
				lblFondoDescartarBtnC4.setVisible(false);
				VistaGrafica.getInstance().cartaADescartarSeleccionada(3);
			}
		});

		btnDescartarCarta5 = new JButton("Descartar");
		btnDescartarCarta5.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnDescartarCarta5.setForeground(Color.black);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnDescartarCarta5.setForeground(Color.white);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				btnDescartarCarta5.setVisible(false);
				lblFondoDescartarBtnC5.setVisible(false);
				VistaGrafica.getInstance().cartaADescartarSeleccionada(4);
			}
		});
		btnDescartarCarta5.setContentAreaFilled(false);
		btnDescartarCarta5.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnDescartarCarta5.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 13));
		btnDescartarCarta5.setForeground(new Color(255, 255, 255));
		btnDescartarCarta5.setBounds(534, 123, 89, 23);
		panelDescartarCartas.add(btnDescartarCarta5);

		lblFondoDescartarBtnC5 = new JLabel("");
		lblFondoDescartarBtnC5
				.setIcon(new ImageIcon(getClass().getResource("/cartelMadera3.png")));
		lblFondoDescartarBtnC5.setHorizontalAlignment(SwingConstants.CENTER);
		lblFondoDescartarBtnC5.setBounds(534, 123, 89, 23);
		panelDescartarCartas.add(lblFondoDescartarBtnC5);
		btnDescartarCarta4.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 13));
		btnDescartarCarta4.setForeground(new Color(255, 255, 255));
		btnDescartarCarta4.setContentAreaFilled(false);
		btnDescartarCarta4.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnDescartarCarta4.setBounds(413, 123, 89, 23);
		panelDescartarCartas.add(btnDescartarCarta4);

		lblFondoDescartarBtnC4 = new JLabel("");
		lblFondoDescartarBtnC4
				.setIcon(new ImageIcon(getClass().getResource("/cartelMadera3.png")));
		lblFondoDescartarBtnC4.setHorizontalAlignment(SwingConstants.CENTER);
		lblFondoDescartarBtnC4.setBounds(413, 123, 89, 23);
		panelDescartarCartas.add(lblFondoDescartarBtnC4);
		btnDescartarCarta3.setContentAreaFilled(false);
		btnDescartarCarta3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnDescartarCarta3.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 13));
		btnDescartarCarta3.setForeground(new Color(255, 255, 255));
		btnDescartarCarta3.setBounds(284, 123, 89, 23);
		panelDescartarCartas.add(btnDescartarCarta3);

		lblFondoDescartarBtnC3 = new JLabel("");
		lblFondoDescartarBtnC3
				.setIcon(new ImageIcon(getClass().getResource("/cartelMadera3.png")));
		lblFondoDescartarBtnC3.setHorizontalAlignment(SwingConstants.CENTER);
		lblFondoDescartarBtnC3.setBounds(284, 123, 89, 23);
		panelDescartarCartas.add(lblFondoDescartarBtnC3);
		btnDescartarCarta2.setBounds(154, 123, 89, 23);
		panelDescartarCartas.add(btnDescartarCarta2);
		btnDescartarCarta2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnDescartarCarta2.setContentAreaFilled(false);
		btnDescartarCarta2.setForeground(new Color(255, 255, 255));
		btnDescartarCarta2.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 13));

		lblFondoDescartarBtnC2 = new JLabel("");
		lblFondoDescartarBtnC2
				.setIcon(new ImageIcon(getClass().getResource("/cartelMadera3.png")));
		lblFondoDescartarBtnC2.setHorizontalAlignment(SwingConstants.CENTER);
		lblFondoDescartarBtnC2.setBounds(154, 123, 89, 23);
		panelDescartarCartas.add(lblFondoDescartarBtnC2);
		btnDescartarCarta1.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 13));
		btnDescartarCarta1.setForeground(new Color(255, 255, 255));
		btnDescartarCarta1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnDescartarCarta1.setContentAreaFilled(false);
		btnDescartarCarta1.setBounds(25, 123, 89, 23);
		panelDescartarCartas.add(btnDescartarCarta1);

		lblFondoDescartarBtnC1 = new JLabel("");
		lblFondoDescartarBtnC1
				.setIcon(new ImageIcon(getClass().getResource("/cartelMadera3.png")));
		lblFondoDescartarBtnC1.setHorizontalAlignment(SwingConstants.CENTER);
		lblFondoDescartarBtnC1.setBounds(25, 123, 89, 23);
		panelDescartarCartas.add(lblFondoDescartarBtnC1);

		JButton btnTerminarDescartes = new JButton("Finalizar descartes");
		btnTerminarDescartes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnTerminarDescartes.setForeground(Color.black);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnTerminarDescartes.setForeground(Color.white);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				panelDescartarCartas.setVisible(false);
				VistaGrafica.getInstance().finalizarFaseDescarte();
			}
		});
		btnTerminarDescartes.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 30));
		btnTerminarDescartes.setForeground(new Color(255, 255, 255));
		btnTerminarDescartes.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnTerminarDescartes.setContentAreaFilled(false);
		btnTerminarDescartes.setBounds(697, -2, 333, 60);
		panelDescartarCartas.add(btnTerminarDescartes);

		JLabel lblFondoMaderaBtnDescartes = new JLabel("New label");
		lblFondoMaderaBtnDescartes
				.setIcon(new ImageIcon(getClass().getResource("/imagenMadera.jpg")));
		lblFondoMaderaBtnDescartes.setBounds(0, 115, 664, 39);
		panelDescartarCartas.add(lblFondoMaderaBtnDescartes);

		JLabel lblFondoMaderaBtnTerminarDescartes = new JLabel("New label");
		lblFondoMaderaBtnTerminarDescartes
				.setIcon(new ImageIcon(getClass().getResource("/imagenMadera.jpg")));
		lblFondoMaderaBtnTerminarDescartes.setBounds(697, -2, 333, 60);
		panelDescartarCartas.add(lblFondoMaderaBtnTerminarDescartes);
		panelOpcionesSiguienteRondaJuego.setBounds(63, 67, 518, 200);
		contentPane.add(panelOpcionesSiguienteRondaJuego);
		panelOpcionesSiguienteRondaJuego.setLayout(null);

		JButton btnSalirDelJuego = new JButton("Salir");
		btnSalirDelJuego.setBorderPainted(false);
		btnSalirDelJuego.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnSalirDelJuego.setForeground(Color.black);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnSalirDelJuego.setForeground(Color.white);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				panelOpcionesSiguienteRondaJuego.setVisible(false);
				VistaGrafica.getInstance().jugadorSaleDelJuegoPostPrimeraPartida();
				System.exit(0);
			}
		});

		JButton btnSeguirJugadorNuevaRonda = new JButton("Seguir jugando");
		btnSeguirJugadorNuevaRonda.setContentAreaFilled(false);
		btnSeguirJugadorNuevaRonda.setBackground(new Color(255, 255, 255));
		btnSeguirJugadorNuevaRonda.setBorderPainted(false);
		btnSeguirJugadorNuevaRonda.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnSeguirJugadorNuevaRonda.setForeground(Color.black);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnSeguirJugadorNuevaRonda.setForeground(Color.white);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				panelOpcionesSiguienteRondaJuego.setVisible(false);
				VistaGrafica.getInstance().listoParaIniciarJuegoPostPrimerPartido();
			}
		});

		JButton btnVolverMenuPrincipal = new JButton("Menu principal");
		btnVolverMenuPrincipal.setBorderPainted(false);
		btnVolverMenuPrincipal.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnVolverMenuPrincipal.setForeground(Color.black);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnVolverMenuPrincipal.setForeground(Color.white);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				panelOpcionesSiguienteRondaJuego.setVisible(false);
				VistaGrafica.getInstance().volverMenuPrincipal();
			}
		});
		btnVolverMenuPrincipal.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnVolverMenuPrincipal.setForeground(new Color(255, 255, 255));
		btnVolverMenuPrincipal.setContentAreaFilled(false);
		btnVolverMenuPrincipal.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 17));
		btnVolverMenuPrincipal.setBounds(182, 114, 156, 59);
		panelOpcionesSiguienteRondaJuego.add(btnVolverMenuPrincipal);

		JLabel lblBtnFondoMaderaMenuPrincipal = new JLabel("");
		lblBtnFondoMaderaMenuPrincipal.setHorizontalAlignment(SwingConstants.CENTER);
		lblBtnFondoMaderaMenuPrincipal
				.setIcon(new ImageIcon(getClass().getResource("/cartelMadera.png")));
		lblBtnFondoMaderaMenuPrincipal.setBounds(182, 114, 156, 59);
		panelOpcionesSiguienteRondaJuego.add(lblBtnFondoMaderaMenuPrincipal);
		btnSeguirJugadorNuevaRonda.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 18));
		btnSeguirJugadorNuevaRonda.setForeground(new Color(255, 255, 255));
		btnSeguirJugadorNuevaRonda.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSeguirJugadorNuevaRonda.setBounds(346, 114, 156, 59);
		panelOpcionesSiguienteRondaJuego.add(btnSeguirJugadorNuevaRonda);

		JLabel lblFondoMaderaBotonSeguirJugando = new JLabel("");
		lblFondoMaderaBotonSeguirJugando
				.setIcon(new ImageIcon(getClass().getResource("/cartelMadera.png")));
		lblFondoMaderaBotonSeguirJugando.setHorizontalAlignment(SwingConstants.CENTER);
		lblFondoMaderaBotonSeguirJugando.setBounds(346, 114, 156, 59);
		panelOpcionesSiguienteRondaJuego.add(lblFondoMaderaBotonSeguirJugando);
		btnSalirDelJuego.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSalirDelJuego.setContentAreaFilled(false);
		btnSalirDelJuego.setForeground(new Color(255, 255, 255));
		btnSalirDelJuego.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));
		btnSalirDelJuego.setBounds(10, 114, 156, 59);
		panelOpcionesSiguienteRondaJuego.add(btnSalirDelJuego);

		JLabel lblBtnFondoMaderaClaraSalir = new JLabel("");
		lblBtnFondoMaderaClaraSalir.setHorizontalAlignment(SwingConstants.CENTER);
		lblBtnFondoMaderaClaraSalir
				.setIcon(new ImageIcon(getClass().getResource("/cartelMadera.png")));
		lblBtnFondoMaderaClaraSalir.setBounds(10, 114, 156, 59);
		panelOpcionesSiguienteRondaJuego.add(lblBtnFondoMaderaClaraSalir);

		JLabel lblRondaTerminada = new JLabel("Ronda terminada");
		lblRondaTerminada.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 45));
		lblRondaTerminada.setForeground(new Color(255, 255, 255));
		lblRondaTerminada.setHorizontalAlignment(SwingConstants.CENTER);
		lblRondaTerminada.setBounds(10, 11, 498, 59);
		panelOpcionesSiguienteRondaJuego.add(lblRondaTerminada);

		JLabel lblFondoMaderaSigRonda = new JLabel("");
		lblFondoMaderaSigRonda
				.setIcon(new ImageIcon(getClass().getResource("/imagenMadera.jpg")));
		lblFondoMaderaSigRonda.setBounds(0, 0, 518, 199);
		panelOpcionesSiguienteRondaJuego.add(lblFondoMaderaSigRonda);
		panelBarraSuperior.setLayout(null);
		panelBarraSuperior.setBounds(0, 0, 1121, 21);
		contentPane.add(panelBarraSuperior);

		lblNombreJugadorVentana = new JLabel("");
		lblNombreJugadorVentana.setBounds(10, -1, 319, 21);
		panelBarraSuperior.add(lblNombreJugadorVentana);
		lblNombreJugadorVentana.setForeground(new Color(255, 255, 255));
		lblNombreJugadorVentana.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 13));

		JButton btnSalir = new JButton("X");
		btnSalir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSalir.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				VistaGrafica.getInstance().jugadorSaleDelJuegoPostPrimeraPartida();
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

		JPanel panelNotificaciones = new JPanel();
		panelNotificaciones.setBounds(639, 50, 472, 301);
		contentPane.add(panelNotificaciones);
		panelNotificaciones.setLayout(null);

		textAreaNotificaciones = new JTextArea();
		textAreaNotificaciones.setWrapStyleWord(true);
		textAreaNotificaciones.setLineWrap(true);
		textAreaNotificaciones.setEditable(false);
		textAreaNotificaciones.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 13));
		textAreaNotificaciones.setForeground(new Color(255, 255, 255));
		textAreaNotificaciones.setBounds(10, 11, 452, 279);
		panelNotificaciones.add(textAreaNotificaciones);
		textAreaNotificaciones.setBackground(new Color(0, 0, 0));
		textAreaNotificaciones.setBorder(null);

		JScrollPane scrollBar = new JScrollPane(textAreaNotificaciones);
		scrollBar.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollBar.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollBar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		scrollBar.setBounds(10, 11, 452, 279);
		panelNotificaciones.add(scrollBar);

		JLabel lblFondoMaderaNotificaciones = new JLabel("");
		lblFondoMaderaNotificaciones.setBounds(0, 0, 472, 301);
		panelNotificaciones.add(lblFondoMaderaNotificaciones);
		lblFondoMaderaNotificaciones.setIcon(new ImageIcon(getClass().getResource("/imagenMadera.jpg")));

		JPanel panelCartas = new JPanel();
		panelCartas.setOpaque(false);
		panelCartas.setBounds(10, 362, 667, 239);
		contentPane.add(panelCartas);
		panelCartas.setLayout(null);

		lblCarta1 = new JLabel("");
		lblCarta1.setBounds(-13, 11, 161, 217);
		lblCarta1.setIcon(null);
		panelCartas.add(lblCarta1);

		lblCarta2 = new JLabel("");
		lblCarta2.setBounds(122, 11, 161, 217);
		panelCartas.add(lblCarta2);
		lblCarta2.setIcon(null);

		lblCarta3 = new JLabel("");
		lblCarta3.setBounds(249, 11, 161, 217);
		panelCartas.add(lblCarta3);
		lblCarta3.setIcon(null);

		lblCarta4 = new JLabel("");
		lblCarta4.setBounds(377, 11, 161, 217);
		panelCartas.add(lblCarta4);
		lblCarta4.setIcon(null);

		lblCarta5 = new JLabel("");
		lblCarta5.setBounds(503, 11, 161, 217);
		panelCartas.add(lblCarta5);
		lblCarta5.setIcon(null);

		JLabel lblFondoMadera = new JLabel("New label");
		lblFondoMadera.setBounds(-3, 0, 669, 239);
		panelCartas.add(lblFondoMadera);
		lblFondoMadera.setIcon(null);

		JLabel lblImagenMesaFondo = new JLabel("New label");
		lblImagenMesaFondo.setIcon(new ImageIcon(getClass().getResource("/FondoMesa.png")));
		lblImagenMesaFondo.setBounds(32, 88, 597, 265);
		contentPane.add(lblImagenMesaFondo);

		JLabel lblImagenFondoVerde = new JLabel("Fondo");
		lblImagenFondoVerde.setIcon(new ImageIcon(getClass().getResource("/FondoVerdeInicio.jpg")));
		lblImagenFondoVerde.setBounds(0, 0, 1121, 612);
		contentPane.add(lblImagenFondoVerde);
	}

	public void mostrarCartas(List<Carta> cartas) {

		JLabel[] labels = { lblCarta1, lblCarta2, lblCarta3, lblCarta4, lblCarta5 };

		MapeoDeCartas cartasMapeadas = new MapeoDeCartas();

		for (int i = 0; i < cartas.size(); i++) {

			Carta carta = cartas.get(i);
			ImageIcon imagenCarta = cartasMapeadas.getImagenCarta(carta.toString());

			if (imagenCarta != null) {
				labels[i].setIcon(imagenCarta);
			} else {
				labels[i].setIcon(null);
				labels[i].setText("Imagen no encontrada");
			}

		}

	}

	public void setearNombreEnLaBarra(String nombre) {
		this.lblNombreJugadorVentana.setText(nombre);
	}

	public String getNotificaciones() {

		return this.textAreaNotificaciones.getText();

	}

	public void escribirNotificacion(String notificacion) {
		this.textAreaNotificaciones.append(notificacion + ".\n");
	}

	public void mostrarOpcionesParaNuevaRondaJuego() {
		this.panelOpcionesSiguienteRondaJuego.setVisible(true);
	}

	public void limpiarPantalla() {
		this.textAreaNotificaciones.setText("");
	}

	public void mostrarMenuDescartes() {
		this.panelDescartarCartas.setVisible(true);

		this.btnDescartarCarta1.setVisible(true);
		this.lblFondoDescartarBtnC1.setVisible(true);

		this.btnDescartarCarta2.setVisible(true);
		this.lblFondoDescartarBtnC2.setVisible(true);

		this.btnDescartarCarta3.setVisible(true);
		this.lblFondoDescartarBtnC3.setVisible(true);

		this.btnDescartarCarta4.setVisible(true);
		this.lblFondoDescartarBtnC4.setVisible(true);

		this.btnDescartarCarta5.setVisible(true);
		this.lblFondoDescartarBtnC5.setVisible(true);
	}

	public void mostrarErrorFondosInsuficientesParaSeguirJugando() {
		this.panelOpcionesSiguienteRondaJuego.setVisible(false);
		this.panelErrores.setVisible(true);
		this.lblErrorFondosInsuficientes.setVisible(true);
	}

	public void mostrarErrorJugadoresInsuficientes() {
		this.panelOpcionesSiguienteRondaJuego.setVisible(false);
		this.lblErrorJugadoresInsuficientes.setVisible(true);
		this.panelErrores.setVisible(true);
	}
}
