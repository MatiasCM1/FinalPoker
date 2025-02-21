package ar.edu.unlu.poker.serializacion;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unlu.poker.serializacion.stats.EstadisticasJugador;

public class Serializador {

	private static final String ARCHIVO = "estadisticasJugadores.dat";

	public static void guardarEstadisticas(List<EstadisticasJugador> jugadores) { //Recibe una lista con los datos y lo guarda en el archivo

		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO))) {
			oos.writeObject(jugadores);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	public static List<EstadisticasJugador> cargarEstadisticas() { //Devuelve la lista que esta guardada en el archivo

		File archivo = new File(ARCHIVO);
		if (!archivo.exists()) {
			return new ArrayList<>(); // Si el archivo no existe, devuelve una lista vacia
		}
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARCHIVO))) {
			return (List<EstadisticasJugador>) ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			return new ArrayList<>(); // Si hay un error, devuelve una lista vacia
		}

	}

}
