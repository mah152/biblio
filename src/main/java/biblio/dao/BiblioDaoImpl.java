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

@Repository
public class BiblioDaoImpl implements BiblioDao {

	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) throws DataAccessException {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

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

	@Override
	public List<Biblio> findAll() {

		String sql = "SELECT * FROM biblio";
		List<Biblio> result = namedParameterJdbcTemplate.query(sql, new BiblioMapper());

		return result;

	}

	@Override
	public void save(Biblio biblio) {

		KeyHolder keyHolder = new GeneratedKeyHolder();

		String sql = "INSERT INTO BIBLIO(AUTHOR, TITLE, YEAR, JOURNAL) "
				+ "VALUES ( :author, :title, :year, :journal)";

		namedParameterJdbcTemplate.update(sql, getSqlParameterByModel(biblio), keyHolder);
		biblio.setId(keyHolder.getKey().intValue());
		
	}

	private SqlParameterSource getSqlParameterByModel(Biblio biblio) {

		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("id", biblio.getId());
		paramSource.addValue("author", biblio.getAuthor());
		paramSource.addValue("title", biblio.getTitle());
		paramSource.addValue("year", biblio.getYear());
		paramSource.addValue("journal", biblio.getJournal());
		
		return paramSource;
	}

	private static final class BiblioMapper implements RowMapper<Biblio> {

		public Biblio mapRow(ResultSet rs, int rowNum) throws SQLException {
			Biblio biblio = new Biblio();
			biblio.setId(rs.getInt("id"));
			biblio.setAuthor(rs.getString("author"));
			biblio.setTitle( rs.getString("title" ));
			biblio.setYear( rs.getInt("year" ));
			biblio.setJournal( rs.getString("journal" ));
			return biblio;
		}
	}

}
