package ar.edu.unlu.poker.serializacion;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import ar.edu.unlu.poker.serializacion.stats.EstadisticasJugador;

public class Serializador {
	
	private static final String ARCHIVO = "estadisticasJugadores.dat";
	
	public static void guardarEstadisticas(EstadisticasJugador jugador) {
		
		List<EstadisticasJugador> jugadores = cargarEstadisticas();
		
		//Buscar si el jugador esta registrado
		boolean flag = false;
		
		for (EstadisticasJugador j : jugadores) {
			if (j.getNombreJugador().equals(jugador.getNombreJugador())) {
				j.incrementarCantidadPartidasGanadas();
				flag = true;
				break;
			}
		}
		
		if (!flag) {
			jugadores.add(jugador); //SI NO ESTA EN LA LISTA, SE AGREGA
		}
		
		//Guardar datos en el archivo
		try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(ARCHIVO))){
			for (EstadisticasJugador j : jugadores) {
				out.writeObject(j);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static List<EstadisticasJugador> cargarEstadisticas(){
		
		List<EstadisticasJugador> jugadores = new ArrayList<>();
		
		File archivo = new File(ARCHIVO);
		
		if (!archivo.exists()) {
			return jugadores; //No hay datos guardados
		}
		
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(archivo))){
			while (true) {
				try {
					EstadisticasJugador jugador = (EstadisticasJugador) in.readObject();
					jugadores.add(jugador);
				} catch (EOFException e) {
					break;
				}
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return jugadores;
		
	}
	
}
