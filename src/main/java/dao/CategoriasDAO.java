package dao;

import java.time.Duration;
import java.time.LocalTime;

import conexiones.ConexionRedis;
import pojos.UsuarioActual;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class CategoriasDAO {
	private static CategoriasDAO instancia;
	
	public static CategoriasDAO getInstancia() {
		if (instancia == null)
			instancia = new CategoriasDAO();
		return instancia;
	} 
	
	public void guardarTiempo(UsuarioActual usuario) {
        Duration duration = Duration.between(usuario.getHoraIni(), usuario.getHoraFin());
        
        long minutos = duration.toMinutes();
        String clave = "minutos"+usuario.getNombreUs();
        
        Jedis jedis = ConexionRedis.getInstancia().getJedis().getResource();
        jedis.incrBy(clave, minutos);
        jedis.close();
	}
	
	public void getCategoria(String nombre_us) {
		String clave = "minutos"+nombre_us;
		
		JedisPool jedisPool = ConexionRedis.getInstancia().getJedis();
		Jedis jedis = jedisPool.getResource();
        int x = Integer.parseInt(jedis.get(clave));
       
        jedis.close();
        jedisPool.close();
        
        
        if(x < 120) {
        	System.out.println("CATEGORIA LOW");
        }
        else if(x < 240) {
        	System.out.println("CATEGORIA MEDIUM");
        }
        else {
        	System.out.println("CATEGORIA TOP");
        }
	}
	public void cortarConex() {
		ConexionRedis.getInstancia().getJedis().close();
	}
}
