package biblio.model;
/**
* <h1>This is the Biblio model class implementation</h1>
* It contains the fields, getters, setters and toString methods
* 
* @author  Mohammed Binhamed
* @version 1.0
*/
public class Biblio {
	/**
	 * database id, automatically generated in the database
	 */
	Integer id;
	
	/**
	 * name of the author/s
	 */
	String author;
	
    /**
     * article title
     */
	String title;
	
	/**
	 * publication year
	 */
	Integer year;
	
	/**
	 * journal where article was published
	 */
	String journal;
			
	/**
	 * bibtex citation key
	 */
	String bibtexkey;
	
	/**
	 * article pages from journal
	 * ex. 42-111 or 7,41,73-97 or 43+
	 * 
	 */
	String pages;
	
	/**
	 * journal volume
	 */
	String volume;
	
	/**
	 * journal number
	 */
	String number;

	/**
	 * this method is used to determine if a new biblio is being added,
	 * or an existing biblio is being updated 
	 * @return boolean
	 */
	public boolean isNew() {
		return (this.id == null);
	}
	/**
	 * 
	 * @return Integer returns the database id 
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * set the database id
	 * @param id 
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}
	public String getJournal() {
		return journal;
	}

	public void setJournal(String journal) {
		this.journal = journal;
	}
	
	public String getBibtexkey() {
		return bibtexkey;
	}

	public void setBibtexkey(String bibtexkey) {
		this.bibtexkey = bibtexkey;
	}
	public String getPages() {
		return pages;
	}

	public void setPages(String pages) {
		this.pages = pages;
	}
	
	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}
	
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	/**
	 * string representation of biblio, useful in debugging
	 * @return String 
	 */
	@Override
	public String toString() {
		return "Biblio [id=" + id + ", author=" + author + ", title=" + title + ", year=" + year +
				", journal=" + journal + ", bibtexKey=" + bibtexkey + ", pages=" + pages + 
				", volume=" + volume + ", number=" + number;
	}

}
