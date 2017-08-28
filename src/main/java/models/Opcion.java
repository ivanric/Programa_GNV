package models;

public class Opcion {
	private Integer idopc;
	private String nombre;
	private String icono;
	private String enlace;
	private String clase;
	private Integer estado;
	public Opcion() {
//		super();
		// TODO Auto-generated constructor stub
	}
	public Opcion(Integer idopc, String nombre, String icono, String enlace, String clase, Integer estado) {
//		super();
		this.idopc = idopc;
		this.nombre = nombre;
		this.icono = icono;
		this.enlace = enlace;
		this.clase = clase;
		this.estado = estado;
	}
	public Integer getIdopc() {
		return idopc;
	}
	public void setIdopc(Integer idopc) {
		this.idopc = idopc;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getIcono() {
		return icono;
	}
	public void setIcono(String icono) {
		this.icono = icono;
	}
	public String getEnlace() {
		return enlace;
	}
	public void setEnlace(String enlace) {
		this.enlace = enlace;
	}
	public String getClase() {
		return clase;
	}
	public void setClase(String clase) {
		this.clase = clase;
	}
	public Integer getEstado() {
		return estado;
	}
	public void setEstado(Integer estado) {
		this.estado = estado;
	}
	@Override
	public String toString() {
		return "Opcion [idopc=" + idopc + ", nombre=" + nombre + ", icono=" + icono + ", enlace=" + enlace + ", clase="
				+ clase + ", estado=" + estado + "]";
	}
	
	
	
}
