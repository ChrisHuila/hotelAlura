package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import conexionSQL.DbConnection;
import model.Reserva;

public class ReservaDAO {
	private Connection con;
	
	public ReservaDAO(Connection connection) {
		this.con = connection;
	}
	
	public void guardar(Reserva reserva) {
		String SQL ="INSERT INTO reservas(fechaEntrada,fechaSalida,valor,formaPago ) VALUES (?,?,?,?)";
		
		try {
			PreparedStatement pst=con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
			
			pst.setDate(1, reserva.getFechaEntrada());
			pst.setDate(2, reserva.getFechaSalida());
			
			pst.setString(3, reserva.getValor());
			pst.setString(4, reserva.getFormaPago());
			
			pst.execute();
			
			ResultSet rs=pst.getGeneratedKeys();
			if(rs.next()) {
				reserva.setId(rs.getInt(1));				
			}		
			
			pst.close();
			
		} catch (SQLException e) {   
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{	    

			desconectarBd(con);	           
	    }
	
	}
	
	public void mostrarReserva(JTable tablaR,  DefaultTableModel modeloR, String id) {
				
		
		try {

			if(con.isClosed()) {
				 con = nuevaConexion(con);				 
			}
			
			String regristros[] = new String[5];

			String SQL = "SELECT * FROM reservas WHERE id = ?"; 
			PreparedStatement consulta = con.prepareStatement(SQL);
			consulta.setInt(1, Integer.parseInt(id));
			
			ResultSet rs= consulta.executeQuery();
			
			if(rs.next()) {
				regristros[0]= rs.getString("id");
				regristros[1]= rs.getString("fechaEntrada");
				regristros[2]= rs.getString("fechaSalida");
				regristros[3]= rs.getString("valor");
				regristros[4]= rs.getString("formaPago");	
				
				modeloR.addRow(regristros);
				tablaR.setModel(modeloR);
			}
	 
			rs.close();
			consulta.close();
	
		} catch (SQLException e) {
			System.out.println("Hay un error" + e);
		}finally{	    

			desconectarBd(con);	           
	    }
	
		
	}
	
	public void mostrarDatosApellido(JTable tablaR, DefaultTableModel modeloR, String apellido) {
		
//		Apartir del apellido consulta id reserva
		try {
			
			if(con.isClosed()) {
				 con = nuevaConexion(con);				 
			}
			
//		Para almacenar datos reserva
		String regristros[] = new String[6];
		
//      Almacenar id Reserva buscado por el apellido del huesped
		String registrosh[] = new String[8];
		
		
		String SQLhuesped = "SELECT * FROM huespedes WHERE apellido = ?"; 	
		PreparedStatement consultaH = con.prepareStatement(SQLhuesped);

		consultaH.setString(1, apellido);
		
		ResultSet rsH = consultaH.executeQuery();
		

		String SQL = "SELECT * FROM reservas WHERE id = ?"; 
		PreparedStatement consulta = con.prepareStatement(SQL);
		
//		Toma el id de la tabla huesped para poder mostrar la tabla de reserva
		if(rsH.next()) {
			registrosh[5]= rsH.getString("idReserva");
			consulta.setInt(1, Integer.parseInt(registrosh[5]));	

		}
		
//		Para tabla reserva

		ResultSet rs= consulta.executeQuery();
		
		if(rs.next()) {
			regristros[0]= rs.getString("id");
			regristros[1]= rs.getString("fechaEntrada");
			regristros[2]= rs.getString("fechaSalida");
			regristros[3]= rs.getString("valor");
			regristros[4]= rs.getString("formaPago");

			modeloR.addRow(regristros);
			tablaR.setModel(modeloR);
		}
		
		rs.close();
		rsH.close();
		consulta.close();
		consultaH.close();

		} catch (SQLException e) {
			System.out.println("Hay un error" + e);
		}finally{	    

			desconectarBd(con);	           
	    }
	
	}
	
	public void actualizarReserva(Reserva reserva) {
		String SQL ="UPDATE reservas set fechaEntrada=? ,fechaSalida=? ,valor=? ,formaPago=? WHERE id=?  ";
		
		try {
			PreparedStatement pst=con.prepareStatement(SQL);
			
			pst.setDate(1, reserva.getFechaEntrada());
			pst.setDate(2, reserva.getFechaSalida());
			
			pst.setString(3, reserva.getValor());
			pst.setString(4, reserva.getFormaPago());
			
			pst.setInt(5, reserva.getId());
			
			if(pst.executeUpdate() > 0) {
				JOptionPane.showMessageDialog(null, "Los datos han sido modificados con éxito", "Operación Exitosa", 
                        JOptionPane.INFORMATION_MESSAGE);
			}else {
				JOptionPane.showMessageDialog(null, "No se ha podido realizar la actualización de los datos\n"
                        + "Inténtelo nuevamente.", "Error en la operación", 
                        JOptionPane.ERROR_MESSAGE);
			}
			
			pst.close();
           

		} catch (SQLException e) {   
			System.out.println("Desde actualizacion reserva " + e);
			
		    JOptionPane.showMessageDialog(null, "No se ha podido realizar la actualización de los datos\n"
                    + "Inténtelo nuevamente.\n"
                    + "Error: "+e, "Error en la operación", 
                    JOptionPane.ERROR_MESSAGE);
		}finally{	    

			desconectarBd(con);	           
	    }
	
	}
	
	public void eliminarReserva(String id) {
		try {
			if(con.isClosed()) {
				 con = nuevaConexion(con);				 
			}
			
			String SQL = "DELETE FROM reservas WHERE id=" + id ;

			Statement st = con.createStatement();
			
			int n = st.executeUpdate(SQL);
			
			if(n >= 0) {
				JOptionPane.showMessageDialog(null, "Registro eliminado", "Operación Exitosa", 
                        JOptionPane.INFORMATION_MESSAGE);
			}else {
				JOptionPane.showMessageDialog(null, "No se ha podido eliminar\n"
                        + "Inténtelo nuevamente.", "Error en la operación", 
                        JOptionPane.ERROR_MESSAGE);
			}
			
			st.close();
			 System.out.println("Se desconecto la st base de datos");
			 
		} catch (Exception e) {
			System.out.println("Error desde eliminar reserva " + e);
		    JOptionPane.showMessageDialog(null, "No se ha podido eliminar\n"
                    + "Inténtelo nuevamente.\n"
                    + "Error: "+e, "Error en la operación", 
                    JOptionPane.ERROR_MESSAGE);
			
	}finally{

		desconectarBd(con);		
	    }
	
	}
	
	void desconectarBd(Connection con) {
        if(con!=null){
	        
            try {                
            	con.close();
            } catch (SQLException e) {
            
                JOptionPane.showMessageDialog(null, "Error al intentar cerrar la conexión."
                                          + "Error: "+e, "Error en la operación", 
                                          JOptionPane.ERROR_MESSAGE);
                
            }
            
        }
	}
	
	Connection nuevaConexion(Connection con ) {
		DbConnection nuevaCon = new DbConnection();
		con = nuevaCon.getConnection();
		return con;
	}
}


