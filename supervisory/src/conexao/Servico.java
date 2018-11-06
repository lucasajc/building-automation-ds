package conexao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.HashMap;

import realtime.UpdaterAlarmeInvasao;
import DTO.Registro;
import DTO.Tabela;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class Servico {

	//static int port = 80;
	//static String endereco = "http://10.0.0.200:80";
	static int port = 500;
	static String endereco = "localhost";
	
	public static Tabela lerStatus(String endereco, int port) throws IOException {
		
		Registro registro = new Registro();
		Tabela tabela = new Tabela();
		
		char c;
		int contadorLinha = 0;
		StringBuffer sb = new StringBuffer();
		String buffer = null;

		try {
			URL u = new URL(endereco);
			if (u.getPort() != -1)
				port = u.getPort();
			if (!(u.getProtocol().equalsIgnoreCase("http"))) {
				System.err.println("Apenas escuta HTTP.");
			}

			Socket s = new Socket(u.getHost(), port);

			OutputStream theOutput = s.getOutputStream();
			PrintWriter pw = new PrintWriter(theOutput, false);

			pw.print(Constants.STATS);
			pw.flush();

			InputStream in = s.getInputStream();
			InputStreamReader isr = new InputStreamReader(in);
			BufferedReader br = new BufferedReader(isr);
			
			StringBuffer tudo = new StringBuffer();
			
			while ((c = (char) br.read()) != (char) -1) {
				
				if(c == '\n'){
					
					buffer = sb.toString().replaceAll("\r", "");
					
					switch(contadorLinha){
					case 0:
						registro.setId(buffer);
						break;
					case 1:
						registro.setTipo(buffer);
						break;
					case 2:
						registro.setDescricao(buffer);
						break;
					case 3:
						registro.setArea(Integer.parseInt(buffer));
						break;
					case 4:
						registro.setSeq(Integer.parseInt(buffer));
						break;
					case 5:
						registro.setEstado(Integer.parseInt(buffer));
						break;
					case 6:
						registro.setValor(Double.parseDouble(buffer));
						break;	
					case 7:
						registro.setVariavel(buffer.charAt(0));
						break;
					case 8:
						registro.setPino(Integer.parseInt(buffer));
						break;
					}
					
					registro.setUrl(endereco+":"+port);
					
					sb = new StringBuffer();
					
					contadorLinha++;
					
					if(contadorLinha==9){
						contadorLinha = 0;
						tabela.getRegistros().add(registro);
						registro = new Registro();
					}
				}
				else{
					sb.append(c);
					tudo.append(c);
				}
				
			}
			s.close();
			
			tudo.toString();
			return tabela;

		} catch (MalformedURLException ex) {
			System.err.println(endereco + " nao é uma URL válida");
			return new Tabela();
		} catch (IOException ex) {
			//System.err.println(ex);
			System.out.println("-> Erro de conexão em: lerStatus("+endereco+","+port+")");
			return new Tabela();
		} catch (NullPointerException ex) {
			System.err.println(ex);
			return new Tabela();
		}
		

	}
	
	public static HashMap<String, Tabela> lerSupervisorio(String endereco, int port){
		ObjectMapper mapper = new ObjectMapper();
		TypeReference<HashMap<String,Tabela>> typeRef = new TypeReference<HashMap<String,Tabela>>() {};
		HashMap<String, Tabela> mapa = new HashMap<String, Tabela>();
		char c;
		try {
			ServerSocket servidor = new ServerSocket(port);
			Socket s = servidor.accept();
		    //System.out.println("Nova conexão com o cliente " +   
		   //   s.getInetAddress().getHostAddress()
		   // );
		    
		    InputStream in = s.getInputStream();
			InputStreamReader isr = new InputStreamReader(in);
			BufferedReader br = new BufferedReader(isr);
				
			StringBuffer tudo = new StringBuffer();
			
				while ((c = (char) br.read()) != (char) -1) {
					tudo.append(c);
					if((tudo.toString().equalsIgnoreCase(Constants.ALRM1))||(tudo.toString().equalsIgnoreCase(Constants.ALRM2))){
						s.close();
						servidor.close();
						System.out.println("*Alarme ("+tudo.toString()+") recebido em: "+endereco+":"+port);
						Thread threadAlarme = new Thread(new UpdaterAlarmeInvasao(port,endereco));
						threadAlarme.start();
						return null;
					}
				}
			s.close();
			servidor.close();	
			// Convert JSON string to Object
			String jsonInString = tudo.toString();
			//System.out.println(tudo.toString());
			mapa = mapper.readValue(jsonInString, typeRef);
			//System.out.println(mapa);

			//Pretty print
			//String prettyStaff1 = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mapa);
			//System.out.println(prettyStaff1);	
				
			
		} catch (IOException e) {
			//e.printStackTrace();
			System.out.println("-> Erro de conexão em: lerSupervisorio("+endereco+","+port+")");
		}
		
		return mapa;
	}
	
	public static void enviarMapaSupervisorio(String endereco, int port){
		ObjectMapper mapper = new ObjectMapper();
		String stream;
		try {

			// Convert object to JSON string
			stream = mapper.writeValueAsString(Indice.mapa);
			//System.out.println(stream);

			// Convert object to JSON string and pretty print
			stream = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(Indice.mapa);
			//System.out.println(stream);
			
			URL u = new URL(endereco+":"+port);
			if (u.getPort() != -1)
				port = u.getPort();
			if (!(u.getProtocol().equalsIgnoreCase("http"))) {
				System.err.println("Apenas escuta HTTP.");
			}

			Socket s = new Socket(u.getHost(), port);

			OutputStream theOutput = s.getOutputStream();
			PrintWriter pw = new PrintWriter(theOutput, false);

			pw.print(stream);
			pw.flush();
			s.close();

		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			//e.printStackTrace();
			System.out.println("-> Erro de conexão em: enviarMapaSupervisorio("+endereco+","+port+")");
		}
	}
	
	static class handler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
        	ObjectMapper mapper = new ObjectMapper();
			String stream = mapper.writeValueAsString(Indice.mapa);
			stream = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(Indice.mapa);
			
            String response = stream;
            t.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
	
	public static void atendeGerenciador(int port){
		
		HttpServer server;
		try {
			server = HttpServer.create(new InetSocketAddress(port), 0);
		
        server.createContext("/stats", new handler());
        server.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Double lerTemperatura() throws IOException {

		try {
			URL u = new URL(endereco);
			if (u.getPort() != -1)
				port = u.getPort();
			if (!(u.getProtocol().equalsIgnoreCase("http"))) {
				System.err.println("Apenas escuta HTTP.");
			}

			Double temperatura = 0.0;
			StringBuffer sb = new StringBuffer();
			
			Socket s = new Socket(u.getHost(), port);
			OutputStream theOutput = s.getOutputStream();
			PrintWriter pw = new PrintWriter(theOutput, false);

			pw.print(Constants.TMPUM);
			pw.flush();

			InputStream in = s.getInputStream();
			InputStreamReader isr = new InputStreamReader(in);
			BufferedReader br = new BufferedReader(isr);

			char c;
			int contadorLinha = 0;
			while ((c = (char) br.read()) != (char) -1) {
				sb.append(c);
				if (c == '\r') {
					if (contadorLinha == 0) {
						temperatura = Double.valueOf(sb.toString());
						sb = new StringBuffer();
					} else {
						sb = new StringBuffer();
					}
					contadorLinha++;
				}
			}

			s.close();
			return temperatura;

		} catch (MalformedURLException ex) {
			System.err.println(endereco + " nao ï¿½ uma URL vï¿½lida");
			return 0.0;
		} catch (IOException ex) {
			System.err.println(ex);
			return 0.0;
		}

	}
	
	public static Double lerUmidade() throws IOException {

		try {
			URL u = new URL(endereco);
			if (u.getPort() != -1)
				port = u.getPort();
			if (!(u.getProtocol().equalsIgnoreCase("http"))) {
				System.err.println("Apenas escuta HTTP.");
			}

			Socket s = new Socket(u.getHost(), port);

			Double umidade = 0.0;
			StringBuffer sb = new StringBuffer();

			OutputStream theOutput = s.getOutputStream();
			PrintWriter pw = new PrintWriter(theOutput, false);

			pw.print(Constants.TMPUM);
			pw.flush();

			InputStream in = s.getInputStream();
			InputStreamReader isr = new InputStreamReader(in);
			BufferedReader br = new BufferedReader(isr);

			char c;
			int contadorLinha = 0;
			while ((c = (char) br.read()) != (char) -1) {
				sb.append(c);
				if (c == '\r') {
					if (contadorLinha == 0) {
						sb = new StringBuffer();
					} else {
						umidade = Double.valueOf(sb.toString());
						sb = new StringBuffer();
					}
					contadorLinha++;
				}
			}

			s.close();
			return umidade;

		} catch (MalformedURLException ex) {
			System.err.println(endereco + " nao ï¿½ uma URL vï¿½lida");
			return 0.0;
		} catch (IOException ex) {
			System.err.println(ex);
			return 0.0;
		}

	}

	public static boolean acenderLuzes() throws IOException {

		try {
			URL u = new URL(endereco);
			if (u.getPort() != -1)
				port = u.getPort();
			if (!(u.getProtocol().equalsIgnoreCase("http"))) {
				System.err.println("Apenas escuta HTTP.");
			}

			Socket s = new Socket(u.getHost(), port);

			StringBuffer sb = new StringBuffer();

			OutputStream theOutput = s.getOutputStream();
			PrintWriter pw = new PrintWriter(theOutput, false);

			pw.print(Constants.ALUZ1);
			pw.flush();

			InputStream in = s.getInputStream();
			InputStreamReader isr = new InputStreamReader(in);
			BufferedReader br = new BufferedReader(isr);

			char c;
			while ((c = (char) br.read()) != (char) -1) {
				sb.append(c);
			}

			System.out.println(sb.toString());

			s.close();
			
			return true;

		} catch (MalformedURLException ex) {
			System.err.println(endereco + " nao ï¿½ uma URL vï¿½lida");
			return false;
		} catch (IOException ex) {
			System.err.println(ex);
			return false;
		}

	}

	public static boolean desligarLuzes() throws IOException {

		try {
			URL u = new URL(endereco);
			if (u.getPort() != -1)
				port = u.getPort();
			if (!(u.getProtocol().equalsIgnoreCase("http"))) {
				System.err.println("Apenas escuta HTTP.");
			}

			Socket s = new Socket(u.getHost(), port);

			StringBuffer sb = new StringBuffer();

			OutputStream theOutput = s.getOutputStream();
			PrintWriter pw = new PrintWriter(theOutput, false);

			pw.print(Constants.DLUZ1);
			pw.flush();

			InputStream in = s.getInputStream();
			InputStreamReader isr = new InputStreamReader(in);
			BufferedReader br = new BufferedReader(isr);

			char c;
			while ((c = (char) br.read()) != (char) -1) {
				sb.append(c);
			}

			System.out.println(sb.toString());

			s.close();
			
			return true;

		} catch (MalformedURLException ex) {
			System.err.println(endereco + " nao ï¿½ uma URL vï¿½lida");
			return false;
		} catch (IOException ex) {
			System.err.println(ex);
			return false;
		}
		
		
	}

}
