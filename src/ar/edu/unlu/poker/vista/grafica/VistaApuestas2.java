package ar.edu.unlu.poker.vista.grafica;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ar.edu.unlu.poker.modelo.Carta;

import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.List;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;
import java.awt.Cursor;

public class VistaApuestas2 extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private int xMouse;
	private int yMouse;
	private JLabel lblCarta1;
	private JLabel lblCarta2;
	private JLabel lblCarta3;
	private JLabel lblCarta4;
	private JLabel lblCarta5;
	private JTextArea textAreaNotificaciones;
	private JLabel lblNombreJugadorVentana;
	private JButton btnAceptarEnvite;
	private JButton btnCancelarEnvite;
	private JPanel panelRealizarEnvite;
	private JTextField txtFieldCantidadApuesta;
	private JPanel panelErrores;
	private JLabel lblFondoNegroErrores;
	private JLabel lblErrorNumeroEntero;
	private JLabel lblApuestaMayorIgualAnterior;
	private JLabel lblFondosInsuficientes;
	private JLabel lblErrorJugadorManoEnvita;
	private JPanel panelApuestasDesiguales;
	private JPanel panelBtnApuestas;
	private boolean apuestasDesiguales;

	

	public VistaApuestas2() {
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
		
		panelRealizarEnvite = new JPanel();
		panelRealizarEnvite.setVisible(false);
		
		panelApuestasDesiguales = new JPanel();
		panelApuestasDesiguales.setVisible(false);
		
		panelErrores = new JPanel();
		panelErrores.setBounds(265, 159, 360, 187);
		contentPane.add(panelErrores);
		panelErrores.setLayout(null);
		
		JButton btnSalirVentanaErrores = new JButton("X");
		btnSalirVentanaErrores.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSalirVentanaErrores.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnSalirVentanaErrores.setForeground(Color.red);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnSalirVentanaErrores.setForeground(Color.white);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				panelErrores.setVisible(false);
				lblFondosInsuficientes.setVisible(false);
				lblApuestaMayorIgualAnterior.setVisible(false);
				lblErrorNumeroEntero.setVisible(false);
				lblFondoNegroErrores.setVisible(false);
				lblErrorJugadorManoEnvita.setVisible(false);
				panelBtnApuestas.setVisible(true);
				if (apuestasDesiguales) {
					mostrarMenuApuestaDesigual();
				}
			}
		});
		
		lblApuestaMayorIgualAnterior = new JLabel("La apuesta debe ser mayor o igual a la anterior");
		lblApuestaMayorIgualAnterior.setVisible(false);
		
		lblFondosInsuficientes = new JLabel("Fondos insuficientes");
		lblFondosInsuficientes.setForeground(new Color(255, 0, 0));
		lblFondosInsuficientes.setHorizontalAlignment(SwingConstants.CENTER);
		lblFondosInsuficientes.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));
		lblFondosInsuficientes.setVisible(false);
		
		lblErrorJugadorManoEnvita = new JLabel("El jugador mano debe envitar obligatoriamente");
		lblErrorJugadorManoEnvita.setVisible(false);
		lblErrorJugadorManoEnvita.setHorizontalAlignment(SwingConstants.CENTER);
		lblErrorJugadorManoEnvita.setForeground(new Color(255, 0, 0));
		lblErrorJugadorManoEnvita.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
		lblErrorJugadorManoEnvita.setBounds(10, 86, 340, 37);
		panelErrores.add(lblErrorJugadorManoEnvita);
		lblFondosInsuficientes.setBounds(10, 86, 340, 37);
		panelErrores.add(lblFondosInsuficientes);
		lblApuestaMayorIgualAnterior.setHorizontalAlignment(SwingConstants.CENTER);
		lblApuestaMayorIgualAnterior.setForeground(new Color(255, 0, 0));
		lblApuestaMayorIgualAnterior.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
		lblApuestaMayorIgualAnterior.setBounds(10, 86, 340, 37);
		panelErrores.add(lblApuestaMayorIgualAnterior);
		
		lblErrorNumeroEntero = new JLabel("Ingrese un numero entero");
		lblErrorNumeroEntero.setVisible(false);
		lblErrorNumeroEntero.setForeground(new Color(255, 0, 0));
		lblErrorNumeroEntero.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));
		lblErrorNumeroEntero.setHorizontalAlignment(SwingConstants.CENTER);
		lblErrorNumeroEntero.setBounds(10, 86, 340, 37);
		panelErrores.add(lblErrorNumeroEntero);
		
		lblFondoNegroErrores = new JLabel("New label");
		lblFondoNegroErrores.setVisible(false);
		lblFondoNegroErrores.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));
		lblFondoNegroErrores.setIcon(new ImageIcon("C:\\Users\\Colo\\eclipse-workspace\\FinalPoker\\resources\\fondoNegro.jpg"));
		lblFondoNegroErrores.setBounds(10, 86, 340, 37);
		panelErrores.add(lblFondoNegroErrores);
		btnSalirVentanaErrores.setContentAreaFilled(false);
		btnSalirVentanaErrores.setBackground(new Color(240, 240, 240));
		btnSalirVentanaErrores.setBorder(null);
		btnSalirVentanaErrores.setForeground(new Color(255, 255, 255));
		btnSalirVentanaErrores.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 24));
		btnSalirVentanaErrores.setBounds(302, 11, 48, 37);
		panelErrores.add(btnSalirVentanaErrores);
		
		JLabel lblErrores = new JLabel("Error");
		lblErrores.setForeground(new Color(255, 255, 255));
		lblErrores.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 27));
		lblErrores.setHorizontalAlignment(SwingConstants.CENTER);
		lblErrores.setBounds(10, 11, 340, 37);
		panelErrores.add(lblErrores);
		
		JLabel lblFondoMaderaErrores = new JLabel("New label");
		lblFondoMaderaErrores.setBounds(0, 0, 360, 187);
		panelErrores.add(lblFondoMaderaErrores);
		lblFondoMaderaErrores.setIcon(new ImageIcon("C:\\Users\\Colo\\eclipse-workspace\\FinalPoker\\resources\\imagenMadera.jpg"));
		panelErrores.setVisible(false);
		panelApuestasDesiguales.setBounds(298, 181, 309, 147);
		contentPane.add(panelApuestasDesiguales);
		panelApuestasDesiguales.setLayout(null);
		
		JButton btnFicharApuestasDesiguales = new JButton("Fichar");
		btnFicharApuestasDesiguales.setBorderPainted(false);
		btnFicharApuestasDesiguales.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnFicharApuestasDesiguales.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnFicharApuestasDesiguales.setForeground(Color.black);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnFicharApuestasDesiguales.setForeground(Color.white);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				panelApuestasDesiguales.setVisible(false);
				panelBtnApuestas.setVisible(true);
				VistaGrafica.getInstance().realizarFichePostEnvite();
			}
		});
		
		JButton btnPasarApuestasDesiguales = new JButton("Pasar");
		btnPasarApuestasDesiguales.setBorderPainted(false);
		btnPasarApuestasDesiguales.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnPasarApuestasDesiguales.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnPasarApuestasDesiguales.setForeground(Color.black);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnPasarApuestasDesiguales.setForeground(Color.white);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				panelApuestasDesiguales.setVisible(false);
				panelBtnApuestas.setVisible(true);
				VistaGrafica.getInstance().realizarPasarPostEnvite();
			}
		});
		btnPasarApuestasDesiguales.setBounds(167, 60, 111, 35);
		panelApuestasDesiguales.add(btnPasarApuestasDesiguales);
		btnPasarApuestasDesiguales.setIcon(null);
		btnPasarApuestasDesiguales.setForeground(Color.WHITE);
		btnPasarApuestasDesiguales.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 25));
		btnPasarApuestasDesiguales.setContentAreaFilled(false);
		btnPasarApuestasDesiguales.setBackground(new Color(255, 255, 255, 100));
		
		JLabel lblFondoBtnPasar = new JLabel("");
		lblFondoBtnPasar.setIcon(new ImageIcon("C:\\Users\\Colo\\eclipse-workspace\\FinalPoker\\resources\\cartelMadera4.png"));
		lblFondoBtnPasar.setHorizontalAlignment(SwingConstants.CENTER);
		lblFondoBtnPasar.setBounds(167, 60, 111, 35);
		panelApuestasDesiguales.add(lblFondoBtnPasar);
		btnFicharApuestasDesiguales.setBounds(20, 60, 111, 35);
		panelApuestasDesiguales.add(btnFicharApuestasDesiguales);
		btnFicharApuestasDesiguales.setIcon(null);
		btnFicharApuestasDesiguales.setForeground(Color.WHITE);
		btnFicharApuestasDesiguales.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 25));
		btnFicharApuestasDesiguales.setContentAreaFilled(false);
		btnFicharApuestasDesiguales.setBackground(new Color(255, 255, 255, 100));
		
		JLabel lblFondoBtnFichar = new JLabel("");
		lblFondoBtnFichar.setHorizontalAlignment(SwingConstants.CENTER);
		lblFondoBtnFichar.setIcon(new ImageIcon("C:\\Users\\Colo\\eclipse-workspace\\FinalPoker\\resources\\cartelMadera4.png"));
		lblFondoBtnFichar.setBounds(20, 60, 111, 35);
		panelApuestasDesiguales.add(lblFondoBtnFichar);
		
		JLabel lblApuestasDesiguales = new JLabel("Apuestas Desiguales");
		lblApuestasDesiguales.setBounds(20, 0, 289, 38);
		panelApuestasDesiguales.add(lblApuestasDesiguales);
		lblApuestasDesiguales.setHorizontalAlignment(SwingConstants.CENTER);
		lblApuestasDesiguales.setForeground(Color.WHITE);
		lblApuestasDesiguales.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 30));
		
		JLabel lblFondoMaderaApuestasDesiguales = new JLabel("");
		lblFondoMaderaApuestasDesiguales.setIcon(new ImageIcon("C:\\Users\\Colo\\eclipse-workspace\\FinalPoker\\resources\\imagenMadera.jpg"));
		lblFondoMaderaApuestasDesiguales.setBounds(0, 0, 309, 147);
		panelApuestasDesiguales.add(lblFondoMaderaApuestasDesiguales);
		panelRealizarEnvite.setBounds(298, 181, 309, 147);
		contentPane.add(panelRealizarEnvite);
		panelRealizarEnvite.setLayout(null);
		
		btnAceptarEnvite = new JButton("Aceptar");
		btnAceptarEnvite.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAceptarEnvite.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnAceptarEnvite.setBackground(new Color(255, 255, 255, 100));
		btnAceptarEnvite.setContentAreaFilled(false);
		btnAceptarEnvite.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnAceptarEnvite.setForeground(Color.black);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnAceptarEnvite.setForeground(Color.white);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				panelRealizarEnvite.setVisible(false);
				
				panelBtnApuestas.setVisible(true);
				
				VistaGrafica.getInstance().realizarEnvitarSegundaRonda(txtFieldCantidadApuesta.getText());
				
				txtFieldCantidadApuesta.setText("");
				//VistaGrafica.getInstance().notificarEnviteRealizado(txtFieldCantidadApuesta.getText());
				
				//txtFieldCantidadApuesta.setText("");
			}
		});
		
		btnCancelarEnvite = new JButton("Cancelar");
		btnCancelarEnvite.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCancelarEnvite.setBackground(new Color(255, 255, 255, 100));
		btnCancelarEnvite.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnCancelarEnvite.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnCancelarEnvite.setForeground(Color.black);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnCancelarEnvite.setForeground(Color.white);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				panelRealizarEnvite.setVisible(false);
				panelBtnApuestas.setVisible(true);
				txtFieldCantidadApuesta.setText("");
			}
		});
		btnCancelarEnvite.setForeground(new Color(255, 255, 255));
		btnCancelarEnvite.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 17));
		btnCancelarEnvite.setContentAreaFilled(false);
		btnCancelarEnvite.setIcon(null);
		btnCancelarEnvite.setBounds(23, 101, 107, 35);
		panelRealizarEnvite.add(btnCancelarEnvite);
		
		JLabel lblFondoMaderaBtnCancelar = new JLabel("");
		lblFondoMaderaBtnCancelar.setIcon(new ImageIcon("C:\\Users\\Colo\\eclipse-workspace\\FinalPoker\\resources\\cartelMadera4.png"));
		lblFondoMaderaBtnCancelar.setHorizontalAlignment(SwingConstants.CENTER);
		lblFondoMaderaBtnCancelar.setBounds(10, 101, 132, 35);
		panelRealizarEnvite.add(lblFondoMaderaBtnCancelar);
		btnAceptarEnvite.setIcon(null);
		btnAceptarEnvite.setForeground(Color.WHITE);
		btnAceptarEnvite.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 17));
		btnAceptarEnvite.setBounds(178, 101, 107, 35);
		panelRealizarEnvite.add(btnAceptarEnvite);
		
		JLabel lblFondoMaderaBtnAceptar = new JLabel("");
		lblFondoMaderaBtnAceptar.setIcon(new ImageIcon("C:\\Users\\Colo\\eclipse-workspace\\FinalPoker\\resources\\cartelMadera4.png"));
		lblFondoMaderaBtnAceptar.setHorizontalAlignment(SwingConstants.CENTER);
		lblFondoMaderaBtnAceptar.setBounds(167, 101, 132, 35);
		panelRealizarEnvite.add(lblFondoMaderaBtnAceptar);
		
		txtFieldCantidadApuesta = new JTextField();
		txtFieldCantidadApuesta.setBounds(53, 60, 214, 20);
		panelRealizarEnvite.add(txtFieldCantidadApuesta);
		txtFieldCantidadApuesta.setColumns(10);
		
		JLabel lblEnvite = new JLabel("Envite");
		lblEnvite.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 30));
		lblEnvite.setHorizontalAlignment(SwingConstants.CENTER);
		lblEnvite.setForeground(new Color(255, 255, 255));
		lblEnvite.setBounds(89, 11, 132, 38);
		panelRealizarEnvite.add(lblEnvite);
		
		JLabel lblFondoManderaEnvite = new JLabel("");
		lblFondoManderaEnvite.setBounds(0, 0, 315, 147);
		panelRealizarEnvite.add(lblFondoManderaEnvite);
		lblFondoManderaEnvite.setIcon(new ImageIcon("C:\\Users\\Colo\\eclipse-workspace\\FinalPoker\\resources\\imagenMadera.jpg"));
		panelBarraSuperior.setLayout(null);
		panelBarraSuperior.setBounds(0, 0, 1121, 21);
		contentPane.add(panelBarraSuperior);
		
		JButton btnSalir = new JButton("X");
		btnSalir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSalir.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				VistaGrafica.getInstance().jugadorSaleDelJuego();
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
		
		lblNombreJugadorVentana = new JLabel("");
		lblNombreJugadorVentana.setBounds(10, -1, 319, 21);
		panelBarraSuperior.add(lblNombreJugadorVentana);
		lblNombreJugadorVentana.setForeground(new Color(255, 255, 255));
		lblNombreJugadorVentana.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 13));
		btnSalir.setOpaque(false);
		btnSalir.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 10));
		btnSalir.setBorder(null);
		btnSalir.setBackground(Color.RED);
		btnSalir.setBounds(1082, 1, 39, 19);
		panelBarraSuperior.add(btnSalir);
		
		
		panelBarraSuperior.setLayout(null);
		panelBarraSuperior.setBounds(0, 0, 1121, 21);
		
		
		
		JLabel lblFondoMaderaBarra = new JLabel("New label");
		lblFondoMaderaBarra.setIcon(new ImageIcon(getClass().getResource("/imagenMadera.jpg")));
		lblFondoMaderaBarra.setBounds(0, 0, 1121, 21);
		panelBarraSuperior.add(lblFondoMaderaBarra);
		
		panelBtnApuestas = new JPanel();
		panelBtnApuestas.setOpaque(false);
		panelBtnApuestas.setBounds(687, 362, 408, 239);
		contentPane.add(panelBtnApuestas);
		panelBtnApuestas.setLayout(null);
		
		JPanel panelBtnEnvite = new JPanel();
		panelBtnEnvite.setBounds(10, 11, 388, 60);
		panelBtnApuestas.add(panelBtnEnvite);
		panelBtnEnvite.setLayout(null);
		
		JButton btnEnvite = new JButton("Envitar");
		btnEnvite.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEnvite.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnEnvite.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnEnvite.setForeground(Color.black);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				btnEnvite.setForeground(Color.white);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				panelBtnApuestas.setVisible(false);
				panelRealizarEnvite.setVisible(true);
			}
		});
		btnEnvite.setContentAreaFilled(false);
		btnEnvite.setBounds(0, 0, 388, 60);
		panelBtnEnvite.add(btnEnvite);
		btnEnvite.setOpaque(false);
		btnEnvite.setForeground(Color.WHITE);
		btnEnvite.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 30));
		btnEnvite.setBorder(null);
		
		JLabel lblFondoMaderaBtn = new JLabel("New label");
		lblFondoMaderaBtn.setBounds(0, 0, 388, 60);
		panelBtnEnvite.add(lblFondoMaderaBtn);
		lblFondoMaderaBtn.setIcon(new ImageIcon(getClass().getResource("/imagenMadera.jpg")));
		
		JPanel panelBtnFiche = new JPanel();
		panelBtnFiche.setBounds(10, 85, 388, 60);
		panelBtnApuestas.add(panelBtnFiche);
		panelBtnFiche.setLayout(null);
		
		JButton btnFiche = new JButton("Fichar");
		btnFiche.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnFiche.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnFiche.setForeground(Color.black);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				btnFiche.setForeground(Color.white);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				
				VistaGrafica.getInstance().realizarFicharSegundaRonda();
				
			}
		});
		btnFiche.setContentAreaFilled(false);
		btnFiche.setOpaque(false);
		btnFiche.setForeground(Color.WHITE);
		btnFiche.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 30));
		btnFiche.setBorder(null);
		btnFiche.setBounds(0, 0, 388, 60);
		panelBtnFiche.add(btnFiche);
		
		JLabel lblFondoMaderaBtn_1 = new JLabel("New label");
		lblFondoMaderaBtn_1.setIcon(new ImageIcon(getClass().getResource("/imagenMadera.jpg")));
		lblFondoMaderaBtn_1.setBounds(0, 0, 388, 60);
		panelBtnFiche.add(lblFondoMaderaBtn_1);
		
		JPanel panelBtnPase = new JPanel();
		panelBtnPase.setBounds(10, 156, 388, 60);
		panelBtnApuestas.add(panelBtnPase);
		panelBtnPase.setLayout(null);
		
		JButton btnPase = new JButton("Pasar");
		btnPase.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnPase.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnPase.setForeground(Color.black);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				btnPase.setForeground(Color.white);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				VistaGrafica.getInstance().realizarPase();
			}
		});
		btnPase.setContentAreaFilled(false);
		btnPase.setOpaque(false);
		btnPase.setForeground(Color.WHITE);
		btnPase.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 30));
		btnPase.setBorder(null);
		btnPase.setBounds(0, 0, 388, 60);
		panelBtnPase.add(btnPase);
		
		JLabel lblFondoMaderaBtn_2 = new JLabel("New label");
		lblFondoMaderaBtn_2.setIcon(new ImageIcon(getClass().getResource("/imagenMadera.jpg")));
		lblFondoMaderaBtn_2.setBounds(0, 0, 388, 60);
		panelBtnPase.add(lblFondoMaderaBtn_2);
		
		JPanel panelNotificaciones = new JPanel();
		panelNotificaciones.setBounds(635, 50, 472, 301);
		contentPane.add(panelNotificaciones);
		panelNotificaciones.setLayout(null);
		
		textAreaNotificaciones = new JTextArea();
		textAreaNotificaciones.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 13));
		textAreaNotificaciones.setForeground(new Color(255, 255, 255));
		textAreaNotificaciones.setBounds(10, 11, 452, 279);
		panelNotificaciones.add(textAreaNotificaciones);
		textAreaNotificaciones.setBackground(new Color(0, 0, 0));
		textAreaNotificaciones.setBorder(null);
		
		JLabel lblFondoMaderaNotificaciones = new JLabel("");
		lblFondoMaderaNotificaciones.setBounds(0, 0, 472, 301);
		panelNotificaciones.add(lblFondoMaderaNotificaciones);
		lblFondoMaderaNotificaciones.setIcon(new ImageIcon(getClass().getResource("/imagenMadera.jpg")));
		
		JPanel panelCartas = new JPanel();
		panelCartas.setBounds(10, 362, 667, 239);
		panelCartas.setOpaque(false);
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
		
		JLabel lblImagenMesaFondo = new JLabel("");
		lblImagenMesaFondo.setBounds(10, 58, 597, 293);
		lblImagenMesaFondo.setIcon(new ImageIcon(getClass().getResource("/FondoMesa.png")));
		contentPane.add(lblImagenMesaFondo);
		
		JLabel lblImagenFondoVerde = new JLabel("Fondo");
		lblImagenFondoVerde.setBounds(0, 0, 1121, 612);
		lblImagenFondoVerde.setIcon(new ImageIcon(getClass().getResource("/FondoVerdeInicio.jpg")));
		contentPane.add(lblImagenFondoVerde);
	}
	
	public void informarJugadorMano(String nombreJugadorMano) {
		this.textAreaNotificaciones.append("Jugador mano: " + nombreJugadorMano + ".\n");
	}
	
	public void mostrarCartas(List<Carta> cartas) {
		
		JLabel[] labels = {lblCarta1, lblCarta2, lblCarta3, lblCarta4, lblCarta5};
		
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

	public void escribirNotificaciones(String notificaciones) {
		this.textAreaNotificaciones.setText("");
		this.textAreaNotificaciones.setText(notificaciones);
	}
	
	public void mostrarMenuEnvite() {
		this.panelBtnApuestas.setVisible(false);
		this.panelRealizarEnvite.setVisible(true);
	}
	
	public void mostrarErrorNumeroEntero() {
		if (this.panelApuestasDesiguales.isVisible()) {
			this.apuestasDesiguales = true;
			this.panelApuestasDesiguales.setVisible(false);
			
		}
		this.panelRealizarEnvite.setVisible(false);
		this.panelErrores.setVisible(true);
		this.lblFondoNegroErrores.setVisible(true);
		this.lblErrorNumeroEntero.setVisible(true);
		this.panelBtnApuestas.setVisible(false);
	}
	
	public void mostrarErrorApuestaInsuficiente() {
		if (this.panelApuestasDesiguales.isVisible()) {
			this.apuestasDesiguales = true;
			this.panelApuestasDesiguales.setVisible(false);
		}
		this.panelRealizarEnvite.setVisible(false);
		this.panelErrores.setVisible(true);
		this.lblFondoNegroErrores.setVisible(true);
		this.lblApuestaMayorIgualAnterior.setVisible(true);
		this.panelBtnApuestas.setVisible(false);
	}
	
	
	public void mostrarErrorFondosInsuficientes() {
		if (this.panelApuestasDesiguales.isVisible()) {
			this.apuestasDesiguales = true;
			this.panelApuestasDesiguales.setVisible(false);
		}
		this.panelRealizarEnvite.setVisible(false);
		this.panelErrores.setVisible(true);
		this.lblFondoNegroErrores.setVisible(true);
		this.lblFondosInsuficientes.setVisible(true);
		this.panelBtnApuestas.setVisible(false);
	}

	public void mostrarErrorJugadorManoDebeEnvitar() {
		if (this.panelApuestasDesiguales.isVisible()) {
			this.apuestasDesiguales = true;
			this.panelApuestasDesiguales.setVisible(false);
		}
		this.panelRealizarEnvite.setVisible(false);
		this.panelErrores.setVisible(true);
		this.lblFondoNegroErrores.setVisible(true);
		this.lblErrorJugadorManoEnvita.setVisible(true);
		this.panelBtnApuestas.setVisible(false);
	}

	public void mostrarMenuApuestaDesigual() {
		this.panelBtnApuestas.setVisible(false);
		this.apuestasDesiguales = false;
		this.panelApuestasDesiguales.setVisible(true);
	}
}