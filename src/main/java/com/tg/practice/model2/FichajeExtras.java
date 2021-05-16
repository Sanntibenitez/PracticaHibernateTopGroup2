package com.tg.practice.model2;

import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Embeddable
public class FichajeExtras {

	private String usuario;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaModificacion;

	@Enumerated
	private MotivoFichajeManual motivoFichajeManual;

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public MotivoFichajeManual getMotivoFichajeManual() {
		return motivoFichajeManual;
	}

	public void setMotivoFichajeManual(MotivoFichajeManual motivoFichajeManual) {
		this.motivoFichajeManual = motivoFichajeManual;
	}

	

}