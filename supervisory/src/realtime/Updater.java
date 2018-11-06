package realtime;

import java.io.IOException;

import politicas.VerificaGatilho;
import DTO.Tabela;
import conexao.Indice;
import conexao.Servico;

public class Updater implements Runnable{
	
	//static int port = 80;
	//static String endereco = "http://10.0.0.200:80";
	
	//static int porta = 500;
	//static String endereco = "http://localhost";
	
	private int porta;
	private String endereco;
	
	public Updater(int porta, String endereco) {
		super();
		this.porta = porta;
		this.endereco = endereco;
	}
	
	public void run () {
		//Mapa mapa = new Mapa();
		//mapa.getTabelas().add(new Tabela());
		
		Indice.mapa.put(endereco+":"+porta, new Tabela());
		
		while(true){
			try {
				//System.out.println(endereco+":"+porta+" running");
				Indice.mapa.replace(endereco+":"+porta, Servico.lerStatus(endereco,porta));
				if(VerificaGatilho.check(Indice.mapa.get(endereco+":"+porta))==0){
					//Tabela errada = Indice.mapa.get(endereco+":"+porta);
					System.out.println("-> Ponteiro nulo em thread do endereço: "+endereco+":"+porta);
				}
				//mapa.getTabelas().set(0, Servico.lerStatus(endereco,porta));
				//VerificaGatilho.check(mapa.getTabelas().get(0));
				
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}			
			
			/*for(int i=0;i<Indice.mapa.get(endereco).getRegistros().size();i++){
				System.out.println("----- Registro "+ i+"-----");
				System.out.println(Indice.mapa.get(endereco).getRegistros().get(i).getId());
				System.out.println(Indice.mapa.get(endereco).getRegistros().get(i).getTipo());
				System.out.println(Indice.mapa.get(endereco).getRegistros().get(i).getDescricao());
				System.out.println(Indice.mapa.get(endereco).getRegistros().get(i).getArea());
				System.out.println(Indice.mapa.get(endereco).getRegistros().get(i).getSeq());
				System.out.println(Indice.mapa.get(endereco).getRegistros().get(i).getEstado());
				System.out.println(Indice.mapa.get(endereco).getRegistros().get(i).getValor());
				System.out.println(Indice.mapa.get(endereco).getRegistros().get(i).getVariavel());
				System.out.println(Indice.mapa.get(endereco).getRegistros().get(i).getPino());
			}*/
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
