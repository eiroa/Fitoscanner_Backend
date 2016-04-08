package ar.edu.unq.tip_eiroa_mauro.server.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import ar.edu.unq.tip_eiroa_mauro.server.model.Authentication;
import ar.edu.unq.tip_eiroa_mauro.server.model.Usuario;
import ar.edu.unq.tip_eiroa_mauro.server.persistence.EspecieDao;
import ar.edu.unq.tip_eiroa_mauro.server.persistence.UsuarioDao;

@ContextConfiguration(locations = {"classpath:spring-test-context.xml"})
public class UsuarioServiceTest extends AbstractTransactionalJUnit4SpringContextTests {
	
	@Autowired
	private UsuarioDao usuarioDao;
	
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Test
	public void testAdminUser(){
		this.usuarioService.saveAdmin("admin", "admin", "admin", "admin", "admin", "admin");
		Usuario admin = this.usuarioService.findByNick("admin");
		assertEquals(admin.getAdmin(), true);
	}
	
	@Test
	public void testLoginUser(){
		this.usuarioService.saveAdmin("a", "a", "a", "a", "123", "pass");
		Authentication token = usuarioService.authenticateUser("a", "pass");
		System.out.println(token.getToken());
		System.out.println(token.getNick());
		assertEquals(token.getValidUser(),true);
	}

}
