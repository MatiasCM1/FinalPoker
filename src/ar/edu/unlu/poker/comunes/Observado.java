package ar.edu.unlu.poker.comunes;

import ar.edu.unlu.poker.modelo.Informe;

public interface Observado {

	void agregarObservador(Observer o);
    void notificarObservers(Informe informe);
	
}
