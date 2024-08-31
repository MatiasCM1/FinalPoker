package ar.edu.unlu.poker.comunes;

import ar.edu.unlu.poker.modelo.Informe;

public interface Observer {

	void update(Observado observado, Informe informe);
	
}
