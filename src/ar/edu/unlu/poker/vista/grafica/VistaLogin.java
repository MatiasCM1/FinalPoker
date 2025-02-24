package ar.edu.unlu.poker.vista.grafica;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import ar.edu.unlu.poker.controlador.Controlador;

public class VistaLogin extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNombre;
	private JTextField txtFondos;
	private int xMouse;
	private int yMouse;
	private JPanel panelErrores;
	private JButton btnIngresar;
	private JLabel lblBtnIngresar;
	private JLabel lblMsgErrorPartidaComenzada;
	private JLabel lblErrorCantidadJugadoresMaxima;
	private JLabel lblMensajeErrorNombre;

	public VistaLogin() {
		setUndecorated(true);
		setLocationByPlatform(true);
		setResizable(false);
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

		lblMensajeErrorNombre = new JLabel("Error, ingrese un nombre de usuario");
		lblMensajeErrorNombre.setVisible(false);

		JLabel lblMensajeErrorFondos = new JLabel("Error, ingrese los fondos correctamente");
		lblMensajeErrorFondos.setVisible(false);

		panelErrores = new JPanel();
		panelErrores.setVisible(false);
		panelErrores.setBounds(307, 163, 481, 268);
		contentPane.add(panelErrores);
		panelErrores.setLayout(null);

		JButton btnCerrarPanelErrores = new JButton("X");
		btnCerrarPanelErrores.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnCerrarPanelErrores.setForeground(Color.red);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnCerrarPanelErrores.setForeground(Color.white);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				btnIngresar.setVisible(true);
				lblBtnIngresar.setVisible(true);
				lblMsgErrorPartidaComenzada.setVisible(false);
				lblErrorCantidadJugadoresMaxima.setVisible(false);
				panelErrores.setVisible(false);
			}
		});

		lblMsgErrorPartidaComenzada = new JLabel("La partida ya ha comenzado");
		lblMsgErrorPartidaComenzada.setVisible(false);

		lblErrorCantidadJugadoresMaxima = new JLabel("Cantidad de jugadores maxima");
		lblErrorCantidadJugadoresMaxima.setVisible(false);
		lblErrorCantidadJugadoresMaxima.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));
		lblErrorCantidadJugadoresMaxima.setHorizontalAlignment(SwingConstants.CENTER);
		lblErrorCantidadJugadoresMaxima.setForeground(new Color(255, 0, 0));
		lblErrorCantidadJugadoresMaxima.setBounds(10, 113, 461, 34);
		panelErrores.add(lblErrorCantidadJugadoresMaxima);
		lblMsgErrorPartidaComenzada.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));
		lblMsgErrorPartidaComenzada.setForeground(new Color(255, 0, 0));
		lblMsgErrorPartidaComenzada.setHorizontalAlignment(SwingConstants.CENTER);
		lblMsgErrorPartidaComenzada.setBounds(10, 113, 461, 34);
		panelErrores.add(lblMsgErrorPartidaComenzada);

		JLabel lblFondoNegroErrores = new JLabel("New label");
		lblFondoNegroErrores.setIcon(new ImageIcon(getClass().getResource("/fondoNegro.jpg")));
		lblFondoNegroErrores.setBounds(10, 113, 461, 34);
		panelErrores.add(lblFondoNegroErrores);
		btnCerrarPanelErrores.setContentAreaFilled(false);
		btnCerrarPanelErrores.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCerrarPanelErrores.setForeground(new Color(255, 255, 255));
		btnCerrarPanelErrores.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
		btnCerrarPanelErrores.setBounds(436, 0, 45, 34);
		panelErrores.add(btnCerrarPanelErrores);

		JLabel lblTituloErrores = new JLabel("Error");
		lblTituloErrores.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 30));
		lblTituloErrores.setForeground(new Color(255, 255, 255));
		lblTituloErrores.setHorizontalAlignment(SwingConstants.CENTER);
		lblTituloErrores.setBounds(10, 11, 461, 45);
		panelErrores.add(lblTituloErrores);

		JLabel lblFondoMaderaErrores = new JLabel("New label");
		lblFondoMaderaErrores.setIcon(new ImageIcon(getClass().getResource("/imagenMadera.jpg")));
		lblFondoMaderaErrores.setBounds(0, 0, 481, 268);
		panelErrores.add(lblFondoMaderaErrores);
		lblMensajeErrorFondos.setForeground(new Color(255, 0, 0));
		lblMensajeErrorFondos.setHorizontalAlignment(SwingConstants.CENTER);
		lblMensajeErrorFondos.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 13));
		lblMensajeErrorFondos.setBounds(414, 410, 278, 21);
		contentPane.add(lblMensajeErrorFondos);
		lblMensajeErrorNombre.setHorizontalAlignment(SwingConstants.CENTER);
		lblMensajeErrorNombre.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 13));
		lblMensajeErrorNombre.setForeground(new Color(255, 0, 0));
		lblMensajeErrorNombre.setBackground(new Color(255, 255, 255));
		lblMensajeErrorNombre.setBounds(414, 253, 278, 16);
		contentPane.add(lblMensajeErrorNombre);
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

		JPanel panelIngresar = new JPanel();
		panelIngresar.setOpaque(false);

		panelIngresar.setBorder(null);
		panelIngresar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		panelIngresar.setBackground(new Color(12, 79, 36));
		panelIngresar.setBounds(358, 468, 374, 75);
		contentPane.add(panelIngresar);
		panelIngresar.setLayout(null);

		btnIngresar = new JButton("Ingresar");
		btnIngresar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnIngresar.setBounds(0, 0, 374, 75);
		panelIngresar.add(btnIngresar);
		btnIngresar.setOpaque(false);
		btnIngresar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnIngresar.setForeground(Color.black);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnIngresar.setForeground(Color.white);
			}

			@Override
			public void mouseClicked(MouseEvent e) {

				lblMensajeErrorNombre.setVisible(false);
				lblMensajeErrorFondos.setVisible(false);

				boolean flag = false;


				if (!VistaGrafica.getInstance().validarTextoNombre(txtNombre.getText())) {
					lblMensajeErrorNombre.setVisible(true);
					flag = true;
				}

				if (!VistaGrafica.getInstance().validarTextoFondos(txtFondos.getText())) {
					lblMensajeErrorFondos.setVisible(true);
					flag = true;
				}

				if (!flag) {
					VistaGrafica.getInstance().setJugador(txtNombre.getText(), Integer.parseInt(txtFondos.getText()));
					VistaGrafica.getInstance().agregarJugador();
				}

			}

		});
		btnIngresar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnIngresar.setForeground(new Color(255, 255, 255));
		btnIngresar.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 30));
		btnIngresar.setBorder(null);
		btnIngresar.setBackground(new Color(255, 128, 0));

		lblBtnIngresar = new JLabel("");
		lblBtnIngresar.setIcon(new ImageIcon(getClass().getResource("/imagenMadera.jpg")));

		lblBtnIngresar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblBtnIngresar.setHorizontalAlignment(SwingConstants.CENTER);
		lblBtnIngresar.setForeground(new Color(255, 255, 255));
		lblBtnIngresar.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 30));
		lblBtnIngresar.setBounds(0, 0, 374, 75);
		panelIngresar.add(lblBtnIngresar);

		JLabel lblFondomadera = new JLabel("New label");
		lblFondomadera.setIcon(new ImageIcon(getClass().getResource("/imagenMadera.jpg")));
		contentPane.add(lblFondomadera);

		JSeparator separator_fondos = new JSeparator();
		separator_fondos.setBounds(358, 429, 374, 2);
		contentPane.add(separator_fondos);

		txtFondos = new JTextField();
		txtFondos.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (txtFondos.getText().length() >= 6) {
					e.consume();
				}
			}
		});
		txtFondos.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 11));
		txtFondos.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (txtFondos.getText().equals("Ingrese los fondos")) {
					txtFondos.setText("");
					txtFondos.setForeground(Color.black);
				}
				if (txtNombre.getText().isEmpty()) {
					txtNombre.setText("Ingrese su nombre de usuario");
					txtNombre.setForeground(Color.gray);
				}
			}
		});
		txtFondos.setBorder(null);
		txtFondos.setText("Ingrese los fondos");
		txtFondos.setForeground(Color.LIGHT_GRAY);
		txtFondos.setColumns(10);
		txtFondos.setBounds(358, 376, 374, 31);
		contentPane.add(txtFondos);

		JLabel lblfondos = new JLabel("Fondos");
		lblfondos.setForeground(Color.WHITE);
		lblfondos.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 30));
		lblfondos.setBounds(358, 319, 278, 46);
		contentPane.add(lblfondos);

		JSeparator separator_nombre = new JSeparator();
		separator_nombre.setBounds(358, 273, 374, 2);
		contentPane.add(separator_nombre);

		txtNombre = new JTextField();
		txtNombre.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (txtNombre.getText().length() >= 6) {
					e.consume();
				}
			}
		});
		txtNombre.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 11));
		txtNombre.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (txtNombre.getText().equals("Ingrese su nombre de usuario")) {
					txtNombre.setText("");
					txtNombre.setForeground(Color.black);
				}
				if (txtFondos.getText().isEmpty()) {
					txtFondos.setText("Ingrese los fondos");
					txtFondos.setForeground(Color.gray);
				}
			}
		});
		txtNombre.setBorder(null);
		txtNombre.setText("Ingrese su nombre de usuario");
		txtNombre.setForeground(new Color(192, 192, 192));
		txtNombre.setBounds(358, 220, 374, 31);
		contentPane.add(txtNombre);
		txtNombre.setColumns(10);

		JLabel lblImagenCartas = new JLabel("Fondo Fichas");
		lblImagenCartas.setIcon(new ImageIcon(getClass().getResource("/FotoFichasInicio.png")));
		lblImagenCartas.setBounds(892, 66, 229, 411);
		contentPane.add(lblImagenCartas);

		JLabel lblNewLabel = new JLabel("Nombre");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 30));
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setBounds(358, 163, 278, 46);
		contentPane.add(lblNewLabel);

		JLabel lblImgCartasTitulo = new JLabel("");
		lblImgCartasTitulo.setHorizontalTextPosition(SwingConstants.CENTER);
		lblImgCartasTitulo.setVisible(false);
		lblImgCartasTitulo.setIcon(new ImageIcon(getClass().getResource("/CartasTituloPoker.png")));
		lblImgCartasTitulo.setBounds(463, 66, 59, 59);
		contentPane.add(lblImgCartasTitulo);

		JLabel lblNombreJuegoPoker = new JLabel("POKER");
		lblNombreJuegoPoker.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombreJuegoPoker.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblNombreJuegoPoker.setText("P   KER");
				lblImgCartasTitulo.setVisible(true);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				lblNombreJuegoPoker.setText("POKER");
				lblImgCartasTitulo.setVisible(false);
			}
		});

		lblNombreJuegoPoker.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 80));
		lblNombreJuegoPoker.setForeground(new Color(255, 255, 255));
		lblNombreJuegoPoker.setBounds(358, 45, 374, 107);
		contentPane.add(lblNombreJuegoPoker);

		JLabel lblImagenFondoVerde = new JLabel("Fondo");
		lblImagenFondoVerde.setIcon(new ImageIcon(getClass().getResource("/FondoVerdeInicio.jpg")));
		lblImagenFondoVerde.setBounds(0, 0, 1121, 612);
		contentPane.add(lblImagenFondoVerde);
	}

	public void mostrarErrorPartidaComenzada() {
		this.btnIngresar.setVisible(false);
		this.lblBtnIngresar.setVisible(false);
		this.lblMsgErrorPartidaComenzada.setVisible(true);
		this.panelErrores.setVisible(true);
	}

	public void mostrarErrorCantidadMaximaJugadores() {
		this.btnIngresar.setVisible(false);
		this.lblBtnIngresar.setVisible(false);
		this.lblErrorCantidadJugadoresMaxima.setVisible(true);
		this.panelErrores.setVisible(true);
	}
	
	public void mostrarErrorNombre() {
		this.lblMensajeErrorNombre.setVisible(true);
	}
}
