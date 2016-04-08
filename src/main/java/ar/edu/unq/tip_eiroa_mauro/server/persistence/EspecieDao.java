package ar.edu.unq.tip_eiroa_mauro.server.persistence;

import java.util.List;

import ar.edu.unq.tip_eiroa_mauro.server.model.Especie;
import ar.edu.unq.tip_eiroa_mauro.server.model.Muestra;
import ar.edu.unq.tip_eiroa_mauro.server.model.Usuario;

/**
 * 
 * @author eiroa
 *
 */
public class EspecieDao extends AbstractDao<Especie> implements
GenericRepository<Especie>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2937177872132878640L;

	@Override
	protected Class<Especie> getDomainClass() {
		return Especie.class;
	}
	
	public Especie findByName(String name){
		List<Especie> species= this.getSessionFactory().getCurrentSession()
				.createSQLQuery("select * from specie e where e.name="+name)
				.addEntity(Especie.class)
				.list();
		if(species.isEmpty())return null;
		return species.get(0);
	}
	
	public List<Especie> findPlagues(){
		List<Especie> species= this.getSessionFactory().getCurrentSession()
				.createSQLQuery("select * from specie e where e.isPlague=1")
				.addEntity(Especie.class)
				.list();
		if(species.isEmpty())return null;
		return species;
	}
	
	public List<Especie>findCrops(){
		List<Especie> species= this.getSessionFactory().getCurrentSession()
				.createSQLQuery("select * from specie e where e.isCrop=1")
				.addEntity(Especie.class)
				.list();
		if(species.isEmpty())return null;
		return species;
	}

	@Override
	public List<Especie> findByExample(Especie exampleObject) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
