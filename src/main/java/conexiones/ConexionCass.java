package conexiones;

import com.datastax.driver.core.Cluster;

public class ConexionCass {
	private static ConexionCass instancia;
	private Cluster cluster;

	private ConexionCass() {
		cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
	}
	public static ConexionCass getInstancia() {
		if (instancia == null)
			instancia = new ConexionCass();
		return instancia;
	}
	public Cluster getCluster() {
		return cluster;
	}
}
