package controller;

import java.sql.Connection;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import conexionSQL.DbConnection;
import dao.HuespedDAO;
import model.Huesped;
import model.Reserva;

public class HuespedController {

	private HuespedDAO huespedDAO;
	
	public HuespedController() {
		Connection con = new DbConnection().getConnection();
		this.huespedDAO = new HuespedDAO(con);
	}
	
	public void guardar(Huesped huesped) {
		this.huespedDAO.guardar(huesped);
		
	}
	
	
	public void mostrarHuesped( JTable tablaH,  DefaultTableModel modeloH, String id) {
		this.huespedDAO.mostrarHuesped(tablaH, modeloH, id);
		
	}
	
	public void mostrarDatosHuespedApellido( JTable tablaH, DefaultTableModel modeloH, String apellido) {
		this.huespedDAO.mostrarDatosApellido(tablaH, modeloH, apellido);		
	}
	
	public void actualizarHuesped(Huesped huesped) {
		this.huespedDAO.actualizarHuesped(huesped);			
	}
	
	public void eliminarHuesped(String idReservaHuesped) {
		this.huespedDAO.eliminarHuesped(idReservaHuesped);
	}
}
