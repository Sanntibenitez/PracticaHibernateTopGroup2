package com.tg.practice.test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Restrictions;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tg.practice.model2.Empleado;
import com.tg.practice.model2.EmpleadoContratado;
import com.tg.practice.model2.EmpleadoPermanente;
import com.tg.practice.model2.Fichaje;
import com.tg.practice.model2.FichajeExtras;
import com.tg.practice.model2.Localidad;
import com.tg.practice.model2.MotivoFichajeManual;
import com.tg.practice.model2.Puesto;
import com.tg.practice.model2.Sucursal;
import com.tg.practice.model2.TipoContrato;
import com.tg.practice.model2.TipoFichaje;

@SuppressWarnings("unchecked")
@DisplayName("Practica 2 Hibernate")
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
	@DisplayName("Lista de empleados")
	public void listarEmpleados() {

		Session session = sessionFactory.openSession();

		Criteria criteria = session.createCriteria(Empleado.class);
		List<Empleado> empleados = criteria.list();
		for (Empleado empleado : empleados) {
			System.out.println(empleado);
		}
	}

	@Test
	@DisplayName("Lista de sucursales")
	public void listarSucursales() {

		Session session = sessionFactory.openSession();

		Criteria criteria = session.createCriteria(Sucursal.class);
		List<Sucursal> sucursales = criteria.list();
		for (Sucursal sucursal : sucursales) {
			System.out.println(sucursal);
		}
	}

	@Test
	@DisplayName("Lista de fichajes")
	public void listarFichajes() {

		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(Fichaje.class);
		List<Fichaje> fichajes = criteria.list();
		for (Fichaje fichaje : fichajes) {
			System.out.println(fichaje);
		}
	}

	@Test
	@DisplayName("Lista de fichajes")
	public void listarPuestos() {

		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(Puesto.class);
		List<Puesto> puestos = criteria.list();
		for (Puesto puesto : puestos) {
			System.out.println(puesto);
		}
	}

	@Test
	@DisplayName("Crea un nuevo empleadoContratado Anual Analista y le asigna la sucursal A de principal y la B y E de sucursales habilitadas")
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

		criteriaSucursalB.add(Restrictions.eq("nomenclador", "SUC3"));
		Sucursal sucursalB = (Sucursal) criteriaSucursalB.uniqueResult();

		criteriaSucursalE.add(Restrictions.eq("direccion", "Lisandro 1345"));
		Sucursal sucursalE = (Sucursal) criteriaSucursalE.uniqueResult();

		Date fechaNacimiento = Utils.StringToDate("06-12-1989 17:03:00");
		Date fechaIngreso = Utils.StringToDate("16-05-2021 09:00:00");
		Date fechaEgreso = Utils.StringToDate("16-05-2022 09:00:00");

		//Creo un Set de sucursales habilitadas para luego agregarselo al nuevo empleado
		Set<Sucursal> sucursalesHabilitdas = new HashSet<Sucursal>();
		sucursalesHabilitdas.add(sucursalE);
		sucursalesHabilitdas.add(sucursalB);
		sucursalesHabilitdas.add(sucursalA);

		EmpleadoContratado empleadoContratado = new EmpleadoContratado();

		empleadoContratado.setNombre("Rafael");
		empleadoContratado.setApellido("Castillo");
		empleadoContratado.setnLegajo(5688);
		empleadoContratado.setFechaNacimiento(fechaNacimiento);
		empleadoContratado.setDni(56895232);
		empleadoContratado.setCuil("20-56895232-7");
		empleadoContratado.setSucursalPrincipal(sucursalA);
		empleadoContratado.setSueldo(50000);
		empleadoContratado.setPuesto(puesto);
		empleadoContratado.setFechaInicioContrato(fechaIngreso);
		empleadoContratado.setFechaFinContrato(fechaEgreso);
		empleadoContratado.setTipoContrato(TipoContrato.ANUAL);
		
		//Seteo las sucursales habilitadas para el nuevo usuario
		empleadoContratado.setSucursalesHabilitadas(sucursalesHabilitdas);

		session.save(empleadoContratado);
		tx.commit();
		
		//Imprime en consola los empleados para ver que se hayan creado correctamente
		this.listarEmpleados();
		
		//Imprime en consola las sucursales habilitdas del usuario para ver que se hayan agregado correctamente
		Utils.listarSucursalesHabilitadas(empleadoContratado);
	}

	@Test
	@DisplayName("Busca el fichaje del empleado Pedro Lopez del dia 16/04/2013 y lo modifica")
	public void buscarYModificarFichajePedroLopez() {

		Session session = sessionFactory.openSession();
		
		//Fecha inicio del dia 16/04/2013
		GregorianCalendar date = new GregorianCalendar(2013, 03, 16, 0, 0, 0);
		
		//Fecha fin del dia 16/04/2013
		GregorianCalendar date1 = new GregorianCalendar(2013, 03, 16, 23, 59, 59);

		Criteria criteria = session.createCriteria(Fichaje.class)
				.createAlias("empleado", "emp")
				.add(Restrictions.eq("emp.dni", 25432346))
				.add(Restrictions.eq("tipoFichaje", TipoFichaje.MANUAL))
				.add(Restrictions.between("ingreso", date.getTime(), date1.getTime()));

		Fichaje fichaje = (Fichaje) criteria.uniqueResult();
		
		//Imprime el fichaje antes que se le realicen los cambios
		System.out.println(fichaje);

		session.close();

		Session sessionNew = sessionFactory.openSession();
		Transaction tx = sessionNew.beginTransaction();

		sessionNew.update(fichaje);

		Criteria criteriaSucursalE = sessionNew.createCriteria(Sucursal.class);
		criteriaSucursalE.add(Restrictions.eq("id", 1l));
		Sucursal sucursalE = (Sucursal) criteriaSucursalE.uniqueResult();

		FichajeExtras fichajeExtras = new FichajeExtras();
		fichajeExtras.setMotivoFichajeManual(MotivoFichajeManual.URGENCIAS);
		fichajeExtras.setUsuario("Marcelo");
		fichajeExtras.setFechaModificacion(Calendar.getInstance().getTime());
		fichaje.setFichajeExtra(fichajeExtras);
		fichaje.setSucursal(sucursalE);
		
		sessionNew.save(fichaje);
		
		//Imprime el fichaje luego de que se le aplicaron los cambios junto con el usuario que lo realizo y la fecha 
		System.out.println(fichaje);
		System.out.println("Usuario que realizo el fichaje: " + fichaje.getFichajeExtra().getUsuario());
		System.out.println("Fecha del fichaje: " + fichaje.getFichajeExtra().getFechaModificacion());

		tx.commit();

		sessionNew.close();
	}

	@Test
	@DisplayName("Busca los fichajes automaticos entre las fechas 07/03/2013 y 09/03/2013 de la ciudad de Santa Fe y los elimina")
	public void buscarYEliminarFichajes() {
		// Se elimina el fichaje 960 que es el unico que cumple con todas las
		// restricciones
		
		Session session = sessionFactory.openSession();

		Transaction tx = session.beginTransaction();

		GregorianCalendar date = new GregorianCalendar(2013, 02, 7, 0, 0, 0);
		GregorianCalendar date1 = new GregorianCalendar(2013, 02, 9, 23, 59, 59);

		Criteria criteria = session.createCriteria(Fichaje.class)
				.createAlias("sucursal", "sucur")
				.createAlias("sucur.localidad", "local")
				.add(Restrictions.eq("local.nombre", "Santa Fe"))
				.add(Restrictions.eq("tipoFichaje", TipoFichaje.MANUAL))
				.add(Restrictions.between("ingreso", date.getTime(), date1.getTime()));

		List<Fichaje> fichajes = criteria.list();
		for (Fichaje fichaje : fichajes) {
			session.delete(fichaje);
		}

		tx.commit();

		session.close();

		//Imprimo la lista de fichajes y me fijo que el fichaje con id 960 ya no existe
		this.listarFichajes();

	}

	@Test
	@DisplayName("Crea una sucursal nueva y le asigna todos los empleados disponibles como empleados habilitados")
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
		session.close();
		
		//Muestra por consola que todos los empleados estan asociados con la nueva sucursal creada
		Utils.listarEmpleadosPorSucursal(sucursalF);
	}

	@Test
	@DisplayName("Fichajes empleados habilitados para la Sucursal A Manuales")
	public void fichajesManualesSucursalA() {

		Session session = sessionFactory.openSession();

		Criteria criteriaFichajes = session.createCriteria(Fichaje.class)
				.createAlias("sucursal", "suc")
				.add(Restrictions.eq("suc.descripcion", "Sucursal A"))
				.add(Restrictions.eq("tipoFichaje", TipoFichaje.MANUAL));

		List<Fichaje> fichajes = criteriaFichajes.list();
		for (Fichaje fichaje : fichajes) {
			System.out.println(fichaje);
		}
		
		session.close();
	}

	@Test
	@DisplayName("Fichajes empleados anuales en Cordoba en los ultimos 2 meses")
	public void fichajesAnualesSantaFeUltimos2Meses() {

		// Trae los fichajes de empleados con contrato ANUAL entre el 01/03/2013 y 31/05/2013
		// de Santa Fe por que Cordoba no me toma el nombre

		Session session = sessionFactory.openSession();

		GregorianCalendar date = new GregorianCalendar(2013, 02, 1, 0, 0, 0);
		GregorianCalendar date1 = new GregorianCalendar(2013, 04, 31, 23, 59, 59);

		Criteria criteriaFichajes = session.createCriteria(Fichaje.class)
				.createAlias("empleado", "emp")
				.createAlias("sucursal", "suc")
				.createAlias("suc.localidad", "localidad")
				.add(Restrictions.eq("emp.tipoContrato", TipoContrato.ANUAL))
				.add(Restrictions.eq("localidad.nombre", "Santa Fe"))
				.add(Restrictions.between("ingreso", date.getTime(), date1.getTime()));

		List<Fichaje> fichajes = criteriaFichajes.list();
		for (Fichaje fichaje : fichajes) {
			System.out.println(fichaje);
		}
		
		session.close();
	}

	@Test
	@DisplayName("Empleado permanentes con bono mayor al 30%")
	public void empleadoPermanenteBonoMayorA30Porciento() {

		Session session = sessionFactory.openSession();

		Criteria criteriaEmpleados = session.createCriteria(EmpleadoPermanente.class)
				.add(Restrictions.gt("porcentajeBono", 0.3));

		List<EmpleadoPermanente> empleadosPermanentes = criteriaEmpleados.list();

		for (EmpleadoPermanente empleado : empleadosPermanentes) {
			session.save(empleado);
		}

		session.close();

		Session sessionNew = sessionFactory.openSession();
		
		// Imprimo por consola los datos pedidos. Pide ademas las sucursales, uno de los dos empleados no tiene sucursales Habilitadas
		// Si agrego para que imprima ese campo no lo muestra
		for (EmpleadoPermanente empleado : empleadosPermanentes) {
			sessionNew.update(empleado);
			System.out.println(empleado.getNombre() + " " + empleado.getApellido() + " " + empleado.getPuesto().getNombre());
		}

		sessionNew.close();
	}

	@Test
	@DisplayName("Fichajes por puesto y el empleado mas joven por puesto y sucursal")
	public void fichajesPorPuesto() {

		// No hay fichajes para los puestos de Desarrollador,Arquitecto ni Lider de proyecto
		// (que ademas esta mal escrito en la BBDD)

		Session session = sessionFactory.openSession();

		Criteria criteriaFichajesAnalista = session.createCriteria(Fichaje.class).createAlias("empleado", "emp")
				.createAlias("empleado.puesto", "puesto").add(Restrictions.eq("puesto.nombre", "Analista"));

		Criteria criteriaFichajesDesarrollador = session.createCriteria(Fichaje.class).createAlias("empleado", "emp")
				.createAlias("empleado.puesto", "puesto").add(Restrictions.eq("puesto.nombre", "Desarrollador"));

		Criteria criteriaFichajesArquitecto = session.createCriteria(Fichaje.class).createAlias("empleado", "emp")
				.createAlias("empleado.puesto", "puesto").add(Restrictions.eq("puesto.nombre", "Arquitecto"));

		Criteria criteriaFichajesLider = session.createCriteria(Fichaje.class).createAlias("empleado", "emp")
				.createAlias("empleado.puesto", "puesto")
				.add(Restrictions.eq("puesto.nombre", "L\u00edder de proyecto"));

		List<Fichaje> fichajesAnalistas = criteriaFichajesAnalista.list();
		List<Fichaje> fichajesDesarrolladores = criteriaFichajesDesarrollador.list();
		List<Fichaje> fichajesArquitectos = criteriaFichajesArquitecto.list();
		List<Fichaje> fichajesLider = criteriaFichajesLider.list();

		int cantidadDeFichajesAnalistas = 0;
		for (Fichaje fichaje : fichajesAnalistas) {
			cantidadDeFichajesAnalistas++;
		}

		int cantidadDeFichajesDesarrolladores = 0;
		for (Fichaje fichaje : fichajesDesarrolladores) {
			cantidadDeFichajesAnalistas++;
		}

		int cantidadDeFichajesArquitectos = 0;
		for (Fichaje fichaje : fichajesArquitectos) {
			cantidadDeFichajesAnalistas++;
		}

		int cantidadDeFichajesLider = 0;
		for (Fichaje fichaje : fichajesLider) {
			cantidadDeFichajesLider++;
		}

		System.out.println("Cantidad de fichajes Analistas: " + cantidadDeFichajesAnalistas);
		System.out.println("Cantidad de fichajes Desarrolladores: " + cantidadDeFichajesDesarrolladores);
		System.out.println("Cantidad de fichajes Arquitectos: " + cantidadDeFichajesArquitectos);
		System.out.println("Cantidad de fichajes Lider: " + cantidadDeFichajesLider);

		session.close();
	}

	@Test
	@DisplayName("Fichajes manuales realizados por fmateo a empleado con hijos")
	public void fichajesManualesEmpeadoConHijos() {
		// No hay empleados con Hijos, por ende no devuelve ningun dato.
		Session session = sessionFactory.openSession();

		Criteria criteriaFichajes = session.createCriteria(Fichaje.class).createAlias("empleado", "emp")
				.add(Restrictions.eq("fichajeExtra.usuario", "fmateo")).add(Restrictions.gt("emp.cantidadHijos", 0));

		List<Fichaje> fichajesFMateo = criteriaFichajes.list();

		for (Fichaje fichaje : fichajesFMateo) {
			System.out.println(fichaje);
		}

		session.close();
	}

	@Test
	@DisplayName("Fichajes Sucursal central con mas de 6 horas entre ingreso y egreso")
	public void fichajesSucursalCentralConDiferenciaEntreEgresoEIngreso() {

		Session session = sessionFactory.openSession();

		Criteria criteriaFichajes = session.createCriteria(Fichaje.class).createAlias("sucursal", "suc")
				.add(Restrictions.eq("suc.central", true));

		List<Fichaje> fichajesSucursalCentral = criteriaFichajes.list();

		for (Fichaje fichaje : fichajesSucursalCentral) {
			System.out.println(fichaje);
		}

	}

	public void listarFichajesPorSucursal(Long id) {
		Session session = sessionFactory.openSession();

		Criteria criteriaSucursalE = session.createCriteria(Sucursal.class);
		criteriaSucursalE.add(Restrictions.eq("id", 1l));

		Criteria criteria = session.createCriteria(Fichaje.class).createAlias("sucursal", "suc")
				.add(Restrictions.eq("suc.id", id));

		List<Fichaje> fichajes = criteria.list();
		for (Fichaje fichaje : fichajes) {
			System.out.println(fichaje);
		}

	}

}
