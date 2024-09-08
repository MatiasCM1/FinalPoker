package ar.edu.unlu.poker.modelo;

import java.rmi.RemoteException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;

public class ResultadoJugadaJugador {
	
	private static final HashMap<String, Integer> valorCarta= new HashMap<String, Integer>();
	
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
	
	public LinkedList<Carta> ordenarCartas(LinkedList<Carta> cartas){
		LinkedList<Carta> cartasOrdenadas = new LinkedList<Carta>(cartas);
		cartasOrdenadas.sort(Comparator.comparing(carta -> {
			try {
				return valorCarta.get(carta.getValor());
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}));
		return cartasOrdenadas;
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
	
	public boolean escaleraColor(LinkedList<Carta> cartas) throws RemoteException {
		return ((escalera(cartas)) && (color(cartas)));
	}
	
	public boolean color(LinkedList<Carta> cartas) throws RemoteException {
		String paloCarta = cartas.getFirst().getPalo();
		for (Carta c : cartas) {
			if (!(c.getPalo().equals(paloCarta))) {
				return false;
			}
		}
		return true;
	}
	
	public boolean escalera(LinkedList<Carta> cartas) throws RemoteException {
		LinkedList<Carta> cartasOrdenadas = this.ordenarCartas(cartas);
		boolean flagAS = cartasOrdenadas.getLast().getValor().equals("AS");
		int cartaAnt = valorCarta.get(cartasOrdenadas.getFirst().getValor());
		for (int i = 1; i < cartasOrdenadas.size(); i++) {
			int cartaActual = valorCarta.get(cartasOrdenadas.get(i).getValor());
			if (cartaActual != cartaAnt + 1) {
				if(flagAS && cartaAnt == 5 && cartaActual == 14) { //Verifica escalera minima donde el AS es 1
					continue;
				}
				return false;
			}
			cartaAnt = cartaActual;
		}
		return true;
	}
	
	public int contarRepeticiones(LinkedList<Carta> cartas, Carta carta) {
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
	
	public boolean par(LinkedList<Carta> cartas) {
		return cartas.stream().anyMatch(c -> contarRepeticiones(cartas, c) == 2);
	}
	
	public boolean trio(LinkedList<Carta> cartas) {
		return cartas.stream().anyMatch(c -> contarRepeticiones(cartas, c) == 3);
	}
	
	public boolean poker(LinkedList<Carta> cartas) {
		return cartas.stream().anyMatch(c -> contarRepeticiones(cartas, c) == 4);
	}
	
	public Carta mayorDeDosCartas(Carta c1, Carta c2) throws RemoteException {
		int valorC1 = valorCarta.get(c1.getValor());
        int valorC2 = valorCarta.get(c2.getValor());
        return (valorC1 > valorC2) ? c1 : (valorC1 < valorC2) ? c2 : null;
	}
	
	public Carta cartaMasAlta(LinkedList<Carta> cartas) {
		LinkedList<Carta> cartasOrdenadas = this.ordenarCartas(cartas);
		return cartasOrdenadas.getLast();
	}
	
	public boolean doblePar(LinkedList<Carta> cartas) {
		LinkedList<Carta> cartasRestantes = new LinkedList<Carta>(cartas);
	    // Buscar el primer par
	    Carta primerPar = cartasRestantes.stream().filter(c -> contarRepeticiones(cartasRestantes, c) == 2).findFirst().orElse(null);
	    if (primerPar != null) {
	        // Eliminar las cartas del primer par
	        cartasRestantes.removeIf(c -> {
				try {
					return c.getValor().equals(primerPar.getValor());
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return false;
			});
	        // Buscar el segundo par
	        Carta segundoPar = cartasRestantes.stream().filter(c -> contarRepeticiones(cartasRestantes, c) == 2).findFirst().orElse(null);
	        return segundoPar != null;
	    }
	    return false;
	}
	
	public boolean full(LinkedList<Carta> cartas) {
		LinkedList<Carta> cartasOrdenadas = ordenarCartas(cartas);
		//Verificar si hay trio
		Carta cartaTrio = cartasOrdenadas.stream().filter(c -> contarRepeticiones(cartas, c) == 3).findFirst().orElse(null);
		if (cartaTrio != null) { //Elimino las cartas del trio para verificar que las cartas del par sean distintas que las del trio
			LinkedList<Carta> cartasRestantes = new LinkedList<Carta>(cartasOrdenadas);
			cartasRestantes.removeIf(c -> {
				try {
					return c.getValor().equals(cartaTrio.getValor());
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return false;
			});
			//Verificar si hay par
			boolean hayPar = cartasRestantes.stream().anyMatch(c -> contarRepeticiones(cartasRestantes, c) == 2);
			return hayPar;
		}
		return false;
	}

}
