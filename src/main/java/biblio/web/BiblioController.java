package biblio.web;

import java.io.FileReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.jbibtex.BibTeXDatabase;
import org.jbibtex.BibTeXEntry;
import org.jbibtex.BibTeXObject;
import org.jbibtex.BibTeXParser;
import org.jbibtex.Key;
import org.jbibtex.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

//http://www.tikalk.com/redirectattributes-new-feature-spring-mvc-31/
//https://en.wikipedia.org/wiki/Post/Redirect/Get
//http://www.oschina.net/translate/spring-mvc-flash-attribute-example
@Controller
public class BiblioController {

	private final Logger logger = LoggerFactory.getLogger(BiblioController.class);

	@Autowired
	BiblioFormValidator biblioFormValidator;
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(biblioFormValidator);
	}

	private BiblioService biblioService;

	@Autowired
	public void setBiblioService(BiblioService biblioService) {
		this.biblioService = biblioService;
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model) {
		logger.debug("index()");
		return "redirect:/biblios";
	}

	// list page
	@RequestMapping(value = "/biblios", method = RequestMethod.GET)
	public String showAllBiblios(Model model) {

		logger.debug("showAllBiblios()");
		model.addAttribute("biblios", biblioService.findAll());
		return "biblios/list";

	}

	// save or update biblio
	@RequestMapping(value = "/biblios", method = RequestMethod.POST)
	public String saveOrUpdateBiblio(@ModelAttribute("biblioForm") @Validated Biblio biblio,
			BindingResult result, Model model, final RedirectAttributes redirectAttributes) {

		logger.debug("saveOrUpdateBiblio() : {}", biblio);

		if (result.hasErrors()) {
			return "biblios/biblioform";
		} else {

			redirectAttributes.addFlashAttribute("css", "success");
			if(biblio.isNew()){
				redirectAttributes.addFlashAttribute("msg", "Bibliography added successfully!");
			} else{
        redirectAttributes.addFlashAttribute("msg", "Bibliography updated successfully!");
      }
	
			biblioService.saveOrUpdate(biblio);
			
			// POST/REDIRECT/GET
			return "redirect:/biblios/" + biblio.getId();

			// POST/FORWARD/GET
			// return "biblio/list";

		}

	}

	// show add bibliography form
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
  // show update form
  @RequestMapping(value = "/biblios/{id}/update", method = RequestMethod.GET)
  public String showUpdateForm(@PathVariable("id") int id, Model model) {

    logger.debug("showUpdateForm() : {}", id);
    Biblio biblio = biblioService.findById(id);
    model.addAttribute("biblioForm", biblio);

    return "biblios/biblioform";

  }

	// show biblio
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

	@ExceptionHandler(EmptyResultDataAccessException.class)
	public ModelAndView handleEmptyData(HttpServletRequest req, Exception ex) {

		logger.debug("handleEmptyData()");
		logger.error("Request: {}, error ", req.getRequestURL(), ex);

		ModelAndView model = new ModelAndView();
		model.setViewName("biblio/show");
		model.addObject("msg", "entry not found");

		return model;

	}

  // delete entry
  @RequestMapping(value = "biblios/{id}/delete", method = RequestMethod.GET)
  public String deleteBiblio(@PathVariable("id") int id, final RedirectAttributes redirectAttributes) {

    logger.debug("deleteBiblio() : {}", id);

    biblioService.delete(id);

    redirectAttributes.addFlashAttribute("css", "success");
    redirectAttributes.addFlashAttribute("msg", "The article has been deleted!");

    return "redirect:/biblios";

  }

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
    redirectAttributes.addFlashAttribute("msg", "Bibliography has been exported to file biblio-data.bib!");

    return "redirect:/biblios";

  }

  // import bib-tex // https://github.com/jbibtex/jbibtex
  @RequestMapping(value = "biblios/import", method = RequestMethod.GET)
  public String importBiblios(final RedirectAttributes redirectAttributes) {

    logger.debug("importBiblios() : {}");
    try {
      BibTeXParser parser = new BibTeXParser();
      BibTeXDatabase database = parse(parser, fileName);
      //List<BibTeXObject> objects = database.getObjects();

      Collection<BibTeXEntry> entries = (database.getEntries()).values();
      System.out.println( "num entries : " +  entries.size() );
      for(BibTeXEntry entry : entries) {
        Biblio biblio = new Biblio();
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
              
        biblioService.saveOrUpdate(biblio);
        
        //update bibtexkey
        
        
        // The field is not defined
        if(value == null){
          continue;
        }
      }
    }  catch(Exception e) {
      System.out.println( "Bibtex Parse Exception\n" + e );
    }
    redirectAttributes.addFlashAttribute("css", "success");
    redirectAttributes.addFlashAttribute("msg", "Bibliography from file biblio-data.bib has been imported!");
    return "redirect:/biblios";
  }

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

  PrintWriter writer;
  String fileName = "biblio-data.bib";

  void openExportFile() {
    try {
      writer =  new PrintWriter(fileName, "UTF-8");
    } catch (Exception e) {
      System.out.println( "Could not open export file\n" );
      System.exit(11);
    }
  }

  void closeExportFile() {
    writer.close();
  }

  void exportStr( String s ) {
    try{
      writer.print( s ); 

    } catch (Exception e) {
      System.out.println( "Error writing file\n" );
      System.exit(12);
    }
  }

  // search
	@RequestMapping(value = "/biblios/search", method = RequestMethod.GET)
	public String showSearchForm(Model model) {

		logger.debug("showSearchForm()");

		Biblio biblio = new Biblio();

		biblio.setAuthor("");
		biblio.setTitle("");
//		biblio.setYear(2017);
		biblio.setJournal("");
		model.addAttribute("searchform", biblio);
	
		return "biblios/searchform";

	}

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

