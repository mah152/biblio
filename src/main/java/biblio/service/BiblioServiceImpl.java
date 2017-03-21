package biblio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import biblio.dao.BiblioDao;
import biblio.model.Biblio;

@Service("biblioService")
public class BiblioServiceImpl implements BiblioService {

	BiblioDao biblioDao;

	@Autowired
	public void setBiblioDao(BiblioDao biblioDao) {
		this.biblioDao = biblioDao;
	}

	@Override
	public Biblio findById(Integer id) {
		return biblioDao.findById(id);
	}

	@Override
	public List<Biblio> findAll() {
		return biblioDao.findAll();
	}

	@Override
	public void saveOrUpdate(Biblio biblio) {

		if (findById(biblio.getId())==null) {
			biblioDao.save(biblio);
		}
	}

}
