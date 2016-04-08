package ar.edu.unq.tip_eiroa_mauro.server.services;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.tip_eiroa_mauro.server.model.Especie;
import ar.edu.unq.tip_eiroa_mauro.server.model.Imagen;
import ar.edu.unq.tip_eiroa_mauro.server.model.ResolucionMuestra;
import ar.edu.unq.tip_eiroa_mauro.server.model.Tratamiento;
import ar.edu.unq.tip_eiroa_mauro.server.persistence.EspecieDao;
import ar.edu.unq.tip_eiroa_mauro.server.persistence.ImagenDao;
import ar.edu.unq.tip_eiroa_mauro.server.persistence.TratamientoDao;

public class TratamientoService {
	private EspecieDao especieDao;
	private TratamientoDao tratamientoDao;
	

	public void setEspecieDao(EspecieDao especieDao) {
		this.especieDao = especieDao;
	}


	public void setTratamientoDao(TratamientoDao tratamientoDao) {
		this.tratamientoDao = tratamientoDao;
	}
	

	@Transactional(readOnly=true)
	public List<Tratamiento> findAll(){
		return tratamientoDao.findAll();
	}
	
	@Transactional()
	public Tratamiento findById(long id){
		return tratamientoDao.findById(id);
	}
	
	@Transactional
	public void saveCrop(String name,String scName,
			String description){
		
	}
	
	@Transactional
	public void savePlague(String name,String scName,
			String description){

		
	}
	
	@Transactional
	public void saveNoImages(String name, String description,String idTargets, String tFreq, String freq, 
			String tQuan, String quan, String useEx, String extra1,String extra2, String extra3){
		Set<Especie> targets= new HashSet<Especie>();
		Especie target;
		//Ids de especies que ataca concatenedos en string y divididos por un -
		if (idTargets.contains("-")) {
			for (String retval: idTargets.split("-")){
				target = this.especieDao.findById(Long.parseLong(retval));
				targets.add(target);
		      }
		} else {
			target = this.especieDao.findById(Long.parseLong(idTargets));
			targets.add(target);
		}
			this.tratamientoDao.save(new Tratamiento(name, quan, tQuan, tFreq, freq, useEx, description, extra1,
					extra2,extra3,null, targets));
	}
	
	
	
	@Transactional
	public void deleteById(long id) {
		this.tratamientoDao.deleteById(id);
	}
	
	/*
	 * Se fuerza a que el tratamiento tenga imagenes
	 */
	@Transactional
	public void save3Images(String name, String desc, String idTargets,String im1, String base64_1,
			String im2, String base64_2, String im3, String base64_3) {
		Set<Especie> targets= new HashSet<Especie>();
		Especie target;
		//Ids de especies que ataca concatenedos en string y divididos por un -
		if (idTargets.contains("-")) {
			for (String retval: idTargets.split("-")){
				target = this.especieDao.findById(Long.parseLong(retval));
				targets.add(target);
		      }
		} else {
			target = this.especieDao.findById(Long.parseLong(idTargets));
			targets.add(target);
		}
		List<Imagen> imagenes = new LinkedList<Imagen>(); 
		
		Imagen i1 = new Imagen(im1, base64_1);
		Imagen i2 = new Imagen(im2, base64_2);
		Imagen i3 = new Imagen(im3, base64_3);
		imagenes.add(i1);
		imagenes.add(i2);
		imagenes.add(i3);
		this.tratamientoDao.save(new Tratamiento(name,desc,imagenes,targets));
	}

	@Transactional	
	public void saveTreatmentImage(long id, String imageName, String base64) {
		Tratamiento target =this.tratamientoDao.findById(id);
		Imagen img = new Imagen(imageName, base64);
		target.getImages().add(img);
		this.tratamientoDao.update(target);
	}

	@Transactional
	public void editNoImages(long idTreatment, String name, String desc,
			String idTargets, String tFreq, String freq, String tQuan, String quan, String useEx,
			String extra1, String extra2, String extra3) {
		Tratamiento treatment = this.tratamientoDao.findById(idTreatment);
		Set<Especie> targets= new HashSet<Especie>();
		Especie target;
		//Ids de especies que ataca concatenedos en string y divididos por un -
		if (idTargets.contains("-")) {
			for (String retval: idTargets.split("-")){
				target = this.especieDao.findById(Long.parseLong(retval));
				targets.add(target);
		      }
		} else {
			target = this.especieDao.findById(Long.parseLong(idTargets));
			targets.add(target);
		}
		treatment.setTargets(targets);
		treatment.setName(name);
		treatment.setDescription(desc);
		treatment.setFrequency(freq);
		treatment.setTypeFrequency(tFreq);
		treatment.setTypeQuantity(tQuan);
		treatment.setQuantity(quan);
		treatment.setUseExplanation(useEx);
		treatment.setExtraLink1(extra1);
		treatment.setExtraLink2(extra2);
		treatment.setExtraLink3(extra3);
		
		//update
		this.tratamientoDao.update(treatment);
		
	}

	@Transactional
	public void addSpecieTarget(long idTreatment, long idTarget) {
		Tratamiento treatment = this.tratamientoDao.findById(idTreatment);
		treatment.getTargets().add(this.especieDao.findById(idTarget));
		this.tratamientoDao.update(treatment);
	}
	
	@Transactional
	public void deleteSpecieTarget(long idTreatment, long idTarget) {
		Tratamiento treatment = this.tratamientoDao.findById(idTreatment);
		treatment.getTargets().remove(this.especieDao.findById(idTarget));
		this.tratamientoDao.update(treatment);
	}

	@Transactional
	public void saveWith1Image(String name, String desc, String idTargets,
			String im, String base64, String tFreq, String freq, String tQuan, String quan, String useEx, 
			String extra1,String extra2,String extra3) {
		Set<Especie> targets= new HashSet<Especie>();
		Especie target;
		if (idTargets.contains("-")) {
			for (String retval: idTargets.split("-")){
				target = this.especieDao.findById(Long.parseLong(retval));
				targets.add(target);
		      }
		} else {
			target = this.especieDao.findById(Long.parseLong(idTargets));
			targets.add(target);
		}
		List<Imagen> imagenes = new LinkedList<Imagen>(); 
		
		Imagen i1 = new Imagen(im, base64);

		imagenes.add(i1);
		this.tratamientoDao.save(new Tratamiento(name, quan, tQuan, tFreq, freq, useEx, desc, extra1,extra2,extra3,imagenes, targets));
		
	}
	
	/**
	 * Metodo generico para guardar un tratamiento nueva con o sin imagenes
	 * 
	 * @param name
	 * @param isCrop
	 * @param isPlague
	 * @param scientificName
	 * @param desc
	 * @param imagesRaw
	 */
	@Transactional
	public void saveWithNImages(String name, String desc, String idTargets,
			String tFreq, String freq, String tQuan, String quan, String useEx, 
			String extra1,String extra2,String extra3,
			Map<String,String> imagesRaw) {
		
		
		Set<Especie> targets= new HashSet<Especie>();
		Especie target;
		if (idTargets.contains("-")) {
			for (String retval: idTargets.split("-")){
				target = this.especieDao.findById(Long.parseLong(retval));
				targets.add(target);
		      }
		} else {
			target = this.especieDao.findById(Long.parseLong(idTargets));
			targets.add(target);
		}
		
		Tratamiento newTreatment = new Tratamiento(name, quan, tQuan, tFreq, freq, useEx, desc, extra1,extra2,extra3,null, targets);
		
		if(imagesRaw == null || imagesRaw.isEmpty()){
			this.tratamientoDao.save(newTreatment);
		}else{
			List<Imagen> imagesProcessed = new LinkedList<Imagen>(); 
			
			// { titulo -> base64}
			for (Map.Entry<String, String> entry : imagesRaw.entrySet())
			{
				imagesProcessed.add(new Imagen(entry.getKey(), entry.getValue()));
			}
			newTreatment.setImages(imagesProcessed);
			this.tratamientoDao.save(newTreatment);
		}
	}

	@Transactional
	public ResolucionMuestra requestTreatment(String imei, String hash) {
		return this.tratamientoDao.requestTreatments(imei, hash);
	}
	
	
}	
