package com.tg.practice.model2;

import java.util.Collection;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Localidad {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	private Long id;

	private String codigoPostal;

	@Basic(optional = false)
	private String nombre;

	@OneToMany(mappedBy = "localidad")
	private Collection<Sucursal> sucursales;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Collection<Sucursal> getSucursales() {
		return sucursales;
	}

	public void setSucursales(Collection<Sucursal> sucursales) {
		this.sucursales = sucursales;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer("Localidad : ").append(id).append("Nombre ").append(nombre).append("   ");
		return sb.toString();
	}

}
