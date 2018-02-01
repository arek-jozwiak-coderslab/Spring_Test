package pl.coderslab.service;

import java.util.List;

import pl.coderslab.model.Student;

public interface StudentService {
    
    List<Student> listAllStudents();
    
    Student findByFirstName(String firstName); 
    Student addStudent(Student student);
    Student getStudentById(Long id);
    Student getStudentByLastName(String lastName);
    

}
