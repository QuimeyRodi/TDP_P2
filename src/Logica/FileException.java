package Logica;

/**
 * Clase que modela unas situación de archivo de solución incorrecto
 * 
 * @author Quimey Rodi
 *
 */
public class FileException extends Exception {

	private static final long serialVersionUID = -6425012407312898225L;

	/**
	 * El constructor inicializa la excepción, indicando el origen del error,
	 * 
	 * @param cadena - mensaje de la situación
	 */
	public FileException(String cadena) {
		super(cadena);
	}
}
