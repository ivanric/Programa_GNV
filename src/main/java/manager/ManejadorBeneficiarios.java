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

import manager.ManejadorUsuarios.objUsuario;
import models.Beneficiario;
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
			b.setCarnetPropiedad(rs.getInt("carnetPropiedad"));
			b.setRua(rs.getInt("rua"));
			b.setInstitucionPerteneciente(rs.getInt("institucionPerteneciente"));
			b.setPagoImpuestos(rs.getInt("pagoImpuestos"));
			b.setLicenciaConducir(rs.getInt("licenciaConducir"));
			b.setOtrosDoc(rs.getInt("otrosDoc"));
			b.setEstado(rs.getInt("estado"));
			b.setIdper(rs.getInt("idper"));
			return b;
	    }
	}
	public Beneficiario obtenerBeneficiario(int idper){
		return this.db.queryForObject("select * from beneficiario where idper=?", new objBeneficiario(),idper);
	}
	public List<Persona> Lista(HttpServletRequest req){
		String filtro=req.getParameter("filtro");
		int estado=Integer.parseInt(req.getParameter("estado"));
		String sql="select p.*,b.* from persona p join beneficiario b on b.idper=p.idper where concat(p.nombres,p.ap,p.am,p.ci) LIKE ? and (b.estado=? or ?=-1) ORDER BY b.idben ASC";
		return this.db.query(sql, new objPersona(),"%"+filtro+"%",estado,estado);
	}
	
}
