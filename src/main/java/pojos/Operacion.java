package pojos;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import dao.OperacionesDAO;

public class Operacion {
	private int idOperacion;
	private int idFactura;
	private String medio;
	private String operador;
	private Date fecha;
	private int hora;
	private double monto;
	
	public Operacion(int idFact, String medioPago, String operadorIner,double montoTOT) {
		this.idOperacion = OperacionesDAO.getInstancia().darIdOperacion() + 1;
		this.idFactura = idFact;
		this.medio = medioPago;
		this.operador = operadorIner;
		this.fecha = java.sql.Date.valueOf(LocalDate.now());
		this.hora = LocalTime.now().getHour();
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
	public void setMonto(double monto) {
		this.monto = monto;
	}
	public int getHora() {
		return hora;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public Date getFecha() {
		return this.fecha;
	}
	public void setHora(int hora) {
		this.hora = hora;
	}

	
}
