package realtime;

import conexao.Indice;
import conexao.Servico;


public class UpdaterGerenciador implements Runnable{
	
	//static int port = 80;
	//static String endereco = "http://10.0.0.200:80";
	
	//static int porta = 500;
	//static String endereco = "http://localhost";
	
	private int porta;
	private String endereco;
	
	public UpdaterGerenciador(int porta, String endereco) {
		super();
		this.porta = porta;
		this.endereco = endereco;
	}
	
	public void run () {
		int port = 21000+Indice.idSupervisorio;
		System.out.println(endereco+":"+port+" running (gerenciador server)");
		Servico.atendeGerenciador(port);
		/*
		while(true){
			System.out.println(endereco+":"+porta+" running (gerenciador server)");
			Servico.atendeGerenciador(700+Indice.idSupervisorio);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}		
		}
		*/
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
