package biblio.service;

import java.util.List;

import biblio.model.Biblio;
/**
* <h1>This is the BiblioService interface </h1>
* It contains methods to find, save, update and delete Biblio data   
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
