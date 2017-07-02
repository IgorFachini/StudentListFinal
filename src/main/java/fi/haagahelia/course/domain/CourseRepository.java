package fi.haagahelia.course.domain;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import fi.haagahelia.course.entity.Course;

@Transactional
public interface CourseRepository extends CrudRepository<Course, Long>  {
    
	List<Course> findByName(String name);
}

