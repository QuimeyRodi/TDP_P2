package GUI;

import java.awt.Image;
import java.util.Timer;
//import java.util.TimerTask;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import Logica.Entidad_grafica;

/**
 * Clase que modela un temporizador y su grafica
 * 
 * @author Quimey Rodi
 *
 */
public class Temporizador {
	private Timer timer;
	private TimerTask task;
	private int hours;
	private int minutes;
	private int seconds;

	/**
	 * Inicializa y comienza con el temporizador
	 * 
	 * @param hora labels donde se vera el temporizador
	 * @param w    ancho de cada label
	 * @param h    alto de cada label
	 */
	public Temporizador(JLabel[] hora, int w, int h) {
		timer = new Timer();
		hours = minutes = seconds = 0;
//	}
//
//	/**
//	 * Comienza con el temporizador (actualizando las imagenes)
//	 * 
//	 * @param hora labels donde se vera el temporizador
//	 * @param w    ancho de cada label
//	 * @param h    alto de cada label
//	 */
//	public void comenzar(JLabel[] hora, int w, int h) {

		task = new TimerTask() {

			int sec = 0;
			Entidad_grafica entidad_hora1 = new Entidad_reloj();
			Entidad_grafica entidad_hora2 = new Entidad_reloj();
			Entidad_grafica entidad_min1 = new Entidad_reloj();
			Entidad_grafica entidad_min2 = new Entidad_reloj();
			Entidad_grafica entidad_sec1 = new Entidad_reloj();
			Entidad_grafica entidad_sec2 = new Entidad_reloj();

			public void run() {
				update_time(sec);

				entidad_hora2.actualizar(hours % 10);
				reDimensionar(entidad_hora2.getGrafico(), w, h);
				hora[1].setIcon(entidad_hora2.getGrafico());
				hora[1].repaint();
				hours = hours / 10;

				entidad_hora1.actualizar(hours);
				hora[0].setIcon(entidad_hora1.getGrafico());
				reDimensionar(entidad_hora1.getGrafico(), w, h);
				hora[0].repaint();

				entidad_min2.actualizar(minutes % 10);
				hora[3].setIcon(entidad_min2.getGrafico());
				reDimensionar(entidad_min2.getGrafico(), w, h);
				hora[3].repaint();
				minutes = minutes / 10;

				entidad_min1.actualizar(minutes);
				hora[2].setIcon(entidad_min1.getGrafico());
				reDimensionar(entidad_min1.getGrafico(), w, h);
				hora[2].repaint();

				entidad_sec2.actualizar(seconds % 10);
				hora[5].setIcon(entidad_sec2.getGrafico());
				reDimensionar(entidad_sec2.getGrafico(), w, h);
				hora[5].repaint();
				seconds = seconds / 10;

				entidad_sec1.actualizar(seconds);
				hora[4].setIcon(entidad_sec1.getGrafico());
				reDimensionar(entidad_sec1.getGrafico(), w, h);
				hora[4].repaint();

				sec++;
			}
		};
		runTimer();
	}

	/**
	 * Actualiza el tiempo del temporizador
	 * 
	 * @param sec segundos transcurridos
	 */
	public void update_time(int sec) {
		int resto = 0;
		if (sec >= 3600) { // una hora o más
			hours = sec / 3600;
			resto = sec % 3600; // por si es mas o menos de un minuto

			if (resto >= 60) { // controlo si el esto es mas o igual a un minuto
				minutes = resto / 60;
				seconds = resto % 60;
			} else { // si es menos de un minuto
				seconds = resto;
			}
		}
		// un minuto o mas
		else if (sec >= 60) {
			hours = 0; // 62
			minutes = sec / 60;
			seconds = sec % 60;
		}
		// solo segundos
		else if (sec < 60) {
			hours = 0;
			minutes = 0;
			seconds = sec;
		}

	}

	/**
	 * Inicia el temporizador
	 * 
	 */
	public void runTimer() {
		timer.schedule(task, 0, 1000);
	}

	/**
	 * Detiene el temporizador
	 */
	public void stop() {
		timer.cancel();
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