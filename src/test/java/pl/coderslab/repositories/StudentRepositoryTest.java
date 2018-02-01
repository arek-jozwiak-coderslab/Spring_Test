package pl.coderslab.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import pl.coderslab.model.Student;
import pl.coderslab.repository.StudentRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class StudentRepositoryTest {

    private static final Logger log = LoggerFactory.getLogger(StudentRepositoryTest.class);

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void given_find_by_first_name_then_return_student() {
        // given
        Student john = new Student();
        john.setFirstName("John");
        entityManager.persistAndFlush(john);
        log.info(john.getId().toString());
        // when
        Student result = studentRepository.findOneByFirstName("John");
        // then
        assertEquals(result.getFirstName(), john.getFirstName());
    }

    @Test
    public void given_mark_then_find_john_should_not_be_null() {
        // given
        Student mark = new Student();
        mark.setFirstName("Mark");
        entityManager.persist(mark);
        log.warn(mark.getId().toString());
        // when
        Student result = studentRepository.findOneByFirstName("John");
        // then
        assertNull(result);
    }
    
    @Test
    public void given_jo_and_john_then_find_jo_should_return_two_elements() {
        // given
        Student jo = entityManager.persistAndFlush(new Student("jo"));
        Student john = entityManager.persistAndFlush(new Student("john"));
        log.warn(jo.getId().toString());
        log.warn(john.getId().toString());
        // when
        List<Student> result = studentRepository.findBySome("jo");
        // then
        assertThat(result).containsExactly(jo, john);
    }
}
