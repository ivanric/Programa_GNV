package models;

public class Solicitud {
	protected Integer idsolt;
	protected String numSolt;
	protected String fecha;
	protected String observaciones;
//	protected String placa;
	protected Integer aprobadoSiNo;
	protected String login;
	protected Integer estado;
	protected Vehiculo vehiculo;

	
	public Solicitud() {

	}
	public Solicitud(Integer idsolt, String numSolt, String fecha, String observaciones, Integer aprobadoSiNo,
			String login, Integer estado, Vehiculo vehiculo) {
		super();
		this.idsolt = idsolt;
		this.numSolt = numSolt;
		this.fecha = fecha;
		this.observaciones = observaciones;
		this.aprobadoSiNo = aprobadoSiNo;
		this.login = login;
		this.estado = estado;
		this.vehiculo = vehiculo;
	}
	public Integer getIdsolt() {
		return idsolt;
	}
	public void setIdsolt(Integer idsolt) {
		this.idsolt = idsolt;
	}
	public String getNumSolt() {
		return numSolt;
	}
	public void setNumSolt(String numSolt) {
		this.numSolt = numSolt;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public Integer getAprobadoSiNo() {
		return aprobadoSiNo;
	}
	public void setAprobadoSiNo(Integer aprobadoSiNo) {
		this.aprobadoSiNo = aprobadoSiNo;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public Integer getEstado() {
		return estado;
	}
	public void setEstado(Integer estado) {
		this.estado = estado;
	}
	public Vehiculo getVehiculo() {
		return vehiculo;
	}
	public void setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}
	@Override
	public String toString() {
		return "Solicitud [idsolt=" + idsolt + ", numSolt=" + numSolt + ", fecha=" + fecha + ", observaciones="
				+ observaciones + ", aprobadoSiNo=" + aprobadoSiNo + ", login=" + login + ", estado=" + estado
				+ ", vehiculo=" + vehiculo + "]";
	}
	
	
}
