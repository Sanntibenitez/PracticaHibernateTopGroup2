package com.tg.practice.model2;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class EmpleadoPermanente extends Empleado {

	@Column(nullable = false)
	private Double porcentajeBono;

	private Integer cantidadHijos;

	public Double getPorcentajeBono() {
		return porcentajeBono;
	}

	public void setPorcentajeBono(Double porcentajeBono) {
		this.porcentajeBono = porcentajeBono;
	}

	public Integer getCantidadHijos() {
		return cantidadHijos;
	}

	public void setCantidadHijos(Integer cantidadHijos) {
		this.cantidadHijos = cantidadHijos;
	}
	
	@Override
	public String toString() {
		
		return super.toString() + this.getCantidadHijos();
		
	}

}
