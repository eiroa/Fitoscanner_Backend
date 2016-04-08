package ar.edu.unq.tip_eiroa_mauro.server.persistence;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import ar.edu.unq.tip_eiroa_mauro.server.model.Muestra;
import ar.edu.unq.tip_eiroa_mauro.server.model.Usuario;

/**
 * 
 * @author eiroa
 *
 */
public class UsuarioDao extends AbstractDao<Usuario> implements
GenericRepository<Usuario>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2937177872132878640L;

	@Override
	protected Class<Usuario> getDomainClass() {
		return Usuario.class;
	}
	
	public Usuario findByImei(String imei){
		System.out.println("trying to get user with imei..."+imei);
//		List<Usuario> users= this.getSessionFactory().getCurrentSession()
//				.createSQLQuery("select * from user u where u.imei= :imei")
//				.addEntity("user",Usuario.class)
//				.setParameter("imei",imei)
//				.list();
		
//		Criteria crit = getSessionFactory().getCurrentSession().createCriteria(Usuario.class);
		List<Usuario>users = getSessionFactory().getCurrentSession().createCriteria(Usuario.class)
			    .add( Restrictions.eq( "imei", new String(imei)
			    ) )
			    .list();
		
		System.out.println("Result obtained from query.." + users);
		if(users.isEmpty())return null;
		return users.get(0);
	}

	public Usuario findByNick(String nick) {
//		List<Usuario> users= this.getSessionFactory().getCurrentSession()
//				.createSQLQuery("select * from user u where u.nick= :nick")
//				.addEntity("user",Usuario.class)
//				.setParameter("nick",nick)
//				.list();
		List<Usuario>users = getSessionFactory().getCurrentSession().createCriteria(Usuario.class)
			    .add( Restrictions.eq( "nick", new String(nick)
			    ) )
			    .list();
		
		if(users.isEmpty())return null;
		return users.get(0);
	}

	@Override
	public List<Usuario> findByExample(Usuario exampleObject) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
