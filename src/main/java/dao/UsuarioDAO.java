package dao;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

import conexiones.ConexionCass;
import pojos.*;

public class UsuarioDAO {
	private static UsuarioDAO instancia;
	
	public static UsuarioDAO getInstancia() {
		if (instancia == null)
			instancia = new UsuarioDAO();
		return instancia;
	} 
	
	public UsuarioActual guardarDatos(String user) { 		// ACA SE GENERA EL OBEJTO USUARIO PARA PODER USARLO EN VARIOS 
		UsuarioActual usAc = new UsuarioActual();
		
		Cluster cluster = ConexionCass.getInstancia().getCluster();
		Session secion = cluster.connect();
		
		Row reslt = secion.execute(" SELECT * FROM usuarios.datos_usuarios WHERE usuario = '"+ user +"' ").one();
		
		usAc.setNombreUs(reslt.getString("usuario"));
		usAc.setContraUs(reslt.getString("contrasena"));
		usAc.setDireccion(reslt.getString("direccion"));
		usAc.setDocumento(reslt.getInt("documento"));
		
		cluster.close();
		secion.close();
		
		return usAc;
	}

	public boolean verificarCC(String contra, String user) { // TENIENDO EN CUENTA EL USUARIO HAY QUE VER LA CONTRASEÑA DE LA BD CASANDRA
		
		Cluster cluster = ConexionCass.getInstancia().getCluster();
		Session secion = cluster.connect();
		
		Row reslt = secion.execute(" SELECT * FROM usuarios.datos_usuarios WHERE usuario = '"+ user +"' AND contrasena = '"+ contra +"'").one();
		
		secion.close();
		
		if (reslt == null)
			return false;
		
		return true;
	}

	public boolean verificarUser(String user) {		// COMPRBAR QUE EL USUARIO ESTA EN LA BASE DE DATOS

		Cluster cluster = ConexionCass.getInstancia().getCluster();
		Session secion = cluster.connect();
		
		Row reslt = secion.execute(" SELECT * FROM usuarios.datos_usuarios WHERE usuario = '"+ user +"' ").one();
		
		secion.close();
		
		if (reslt == null)
			return false;
		
		return true;
	}
}
