package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import manager.ManejadorBeneficiarios;
import manager.ManejadorSolicitudes;
import models.OpcionesVehiculo;
import models.Persona;
import models.Solicitud;
import models.TipoVehiculo;
import models.Vehiculo;

@RequestMapping("/RestSolicitudes/")
@RestController
public class RestSolicitudes {
	@Autowired
	ManejadorSolicitudes manejadorSolicitudes;
	@Autowired
	ManejadorBeneficiarios manejadorBeneficiarios;
	
	@RequestMapping(value="listar")
	public ResponseEntity<List<Solicitud>> listarBneneficiarios(HttpServletRequest req,HttpServletResponse res){	
		List<Solicitud> solicitudes=this.manejadorSolicitudes.Listar(req);
		System.out.println("listaSolt: "+solicitudes.toString());
		return new ResponseEntity<List<Solicitud>>(solicitudes,HttpStatus.OK);
	}
	@RequestMapping({"busqueda_benficiario"})
	public ResponseEntity<List<?>> busqueda_benficiario(HttpServletRequest req,HttpServletResponse res){
		List<?> listaBen=this.manejadorBeneficiarios.Listabenficiario(req);
//		System.out.println("l: "+listaBen);
		return new ResponseEntity<List<?>>(listaBen,HttpStatus.OK);
	}
	@RequestMapping({"opcionesVehiculo"})
	public ResponseEntity<OpcionesVehiculo> opcionesVehiculo(HttpServletRequest req,HttpServletResponse res){

		OpcionesVehiculo lista= new OpcionesVehiculo();
		

		lista.setTipoVehiculo(this.manejadorSolicitudes.tipoVehiculo());
		lista.setMarcaVehiculo(this.manejadorSolicitudes.marcaVehiculo());
		lista.setModeloVehiculo(this.manejadorSolicitudes.modeloVehiculo());
		lista.setTipoMotor(this.manejadorSolicitudes.tipoMotorVehiculo());
		lista.setTipoServicio(this.manejadorSolicitudes.tipoServicioVehiculo());
		lista.setCombustibles(this.manejadorSolicitudes.tipoCombustible());
		lista.setNumSolt(this.manejadorSolicitudes.numeroSolicitud());
		System.out.println("lista_listas: "+lista);
		return new ResponseEntity<OpcionesVehiculo>(lista,HttpStatus.OK);
	}
	@RequestMapping ({"existePlaca"})
	public ResponseEntity<Map<String, Object>> existe(HttpServletRequest req){
		Map<String, Object> mapa=new HashMap<String, Object>();
		String placa=req.getParameter("placa");
		int existe;
		System.out.println("tam_"+placa.length());
		
		if(this.manejadorSolicitudes.verificarPlaca(placa)){
			existe=2;
		}else{
			existe=this.manejadorSolicitudes.EstadoPlaca(placa);			
		}
		

		System.out.println("existe: "+existe);
		mapa.put("estado", existe);
		return new ResponseEntity<Map<String,Object>>(mapa,HttpStatus.OK);
	}
	@RequestMapping({"PlacaDatos"})
	public ResponseEntity<Vehiculo> DatosVeh(HttpServletRequest req){
		String placa=req.getParameter("placa");
		System.out.println("la placa es : "+placa);
		Vehiculo veh=this.manejadorSolicitudes.DatosVehiculo(placa);
		return new ResponseEntity<Vehiculo>(veh,HttpStatus.OK);
	}
	
	@Transactional
	@RequestMapping(value="adicionar")
	public Map<String, Object> adicionar(HttpServletRequest req,HttpServletResponse res,Solicitud s,Vehiculo v,@RequestParam int [] combustible){
		System.out.println("SolicitudSolicitud: "+s.toString());
		System.out.println("VehiculoSolicitud: "+v.toString());
		
		HttpSession sesion=req.getSession(true);
		Persona xuser=(Persona) sesion.getAttribute("xusuario");
		
		System.out.println("tamanio: "+combustible.length);
		Map<String, Object> respuesta=new HashMap<String, Object>();
		
		for (int i : combustible) {
			System.out.println("cod_combustible: "+i);
		}
		try {
			boolean solicitud=this.manejadorSolicitudes.registrar(req,xuser,v,s,combustible);
			System.out.println(solicitud);
			respuesta.put("estado", solicitud);
//			respuesta.put("estado", true);
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			respuesta.put("estado",false);
		}

		return respuesta;
	}

	@Transactional
	@RequestMapping(value="anular")
	public Map<String, Object> anular(HttpServletRequest req,HttpServletResponse res){
		HttpSession sesion=req.getSession(true);
		Persona xuser=(Persona) sesion.getAttribute("xusuario");
		String idsolt=req.getParameter("idsolt");
		System.out.println("idSolt_servidor"+idsolt);
		Map<String, Object> respuesta=new HashMap<String, Object>();
		try {
			boolean solicitud=this.manejadorSolicitudes.anular(Integer.parseInt(idsolt));
			respuesta.put("estado", solicitud);
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			// TODO: handle exception
			respuesta.put("estado",false);
		}
//		respuesta.put("estado", true);
		return respuesta;
	}
}
