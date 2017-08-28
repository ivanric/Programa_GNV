package controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import manager.ManejadorBeneficiarios;
import models.Beneficiario;
import models.Persona;

@RequestMapping("/RestBeneficiarios/")
@RestController
public class RestBeneficiarios {
	@Autowired
	ManejadorBeneficiarios manejadorBeneficiarios;
	
	@RequestMapping(value="listar")
	public ResponseEntity<List<Persona>> listarBneneficiarios(HttpServletRequest req,HttpServletResponse res){	
		List<Persona> beneficiarios=this.manejadorBeneficiarios.Lista(req);
		return new ResponseEntity<List<Persona>>(beneficiarios,HttpStatus.OK);
	}
}
