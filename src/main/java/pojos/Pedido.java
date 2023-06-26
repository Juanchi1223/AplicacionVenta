package pojos;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

public class Pedido {
	private int idPedido;
	private String nombre;
	private String apellido;
	private String direccion;
	private String iva;
	private double monto;
	private double descuento;
	private double impuestos;
	private List<Document> carrito = new ArrayList<Document>();
	
	
	public String getNombre() {
		return nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public String getDireccion() {
		return direccion;
	}
	public String getIva() {
		return iva;
	}
	public double getMonto() {
		return monto;
	}
	public double getDescuento() {
		return descuento;
	}
	public double getImpuestos() {
		return impuestos;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public void setIva(String iva) {
		this.iva = iva;
	}
	public void setMonto(double monto) {
		this.monto = monto;
	}
	public void setDescuento(double descuento) {
		this.descuento = descuento;
	}
	public void setImpuestos(double impuestos) {
		this.impuestos = impuestos;
	}
	public List<Document> getCarrito() {
		return carrito;
	}
	public void setCarrito(List<Document> carrito) {
		this.carrito = carrito;
	}
	public void setCarrito(ArrayList<Ingreso> carrito){
		this.carrito = new ArrayList<Document>();	
		for(Ingreso i : carrito) {
			Document aux = new Document();
			aux.append("producto", i.getNombre_producto());
			aux.append("cantidad", i.getCantidad());
			this.carrito.add(aux);
		}
	}
	public int getIdPedido() {
		return idPedido;
	}
	public void setIdPedido(int idPedido) {
		this.idPedido = idPedido;
	}
}
