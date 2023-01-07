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
import model.Huesped;


public class HuespedDAO {

	private Connection con;
	
	public HuespedDAO(Connection conecction) {
		this.con = conecction;
	}
	
	public void guardar(Huesped huesped) {
		String SQL ="INSERT INTO huespedes(nombre,apellido,nacionalidad,telefono,idReserva,dateNacimiento) VALUES (?,?,?,?,?,?)";
		
		try {
			PreparedStatement pst=con.prepareStatement(SQL);
			
			pst.setString(1, huesped.getNombre());
			pst.setString(2, huesped.getApellido());
			pst.setString(3, huesped.getNacionalidad());
	
			pst.setLong(4, huesped.getTelefono());			

			pst.setInt(5, huesped.getIdReserva());
			pst.setDate(6, huesped.getDateNacimiento());
			pst.execute();
			System.out.println("Huesped exitoso");
			pst.close();
		} catch (SQLException e) {
			System.out.println("hay un error con huspedesDAO "+ e);
		}finally{        
			desconectarBd(con);	
		 }		
			
	}
	
	public void mostrarHuesped( JTable tablaH, DefaultTableModel modeloH, String id) {
		
		try {
			if(con.isClosed()) {
				 con = nuevaConexion(con);				 
			}
		String registrosh[] = new String[8];
		
		String SQLhuesped = "SELECT * FROM huespedes WHERE idReserva = ?"; 	
		PreparedStatement consultaH = con.prepareStatement(SQLhuesped);
		consultaH.setInt(1, Integer.parseInt(id));
		
		ResultSet rsH = consultaH.executeQuery();
		if(rsH.next()) {
			registrosh[0]= rsH.getString("id");
			registrosh[1]= rsH.getString("nombre");
			registrosh[2]= rsH.getString("apellido");
			registrosh[3]= rsH.getString("nacionalidad");
			registrosh[4]= rsH.getString("telefono");
			registrosh[5]= rsH.getString("idReserva");
			registrosh[6]= rsH.getString("dateNacimiento");
			
			modeloH.addRow(registrosh);
			tablaH.setModel(modeloH);

		}
		rsH.close();
		consultaH.close();

	} catch (SQLException e) {
		System.out.println("Hay un error" + e);
	}finally{        
		desconectarBd(con);	
 }		
		
	}
	
	public void mostrarDatosApellido( JTable tablaH, DefaultTableModel modeloH, String apellido) {
		
		try {
			if(con.isClosed()) {
				 con = nuevaConexion(con);				 
			}

			String registrosh[] = new String[8];
			
			String SQLhuesped = "SELECT * FROM huespedes WHERE apellido = ?"; 	
			PreparedStatement consultaH = con.prepareStatement(SQLhuesped);
			
			consultaH.setString(1, apellido);			
			
			ResultSet rsH = consultaH.executeQuery();
			
			String SQL = "SELECT * FROM reservas WHERE id = ?"; 
			PreparedStatement consulta = con.prepareStatement(SQL);
			
			if(rsH.next()) {
				registrosh[0]= rsH.getString("id");
				registrosh[1]= rsH.getString("nombre");
				registrosh[2]= rsH.getString("apellido");
				registrosh[3]= rsH.getString("nacionalidad");
				registrosh[4]= rsH.getString("telefono");
				registrosh[5]= rsH.getString("idReserva");
				consulta.setInt(1, Integer.parseInt(registrosh[5]));
				registrosh[6]= rsH.getString("dateNacimiento");	
				
				modeloH.addRow(registrosh);
				tablaH.setModel(modeloH);


			}
			rsH.close();
			consultaH.close();

		} catch (SQLException e) {
			System.out.println("Hay un error" + e);
			
		}finally{
	           
			desconectarBd(con);	
	    }
	
	}
	
	public void actualizarHuesped(Huesped huesped) {
		String SQL ="UPDATE huespedes SET nombre=?,apellido=?,nacionalidad=?,telefono=?,idReserva=?,dateNacimiento=? WHERE id=? ";
		
		try {
			PreparedStatement pst=con.prepareStatement(SQL);
			
			pst.setString(1, huesped.getNombre());
			pst.setString(2, huesped.getApellido());
			pst.setString(3, huesped.getNacionalidad());
	
			pst.setLong(4, huesped.getTelefono());			

			pst.setInt(5, huesped.getIdReserva());
			pst.setDate(6, huesped.getDateNacimiento());
			pst.setInt(7, huesped.getId());
			
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
		System.out.println("Desde actualizacion huesped " + e);
			
		    JOptionPane.showMessageDialog(null, "No se ha podido realizar la actualización de los datos\n"
                    + "Inténtelo nuevamente.\n"
                    + "Error: "+e, "Error en la operación", 
                    JOptionPane.ERROR_MESSAGE);
		}finally{
	           
			desconectarBd(con);	
	    }
	}
	
	public void eliminarHuesped(String idReservaHuesped) {
		try {
			
			if(con.isClosed()) {
				 con = nuevaConexion(con);				 
			}
			
			String SQL ="DELETE FROM huespedes WHERE idReserva= "+ idReservaHuesped ;		
			
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
		} catch (Exception e) {
			System.out.println("Error desde eliminar huesped " + e);
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
