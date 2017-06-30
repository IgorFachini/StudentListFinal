package fi.haagahelia.course.web;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.InternetAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;

import fi.haagahelia.course.domain.Student;
import fi.haagahelia.course.domain.StudentRepository;
import it.ozimov.springboot.mail.model.Email;
import it.ozimov.springboot.mail.model.defaultimpl.DefaultEmail;
import it.ozimov.springboot.mail.service.EmailService;

@Controller
public class StudentController {
	@Autowired
    private StudentRepository repository; 

	@Autowired
	public EmailService emailService;

	@RequestMapping(value = "/call", method = RequestMethod.POST)
	public String call(@RequestParam("present") ArrayList<String> values, Model model)  {
		List<Student> students = (List<Student>) repository.findAll();
			
		for (String id : values) {
			if(students.contains(repository.findOne(Long.parseLong(id)))){
				Student student = repository.findOne(Long.parseLong(id));
				if(student != null){
					students.remove(student);
					sendMail(student.getEmail(),student.getFirstName(), "Comparecimento do aluno", "Ola, o aluno " + student.getFirstName() 
					+ " compareceu a aula de hoje","Comparecimento do aluno: " + student.getFirstName() + " " + student.getLastName(),model);				
				}
				
			}
		}
		for (Student student : students) {
			sendMail(student.getEmail(),student.getFirstName(), "Falta do aluno", "Ola, o aluno " + student.getFirstName() 
			+ " falto a aula de hoje","Falta do aluno: " + student.getFirstName() + " " + student.getLastName(),model);
		}
		return "redirect:/students";
	}
	
	@Async
	public void sendMail(String toEmail,String name, String subject, String message,String mensagemTitulo, Model model) {
		Email email = null;
		try {
			email = DefaultEmail.builder().from(new InternetAddress("professor@catolicasc", mensagemTitulo))
					.to(Lists.newArrayList(new InternetAddress(toEmail, name)))
					.subject(subject).body(message).encoding("UTF-8")
					.build();
			emailService.send(email);
			System.out.println("Send Emails successful");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("Erro for Send Emails");
			System.err.println(e);
		}

		
	}
	
	@RequestMapping("/login")
	public String login() {
    	return "login";
    }	
	
	@RequestMapping("/students")
	public String index(Model model) {
		List<Student> students = (List<Student>) repository.findAll();
		model.addAttribute("students", students);
    	return "students";
    }

    @RequestMapping(value = "add")
    public String addStudent(Model model){
    	model.addAttribute("student", new Student());
        return "addStudent";
    }	

    @RequestMapping(value = "/edit/{id}")
    public String editStudent(@PathVariable("id") Long studentId, Model model){
    	model.addAttribute("student", repository.findOne(studentId));
        return "editStudent";
    }	    
    
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public String save(Student student){
        repository.save(student);
    	return "redirect:/students";
    }
    
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteStudent(@PathVariable("id") Long studentId, Model model) {
    	repository.delete(studentId);
        return "redirect:/students";
    }    
    
    @RequestMapping(value = "getstudents", method = RequestMethod.GET)
    public @ResponseBody List<Student> getStudents() {
            return (List<Student>)repository.findAll();
    }      
}
