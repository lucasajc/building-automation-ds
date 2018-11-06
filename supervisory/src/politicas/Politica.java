package politicas;

import DTO.Registro;

public interface Politica {
	boolean identificaGatilho(Registro registro);
	
	boolean executaPolitica(Registro registro);
}
