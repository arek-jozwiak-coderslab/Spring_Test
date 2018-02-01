package pl.coderslab.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.coderslab.model.Student;
import pl.coderslab.repository.StudentRepository;

public class StudentServiceTest {

    private static final Logger log = LoggerFactory.getLogger(StudentServiceTest.class);

    private EmailService emailService;
    private StudentService service;
    private StudentRepository repository;

    @Before
    public void setUp() {
        repository = mock(StudentRepository.class);
        emailService = mock(EmailService.class);
        service = new StudentServiceImpl(repository, emailService);
    }

    @Test
    public void when_searching_john_then_return_object() {
        // given
        Student john = new Student("John");
        when(repository.findOneByFirstName("John")).thenReturn(john);

        // when
        Student student = service.findByFirstName("John");
        // then
        verify(repository, times(1)).findOneByFirstName(anyString());
        assertEquals(student.getFirstName(), "John");
    }

    @Test
    public void when_save_student_then_it_is_returned_correctly() {
        // given
        Student student = new Student("John");
        when(repository.save(student)).thenReturn(student);
        // when
        Student result = service.addStudent(student);
        // then
        assertNotNull(result);
        assertEquals(student.getFirstName(), result.getFirstName());
    }
 
  @Test
    public void when_save_student_then_email_is_send() {
        // given
        Student student = new Student("John");
    
        // when
         service.addStudent(student);
        // then
         verify(emailService, times(1)).sendEmail(anyString(), anyString());
    }


}
