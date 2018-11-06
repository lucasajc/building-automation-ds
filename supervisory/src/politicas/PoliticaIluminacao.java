package politicas;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import DTO.Registro;
import bd.SqliteConnection;
import conexao.Constants;
import conexao.Indice;

public class PoliticaIluminacao {

	public static boolean gravarBD(Registro registro) throws ClassNotFoundException{
		
		String sql = "INSERT INTO Estado_Variaveis (Tipo_Variavel_idTipo_Variavel,Comodo_idComodo,estado,valor,horario,descricao) VALUES (?,?,?,?,?,?)";
		try (Connection conn = SqliteConnection.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
        	boolean estado = false;
        	
        	if(registro.getEstado()==1){
        		estado = true;
        	}
        	
        	pstmt.setInt(1, 6);
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
	
	public static void enviarRequisicaoControleIluminacao(Registro registro,int acao){
		int area = registro.getArea();
		ArrayList<String> listaChaves = new ArrayList<String>(Indice.mapa.keySet());
		
		
		for(int i=0;i<listaChaves.size();i++){
			for(int j=0;j<Indice.mapa.get(listaChaves.get(i)).getRegistros().size();j++){
				char tipo = Indice.mapa.get(listaChaves.get(i)).getRegistros().get(j).getId().charAt(2);
				if((Indice.mapa.get(listaChaves.get(i)).getRegistros().get(j).getArea() == area)&&(tipo=='L')){
					mensagemRequisicao(Indice.mapa.get(listaChaves.get(i)).getRegistros().get(j),acao);
					break;
				}
			}
		}
		
	}
	
	public static void mensagemRequisicao(Registro registro,int acao){
		int port = 0;

		try {
			URL u = new URL(registro.getUrl());
			if (u.getPort() != -1)
				port = u.getPort();
			if (!(u.getProtocol().equalsIgnoreCase("http"))) {
				System.err.println("Apenas escuta HTTP.");
			}

			Socket s = new Socket(u.getHost(), port);

			OutputStream theOutput = s.getOutputStream();
			PrintWriter pw = new PrintWriter(theOutput, false);
			
			if(acao==1){
				System.out.println("Enviando: "+Constants.ICI+"/"+registro.getArea());
				pw.print(Constants.ICI+"/"+registro.getArea());
			}
			else{
				System.out.println("Enviando: "+Constants.FCI+"/"+registro.getArea());
				pw.print(Constants.FCI+"/"+registro.getArea());
			}
			pw.flush();
			
			s.close();

		} catch (MalformedURLException ex) {
			System.err.println(registro.getUrl() + " nao é uma URL válida");
		} catch (IOException ex) {
			//System.err.println(ex);
			System.out.println("-> Erro de conexão em: lerStatus("+registro.getUrl()+")");
		} catch (NullPointerException ex) {
			System.err.println(ex);
		}
	}
		
}
