package dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;
import static com.mongodb.client.model.Updates.push;
import static com.mongodb.client.model.Updates.pull;


import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.util.ArrayList;

import conexiones.ConexionMongo;
import pojos.Producto;
import pojos.Ingreso;


public class CatalogoDAO {
	private static CatalogoDAO instancia;
	
	public static CatalogoDAO getInstancia() {
		if (instancia == null)
			instancia = new CatalogoDAO();
		return instancia;
	} 
	
	public void buscarCatalogo() {
		CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
	    CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
		
	    MongoDatabase database = ConexionMongo.getInstancia().getCliente().getDatabase("aplicacion").withCodecRegistry(pojoCodecRegistry);
		MongoCollection<Producto> colecion = database.getCollection("catalogo", Producto.class); 
		
		System.out.println();
		System.out.println("------- CATALOGO --------");
		
		System.out.println(" nombre producto | precio");
		System.out.println("-------------------------");
		
		colecion.find().forEach(doc -> System.out.println(String.format("%9s", doc.getNombre_prod())+ String.format("%8s", " ") + "| " +  doc.getPrecio()));  
		
		System.out.println("-------------------------");
		System.out.println();
	}
	
	public double precio(Ingreso ingreso) {
		CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
	    CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
		
	    MongoDatabase database = ConexionMongo.getInstancia().getCliente().getDatabase("aplicacion").withCodecRegistry(pojoCodecRegistry);
		MongoCollection<Producto> colecion = database.getCollection("catalogo", Producto.class); 
		
		Producto prodcuto = colecion.find(eq("nombre_prod", ingreso.getNombre_producto())).first();

		return prodcuto.getPrecio() * ingreso.getCantidad();
	}
	
	public boolean isProducto(String nombreProd) {
		boolean flag = false;
		CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
	    CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
		
	    MongoDatabase database = ConexionMongo.getInstancia().getCliente().getDatabase("aplicacion").withCodecRegistry(pojoCodecRegistry);
		MongoCollection<Producto> colecion = database.getCollection("catalogo", Producto.class); 
		
		Producto producto = colecion.find(eq("nombre_prod", nombreProd)).first();
		if(producto != null) {
			flag = true;
		}
		
		return flag; 
	}
	
	public String cambiarDesc(String producto, String desc) {
		CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
	    CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
		
	    MongoDatabase database = ConexionMongo.getInstancia().getCliente().getDatabase("aplicacion").withCodecRegistry(pojoCodecRegistry);
		MongoCollection<Producto> colecion = database.getCollection("catalogo", Producto.class); 
		
		Producto aux = colecion.find(eq("nombre_prod", producto)).first();
		String retorno = aux.getDescripcion();
		colecion.updateOne(eq("nombre_prod", producto), set("descripcion", desc));
		System.out.println("Se cambio la descripcion de " + producto);
		return retorno;
	}
	// MODIFICAR FOTOS
	public ArrayList<String> agregarFoto(String producto, String foto){ 	
		CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
	    CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
		
	    MongoDatabase database = ConexionMongo.getInstancia().getCliente().getDatabase("aplicacion").withCodecRegistry(pojoCodecRegistry);
		MongoCollection<Producto> colecion = database.getCollection("catalogo", Producto.class); 
		
		Producto aux = colecion.find(eq("nombre_prod", producto)).first();
		ArrayList<String> retorno = aux.getFotos();
		
		colecion.updateOne(eq("nombre_prod", producto), push("fotos", foto));
		return retorno;
	}
	public ArrayList<String> sacarFoto(String producto, String foto){ 	
		CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
	    CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
		
	    MongoDatabase database = ConexionMongo.getInstancia().getCliente().getDatabase("aplicacion").withCodecRegistry(pojoCodecRegistry);
		MongoCollection<Producto> colecion = database.getCollection("catalogo", Producto.class); 
		
		Producto aux = colecion.find(eq("nombre_prod", producto)).first();
		ArrayList<String> retorno = aux.getFotos();
		
		colecion.updateOne(eq("nombre_prod", producto), pull("fotos", foto));
		return retorno;
	}
	
	// MODIFICAR COMENTARIOS
	public ArrayList<String> agregarComentario(String producto, String comentario){ 
		CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
	    CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
		
	    MongoDatabase database = ConexionMongo.getInstancia().getCliente().getDatabase("aplicacion").withCodecRegistry(pojoCodecRegistry);
		MongoCollection<Producto> colecion = database.getCollection("catalogo", Producto.class); 
		
		Producto aux = colecion.find(eq("nombre_prod", producto)).first();
		ArrayList<String> retorno = aux.getComentarios();
		
		colecion.updateOne(eq("nombre_prod", producto), push("comentarios", comentario));
		return retorno;
	}
	public ArrayList<String> sacarComentario(String producto, String comentario){ 	
		CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
	    CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
		
	    MongoDatabase database = ConexionMongo.getInstancia().getCliente().getDatabase("aplicacion").withCodecRegistry(pojoCodecRegistry);
		MongoCollection<Producto> colecion = database.getCollection("catalogo", Producto.class); 
		
		Producto aux = colecion.find(eq("nombre_prod", producto)).first();
		ArrayList<String> retorno = aux.getComentarios();
		
		colecion.updateOne(eq("nombre_prod", producto), pull("comentarios", comentario));
		return retorno;
	} 
	// MODIFICAR VIDEOS
	public ArrayList<String> agregarVideos(String producto, String videos){ 
		CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
	    CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
		
	    MongoDatabase database = ConexionMongo.getInstancia().getCliente().getDatabase("aplicacion").withCodecRegistry(pojoCodecRegistry);
		MongoCollection<Producto> colecion = database.getCollection("catalogo", Producto.class); 
		
		Producto aux = colecion.find(eq("nombre_prod", producto)).first();
		ArrayList<String> retorno = aux.getVideos();
		
		colecion.updateOne(eq("nombre_prod", producto), push("videos", videos));
		return retorno;
	}
	public ArrayList<String> sacarVideos(String producto, String videos){ 	
		CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
	    CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
		
	    MongoDatabase database = ConexionMongo.getInstancia().getCliente().getDatabase("aplicacion").withCodecRegistry(pojoCodecRegistry);
		MongoCollection<Producto> colecion = database.getCollection("catalogo", Producto.class); 
		
		Producto aux = colecion.find(eq("nombre_prod", producto)).first();
		ArrayList<String> retorno = aux.getVideos();
		
		colecion.updateOne(eq("nombre_prod", producto), pull("videos", videos));
		return retorno;
	} 
	// MODIFICAR PRECIO

	public double cambiarPrecio(String prod, double precio) {
		CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
	    CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
		
	    MongoDatabase database = ConexionMongo.getInstancia().getCliente().getDatabase("aplicacion").withCodecRegistry(pojoCodecRegistry);
		MongoCollection<Producto> colecion = database.getCollection("catalogo", Producto.class); 
		
		Producto aux = colecion.find(eq("nombre_prod", prod)).first();
		double retorno = aux.getPrecio();
		colecion.updateOne(eq("nombre_prod", prod), set("precio", precio));
		
		System.out.println("Se cambio el precio de " + prod);
		return retorno;
	}
	
	
}
