package DTO;

import java.io.Serializable;
import java.util.ArrayList;

public class Supervisorio implements Serializable{
	
	private static final long serialVersionUID = -7326135922941418474L;
	
	private ArrayList<Microcontrolador> microcontroladores;
	private String endereco;
	private int porta;
	
	public Supervisorio(String endereco, int porta) {
		this.microcontroladores = new ArrayList<Microcontrolador>();
		this.endereco = endereco;
		this.porta = porta;
	}
	
	public ArrayList<Microcontrolador> getMicrocontroladores() {
		return microcontroladores;
	}
	public void setMicrocontroladores(ArrayList<Microcontrolador> microcontroladores) {
		this.microcontroladores = microcontroladores;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public int getPorta() {
		return porta;
	}
	public void setPorta(int porta) {
		this.porta = porta;
	}
}
