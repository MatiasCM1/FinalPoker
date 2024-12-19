package ar.edu.unlu.poker.vista.grafica;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class VistaApuestas extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VistaApuestas frame = new VistaApuestas();
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
	public VistaApuestas() {
		setForeground(new Color(255, 255, 255));
		setResizable(false);
		setLocationByPlatform(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1121, 612);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panelBtnApuestas = new JPanel();
		panelBtnApuestas.setOpaque(false);
		panelBtnApuestas.setBounds(687, 323, 408, 239);
		contentPane.add(panelBtnApuestas);
		panelBtnApuestas.setLayout(null);
		
		JPanel panelBtnEnvite = new JPanel();
		panelBtnEnvite.setBounds(10, 11, 388, 60);
		panelBtnApuestas.add(panelBtnEnvite);
		panelBtnEnvite.setLayout(null);
		
		JButton btnEnvite = new JButton("Envitar");
		btnEnvite.setBounds(0, 0, 388, 60);
		panelBtnEnvite.add(btnEnvite);
		btnEnvite.setOpaque(false);
		btnEnvite.setForeground(Color.WHITE);
		btnEnvite.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 30));
		btnEnvite.setBorder(null);
		
		JLabel lblFondoMaderaBtn = new JLabel("New label");
		lblFondoMaderaBtn.setBounds(0, 0, 388, 60);
		panelBtnEnvite.add(lblFondoMaderaBtn);
		lblFondoMaderaBtn.setIcon(new ImageIcon("C:\\Users\\Colo\\eclipse-workspace\\FinalPoker\\src\\ar\\edu\\unlu\\poker\\images\\imagenMadera.jpg"));
		
		JPanel panelBtnFiche = new JPanel();
		panelBtnFiche.setBounds(10, 85, 388, 60);
		panelBtnApuestas.add(panelBtnFiche);
		panelBtnFiche.setLayout(null);
		
		JButton btnFiche = new JButton("Fichar");
		btnFiche.setOpaque(false);
		btnFiche.setForeground(Color.WHITE);
		btnFiche.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 30));
		btnFiche.setBorder(null);
		btnFiche.setBounds(0, 0, 388, 60);
		panelBtnFiche.add(btnFiche);
		
		JLabel lblFondoMaderaBtn_1 = new JLabel("New label");
		lblFondoMaderaBtn_1.setIcon(new ImageIcon("C:\\Users\\Colo\\eclipse-workspace\\FinalPoker\\src\\ar\\edu\\unlu\\poker\\images\\imagenMadera.jpg"));
		lblFondoMaderaBtn_1.setBounds(0, 0, 388, 60);
		panelBtnFiche.add(lblFondoMaderaBtn_1);
		
		JPanel panelBtnPase = new JPanel();
		panelBtnPase.setBounds(10, 156, 388, 60);
		panelBtnApuestas.add(panelBtnPase);
		panelBtnPase.setLayout(null);
		
		JButton btnPase = new JButton("Pasar");
		btnPase.setOpaque(false);
		btnPase.setForeground(Color.WHITE);
		btnPase.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 30));
		btnPase.setBorder(null);
		btnPase.setBounds(0, 0, 388, 60);
		panelBtnPase.add(btnPase);
		
		JLabel lblFondoMaderaBtn_2 = new JLabel("New label");
		lblFondoMaderaBtn_2.setIcon(new ImageIcon("C:\\Users\\Colo\\eclipse-workspace\\FinalPoker\\src\\ar\\edu\\unlu\\poker\\images\\imagenMadera.jpg"));
		lblFondoMaderaBtn_2.setBounds(0, 0, 388, 60);
		panelBtnPase.add(lblFondoMaderaBtn_2);
		
		JPanel panelNotificaciones = new JPanel();
		panelNotificaciones.setBounds(623, 11, 472, 301);
		contentPane.add(panelNotificaciones);
		panelNotificaciones.setLayout(null);
		
		JTextArea textAreaNotificaciones = new JTextArea();
		textAreaNotificaciones.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 13));
		textAreaNotificaciones.setForeground(new Color(255, 255, 255));
		textAreaNotificaciones.setBounds(10, 11, 452, 279);
		panelNotificaciones.add(textAreaNotificaciones);
		textAreaNotificaciones.setBackground(new Color(0, 0, 0));
		textAreaNotificaciones.setBorder(null);
		
		JLabel lblFondoMaderaNotificaciones = new JLabel("");
		lblFondoMaderaNotificaciones.setBounds(0, 0, 472, 301);
		panelNotificaciones.add(lblFondoMaderaNotificaciones);
		lblFondoMaderaNotificaciones.setIcon(new ImageIcon("C:\\Users\\Colo\\eclipse-workspace\\FinalPoker\\src\\ar\\edu\\unlu\\poker\\images\\imagenMadera.jpg"));
		
		JPanel panelCartas = new JPanel();
		panelCartas.setBounds(10, 323, 667, 239);
		panelCartas.setOpaque(false);
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
		lblFondoMadera.setIcon(null);
		
		JLabel lblImagenMesaFondo = new JLabel("New label");
		lblImagenMesaFondo.setBounds(28, 11, 597, 293);
		lblImagenMesaFondo.setIcon(new ImageIcon("C:\\Users\\Colo\\eclipse-workspace\\FinalPoker\\src\\ar\\edu\\unlu\\poker\\images\\FondoMesa.png"));
		contentPane.add(lblImagenMesaFondo);
		
		JLabel lblImagenFondoVerde = new JLabel("Fondo");
		lblImagenFondoVerde.setBounds(10, 11, 1105, 573);
		lblImagenFondoVerde.setIcon(new ImageIcon("C:\\Users\\Colo\\eclipse-workspace\\FinalPoker\\src\\ar\\edu\\unlu\\poker\\images\\FondoVerdeInicio.jpg"));
		contentPane.add(lblImagenFondoVerde);
	}
}
