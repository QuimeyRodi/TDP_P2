package Logica;

import javax.swing.ImageIcon;

/**
 * Clase que modela una entidad grafica
 * 
 * @author Quimey Rodi
 *
 */
public class Entidad_grafica {
	protected ImageIcon grafico;
	protected String[] imagenes;

	/**
	 * Crea una entidad grafica vacia
	 */
	public Entidad_grafica() {
		grafico = new ImageIcon();
		imagenes = new String[] { "/img/bola0.png", "/img/bola1.png", "/img/bola2.png", "/img/bola3.png",
				"/img/bola4.png", "/img/bola5.png", "/img/bola6.png", "/img/bola7.png", "/img/bola8.png",
				"/img/bola9.png" };
	}

	/**
	 * Actualiza la entidad grafica, modificando su grafico
	 * 
	 * @param indice valor que corresponde con el nuevo grafico de la entidad
	 */
	public void actualizar(int indice) {
		if (indice < this.imagenes.length) {
			ImageIcon imageIcon = new ImageIcon(this.getClass().getResource(imagenes[indice]));
			this.grafico.setImage(imageIcon.getImage());
		}
	}

	/**
	 * Retorna el grafico de la entidad
	 * 
	 * @return ImageIcon que corresponde con el grafico de la entidad
	 */
	public ImageIcon getGrafico() {
		return this.grafico;
	}

//	/**
//	 * Establece el grafico de la entidad
//	 * 
//	 * @param grafico ImageIcon que sera la nueva imagen de la entidad
//	 */
//	public void setGrafico(ImageIcon grafico) {
//		this.grafico = grafico;
//	}

	/**
	 * Retorna las posibles imagenes de la entidad grafica
	 * 
	 * @return nombres de las posibles imagenes
	 */
	public String[] getImagenes() {
		return this.imagenes;
	}

}
