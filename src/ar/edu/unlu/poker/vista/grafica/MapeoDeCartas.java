package ar.edu.unlu.poker.vista.grafica;

import java.io.File;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

public class MapeoDeCartas {

	private Map<String, String> mapaImagenes;

	public MapeoDeCartas() {

		mapaImagenes = new HashMap<>();

		this.comenzarMapeo();

	}

	private void comenzarMapeo() {

		String[] palos = { "PICA", "CORAZON", "TREBOL", "DIAMANTE" };
		String[] valores = { "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "AS" };

		for (String palo : palos) {

			for (String valor : valores) {

				String nombreCarta = valor + " de " + palo;
				String nombreImagen = obtenerNombreImagen(palo, valor);

				mapaImagenes.put(nombreCarta, nombreImagen);

			}
		}
	}

	private String obtenerNombreImagen(String palo, String valor) {

		String paloImagen;

		switch (palo) {
		case "PICA":
			paloImagen = "Pica";
			break;
		case "CORAZON":
			paloImagen = "Corazon";
			break;
		case "TREBOL":
			paloImagen = "Trebol";
			break;
		case "DIAMANTE":
			paloImagen = "Diamante";
			break;
		default:
			throw new IllegalArgumentException("Palo no reconocido: " + palo);
		}

		return paloImagen + " - " + valor;

	}

	public ImageIcon getImagenCarta(String nombreCarta) {

		String nombreImagen = mapaImagenes.get(nombreCarta);

		if (nombreImagen != null) {
			java.net.URL recurso = getClass().getResource("/cards/" + nombreImagen + ".png");

			if (recurso != null) {
				return new ImageIcon(recurso);
			} else {
				System.err.println("Recurso no encontrado para la carta: " + nombreCarta);
				return null;
			}
		} else {
			System.err.println("Carta inexistente: " + nombreCarta);
			return null;
		}

	}

}
