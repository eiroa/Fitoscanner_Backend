package ar.edu.unq.tip_eiroa_mauro.server.services;

import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.tip_eiroa_mauro.server.model.Imagen;
import ar.edu.unq.tip_eiroa_mauro.server.persistence.ImagenDao;

public class ImagenService {
	private ImagenDao imagenDao;
	

	
	public ImagenDao getImagenDao() {
		return imagenDao;
	}

	public void setImagenDao(ImagenDao imagenDao) {
		this.imagenDao = imagenDao;
	}

	@Transactional
	public String getImageBase64(long id) {
		Imagen target = this.imagenDao.findById(id);
		return target.getBase64();
	}
	
	@Transactional
	public Imagen getImage(long id) {
		Imagen target = this.imagenDao.findById(id);
		return target;
	}
	
	@Transactional
	public void deleteImage(long id) {
		this.imagenDao.deleteById(id);
	}
	
	
	@Transactional
	public void editImage(long idImage, String imageName, String base64, Boolean altersBase64){
		Imagen target =this.imagenDao.findById(idImage);
		target.setName(imageName);
		if(altersBase64){
			target.setBase64(base64);
		}
		this.imagenDao.update(target);
	}
	
	
}
