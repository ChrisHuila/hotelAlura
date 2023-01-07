package controller;

import java.sql.Connection;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import conexionSQL.DbConnection;
import dao.ReservaDAO;
import model.Reserva;

public class ReservaController {

	private ReservaDAO reservaDAO;	
	
	public ReservaController() {
		Connection con = new DbConnection().getConnection();
		this.reservaDAO = new ReservaDAO(con);
	}
	
	public int guardar(Reserva reserva) {
		this.reservaDAO.guardar(reserva);
		return reserva.getId();
		
	}
	
	public void mostrarDatosReserva(JTable tablaR,  DefaultTableModel modeloR,  String id) {
		this.reservaDAO.mostrarReserva(tablaR,  modeloR,  id);
		
	}
	
	public void mostrarDatosReservaApellido(JTable tablaR, DefaultTableModel modeloR, String apellido) {
		this.reservaDAO.mostrarDatosApellido(tablaR, modeloR, apellido);
		
	}
	
	public void actualizarReserva(Reserva reserva) {
		this.reservaDAO.actualizarReserva(reserva);		
	}
	
	public void eliminarReserva(String id) {
		this.reservaDAO.eliminarReserva(id);
	}
}
