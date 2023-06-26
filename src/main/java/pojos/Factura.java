package pojos;

import dao.FacturasDAO;

public class Factura {
	private int idFactura;
	private int DNIusuario;
	private String formaDePago;
	private double monto;
	
	public Factura() {
	
	}
	
	public Factura(int DNI, String forma, double montoTot) {
		this.idFactura = FacturasDAO.getInstancia().darIdFact() + 1;
		this.DNIusuario = DNI;
		this.formaDePago = forma;
		this.monto = montoTot;
	}
	
	public int getIdFactura() {		// HAY QUE TENER EL ID DE LA ULTIMA FACTURA 
		return idFactura;
	}
	public int getDNIusuario() {
		return DNIusuario;
	}
	public String getFormaDePago() {
		return formaDePago;
	}
	public double getMonto() {
		return monto;
	}
	public void setIdFactura(int idFactura) {
		this.idFactura = idFactura;
	}
	public void setDNIusuario(int dNIusuario) {
		DNIusuario = dNIusuario;
	}
	public void setFormaDePago(String formaDePago) {
		this.formaDePago = formaDePago;
	}
	public void setMonto(double monto) {
		this.monto = monto;
	}
	
	
}
