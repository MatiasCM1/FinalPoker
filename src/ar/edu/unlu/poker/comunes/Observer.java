package ar.edu.unlu.poker.comunes;

import ar.edu.unlu.poker.modelo.Informe;
import ar.edu.unlu.poker.modelo.Jugador;

public interface Observer {

	void update(Observado observado, Informe informe);
	//void update(Observado observado, Informe informe, Jugador jugador);
	
}
