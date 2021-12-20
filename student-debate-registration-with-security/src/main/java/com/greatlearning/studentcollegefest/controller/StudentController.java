package com.greatlearning.studentcollegefest.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.greatlearning.studentcollegefest.entity.Student;
import com.greatlearning.studentcollegefest.service.StudentService;

@Controller
//@RequestMapping("/students")
public class StudentController {

	@Autowired
	private StudentService studentService;

	public StudentController() {
		System.out.println("The controller class");
	}

	@GetMapping("/list")
	public String getStudents(Model theModel) {
		System.out.println("list");
		List<Student> theStudent = studentService.findAll();
		theModel.addAttribute("Student", theStudent);
		return "studentlist";
	}

	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {

		System.out.println("inside add form method");
		Student theStudent = new Student();

		theModel.addAttribute("Student", theStudent);

		return "studentform";
	}

	@RequestMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("studentid") int theId, Model theModel) {

		Student student = studentService.findById(theId);
		theModel.addAttribute("Student", student);

		return "studentform";
	}

	@PostMapping("/save")
	public String saveStudent(Student student) {
		studentService.save(student);

		return "redirect:/list";

	}

	@RequestMapping("/deleteStudent")
	public String deleteStudent(@RequestParam("studentid") int id) {
		studentService.DeleteById(id);
		return "redirect:/list";
	}

	@RequestMapping(value = "/403")
	public ModelAndView accesssDenied(Principal user) {

		ModelAndView model = new ModelAndView();

		if (user != null) {
			model.addObject("msg", "Hi " + user.getName() + ", you do not have permission to access this page!");
		} else {
			model.addObject("msg", "You do not have permission to access this page!");
		}

		model.setViewName("403");
		return model;

	}
}
