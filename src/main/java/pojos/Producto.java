package pojos;

import java.util.ArrayList;

public class Producto {
	private String nombre_prod;
	private String descripcion;
	private ArrayList<String> fotos;
	private ArrayList<String> commentarios;
	private ArrayList<String> videos;
	private double precio;
	
	
	public String getNombre_prod() {
		return nombre_prod;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public ArrayList<String> getFotos() {
		return fotos;
	}
	public ArrayList<String> getCommentarios() {
		return commentarios;
	}
	public ArrayList<String> getVideos() {
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
	public void setFotos(ArrayList<String> fotos) {
		this.fotos = fotos;
	}
	public void setCommentarios(ArrayList<String> commentarios) {
		this.commentarios = commentarios;
	}
	public void setVideos(ArrayList<String> videos) {
		this.videos = videos;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	
}
