package microcontrolador;

import java.io.IOException;
import java.net.UnknownHostException;


public class Main {

	public static void main(String[] args) throws UnknownHostException, IOException{
		Tabela tabela = new Tabela();
		
		tabela = Simulador.popularTabela(tabela);
		
		ClienteArduino.conexao(tabela);

	}

}
