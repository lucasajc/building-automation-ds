package politicas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import main.Camadas;
import DTO.CamadaSupervisorio;
import DTO.Registro;
import DTO.RegraSeguranca;
import bd.SqliteConnection;
import conexao.Constants;

public class PoliticaInvasao {

	public static boolean gravarBD(Registro registro) throws ClassNotFoundException{
			
			String sql = "INSERT INTO Estado_Variaveis (Tipo_Variavel_idTipo_Variavel,Comodo_idComodo,estado,valor,horario,descricao) VALUES (?,?,?,?,?,?)";
			try (Connection conn = SqliteConnection.connect();
	                PreparedStatement pstmt = conn.prepareStatement(sql)) {
				
	        	boolean estado = false;
	        	
	        	if(registro.getEstado()==1){
	        		estado = true;
	        	}
	        	
	        	pstmt.setInt(1, 3);
	            pstmt.setInt(2, registro.getArea());
	            pstmt.setBoolean(3, estado);
	            pstmt.setDouble(4, registro.getValor());
	            pstmt.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
	            pstmt.setString(6, registro.getDescricao());
	            pstmt.executeUpdate();
	            
	            return true;
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
			
			return false;
			
	}
	
	 public static RegraSeguranca obterRegra(int comodo) throws ClassNotFoundException{
	        String sql = "SELECT * FROM Regras_Seguranca where Comodo_idComodo ="+comodo;
	        
	        
	        RegraSeguranca regra = new RegraSeguranca();
	        try (Connection conn = SqliteConnection.connect();
	             Statement stmt  = conn.createStatement();
	             ResultSet rs    = stmt.executeQuery(sql)){
	            while (rs.next()) {
	            	regra.setIdRegraSeguranca(rs.getInt("idRegras_Seguranca"));
	            	regra.setIdComodo(rs.getInt("Comodo_idComodo"));
	            	regra.setHoraInicio(Time.valueOf(rs.getString("horaInicio")));
	            	regra.setHoraFim(Time.valueOf(rs.getString("horaFim")));
	            }
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
			return regra;
	    }
	 
	 public static boolean verificaHorario(RegraSeguranca regra){
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			Date hora = Calendar.getInstance().getTime();
			String dataFormatada = sdf.format(hora);
			Time tempoAtual = Time.valueOf(dataFormatada);
			if(regra.getHoraInicio().after(regra.getHoraFim())){
				if(tempoAtual.after(regra.getHoraInicio())){
					return true;
				}
			}
			else{
				if((tempoAtual.after(regra.getHoraInicio()))&&(tempoAtual.before(regra.getHoraInicio()))){
					return true;
				}
			}
			
			return true;
	 }
	 
	 public static void broadcastAlarme(){
		 for(int i=0;i<Camadas.camadaMicro.getMicrocontroladores().size();i++){
				enviarAlarme(Camadas.camadaMicro.getMicrocontroladores().get(i).getEndereco(),Camadas.camadaMicro.getMicrocontroladores().get(i).getPorta());
		 }
		 for(int i=0;i<Camadas.camadaSup.getSupervisorios().size();i++){
				//enviarAlarme(Camadas.camadaSup.getSupervisorios().get(i).getEndereco(),Camadas.camadaMicro.getMicrocontroladores().get(i).getPorta());
			 enviarAlarme(Camadas.camadaSup.getSupervisorios().get(i).getEndereco(),Camadas.camadaSup.getSupervisorios().get(i).getPorta());
		 }
	 }
	 
	 public static void multicastAlarme(String enderecoRemetente){
		 CamadaSupervisorio novaCamadaSup = new CamadaSupervisorio();
		 
		 for(int i=0;i<Camadas.camadaSup.getSupervisorios().size();i++){
			 if(!Camadas.camadaSup.getSupervisorios().get(i).getEndereco().equals(enderecoRemetente)){
				 novaCamadaSup.getSupervisorios().add(Camadas.camadaSup.getSupervisorios().get(i));
			 }
		 }
		 
		 for(int i=0;i<Camadas.camadaMicro.getMicrocontroladores().size();i++){
				enviarAlarme(Camadas.camadaMicro.getMicrocontroladores().get(i).getEndereco(),Camadas.camadaMicro.getMicrocontroladores().get(i).getPorta());
		 }
		 for(int i=0;i<novaCamadaSup.getSupervisorios().size();i++){
				//enviarAlarme(novaCamadaSup.getSupervisorios().get(i).getEndereco(),Camadas.camadaMicro.getMicrocontroladores().get(i).getPorta());
			 enviarAlarme(novaCamadaSup.getSupervisorios().get(i).getEndereco(),novaCamadaSup.getSupervisorios().get(i).getPorta());
		 }
	 }
	 
	 public static void enviarAlarme(String endereco, int port){
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

				pw.print(Constants.ALRM1);
				pw.flush();
				s.close();
				
				System.out.println("Alarme ("+Constants.ALRM1+") enviado para: "+endereco+":"+port);
				
				

			} catch (MalformedURLException ex) {
				System.err.println(endereco + " nao é uma URL válida");
			} catch (IOException e) {
				System.out.println("-> Erro de conexão em: enviarAlarme("+endereco+","+port+")");
			}
	 }
	 
	 public static void lerAlarmeSupervisorio(String endereco, int port){
			char c;
			try {
				ServerSocket servidor = new ServerSocket(port);
				Socket s = servidor.accept();
			    
			    InputStream in = s.getInputStream();
				InputStreamReader isr = new InputStreamReader(in);
				BufferedReader br = new BufferedReader(isr);
					
				StringBuffer tudo = new StringBuffer();
				
					while ((c = (char) br.read()) != (char) -1) {
						tudo.append(c);
						if(tudo.toString().equalsIgnoreCase(Constants.ALRM1)){
							s.close();
							servidor.close();	
							System.out.println(tudo.toString() +": recebido de: "+endereco+":"+port);
							multicastAlarme(endereco);
						    break;
						}
					}
				s.close();
				servidor.close();	
			} catch (IOException e) {
				System.out.println("-> Erro de conexão em: lerAlarmeSupervisorio("+endereco+","+port+")");
			}
		}
}
