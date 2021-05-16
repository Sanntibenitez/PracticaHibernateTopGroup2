package com.tg.practice.model2;

import java.util.Date;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Fichaje {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date ingreso;

	@Temporal(TemporalType.TIMESTAMP)
	private Date egreso;

	@Enumerated(EnumType.ORDINAL)
	private TipoFichaje tipoFichaje;

	@Embedded
	private FichajeExtras fichajeExtra;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private Sucursal sucursal;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private Empleado empleado;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getIngreso() {
		return ingreso;
	}

	public void setIngreso(Date ingreso) {
		this.ingreso = ingreso;
	}

	public Date getEgreso() {
		return egreso;
	}

	public void setEgreso(Date egreso) {
		this.egreso = egreso;
	}

	public TipoFichaje getTipoFichaje() {
		return tipoFichaje;
	}

	public void setTipoFichaje(TipoFichaje tipoFichaje) {
		this.tipoFichaje = tipoFichaje;
	}

	public FichajeExtras getFichajeExtra() {
		return fichajeExtra;
	}

	public void setFichajeExtra(FichajeExtras fichajeExtra) {
		this.fichajeExtra = fichajeExtra;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public Empleado getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer("Fichaje : ").append(id).append("   ").append(empleado.getNombre())
				.append("   ").append(ingreso);
		return sb.toString();
	}

}