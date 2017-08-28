package controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


import manager.ManejadorModulos;
import manager.ManejadorOpciones;
import manager.ManejadorProcesos;
import manager.ManejadorRoles;
import manager.ManejadorUsuarios;
import models.Modulo;
import models.Persona;
import models.Proceso;
import models.Rol;
/**
 * Handles requests for the application home page.
 */
//@RestController
@RequestMapping("/principal/")
@Controller
public class Logueo {
	
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
	
	@RequestMapping(value = "/index")
	public String home(Model model) {
//		model.addAttribute("Hello", "Hola Mundo Velocity");
		return "index";
	}
	
	@RequestMapping(value = "/inicio")
	public String inicio(HttpServletRequest request, Model model) {
		
		HttpSession sesion=request.getSession(true);
		Persona xuser=(Persona) sesion.getAttribute("xusuario");
		System.out.println("Llego Sesion Logueada: "+xuser);
		System.out.println("Llega usuario "+xuser);
		try {
			if (xuser==null){
				return "redirect:index.html";
			}else{
				List<Rol> Roles=this.manejadorRoles.ListarRolUsuario(xuser.getIdper());
//				for (Object i : Roles) {
//					System.out.println("Roles"+i);
//				}
				Rol r1=Roles.get(0);
//				System.out.println("idrol_1: "+r1.getIdrol());
;
				List<Modulo> xmenus = this.manejadorModulos.menusRol(r1.getIdrol());
//				List<?> xprocesos=this.procesoManager.ListarProcesos();
				for (Modulo modulo : xmenus) {
					System.out.println("Modulos:"+modulo.toString());
				}
				for (int i = 0; i < xmenus.size(); i++) {
					Modulo mx = xmenus.get(i);
//					List <Proceso> lp=this.manejadorProcesos.getProcesosByMenu( mx.getIdmod());
//					System.out.println("lp: "+lp.toString());
					mx.setProcesos( this.manejadorProcesos.getProcesosByMenu( mx.getIdmod(),r1.getIdrol()));
					for (int j = 0; j < mx.getProcesos().size(); j++) {
						Proceso p=mx.getProcesos().get(j);
						p.setOpciones(this.manejadorOpciones.getOpcionesByRolMenuProc(r1.getIdrol(),mx.getIdmod(),p.getIdproc()));
					}
				}

				for (Modulo modulo : xmenus) {
					System.out.println("Procesos_date:"+modulo.getProcesos().toString());
				}
//				System.out.println("Lista de Menus: "+xmenus.toString());
//				System.out.println("lista de Procesos: "+xprocesos);
//				List<?> xsubmenus = this.menuManager.xsubmenus();/*se hace una consulta y se captura todo un objeto de submenus*/
				
//				model.addAttribute("xsubmenus", xsubmenus);
				model.addAttribute("listRoles",Roles);
				model.addAttribute("xmenus", xmenus);
//				model.addAttribute("rolPrincipal", r1);
//				model.addAttribute("xprocesos",xprocesos);
				model.addAttribute("msg","Bienvenido "+xuser.getNombres()+" "+xuser.getAp()+" "+xuser.getAm());
				model.addAttribute("usuario",xuser);
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "principal/inicio";
	
	}
	@RequestMapping({"/alerta"})
	public String alerta(Model m,HttpServletRequest req,HttpServletResponse res){
		m.addAttribute("mensaje","¿Usted desea salir del Sistema..?");
		return "principal/desconectar";
	}
	@RequestMapping({"/desconectar"})
	public String desc(Model m,HttpServletRequest req,HttpServletResponse res){
		HttpSession sesion=req.getSession(true);
		sesion.removeAttribute("xusuario");
		return "redirect:index";
	}
	
//	@RequestMapping(value="RetornoMenuRol.html")
//	public String  ModalAddR(Model model,HttpServletRequest reques,@RequestParam String idrol){
//		Persona persona=(Persona)reques.getSession().getAttribute("xusuario");	
//		System.out.println("LLEgue idrol: "+idrol);
//		int codrol=Integer.parseInt(idrol);
//		List<Modulo> xmenus = this.manejadorModulos.LisRolmenus(codrol);
//		for (int i = 0; i < xmenus.size(); i++) {
//			Modulo mx = xmenus.get(i);
//			mx.setProcesos( this.manejadorProcesos.getProcesosByMenu( mx.getIdmod()));
//		}
//		model.addAttribute("xmenus", xmenus);
//		return "principal/nuevoModulo";
//	}
	
}
