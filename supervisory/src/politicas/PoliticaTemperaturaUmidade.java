package politicas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import DTO.Registro;
import bd.SqliteConnection;

public class PoliticaTemperaturaUmidade {

	public static boolean gravarBDTemperatura(Registro registro) throws ClassNotFoundException{
		
		String sql = "INSERT INTO Estado_Variaveis (Tipo_Variavel_idTipo_Variavel,Comodo_idComodo,estado,valor,horario,descricao) VALUES (?,?,?,?,?,?)";
		try (Connection conn = SqliteConnection.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
        	boolean estado = false;
        	
        	if(registro.getEstado()==1){
        		estado = true;
        	}
        	
        	pstmt.setInt(1, 1);
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
	
public static boolean gravarBDUmidade(Registro registro) throws ClassNotFoundException{
		
		String sql = "INSERT INTO Estado_Variaveis (Tipo_Variavel_idTipo_Variavel,Comodo_idComodo,estado,valor,horario,descricao) VALUES (?,?,?,?,?,?)";
		try (Connection conn = SqliteConnection.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
        	boolean estado = false;
        	
        	if(registro.getEstado()==1){
        		estado = true;
        	}
        	
        	pstmt.setInt(1, 2);
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
	
}
