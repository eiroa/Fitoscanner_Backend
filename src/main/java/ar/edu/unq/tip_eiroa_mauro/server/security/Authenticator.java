package ar.edu.unq.tip_eiroa_mauro.server.security;

import java.util.HashMap;

import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.CodeSignature;
import org.slf4j.LoggerFactory;

import ar.edu.unq.tip_eiroa_mauro.server.model.ErrorEntity;
import ar.edu.unq.tip_eiroa_mauro.server.services.UsuarioService;

@Aspect
public class Authenticator {
	
	private UsuarioService usuarioService;
	
	
	
	public UsuarioService getUsuarioService() {
		return usuarioService;
	}

	public void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	final static org.slf4j.Logger logger = LoggerFactory.getLogger(Authenticator.class);

	private HashMap<String, String> buildParameterList(
			ProceedingJoinPoint method) {
		String[] parameterNames = ((CodeSignature) method.getSignature())
				.getParameterNames();
		Object[] parameterValues = method.getArgs();
		HashMap<String, String> result = new HashMap<String, String>();
		for (int i = 0; i < parameterNames.length; i++) {
			if(parameterValues[i] != null){
				result.put(parameterNames[i], parameterValues[i].toString());
			}
			
		}
		return result;
	}
 
	/**
	 * Ante cada peticion Rest se validará que el usuario intentando ejecutarlos 
	 * está validado en el servidor
	 * @param method
	 * @return
	 */
	@Around("!execution(* ar.edu.unq.tip_eiroa_mauro.server.rest.UsuarioRest.*(..)) && "
			+ "execution( public * ar.edu.unq.tip_eiroa_mauro.server.rest..*(..))")
	@Produces("application/json")
	public Response validateMethodInvocation(ProceedingJoinPoint method) {
		
		HashMap<String, String> parameters = this.buildParameterList(method);
		String signature = method.getSignature().getName();
		logger.info("Method invoked : "+ signature);
		logger.info("Method arguments: "+ parameters);
		String nick = parameters.get("nick");
		String token = parameters.get("token");
		//Metodos get , guardar muestra y obtener tratamiento son publicos
		if (signature.contains("get") || 
				signature.contains("saveSample") || 
				signature.contains("requestTreatment")){
			try {
				return (Response)method.proceed();
			} catch (Throwable e) {
				e.printStackTrace();
				logger.error("Error calling method"+ method.getSignature().getName());
				return Response.status(503).header("Access-Control-Allow-Origin", "*").build();
			}
		}else{
			if(nick != null && token!= null &&  this.usuarioService.isAuthorizedUser(nick, token)){
				try {
					return (Response)method.proceed();
				} catch (Throwable e) {
					logger.error("Error calling method"+ method.getSignature().getName());
					return Response.status(503).header("Access-Control-Allow-Origin", "*").build();
				}
			}else{
				ErrorEntity error =  new ErrorEntity();
				error.setErrorMessage("Usuario no identificado, por favor ingrese usuario y contraseña");
				return Response.status(403).header("Access-Control-Allow-Origin", "*").entity(error).build();
			}
		}
	}
}
