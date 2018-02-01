package pl.coderslab.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pl.coderslab.model.Student;
import pl.coderslab.service.StudentService;

@Controller
public class StudentController {
    private StudentService service;

    @Autowired
    public StudentController(StudentService service) {
        this.service = service;
    }

    @RequestMapping("/students/list")
    public String list(Model model) {
        model.addAttribute("list", service.listAllStudents());
        model.addAttribute("some", "someValue");
        return "student/studentList";
    }

    @GetMapping("/students/show/{id}")
    public String show(Model model, @PathVariable long id) {
        Student student = service.getStudentById(id);
        model.addAttribute("student", student);
        return "student/addStudent";
    }
    @GetMapping("/students/add")
    public String showForm(){
        return "student/addStudent";
    }
    
    @PostMapping("/students/add")
    public String submitForm (@Valid @ModelAttribute("student") Student student, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "student/addStudent";
        }
        return "redirect:/students/list";
    }

}
