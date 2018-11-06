package conexao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import DTO.Gerenciador;
import DTO.Microcontrolador;
import DTO.Registro;
import DTO.Supervisorio;
import DTO.Tabela;

public class Configuracao {

	public static ArrayList<Microcontrolador> mapearMicrocontroladores() throws IOException{
		
		ArrayList<Microcontrolador> listaMc = new ArrayList<Microcontrolador>();
		
		int contadorPontoVirgula = 0;
		boolean lerEndereco = true;
		
		//PathBuilder path = new PathBuilder("microcontroladores");
		//BufferedReader br = new BufferedReader(new FileReader(path.getPath().substring(5)));
		BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Lucas\\simuladores\\Supervisorio"+Indice.idSupervisorio+"\\config\\microcontroladores"));
		//BufferedReader br = new BufferedReader(new FileReader("config/microcontroladores"));
		StringBuffer endereco = new StringBuffer();
		StringBuffer porta = new StringBuffer();
		char caracter;
		while(br.ready()){
			caracter = (char)br.read();
			
			if(caracter=='\n'){
				listaMc.add(new Microcontrolador(endereco.toString(), Integer.parseInt(porta.toString())));
				endereco = new StringBuffer();
				porta = new StringBuffer();
				lerEndereco = true;
				contadorPontoVirgula=0;
				caracter = (char)br.read();
			}
			else if(caracter==':'){
				contadorPontoVirgula++;
				
			}
			if((caracter==':')&&(contadorPontoVirgula==2)){
				lerEndereco = false;
				caracter = (char)br.read();
			}
			if(lerEndereco==true){
				endereco.append(caracter);
			}
			else if((lerEndereco==false)&&(caracter!='\r')){
				porta.append(caracter);
			}
			
		}
		listaMc.add(new Microcontrolador(endereco.toString(), Integer.parseInt(porta.toString())));
		br.close();
		return listaMc;
	}
	
	public static ArrayList<Supervisorio> mapearSupervisorios() throws IOException{
			
			ArrayList<Supervisorio> listaSup = new ArrayList<Supervisorio>();
			
			int contadorPontoVirgula = 0;
			boolean lerEndereco = true;
			
			//PathBuilder path = new PathBuilder("supervisorios");
			//BufferedReader br = new BufferedReader(new FileReader(path.getPath().substring(5)));
			//BufferedReader br = new BufferedReader(new FileReader("config/supervisorios"));
			BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Lucas\\simuladores\\Supervisorio"+Indice.idSupervisorio+"\\config\\supervisorios"));
			StringBuffer endereco = new StringBuffer();
			StringBuffer porta = new StringBuffer();
			char caracter;
			while(br.ready()){
				caracter = (char)br.read();
				
				if(caracter=='\n'){
					listaSup.add(new Supervisorio(endereco.toString(), Integer.parseInt(porta.toString())));
					endereco = new StringBuffer();
					porta = new StringBuffer();
					lerEndereco = true;
					contadorPontoVirgula=0;
					caracter = (char)br.read();
				}
				else if(caracter==':'){
					contadorPontoVirgula++;
					
				}
				if((caracter==':')&&(contadorPontoVirgula==2)){
					lerEndereco = false;
					caracter = (char)br.read();
				}
				if(lerEndereco==true){
					endereco.append(caracter);
				}
				else if((lerEndereco==false)&&(caracter!='\r')){
					porta.append(caracter);
				}
				
			}
			listaSup.add(new Supervisorio(endereco.toString(), Integer.parseInt(porta.toString())));
			br.close();
			return listaSup;
		}
	
	public static Gerenciador mapearGerenciador() throws IOException{
		Gerenciador gerenciador;
		int contadorPontoVirgula = 0;
		boolean lerEndereco = true;
		
		//PathBuilder path = new PathBuilder("supervisorios");
		//BufferedReader br = new BufferedReader(new FileReader(path.getPath().substring(5)));
		//BufferedReader br = new BufferedReader(new FileReader("config/supervisorios"));
		BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Lucas\\simuladores\\Supervisorio"+Indice.idSupervisorio+"\\config\\gerenciador"));
		StringBuffer endereco = new StringBuffer();
		StringBuffer porta = new StringBuffer();
		char caracter;
		while(br.ready()){
			caracter = (char)br.read();
			
			if(caracter=='\n'){
				gerenciador = new Gerenciador(endereco.toString(), Integer.parseInt(porta.toString()));
				endereco = new StringBuffer();
				porta = new StringBuffer();
				lerEndereco = true;
				contadorPontoVirgula=0;
				caracter = (char)br.read();
			}
			else if(caracter==':'){
				contadorPontoVirgula++;
				
			}
			if((caracter==':')&&(contadorPontoVirgula==2)){
				lerEndereco = false;
				caracter = (char)br.read();
			}
			if(lerEndereco==true){
				endereco.append(caracter);
			}
			else if((lerEndereco==false)&&(caracter!='\r')){
				porta.append(caracter);
			}
			
		}
		gerenciador = new Gerenciador(endereco.toString(), Integer.parseInt(porta.toString()));
		br.close();
		return gerenciador;
	}
	
		public static boolean verificaNulidadeRegistro(Registro registro){
			if(registro==null){
				return false;
			}
			return true;
		}
		
		public static boolean verificaNulidadeTabela(Tabela tabela){
			if(tabela==null){
				return false;
			}
			return true;
		}
	
	
}
