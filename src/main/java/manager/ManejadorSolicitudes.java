package manager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import models.Beneficiario;
import models.CombustibleVehiculo;
import models.Documento;
import models.MarcaVehiculo;
import models.ModeloVehiculo;
import models.Persona;
import models.Solicitud;
import models.TipoMotorVehiculo;
import models.TipoServicioVehiculo;
import models.TipoVehiculo;
import models.Vehiculo;
import models.respuesta;


@Service
public class ManejadorSolicitudes{
private JdbcTemplate db;
	
	@Autowired
	public void setDataSource(DataSource dataSource){
		db = new JdbcTemplate(dataSource);
	}
	
	
	//@aui empieza los objetos
	public class objSolicitud implements RowMapper<Solicitud>{
		@Override
		public Solicitud mapRow(ResultSet rs, int arg1) throws SQLException {
			Solicitud s= new Solicitud();
			s.setIdsolt(rs.getInt("idsolt"));
			s.setNumSolt(rs.getString("numSolt"));
			s.setFecha(rs.getString("fechaCreacion"));
			s.setObservaciones(rs.getString("observaciones"));
			s.setAprobadoSiNo(rs.getInt("aprobadoSiNo"));
			s.setLogin(rs.getString("login"));
			s.setEstado(rs.getInt("estado"));
			try {
				s.setVehiculo(metVehiculo(rs.getString("placa")));
			} catch (Exception e) {
				s.setVehiculo(null);
			}
			return s;
	    }
	}
	
	public class objVehiculo implements RowMapper<Vehiculo>{
		@Override
		public Vehiculo mapRow(ResultSet rs, int arg1) throws SQLException {
			Vehiculo v= new Vehiculo();
			v.setPlaca(rs.getString("placa"));
			v.setTjeta_prioridad(rs.getString("tjeta_prioridad"));
			v.setCilindrada(rs.getString("cilindrada"));
			v.setColor(rs.getString("color"));
			v.setNum_motor(rs.getString("num_motor"));
			v.setNum_chasis(rs.getString("num_chasis"));
			try {
				v.setCombustibleVehiculo(metConmbustibles(rs.getString("placa")));
			} catch (Exception e) {
				// TODO: handle exception
				v.setCombustibleVehiculo(null);
			}
			try {
				v.setTipoVehiculo(metTipoVehiculo(rs.getInt("idtipv")));
			} catch (Exception e) {
				v.setTipoVehiculo(null);
			}
			try {
				v.setMarcaVehiculo(metMarcaVehiculo(rs.getInt("idmarcv")));
			} catch (Exception e) {
				v.setMarcaVehiculo(null);
			}
			try {
				v.setTipoServicio(metTipoServicio(rs.getInt("idTipSv")));
			} catch (Exception e) {
				v.setTipoServicio(null);
			}
			try {
				v.setTipoMotor(metTipoMotor(rs.getInt("idtipoMotorVeh")));
			} catch (Exception e) {
				v.setTipoMotor(null);
			}
			try {
				v.setModeloVehiculo(metModeloVehiculo(rs.getInt("idmodv")));
			} catch (Exception e) {
				v.setModeloVehiculo(null);
			}
			try {
				v.setPersona(metObtenerPersona(rs.getInt("idben")));
			} catch (Exception e) {
				v.setPersona(null);
			}
			return v;
	    }
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
				p.setBeneficiario(metBeneficiario(rs.getInt("idper")));
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
	
	public class objCombustibleVehiculo implements RowMapper<CombustibleVehiculo>{
		@Override
		public CombustibleVehiculo mapRow(ResultSet rs, int arg1) throws SQLException {
			CombustibleVehiculo c= new CombustibleVehiculo();
			c.setIdcomb(rs.getInt("idcomb"));
			c.setNombre(rs.getString("nombre"));
			c.setDetalle(rs.getString("detalle"));
			c.setEstado(rs.getInt("estado"));
			return c;
	    }
	}
	
	public class objTipoVehiculo implements RowMapper<TipoVehiculo>{
		@Override
		public TipoVehiculo mapRow(ResultSet rs, int arg1) throws SQLException {
			TipoVehiculo tp= new TipoVehiculo();
			tp.setIdtipv(rs.getInt("idtipv"));
			tp.setNombre(rs.getString("nombre"));
			tp.setEstado(rs.getInt("estado"));
			return tp;
	    }
	}
	
	public class objMarcaVehiculo implements RowMapper<MarcaVehiculo>{
		@Override
		public MarcaVehiculo mapRow(ResultSet rs, int arg1) throws SQLException {
			MarcaVehiculo mv= new MarcaVehiculo();
			mv.setIdmarcv(rs.getInt("idmarcv"));
			mv.setNombre(rs.getString("nombre"));
			mv.setEstado(rs.getInt("estado"));
			return mv;
	    }
	}
	
	public class objTipoServicio implements RowMapper<TipoServicioVehiculo>{
		@Override
		public TipoServicioVehiculo mapRow(ResultSet rs, int arg1) throws SQLException {
			TipoServicioVehiculo ts= new TipoServicioVehiculo();
			ts.setIdTipSv(rs.getInt("idTipSv"));
			ts.setNombre(rs.getString("nombre"));
			ts.setEstado(rs.getInt("estado"));
			return ts;
	    }
	}
	public class objTipoMotor implements RowMapper<TipoMotorVehiculo>{
		@Override
		public TipoMotorVehiculo mapRow(ResultSet rs, int arg1) throws SQLException {
			TipoMotorVehiculo tm= new TipoMotorVehiculo();
			tm.setIdtipoMotorVeh(rs.getInt("idtipoMotorVeh"));
			tm.setNombre(rs.getString("nombre"));
			tm.setEstado(rs.getInt("estado"));
			return tm;
	    }
	}
	public class objModeloVehiculo implements RowMapper<ModeloVehiculo>{
		@Override
		public ModeloVehiculo mapRow(ResultSet rs, int arg1) throws SQLException {
			ModeloVehiculo mv= new ModeloVehiculo();
			mv.setIdmodv(rs.getInt("idmodv"));
			mv.setNombre(rs.getString("nombre"));
			mv.setEstado(rs.getInt("estado"));
			return mv;
	    }
	}
	 
	public Beneficiario metBeneficiario(int idper){
		return this.db.queryForObject("select * from beneficiario where idper=?", new objBeneficiario(),idper);
	}
	public List<Documento> getDocumentos(int idben){
		return this.db.query("SELECT d.* FROM docBeneficiario d,beneficiario b,bendoc bd WHERE d.iddocb=bd.iddocb and b.idben=bd.idben and b.idben=?", new objDocumento(),idben);
	}
	
	//$empesemos
	public Vehiculo metVehiculo(String placa){
		return this.db.queryForObject("select * from vehiculo where placa=?", new objVehiculo(),placa);
	}
	public List<CombustibleVehiculo> metConmbustibles(String placa){
		return this.db.query("SELECT c.* FROM combustible c,vehiculo v,combveh cv WHERE c.idcomb=cv.idcomb and v.placa=cv.placa and v.placa=?", new objCombustibleVehiculo(),placa);
	}
	public TipoVehiculo metTipoVehiculo(int idtipv){
		return this.db.queryForObject("select tp.* from tipoVehiculo tp where idtipv=? and tp.estado=1", new objTipoVehiculo(),idtipv);
	}
	public MarcaVehiculo metMarcaVehiculo(int idmarcv){
		return this.db.queryForObject("select * from marcaVehiculo  where idmarcv=? and estado=1", new objMarcaVehiculo(),idmarcv);
	}
	public TipoServicioVehiculo metTipoServicio(int idTipSv){
		return this.db.queryForObject("select * from tipoServicioVehiculo where idTipSv=? and estado=1", new objTipoServicio(),idTipSv);
	}
	public TipoMotorVehiculo metTipoMotor(int idtipoMotorVeh){
		return this.db.queryForObject("select * from tipoMotorVehiculo where idtipoMotorVeh=? and estado=1", new objTipoMotor(),idtipoMotorVeh);
	}
	public ModeloVehiculo metModeloVehiculo(int idmodv){
		return this.db.queryForObject("select * from modeloVehiculo where idmodv=? and estado=1", new objModeloVehiculo(),idmodv);
	}
	public Persona metObtenerPersona(int idben){
		String sql="SELECT p.* FROM persona p JOIN beneficiario b ON b.idper=p.idper JOIN vehiculo v ON v.idben=b.idben and v.idben=?";
		return this.db.queryForObject(sql,new objPersona(),idben);
	}
	//Solicitudes
	public List<Solicitud> Listar(HttpServletRequest req){
		String filtro=req.getParameter("filtro");
		int estado=Integer.parseInt(req.getParameter("estado"));
		String sql="select s.* from solicitud s join vehiculo v on s.placa=v.placa where  (v.placa LIKE ?) and (s.estado=? or ?=-1) ORDER BY s.idsolt ASC";
		return this.db.query(sql, new objSolicitud(),"%"+filtro+"%",estado,estado);
	}
	
	public List<Documento> listaDocumentos(){
		String sql="SELECT * FROM docBeneficiario WHERE estado=1 ORDER BY iddocb ASC";
		return this.db.query(sql,new objDocumento());
	}
	
	//Metodos al adicionar
	public List<TipoVehiculo> tipoVehiculo(){
		String sql="select tp.* from tipoVehiculo tp where tp.estado=1";
		return this.db.query(sql,new objTipoVehiculo());
	}
	public List<MarcaVehiculo> marcaVehiculo(){
		String sql="select * from marcaVehiculo  where estado=1";
		return this.db.query(sql,new objMarcaVehiculo());
	}
	public List<ModeloVehiculo> modeloVehiculo(){
		String sql="select * from modeloVehiculo where estado=1";
		return this.db.query(sql, new objModeloVehiculo());
	}
	public List<TipoMotorVehiculo> tipoMotorVehiculo(){
		String sql="select * from tipoMotorVehiculo where estado=1";
		return this.db.query(sql, new objTipoMotor());
	}
	public List<TipoServicioVehiculo> tipoServicioVehiculo(){
		String sql="select * from tipoServicioVehiculo where estado=1";
		return this.db.query(sql,new objTipoServicio());
	}
	public List<CombustibleVehiculo> tipoCombustible(){
		String sql="SELECT * FROM combustible  WHERE estado=1";
		return this.db.query(sql,new objCombustibleVehiculo());
	}
	public int numeroSolicitud(){
		String sql="select COALESCE(max(idsolt),0)+1 as idsolt from solicitud";
		return db.queryForObject(sql, Integer.class);
	}
	public boolean verificarPlaca(String placa){
		System.out.println("entro sql_placa:"+placa);
		String sql="";
		try {
			sql="SELECT COUNT(placa) FROM vehiculo WHERE placa=? and estado=0";
			int data=this.db.queryForObject(sql,Integer.class,placa);
			System.out.println("ver????:"+data);
			if(data!=0){
				return true;	
			}else{
				return false;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			System.out.println("entro catch");	
			return false;
		}
		
	}
	
	public int EstadoPlaca(String placa){
		System.out.println("entro sql_placa:"+placa);
		String sql="";
		try {
			sql="SELECT COUNT(v.placa) FROM solicitud s, vehiculo v WHERE v.placa=s.placa and v.placa=? and v.estado=1";
//			sql="SELECT v.placa FROM solicitud s, vehiculo v WHERE v.placa=s.placa and v.placa=? and v.estado=0";
//			Map<String, Object>data=this.db.queryForMap(sql,new Object[]{placa});
			int data=this.db.queryForObject(sql,Integer.class,placa);
			System.out.println("existe????:"+data);
			if(data!=0){
//				System.out.println("entro if_placa: "+data);
				return 1;	
			}else{
//				System.out.println("entro else_placa: "+data);
				return 0;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			System.out.println("entro catch");	
			return 0;
		}
		
	}
	
	public boolean registrar(HttpServletRequest req,Persona xuser,Vehiculo v,Solicitud s,int [] combustible){
		int idsolt= generarIdSol();

		String sql="";
		try {
			sql="INSERT INTO vehiculo(placa,tjeta_prioridad,cilindrada,color,num_motor,num_chasis,idtipv,idmarcv,idben,idTipSv,idtipoMotorVeh,idmodv) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
			this.db.update(sql,v.getPlaca(),v.getTjeta_prioridad(),Integer.parseInt(v.getCilindrada()),v.getColor(),v.getNum_motor(),v.getNum_chasis(),v.getIdtipv(),v.getIdmarcv(),v.getIdben(),v.getIdTipSv(),v.getIdtipoMotorVeh(),v.getIdmodv());
			sql="INSERT INTO combveh(placa,idcomb) VALUES(?,?)";
			for (int i = 0; i <combustible.length; i++) {
				this.db.update(sql,v.getPlaca(),combustible[i]);	
			}
			sql="INSERT INTO solicitud(idsolt,numSolt,fechaCreacion,observaciones,placa,login) VALUES(?,?,?,?,?,?)";
			this.db.update(sql,idsolt,s.getNumSolt(),s.getFecha(),s.getObservaciones(),v.getPlaca(),xuser.getUsuario().getLogin());	
			
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean anular(Integer idsolt){
		String sql="";
		try {
			sql="UPDATE solicitud SET estado=0 WHERE idsolt=?";
			int a=this.db.update(sql,idsolt);
			System.out.println("sql_anulo: "+a);
			sql="UPDATE v SET v.estado=0 FROM vehiculo v INNER JOIN solicitud s ON s.placa=v.placa WHERE s.idsolt=?";
			this.db.update(sql,idsolt);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	public int generarIdSol(){
		String sql="select COALESCE(max(idsolt),0)+1 as idsolt from solicitud";
		return db.queryForObject(sql, Integer.class);
	}
}
