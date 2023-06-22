package aplicacion;

import java.util.Scanner;

import dao.*;
import pojos.Ingreso;
import pojos.UsuarioActual;

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
		 * 				SE AGREGA EL LOG 	 
		 * 
		*/
		
		
		System.out.println("---- TIENDA ONLINE ----");
	
		UsuarioActual usuario = ingresarUsuario(); 
		//carrito(); // ACA ES DONDE SE VA A CAMBIAR ENTRE ADMIN O US FINAL
		
		Ingreso aux = new Ingreso("mesa", 1);
		Ingreso aux2 = new Ingreso("silla", 3);
		CarritoDAO.getInstancia().agregarCarrito(aux, usuario.getNombreUs());
		CarritoDAO.getInstancia().agregarCarrito(aux2, usuario.getNombreUs());
		
		CarritoDAO.getInstancia().eliminarCarrito(usuario.getNombreUs(), "silla");
		
		System.out.println("Termino la ejecucion");
	}

	private static void carrito() {
		String x;
		do {
			CatalogoDAO.getInstancia().buscarCatalogo();
			x = opcionesCarrito(); // agregar, eliminiar, cantidad o terminar (x) 
		}
		while (!x.equalsIgnoreCase("x"));
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
		
		input.close();
		return UsuarioDAO.getInstancia().guardarDatos(user);
	}
}
