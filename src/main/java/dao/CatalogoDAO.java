package dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import static com.mongodb.client.model.Filters.eq;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

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
}
