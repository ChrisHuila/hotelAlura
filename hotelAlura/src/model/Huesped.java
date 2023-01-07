package model;

import java.sql.Date;

public class Huesped {

	private Integer id;
	private String nombre;
	private String apellido;
	private String nacionalidad;
	private long telefono;
	private Integer idReserva;
	private Date dateNacimiento;
	


	
	public Huesped(String nombre, String apellido, String nacionalidad, long telefono, Integer idReserva,
			Date dateNacimiento) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.nacionalidad = nacionalidad;
		this.telefono = telefono;
		this.idReserva = idReserva;
		this.dateNacimiento = dateNacimiento;
	}




	public Huesped(Integer id, String nombre, String apellido, String nacionalidad, long telefono, Integer idReserva,
			Date dateNacimiento) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.nacionalidad = nacionalidad;
		this.telefono = telefono;
		this.idReserva = idReserva;
		this.dateNacimiento = dateNacimiento;
	}




	public Integer getId() {
		return id;
	}

	public Integer getIdReserva() {
		return idReserva;
	}

	public String getNombre() {
		return nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public long getTelefono() {
		return telefono;
	}

	public Date getDateNacimiento() {
		return dateNacimiento;
	}



}
