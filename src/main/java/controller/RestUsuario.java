package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import manager.ManejadorModulos;
import manager.ManejadorOpciones;
import manager.ManejadorProcesos;
import manager.ManejadorRoles;
import manager.ManejadorUsuarios;
import models.Modulo;
import models.Persona;
import models.Proceso;
import models.Rol;
import models.respuesta;

@RequestMapping("/usuario/")
@RestController
public class RestUsuario {
	@Autowired 
	ManejadorUsuarios manejadorUsuarios;
	@Autowired 
	ManejadorRoles manejadorRoles;
	@Autowired 
	ManejadorModulos manejadorModulos;
	@Autowired 
	ManejadorProcesos manejadorProcesos;
	@Autowired
	ManejadorOpciones manejadorOpciones;
	
	@Transactional
	@ResponseBody 
	@RequestMapping({"/validar"})	
	public  respuesta validar(HttpServletRequest request, Model model,@RequestParam String xlogin, String xpassword)  throws IOException  {			
		
//		Map<String, Object> Data=new HashMap<>();
		respuesta resp=new respuesta();
		try {
			Persona xusuario=this.manejadorUsuarios.iniciarSession(xlogin,xpassword);
			System.out.println(xusuario);
			System.out.println("idPer: "+xusuario.getIdper());
			List<Rol> ListaRoles=this.manejadorRoles.ControlRoles(xusuario.getIdper());
			System.out.println("RolesFuera: "+ListaRoles.toString());
			if(xusuario!=null){
				System.out.println("Logueado: "+xusuario.toString());
				System.out.println("Roles: "+ListaRoles.toString());
				System.out.println(xusuario.getEstado());
				if(xusuario.getEstado()!=1){
//					Data.put("msg", "Usuario no esta activo");
//					Data.put("status", false);
					resp.setMsg("Usuario no esta activo");
					resp.setStatus(false);
				}
				else if(ListaRoles.size()==0){
					resp.setMsg("Este usuario no tiene Roles");
					resp.setStatus(false);
				}
				else{
					HttpSession sesion=request.getSession(true);
					sesion.setAttribute("xusuario",xusuario);
//					Data.put("msg", "Usuario registrado con Exito");
//					Data.put("status", true);
					resp.setMsg("Usuario registrado con Exito");
					resp.setStatus(true);
				}
			}else{

//				model.addAttribute("msg","Usuario incorrecto, por favor verifique login y clave");
//				Data.put("msg", "Sin accesso");
//				Data.put("status", false);		
				resp.setMsg("Ocurrio un Error con el Servidor");
				resp.setStatus(false);
			}
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			// TODO: handle exception
			System.out.println(e.getMessage());
//			Data.put("msg", "Sin accesso");
//			Data.put("status", false);
			resp.setMsg("Usuario incorrecto, por favor verifique login y clave.");
			resp.setStatus(false);

		}
		System.out.println("objeto:"+resp.toString());

		return resp;
	} 
	
	@ResponseBody 
	@RequestMapping(value="MenuRol")
//	public ResponseEntity<List<Modulo>>  ModalAddR(Model model,HttpServletRequest reques,@RequestParam String idrol){
	public ResponseEntity<List<Modulo>>  ModalAddR(Model model,HttpServletRequest reques,@RequestParam String idrol){
		Persona persona=(Persona)reques.getSession().getAttribute("xusuario");	
		System.out.println("idrol: "+idrol);
		int codrol=Integer.parseInt(idrol);
		List<Modulo> xmenus = this.manejadorModulos.LisRolmenus(codrol);
		for (int i = 0; i < xmenus.size(); i++) {
			Modulo mx = xmenus.get(i);
			mx.setProcesos( this.manejadorProcesos.getProcesosByMenu( mx.getIdmod(),codrol));
//			System.out.println("modulos: "+mx);
			for (int j = 0; j < mx.getProcesos().size(); j++) {
				Proceso p=mx.getProcesos().get(j);
				p.setOpciones(this.manejadorOpciones.getOpcionesByRolMenuProc(codrol,mx.getIdmod(),p.getIdproc()));
			}
		}
		List<Modulo> m=new ArrayList<Modulo>();
		m.add(new Modulo(1, "m1", "", 1, null));
		respuesta e= new respuesta();
		e.setMsg("men1");
		e.setStatus(true);
		return new ResponseEntity<List<Modulo>>(xmenus,HttpStatus.OK);
	}
	
	@ResponseBody 
	@RequestMapping(value="obtenerRoles")
	public ResponseEntity<List<Rol>> obtenerRol(HttpServletRequest request,HttpServletResponse response){
		System.out.println("llegue ObtenerRoles");
		HttpSession sesion=request.getSession(true);
		Persona xuser=(Persona) sesion.getAttribute("xusuario");
		List<Rol> Roles=null;
		try {
			if (xuser==null) {
				System.out.println(" usuarui nullo");
//				"redirect: principal/index.html";
				response.sendRedirect("principal/index.html");
				return new ResponseEntity<List<Rol>>(HttpStatus.NOT_FOUND);
			} else {
				System.out.println("usuario ok");
				Roles=this.manejadorRoles.ListarRolUsuario(xuser.getIdper());
//				Rol r1=Roles.get(0);
//				for (Rol i : Roles) {
					
//				}
				
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		System.out.println("newRol: "+Roles);
		return new ResponseEntity<List<Rol>>(Roles,HttpStatus.OK);
	}
}
