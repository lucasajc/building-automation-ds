package realtime;

import java.io.IOException;

import politicas.PoliticaInvasao;
import politicas.VerificaGatilho;
import DTO.Tabela;
import conexao.Indice;
import conexao.Servico;

public class UpdaterAlarme implements Runnable{
	
	//static int port = 80;
	//static String endereco = "http://10.0.0.200:80";
	
	//static int porta = 500;
	//static String endereco = "http://localhost";
	
	private int porta;
	private String endereco;
	
	public UpdaterAlarme(int porta, String endereco) {
		super();
		this.porta = porta;
		this.endereco = endereco;
	}
	
	public void run () {
		
		PoliticaInvasao.multicastAlarme(endereco);
		
	}

	public int getPorta() {
		return porta;
	}

	public void setPorta(int porta) {
		this.porta = porta;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	
	
}
