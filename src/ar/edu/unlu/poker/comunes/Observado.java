package ar.edu.unlu.poker.comunes;

import ar.edu.unlu.poker.modelo.Informe;
import ar.edu.unlu.poker.modelo.Jugador;

public interface Observado {

	void agregarObservador(Observer o);
    void notificarObservers(Informe informe);
    //void notificarObservers(Informe informe, Jugador jugador);
	
}
