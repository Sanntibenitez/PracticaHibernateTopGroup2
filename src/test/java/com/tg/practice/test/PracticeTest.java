package com.tg.practice.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Restrictions;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tg.practice.model2.Empleado;
import com.tg.practice.model2.EmpleadoPermanente;
import com.tg.practice.model2.Fichaje;
import com.tg.practice.model2.Localidad;
import com.tg.practice.model2.Puesto;
import com.tg.practice.model2.Sucursal;

@SuppressWarnings("unchecked")
public class PracticeTest {
	private static Logger log = LoggerFactory.getLogger(PracticeTest.class);
	protected static SessionFactory sessionFactory;

	@BeforeClass
	public static void initialize() {

		sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
		log.info("Session factory creado correctamente");

	}

	@AfterClass
	public static void endTest() {
		sessionFactory.close();
	}

	@Test
	public void listarEmpleados() {

		Session session = sessionFactory.openSession();

		Criteria criteria = session.createCriteria(Empleado.class);
		List<Empleado> empleados = criteria.list();
		for (Empleado empleado : empleados) {
			System.out.println(empleado);
		}
	}

	@Test
	public void listarSucursales() {

		Session session = sessionFactory.openSession();

		Criteria criteria = session.createCriteria(Sucursal.class);
		List<Sucursal> sucursales = criteria.list();
		for (Sucursal sucursal : sucursales) {
			System.out.println(sucursal);
		}
	}

	@Test
	public void crearNuevoEmpleado() {

		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		Criteria criteria = session.createCriteria(Puesto.class);

		Criteria criteriaSucursalA = session.createCriteria(Sucursal.class);
		Criteria criteriaSucursalB = session.createCriteria(Sucursal.class);
		Criteria criteriaSucursalE = session.createCriteria(Sucursal.class);

		criteria.add(Restrictions.eq("nombre", "Analista"));
		Puesto puesto = (Puesto) criteria.uniqueResult();

		criteriaSucursalA.add(Restrictions.eq("descripcion", "Sucursal A"));
		Sucursal sucursalA = (Sucursal) criteriaSucursalA.uniqueResult();

		criteriaSucursalB.add(Restrictions.eq("descripcion", "Sucursal B"));
		Sucursal sucursalB = (Sucursal) criteriaSucursalB.uniqueResult();

		criteriaSucursalE.add(Restrictions.eq("descripcion", "Sucursal E"));
		Sucursal sucursalE = (Sucursal) criteriaSucursalE.uniqueResult();

		Date date = StringToDate("06-12-2015 17:03:00");

		EmpleadoPermanente empleadoPermanente = new EmpleadoPermanente();

		empleadoPermanente.setNombre("Rafael");
		empleadoPermanente.setApellido("Castillo");
		empleadoPermanente.setnLegajo(5688);
		empleadoPermanente.setFechaNacimiento(date);
		empleadoPermanente.setDni(56895232);
		empleadoPermanente.setCuil("20-56895232-7");
		empleadoPermanente.setSucursalPrincipal(sucursalA);
		empleadoPermanente.setSueldo(50000);
		empleadoPermanente.setPuesto(puesto);
		empleadoPermanente.setPorcentajeBono(0.3);
		empleadoPermanente.setCantidadHijos(3);

		session.save(empleadoPermanente);
		tx.commit();

		this.listarEmpleados();
	}

	@Test
	public void buscarFichaje() {

		Session session = sessionFactory.openSession();

		Date date = StringToDate("2010-03-07 00:00:00");
		Date date1 = StringToDate("2013-03-09 00:00:00");

//		Criteria criteria = session.createCriteria(Fichaje.class).createAlias("empleado", "emp")
//				.add(Restrictions.eq("emp.dni", 25432346)).add(Restrictions.between("ingreso", date, date1));

//		Criteria criteria = session.createCriteria(Fichaje.class).add(Restrictions.between("ingreso", date, date1));

		Criteria criteria = session.createCriteria(Fichaje.class).add(Restrictions.gt("ingreso", date))
				.add(Restrictions.lt("ingreso", date1));

		List<Fichaje> fichajes = criteria.list();

		for (Fichaje fichaje : fichajes) {
			System.out.println(fichaje);
		}

	}

	@Test
	public void crearSucursal() {

		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		Criteria criteriaEmpleado = session.createCriteria(Empleado.class);
		List<Empleado> empleados = criteriaEmpleado.list();

		Criteria criteriaLocalidad = session.createCriteria(Localidad.class);
		criteriaLocalidad.add(Restrictions.eq("nombre", "Rosario"));

		Localidad localidad = (Localidad) criteriaLocalidad.uniqueResult();

		Sucursal sucursalF = new Sucursal();

		sucursalF.setEmpleadosHabilitados(empleados);
		sucursalF.setNomenclador("Nomenclador");
		sucursalF.setDescripcion("Descricpion");
		sucursalF.setDireccion("Direccion");
		sucursalF.setCentral(true);
		sucursalF.setLocalidad(localidad);

		session.save(sucursalF);
		tx.commit();

		this.listarEmpleadosPorSucursal(sucursalF);

	}

	public Date StringToDate(String s) {

		Date result = null;
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			result = dateFormat.parse(s);
		}

		catch (ParseException e) {
			e.printStackTrace();

		}
		return result;
	}

	public void listarEmpleadosPorSucursal(Sucursal sucursal) {

		Collection<Empleado> empleados = sucursal.getEmpleadosHabilitados();

		for (Empleado empleado : empleados) {
			System.out.println(empleado);
		}
	}

}
