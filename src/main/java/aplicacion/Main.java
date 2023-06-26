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
		/* 
		 * 
		 * TO DO LIST:
		 * 	CARRITO 	->		- CORROBORAR Q LO INGRESADO SEA UN PRODUCTO
		 *						- CUANDO SE PASA AL PEDIDO VACIAR EL CARRITO 
		 *
		 * 	FACTURAR 	->		- VER LO DEL DATE
		 * 
		 * 	REGISTRO DE OP -> 	- VER LO DEL DATE
		 * 						- VER EL MINUTO
		 *						- METODO PARA PAGAR FACTURAS (SOLO PUEDE HABER UN IDFACTURA POR OPERACION)		
		 *
	 	 *	CATEGORIAS -> 		- BUSCAR CUANDO TERMINA LA EJECUCION
	 	 *						- SACAR DIFERENCIA Y GUARDAR EN REDIS
	 	 *	
	 	 *	INICIO DE SECION ->	- BASICAMENTE UNA DIFETENTE APLIACION PARA EL ADMIN
		 *						- METODO PARA CAMBIAR EL CATAGOLOGO
		 *						- REGISTRAR EL CAMBIO QUE HAGA SOBRE EL CATALOGO
		 *				 			
		 * 
		 */
		
		
		System.out.println("---- TIENDA ONLINE ----");
	
		//UsuarioActual usuario = ingresarUsuario(); 
	
		//carrito(usuario); // ACA ES DONDE SE VA A CAMBIAR ENTRE ADMIN O US FINAL

		//hacerPedido(usuario);			// TODO LUEGO DE HACER EL PEDIDO BORRAR EL CARRITO
		
		//pasarAFacturas();
		//FacturasDAO.getInstancia().pagar(new Operacion(1, 1, "efvo", "cajero", LocalDateTime.now(), 1500)); 
		
		pagar(FacturasDAO.getInstancia().buscarFactura(0));		// TODO conectar al flujo del programa
		
		System.out.println("Termino la ejecucion");
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

	private static void pasarAFacturas(UsuarioActual usuario, int idPedido) {
		Scanner input = new Scanner(System.in);
		String forma;
		
		System.out.println("Medio de pago a usar: ");
		forma = input.nextLine();
		
		Factura aux = new Factura(usuario.getDocumento(), forma, PedidosDAO.getInstancia().buscarMonto(idPedido));

		FacturasDAO.getInstancia().guardarFactura(aux);
		
	}

	private static void hacerPedido(UsuarioActual usuario) { // cambiar parametro a usuario actual
		// crear el pedido, buscar el monto de redis, ingresar el pedido a mongo
		Scanner input = new Scanner(System.in);
		Pedido aux = new Pedido();
		
		aux.setIdPedido(PedidosDAO.getInstancia().darId()+1);
		
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
		
		System.out.println(aux.getCarrito().toString());
		
		PedidosDAO.getInstancia().agregarPedido(aux);
		pasarAFacturas(usuario, aux.getIdPedido());
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
		
		System.out.println("Que producto queres ingresar: ");
		producto = input.nextLine();
		
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
