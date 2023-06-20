package in.vasanth.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import in.vasanth.binding.DashBoardForm;
import in.vasanth.binding.EnquiryForm;
import in.vasanth.binding.EnquirySearch;
import in.vasanth.entity.EnquiryDtls;
import in.vasanth.service.EnquiryService;

@Controller
public class EnquiryController {

	@Autowired
	private EnquiryService enqService;

	@Autowired
	private HttpSession session;

	@GetMapping("/logout")
	public String logout() {

		session.invalidate();
		return "index";
	}

	@PostMapping("/saveEnquiry")
	public String addEnquiries(@ModelAttribute("formDtls") EnquiryForm form, Model model) {
		System.out.println(form);
		boolean status = enqService.addEnquiry(form);
		if (status) {
			model.addAttribute("success", "Enquiry is added");
		} else {
			model.addAttribute("error", "Problem occured try again");
		}

		return "add-enquiry";

	}

	@GetMapping("/addEnquiry")
	public String addEnquiry(Model model) {

		List<String> courseNames = enqService.getCourseNames();

		List<String> enquiryStatus = enqService.getEnquiryStatus();
		init(model, courseNames, enquiryStatus);

		return "add-enquiry";
	}

	private void init(Model model, List<String> courseNames, List<String> enquiryStatus) {
		model.addAttribute("courses", courseNames);
		model.addAttribute("enqStatus", enquiryStatus);
		model.addAttribute("formDtls", new EnquiryForm());
	}

	@GetMapping("/viewEnquiry")
	public String viewEnquiry(Model model) {
		List<String> courseNames = enqService.getCourseNames();

		List<String> enquiryStatus = enqService.getEnquiryStatus();

		init(model, courseNames, enquiryStatus);
		Integer userId = (Integer) session.getAttribute("userId");
		List<EnquiryDtls> enquiries = enqService.getEnquiries(userId);

		model.addAttribute("enquiries", enquiries);

		return "view-enquiries";
	}

	@GetMapping("/filterEnquiry")
	public String filterEnquiry(@RequestParam String cname, @RequestParam String status, @RequestParam String classmode,
			Model model) {
		EnquirySearch criteria = new EnquirySearch();
		criteria.setClassMode(classmode);
		criteria.setCourse(cname);
		criteria.setEnqStatus(status);
		System.out.println(criteria);
		Integer userId = (Integer) session.getAttribute("userId");
		List<EnquiryDtls> filteredEnquiries = enqService.getEnquiries(userId, criteria);
		model.addAttribute("enquiries", filteredEnquiries);

		return "filtered";
	}

	@GetMapping("/dashboard")
	public String dashboardResponse(Model model) {
		Integer userId = (Integer) session.getAttribute("userId");
		DashBoardForm dashboardData = enqService.getDashboardData(userId);

		model.addAttribute("dashData", dashboardData);

		return "dashboard";
	}

	@GetMapping("/edit")
	public String editAndUpdate(@RequestParam("enqId") Integer enquiryId, Model model) {
		EnquiryDtls editEnquiry = enqService.editEnquiry(enquiryId);
		System.out.println(enquiryId);
		
		
		EnquiryForm form=new EnquiryForm();
		BeanUtils.copyProperties(editEnquiry, form);
		System.out.println(form);
		List<String> courseNames = enqService.getCourseNames();

		List<String> enquiryStatus = enqService.getEnquiryStatus();
		model.addAttribute("courses", courseNames);
		model.addAttribute("enqStatus", enquiryStatus);
		
		model.addAttribute("formDtls", form);
		

		return "add-enquiry";
	}

}
