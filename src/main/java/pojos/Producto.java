package pojos;

public class Producto {
	private String nombre_prod;
	private String descripcion;
	private String fotos;
	private String commentarios;
	private String videos;
	private double precio;
	
	
	public String getNombre_prod() {
		return nombre_prod;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public String getFotos() {
		return fotos;
	}
	public String getCommentarios() {
		return commentarios;
	}
	public String getVideos() {
		return videos;
	}
	public double getPrecio() {
		return precio;
	}
	public void setNombre_prod(String nombre_prod) {
		this.nombre_prod = nombre_prod;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public void setFotos(String fotos) {
		this.fotos = fotos;
	}
	public void setCommentarios(String commentarios) {
		this.commentarios = commentarios;
	}
	public void setVideos(String videos) {
		this.videos = videos;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	
}
