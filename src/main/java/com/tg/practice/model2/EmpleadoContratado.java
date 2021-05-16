package com.tg.practice.model2;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class EmpleadoContratado extends Empleado {
	
	@Temporal(TemporalType.DATE)
	@Column(nullable=false)
	private Date fechaInicioContrato;
	
	@Temporal(TemporalType.DATE)
	@Column(nullable=false)
	private Date fechaFinContrato;
	
	@Enumerated
	@Column(nullable=false)
	private TipoContrato tipoContrato;

	public Date getFechaInicioContrato() {
		return fechaInicioContrato;
	}

	public void setFechaInicioContrato(Date fechaInicioContrato) {
		this.fechaInicioContrato = fechaInicioContrato;
	}

	public Date getFechaFinContrato() {
		return fechaFinContrato;
	}

	public void setFechaFinContrato(Date fechaFinContrato) {
		this.fechaFinContrato = fechaFinContrato;
	}

	public TipoContrato getTipoContrato() {
		return tipoContrato;
	}

	public void setTipoContrato(TipoContrato tipoContrato) {
		this.tipoContrato = tipoContrato;
	}
	
	
	
	

}
