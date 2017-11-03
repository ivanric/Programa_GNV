package controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import manager.ManejadorBeneficiarios;
import models.Beneficiario;
import models.Documento;
import models.Persona;
import models.respuesta;

@RequestMapping("/RestBeneficiarios/")
@RestController
public class RestBeneficiarios {
	@Autowired
	ManejadorBeneficiarios manejadorBeneficiarios;
	
	@RequestMapping(value="listar")
	public ResponseEntity<List<Persona>> listarBneneficiarios(HttpServletRequest req,HttpServletResponse res){	
		List<Persona> beneficiarios=this.manejadorBeneficiarios.Lista(req);
		System.out.println("listaBen: "+beneficiarios.toString());
		return new ResponseEntity<List<Persona>>(beneficiarios,HttpStatus.OK);
	}
	@RequestMapping(value="documentosBeneficiario")
	public ResponseEntity<List<Documento>> docuemtosBeneficiario(HttpServletRequest req,HttpServletResponse res){	
		List<Documento> listaDocumentos=this.manejadorBeneficiarios.listaDocumentos();
		return new ResponseEntity<List<Documento>>(listaDocumentos,HttpStatus.OK);
	}
	@RequestMapping(value="adicionar")
	public Map<String, Object> adicionar(HttpServletRequest req,HttpServletResponse res,Persona p,@RequestParam String ci){
//		System.out.println("Pers: "+p);
//		System.out.println("ci: "+ci);
		String[] documentos=req.getParameterValues("documentos[]");
//		System.out.println("documentosArray: "+documentos.toString());
		System.out.println("tamañoDocArray: "+documentos.length);
		Map<String, Object> respuesta=new HashMap<String, Object>();
		for (String i : documentos) {
			System.out.println("coddocb: "+i);
		}
		try {
			boolean consulta=this.manejadorBeneficiarios.registrar(req, p, documentos);
			System.out.println(consulta);
			respuesta.put("estado", consulta);
		} catch (Exception e) {
			// TODO: handle exception
			respuesta.put("estado",false);
		}
		respuesta.put("estado",true);
		return respuesta;
	}
	@RequestMapping(value="datos-mod")
	public ResponseEntity<Persona> datosMod(HttpServletRequest req){
		Persona BeneficiarioDatos=this.manejadorBeneficiarios.datosModificar(req);
		return new ResponseEntity<Persona>(BeneficiarioDatos,HttpStatus.OK);		
	}
	@RequestMapping(value="modificar")
	public Map<String, Object> modificar(HttpServletRequest req,HttpServletResponse res,Persona p,@RequestParam String ci){
		Map<String, Object> respuesta=new HashMap<String, Object>();
		String[] documentos=req.getParameterValues("documentos[]");
		for (String i : documentos) {
			System.out.println("coddocb_modifocado: "+i);
		}
		try {
			boolean consulta=this.manejadorBeneficiarios.modificar(req, p, documentos);
			System.out.println(consulta);
			respuesta.put("estado", consulta);
		} catch (Exception e) {
			// TODO: handle exception
			respuesta.put("estado",false);
		}
		return respuesta;
	}
	
	@RequestMapping(value="eliminar")
	public String elim(HttpServletRequest req){
		System.out.println("lego eliminar");
		return "hola eliminar";		
	}
	

	
}
