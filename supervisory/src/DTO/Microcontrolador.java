package DTO;

import java.io.Serializable;

public class Microcontrolador implements Serializable{
	
	private static final long serialVersionUID = 8344677157600174318L;
	private Mapa mapa;
	private String endereco;
	private int porta;
	
	public Microcontrolador(String endereco, int porta) {
		this.mapa = new Mapa();
		this.endereco = endereco;
		this.porta = porta;
	}
	
	public Mapa getMapa() {
		return mapa;
	}
	public void setMapa(Mapa mapa) {
		this.mapa = mapa;
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
