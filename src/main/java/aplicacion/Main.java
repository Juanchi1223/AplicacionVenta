package aplicacion;

import java.util.ArrayList;
import java.util.Scanner;

import dao.*;
import pojos.*;

public class Main {

	public static void main(String[] args) {
		/*
		 * 	ACA SE DESARROLLA EL CODIGO: 
		 * 
		 * 	PRIMERO VA A ESTAR EL INICIO DE SECION -> LUEGO GUARDAMOS ToDO EN UN OBJETO USUARIO (LISTO)
		 * 	
		 * 	DOS USUARIOS: COMPRADOR Y ADMINISTRADOR (ARRANCAMOS CON USUARIO)
		 *
		 * 	DESPUES VAN A HABER 4 OPCIONES:
		 * 			- CARRITO
		 * 				- VER EL CATALOGO (HAGAMOS UNA TABLA)
		 *				- HACER PEDIDO
		 * 			- FACTURAR
		 * 				- METODO DE PAGO
		 * 				+ VER LAS FACTURAS
		 * 			+ ACTUALIZAR CATALOGO
		 * 				+ SE AGREGA EL LOG 	 
		 * 
		*/
		
		
		System.out.println("---- TIENDA ONLINE ----");
	
		//UsuarioActual usuario = ingresarUsuario(); 
	
		//carrito(usuario.getNombreUs()); // ACA ES DONDE SE VA A CAMBIAR ENTRE ADMIN O US FINAL
	
		hacerPedido("Arenales 2057", "rgodio");
		
		System.out.println("Termino la ejecucion");
	}

	private static void hacerPedido(String direccion, String nombreUs ) { // cambiar parametro a usuario actual
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
		boolean condicion = condicionStringBool();
		aux.setIva(condicion);
		
		aux.setDireccion(direccion);
		
		double monto = getMonto(nombreUs);
		aux.setMonto(monto);
		
		System.out.println("Ingresa la cantidad de descuento aplicado: ");
		double descuento = input.nextDouble();
		aux.setDescuento(descuento);
		
		System.out.println(" Ingresa la cantidad de impuestos aplicados: ");
		double impuesto = input.nextDouble();
		aux.setImpuestos(impuesto);
		
		PedidosDAO.getInstancia().agregarPedido(aux);
	}

	private static double getMonto(String nombreUs) {
		double monto = 0;
		
		ArrayList<Ingreso> carrito = CarritoDAO.getInstancia().getCarrito(nombreUs);
		
		for(Ingreso i: carrito){
			monto += CatalogoDAO.getInstancia().precio(i);
		}
		
		return monto;
	}

	private static boolean condicionStringBool() {
		Scanner input = new Scanner(System.in);
		int x;
		do {
			System.out.println("Ingresa 0(True) o 1(False) para la condicion ante el iva");
			x = input.nextInt();
		}
		while(x != 0 && x != 1); 
		
		if(x == 0)
			return true;
		
		return false;
	}

	private static void carrito(String usuario) {
		Scanner input = new Scanner(System.in);
		int x;
		
		do {
			CatalogoDAO.getInstancia().buscarCatalogo();
			opcionesCarrito();
			System.out.println("Que opcion queres usar: ");

			x = input.nextInt();
			
			switch (x){
				case 1:
					agregarProducto(usuario);
					break;
				case 2:
					sacarProducto(usuario);
					break;
				case 3:
					cambiarProducto(usuario);
					break;
				case 4:
					terminar();
					break;
				default: 
					System.out.println("NO ES NINGUNA OPCION");
			}
		}
		while (x != 4);		
	}



	private static void terminar() {		
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
		System.out.println("Ingresa (4) para cerrar el carrito y hacer el pedido");
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
