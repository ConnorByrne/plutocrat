<<<<<<< HEAD
package net.plutocrat.company;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CompanyController {
	
	@Autowired
	private CompanyService service;
	
	@RequestMapping("/")
	public ModelAndView home() {
		ModelAndView mav = new ModelAndView("index");
		List<Company> listCompany = service.listAll();
		mav.addObject("listCompany", listCompany);
		return mav;
	}
	
	@RequestMapping("/companyview")
	public ModelAndView companyView(@RequestParam String name) {
		ModelAndView mav = new ModelAndView("companyview");
		List<Company> companyList = (List<Company>) service.findByName(name);
		Company company=companyList.get(0);
		mav.addObject("company", company);
		return mav;
	}
	
	@RequestMapping("/search")
	public ModelAndView search(@RequestParam String keyword) {
		ModelAndView mav = new ModelAndView("search");
		List<Company> result = service.search(keyword);
		mav.addObject("result", result);
		return mav;
	}
}
=======
package net.plutocrat.company;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CompanyController {
	
	@Autowired
	private CompanyService service;
	
	@RequestMapping("/")
	public ModelAndView home() {
		ModelAndView mav = new ModelAndView("index");
		List<Company> listCompany = service.listAll();
		mav.addObject("listCompany", listCompany);
		return mav;
	}
	
	@RequestMapping("/companyview")
	public ModelAndView companyView(@RequestParam String name) {
		ModelAndView mav = new ModelAndView("companyview");
		List<Company> companyList = (List<Company>) service.findByName(name);
		Company company=companyList.get(0);
		mav.addObject("company", company);
		return mav;
	}
	
	@RequestMapping("/search")
	public ModelAndView search(@RequestParam String keyword) {
		ModelAndView mav = new ModelAndView("search");
		List<Company> result = service.search(keyword);
		mav.addObject("result", result);
		return mav;
	}
}
>>>>>>> 3f1b7b7c045131098ae621106a4a497b14ee1e01
