package DTO;

import java.io.Serializable;

public class Gerenciador implements Serializable{
	
	private static final long serialVersionUID = 2898752896706228938L;
	
	private String endereco;
	private int porta;
	
	public Gerenciador(String endereco, int porta) {
		this.endereco = endereco;
		this.porta = porta;
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
