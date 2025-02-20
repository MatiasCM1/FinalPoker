package ar.edu.unlu.poker.modelo;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.LinkedList;

public class ResultadoJugadaJugador {

	private static final HashMap<String, Integer> valorCarta = new HashMap<String, Integer>();

	public ResultadoJugadaJugador() {
		valorCarta.put("2", 2);
		valorCarta.put("3", 3);
		valorCarta.put("4", 4);
		valorCarta.put("5", 5);
		valorCarta.put("6", 6);
		valorCarta.put("7", 7);
		valorCarta.put("8", 8);
		valorCarta.put("9", 9);
		valorCarta.put("10", 10);
		valorCarta.put("J", 11);
		valorCarta.put("Q", 12);
		valorCarta.put("K", 13);
		valorCarta.put("AS", 14);
	}

	public Resultado devolverValor(LinkedList<Carta> cartas) throws RemoteException {
		if (escaleraColor(cartas)) {
			return Resultado.ESCALERA_COLOR;
		} else if (poker(cartas)) {
			return Resultado.POKER;
		} else if (full(cartas)) {
			return Resultado.FULL;
		} else if (color(cartas)) {
			return Resultado.COLOR;
		} else if (escalera(cartas)) {
			return Resultado.ESCALERA;
		} else if (trio(cartas)) {
			return Resultado.TRIO;
		} else if (doblePar(cartas)) {
			return Resultado.DOBLE_PAR;
		} else if (par(cartas)) {
			return Resultado.PAR;
		} else {
			return Resultado.CARTA_MAYOR;
		}
	}

	private boolean escaleraColor(LinkedList<Carta> cartas) throws RemoteException {
		return ((escalera(cartas)) && (color(cartas)));
	}

	private boolean color(LinkedList<Carta> cartas) throws RemoteException {
		String paloCarta = cartas.getFirst().getPalo();
		for (Carta c : cartas) {
			if (!(c.getPalo().equals(paloCarta))) {
				return false;
			}
		}
		return true;
	}

	private boolean escalera(LinkedList<Carta> cartas) throws RemoteException {
		boolean flagAS = cartas.getLast().getValor().equals("AS");
		int cartaAnt = valorCarta.get(cartas.getFirst().getValor());
		for (int i = 1; i < cartas.size(); i++) {
			int cartaActual = valorCarta.get(cartas.get(i).getValor());
			if (cartaActual != cartaAnt + 1) {
				if (flagAS && cartaAnt == 5 && cartaActual == 14) { // Verifica escalera minima donde el AS es 1
					continue;
				}
				return false;
			}
			cartaAnt = cartaActual;
		}
		return true;
	}

	private int contarRepeticiones(LinkedList<Carta> cartas, Carta carta) {
		return (int) cartas.stream().filter(c -> {
			try {
				return c.getValor().equals(carta.getValor());
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}).count();
	}

	private boolean par(LinkedList<Carta> cartas) {
		return cartas.stream().anyMatch(c -> contarRepeticiones(cartas, c) == 2);
	}

	private boolean trio(LinkedList<Carta> cartas) {
		return cartas.stream().anyMatch(c -> contarRepeticiones(cartas, c) == 3);
	}

	private boolean poker(LinkedList<Carta> cartas) {
		return cartas.stream().anyMatch(c -> contarRepeticiones(cartas, c) == 4);
	}

	private Carta mayorDeDosCartas(Carta c1, Carta c2) throws RemoteException {
		int valorC1 = valorCarta.get(c1.getValor());
		int valorC2 = valorCarta.get(c2.getValor());
		return (valorC1 > valorC2) ? c1 : (valorC1 < valorC2) ? c2 : null;
	}

	private Carta cartaMasAlta(LinkedList<Carta> cartas) {
		return cartas.getLast();
	}

	private boolean doblePar(LinkedList<Carta> cartas) {
		LinkedList<Carta> cartasRestantes = new LinkedList<Carta>(cartas);
		// Buscar el primer par
		Carta primerPar = cartasRestantes.stream().filter(c -> contarRepeticiones(cartasRestantes, c) == 2).findFirst()
				.orElse(null);

		if (primerPar != null) {
			// Eliminar las cartas del primer par
			cartasRestantes.removeIf(c -> {
				try {
					return c.getValor().equals(primerPar.getValor());
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				return false;
			});
			// Buscar el segundo par
			Carta segundoPar = cartasRestantes.stream().filter(c -> contarRepeticiones(cartasRestantes, c) == 2)
					.findFirst().orElse(null);

			return segundoPar != null;
		}
		return false;
	}

	private boolean full(LinkedList<Carta> cartas) {
		// Verificar si hay trio
		Carta cartaTrio = cartas.stream().filter(c -> contarRepeticiones(cartas, c) == 3).findFirst().orElse(null);
		if (cartaTrio != null) { // Elimino las cartas del trio para verificar que las cartas del par sean
									// distintas que las del trio
			LinkedList<Carta> cartasRestantes = new LinkedList<Carta>(cartas);
			cartasRestantes.removeIf(c -> {
				try {
					return c.getValor().equals(cartaTrio.getValor());
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return false;
			});
			// Verificar si hay par
			boolean hayPar = cartasRestantes.stream().anyMatch(c -> contarRepeticiones(cartasRestantes, c) == 2);
			return hayPar;
		}
		return false;
	}

	public static HashMap<String, Integer> getValorcarta() {
		return valorCarta;
	}

}
