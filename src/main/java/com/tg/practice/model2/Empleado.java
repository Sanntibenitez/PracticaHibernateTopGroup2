package com.tg.practice.model2;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Empleado {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(nullable = false)
	private String apellido;

	@Column(nullable = false)
	private String nombre;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date fechaNacimiento;

	@Column(nullable = false)
	private Integer nLegajo;

	@Column(nullable = false)
	private Integer dni;

	@Column(nullable = false)
	private String cuil;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Puesto puesto;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private Sucursal sucursalPrincipal;

	@Temporal(TemporalType.DATE)
	private Date fechaIngreso;

	@Column(nullable = false)
	private Integer sueldo;

	@ManyToMany
	private Set<Sucursal> sucursalesHabilitadas;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public Integer getnLegajo() {
		return nLegajo;
	}

	public void setnLegajo(Integer nLegajo) {
		this.nLegajo = nLegajo;
	}

	public Integer getDni() {
		return dni;
	}

	public void setDni(Integer dni) {
		this.dni = dni;
	}

	public String getCuil() {
		return cuil;
	}

	public void setCuil(String cuil) {
		this.cuil = cuil;
	}

	public Puesto getPuesto() {
		return puesto;
	}

	public void setPuesto(Puesto puesto) {
		this.puesto = puesto;
	}

	public Sucursal getSucursalPrincipal() {
		return sucursalPrincipal;
	}

	public void setSucursalPrincipal(Sucursal sucursalPrincipal) {
		this.sucursalPrincipal = sucursalPrincipal;
	}

	public Date getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public Integer getSueldo() {
		return sueldo;
	}

	public void setSueldo(Integer sueldo) {
		this.sueldo = sueldo;
	}

	public Set<Sucursal> getSucursalesHabilitadas() {
		return sucursalesHabilitadas;
	}

	public void setSucursalesHabilitadas(Set<Sucursal> sucursalesHabilitadas) {
		this.sucursalesHabilitadas = sucursalesHabilitadas;
	}

	public void agregarSucursal(Sucursal sucursal) {
		this.sucursalesHabilitadas.add(sucursal);
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer("Empleado : ").append(id).append("   ").append(nombre).append("   ")
				.append(apellido).append("   ").append(dni).append("   ").append(fechaNacimiento).append("   ");
		return sb.toString();
	}

}
