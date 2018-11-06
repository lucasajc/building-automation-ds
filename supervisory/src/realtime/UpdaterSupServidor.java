package realtime;

import conexao.Servico;

public class UpdaterSupServidor implements Runnable{
	private int porta;
	private String endereco;
	
	public UpdaterSupServidor(int porta, String endereco) {
		super();
		this.porta = porta;
		this.endereco = endereco;
	}
	
	public void run () {
		
		//Indice.mapa.put(endereco, new Tabela());
		
		while(true){
			//System.out.println(endereco+":"+porta+" running (supervisorio server)");
			Servico.enviarMapaSupervisorio(endereco, porta);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}		
		}
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
