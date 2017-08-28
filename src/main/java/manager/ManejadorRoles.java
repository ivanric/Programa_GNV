package manager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import models.Rol;



//@Service indica que la clase es un bean de la capa de negocio
@Service
public class ManejadorRoles{
	
	private JdbcTemplate db;
	@Autowired
	public void setDataSource(DataSource dataSource){
		db = new JdbcTemplate(dataSource);
	}
	public class objRol implements RowMapper<Rol>{
		@Override
		public Rol mapRow(ResultSet rs, int arg1) throws SQLException {
			Rol r= new Rol();
			r.setIdrol(rs.getInt("idrol"));
			r.setNombre(rs.getString("nombre"));
			r.setEstado(rs.getInt("estado"));
			return r;
	    }
	}	
	public List<Rol> ListarRolUsuario(int idper){
		String sql="select r.* from rol r,usuario us,rolusu rs where r.idrol=rs.idrol and us.login=rs.login and us.idper=?";
		return this.db.query(sql,new objRol(),idper);	
	}
	public List<Rol> ControlRoles(int codper){
		System.out.println("codper: "+codper);
		String sql="select r.* from rol r,usuario us,rolusu rs where r.idrol=rs.idrol and rs.login=us.login and us.idper=?";
		return this.db.query(sql,new objRol(),codper);
	}
	


	

}