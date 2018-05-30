package com.guru.bharath;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author AB40286
 *
 */
@Controller
public class PersonController {

	@Value("${welcome.message}")
	private String welcomeMessage;

	@Value("${error.message}")
	private String errorMessage;

	private static List<Person> personList = new ArrayList<>();

	static {
		personList.add(new Person("Guru", "Raghaverndra"));
		personList.add(new Person("Bharath", "The Boss"));
	}

	@RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
	public String welcome(Model modelMap) {
		modelMap.addAttribute("message", this.welcomeMessage);
		return "index";

	}

	@RequestMapping(value = "/personList", method = RequestMethod.GET)
	public String getPersonList(Model model) {
		model.addAttribute("personList", personList);
		return "personList";
	}

	@RequestMapping(value = "/addPerson", method = RequestMethod.GET)
	public String addPerson(Model modelMap) {
		PersonForm personForm = new PersonForm();
		modelMap.addAttribute("personForm", personForm);
		return "addPerson";
	}

	@RequestMapping(value = "/addPerson", method = RequestMethod.POST)
	public String savePerson(Model model, @ModelAttribute("personForm") PersonForm personForm) {

		if (personForm.getFirstName() != null && personForm.getFirstName().length() > 0
				&& personForm.getLastName() != null && personForm.getFirstName().length() > 0) {
			Person person = new Person(personForm.getFirstName(), personForm.getLastName());
			personList.add(person);
			return "redirect:/personList";
		}

		model.addAttribute("errorMessage", this.errorMessage);
		return "addPerson";
	}

}
