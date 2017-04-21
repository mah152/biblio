package biblio.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import biblio.model.Biblio;
/**
* <h1>Implements the methods in the BiblioDao interface</h1>
*    
*
* @author  Mohammed Binhamed
* @version 1.0
*/

@Repository
public class BiblioDaoImpl implements BiblioDao {

	/**
	 * allows the use of named parameters with JdbcTemplate  
	 */
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	/**
	 * let spring autowire the bean
	 * @param namedParameterJdbcTemplate
	 * @throws DataAccessException
	 */
	@Autowired
	public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) throws DataAccessException {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	/**
	 * finds the biblio record with the given id and returns it
	 *  
	 * @param  id
	 * @return Biblio if found, null otherwise
	 */
	@Override
	public Biblio findById(Integer id) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);

		String sql = "SELECT * FROM biblio WHERE id=:id";

		Biblio result = null;
		try {
			result = namedParameterJdbcTemplate.queryForObject(sql, params, new BiblioMapper());
		} catch (EmptyResultDataAccessException e) {
			// do nothing, return null
		}
		return result;
	}
	
	/**
	 * finds all biblio-s from the database and returns them in a list
	 *  
	 * @return list with biblio records
	 */
	@Override
	public List<Biblio> findAll() {

		String sql = "SELECT * FROM biblio";
		List<Biblio> result = namedParameterJdbcTemplate.query(sql, new BiblioMapper());

		return result;
	}
	
	/**
	 * finds biblio-s with given title and returns them in a list
	 * @param  title
	 * @return list with biblio records
	 */
	@Override
	public List<Biblio> findByTitle(String title) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("title", title);

		String sql = 
				"SELECT * FROM biblio WHERE title LIKE '" + title + "%' OR title LIKE '%" + title + "%'";

		List<Biblio> result = namedParameterJdbcTemplate.query(sql, params, new BiblioMapper());
		System.out.println( "" + result.size() );
		return result;
	}

	/**
	 * saves given biblio in the database
	 * @param  biblio to save
	 */
	@Override
	public void save(Biblio biblio) {

		KeyHolder keyHolder = new GeneratedKeyHolder();

		String sql = "INSERT INTO BIBLIO(AUTHOR, TITLE, YEAR, JOURNAL, BIBTEXKEY, PAGES, VOLUME, NUMBER) "
				+ "VALUES ( :author, :title, :year, :journal, :bibtexkey, :pages, :volume, :number)";

		namedParameterJdbcTemplate.update(sql, getSqlParameterByModel(biblio), keyHolder);
		biblio.setId(keyHolder.getKey().intValue());
		
		//create bibtex key if user didn't provide
		if( biblio.getBibtexkey() == null ){
			biblio.setBibtexkey( new String( biblio.getId().toString()) );
			update(biblio);
		}
		else if( biblio.getBibtexkey().trim().isEmpty() ){
			biblio.setBibtexkey(biblio.getId().toString());
			update(biblio);
		}
	}
	
	/**
	 * updates given biblio in the database
	 * @param  biblio
	 */
	@Override
	public void update(Biblio biblio) {

		String sql =
				"UPDATE BIBLIO SET AUTHOR=:author, TITLE=:title, YEAR=:year, JOURNAL=:journal, BIBTEXKEY=:bibtexkey, PAGES=:pages, VOLUME=:volume, NUMBER=:number WHERE id=:id";

		namedParameterJdbcTemplate.update(sql, getSqlParameterByModel(biblio));
	}
	
	/**
	 * delete biblio from the database
	 * @param  id
	 */
	@Override
	public void delete(Integer id) {

		String sql = "DELETE FROM BIBLIO WHERE id= :id";
		namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource("id", id));
	}
	
	/**
	 * helper method used with NamedParameterJdbcTemplate 
	 * creates and returns a map with sql parameters 
	 * 
	 * @param  biblio
	 * @return map with sql parameters
	 */
	private SqlParameterSource getSqlParameterByModel(Biblio biblio) {

		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("id", biblio.getId());
		paramSource.addValue("author", biblio.getAuthor());
		paramSource.addValue("title", biblio.getTitle());
		paramSource.addValue("year", biblio.getYear());
		paramSource.addValue("journal", biblio.getJournal());
		paramSource.addValue("bibtexkey", biblio.getBibtexkey());
		paramSource.addValue("pages", biblio.getPages());
		paramSource.addValue("volume", biblio.getVolume());
		paramSource.addValue("number", biblio.getNumber());
		
		return paramSource;
	}
	
    /**
	* This class implements the mapRow method from the RowMapper interface  
	* which is used by the JdbcTemplate
	*/
	private static final class BiblioMapper implements RowMapper<Biblio> {
		/**
		 * maps a row from a jdbc ResultSet to a Biblio object.
		 * @param rs
		 * @param rowNum
		 * @return biblio
		 */
		public Biblio mapRow(ResultSet rs, int rowNum) throws SQLException {
			Biblio biblio = new Biblio();
			biblio.setId(rs.getInt("id"));
			biblio.setAuthor(rs.getString("author"));
			biblio.setTitle( rs.getString("title" ));
			biblio.setYear( rs.getInt("year" ));
			biblio.setJournal( rs.getString("journal" ));
			biblio.setBibtexkey( rs.getString("bibtexkey" ));
			biblio.setPages( rs.getString("pages" ));
			biblio.setVolume( rs.getString("volume" ));
			biblio.setNumber( rs.getString("number" ));
			return biblio;
		}
	}

}
