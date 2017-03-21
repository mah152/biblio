package biblio.model;

public class Biblio {
	// form:hidden - hidden value
	Integer id;

	// form:input - textbox
	String author;

	// form:input - textbox
	String title;

	// form:input - textbox
	Integer year;
	
	// form:input - textbox
	String journal;
	

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

	@Override
	public String toString() {
		return "Biblio [id=" + id + ", author=" + author + ", title=" + title + ", year=" + year +
				", journal=" + journal;
	}

}
