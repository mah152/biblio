package biblio.dao;

import java.util.List;

import biblio.model.Biblio;
/**
* <h1>Contains methods to find, save, update and delete Biblio data</h1>
* It is part of the data access layer
* 
* @author  Mohammed Binhamed
* @version 1.0
*/
public interface BiblioDao {

	Biblio findById(Integer id);

	List<Biblio> findAll();
	List<Biblio> findByTitle(String title);
	void save(Biblio biblio);
	void update(Biblio biblio);
	void delete(Integer id);

}
