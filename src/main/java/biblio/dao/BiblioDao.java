package biblio.dao;

import java.util.List;

import biblio.model.Biblio;

public interface BiblioDao {

	Biblio findById(Integer id);

	List<Biblio> findAll();

	void save(Biblio biblio);
  void update(Biblio biblio);
  void delete(Integer id);

}
