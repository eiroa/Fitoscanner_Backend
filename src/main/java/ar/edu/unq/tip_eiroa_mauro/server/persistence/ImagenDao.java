package ar.edu.unq.tip_eiroa_mauro.server.persistence;

import java.util.List;

import ar.edu.unq.tip_eiroa_mauro.server.model.Imagen;
import ar.edu.unq.tip_eiroa_mauro.server.model.Muestra;

public class ImagenDao extends AbstractDao<Imagen> implements
GenericRepository<Imagen>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4187595638948629134L;

	@Override
	protected Class<Imagen> getDomainClass() {
		return Imagen.class;
	}

	@Override
	public List<Imagen> findByExample(Imagen exampleObject) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
