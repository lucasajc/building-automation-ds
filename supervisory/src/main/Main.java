package main;

import java.io.IOException;
import java.util.ArrayList;

import realtime.Timer;
import realtime.Updater;
import realtime.UpdaterGerenciador;
import realtime.UpdaterSupCliente;
import realtime.UpdaterSupServidor;
import DTO.CamadaMicrocontroladores;
import DTO.CamadaSupervisorio;
import conexao.Configuracao;
import conexao.Indice;

public class Main {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		
		int porta = 600+Indice.idSupervisorio;
		
		ArrayList<Updater> listaUpdater = new ArrayList<Updater>();
		ArrayList<Thread> listaThreadUpdater = new ArrayList<Thread>();
		
		ArrayList<UpdaterSupServidor> listaUpdaterSupServidor = new ArrayList<UpdaterSupServidor>();
		ArrayList<Thread> listaThreadUpdaterSupServidor = new ArrayList<Thread>();
		
		ArrayList<UpdaterSupCliente> listaUpdaterSupCliente = new ArrayList<UpdaterSupCliente>();
		ArrayList<Thread> listaThreadUpdaterSupCliente = new ArrayList<Thread>();
		
		Camadas.camadaMicro = new CamadaMicrocontroladores();
		Camadas.camadaSup = new CamadaSupervisorio();
		
		Camadas.camadaMicro.setMicrocontroladores(Configuracao.mapearMicrocontroladores());
		Camadas.camadaSup.setSupervisorios(Configuracao.mapearSupervisorios());
		
		Camadas.camadaGerenciador = Configuracao.mapearGerenciador();
		
		//cria uma thread para contar o tempo de amostragem
		Thread timer = new Thread(new Timer());
		timer.start();
		
		//Cria uma thread/updater para cada microcontrolador conectado
		for(int i=0;i<Camadas.camadaMicro.getMicrocontroladores().size();i++){
			listaUpdater.add(new Updater(Camadas.camadaMicro.getMicrocontroladores().get(i).getPorta(),Camadas.camadaMicro.getMicrocontroladores().get(i).getEndereco()));
			listaThreadUpdater.add(new Thread(listaUpdater.get(i)));
			listaThreadUpdater.get(i).start();
		}
		
		//Cria uma thread/updater para cada supervisorio conectado
		for(int i=0;i<Camadas.camadaSup.getSupervisorios().size();i++){
			//listaUpdaterSupServidor.add(new UpdaterSupServidor(porta,Indice.endereco));
			listaUpdaterSupServidor.add(new UpdaterSupServidor(Camadas.camadaSup.getSupervisorios().get(i).getPorta(),Camadas.camadaSup.getSupervisorios().get(i).getEndereco()));
			listaThreadUpdaterSupServidor.add(new Thread(listaUpdaterSupServidor.get(i)));
			listaThreadUpdaterSupServidor.get(i).start();
		}
		
		//Cria uma thread/updater para cada supervisorio conectado
		for(int i=0;i<Camadas.camadaSup.getSupervisorios().size();i++){
			//listaUpdaterSupCliente.add(new UpdaterSupCliente(Camadas.camadaSup.getSupervisorios().get(i).getPorta(),Camadas.camadaSup.getSupervisorios().get(i).getEndereco()));
			listaUpdaterSupCliente.add(new UpdaterSupCliente(porta,Indice.endereco));
			listaThreadUpdaterSupCliente.add(new Thread(listaUpdaterSupCliente.get(i)));
			listaThreadUpdaterSupCliente.get(i).start();
		}
		
		//cria uma thread para conexão com o gerenciador
		Thread updaterGerenciador = new Thread(new UpdaterGerenciador(Camadas.camadaGerenciador.getPorta(),Camadas.camadaGerenciador.getEndereco()));
		updaterGerenciador.start();
	}

}
