package dao;

import java.sql.SQLException;
import java.sql.Statement;

import conexiones.ConexionMySQL;
import pojos.Factura;
import pojos.Operacion;

public class FacturasDAO {
	private static FacturasDAO instancia;
	
	public static FacturasDAO getInstancia() {
		if (instancia == null)
			instancia = new FacturasDAO();
		return instancia;
	} 
	
	public void guardarFactura(Factura factura) {
		try {
			Statement x = ConexionMySQL.getInstancia().getConnection().createStatement();
			x.execute("INSERT INTO facturas (idFactura, DNIusuario, formaPago, monto) VALUES ("+ factura.getIdFactura()+","+factura.getDNIusuario()+", '"+factura.getFormaDePago()+"' ,"+factura.getMonto() +")");
			x.close();
			System.out.println("Se agrego la factura a la base de datos :) ");
		} catch (SQLException e) {
			System.out.println("ERROR no se pudo acceder al MySQL: " + e);
		}
	}
	public void pagar(Operacion operacion) {
		Statement x;
		try {
			x = ConexionMySQL.getInstancia().getConnection().createStatement();
			x.execute("INSERT INTO operaciones VALUES ("
					+ operacion.getIdOperacion() + ","
					+ operacion.getIdFactura() + ", '"
					+ operacion.getMedio() + "', '"
					+ operacion.getOperador() + "', "
					+ "NULL , "
					+ operacion.getMonto()
					+ ")");
			x.close();
			System.out.println("Se agrego la operacion de paga a la bd");
		} catch (SQLException e) {
			System.out.println("ERROR no se pudo acceder al MySQL: " + e);
		}
	}
}
