package biblio.model;

public class Biblio {
	// form:hidden - hidden value
	Integer id;
	
	//required
	String author;

	String title;

	Integer year;
	
	String journal;
			
	//citation key ex. 1
	String bibtexkey;
	
	//optional
	//ex. 42-111 or 7,41,73-97 or 43+
	String pages;
	
	String volume;
	
	String number;


	public boolean isNew() {
		return (this.id == null);
	}

	public Integer getId() {
		return id;
	}

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

	@Override
	public String toString() {
		return "Biblio [id=" + id + ", author=" + author + ", title=" + title + ", year=" + year +
				", journal=" + journal + ", bibtexKey=" + bibtexkey + ", pages=" + pages + 
				", volume=" + volume + ", number=" + number;
	}

}
