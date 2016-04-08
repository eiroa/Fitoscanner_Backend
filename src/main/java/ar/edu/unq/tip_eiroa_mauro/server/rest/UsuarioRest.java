package ar.edu.unq.tip_eiroa_mauro.server.rest;

import java.util.List;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.eclipse.equinox.internal.app.ErrorApplication;
import org.springframework.stereotype.Service;

import ar.edu.unq.tip_eiroa_mauro.server.model.Authentication;
import ar.edu.unq.tip_eiroa_mauro.server.model.ErrorEntity;
import ar.edu.unq.tip_eiroa_mauro.server.model.Usuario;
import ar.edu.unq.tip_eiroa_mauro.server.services.UsuarioService;

@Service
@Path("/user")
public class UsuarioRest {
	private UsuarioService usuarioService;

	public void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}
	
	
	@GET
	@Path("/all")
	@Produces("application/json")
	public Response getAllUsers() {
		List<Usuario> obs =  usuarioService.findAll();
		return Response.ok().header("Access-Control-Allow-Origin", "*").entity(obs).build();
	}
	
	@POST
	@Path("/save")
	@Consumes("application/x-www-form-urlencoded")
	public Response saveUser(
			@FormParam("imei") String imei,
			@FormParam("name") String name,
			@FormParam("surname") String surname,
			@FormParam("code") String code,
			@FormParam("nick") String nick,
			@FormParam("anon") Boolean anonymous,
			@FormParam("ps") String pass
			){
		usuarioService.saveStandard(anonymous,name,surname,code,nick,imei,pass);
		return Response.ok().header("Access-Control-Allow-Origin", "*").build();
	}
	
	@POST
	@Path("/authenticate")
	@Produces("application/json")
	public Response authenticateUser(
			@FormParam("user_nick") String nick,
			@FormParam("ps") String pass
			){
		Authentication auth = usuarioService.authenticateUser(nick,pass);
		if(auth.getValidUser()){
			return Response.ok().header("Access-Control-Allow-Origin", "*").entity(auth).build();
		}else{
			return Response.status(401).header("Access-Control-Allow-Origin", "*").entity(auth).build();
		}
	}
	
	@POST
	@Path("/validateAdmin")
	@Produces("application/json")
	public Response validateUser(
			@FormParam("nick") String nick,
			@FormParam("token") String token
			){
		if(usuarioService.isAuthorizedUser(nick,token)){
			return Response.ok().header("Access-Control-Allow-Origin", "*").build();
		}else{
			ErrorEntity error =  new ErrorEntity();
			error.setErrorMessage("Usuario no identificado, por favor ingrese usuario y contraseña");
			return Response.status(403).header("Access-Control-Allow-Origin", "*").entity(error).build();
		}
		
	}
	
	@POST
	@Path("/edit")
	@Consumes("application/x-www-form-urlencoded")
	public Response editUser(
			@FormParam("id") long id,
			@FormParam("imei") String imei,
			@FormParam("name") String name,
			@FormParam("surname") String surname,
			@FormParam("code") String code,
			@FormParam("nick") String nick,
			@FormParam("anon") Boolean anonymous,
			@FormParam("admin") Boolean admin
			){
		usuarioService.editUser(id,admin,anonymous,name,surname,code,nick,imei);
		return Response.ok().header("Access-Control-Allow-Origin", "*").build();
	}
	
	@POST
	@Path("/saveAdmin")
	@Consumes("application/x-www-form-urlencoded")
	public Response saveUserAdmin(
			@FormParam("imei") String imei,
			@FormParam("name") String name,
			@FormParam("surname") String surname,
			@FormParam("code") String code,
			@FormParam("nick") String nick,
			@FormParam("ps") String pass
			){
		usuarioService.saveAdmin(name, surname, code, nick, imei,pass);
		return Response.ok().header("Access-Control-Allow-Origin", "*").build();
	}
	
	@POST
	@Path("/searchAll")
	@Consumes("application/x-www-form-urlencoded")
	public Response searchUsers(
			@FormParam("pageNumber") Integer pn,
			@FormParam("pageSize") Integer ps,
			@FormParam("resultCount") Integer rc,
			@FormParam("name") String name
			) {
		
		return Response.ok().header("Access-Control-Allow-Origin", "*").build();
	}
	
	@POST
	@Path("/findById")
	@Consumes("application/x-www-form-urlencoded")
	public Response findById(
			@FormParam("id") long id
			) {
		Usuario result =usuarioService.findById(id);
		
		return Response.ok().header("Access-Control-Allow-Origin", "*").entity(result).build();
	}
	
	@POST
	@Path("/delete")
	@Consumes("application/x-www-form-urlencoded")
	public Response deleteUser(
			@FormParam("userId") long id,
			@FormParam("nick") String nick,
			@FormParam("token") String token
			) {
		if(usuarioService.isAuthorizedUser(nick,token)){
			usuarioService.deleteById(id);
			return Response.ok().header("Access-Control-Allow-Origin", "*").build();
		}else{
			ErrorEntity error =  new ErrorEntity();
			error.setErrorMessage("Usuario no identificado, por favor ingrese usuario y contraseña");
			return Response.status(403).header("Access-Control-Allow-Origin", "*").entity(error).build();
		}
	}
}
