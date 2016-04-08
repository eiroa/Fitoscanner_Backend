package ar.edu.unq.tip_eiroa_mauro.server.persistence;

import java.util.List;

import org.hibernate.Query;

import ar.edu.unq.tip_eiroa_mauro.server.model.Especie;
import ar.edu.unq.tip_eiroa_mauro.server.model.Muestra;
import ar.edu.unq.tip_eiroa_mauro.server.model.Usuario;

/**
 * 
 * @author eiroa
 *
 */
public class MuestraDao extends AbstractDao<Muestra> implements
GenericRepository<Muestra>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2334641144058210197L;

	@Override
	protected Class<Muestra> getDomainClass() {
		return Muestra.class;
	}
	
	public Muestra findByName(String nameSample){
		List<Muestra> samples = this.getSessionFactory().getCurrentSession()
				.createSQLQuery("select * from sample s where s.name='"+ nameSample+"'")
				.addEntity(Muestra.class)
				.list();
		if(samples.isEmpty())return null;
		return samples.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public List<Muestra> findAllOfUser(Usuario user, Boolean resolved){
		List<Muestra> samples = this.getSessionFactory().getCurrentSession()
				.createSQLQuery("select * from sample s where s.user="+user+" and "+ "s.resolved="+resolved)
				.addEntity(Muestra.class)
				.list();
		return samples;
	}
	
	public Especie getdeterminedSpecie(long idSample){
		List<Especie> species = (List<Especie>) this.getSessionFactory().getCurrentSession()
				.createSQLQuery(
			    "SELECT * FROM sample as sa, specie as sp WHERE sa.id = :id AND sa.id_specie_resolved = sp.id")
			    .addEntity(Especie.class)
			    .setParameter("id", idSample).list();
		return species.get(0);
	}
	
	/**
	 * Update con sql nativo, debido a que se decidio no mapear la especie resuelta a traves de hibernate
	 * @param sampleId
	 * @param specieResolved
	 * @param treatmentNames 
	 * @param specieName 
	 */
	public void resolveSample(long sampleId, long specieResolved, String specieName, String treatmentNames){
		Query update = this.getSessionFactory().getCurrentSession()
				.createSQLQuery(
			    "UPDATE sample SET  resolved = 1,"
			    + " id_specie_resolved = :idTarget , "
			    + " specie_name_resolved= :specieName , "
			    + " treatment_names_resolved = :treatmentNames ,"
			    + " date_treatment_obtained = (SELECT NOW()) "
			    + "WHERE id = :sampleId");
			update.setLong("idTarget", specieResolved);
			update.setLong("sampleId", sampleId);
			update.setString("specieName", specieName);
			update.setString("treatmentNames", treatmentNames);
			int updated = update.executeUpdate();
	}
	
	/**
	 * Se obtienen las especies resueltas en una consulta SQL adicional y se asignan a las muestras correspondientes
	 * @param findById
	 * @param resolved
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Muestra> findAllOfUserSQLStyle(Usuario findById,
			Boolean resolved) {
		
		
		List<Especie> species;
		List<Muestra> samples = (List<Muestra>) this.getSessionFactory().getCurrentSession()
				.createSQLQuery(
			    "SELECT * FROM sample as sa LEFT JOIN specie as sp ON "
			    + "sa.id_specie_resolved = sp.id WHERE "
			    + "sa.id_user_origin = :idUser "
			    + "AND sa.resolved = :resolved")
			    .addEntity(Muestra.class)
			    .setParameter("idUser", findById.getId())
			    .setParameter("resolved", resolved).list();
		
		if(samples.size() >0){
			for (Muestra sample : samples) {
				if(sample.getResolved()){
					 species = (List<Especie>) this.getSessionFactory().getCurrentSession()
								.createSQLQuery(
						    "SELECT sp.id,sp.name,sp.description,sp.isCrop,sp.isPlague,sp.scientific_name,sp.active "
						    + "FROM sample as sa, specie as sp "
						    + "WHERE sa.id = :id "
						    + "AND sa.id_specie_resolved = sp.id")
						    .addEntity(Especie.class)
						    .setParameter("id", sample.getId()).list();
					 sample.setSpecie(species.get(0));
				}
			}
		}
		
		
		return samples;
	}
	
	/**
	 * El siguiente método obtiene todas las muestras sin resolver aunque limitando la cantidad final
	 * La idea es que el usuario administrador tenga acceso a las últimas muestras por resolver de forma
	 * rapida
	 * @param limit
	 * @return
	 */
	public List<Muestra> findLatestUnresolvedSamples(Integer limitSamples) {
		// Se ordena por fecha directamente desde SQL mediante instruccion ORDER BY y además de forma
		// descendente con instrucción DESC
		List<Muestra> samples = (List<Muestra>) this.getSessionFactory().getCurrentSession()
				.createSQLQuery(
			  "SELECT * FROM sample where resolved = :cero ORDER BY sample_date DESC LIMIT :limit")
			  .addEntity(Muestra.class)
			  .setParameter("cero", 0)
			  .setParameter("limit", limitSamples)
			  .list();
		
		return samples;
	}

	@Override
	public List<Muestra> findByExample(Muestra exampleObject) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
//	@Override
//	public void save(Muestra entity) {
//		System.out.println("gogogo");
////		this.getSessionFactory().getCurrentSession().save(entity);
//		
//		
//		this.getSessionFactory().getCurrentSession().save(entity);
////		.createSQLQuery(
////				"insert into sample"
////						+ " ( images_hash, lat, lon, name, resolved, sample_date, id_user_origin) "
////						+ "values ( 'sa', 1, 1, 'mierda', 0, '2016-03-18 06:29:15', 26)")
////	  .executeUpdate();
//		System.out.println("did");
//		
//	}

}
