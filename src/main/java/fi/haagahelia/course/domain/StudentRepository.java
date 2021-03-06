package fi.haagahelia.course.domain;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import fi.haagahelia.course.entity.Student;

@Transactional
public interface StudentRepository extends CrudRepository<Student, Long> {

    List<Student> findByLastName(String lastname);
    
}
