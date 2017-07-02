package fi.haagahelia.course.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import fi.haagahelia.course.entity.Course;

public interface CourseRepository extends CrudRepository<Course, Long>  {
    
	List<Course> findByName(String name);
}

