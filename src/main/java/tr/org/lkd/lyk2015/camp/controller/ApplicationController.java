package tr.org.lkd.lyk2015.camp.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import tr.org.lkd.lyk2015.camp.controller.validation.ApplicationFormValidator;
import tr.org.lkd.lyk2015.camp.model.dto.ApplicationFormDto;
import tr.org.lkd.lyk2015.camp.service.ApplicationService;
import tr.org.lkd.lyk2015.camp.service.ApplicationService.ValidationResult;
import tr.org.lkd.lyk2015.camp.service.CourseService;

@Controller
public class ApplicationController {

	@Autowired
	private ApplicationFormValidator applicationFormValidator;

	@Autowired
	private CourseService courseService;

	@Autowired
	private ApplicationService applicationService;

	@InitBinder
	protected void initBinder(final WebDataBinder binder) {
		binder.addValidators(this.applicationFormValidator);
	}

	@RequestMapping(value = "/basvuru", method = RequestMethod.GET)
	public String form(@ModelAttribute("form") ApplicationFormDto applicationFormDto, Model model) {

		model.addAttribute("courses", this.courseService.getAllActive());
		return "applicationForm";
	}

	@RequestMapping(value = "/basvuru", method = RequestMethod.POST)
	public String create(@ModelAttribute("form") @Valid ApplicationFormDto applicationFormDto,
			BindingResult bindingResult, Model model) {

		if (!bindingResult.hasErrors()) {
			model.addAttribute("courses", this.courseService.getAllActive());
			return "applicationForm";
		} else {
			this.applicationService.create(applicationFormDto);
			model.addAttribute("message", "Başvurunuz başarıyla alındı, epostanızı kontrol ediniz.");
			return "applicationSuccess";
		}
	}

	@RequestMapping(value = "/applications", method = RequestMethod.GET)
	public String list(Model model) {

		model.addAttribute("applications", this.applicationService.getAll());
		return "admin/applicationList";
	}

	@RequestMapping(value = "/applications/validate/{uuid}", method = RequestMethod.GET)
	public String validation(@PathVariable("uuid") String uuid, Model model) {

		ValidationResult result = this.applicationService.validate(uuid);

		model.addAttribute("message", result.toString());

		return "applicationSuccess";
	}

}
