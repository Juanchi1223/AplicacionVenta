package dao;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import conexiones.ConexionRedis;

import com.google.gson.Gson;

import pojos.Ingreso;

public class CarritoDAO {
	private static CarritoDAO instancia;
	
	public static CarritoDAO getInstancia() {
		if (instancia == null)
			instancia = new CarritoDAO();
		return instancia;
	} 
	
	public void agregarCarrito(Ingreso ingreso, String usuario) {	// TODO arreglar se puede ingresar duplicados
		JedisPool pool = ConexionRedis.getInstancia().getJedis();
		Jedis jedis = pool.getResource();

		
		Gson gson = new Gson();
        String ingresoJson = gson.toJson(ingreso);

        jedis.rpush(usuario+"Carrito", ingresoJson);
        
        jedis.close();
        System.out.print("Se agrego "+ ingreso.getNombre_producto()+" correctamente");
	}
	public void eliminarCarrito(String usuario, String nombre_producto) {
		JedisPool pool = ConexionRedis.getInstancia().getJedis();
		Jedis jedis = pool.getResource();
		
		long tope = jedis.llen(usuario+"Carrito");
		Gson gson = new Gson();
		
		for(long i = 0; i < tope; i++){
			String objJson = jedis.lindex(usuario+"Carrito", i);
			Ingreso ingreso = gson.fromJson(objJson, Ingreso.class);
			if(ingreso.getNombre_producto().equals(nombre_producto)) {
				jedis.lrem(usuario+"Carrito", 0, objJson);
				break;
			}
		}
		System.out.println("Se borro el "+ nombre_producto);
		jedis.close();
	}
	
	public void cambiarCarrito() {
		// parecid a cambiar pero se ingresa la cantiadad producto
	}
	
	public void undo() {
		// vas a tener que hacer un rpop y lesto 
	}
}