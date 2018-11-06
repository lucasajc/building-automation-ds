package DTO;

import java.io.Serializable;
import java.util.ArrayList;

public class Mapa implements Serializable{

	private static final long serialVersionUID = 1582909483262958943L;
	
	private ArrayList<Tabela> tabelas;

	public Mapa() {
		this.tabelas = new ArrayList<Tabela>();
	}

	/**
	 * @return the tabelas
	 */
	public ArrayList<Tabela> getTabelas() {
		return tabelas;
	}

	/**
	 * @param tabelas the tabelas to set
	 */
	public void setTabelas(ArrayList<Tabela> tabelas) {
		this.tabelas = tabelas;
	}

}
