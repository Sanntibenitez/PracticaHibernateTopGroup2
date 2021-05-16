package com.tg.practice.model2;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Sucursal {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(nullable = false)
	private String nomenclador;

	@Column(nullable = false)
	private String descripcion;

	private String direccion;

	private Boolean central;

	@ManyToOne(fetch = FetchType.LAZY)
	private Localidad localidad;

	@ManyToMany(mappedBy = "sucursalesHabilitadas")
	private Collection<Empleado> empleadosHabilitados;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNomenclador() {
		return nomenclador;
	}

	public void setNomenclador(String nomenclador) {
		this.nomenclador = nomenclador;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Boolean getCentral() {
		return central;
	}

	public void setCentral(Boolean central) {
		this.central = central;
	}

	public Localidad getLocalidad() {
		return localidad;
	}

	public void setLocalidad(Localidad localidad) {
		this.localidad = localidad;
	}

	public Collection<Empleado> getEmpleadosHabilitados() {
		return empleadosHabilitados;
	}

	public void setEmpleadosHabilitados(Collection<Empleado> empleadosHabilitados) {
		this.empleadosHabilitados = empleadosHabilitados;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer("Sucursal : ").append(id).append("   ").append(direccion).append("   ")
				.append(nomenclador).append("   ").append(descripcion).append("");
		return sb.toString();
	}

}