package models;

public class Persona {
	private Integer idper;
	private String ci;
	private String nombres;
	private String ap;
	private String am;
	private String genero;
	private String direccion;
	private String email;
	private String foto;
	private Integer estado;
	private Usuario usuario;
	private Beneficiario beneficiario;
	public Persona() {
//		super();
		// TODO Auto-generated constructor stub
	}
	public Persona(Integer idper, String ci, String nombres, String ap, String am, String genero, String direccion,
			String email, String foto, Integer estado, Usuario usuario, Beneficiario beneficiario) {
		super();
		this.idper = idper;
		this.ci = ci;
		this.nombres = nombres;
		this.ap = ap;
		this.am = am;
		this.genero = genero;
		this.direccion = direccion;
		this.email = email;
		this.foto = foto;
		this.estado = estado;
		this.usuario = usuario;
		this.beneficiario = beneficiario;
	}
	public Integer getIdper() {
		return idper;
	}
	public void setIdper(Integer idper) {
		this.idper = idper;
	}
	public String getCi() {
		return ci;
	}
	public void setCi(String ci) {
		this.ci = ci;
	}
	public String getNombres() {
		return nombres;
	}
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}
	public String getAp() {
		return ap;
	}
	public void setAp(String ap) {
		this.ap = ap;
	}
	public String getAm() {
		return am;
	}
	public void setAm(String am) {
		this.am = am;
	}
	public String getGenero() {
		return genero;
	}
	public void setGenero(String genero) {
		this.genero = genero;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFoto() {
		return foto;
	}
	public void setFoto(String foto) {
		this.foto = foto;
	}
	public Integer getEstado() {
		return estado;
	}
	public void setEstado(Integer estado) {
		this.estado = estado;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Beneficiario getBeneficiario() {
		return beneficiario;
	}
	public void setBeneficiario(Beneficiario beneficiario) {
		this.beneficiario = beneficiario;
	}
	@Override
	public String toString() {
		return "Persona [idper=" + idper + ", ci=" + ci + ", nombres=" + nombres + ", ap=" + ap + ", am=" + am
				+ ", genero=" + genero + ", direccion=" + direccion + ", email=" + email + ", foto=" + foto
				+ ", estado=" + estado + ", usuario=" + usuario + ", beneficiario=" + beneficiario + "]";
	}
	
	
	
}
