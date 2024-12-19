package ar.edu.unlu.poker.vista.grafica;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;

public class VistaJuegoCartas extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VistaJuegoCartas frame = new VistaJuegoCartas();
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
	public VistaJuegoCartas() {
		setResizable(false);
		setLocationByPlatform(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1121, 612);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextArea textAreaNotificaciones = new JTextArea();
		textAreaNotificaciones.setBounds(683, 22, 412, 282);
		contentPane.add(textAreaNotificaciones);
		
		JPanel panelCartas = new JPanel();
		panelCartas.setOpaque(false);
		panelCartas.setBounds(10, 323, 667, 239);
		contentPane.add(panelCartas);
		panelCartas.setLayout(null);
		
		JLabel lblCarta_1 = new JLabel("");
		lblCarta_1.setBounds(-13, 11, 161, 217);
		lblCarta_1.setIcon(new ImageIcon("C:\\Users\\Colo\\eclipse-workspace\\FinalPoker\\src\\ar\\edu\\unlu\\poker\\images\\carts\\Corazon - 2.png"));
		panelCartas.add(lblCarta_1);
		
		JLabel lblCarta_2 = new JLabel("");
		lblCarta_2.setBounds(122, 11, 161, 217);
		panelCartas.add(lblCarta_2);
		lblCarta_2.setIcon(new ImageIcon("C:\\Users\\Colo\\eclipse-workspace\\FinalPoker\\src\\ar\\edu\\unlu\\poker\\images\\carts\\Pica - AS.png"));
		
		JLabel lblCarta_3 = new JLabel("");
		lblCarta_3.setBounds(249, 11, 161, 217);
		panelCartas.add(lblCarta_3);
		lblCarta_3.setIcon(new ImageIcon("C:\\Users\\Colo\\eclipse-workspace\\FinalPoker\\src\\ar\\edu\\unlu\\poker\\images\\carts\\Trebol - K.png"));
		
		JLabel lblCarta_4 = new JLabel("");
		lblCarta_4.setBounds(377, 11, 161, 217);
		panelCartas.add(lblCarta_4);
		lblCarta_4.setIcon(new ImageIcon("C:\\Users\\Colo\\eclipse-workspace\\FinalPoker\\src\\ar\\edu\\unlu\\poker\\images\\carts\\Corazon - J.png"));
		
		JLabel lblCarta_5 = new JLabel("");
		lblCarta_5.setBounds(503, 11, 161, 217);
		panelCartas.add(lblCarta_5);
		lblCarta_5.setIcon(new ImageIcon("C:\\Users\\Colo\\eclipse-workspace\\FinalPoker\\src\\ar\\edu\\unlu\\poker\\images\\carts\\Corazon - 7.png"));
		
		JLabel lblFondoMadera = new JLabel("New label");
		lblFondoMadera.setBounds(-3, 0, 669, 239);
		panelCartas.add(lblFondoMadera);
		lblFondoMadera.setIcon(new ImageIcon("C:\\Users\\Colo\\eclipse-workspace\\FinalPoker\\src\\ar\\edu\\unlu\\poker\\images\\imagenMadera.jpg"));
		
		JLabel lblImagenMesaFondo = new JLabel("New label");
		lblImagenMesaFondo.setIcon(new ImageIcon("C:\\Users\\Colo\\eclipse-workspace\\FinalPoker\\src\\ar\\edu\\unlu\\poker\\images\\FondoMesa.png"));
		lblImagenMesaFondo.setBounds(28, 11, 597, 293);
		contentPane.add(lblImagenMesaFondo);
		
		JLabel lblImagenFondoVerde = new JLabel("Fondo");
		lblImagenFondoVerde.setIcon(new ImageIcon("C:\\Users\\Colo\\eclipse-workspace\\FinalPoker\\src\\ar\\edu\\unlu\\poker\\images\\FondoVerdeInicio.jpg"));
		lblImagenFondoVerde.setBounds(0, 0, 1105, 573);
		contentPane.add(lblImagenFondoVerde);
	}

}
