package ar.edu.unq.tip_eiroa_mauro.server.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.tip_eiroa_mauro.server.helpers.SecurityHelper;
import ar.edu.unq.tip_eiroa_mauro.server.model.Authentication;
import ar.edu.unq.tip_eiroa_mauro.server.model.Usuario;
import ar.edu.unq.tip_eiroa_mauro.server.persistence.UsuarioDao;

public class UsuarioService {
	private UsuarioDao usuarioDao;

	public void setUsuarioDao(UsuarioDao usuarioDao) {
		this.usuarioDao = usuarioDao;
	}
	
	@Transactional(readOnly=true)
	public List<Usuario> findAll(){
		return usuarioDao.findAll();
	}
	
	@Transactional()
	public Usuario findById(long id){
		return usuarioDao.findById(id);
	}
	
	@Transactional()
	public Usuario findByNick(String nick){
		return usuarioDao.findByNick(nick);
	}
	
	@Transactional()
	public Usuario findByImei(String imei){
		return usuarioDao.findByImei(imei);
	}
	
	@Transactional
	public void saveStandard(Boolean isAnon,String name,String surname,String code,String nick,String imei,String pass){
		
		Usuario maybeUser = this.usuarioDao.findByImei(imei);
		//El usuario puede registrarse por primera vez o bien haber borrado la aplicación y vuelto a 
		//registrarse, en cuyo caso se actualiza el usuario.
		// Invariante: El imei es unico e implica un dispositivo en particular.
		if(maybeUser != null){
			if ( (name == null && surname==null && code== null && nick == null && pass==null)  || isAnon){
				maybeUser.setAdmin(false);
				maybeUser.setAnonymous(true);
				this.usuarioDao.update(maybeUser);
			}else{
				maybeUser.setAdmin(false);
				maybeUser.setAnonymous(false);
				maybeUser.setName(name);
				maybeUser.setSurname(surname);
				maybeUser.setPassword(SecurityHelper.toSHA256(pass));
				maybeUser.setNick(nick);
				maybeUser.setCode(code);
				usuarioDao.update(maybeUser);
			}
			
		}else{
			if ( (name == null && surname==null && code== null && nick == null && pass==null)  || isAnon){
				this.usuarioDao.save(new Usuario(true,imei));
			}else{
				Boolean isAdmin = false;
				String passEncoded = SecurityHelper.toSHA256(pass);
				this.usuarioDao.save(new Usuario(name,surname,code,nick,imei,passEncoded,isAdmin));
			}
		}
		
		
	}
	
	@Transactional
	public void saveAdmin(String name,String surname,String code,String nick,String imei,String pass){
			Boolean isAdmin = true;
			String passEncoded = SecurityHelper.toSHA256(pass);
			this.usuarioDao.save(new Usuario(name,surname,code,nick,imei,passEncoded,isAdmin));

		
	}
	
	@Transactional
	public void editUser(long id,Boolean admin,Boolean anonymous,String name,String surname,
			String code,String nick,String imei){
		Usuario target =this.usuarioDao.findById(id);
		target.setAnonymous(anonymous);
		target.setAdmin(admin);
		target.setImei(imei);
		target.setCode(code);
		target.setName(name);
		target.setSurname(surname);
		target.setNick(nick);
		this.usuarioDao.update(target);
	}
	
	@Transactional
	public void deleteById(long id) {
		this.usuarioDao.deleteById(id);
	}
	
	/**
	 * Si el usuario al intentar validarse es administrador se devuelve el token, el cual es la clave
	 * hasheada a SHA256
	 * 
	 * @param nick
	 * @param pass
	 * @return
	 */
	@Transactional
	public Authentication authenticateUser(String nick, String pass) {
		 Usuario target = this.findByNick(nick);
		 Authentication auth = new Authentication();
		 if( target!= null && target.getAdmin() && SecurityHelper.toSHA256(pass).equals(target.getPass())){
			 auth.setValidUser(true);
			 auth.setToken(SecurityHelper.toSHA256(target.getPass()));
			 auth.setNick(nick);
		 }else{
			 auth.setValidUser(false);
		 }
		return auth;
	}
	/**
	 * Se valida que el usuario sea efectivamente administrador y exista
	 * @param nick
	 * @param token
	 * @return
	 */
	@Transactional
	public  Boolean isAuthorizedUser(String nick, String token) {
		Usuario target = this.findByNick(nick);
		if( target!= null && target.getAdmin() && SecurityHelper.toSHA256(target.getPass()).equals(token)){
			return true;
		}else{
			return false;
		}
	}
	
}	
