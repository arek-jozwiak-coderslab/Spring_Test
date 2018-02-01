package pl.coderslab.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import pl.coderslab.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
    
    Student findOneByFirstName(String firstName);
    
    @Query("select s from Student s where s.firstName like ?1%")
    List<Student> findBySome(String some);
    
    Optional<Student> findOneByLastName(String lastName);
}