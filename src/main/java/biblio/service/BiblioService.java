package biblio.service;

import java.util.List;

import biblio.model.Biblio;
/**
* <h1>Contains methods to find, save, update and delete Biblio data  </h1>
* 
* It is part of the data service layer
* 
* @author  Mohammed Binhamed
* @version 1.0
*/
public interface BiblioService {

	Biblio findById(Integer id);

	List<Biblio> findAll();
	List<Biblio> findByTitle(String title);

	void saveOrUpdate(Biblio biblio);
	void delete(int id);

}
