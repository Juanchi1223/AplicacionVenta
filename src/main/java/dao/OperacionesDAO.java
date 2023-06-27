package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

import java.util.Scanner;

import pojos.*;


import conexiones.ConexionMySQL;

public class OperacionesDAO {
	private static OperacionesDAO instancia;
	
	public static OperacionesDAO getInstancia() {
		if (instancia == null)
			instancia = new OperacionesDAO();
		return instancia;
	}
	
	public void pagar(Operacion operacion) {
		Statement x;
		try {
			x = ConexionMySQL.getInstancia().getConnection().createStatement();
			x.execute("INSERT INTO operaciones VALUES ("
					+ operacion.getIdOperacion() + ","
					+ operacion.getIdFactura() + ", '"
					+ operacion.getMedio() + "', '"
					+ operacion.getOperador() + "', '"
					+ operacion.getFecha() + "', "
					+ operacion.getHora() + ", "
					+ operacion.getMonto()
					+ ")");
			x.close();
			System.out.println("Se agrego la operacion de paga a la bd");
		} catch (SQLException e) {
			System.out.println("ERROR no se pudo acceder al MySQL: " + e);
		}
	}
	
	public int darIdOperacion(){
		PreparedStatement x;
		int id = -1;
		try {
			x = ConexionMySQL.getInstancia().getConnection().prepareStatement(" SELECT idOperacion FROM operaciones ORDER BY idOperacion DESC LIMIT 1");
			ResultSet aux = x.executeQuery();
			if(aux.next()) {
				id = aux.getInt(1);
			}
			x.close();
			aux.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}
	
	public boolean verificarPagado(int idFactura) {
		boolean flag = false;
		try {
			PreparedStatement x = ConexionMySQL.getInstancia().getConnection().prepareStatement(" SELECT * FROM operaciones WHERE idFactura = "+ idFactura);
			ResultSet aux = x.executeQuery();

			if(aux.next()) {
				flag = true;
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	
		return flag;
	}
}
