package ar.edu.unlu.poker.vista.grafica;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

public class VistaLogin extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNombre;
	private JTextField txtFondos;
	private int xMouse;
	private int yMouse;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VistaLogin frame = new VistaLogin();
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
	public VistaLogin() {
		setLocationByPlatform(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1121, 612);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnIngresar = new JButton("Ingresar");
		btnIngresar.setOpaque(false);
		btnIngresar.setBounds(358, 468, 374, 75);
		contentPane.add(btnIngresar);
		btnIngresar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnIngresar.setForeground(Color.black);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				btnIngresar.setForeground(Color.black);
			}
		});
		btnIngresar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnIngresar.setForeground(new Color(255, 255, 255));
		btnIngresar.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 30));
		btnIngresar.setBorder(null);
		btnIngresar.setBackground(new Color(255, 128, 0));
		
		JPanel panelIngresar = new JPanel();
		panelIngresar.setOpaque(false);
		
		panelIngresar.setBorder(null);
		panelIngresar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		panelIngresar.setBackground(new Color(12, 79, 36));
		panelIngresar.setBounds(358, 468, 374, 75);
		contentPane.add(panelIngresar);
		panelIngresar.setLayout(null);
		
		JLabel lblBtnIngresar = new JLabel("");
		
		lblBtnIngresar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblBtnIngresar.setHorizontalAlignment(SwingConstants.CENTER);
		lblBtnIngresar.setForeground(new Color(255, 255, 255));
		lblBtnIngresar.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 30));
		lblBtnIngresar.setBounds(0, 0, 374, 75);
		panelIngresar.add(lblBtnIngresar);

		
		JLabel lblFondomadera = new JLabel("New label");
		lblFondomadera.setIcon(new ImageIcon("C:\\Users\\Colo\\eclipse-workspace\\FinalPoker\\src\\ar\\edu\\unlu\\poker\\images\\imagenMadera.jpg"));
		lblFondomadera.setBounds(358, 468, 374, 75);
		contentPane.add(lblFondomadera);
		
		JSeparator separator_fondos = new JSeparator();
		separator_fondos.setBounds(358, 429, 374, 2);
		contentPane.add(separator_fondos);
		
		txtFondos = new JTextField();
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
		txtNombre.setBorder(null);
		txtNombre.setText("Ingrese su nombre de usuario");
		txtNombre.setForeground(new Color(192, 192, 192));
		txtNombre.setBounds(358, 220, 374, 31);
		contentPane.add(txtNombre);
		txtNombre.setColumns(10);
		
		JLabel lblImagenCartas = new JLabel("Fondo Fichas");
		lblImagenCartas.setIcon(new ImageIcon("C:\\Users\\Colo\\eclipse-workspace\\FinalPoker\\src\\ar\\edu\\unlu\\poker\\images\\FotoFichasInicio.png"));
		lblImagenCartas.setBounds(892, 66, 239, 411);
		contentPane.add(lblImagenCartas);
		
		JLabel lblNewLabel = new JLabel("Nombre");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 30));
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setBounds(358, 163, 278, 46);
		contentPane.add(lblNewLabel);
		
		JLabel lblNombreJuegoPoker = new JLabel("POKER");
		lblNombreJuegoPoker.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 80));
		lblNombreJuegoPoker.setForeground(new Color(255, 255, 255));
		lblNombreJuegoPoker.setBounds(387, 31, 292, 107);
		contentPane.add(lblNombreJuegoPoker);
		
		JLabel lblImagenFondoVerde = new JLabel("Fondo");
		lblImagenFondoVerde.setIcon(new ImageIcon("C:\\Users\\Colo\\eclipse-workspace\\FinalPoker\\src\\ar\\edu\\unlu\\poker\\images\\FondoVerdeInicio.jpg"));
		lblImagenFondoVerde.setBounds(0, 0, 1131, 617);
		contentPane.add(lblImagenFondoVerde);
	}
	
	
}
