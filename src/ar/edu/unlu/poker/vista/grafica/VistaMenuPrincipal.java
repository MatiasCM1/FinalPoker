package ar.edu.unlu.poker.vista.grafica;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ar.edu.unlu.poker.modelo.Jugador;

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
import java.util.List;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VistaMenuPrincipal extends JFrame {

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
	private JLabel lblFondosJugador_1;
	private JLabel lblFondosJugador_2;
	private JLabel lblFondosJugador_3;
	private JLabel lblFondosJugador_4;
	private JLabel lblFondosJugador_5;
	private JLabel lblFondosJugador_6;
	private JLabel lblFondosJugador_7;
	 
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
		
		JLabel lblImgCartasTitulo = new JLabel("");
		lblImgCartasTitulo.setHorizontalTextPosition(SwingConstants.CENTER);
		lblImgCartasTitulo.setVisible(false);
		lblImgCartasTitulo.setIcon(new ImageIcon("C:\\Users\\Colo\\eclipse-workspace\\FinalPoker\\resources\\CartasTituloPoker.png"));
		lblImgCartasTitulo.setBounds(124, 81, 59, 59);
		contentPane.add(lblImgCartasTitulo);
		panelBarraSuperior.setLayout(null);
		panelBarraSuperior.setBounds(0, 0, 1121, 21);
		contentPane.add(panelBarraSuperior);
		
		JButton btnSalir = new JButton("X");
		btnSalir.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				VistaGrafica.getInstance().seCierraLaVista();
				
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
		
		JLabel lblTxtFondosDinamico = new JLabel(String.valueOf(VistaGrafica.getInstance().getJugadorActual().getFondo()));
		lblTxtFondosDinamico.setHorizontalAlignment(SwingConstants.CENTER);
		lblTxtFondosDinamico.setForeground(Color.WHITE);
		lblTxtFondosDinamico.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));
		lblTxtFondosDinamico.setBounds(860, 136, 223, 43);
		contentPane.add(lblTxtFondosDinamico);
		
		JLabel lblTxtNombreDinamico = new JLabel(VistaGrafica.getInstance().getJugadorActual().getNombre());
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
		btnSalirGrande.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnSalirGrande.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnSalirGrande.setForeground(Color.black);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				btnSalirGrande.setForeground(Color.white);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				//CUANDO SE APRIETA EL BOTON, TENGO QUE VOLVER A LA PANTALLA DE LOGIN
				VistaGrafica.getInstance().volverPantallaLogin();
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
		btnComenzar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnComenzar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnComenzar.setForeground(Color.black);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				btnComenzar.setForeground(Color.white);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				
				VistaGrafica.getInstance().listoParaIniciarJuego();
				
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
		
		lblNombreJugador7 = new JLabel("");
		lblNombreJugador7.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombreJugador7.setForeground(Color.WHITE);
		lblNombreJugador7.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));
		lblNombreJugador7.setBounds(10, 0, 222, 37);
		panelJugador_7.add(lblNombreJugador7);
		
		lblFondosJugador_7 = new JLabel("");
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
		
		lblNombreJugador6 = new JLabel("");
		lblNombreJugador6.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombreJugador6.setForeground(Color.WHITE);
		lblNombreJugador6.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));
		lblNombreJugador6.setBounds(10, 0, 222, 37);
		panelJugador_6.add(lblNombreJugador6);
		
		lblFondosJugador_6 = new JLabel("");
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
		
		lblNombreJugador5 = new JLabel("");
		lblNombreJugador5.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombreJugador5.setForeground(Color.WHITE);
		lblNombreJugador5.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));
		lblNombreJugador5.setBounds(10, 0, 222, 37);
		panelJugador_5.add(lblNombreJugador5);
		
		lblFondosJugador_5 = new JLabel("");
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
		
		lblNombreJugador4 = new JLabel("");
		lblNombreJugador4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombreJugador4.setForeground(Color.WHITE);
		lblNombreJugador4.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));
		lblNombreJugador4.setBounds(10, 0, 222, 37);
		panelJugador_4.add(lblNombreJugador4);
		
		lblFondosJugador_4 = new JLabel("");
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
		
		lblNombreJugador3 = new JLabel("");
		lblNombreJugador3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombreJugador3.setForeground(Color.WHITE);
		lblNombreJugador3.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));
		lblNombreJugador3.setBounds(10, 0, 222, 37);
		panelJugador_3.add(lblNombreJugador3);
		
		lblFondosJugador_3 = new JLabel("");
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
		
		lblNombreJugador2 = new JLabel("");
		lblNombreJugador2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombreJugador2.setForeground(Color.WHITE);
		lblNombreJugador2.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));
		lblNombreJugador2.setBounds(10, 0, 222, 37);
		panelJugador_2.add(lblNombreJugador2);
		
		lblFondosJugador_2 = new JLabel("");
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
		
		lblNombreJugador1 = new JLabel("");
		lblNombreJugador1.setBounds(10, 11, 222, 43);
		panelJugador_1.add(lblNombreJugador1);
		lblNombreJugador1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombreJugador1.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));
		lblNombreJugador1.setForeground(new Color(255, 255, 255));
		
		lblFondosJugador_1 = new JLabel("");
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
		
		
		
		lblNombreJuegoPoker.setForeground(Color.WHITE);
		lblNombreJuegoPoker.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 80));
		lblNombreJuegoPoker.setBounds(71, 56, 292, 107);
		contentPane.add(lblNombreJuegoPoker);
		
		JLabel lblImagenFondoVerde = new JLabel("Fondo");
		lblImagenFondoVerde.setBounds(0, 0, 1121, 612);
		lblImagenFondoVerde.setIcon(new ImageIcon(getClass().getResource("/FondoVerdeInicio.jpg")));
		contentPane.add(lblImagenFondoVerde);
	}
	
	private void setearLabelsTablaJugadores() {
		this.lblNombreJugador1.setText("");
		this.lblNombreJugador2.setText("");
		this.lblNombreJugador3.setText("");
		this.lblNombreJugador4.setText("");
		this.lblNombreJugador5.setText("");
		this.lblNombreJugador6.setText("");
		this.lblNombreJugador7.setText("");
		
		
		this.lblFondosJugador_1.setText("");
		this.lblFondosJugador_2.setText("");
		this.lblFondosJugador_3.setText("");
		this.lblFondosJugador_4.setText("");
		this.lblFondosJugador_5.setText("");
		this.lblFondosJugador_6.setText("");
		this.lblFondosJugador_7.setText("");
	}

	public void actualzarTabla(List<Jugador> jugadores) {
		
		this.setearLabelsTablaJugadores();
		
		JLabel[] labelsNombres = {
		        lblNombreJugador1,
		        lblNombreJugador2,
		        lblNombreJugador3,
		        lblNombreJugador4,
		        lblNombreJugador5,
		        lblNombreJugador6,
		        lblNombreJugador7
		    };
		
		 JLabel[] labelsFondos = {
			        lblFondosJugador_1,
			        lblFondosJugador_2,
			        lblFondosJugador_3,
			        lblFondosJugador_4,
			        lblFondosJugador_5,
			        lblFondosJugador_6,
			        lblFondosJugador_7
			    };
		
		 
		 
		for (int i = 0; i < labelsNombres.length; i++) {
			if (i < jugadores.size()) {
	            Jugador jugador = jugadores.get(i);
	            labelsNombres[i].setText(jugador.getNombre());
	            labelsFondos[i].setText(String.valueOf(jugador.getFondo()));
	        } else {
	            labelsNombres[i].setText("");
	            labelsFondos[i].setText("");
	        }
	    }
	}
	
}
