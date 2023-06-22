package pojos;

public class Ingreso {
	private String nombre_producto;
	private int cantidad;
	
	public Ingreso(String nombre, int cant){
		this.nombre_producto = nombre;
		this.cantidad = cant;
	}
	
	public String getNombre_producto() {
		return nombre_producto;
	}
	public int getCantidad() {
		return cantidad;
	}
	
	public void setNombre_producto(String nombre_producto) {
		this.nombre_producto = nombre_producto;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
}
