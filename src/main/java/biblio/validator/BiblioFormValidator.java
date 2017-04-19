package biblio.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import biblio.model.Biblio;
import biblio.service.BiblioService;

//http://docs.spring.io/spring/docs/current/spring-framework-reference/html/validation.html#validation-mvc-configuring
@Component
public class BiblioFormValidator implements Validator {
		
	@Autowired
	BiblioService biblioService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Biblio.class.equals(clazz);
	}

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
