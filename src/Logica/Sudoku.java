package Logica;

import java.util.LinkedList;
import java.util.Random;
import java.io.*;

/**
 * Clase que modela la parte logica del juego Sudoku. El juego consiste en
 * completar una serie de 9 paneles que a su vez se encuentran divididos en 9
 * celdas con números del 1 al 9.
 * 
 * @author Quimey Rodi
 *
 */
public class Sudoku {
	private Celda[][] tablero;
	private Opcion[] opciones;
	private int[][] cont_archivo; // Contenido del archivo
	private Celda celda_activada;
	private boolean hay_error;
	private int filas;
	private LinkedList<Celda> errores;

	/**
	 * Inicializa el juego
	 * 
	 * @param ruta ruta del archivo con solucion de sudoku
	 * @throws FileException si la solucion en el archivo no es valida o el formato
	 *                       no es valido
	 */
	public Sudoku(String ruta) throws FileException {
		filas = 9;
		tablero = new Celda[filas][filas];
		cont_archivo = new int[filas][filas];
		celda_activada = null;
		hay_error = false;
		errores = new LinkedList<Celda>();
		boolean archivo_valido = controlar_archivo(ruta);

		if (archivo_valido) { // si el formato y solucion son validos, creo las celdas y las opciones para el
								// juego
			for (int i = 0; i < filas; i++) {
				for (int j = 0; j < filas; j++) {
					Celda celda = new Celda();
					tablero[i][j] = celda;
					Random random = new Random();
					int valor = random.nextInt(2);

					// si el valor obtenido con en random es != 0 actualizo la celda con el valor en
					// la pos i,j del archivo
					if (valor != 0) {
						celda.actualizar(cont_archivo[i][j]);
						celda.set_habilitada(false);
					} else
						celda.setValor(0);
					celda.setColumna(j);
					celda.setFila(i);
				}
			}

			opciones = new Opcion[10];
			for (int f = 0; f < 10; f++) {
				opciones[f] = new Opcion();
				opciones[f].setValor(f);
			}
		} else
			throw new FileException("Formato invalido del archivo");

	}

	/**
	 * Retorna la cantidad de filas del tablero
	 * 
	 * @return cantidad de filas
	 */
	public int get_filas() {
		return filas;
	}

	/**
	 * Retorna true si el tablero esta completo y no hay errores, false en caso
	 * contrario
	 * 
	 * @return estado de finalizacion de la partida
	 */
	public boolean gane() {
		boolean gane = true;
		if (!hay_error) {
			for (int fila = 0; fila < filas && gane; fila++) {
				for (int col = 0; col < filas && gane; col++) {
					if (tablero[fila][col].getValor() == 0 || tablero[fila][col].tiene_error()) {
						gane = false;
					}
				}
			}
		} else
			gane = false;
		return gane;
	}

	/**
	 * Retorna true si hay error en el tablero, false en caso contrario
	 * 
	 * @return estado de error en el tablero de juego
	 */
	public boolean hay_error() {
		return hay_error;
	}

	/**
	 * Activa la celda parametrizada, es decir que esta celda es la que será
	 * modificada
	 * 
	 * @param celda celda a activar
	 */
	public void activar_celda(Celda celda) {
		this.celda_activada = celda;
	}

	/**
	 * Retona la celda activada del tablero
	 * 
	 * @return celda activada
	 */
	public Celda get_celda_activada() {
		Celda retorno = null;
		if (celda_activada != null) {
			retorno = celda_activada;
		}
		return retorno;
	}

	/**
	 * Acciona una celda, cambiando su valor por el de la opcion recibida
	 * 
	 * @param c      Celda
	 * @param opcion Opcion
	 */
	public void accionar(Celda c, Opcion opcion) {
		int valor = opcion.getValor();
		c.actualizar(valor);
	}

	/**
	 * Retorna la opcion en la posicion parametrizada
	 * 
	 * @param i posicion de la opcion
	 * @return opcion del juego
	 */
	public Opcion getOpcion(int i) {
		return opciones[i];
	}

	/**
	 * Retorna la celda en la posicion i,j parametrizada
	 * 
	 * @param i fila
	 * @param j columna
	 * @return Celda celda en la posicion
	 */
	public Celda getCelda(int i, int j) {
		return tablero[i][j];
	}

	/**
	 * Controla que el archivo en la ruta parametrizada contenga una solucion valida
	 * de sudoku, retorna true si la solucion es vallida, false en caso contrario
	 * 
	 * @param ruta ruta del archivo a controlar
	 * @return estado de validez de la solucion en el archivo
	 */
	private boolean controlar_archivo(String ruta) {
		boolean es_valido = true;
		BufferedReader puntero = null;
		String arr[];
		int col = 0, fil = 0;
		int digito;
		try {
			InputStream in = Sudoku.class.getClassLoader().getResourceAsStream(ruta);
			InputStreamReader inr = new InputStreamReader(in);
			puntero = new BufferedReader(inr);

			String linea;
			while ((linea = puntero.readLine()) != null && fil < cont_archivo.length) {
				arr = linea.split(" ");
				if (arr.length == filas) {
					for (col = 0; col < arr.length; col++) {
						if (es_numero(arr[col])) {
							digito = Integer.parseInt(arr[col]);
							if (digito > 0 && digito <= 9) {
								cont_archivo[fil][col] = digito;
							} else {
								es_valido = false;
							}
						} else {
							es_valido = false;
						}
					}
					fil++;
				} else {
					es_valido = false;
				}

			}
			if (fil >= filas && linea != null) // <-- tiene filas de mas
				es_valido = false;
			else {
				for (int f = 0; f < filas && es_valido; f++) {
					if (es_valido)
						es_valido = controlar_filas_arch(f);
				}
				for (int c = 0; c < filas && es_valido; c++) {
					if (es_valido)
						es_valido = controlar_columnas_arch(c);
				}

			}

		} catch (IOException e) {
			e.printStackTrace();

		}
		return es_valido;

	}

	/**
	 * Marca a la celda parametrizada estableciendo si tiene o no error
	 * 
	 * @param celda celda a marcar
	 * @param e     estado del error de la celda
	 */
	private void marcar_celda(Celda celda, boolean e) {
		celda.set_error(e);
	}

	/**
	 * Elimina los errores del tablero
	 * 
	 * @param index_actual posicion del primer error
	 */
	private void eliminar_errores_anteriores(int index_actual) {
		Celda celda_actual;
		if (index_actual < errores.size()) {
			celda_actual = errores.get(index_actual);
			celda_actual.set_error(false);
			eliminar_errores_anteriores(index_actual + 1);
			errores.remove(index_actual);
		}

	}

	/**
	 * Controla el tablero tras modifcar la celda parametrizada con el valor de la
	 * opcion parametrizada
	 * 
	 * @param op  opcion seleccionada
	 * @param cel ultima celda modificada
	 */
	public void controlar_tablero(Opcion op, Celda cel) {
		int comp = op.getValor();
		Celda control_cuad = controlar_cuadrante_tab(cel, comp);
		Celda control_fila = controlar_fila_tab(cel, comp);
		Celda control_col = controlar_columna_tab(cel, comp);
		hay_error = !(control_cuad == null && control_fila == null && control_col == null);

		if (!errores.isEmpty()) {
			eliminar_errores_anteriores(0);
		}
		if (hay_error) {
			if (control_cuad != null) {
				marcar_celda(control_cuad, true);
				errores.add(control_cuad);
			}
			if (control_fila != null) {
				marcar_celda(control_fila, true);
				errores.add(control_fila);
			}
			if (control_col != null) {
				marcar_celda(control_col, true);
				errores.add(control_col);
			}
			marcar_celda(cel, true);
		} else {
			eliminar_errores_anteriores(0);
			marcar_celda(cel, false);
		}
	}

	/**
	 * Controla si en la fila de la celda parametrizada hay error tras colocar en
	 * ella el valor parametrizado
	 * 
	 * @param cel        celda del tablero modifcada
	 * @param componente valor de la opcion colocada en la celda
	 * @return estado de validez de la fila
	 */
	private Celda controlar_fila_tab(Celda cel, int componente) {
		Celda celda_error = null;
		int fila = cel.getFila();
		Celda actual;
		if (componente != 0) {
			for (int col = 0; col < filas && celda_error == null; col++) {
				actual = tablero[fila][col];
				if (actual != cel && actual.getValor() == componente)
					celda_error = actual;
			}
		}
		return celda_error;
	}

	/**
	 * 
	 * * Controla si en la columna de la celda parametrizada hay error tras colocar
	 * en ella el valor parametrizado
	 * 
	 * @param cel        celda del tablero modifcada
	 * @param componente valor de la opcion colocada en la celda
	 * @return estado de validez de la columna
	 */
	private Celda controlar_columna_tab(Celda cel, int componente) {
		Celda celda_error = null;
		Celda actual;
		int columna = cel.getColumna();
		if (componente != 0) {
			for (int fila = 0; fila < filas && celda_error == null; fila++) {
				actual = tablero[fila][columna];
				if (actual != cel && actual.getValor() == componente) {
					celda_error = actual;
				}
			}
		}
		return celda_error;
	}

	/**
	 * 
	 * Controla si en el cuadrante de la celda parametrizada hay error tras colocar
	 * en ella el valor parametrizado
	 * 
	 * @param cel        celda del tablero modifcada
	 * @param componente valor de la opcion colocada en la celda
	 * @return estado de validez del cuadrante
	 */
	private Celda controlar_cuadrante_tab(Celda cel, int componente) {
		Celda celda_error = null;
		Celda actual;
		int fila = (cel.getFila() / 3) * 3;
		int columna = (cel.getColumna() / 3) * 3;
		
		if (componente != 0) {
			for (int i = fila; i < fila + 3 && celda_error == null; i++) {
				for (int j = columna; j < columna + 3 && celda_error == null; j++) {
					actual = tablero[i][j];
					if (actual != cel && actual.getValor() == componente)
						celda_error = actual;
				}
			}
		}
		return celda_error;
	}

	/**
	 * Controla si la cadena parametrizada es un numero, retorna true si lo es,
	 * false en caso contrario
	 * 
	 * @param cadena cadena a controlar
	 * @return estado de la validacion de la cadena
	 */
	private static boolean es_numero(String cadena) {
		boolean resultado;
		try {
			Integer.parseInt(cadena);
			resultado = true;
		} catch (NumberFormatException excepcion) {
			resultado = false;
		}
		return resultado;
	}

	/**
	 * Controla si la fila parametrizad del archivo es valida, retorna true si es
	 * valida,false en caso contrario
	 * 
	 * @param fila fila a controlar
	 * @return estado de validez de la fila del archivo
	 */
	private boolean controlar_filas_arch(int fila) {
		boolean cumple_filas = true;
		int numero = 1;
		boolean encontre = false;
		while (cumple_filas && numero < 10) {
			for (int col = 0; col < filas && cumple_filas && !encontre; col++) {
				if (cont_archivo[fila][col] == numero) {
					encontre = true;
					numero++;
				} else if (col == filas - 1)
					cumple_filas = false;
			}
			encontre = false;
		}
		return cumple_filas;

	}

	/**
	 * Controla si la columna parametrizada del archivo es valida, retorna true si
	 * es valida,false en caso contrario
	 * 
	 * @param col columna a controlar
	 * @return estado de validez de la columna del archivo
	 */
	private boolean controlar_columnas_arch(int col) {
		boolean cumple_columnas = true;
		int numero = 1;
		boolean encontre = false;
		while (cumple_columnas && numero < 10) {
			for (int fil = 0; fil < filas && cumple_columnas && !encontre; fil++) {
				if (cont_archivo[fil][col] == numero) {
					encontre = true;
					numero++;
				} else if (fil == filas - 1)
					cumple_columnas = false;
			}
			encontre = false;
		}
		return cumple_columnas;
	}
}
