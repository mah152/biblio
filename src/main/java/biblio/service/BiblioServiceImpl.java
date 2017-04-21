package biblio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import biblio.dao.BiblioDao;
import biblio.model.Biblio;
/**
* <h1>Implements the methods in the BiblioService interface</h1>
*  It is part of the data service layer.  
* 
* @author  Mohammed Binhamed
* @version 1.0
*/
@Service("biblioService")
public class BiblioServiceImpl implements BiblioService {
	/**
	 * biblioDao field
	 */
	BiblioDao biblioDao;
	
	/**
	 * autowire the bean
	 * @param biblioDao
	 */
	@Autowired
	public void setBiblioDao(BiblioDao biblioDao) {
		this.biblioDao = biblioDao;
	}
	
	/**
	 * invokes biblioDao findById to find and return the biblio with given id
	 * @param id
	 * @return biblio
	 */
	@Override
	public Biblio findById(Integer id) {
		return biblioDao.findById(id);
	}
	
	/**
	 * invokes biblioDao findAll to find and return all biblios
	 * @return list of biblio-s
	 */
	@Override
	public List<Biblio> findAll() {
		return biblioDao.findAll();
	}
	
	/**
	 * invokes biblioDao findyTitle to return biblio-s with given title
	 * @param title
	 * @return list of biblio-s
	 */
	@Override
	public List<Biblio> findByTitle(String title) {
		return biblioDao.findByTitle(title);
	}

	/**
	 * invokes biblioDao save or update to save or update a biblio
	 * @param biblio
	 * 
	 */
	@Override
	public void saveOrUpdate(Biblio biblio) {

		if (findById(biblio.getId())==null) {
			biblioDao.save(biblio);
		} else {
			biblioDao.update(biblio);
		}
	}
	/**
	 * invokes biblioDao delete to delete the biblio with given db id
	 * @param id
	 */
	@Override
	public void delete(int id) {
		biblioDao.delete(id);
	}

}
