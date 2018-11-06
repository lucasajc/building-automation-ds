package DTO;

import java.io.Serializable;
import java.util.ArrayList;

public class Tabela implements Serializable{

	private static final long serialVersionUID = -379086900253020092L;

	private ArrayList<Registro> registros;
	
	public Tabela() {
		super();
		this.registros = new ArrayList<Registro>();
	}

	/**
	 * @return the registros
	 */
	public ArrayList<Registro> getRegistros() {
		return registros;
	}

	/**
	 * @param registros the registros to set
	 */
	public void setRegistros(ArrayList<Registro> registros) {
		this.registros = registros;
	}
}
