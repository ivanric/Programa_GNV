package models;

public class Beneficiario {
	protected  Integer idben;
	protected String institucion;
	protected Integer carnetPropiedad;
	protected Integer rua;
	protected Integer institucionPerteneciente;
	protected Integer pagoImpuestos;
	protected Integer licenciaConducir;
	protected Integer otrosDoc;
	protected Integer estado;
	protected Integer idper;
	public Beneficiario() {
//		super();
		// TODO Auto-generated constructor stub
	}
	public Beneficiario(Integer idben, String institucion, Integer carnetPropiedad, Integer rua,
			Integer institucionPerteneciente, Integer pagoImpuestos, Integer licenciaConducir, Integer otrosDoc,
			Integer estado, Integer idper) {
//		super();
		this.idben = idben;
		this.institucion = institucion;
		this.carnetPropiedad = carnetPropiedad;
		this.rua = rua;
		this.institucionPerteneciente = institucionPerteneciente;
		this.pagoImpuestos = pagoImpuestos;
		this.licenciaConducir = licenciaConducir;
		this.otrosDoc = otrosDoc;
		this.estado = estado;
		this.idper = idper;
	}
	public Integer getIdben() {
		return idben;
	}
	public void setIdben(Integer idben) {
		this.idben = idben;
	}
	public String getInstitucion() {
		return institucion;
	}
	public void setInstitucion(String institucion) {
		this.institucion = institucion;
	}
	public Integer getCarnetPropiedad() {
		return carnetPropiedad;
	}
	public void setCarnetPropiedad(Integer carnetPropiedad) {
		this.carnetPropiedad = carnetPropiedad;
	}
	public Integer getRua() {
		return rua;
	}
	public void setRua(Integer rua) {
		this.rua = rua;
	}
	public Integer getInstitucionPerteneciente() {
		return institucionPerteneciente;
	}
	public void setInstitucionPerteneciente(Integer institucionPerteneciente) {
		this.institucionPerteneciente = institucionPerteneciente;
	}
	public Integer getPagoImpuestos() {
		return pagoImpuestos;
	}
	public void setPagoImpuestos(Integer pagoImpuestos) {
		this.pagoImpuestos = pagoImpuestos;
	}
	public Integer getLicenciaConducir() {
		return licenciaConducir;
	}
	public void setLicenciaConducir(Integer licenciaConducir) {
		this.licenciaConducir = licenciaConducir;
	}
	public Integer getOtrosDoc() {
		return otrosDoc;
	}
	public void setOtrosDoc(Integer otrosDoc) {
		this.otrosDoc = otrosDoc;
	}
	public Integer getEstado() {
		return estado;
	}
	public void setEstado(Integer estado) {
		this.estado = estado;
	}
	public Integer getIdper() {
		return idper;
	}
	public void setIdper(Integer idper) {
		this.idper = idper;
	}
	@Override
	public String toString() {
		return "Beneficiario [idben=" + idben + ", institucion=" + institucion + ", carnetPropiedad=" + carnetPropiedad
				+ ", rua=" + rua + ", institucionPerteneciente=" + institucionPerteneciente + ", pagoImpuestos="
				+ pagoImpuestos + ", licenciaConducir=" + licenciaConducir + ", otrosDoc=" + otrosDoc + ", estado="
				+ estado + ", idper=" + idper + "]";
	}
	
	
}
