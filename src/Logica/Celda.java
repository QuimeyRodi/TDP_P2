package Logica;

/**
 * Clase que modela las celdas de sudoku
 * 
 * @author Quimey Rodi
 *
 */
public class Celda {
	private Integer valor;
	private Entidad_grafica entidad;
	private int fila;
	private int columna;
	private boolean habilitada; // si la celda puede ser modificada o no
	private boolean tiene_error;

	/**
	 * Crea una celda vacia
	 */
	public Celda() {
		this.valor = null;
		this.entidad = new Entidad_grafica();
		fila = columna = 0;
		habilitada = true;
		tiene_error = false;
	}

	/**
	 * Actualiza una celda, modificando su valor, esta relacionado con la entidad
	 * grafica
	 * 
	 * @param valor_nuevo entero que representa el nuevo valor
	 */
	public void actualizar(int valor_nuevo) {
		if (valor_nuevo < this.getCantElementos()) {
			valor = valor_nuevo;
		}
		entidad.actualizar(valor);
	}

	/**
	 * Establece si la celda está ocasionando error o no
	 * 
	 * @param error estado del error de la celda
	 */
	public void set_error(boolean error) {
		tiene_error = error;
	}

	/**
	 * Retorna true si la celda tiene error, false en caso contrario
	 * 
	 * @return estado del error de la celda
	 */
	public boolean tiene_error() {
		return tiene_error;
	}

	/**
	 * Retorna la cantidad de elementos de la entidad grafica de la celda
	 * 
	 * @return cantidad de elementos de la entidad grafica
	 */
	private int getCantElementos() {
		return this.entidad.getImagenes().length;
	}

	/**
	 * Retorna el valor de la celda
	 * 
	 * @return valor de la celda
	 */
	public Integer getValor() {
		return valor;
	}

	/**
	 * Establece el valor de la celda (que estará relacionado con su entidad
	 * grafica)
	 * 
	 * @param valor nuevo valor de la celda
	 */
	public void setValor(Integer valor) {
		if (valor != null && valor < this.getCantElementos()) {
			this.valor = valor;
			entidad.actualizar(this.valor);
		} else {
			this.valor = null;
		}
	}

	/**
	 * Retorna la entidad grafica de la celda
	 * 
	 * @return entidad grafica de la celda
	 */
	public Entidad_grafica getEntidad_grafica() {
		return this.entidad;
	}

	/**
	 * Retorna la fila de la celda
	 * 
	 * @return fila de la celda
	 */
	public int getFila() {
		return fila;
	}

	/**
	 * Establece la fila de la celda
	 * 
	 * @param fila fila de la celda
	 */
	public void setFila(int fila) {
		this.fila = fila;
	}

	/**
	 * Retorna la columna de la celda
	 * 
	 * @return columna de la celda
	 */
	public int getColumna() {
		return columna;
	}

	/**
	 * Establece la columna de la celda
	 * 
	 * @param columna columna de la celda
	 */

	public void setColumna(int columna) {
		this.columna = columna;
	}

	/**
	 * Consulta si la celda puede ser modificada o no
	 * 
	 * @return estado de la habilitacion de la celda
	 */
	public boolean esta_habilitada() {
		return habilitada;
	}

	/**
	 * Establece si la celda puede ser modificada o no
	 * 
	 * @param habilitada estado de la habilitacion de la celda
	 */
	public void set_habilitada(boolean habilitada) {
		this.habilitada = habilitada;
	}
}
