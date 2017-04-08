package biblio.web;

import javax.servlet.http.HttpServletRequest;

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
//import javax.validation.Valid;

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
  // show update form
  @RequestMapping(value = "/biblios/{id}/update", method = RequestMethod.GET)
  public String showUpdateBiblioForm(@PathVariable("id") int id, Model model) {

    logger.debug("showUpdateBiblioForm() : {}", id);

    Biblio biblio = biblioService.findById(id);
    model.addAttribute("biblioForm", biblio);

    populateDefaultModel(model);

    return "biblios/biblioform";
  }

  // delete entry
  @RequestMapping(value = "biblios/{id}/delete", method = RequestMethod.POST)
  public String deleteBiblio(@PathVariable("id") int id, final RedirectAttributes redirectAttributes) {

    logger.debug("deleteBiblio() : {}", id);

    biblioService.delete(id);

    redirectAttributes.addFlashAttribute("css", "success");
    redirectAttributes.addFlashAttribute("msg", "Biblio-entry is deleted!");

    return "redirect:/biblios";

  }

}
