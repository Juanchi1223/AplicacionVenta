package pojos;

import java.sql.Date;
import java.time.LocalDateTime;

public class Operacion {
	private int idOperacion;
	private int idFactura;
	private String medio;
	private String operador;
	private LocalDateTime fecha;
	private double monto;
	
	public Operacion(int id, int idFact, String medioPago, String operadorIner, LocalDateTime fechaPago, double montoTOT) {
		this.idOperacion = id;
		this.idFactura = idFact;
		this.medio = medioPago;
		this.operador = operadorIner;
		this.fecha = fechaPago;
		this.monto = montoTOT;
	}
	public int getIdOperacion() {
		return idOperacion;
	}
	public int getIdFactura() {
		return idFactura;
	}
	public String getMedio() {
		return medio;
	}
	public String getOperador() {
		return operador;
	}
	public LocalDateTime getFecha() {
		return fecha;
	}
	public double getMonto() {
		return monto;
	}
	public void setIdOperacion(int idOperacion) {
		this.idOperacion = idOperacion;
	}
	public void setIdFactura(int idFactura) {
		this.idFactura = idFactura;
	}
	public void setMedio(String medio) {
		this.medio = medio;
	}
	public void setOperador(String operador) {
		this.operador = operador;
	}
	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}
	public void setMonto(double monto) {
		this.monto = monto;
	}

	
}
