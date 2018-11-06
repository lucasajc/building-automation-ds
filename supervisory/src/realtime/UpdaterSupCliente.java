package realtime;

import java.util.ArrayList;
import java.util.HashMap;

import DTO.Registro;
import DTO.Tabela;
import conexao.Indice;
import conexao.Servico;

public class UpdaterSupCliente implements Runnable{
	private int porta;
	private String endereco;
	
	public UpdaterSupCliente(int porta, String endereco) {
		super();
		this.porta = porta;
		this.endereco = endereco;
	}
	
	public void run () {
		
		//Indice.mapa.put(endereco, new Tabela());
		
		while(true){
			//System.out.println(endereco+":"+porta+" running (supervisorio client)");
			
			HashMap<String,Tabela> mapa = Servico.lerSupervisorio(endereco,porta);
			if(mapa!=null){
				ArrayList<String> listaChaves = new ArrayList<String>(mapa.keySet());
				
				
				for(int i=0;i<listaChaves.size();i++){
					Indice.mapa.put(listaChaves.get(i), mapa.get(listaChaves.get(i)));
				}
			}
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}		
		}
	}
	
	public void imprimirMapeamento(){
		ArrayList<String> listaChavesTotal = new ArrayList<String>(Indice.mapa.keySet());
		
		for(int i=0;i<listaChavesTotal.size();i++){
			for(int j=0;j<Indice.mapa.get(listaChavesTotal.get(i)).getRegistros().size();j++){
				Registro registro = Indice.mapa.get(listaChavesTotal.get(i)).getRegistros().get(j);
				System.out.println("Registro em "+registro.getUrl()+": "+registro.getDescricao());
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
