package biblio.service;

import java.util.List;

import biblio.model.Biblio;

public interface BiblioService {

	Biblio findById(Integer id);
	
	List<Biblio> findAll();

	void saveOrUpdate(Biblio biblio);
  void delete(int id);

}
