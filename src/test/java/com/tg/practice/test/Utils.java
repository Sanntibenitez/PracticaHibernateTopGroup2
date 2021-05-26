package com.tg.practice.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import com.tg.practice.model2.Empleado;
import com.tg.practice.model2.Sucursal;

public class Utils {

	public static Date StringToDate(String s) {

		Date result = null;
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			result = dateFormat.parse(s);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static void listarEmpleadosPorSucursal(Sucursal sucursal) {

		Collection<Empleado> empleados = sucursal.getEmpleadosHabilitados();

		for (Empleado empleado : empleados) {
			System.out.println(empleado);
		}
	}
	
	public static void listarSucursalesHabilitadas(Empleado empleado) {

		Collection<Sucursal> sucursalesHabilitadas = empleado.getSucursalesHabilitadas();

		for (Sucursal sucursal : sucursalesHabilitadas) {
			System.out.println("Sucursal Habilitada: " + sucursal);
		}
	}
	
}
