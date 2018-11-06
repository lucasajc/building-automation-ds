package politicas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import DTO.Registro;
import bd.SqliteConnection;

public class PoliticaConsumoEnergetico {
	
	
	public static boolean gravarBD(Registro registro) throws ClassNotFoundException{
		
		String sql = "INSERT INTO Consumo_Energetico (Circuito_Eletrico_idCircuito_Eletrico,horario,medicao) VALUES (?,?,?)";
		try (Connection conn = SqliteConnection.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
        	
            pstmt.setInt(1, registro.getSeq());
            pstmt.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            pstmt.setDouble(3, registro.getValor());
            pstmt.executeUpdate();
            
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
		
		return false;
		
	}
}
