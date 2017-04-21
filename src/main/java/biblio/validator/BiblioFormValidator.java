package biblio.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import biblio.model.Biblio;
import biblio.service.BiblioService;
/**
* <h1>This class implements the spring Validator interface methods</h1>
*  It validates Biblio instances
* 
* @author  Mohammed Binhamed
* @version 1.0
*/
//http://docs.spring.io/spring/docs/current/spring-framework-reference/html/validation.html#validation-mvc-configuring
@Component
public class BiblioFormValidator implements Validator {
	
	/**
	 * autowire the biblio service bean
	 */
	@Autowired
	BiblioService biblioService;
	
	/**
	 * supports validation of Biblio instances only
	 * @return true if Biblio
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return Biblio.class.equals(clazz);
	}
	
	/**
	 * performs validation of a biblio
	 * author, title, year, journal, year can't be empty,
	 * year must be a positive number
	 * @param target biblio
	 * @param errors object
	 */
	@Override
	public void validate(Object target, Errors errors) {

		Biblio biblio = (Biblio) target;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "author", "NotEmpty.biblioForm.author");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "NotEmpty.biblioForm.title");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "year", "NotEmpty.biblioForm.year");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "journal", "NotEmpty.biblioForm.journal");
		//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "bibtexkey", "NotEmpty.biblioForm.bibtexkey");
				
		if(biblio.getYear()==null || biblio.getYear()<=0){
			errors.rejectValue("year", "NotEmpty.biblioForm.year");
		}
				
	}

}
