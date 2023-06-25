package pojos;

public class Pedido {
	private String nombre;
	private String apellido;
	private String direccion;
	private boolean iva;
	private double monto;
	private double descuento;
	private double impuestos;
	
	
	public String getNombre() {
		return nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public String getDireccion() {
		return direccion;
	}
	public boolean isIva() {
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
	public void setIva(boolean iva) {
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

	
}