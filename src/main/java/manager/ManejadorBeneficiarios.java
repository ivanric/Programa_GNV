package manager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import models.Beneficiario;
import models.Documento;
import models.Persona;


@Service
public class ManejadorBeneficiarios {
private JdbcTemplate db;
	
	@Autowired
	public void setDataSource(DataSource dataSource){
		db = new JdbcTemplate(dataSource);
	}
	public class objPersona implements RowMapper<Persona>{
		@Override
		public Persona mapRow(ResultSet rs, int arg1) throws SQLException {
			Persona p= new Persona();
			p.setIdper(rs.getInt("idper"));
			p.setCi(rs.getString("ci"));
			p.setNombres(rs.getString("nombres"));
			p.setAp(rs.getString("ap"));
			p.setAm(rs.getString("am"));
			p.setGenero(rs.getString("genero"));
			p.setDireccion(rs.getString("direccion"));
			p.setEmail(rs.getString("email"));
			p.setFoto(rs.getString("foto"));
			p.setEstado(rs.getInt("estado"));
			try {
				p.setBeneficiario(obtenerBeneficiario(rs.getInt("idper")));
			} catch (Exception e) {
				p.setBeneficiario(null);
			}
			return p;
	    }
	}
	public class objBeneficiario implements RowMapper<Beneficiario>{
		@Override
		public Beneficiario mapRow(ResultSet rs, int arg1) throws SQLException {
			Beneficiario b= new Beneficiario();
			b.setIdben(rs.getInt("idben"));
			b.setInstitucion(rs.getString("institucion"));
			b.setDescripcion(rs.getString("descripcion"));
			b.setEstado(rs.getInt("estado"));
			b.setIdper(rs.getInt("idper"));
			try {
				b.setDocumentos(getDocumentos(rs.getInt("idben")));
			} catch (Exception e) {
				// TODO: handle exception
				b.setDocumentos(null);
			}
			return b;
	    }
	}
	public class objDocumento implements RowMapper<Documento>{
		@Override
		public Documento mapRow(ResultSet rs, int arg1) throws SQLException {
			Documento d= new Documento();
			d.setIddocb(rs.getInt("iddocb"));
			d.setNombre(rs.getString("nombre"));
			d.setEstado(rs.getInt("estado"));
			return d;
	    }
	}
	 
	public Beneficiario obtenerBeneficiario(int idper){
		return this.db.queryForObject("select * from beneficiario where idper=?", new objBeneficiario(),idper);
	}
	public List<Documento> getDocumentos(int idben){
		return this.db.query("SELECT d.* FROM docBeneficiario d,beneficiario b,bendoc bd WHERE d.iddocb=bd.iddocb and b.idben=bd.idben and b.idben=?", new objDocumento(),idben);
	}
	public List<Persona> Lista(HttpServletRequest req){
		String filtro=req.getParameter("filtro");
		int estado=Integer.parseInt(req.getParameter("estado"));
		String sql="select p.*,b.* from persona p join beneficiario b on b.idper=p.idper where (concat(p.ap,' ',p.am,' ',p.nombres) LIKE ? or p.ci LIKE ?) and (b.estado=? or ?=-1) ORDER BY b.idben ASC";
		return this.db.query(sql, new objPersona(),"%"+filtro+"%","%"+filtro+"%",estado,estado);
	}
	
	public List<Documento> listaDocumentos(){
		String sql="SELECT * FROM docBeneficiario WHERE estado=1 ORDER BY iddocb ASC";
		return this.db.query(sql,new objDocumento());
	}
	public boolean registrar(HttpServletRequest req,Persona p,String [] iddocb){
		int idper= generarIdPer();
		int idben= generarIdBen();
		String sql="";
		try {
			sql="INSERT INTO persona(idper,ci,nombres,ap,am,genero,direccion,email,estado) VALUES(?,?,?,?,?,?,?,?,?)";
			this.db.update(sql,idper,p.getCi(),p.getNombres(),p.getAp(),p.getAm(),p.getGenero(),p.getDireccion(),p.getEmail(),1);
			sql="INSERT INTO beneficiario(idben,institucion,descripcion,estado,idper) VALUES(?,?,?,?,?)";
			this.db.update(sql,idben,req.getParameter("institucion"),req.getParameter("institucion"),1,idper);
			sql="INSERT INTO bendoc(idben,iddocb) VALUES(?,?)";
			for (int i = 0; i < iddocb.length; i++) {
				this.db.update(sql,idben,Integer.parseInt(iddocb[i]));	
			}
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
	public Persona datosModificar(HttpServletRequest req){
		String sql="";
		Persona p=null;
		Beneficiario b=null;
		int idben=Integer.parseInt(req.getParameter("idben"));
		System.out.println("idben: "+idben);
		try {
			sql="SELECT p.* FROM persona p,beneficiario b WHERE b.idper=p.idper and b.idben=?";
			p=this.db.queryForObject(sql,new objPersona(),idben);
//			sql="SELECT b.* FROM beneficiario b WHERE b.idben=?";
//			b=this.db.queryForObject(sql, new objBeneficiario(),idben);
//			sql="SELECT d.* FROM docBeneficiario d,beneficiario b,bendoc bd WHERE d.iddocb=bd.iddocb and b.idben=bd.idben and b.idben=?";
//			b.setDocumento(this.db.queryForObject(sql, new objDocumento()));
//			p.setBeneficiario(b);
		} catch (Exception e) {
			p=null;
		}
		return p;
	}
	public boolean modificar(HttpServletRequest req,Persona p,String [] iddocb){
		int idper= generarIdPer();
		int idben= generarIdBen();
		String sql="";
		try {
			sql="UPDATE persona SET ci=?,nombres=?,ap=?,am=?,genero=?,direccion=?,email=? WHERE idper=?";
			this.db.update(sql,p.getCi(),p.getNombres(),p.getAp(),p.getAm(),p.getGenero(),p.getDireccion(),p.getEmail(),req.getParameter("idper"));
			sql="UPDATE beneficiario SET institucion=?,descripcion=? WHERE idben=?";
			this.db.update(sql,req.getParameter("institucion"),req.getParameter("descripcion"),req.getParameter("idben"));
//			sql="INSERT INTO bendoc(idben,iddocb) VALUES(?,?)";
//			for (int i = 0; i < iddocb.length; i++) {
//				this.db.update(sql,idben,iddocb[i]);	
//			}
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
	
	public int generarIdPer(){
		String sql="select COALESCE(max(idper),0)+1 as idper from persona";
		return db.queryForObject(sql, Integer.class);
	}
	public int generarIdBen(){
		String sql="select COALESCE(max(idben),0)+1 as idben from beneficiario";
		return db.queryForObject(sql, Integer.class);
	}
	public List<Persona> Listabenficiario(HttpServletRequest req){
		String busq=req.getParameter("texto");
		System.out.println("llego: "+busq=="");
		System.out.println("data: "+busq);
		String sql="select p.* from beneficiario b, persona p where b.idper=p.idper and (concat(p.nombres,' ',p.ap,' ',p.am)LIKE ? or p.ci LIKE ?)";
		return this.db.query(sql,new objPersona(),"%"+busq+"%","%"+busq+"%");
	}
}
