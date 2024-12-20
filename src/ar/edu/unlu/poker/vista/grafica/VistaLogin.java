package ar.edu.unlu.poker.vista.grafica;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

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

		JPanel panelIngresar = new JPanel();
		panelIngresar.setOpaque(false);
		
		panelIngresar.setBorder(null);
		panelIngresar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		panelIngresar.setBackground(new Color(12, 79, 36));
		panelIngresar.setBounds(358, 468, 374, 75);
		contentPane.add(panelIngresar);
		panelIngresar.setLayout(null);
		
		JButton btnIngresar = new JButton("Ingresar");
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
				System.out.println("hola");
			}
		});
		btnIngresar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnIngresar.setForeground(new Color(255, 255, 255));
		btnIngresar.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 30));
		btnIngresar.setBorder(null);
		btnIngresar.setBackground(new Color(255, 128, 0));
		
		JLabel lblBtnIngresar = new JLabel("");
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
		
		JLabel lblNombreJuegoPoker = new JLabel("POKER");
		lblNombreJuegoPoker.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				String texto = lblNombreJuegoPoker.getText();
		        StringBuilder textoHTML = new StringBuilder("<html>");
		        Color[] colores = {Color.MAGENTA, Color.YELLOW, Color.GREEN, Color.CYAN, Color.RED};
		        
		        for (int i = 0; i < texto.length(); i++) {
		            textoHTML.append("<font color='")
		                     .append(toHex(colores[i % colores.length]))
		                     .append("'>")
		                     .append(texto.charAt(i))
		                     .append("</font>");
		        }
		        textoHTML.append("</html>");
		        lblNombreJuegoPoker.setText(textoHTML.toString());
			}
			
			@Override
		    public void mouseExited(MouseEvent e) {
		        // Restaurar el texto original al quitar el mouse
		        lblNombreJuegoPoker.setText("POKER");
		        lblNombreJuegoPoker.setForeground(Color.WHITE);
				lblNombreJuegoPoker.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 80));
				lblNombreJuegoPoker.setBounds(403, 45, 292, 107);
		    }
			
			private String toHex(Color color) {
			    return String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
			}
		});
		lblNombreJuegoPoker.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 80));
		lblNombreJuegoPoker.setForeground(new Color(255, 255, 255));
		lblNombreJuegoPoker.setBounds(403, 45, 292, 107);
		contentPane.add(lblNombreJuegoPoker);
		
		JLabel lblImagenFondoVerde = new JLabel("Fondo");
		lblImagenFondoVerde.setIcon(new ImageIcon(getClass().getResource("/FondoVerdeInicio.jpg")));
		lblImagenFondoVerde.setBounds(0, 0, 1121, 612);
		contentPane.add(lblImagenFondoVerde);
	}
	
	
}
