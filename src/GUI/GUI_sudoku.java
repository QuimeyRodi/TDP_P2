package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import Logica.Celda;
import Logica.FileException;
import Logica.Opcion;
import Logica.Sudoku;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Toolkit;

/**
 * Clase que modela la interfaz grafica del juego Sudoku
 * 
 * @author Quimey Rodi
 *
 */
public class GUI_sudoku extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1553445123289675595L;
	private JPanel contentPane;
	private Sudoku mi_sudoku;
	private JLabel tablero[][];
	private JLabel hora[];
	private JButton opciones[];
	private JLabel ultimo_label;
	private Temporizador temp;

	/**
	 * Inicia la aplicacion
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI_sudoku frame = new GUI_sudoku();
					frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Crea el frame del juego junto con sus componentes
	 */
	public GUI_sudoku() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(GUI_sudoku.class.getResource("/img/bola8.png")));

		setTitle("Sudoku");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 712, 559);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		try {
			mi_sudoku = new Sudoku("archivo/solucion.txt");
			tablero = new JLabel[mi_sudoku.get_filas()][mi_sudoku.get_filas()];
			opciones = new JButton[12];
			// ------------Label para los mensajes--------
			JLabel mensajes = new JLabel("");
			mensajes.setBounds(10, 38, 350, 20);
			contentPane.add(mensajes);
			// --------------------------------------------------

			crear_temporizador();
			crear_tablero_sudoku(mensajes);
			crear_panel_opciones();

			// -----------Boton para reiniciar la partida----------
			JButton reiniciar = new JButton("Nuevo juego");
			reiniciar.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					GUI_sudoku nuevo = new GUI_sudoku();
					nuevo.setVisible(true);
					dispose();
				}
			});

			reiniciar.setBounds(500, 407, 113, 23);
			contentPane.add(reiniciar);

		} catch (FileException e) {
			JOptionPane.showMessageDialog(contentPane, "ARCHIVO INVALIDO", "ERROR", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}

	/**
	 * Crea el tablero del juego Sudoku
	 * 
	 * @param mensajes JLabel para mostrar mensajes
	 */
	private void crear_tablero_sudoku(JLabel mensajes) {
		JPanel panel_tablero = new JPanel();
		panel_tablero.setBounds(10, 81, 402, 394);
		contentPane.add(panel_tablero);
		panel_tablero.setLayout(new GridLayout(9, 0, 0, 0));

		for (int f = 0; f < mi_sudoku.get_filas(); f++) {
			for (int c = 0; c < mi_sudoku.get_filas(); c++) {
				Celda celda = mi_sudoku.getCelda(f, c);
				ImageIcon grafico = celda.getEntidad_grafica().getGrafico();
				JLabel label_celda = new JLabel();

				// ------esto es solo para poder definir los cuadrantes--------------
				if ((f % 3) == 0 && (c % 3) == 0) {
					label_celda.setBorder(BorderFactory.createMatteBorder(3, 3, 1, 1, Color.DARK_GRAY));

				} else {
					if ((f % 3) == 0) {
						if (c == mi_sudoku.get_filas() - 1)
							label_celda.setBorder(BorderFactory.createMatteBorder(3, 1, 1, 3, Color.DARK_GRAY));
						else
							label_celda.setBorder(BorderFactory.createMatteBorder(3, 1, 1, 1, Color.DARK_GRAY));
					} else {
						if ((c % 3) == 0) {
							if (f == mi_sudoku.get_filas() - 1)
								label_celda.setBorder(BorderFactory.createMatteBorder(1, 3, 3, 1, Color.DARK_GRAY));
							else
								label_celda.setBorder(BorderFactory.createMatteBorder(1, 3, 1, 1, Color.DARK_GRAY));
						} else {
							if (c == mi_sudoku.get_filas() - 1)
								if (f == mi_sudoku.get_filas() - 1)
									label_celda.setBorder(BorderFactory.createMatteBorder(1, 1, 3, 3, Color.DARK_GRAY));
								else
									label_celda.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 3, Color.DARK_GRAY));
							else if (f == mi_sudoku.get_filas() - 1)
								label_celda.setBorder(BorderFactory.createMatteBorder(1, 1, 3, 1, Color.DARK_GRAY));
							else
								label_celda.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.DARK_GRAY));
						}
					}
				}
				// -------------------------------------------------------------------
				panel_tablero.add(label_celda);
				tablero[f][c] = label_celda;
				label_celda.setOpaque(true);

				if (!celda.esta_habilitada()) {
					label_celda.setBackground(Color.LIGHT_GRAY);
				}

				label_celda.addComponentListener(new ComponentAdapter() {

					@Override
					public void componentResized(ComponentEvent e) {
						label_celda.setIcon(grafico);
						reDimensionar(grafico, label_celda.getWidth(), label_celda.getHeight());
						label_celda.repaint();
					}
				});
				label_celda.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						if (celda.esta_habilitada()) {// es decir que no puede ser modificado su valor
							if (mi_sudoku.hay_error()) {
								// si el label presionado es igual al label presionado anteriormente
								if (label_celda == ultimo_label) {
									habilitar_opciones(true);
									mensajes.setText(" ");
								} else {// es que quiere modificar otra celda sin haber corregido el error anterior
									habilitar_opciones(false);
									mensajes.setText("Debe corregir el error!");
									ultimo_label.setBackground(Color.cyan);
								}

							} else {// no hay error en el tablero y la celda puede ser modificada
								habilitar_opciones(true);
								mensajes.setText(" ");
								if (ultimo_label != null && ultimo_label != label_celda)
									ultimo_label.setBackground(null);
								label_celda.setBackground(Color.GRAY);
								ultimo_label = label_celda;
								mi_sudoku.activar_celda(celda);
							}
						} else {
							mensajes.setText("Esta celda no se puede modificar");
							habilitar_opciones(false);
						}
					}
				});

			}
		}
	}

	/**
	 * Crea el panel de las opciones del juego
	 */
	private void crear_panel_opciones() {
		JPanel panel_opciones = new JPanel();
		panel_opciones.setBounds(463, 112, 218, 243);
		panel_opciones.setLayout(new GridLayout(0, 3, 0, 0));
		contentPane.add(panel_opciones);

		for (int f = 0; f < 12; f++) {
			JButton boton_num = new JButton();
			ImageIcon imagen;
			Opcion opcion;
			panel_opciones.add(boton_num);
			if (f == 9 || f == 11) {// porque son los labels vacíos
				opcion = null;
				imagen = null;
				boton_num.setEnabled(false);
			} else {
				if (f == 10) { // opcion para poder vaciar una celda
					opcion = mi_sudoku.getOpcion(0);
					imagen = opcion.getEntidad_grafica().getGrafico();
				} else { // resto de las opciones 1..9
					opcion = mi_sudoku.getOpcion(f + 1);
					imagen = opcion.getEntidad_grafica().getGrafico();
				}
			}
			opciones[f] = boton_num;
			boton_num.addComponentListener(new ComponentAdapter() {
				public void componentResized(ComponentEvent e) {
					if (imagen != null) {
						reDimensionar(imagen, boton_num.getWidth(), boton_num.getHeight());
						boton_num.repaint();
						boton_num.setIcon(imagen);
					}
				}
			});

			boton_num.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseClicked(MouseEvent e) {
					Celda celda_act;
					// si el label que presionó podia ser modificado y/o no hay error-> el boton va
					// a estar habilitado
					if (boton_num.isEnabled() && opcion != null) {
						if (ultimo_label != null) {
							celda_act = mi_sudoku.get_celda_activada();// la celda correspondiente al label presionado
							mi_sudoku.accionar(celda_act, opcion);// acciono la celda con el valor de la opcion
							reDimensionar(celda_act.getEntidad_grafica().getGrafico(), ultimo_label.getHeight(),
									ultimo_label.getWidth());
							ultimo_label.repaint();
							ultimo_label.setBackground(null);

							mi_sudoku.controlar_tablero(opcion, celda_act);

							mostrar_errores();

							if (mi_sudoku.gane()) {
								temp.stop();
								JOptionPane.showMessageDialog(contentPane, "GANASTE", "completaste el juego",
										JOptionPane.INFORMATION_MESSAGE);
								habilitar_opciones(false);
								hablitar_tablero(false);
							}
						}
					}
				}
			});

		}

	}

	/**
	 * Crea el temporizaador del juego
	 */
	private void crear_temporizador() {
		JPanel panel_hora = new JPanel();
		panel_hora.setBounds(469, 76, 200, 25);
		contentPane.add(panel_hora);
		panel_hora.setLayout(new GridLayout(0, 8, 0, 0));

		hora = new JLabel[6];
		int indice = 0;
		for (int pos = 0; pos < hora.length + 2; pos++) {
			JLabel label_hora = new JLabel();
			panel_hora.add(label_hora);
			label_hora.getBounds().getHeight();
			// son los espacios entre horas-minutos y minutos-segundos
			if (pos != 2 && pos != 5) {
				hora[indice++] = label_hora;
			}

		}
		temp = new Temporizador(hora, panel_hora.getWidth() / 8, panel_hora.getHeight());
	}

	/**
	 * Permite visualizar que celdas tienen error en el panel de juego
	 */
	private void mostrar_errores() {
		Celda actual;
		JLabel label;
		for (int f = 0; f < mi_sudoku.get_filas(); f++) {
			for (int c = 0; c < mi_sudoku.get_filas(); c++) {
				actual = mi_sudoku.getCelda(f, c);
				label = tablero[f][c];
				if (actual.tiene_error())
					if (label == ultimo_label)
						label.setBackground(Color.CYAN);
					else
						label.setBackground(Color.RED);
				else if (actual.esta_habilitada())
					label.setBackground(null);
				else
					label.setBackground(Color.LIGHT_GRAY);

			}
		}
	}

	/**
	 * Permite habilitar o deshabilitar el panel de opciones
	 * 
	 * @param e estado de las etiquetas de las opciones
	 */
	private void habilitar_opciones(boolean e) {
		for (int i = 0; i < mi_sudoku.get_filas(); i++) {
			opciones[i].setEnabled(e);
		}
		// es la imagen para vaciar una celda
		opciones[mi_sudoku.get_filas() + 1].setEnabled(e);
	}

	/**
	 * Permite habilitar o deshabilitar el panel del juego
	 * 
	 * @param e estado de las etiquetas del tablero
	 */
	private void hablitar_tablero(boolean e) {
		for (int f = 0; f < 9; f++)
			for (int c = 0; c < 9; c++)
				tablero[f][c].setEnabled(e);
	}

	/**
	 * Redimensiona la imagen parametrizada con las medidas recibidas
	 * 
	 * @param grafico imagen a redimensionar
	 * @param w       ancho de la imagen
	 * @param h       alto de la imagen
	 */
	private void reDimensionar(ImageIcon grafico, int w, int h) {
		Image image = grafico.getImage();
		if (image != null) {
			Image newimg = image.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);
			grafico.setImage(newimg);

		}
	}

}
