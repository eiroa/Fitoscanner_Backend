package ar.edu.unq.tip_eiroa_mauro.server.persistence;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unq.tip_eiroa_mauro.server.model.Especie;
import ar.edu.unq.tip_eiroa_mauro.server.model.Muestra;
import ar.edu.unq.tip_eiroa_mauro.server.model.ResolucionMuestra;
import ar.edu.unq.tip_eiroa_mauro.server.model.Tratamiento;
import ar.edu.unq.tip_eiroa_mauro.server.model.Usuario;

/**
 * 
 * @author eiroa
 *
 */
public class TratamientoDao extends AbstractDao<Tratamiento> implements
GenericRepository<Tratamiento>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2937177872132878640L;

	@Override
	protected Class<Tratamiento> getDomainClass() {
		return Tratamiento.class;
	}
	
	public Tratamiento findByName(String name){
		List<Tratamiento> treatments= this.getSessionFactory().getCurrentSession()
				.createSQLQuery("select * from treatment t where t.name="+ name).list();
		if(treatments.isEmpty())return null;
		return treatments.get(0);
	}
	
	
	@SuppressWarnings("unchecked")
	public ResolucionMuestra requestTreatments(String imei, String hash){
		
		ResolucionMuestra result = new ResolucionMuestra();
		result.setValid(true);
		List<Usuario> users= this.getSessionFactory().getCurrentSession()
				.createSQLQuery("select * from user u where u.imei="+ imei)
				.addEntity(Usuario.class)
				.list();
		
		if(users.isEmpty()){
			result.setValid(false);
			result.setMessage("Usuario invalido, por favor reenvie la muestra");
			return result;
		}else{
			result.setUsuario(users.get(0));
		}
		
		List<Muestra> samples = this.getSessionFactory().getCurrentSession()
				.createSQLQuery("select * from sample s where s.images_hash='"+ hash+"'")
				.addEntity(Muestra.class)
				.list();
		if(samples.isEmpty() || result.getUsuario().getId() != samples.get(0).getUser().getId()){
			result.setValid(false);
			result.setMessage("Muestra invalida, por favor reenvie la muestra");
			return result;
		}else{
			result.setIdSample(samples.get(0).getId());
			result.setSampleName(samples.get(0).getName());
			result.setResolved(samples.get(0).getResolved());
		}
		Muestra sample = samples.get(0);
		if(sample.getResolved()){
			List<Especie>species = (List<Especie>) this.getSessionFactory().getCurrentSession()
					.createSQLQuery(
				    "SELECT sp.id,sp.name,sp.description,sp.isCrop,sp.isPlague,sp.scientific_name,sp.active "
				    + "FROM sample as sa, specie as sp "
				    + "WHERE sa.id = :id "
				    + "AND sa.id_specie_resolved = sp.id")
				    .addEntity(Especie.class)
				    .setParameter("id", sample.getId()).list();
			 result.setSpecie(species.get(0));
			List<Tratamiento> treatments= (List<Tratamiento>) this.getSessionFactory().getCurrentSession()
					.createSQLQuery(
				    "SELECT * FROM treatment as tr LEFT JOIN treatmentxspecie as txsp ON "
				    + "tr.id= txsp.id_treatment WHERE "
				    + "txsp.id_specie = :idSpecie ")
				    .addEntity(Tratamiento.class)
				    .setParameter("idSpecie", result.getSpecie().getId()).list();
			result.setTratamientos(treatments);
			result.setMessage("La muestra ha sido identificada satisfactoriamente");
		}else{
			result.setMessage("La muestra aun no ha podidio ser resuelta");
		}
		return result;
	}

	@Override
	public List<Tratamiento> findByExample(Tratamiento exampleObject) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
