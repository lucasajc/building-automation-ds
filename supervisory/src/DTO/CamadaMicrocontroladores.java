package DTO;

import java.io.Serializable;
import java.util.ArrayList;

public class CamadaMicrocontroladores implements Serializable{
	
	private static final long serialVersionUID = -8151272984559591931L;
	
	private ArrayList<Microcontrolador> microcontroladores;

	public CamadaMicrocontroladores() {
		this.setMicrocontroladores(new ArrayList<Microcontrolador>());
	}

	/**
	 * @return the microcontroladores
	 */
	public ArrayList<Microcontrolador> getMicrocontroladores() {
		return microcontroladores;
	}

	/**
	 * @param microcontroladores the microcontroladores to set
	 */
	public void setMicrocontroladores(ArrayList<Microcontrolador> microcontroladores) {
		this.microcontroladores = microcontroladores;
	}

	
	
	
}
