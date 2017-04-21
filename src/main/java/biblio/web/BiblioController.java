package biblio.web;

import java.io.FileReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.jbibtex.BibTeXDatabase;
import org.jbibtex.BibTeXEntry;
import org.jbibtex.BibTeXParser;
import org.jbibtex.Key;
import org.jbibtex.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import biblio.model.Biblio;
import biblio.service.BiblioService;
import biblio.validator.BiblioFormValidator;
/**
 * <h1>This is the BiblioController class</h1>
 * It contains fields and web request handler methods 
 * It is part of the web layer
 * 
 * @author  Mohammed Binhamed
 * @version 1.0
 */
//http://www.tikalk.com/redirectattributes-new-feature-spring-mvc-31/
//https://en.wikipedia.org/wiki/Post/Redirect/Get
//http://www.oschina.net/translate/spring-mvc-flash-attribute-example
@Controller
public class BiblioController {
	/**
	 * logger for printing debug information
	 */
	private final Logger logger = LoggerFactory.getLogger(BiblioController.class);
	
	/**
	 * bibtex file to export biblios to
	 */
	private final String exportFileName = "biblio-export.bib";
	
	/**
	 * bibtex file to import biblios from
	 */
	private final String importFileName = "biblio-import.bib";

	/**
	 * the biblio validator
	 */
	@Autowired
	BiblioFormValidator biblioFormValidator;

	/**
	 * binder will use the biblioFormValidator
	 * @param binder
	 */
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(biblioFormValidator);
	}

	/**
	 * the biblio service
	 */
	private BiblioService biblioService;

	/**
	 * autowire biblioService bean using setter
	 * @param biblioService
	 */
	@Autowired
	public void setBiblioService(BiblioService biblioService) {
		this.biblioService = biblioService;
	}

	/**
	 * handles get requests to the web root 
	 * by delegating them to showAllBiblios controller method to list all biblios
	 * @param model
	 * @return String
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model) {
		logger.debug("index()");
		return "redirect:/biblios";
	}
	/**
	 * handles get requests to show/list all biblios
	 * @param model
	 * @return jsp view name
	 */
	@RequestMapping(value = "/biblios", method = RequestMethod.GET)
	public String showAllBiblios(Model model) {

		logger.debug("showAllBiblios()");
		model.addAttribute("biblios", biblioService.findAll());
		return "biblios/list";
	}

	/**
	 * handles post requests from the submitted add/update biblio form, saves the biblio,
	 * and redirects to showBiblio handler to display the newly added/updated biblio
	 * @param biblio data from form
	 * @param result
	 * @param model
	 * @param redirectAttributes
	 * @return redirect string
	 */
	@RequestMapping(value = "/biblios", method = RequestMethod.POST)
	public String saveOrUpdateBiblio(@ModelAttribute("biblioForm") @Validated Biblio biblio,
			BindingResult result, Model model, final RedirectAttributes redirectAttributes) {

		logger.debug("saveOrUpdateBiblio() : {}", biblio);

		if (result.hasErrors()) {
			return "biblios/biblioform";
		}
		redirectAttributes.addFlashAttribute("css", "success");
		if(biblio.isNew()){
			redirectAttributes.addFlashAttribute("msg", "Bibliography added successfully!");
		} else{
			redirectAttributes.addFlashAttribute("msg", "Bibliography updated successfully!");
		}
		try{
			biblioService.saveOrUpdate(biblio);
		}catch( DuplicateKeyException ex){
			System.out.println( "Biblio already exists!\n" );
			System.out.println( biblio );
			result.rejectValue("bibtexkey", "Duplicate.biblioForm.bibtexkey" );
			return "biblios/biblioform";
		}
		
		// POST/REDIRECT/GET
		return "redirect:/biblios/" + biblio.getId();
		
	}
	/**
	 * handles get requests to show add biblio form/page
	 * creates a new biblio object and adds it to the model
	 * @param model
	 * @return jsp view name
	 */
	@RequestMapping(value = "/biblios/add", method = RequestMethod.GET)
	public String showAddBiblioForm(Model model) {

		logger.debug("showAddBiblioForm()");

		Biblio biblio = new Biblio();

		// set default values
		biblio.setAuthor("author123");
		biblio.setTitle("title123");
		biblio.setYear(2017);
		biblio.setJournal("journal123");
		biblio.setBibtexkey("at2017");
		model.addAttribute("biblioForm", biblio);

		return "biblios/biblioform";

	}
	/**
	 * handles get requests to show update biblio form 
	 * @param id of biblio to update 
	 * @param model
	 * @return biblioform view
	 */
	@RequestMapping(value = "/biblios/{id}/update", method = RequestMethod.GET)
	public String showUpdateForm(@PathVariable("id") int id, Model model) {

		logger.debug("showUpdateForm() : {}", id);
		Biblio biblio = biblioService.findById(id);
		model.addAttribute("biblioForm", biblio);

		return "biblios/biblioform";
	}
	/**
	 * handles get requests to show a biblio
	 * @param id of biblio to show
	 * @param model
	 * @return show.jsp view
	 */
	@RequestMapping(value = "/biblios/{id}", method = RequestMethod.GET)
	public String showBiblio(@PathVariable("id") int id, Model model) {

		logger.debug("showBiblio() id: {}", id);

		Biblio biblio = biblioService.findById(id);
		if (biblio == null) {
			model.addAttribute("css", "danger");
			model.addAttribute("msg", "Biblio not found");
		}
		model.addAttribute("biblio", biblio);

		return "biblios/show";

	}
	/**
	 * biblio not found handler 
	 * @param req
	 * @param ex
	 * @return show jsp view
	 */
	@ExceptionHandler(EmptyResultDataAccessException.class)
	public ModelAndView handleEmptyData(HttpServletRequest req, Exception ex) {

		logger.debug("handleEmptyData()");
		logger.error("Request: {}, error ", req.getRequestURL(), ex);

		ModelAndView model = new ModelAndView();
		model.setViewName("biblio/show");
		model.addObject("msg", "entry not found");

		return model;

	}
	/**
	 * handles get requests to delete a biblio
	 * 
	 * @param id of biblio to delete
	 * @param redirectAttributes
	 * @return redirect to handler showAllBiblios
	 */
	@RequestMapping(value = "biblios/{id}/delete", method = RequestMethod.GET)
	public String deleteBiblio(@PathVariable("id") int id, final RedirectAttributes redirectAttributes) {

		logger.debug("deleteBiblio() : {}", id);

		biblioService.delete(id);

		redirectAttributes.addFlashAttribute("css", "success");
		redirectAttributes.addFlashAttribute("msg", "The article has been deleted!");

		return "redirect:/biblios";
	}
	
	/**
	 * handler for get requests to export the biblio database
	 * @param redirectAttributes
	 * @return
	 */
	// export db
	@RequestMapping(value = "biblios/export", method = RequestMethod.GET)
	public String exportBiblios(final RedirectAttributes redirectAttributes) {

		logger.debug("exportBiblios() : {}");
		openExportFile();
		List<Biblio> data = biblioService.findAll();
		for ( Biblio biblio : data ) {
			String btype = "article";
			String author = biblio.getAuthor();
			String title = biblio.getTitle();
			String journal =  biblio.getJournal();
			Integer year = biblio.getYear();
			String bibtexkey =  biblio.getBibtexkey();
			String pages =  biblio.getPages();
			String volume =  biblio.getVolume();
			String number =  biblio.getNumber();
			exportStr( "@" + btype + "{ " + bibtexkey + ",\n" );
			exportStr( "author  =  {" + author +  "},\n");
			exportStr( "title   =  {" + title  +  "},\n");
			exportStr( "journal =  {" + journal + "},\n");
			exportStr( "year    =  " + year    +   ",\n");
			exportStr( "pages =  {" + pages + "},\n");
			exportStr( "volume =  {" + volume + "},\n");
			exportStr( "number =  {" + number + "},\n");
			exportStr( "}\n\n" );
		}
		closeExportFile();
		redirectAttributes.addFlashAttribute("css", "success");
		String msg = "Bibliography exported to " + exportFileName + "!"; 
		redirectAttributes.addFlashAttribute("msg", msg);

		return "redirect:/biblios";

	}
	
	/**
	 * handles get requests to import bibtex data from importFileName 
	 * uses bibtex parser from https://github.com/jbibtex/jbibtex
	 * @param redirectAttributes
	 * @return redirects to show all biblios
	 */
	@RequestMapping(value = "biblios/import", method = RequestMethod.GET)
	public String importBiblios(final RedirectAttributes redirectAttributes) {

		logger.debug("importBiblios() : {}");
		boolean skipped = false;
		Biblio biblio = null;
		try {
			BibTeXParser parser = new BibTeXParser();
			BibTeXDatabase database = parse(parser, importFileName);
			//List<BibTeXObject> objects = database.getObjects();

			Collection<BibTeXEntry> entries = (database.getEntries()).values();
			System.out.println( "num entries : " +  entries.size() );
			for(BibTeXEntry entry : entries) {
				biblio = new Biblio();
				//author
				Key key = new Key("author");
				Value value = entry.getField(key);
				if(value != null){
					biblio.setAuthor( value.toUserString() );
				}
				//title
				key = new Key("title");
				value = entry.getField(key);
				if(value != null)
					biblio.setTitle( value.toUserString() );
				//year
				key = new Key("year");
				value = entry.getField(key);
				if(value != null)
					biblio.setYear( Integer.parseInt( value.toUserString() ));
				//journal
				key = new Key("journal");
				value = entry.getField(key);
				if(value != null)
					biblio.setJournal( value.toUserString() );

				//bibtexkey
				key = new Key("bibtexkey");
				value = entry.getField(key);
				if(value != null){
					biblio.setBibtexkey( value.toUserString());
				}
				//pages
				key = new Key("pages");
				value = entry.getField(key);
				if(value != null)
					biblio.setPages( value.toUserString());

				//volume
				key = new Key("volume");
				value = entry.getField(key);
				if(value != null)
					biblio.setVolume( value.toUserString());

				//number
				key = new Key("number");
				value = entry.getField(key);
				if(value != null)
					biblio.setNumber( value.toUserString());
				try{
					biblioService.saveOrUpdate(biblio);
				}
				catch( DuplicateKeyException ex){
					System.out.println( "Biblio already exists, skipping import!\n" );
					System.out.println( biblio );
					skipped = true;
				}

				// The field is not defined
				if(value == null){
					continue;
				}
			}
		}
		catch(Exception e) {
			System.out.println( "Bibtex Parse Exception\n" + e );
		}
		redirectAttributes.addFlashAttribute("css", "success");
		String msg = "Bibliography imported from " + importFileName + "!";
		if( skipped ){
			msg = "Bibliography imported from " + importFileName + " Duplicates ignored!";
		}
		
		redirectAttributes.addFlashAttribute("msg", msg);
		return "redirect:/biblios";
	}
	/**
	 * parsing helper method
	 * @param parser bibtex parser from https://github.com/jbibtex/jbibtex
	 * @param path file to parse
	 * @return
	 */
	static  private BibTeXDatabase parse(BibTeXParser parser, String path) {
		try {
			Reader reader=null;
			try {
				reader = new FileReader(path);
				return parser.parse(reader);
			} catch(Exception e) {
				System.out.println( "Parser Exception Error\n" + e );
			} finally {
				reader.close();
			}
		} catch (Exception e ) { 
			System.out.println( "Reader  Exception Error\n" + e ); 
		}
		return null;
	}
	
	/**
	 * file writer
	 */
	PrintWriter writer;
	
	/**
	 * helper method to open export file
	 */
	void openExportFile() {
		try {
			writer =  new PrintWriter(exportFileName, "UTF-8");
		} catch (Exception e) {
			System.out.println( "Could not open export file\n" );
			System.exit(11);
		}
	}
	
	/**
	 * helper method to close export file
	 */
	void closeExportFile() {
		writer.close();
	}
	
	/**
	 * helper method to write a string to export file
	 * @param s
	 */
	void exportStr( String s ) {
		try{
			writer.print( s ); 

		} catch (Exception e) {
			System.out.println( "Error writing file\n" );
			System.exit(12);
		}
	}
	
	/**
	 * handles get requests to show search biblio form
	 * @param model
	 * @return searchform.jsp
	 */
	// search
	@RequestMapping(value = "/biblios/search", method = RequestMethod.GET)
	public String showSearchForm(Model model) {

		logger.debug("showSearchForm()");

		Biblio biblio = new Biblio();

		biblio.setAuthor("");
		biblio.setTitle("");
		//biblio.setYear(2017);
		biblio.setJournal("");
		model.addAttribute("searchform", biblio);

		return "biblios/searchform";

	}
	/**
	 * handles submit search form post requests
	 * 
	 * @param bibliow
	 * @param model
	 * @param redirectAttributes
	 * @return list view with search results
	 */
	@RequestMapping(value = "biblios/dosearch", method = RequestMethod.POST)
	public String searchBiblios(@ModelAttribute("bibliow") Biblio bibliow, Model model, final RedirectAttributes redirectAttributes) {
		logger.debug("searchBiblios() : {}");
		try {
			String title = bibliow.getTitle();
			title = title.substring(1);
			System.out.println( "dosearch starts:" + title );
			List<Biblio> lb = biblioService.findByTitle(title);
			System.out.println( " " + lb );
			model.addAttribute("biblios",lb);
			redirectAttributes.addFlashAttribute("css", "success");
			redirectAttributes.addFlashAttribute("msg", "Search Results:");
			return "biblios/list";       
		} catch (Exception e) {
			System.out.println( "dosearch exception: " + e );
		}
		return null;
	}
}

