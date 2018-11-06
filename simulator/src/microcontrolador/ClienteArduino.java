package microcontrolador;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClienteArduino {
	
	public static int port = 501;
	
	public static void conexao(Tabela tabela) throws UnknownHostException, IOException{
		 
		while(true){
			tabela = Simulador.randomizarTabela(tabela);
			ServerSocket servidor = new ServerSocket(port);
		     
			char c;
			 
		    Socket s = servidor.accept();
		    System.out.println("Nova conexão com o cliente " +   
		      s.getInetAddress().getHostAddress()
		    );
		     
		     
		  
		    OutputStream theOutput = s.getOutputStream();
			PrintWriter pw = new PrintWriter(theOutput, false);
	
		    InputStream in = s.getInputStream();
			InputStreamReader isr = new InputStreamReader(in);
			BufferedReader br = new BufferedReader(isr);
				
			StringBuffer tudo = new StringBuffer();
				
			while ((c = (char) br.read()) != (char) -1) {
				tudo.append(c);
				if(tudo.toString().equalsIgnoreCase("stats")){
					System.out.println(tudo.toString());
					imprimirTabela(tabela,pw);
				    break;
				}
				if(tudo.toString().equalsIgnoreCase("alrm1")){
					System.out.println(tudo.toString());
					//imprimirTabela(tabela,pw);
				    break;
				}
				if(tudo.toString().equalsIgnoreCase("alrm2")){
					System.out.println(tudo.toString());
					//imprimirTabela(tabela,pw);
				    break;
				}
				if(tudo.toString().equalsIgnoreCase("chg11")){
					System.out.println(tudo.toString());
					for(int i=0;i<tabela.getRegistros().size();i++){
						if(tabela.getRegistros().get(i).getArea()==Integer.getInteger(String.valueOf(tudo.charAt(3)))){
							if(tabela.getRegistros().get(i).getSeq()==Integer.getInteger(String.valueOf(tudo.charAt(4)))){
								tabela.getRegistros().get(i).setEstado(1);
							}
						}
					}
					
					imprimirTabela(tabela,pw);
				    break;
				}
			}
			s.close();
		    servidor.close();
		     
		}
	}
	
	public static void imprimirTabela(Tabela tabela,PrintWriter pw){
		for(int i=0;i<tabela.getRegistros().size();i++){
			pw.println(tabela.getRegistros().get(i).getId());
			pw.println(tabela.getRegistros().get(i).getTipo());
			pw.println(tabela.getRegistros().get(i).getDescricao());
			pw.println(tabela.getRegistros().get(i).getArea());
			pw.println(tabela.getRegistros().get(i).getSeq());
			pw.println(tabela.getRegistros().get(i).getEstado());
			pw.println(tabela.getRegistros().get(i).getValor());
			pw.println(tabela.getRegistros().get(i).getVariavel());
			pw.println(tabela.getRegistros().get(i).getPino());
			pw.flush();
		}
	}
}
