package fi.haagahelia.course.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fi.haagahelia.course.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	
    User findByUsername(String username);

}
