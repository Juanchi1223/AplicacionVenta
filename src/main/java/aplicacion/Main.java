package aplicacion;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

import org.bson.Document;

import java.time.*; 


import conexiones.ConexionMySQL;
import dao.*;
import pojos.*;

public class Main {

	public static void main(String[] args) {
		
		System.out.println("---- TIENDA ONLINE ----");
	
		UsuarioActual usuario = ingresarUsuario(); 
		
		if(usuario.getNombreUs().equalsIgnoreCase("admin")) {
			// PROGRAMA MODO ADMINISTRADOR
			System.out.println("--- ENTRO A CAMBIAR EL CATALOGO ---");
			cambiarCatalogo();
		}
		else {
			// PROGRAMA MODO US FINAL
			Scanner input = new Scanner(System.in);
			int x;
			do {
				System.out.println("Ingresar (1) para manejar el carrito");
				System.out.println("Ingresar (2) para pagar facturas viejas");
				System.out.println("Ingresar (0) para cerrar el programa");
				x = input.nextInt();
				switch (x){
					case 1:
						carrito(usuario);
						break;
					case 2:
						pagarFacturas(usuario);
						break;
					case 0:
						break;
					default:
						System.out.println("No es ninguna de las opciones");
						break;
				}
			}
			while (x != 0);
		}
		guardarTiempo(usuario);
		
		System.out.println("Termino la ejecucion");
	}

	private static void cambiarCatalogo() {
		Scanner input = new Scanner(System.in);
		String prod;
		
		System.out.println("Quien esta realizando los cambios: ");
		String operador = input.nextLine();
		
		do
		{
			System.out.println("Ingresar el producto q se busca cambiar");
			prod = input.nextLine();
		}
		while(!CatalogoDAO.getInstancia().isProducto(prod));
		
		
		String character;
		do
		{
			System.out.println("Ingresar (D) para cambiar la descripcion ");
			System.out.println("Ingresar (F) para cambiar las fotos");
			System.out.println("Ingresar (C) para cambiar las comentarios");
			System.out.println("Ingresar (V) para cambiar los videos");
			System.out.println("Ingersar (P) para cambiar el precio");
		
			character = input.nextLine();
			
			if(character.equalsIgnoreCase("D")) {
				cambiarDescripcion(prod, operador);
			}
			else if(character.equalsIgnoreCase("F")) {
				cambiarFotos(prod, operador);
			}
			else if(character.equalsIgnoreCase("C")) {	
				cambiarComentarios(prod, operador);
			}
			else if(character.equalsIgnoreCase("V")) {
				cambiarVideos(prod, operador);
			}
			else if(character.equalsIgnoreCase("P")) {
				cambiarPrecio(prod, operador);
			}
		}
		while(condicionCaracter(character));
		
	}

	private static void cambiarPrecio(String prod, String operador) {
			Scanner input = new Scanner(System.in);
			System.out.println("Ingresar el nuevo precio: ");
			double precio = input.nextDouble();
			double precioViejo = CatalogoDAO.getInstancia().cambiarPrecio(prod, precio);
			
			Document doc = new Document();
			doc.append("producto", prod);
			doc.append("precio nuevo", precio);
			doc.append("precio viejo", precioViejo);
			doc.append("operador", operador);
			
			CambiosDAO.getInstancia().guardarCambio(doc);
	}

	private static void cambiarVideos(String prod, String operador) {
		Scanner input = new Scanner(System.in);
		String x;
		do		// TODO EXTRAER
		{
			System.out.println("Insertar (A) para agregar");
			System.out.println("Insertar (D) para sacar");	
			x = input.nextLine();
		}
		while(!x.equalsIgnoreCase("A") && !x.equalsIgnoreCase("D"));
		
		if(x.equalsIgnoreCase("A")) 
		{
			System.out.println("Ingresar el video en URL: ");
			String video = input.nextLine();
			
			ArrayList<String> valorViejo = CatalogoDAO.getInstancia().agregarVideos(prod, video);
			ArrayList<String> valorNuevo;
			
			if(valorViejo != null) {
				valorNuevo = new ArrayList<String>(valorViejo);
			}
			else {
				valorNuevo = new ArrayList<String>();
			}
			
			valorNuevo.add(video);
			
			Document doc = new Document();
			doc.append("producto", prod);
			doc.append("fotosNuevas", valorNuevo);
			doc.append("fotosViejas", valorViejo);
			doc.append("operador", operador);
			
			CambiosDAO.getInstancia().guardarCambio(doc);
		}
		else
		{
			System.out.println("Ingresar el video en URL a sacar: ");
			String video = input.nextLine();
			
			ArrayList<String> valorViejo = CatalogoDAO.getInstancia().sacarVideos(prod, video);
			ArrayList<String> valorNuevo;
			
			if(valorViejo != null) {
				valorNuevo = new ArrayList<String>(valorViejo);
			}
			else {
				valorNuevo = new ArrayList<String>();
			}
			
			valorNuevo.remove(video);
			
			Document doc = new Document();
			doc.append("producto", prod);
			doc.append("fotosNuevas", valorNuevo);
			doc.append("fotosViejas", valorViejo);
			doc.append("operador", operador);
			
			CambiosDAO.getInstancia().guardarCambio(doc);
		}
	}

	private static void cambiarComentarios(String prod, String operador) {	// TODO VER EL PROBLEMA DEL REGISTRO, Debugger!!
		Scanner input = new Scanner(System.in);
		String x;
		do
		{
			System.out.println("Insertar (A) para agregar");
			System.out.println("Insertar (D) para sacar");	
			x = input.nextLine();
		}
		while(!x.equalsIgnoreCase("A") && !x.equalsIgnoreCase("D"));
		if(x.equalsIgnoreCase("A")) {
			
			System.out.println("Ingresar el comentarios: ");
			String comentario = input.nextLine();
			
			ArrayList<String> valorViejo = CatalogoDAO.getInstancia().agregarComentario(prod, comentario);
			ArrayList<String> valorNuevo;
			
			if(valorViejo != null) {	
				valorNuevo = new ArrayList<String>(valorViejo);
			}
			else{
				valorNuevo = new ArrayList<String>();
			}
			
			valorNuevo.add(comentario);
			
			Document doc = new Document();
			doc.append("producto", prod);
			doc.append("comentariosNuevos", valorNuevo);
			doc.append("comentariosViejos", valorViejo);
			doc.append("operador", operador);
			
			CambiosDAO.getInstancia().guardarCambio(doc);
		}
		else {
			System.out.println("Ingresar el comentario a sacar: ");
			String comentario = input.nextLine();
			
			ArrayList<String> valorViejo = CatalogoDAO.getInstancia().sacarComentario(prod, comentario);
			ArrayList<String> valorNuevo;
			if (valorViejo != null) {
				valorNuevo = new ArrayList<String>(valorViejo);
			}else {
				valorNuevo = new ArrayList<String>();
			}
			
			valorNuevo.remove(comentario);
			
			Document doc = new Document();		// esta funcion se puede extraer para ahora espacio
			doc.append("producto", prod);
			doc.append("comentariosNuevos", valorNuevo);
			doc.append("comentariosViejos", valorViejo);
			doc.append("operador", operador);
			
			CambiosDAO.getInstancia().guardarCambio(doc);
		}
	}

	private static void cambiarFotos(String prod, String operador) {
		Scanner input = new Scanner(System.in);
		String x;
		do 
		{
			System.out.println("Insertar (A) para agrager una foto");
			System.out.println("Insertar (D) para sacar una foto");	
			x = input.nextLine();
		}
		while(!x.equalsIgnoreCase("A") && !x.equalsIgnoreCase("D"));
		
		if(x.equalsIgnoreCase("A")) { // TODO corroborar que la foto este en la lista
			System.out.println("Ingresar la foto como url: ");
			String foto = input.nextLine();
			
			ArrayList<String> fotosViejas = CatalogoDAO.getInstancia().agregarFoto(prod, foto);
			ArrayList<String> fotosNuevos;
			
			if(fotosViejas != null) {
				fotosNuevos = new ArrayList<String>(fotosViejas);
			}
			else {
				fotosNuevos = new ArrayList<String>();
			}
			
			fotosNuevos.add(foto);
			
			Document doc = new Document();
			doc.append("producto", prod);
			doc.append("fotosNuevas", fotosNuevos);
			doc.append("fotosViejas", fotosViejas);
			doc.append("operador", operador);
			
			CambiosDAO.getInstancia().guardarCambio(doc);
		}
		else {	 
			System.out.println("Ingresar la foto como url a sacar: ");
			String foto = input.nextLine();
			
			ArrayList<String> fotosViejas = CatalogoDAO.getInstancia().sacarFoto(prod, foto);
			ArrayList<String> fotosNuevos; 
			if(fotosViejas != null) {
				fotosNuevos = new ArrayList<String>(fotosViejas);
			}
			else {
				fotosNuevos = new ArrayList<String>();
			};
			fotosNuevos.remove(foto);
			
			Document doc = new Document();
			doc.append("producto", prod);
			doc.append("fotosNuevas", fotosNuevos);
			doc.append("fotosViejas", fotosViejas);
			doc.append("operador", operador);
			
			CambiosDAO.getInstancia().guardarCambio(doc);
		}
		
	}

	private static void cambiarDescripcion(String prod, String op) {
		Scanner input = new Scanner(System.in);
		System.out.println("Ingresar la nueva descripcion: ");
		String desc = input.nextLine();
		String descVieja = CatalogoDAO.getInstancia().cambiarDesc(prod, desc);
		
		Document doc = new Document();
		doc.append("producto", prod);
		doc.append("descripcion nueva", desc);
		doc.append("descripcion vieja", descVieja);
		doc.append("operador", op);
		
		CambiosDAO.getInstancia().guardarCambio(doc);
	}

	private static boolean condicionCaracter(String character) {
		if(!character.equalsIgnoreCase("D") && !character.equalsIgnoreCase("F") && !character.equalsIgnoreCase("C") && !character.equalsIgnoreCase("V") && !character.equalsIgnoreCase("P"))
			return false;
		return true;
	}

	private static void guardarTiempo(UsuarioActual usuario) {
		usuario.setHoraFin(LocalTime.now());
		CategoriasDAO.getInstancia().guardarTiempo(usuario);
		
		CategoriasDAO.getInstancia().getCategoria(usuario.getNombreUs());
		
	}

	private static void pagarFacturas(UsuarioActual usuario) {
		Scanner input = new Scanner(System.in);
		// MOSTRAR LAS FACTURAS DEL DNI DEL USUARIO
		ArrayList<Factura> facturas = FacturasDAO.getInstancia().facturasXUs(usuario.getDocumento());
		ArrayList<Factura> facturasPagadas = new ArrayList<Factura>();
		
		for (Factura i : facturas) {
			// ELIMINAR FACTURAS PAGADAS
			if(OperacionesDAO.getInstancia().verificarPagado(i.getIdFactura()))
				facturasPagadas.add(i);
		}
		for (Factura i : facturasPagadas)
		{
			if(facturas.indexOf(i) != -1)
				facturas.remove(i);
		}
		if(!facturas.isEmpty())
		{
		System.out.println("| idFactura |   DNI  | medio de pago | total |");
		for (Factura i: facturas) {
			
			System.out.println(String.format("|%11d|%d|%15s|%7f", i.getIdFactura(), i.getDNIusuario(), i.getFormaDePago(), i.getMonto()));
		}
		boolean flag = true;
		int x;
		do {	// verificar que la fact exita
			System.out.println("Ingresar el id de Factura que se quiera pagar");
			x = input.nextInt();
			if (verificarFactura(x, facturas)){
				flag = false;
			}
		}
		while(flag);
		
		pagar(FacturasDAO.getInstancia().buscarFactura(x));	
		}
		else {
			System.out.println("YA ESTAN PAGAS TODAS LAS FACTURAS");
		}
		
	}

	private static boolean verificarFactura(int x, ArrayList<Factura> facturas) {
		for (Factura i: facturas) {
			if (i.getIdFactura() == x)
				return true;
		}
		return false;
	}

	private static void pagar(Factura factura) {
		Scanner input = new Scanner(System.in);
		System.out.println("Quien era el operador(Enter si no hubo): ");
		String op = input.nextLine(); 
		if(op.isEmpty()) {
			op = null;
		}
		
		Operacion aux = new Operacion(factura.getIdFactura(), factura.getFormaDePago(), op,factura.getMonto());
		OperacionesDAO.getInstancia().pagar(aux);
	}

	private static void pasarAFacturas(UsuarioActual usuario, double monto) {
		Scanner input = new Scanner(System.in);
		String forma;
		
		System.out.println("Medio de pago a usar: ");
		forma = input.nextLine();
		
		Factura aux = new Factura(usuario.getDocumento(), forma, monto);

		FacturasDAO.getInstancia().guardarFactura(aux);
	}

	private static void hacerPedido(UsuarioActual usuario) { // cambiar parametro a usuario actual
		// crear el pedido, buscar el monto de redis, ingresar el pedido a mongo
		Scanner input = new Scanner(System.in);
		Pedido aux = new Pedido();
				
		System.out.println("Ingresa tu nombre de pila: ");
		String nombre = input.nextLine();
		aux.setNombre(nombre); 
		
		System.out.println("Ingresa tu apellido: ");
		String apellido = input.nextLine();
		aux.setApellido(apellido);
		
		System.out.println("Condicion ante el IVA: ");
		String condicion = condicionString();
		aux.setIva(condicion);
		
		aux.setDireccion(usuario.getDireccion());
		
		double monto = getMonto(usuario.getNombreUs());
		aux.setMonto(monto);
		
		System.out.println("Ingresa la cantidad de descuento aplicado: ");
		double descuento = input.nextDouble();
		aux.setDescuento(descuento);
		
		System.out.println(" Ingresa la cantidad de impuestos aplicados: ");
		double impuesto = input.nextDouble();
		aux.setImpuestos(impuesto);
		
		ArrayList<Document> pedido = parseDoc(CarritoDAO.getInstancia().getCarrito(usuario.getNombreUs()));
		aux.setCarrito(pedido);
		
		System.out.println("Se realizo el pedido");
		
		PedidosDAO.getInstancia().agregarPedido(aux);
		CarritoDAO.getInstancia().cerrarCarrito(usuario.getNombreUs());
		
		double montoNeto = monto - descuento + impuesto;
		pasarAFacturas(usuario, montoNeto);
	}
	private static ArrayList<Document> parseDoc(ArrayList<Ingreso> carrito){
		ArrayList<Document> pedidoProd = new ArrayList<Document>();
		for(Ingreso i : carrito) {
			Document aux = new Document();
			aux.append("producto", i.getNombre_producto());
			aux.append("cantidad", i.getCantidad());
			pedidoProd.add(aux);		
		}
		return pedidoProd;
	}

	private static double getMonto(String nombreUs) {
		double monto = 0;
		
		ArrayList<Ingreso> carrito = CarritoDAO.getInstancia().getCarrito(nombreUs);
		
		for(Ingreso i: carrito){
			monto += CatalogoDAO.getInstancia().precio(i);
		}
		
		return monto;
	}

	private static String condicionString() {
		Scanner input = new Scanner(System.in);
		boolean flag = false;
		
		String x;
		do {
			System.out.println("Ingresar C para Consumidor Final");
			System.out.println("Ingresar R para Responsable Inscripto");
			System.out.println("Ingresar S para Sujeto Exento");
			x = input.nextLine();
			if(x.equalsIgnoreCase("C")) {
				return "Consumidor Final";
			}
			else if(x.equalsIgnoreCase("R")) {
				return "Responsable Inscripto";
			}
			else if(x.equalsIgnoreCase("S")) {
				return "Sujeto Exento";
			}
			else {
				flag = true;
			}
		}while(flag);
		return null;
	}
	private static void carrito(UsuarioActual usuario) {
		Scanner input = new Scanner(System.in);
		int x;
		String nusuario = usuario.getNombreUs();
		
		do {
			CatalogoDAO.getInstancia().buscarCatalogo();
			mostrarCarrito(nusuario);
			opcionesCarrito();
			System.out.println("Que opcion queres usar: ");

			x = input.nextInt();
			
			switch (x){
				case 1:
					agregarProducto(nusuario);
					break;
				case 2:
					sacarProducto(nusuario);
					break;
				case 3:
					cambiarProducto(nusuario);
					break;
				case 4:
					estadoAnterior(nusuario);
					break;
				case 5:
					hacerPedido(usuario);
					// borrarCarrito();
					break;
				default: 
					System.out.println("NO ES NINGUNA OPCION");
			}
		}
		while (x != 5);		
	}



	private static void estadoAnterior(String usuario) {
		CarritoDAO.getInstancia().undo(usuario);
	}

	private static void mostrarCarrito(String usuario) {
		ArrayList<Ingreso> carritoAct = CarritoDAO.getInstancia().getCarrito(usuario);
		
		System.out.println();
		System.out.println("-----   CARRITO   -----");
		System.out.println("| producto | cantidad |");
		for(Ingreso i: carritoAct) {
			System.out.println(String.format("|%10s|%10s|", i.getNombre_producto(), Integer.toString(i.getCantidad())));
		}
		System.out.println("-----------------------");
		
	}

	private static void cambiarProducto(String usuario) {
		String producto;
		int cantidad;
		Scanner input = new Scanner(System.in);
		
		System.out.println("Que producto queres cambiar: ");
		producto = input.nextLine();
		
		System.out.println("A que cantidad: ");
		cantidad = Integer.parseInt(input.nextLine());
		
		CarritoDAO.getInstancia().cambiarCarrito(cantidad, usuario, producto);
		
	}

	private static void sacarProducto(String usuario) {
		String producto;
		Scanner input = new Scanner(System.in);
		
		System.out.println("Que producto queres sacar del carrito");
		producto = input.nextLine();
		
		CarritoDAO.getInstancia().eliminarCarrito(usuario, producto);
	}

	private static void agregarProducto(String usuario) {
		String producto;
		int cantidad;
		
		Scanner input = new Scanner(System.in);
		
		do 
		{
			System.out.println("Que producto queres ingresar: ");
			producto = input.nextLine();
		}
		while(!CatalogoDAO.getInstancia().isProducto(producto));
		
		
		System.out.println("Cantidad: ");
		cantidad = Integer.parseInt(input.nextLine());
		
		Ingreso aux = new Ingreso(producto, cantidad);
		CarritoDAO.getInstancia().agregarCarrito(aux, usuario);
		
	}

	private static void opcionesCarrito() { // agregar, eliminiar, cantidad o terminar (x) 		
		System.out.println("Ingresa una de la siguentes opciones");
		System.out.println("Ingresa (1) para agregar un nuevo producto");
		System.out.println("Ingresa (2) para sacar un producto");
		System.out.println("Ingresa (3) para cambiar la cantidad de un producto ingresado");
		System.out.println("Ingresa (4) para volver a un estado anterior");
		System.out.println("Ingresa (5) para cerrar el carrito");
	}

	private static UsuarioActual ingresarUsuario() {
		String user;
		String contra;
		Scanner input = new Scanner(System.in);
		
		do {	// SI O SI HAY Q INGRESAR ALGO
			System.out.print("Ingresar Usuario: ");
			user = input.nextLine();
		}
		while (!UsuarioDAO.getInstancia().verificarUser(user)); 

		System.out.println("Usuario Encontrado");

		do {	// SI O SI HAY Q SABER LA CONTRASEÑA		
			System.out.print("Ingresar Contaseña:");
			contra = input.nextLine();
		}
		while(!UsuarioDAO.getInstancia().verificarCC(contra, user));
		
		System.out.println();
		System.out.println("----  BIENVENIDO " + user + " ----");
		
		return UsuarioDAO.getInstancia().guardarDatos(user);
	}
}
