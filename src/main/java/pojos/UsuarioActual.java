package pojos;

import java.time.LocalTime;

public class UsuarioActual {
	private String nombreUs;
	private String contraUs;
	private String direccion;
	private int documento;
	private LocalTime horaIni;
	private LocalTime horaFin;
	
	public UsuarioActual() {
		this.horaIni =  LocalTime.now();
	}
	
	public String getNombreUs() {
		return nombreUs;
	}
	public String getContraUs() {
		return contraUs;
	}
	public String getDireccion() {
		return direccion;
	}
	public int getDocumento() {
		return documento;
	}
	public LocalTime getHoraIni() {
		return horaIni;
	}
	public LocalTime getHoraFin() {
		return horaFin;
	}
	public void setNombreUs(String nombreUs) {
		this.nombreUs = nombreUs;
	}
	public void setContraUs(String contraUs) {
		this.contraUs = contraUs;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public void setDocumento(int documento) {
		this.documento = documento;
	}
	public void setHoraIni(LocalTime horaIni) {
		this.horaIni = horaIni;
	}
	public void setHoraFin(LocalTime horaFin) {
		this.horaFin = horaFin;
	}

}
