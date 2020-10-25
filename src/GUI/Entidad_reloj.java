package GUI;

import javax.swing.ImageIcon;

import Logica.Entidad_grafica;

/**
 * Clase que modela la entidad grafica del temporizador
 * 
 * @author Quimey Rodi
 *
 */
public class Entidad_reloj extends Entidad_grafica {

	/**
	 * Crea una entidad vacia
	 */
	public Entidad_reloj() {
		grafico = new ImageIcon();
		imagenes = new String[] { "/reloj/cero.jpeg", "/reloj/uno.jpeg", "/reloj/dos.jpeg", "/reloj/tres.jpeg",
				"/reloj/cuatro.jpeg", "/reloj/cinco.jpeg", "/reloj/seis.jpeg", "/reloj/siete.jpeg", "/reloj/ocho.jpeg",
				"/reloj/nueve.jpeg" };
	}

}