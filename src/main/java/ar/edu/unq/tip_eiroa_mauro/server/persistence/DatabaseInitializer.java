package ar.edu.unq.tip_eiroa_mauro.server.persistence;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.test.context.ContextConfiguration;

import ar.edu.unq.tip_eiroa_mauro.server.services.MuestraService;

public class DatabaseInitializer {
	private MuestraService muestraService;
	
	
	
	public MuestraService getMuestraService() {
		return muestraService;
	}

	public void setMuestraService(MuestraService muestraService) {
		this.muestraService = muestraService;
	}

	@PostConstruct
	public void populateDatabase(){
		
		loadSamples();
		Logger logger = Logger.getLogger(getClass());
		logger.info("==========================");
		logger.info("DATABASE POPULATED");
		logger.info("==========================");
	}
	
	private void loadSamples(){
		Map<String,String> imgs = new HashMap<String,String>();
		imgs.put("m1", "imageEncoded1");
		imgs.put("m2", "imageEncoded2");
		imgs.put("m3", "imageEncoded3");
		this.muestraService.saveSample(new DateTime(), true, "123", 
				"33° 53' 1'' S", "100° 2' 2'' W", "testSample", imgs, "sh1");
	}
}
