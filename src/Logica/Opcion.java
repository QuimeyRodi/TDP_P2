package Logica;

/**
 * Clase que modela una opcion de sudoku
 * 
 * @author Quimey Rodi
 *
 */
public class Opcion {
	private Integer valor;
	private Entidad_grafica entidad;

	/**
	 * El constructor crea una opcion vacia
	 */
	public Opcion() {
		this.valor = null;
		this.entidad = new Entidad_grafica();
	}

	/**
	 * Retorna el valor de la opcion
	 * 
	 * @return valor de la opcion
	 */
	public Integer getValor() {
		return valor;
	}

	/**
	 * Establece el valor de la opcion
	 * 
	 * @param valor valor de la opcion
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
	 * Retorna la entidad grafica de la opcion
	 * 
	 * @return entidad grafica de la opcion
	 */
	public Entidad_grafica getEntidad_grafica() {
		return this.entidad;
	}

	/**
	 * Establece la entidad grafica de la opcion
	 * 
	 * @param g nueva entidad grafica
	 */
	public void setGrafica(Entidad_grafica g) {
		this.entidad = g;
	}

	/**
	 * Obtiene la cantidad de elementos de la entidad grafica de la opcion
	 * 
	 * @return cantidad de elementos
	 */
	public int getCantElementos() {
		return this.entidad.getImagenes().length;
	}

}
