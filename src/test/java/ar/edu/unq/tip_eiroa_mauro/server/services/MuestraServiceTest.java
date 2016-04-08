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

import ar.edu.unq.tip_eiroa_mauro.server.model.Imagen;
import ar.edu.unq.tip_eiroa_mauro.server.model.Muestra;
import ar.edu.unq.tip_eiroa_mauro.server.model.Usuario;
import ar.edu.unq.tip_eiroa_mauro.server.persistence.EspecieDao;
import ar.edu.unq.tip_eiroa_mauro.server.persistence.ImagenDao;
import ar.edu.unq.tip_eiroa_mauro.server.persistence.MuestraDao;
import ar.edu.unq.tip_eiroa_mauro.server.persistence.UsuarioDao;
import ar.edu.unq.tip_eiroa_mauro.server.services.MuestraService.CustomComparator;

@ContextConfiguration(locations = {"classpath:spring-test-context.xml"})
public class MuestraServiceTest extends AbstractTransactionalJUnit4SpringContextTests{
	@Autowired
	private MuestraDao muestraDao;
	
	@Autowired
	private ImagenDao imagenDao;
	
	@Autowired
	private UsuarioDao usuarioDao;
	
	@Autowired
	private EspecieDao especieDao;
	
	@Autowired
	private MuestraService muestraService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Test
	public void saveSampleOfNewUser(){
		System.out.println("Starting test save sample of new unregistered user, creating images");
		Map<String,String> imgs = new HashMap<String,String>();
		imgs.put("m1", "imageEncoded1");
		imgs.put("m2", "imageEncoded2");
		imgs.put("m3", "imageEncoded3");
		System.out.println("Attempting to save sample...");
		this.muestraService.saveSample(new DateTime(), true, "123", 
				"33° 53' 1'' S", "100° 2' 2'' W", "testSample", imgs, "sh1");
		System.out.println("Attempting to obtained new saved user");
		Usuario userSaved = this.usuarioService.findByImei("123");
		assertEquals("123", userSaved.getImei());
		
	}
	
}
