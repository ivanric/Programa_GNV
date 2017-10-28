package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import manager.ManejadorSolicitudes;
import models.Solicitud;

@RequestMapping("/RestAprobaciones/")
@RestController
public class RestAprobaciones {
	@Autowired
	ManejadorSolicitudes manejadorSolicitudes;
	
	@RequestMapping(value="listar")
	public ResponseEntity<List<Solicitud>> listarBneneficiarios(HttpServletRequest req,HttpServletResponse res){	
		List<Solicitud> solicitudes=new ArrayList<Solicitud>();
//		solicitudes=this.manejadorSolicitudes.Listar(req);
		System.out.println("listaSolt: "+solicitudes.toString());
		return new ResponseEntity<List<Solicitud>>(solicitudes,HttpStatus.OK);
	}
}
