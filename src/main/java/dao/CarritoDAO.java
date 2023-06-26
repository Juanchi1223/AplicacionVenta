package dao;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
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
	
	public void agregarCarrito(Ingreso ingreso, String usuario) {	 // ver q se ingrese algo del catalogo, se pueden ingresar cosas random 
		if(!duplicado(ingreso, usuario)) {
		JedisPool pool = ConexionRedis.getInstancia().getJedis();
		Jedis jedis = pool.getResource();

		
		Gson gson = new Gson();
        String ingresoJson = gson.toJson(ingreso);

        
        jedis.rpush(usuario+"Carrito", ingresoJson);
        
        jedis.close();
        System.out.print("Se agrego "+ ingreso.getNombre_producto()+" correctamente");
        }
		else {
			System.out.println("Este producto ya fue ingresado, porfavor para cambiar la cantidad comprada usar la opcion de CAMBIAR ;) ");
		}
	}
	private boolean duplicado(Ingreso ingreso, String usuario) {
		ArrayList<Ingreso> carrito = getCarrito(usuario);
		
		for (Ingreso i: carrito) {
			if(i.getNombre_producto().equals(ingreso.getNombre_producto())) 
				return true;
		}
		return false;
	}
	
	public void eliminarCarrito(String usuario, String nombre_producto) {
		JedisPool pool = ConexionRedis.getInstancia().getJedis();
		Jedis jedis = pool.getResource();
		
		String carrito = usuario+"Carrito";
		
		long tope = jedis.llen(carrito);
		Gson gson = new Gson();
		
		for(long i = 0; i < tope; i++){
			String objJson = jedis.lindex(carrito, i);
			Ingreso ingreso = gson.fromJson(objJson, Ingreso.class);
			if(ingreso.getNombre_producto().equals(nombre_producto)) {
				jedis.lrem(carrito, 0, objJson);
				break;
			}
		}
		System.out.println("Se borro el "+ nombre_producto);
		jedis.close();
	}
	
	public void cambiarCarrito(int cantidad, String usuario, String nombre_producto) {
		Jedis jedis = ConexionRedis.getInstancia().getJedis().getResource();
		
		String carrito = usuario+"Carrito";
		long tope = jedis.llen(carrito);
		
		Gson gson = new Gson();
		
		for(long i = 0; i < tope; i++){
			String objJson = jedis.lindex(carrito, i);
			Ingreso ingreso = gson.fromJson(objJson, Ingreso.class);
			if (ingreso.getNombre_producto().equals(nombre_producto)) {
				ingreso.setCantidad(cantidad);
				String objJsonUpdt = gson.toJson(ingreso);
				jedis.lset(carrito, i, objJsonUpdt);
				break;
			}
		}
		
		jedis.close();
	}
	
	public void undo(String usuario) {
		Jedis jedis = ConexionRedis.getInstancia().getJedis().getResource();

		String carrito = usuario+"Carrito";
		
		jedis.rpop(carrito);
		
		jedis.close();
	}
	
	public ArrayList<Ingreso> getCarrito(String usuario) {
		ArrayList<Ingreso> lista = new ArrayList<Ingreso>();
 		Jedis jedis = ConexionRedis.getInstancia().getJedis().getResource();
		
		String carrito = usuario+"Carrito";
		
		long tope = jedis.llen(carrito);
		Gson gson = new Gson();
		
		for(long i = 0; i < tope; i++){
			String objJson = jedis.lindex(carrito, i);
			Ingreso ingreso = gson.fromJson(objJson, Ingreso.class);
			lista.add(ingreso);
		}
		
		jedis.close();
		return lista;
	}
	public void cerrarCarrito(String usuario) {
 		Jedis jedis = ConexionRedis.getInstancia().getJedis().getResource();
 		
		String carrito = usuario+"Carrito";
		
		jedis.del(carrito);
		
		jedis.close();
	}
}