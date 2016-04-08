package ar.edu.unq.tip_eiroa_mauro.server.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.tip_eiroa_mauro.server.model.Especie;
import ar.edu.unq.tip_eiroa_mauro.server.model.Imagen;
import ar.edu.unq.tip_eiroa_mauro.server.model.Tratamiento;
import ar.edu.unq.tip_eiroa_mauro.server.persistence.EspecieDao;
import ar.edu.unq.tip_eiroa_mauro.server.persistence.ImagenDao;

public class EspecieService {
	private EspecieDao especieDao;
	private ImagenDao imagenDao;


	public void setEspecieDao(EspecieDao especieDao) {
		this.especieDao = especieDao;
	}
	

	public void setImagenDao(ImagenDao imagenDao) {
		this.imagenDao = imagenDao;
	}


	@Transactional(readOnly=true)
	public List<Especie> findAll(){
		List<Especie> results = especieDao.findAll();
		for (Especie especie : results) {
			List<String> tnames = new ArrayList<String>();
			for (Tratamiento t : especie.getTreatments()) {
				tnames.add(t.getName());
			}
			especie.setTreatmentNames(tnames);
		}
		return results;
	}
	
	@Transactional(readOnly=true)
	public List<Especie> findPlagues(){
		return especieDao.findPlagues();
	}
	
	@Transactional(readOnly=true)
	public List<Especie> findCrops(){
		return especieDao.findCrops();
	}
	
	@Transactional()
	public Especie findById(long id){
		return especieDao.findById(id);
	}
	
	@Transactional
	public void saveCrop(String name,String scName,
			String description){
//			this.especieDao.save(new Usuario(true,imei));

		
	}
	
	@Transactional
	public void savePlague(String name,String scName,
			String description){
//			this.especieDao.save(new Usuario(true,imei));

		
	}
	
	@Transactional
	@Deprecated
	public void saveNoImages(String name,Boolean isCrop,
			Boolean isPlague, String scientificName, String description){
			this.especieDao.save(new Especie(name, isCrop, isPlague, scientificName, description,true));
	}
	
	
	@Transactional
	public void editSpecieNoImages(long id,Boolean isCrop,Boolean isPlague,String name,String scientific_name,
			String description){
		Especie target =this.especieDao.findById(id);
		target.setIsCrop(isCrop);
		target.setIsPlague(isPlague);
		target.setScientific_name(scientific_name);
		target.setName(name);
		target.setDescription(description);	
		target.setImages(target.getImages());
		this.especieDao.update(target);
	}
	
	@Transactional
	public void editSpecieImage(long id,long idImage, String imageName, String base64){
		Especie target =this.especieDao.findById(id);
		Imagen img;
		for (Imagen imagen : target.getImages()) {
			if(imagen.getId() == idImage){
				imagen.setName(imageName);
				imagen.setBase64(base64);
				return;
			}
		}
		this.especieDao.update(target);
	}
	
	@Transactional
	public void activateSpecie(long id){
		Especie target =this.especieDao.findById(id);
		target.setActive(true);
		this.especieDao.update(target);
	}
	
	@Transactional
	public void disableSpecie(long id){
		Especie target =this.especieDao.findById(id);
		target.setActive(false);
		this.especieDao.update(target);
	}
	
	@Transactional
	public void deleteSpecieImage(long id,long idImage){
		Especie target =this.especieDao.findById(id);
		Imagen img = null;
		for (Imagen imagen : target.getImages()) {
			if(imagen.getId() == idImage){
				img = imagen;
				return;
			}
		}
		target.getImages().remove(img);
		this.imagenDao.deleteById(idImage);
		this.especieDao.update(target);
	}
	
	
	@Transactional
	public void deleteById(long id) {
		this.especieDao.deleteById(id);
	}
	
	/*
	 * Se fuerza a que la especie tenga imagenes
	 */
	@Transactional
	@Deprecated
	public void saveWith4Images(String name, Boolean isCrop, Boolean isPlague,
			String scientificName, String desc, String im1, String base64_1,
			String im2, String base64_2, String im3, String base64_3,
			String im4, String base64_4) {
		
		HashedMap images = new HashedMap(); 
		images.put(im1, base64_1);
		images.put(im2, base64_2);
		images.put(im3, base64_3);
		images.put(im3, base64_4);
		
		saveWithNImages(name, isCrop, isPlague, scientificName, desc, images);
	}

	@Transactional
	public  void updateSpecieImage(long id, String imageName, String base64) {
		Especie target =this.especieDao.findById(id);
		Imagen img = new Imagen(imageName, base64);
		target.getImages().add(img);
		this.especieDao.update(target);
		
	}

	/**
	 * Metodo generico para guardar  una especie nueva con o sin imagenes
	 * 
	 * @param name
	 * @param isCrop
	 * @param isPlague
	 * @param scientificName
	 * @param desc
	 * @param imagesRaw
	 */
	@Transactional
	public void saveWithNImages(String name, boolean isCrop,
			boolean isPlague, String scientificName, String desc,
			Map<String,String> imagesRaw) {
		
		Especie target = new Especie(name, isCrop, isPlague, scientificName, desc,true);
		if(imagesRaw == null || imagesRaw.isEmpty()){
			this.especieDao.save(target);
		}else{
			List<Imagen> imagesProcessed = new LinkedList<Imagen>(); 
			
			// { titulo -> base64}
			for (Map.Entry<String, String> entry : imagesRaw.entrySet())
			{
				imagesProcessed.add(new Imagen(entry.getKey(), entry.getValue()));
			}
			target.setImages(imagesProcessed);
			this.especieDao.save(target);
		}
	}
	
}	
