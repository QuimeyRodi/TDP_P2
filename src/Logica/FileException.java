package Logica;

/**
 * Clase que modela unas situaci�n de archivo de soluci�n incorrecto
 * 
 * @author Quimey Rodi
 *
 */
public class FileException extends Exception {

	private static final long serialVersionUID = -6425012407312898225L;

	/**
	 * El constructor inicializa la excepci�n, indicando el origen del error,
	 * 
	 * @param cadena - mensaje de la situaci�n
	 */
	public FileException(String cadena) {
		super(cadena);
	}
}
