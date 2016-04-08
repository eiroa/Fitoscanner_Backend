package ar.edu.unq.tip_eiroa_mauro.server.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.tip_eiroa_mauro.server.model.Especie;
import ar.edu.unq.tip_eiroa_mauro.server.model.Imagen;
import ar.edu.unq.tip_eiroa_mauro.server.model.Muestra;
import ar.edu.unq.tip_eiroa_mauro.server.model.Tratamiento;
import ar.edu.unq.tip_eiroa_mauro.server.model.Usuario;
import ar.edu.unq.tip_eiroa_mauro.server.persistence.EspecieDao;
import ar.edu.unq.tip_eiroa_mauro.server.persistence.ImagenDao;
import ar.edu.unq.tip_eiroa_mauro.server.persistence.MuestraDao;
import ar.edu.unq.tip_eiroa_mauro.server.persistence.UsuarioDao;

public class MuestraService {
	private MuestraDao muestraDao;
	private ImagenDao imagenDao;
	private UsuarioDao usuarioDao;
	private EspecieDao especieDao;
	
	/*
	 * Un comparador  personalizado que ordena por fecha, alternativa java al order by de SQL
	 */
	class CustomComparator implements Comparator<Muestra> {
	    @Override
	    public int compare(Muestra m1, Muestra m2) {
	        return m1.getSample_date().compareTo(m2.getSample_date());
	    }
	}
	
	public void setEspecieDao(EspecieDao especieDao){
		this.especieDao = especieDao;
	}

	public void setUsuarioDao(UsuarioDao usuarioDao) {
		this.usuarioDao = usuarioDao;
	}

	public void setMuestraDao(MuestraDao muestraDao) {
		this.muestraDao = muestraDao;
	}
	
	public void setImagenDao(ImagenDao imagenDao) {
		this.imagenDao = imagenDao;
	}

	@Transactional
	public void saveMuestra1Imagen(String nameSample, String nameImagen, String base64){
		Muestra muestra = new Muestra(nameSample);
		Imagen img = new Imagen(nameSample, base64, muestra);
		this.muestraDao.save(muestra);
		this.imagenDao.save(img);
	}
	
	@Transactional
	/**
	 * Servicio para el guardado de la muestra, recibe en un mapa las imágenes
	 * compuesto por nombre y base 64
	 * 
	 * @param date
	 * @param isAnon
	 * @param imei
	 * @param lat
	 * @param lon
	 * @param nameSample
	 * @param samples
	 * @param hash
	 */
	public void saveSample(DateTime date,Boolean isAnon,String imei,
			String lat,String lon,String nameSample, Map<String,String> imgs,String hash){
		Usuario user = null;
		if(isAnon){
			 user = usuarioDao.findByImei(imei);
			if(user==null){
				System.out.println("saving new user... with imei:"+imei);
				Usuario nuevo = new Usuario(true,imei);
				usuarioDao.save(nuevo);
			}
		}
		System.out.println("trying to get saved user..");
		user = usuarioDao.findByImei(imei);
		System.out.println("user obtained has imei.." + user.getImei());
		Muestra sample= new Muestra(nameSample, lon, lat, date, user,hash);
		System.out.println("Attempting to save new sample...");
		this.muestraDao.save(sample);
		Muestra sampleSaved = this.muestraDao.findByName(nameSample);
		System.out.println("Attempting to obtained recently saved sample");
		Imagen img;
		for (Map.Entry<String, String> entry : imgs.entrySet())
		{
			img = new Imagen(entry.getKey(), entry.getValue(), sampleSaved);
			this.imagenDao.save(img);
		}	
	}
	
	@Transactional(readOnly=true)
	public List<Muestra> findAll(){
		List<Muestra> muestras =  muestraDao.findAll();
		//Ordenamos por fecha de muestra
		Collections.sort(muestras, new CustomComparator());
		return muestras;
	}
	
	@Transactional
	public List<Muestra> findSamplesOfUser(long userId,Boolean resolved) {
		List<Muestra> muestras =  muestraDao.findAllOfUser(usuarioDao.findById(userId),resolved);
		Collections.sort(muestras, new CustomComparator());
		return muestras;
	}
	
	@Transactional
	public List<Muestra> findSamplesOfUserSQLStyle(long userId,Boolean resolved) {
		List<Muestra> muestras =  muestraDao.findAllOfUserSQLStyle(usuarioDao.findById(userId),resolved);
		Collections.sort(muestras, new CustomComparator());
		return muestras;
	}
	
	/**
	 * Devuelva las ultimas muestra sin resolver ordenandas por fechas y limitadas a la cantidad por parametro
	 * @param limit
	 * @return
	 */
	@Transactional
	public List<Muestra> findLatestUnresolvedSamples(Integer limit){
		List<Muestra> muestras =  muestraDao.findLatestUnresolvedSamples(limit);
		return muestras;
	}
	
	
	@Transactional
	public void resolveSample(long sampleId, long specieResolved) {
		Especie specie = this.especieDao.findById(specieResolved);
		// Aislamos nombre de especie y tratamientos con motivo de registro historico
		// Ante una eventual deteccion automatica, sirve como información histórica para aumentar precision.
		String treatmentNames = "";
		if(specie.getTreatments().size() > 0){
			for (Tratamiento t : specie.getTreatments()) {
				if(treatmentNames.equals("")){
					treatmentNames = treatmentNames.concat(t.getName());
				}else{
					treatmentNames = treatmentNames.concat(" , " +t.getName());
				}
			}
		}else{
			treatmentNames = " NO DATA ";
		}
		
		this.muestraDao.resolveSample( sampleId, specieResolved,specie.getName(),treatmentNames);
	}
	
	@Transactional
	public Especie findSpecieDetermined(long sampleId) {
		return this.muestraDao.getdeterminedSpecie(sampleId);
	}
	
	@Transactional
	public void deleteSample(long sampleId) {
		this.muestraDao.deleteById(sampleId);
	}
	
}
