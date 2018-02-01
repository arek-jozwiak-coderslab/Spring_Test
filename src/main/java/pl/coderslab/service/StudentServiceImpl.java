package pl.coderslab.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import pl.coderslab.exception.BaseEntityNotFoundException;
import pl.coderslab.model.Student;
import pl.coderslab.repository.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService {
    
    private static final Logger log = LoggerFactory.getLogger(StudentServiceImpl.class);


    private StudentRepository repository;
    private EmailService emailService;

    public StudentServiceImpl(StudentRepository repository, EmailService emailService) {
        this.repository = repository;
        this.emailService = emailService;
    }

    @Override
    public List<Student> listAllStudents() {
        return repository.findAll();
    }

    @Override
    public Student findByFirstName(String firstName) {
        return repository.findOneByFirstName(firstName);
    }

    @Override
    public Student addStudent(Student student) {
        emailService.sendEmail(student.getEmail(), "sampleMessage");
        return repository.save(student);
    }
    
    @Override
    public Student getStudentById(Long id) {
        log.debug("getStudentById called");
        return repository.findOne(id);
    }

    @Override
    public Student getStudentByLastName(String lastName) {
      return repository.findOneByLastName(lastName)
                .orElseThrow(() -> new BaseEntityNotFoundException("Appointment with given token not found."));
    }
}
