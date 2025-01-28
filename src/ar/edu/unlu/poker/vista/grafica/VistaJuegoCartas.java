package ar.edu.unlu.poker.vista.grafica;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
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
		
		JPanel panelNotificaciones = new JPanel();
		panelNotificaciones.setBounds(639, 50, 472, 301);
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
		lblImagenMesaFondo.setBounds(10, 58, 597, 293);
		contentPane.add(lblImagenMesaFondo);
		
		JLabel lblImagenFondoVerde = new JLabel("Fondo");
		lblImagenFondoVerde.setIcon(new ImageIcon(getClass().getResource("/FondoVerdeInicio.jpg")));
		lblImagenFondoVerde.setBounds(0, 0, 1121, 612);
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
			System.out.println("Busca esto: " + cartasMapeadas.getImagenCarta(carta.toString()) + " de esto " + carta.toString());
			ImageIcon imagenCarta = cartasMapeadas.getImagenCarta(carta.toString());
		
			
			if (imagenCarta != null) {
				System.out.println("PASO EL NULL MUY BIENNNN");
				labels[i].setIcon(imagenCarta);
			} else {
				labels[i].setIcon(null);
				labels[i].setText("Imagen no encontrada");
			}
			
		}
		
	}
	
}
