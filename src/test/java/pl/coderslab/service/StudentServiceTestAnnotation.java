package pl.coderslab.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnit44Runner;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.junit4.SpringRunner;

import pl.coderslab.model.Student;
import pl.coderslab.repository.StudentRepository;

//@RunWith(MockitoJUnitRunner.class)
@RunWith(SpringRunner.class)
public class StudentServiceTestAnnotation {

    private StudentService service;
    
    @Mock
    private StudentRepository repository;

    @Mock
    private EmailService emailService;

    @Before
    public void setUp() {
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

}
