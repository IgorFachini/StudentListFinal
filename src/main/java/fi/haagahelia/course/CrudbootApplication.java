package fi.haagahelia.course;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import fi.haagahelia.course.domain.Student;
import fi.haagahelia.course.domain.StudentRepository;
import fi.haagahelia.course.domain.User;
import fi.haagahelia.course.domain.UserRepository;
import it.ozimov.springboot.mail.configuration.EnableEmailTools;

@EnableEmailTools
@SpringBootApplication
public class CrudbootApplication {
	
	private static final Logger log = LoggerFactory.getLogger(CrudbootApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(CrudbootApplication.class, args);
	}
	
	/**
	 * Save students and courses to H2 DB for testing
	 * @param repository
	 * @return
	 */
	@Bean
	public CommandLineRunner demo(StudentRepository repository, UserRepository urepository) {
		return (args) -> {
			// save students
			Student student1 = new Student("John", "Johnson", "john@john.com"); 
			repository.save(new Student("Steve", "Stevens", "steve.stevent@st.com"));
			repository.save(new Student("Mary", "Robinson", "mary@robinson.com"));
			repository.save(new Student("Kate", "Keystone", "kate@kate.com"));
			repository.save(new Student("Diana", "Doll", "diana@doll.com"));
				
			repository.save(student1);

			// Create users with BCrypt encoded password (user/user, admin/admin)
			User user1 = new User("user", "$2a$06$3jYRJrg0ghaaypjZ/.g4SethoeA51ph3UD4kZi9oPkeMTpjKU5uo6", "USER");
			User user2 = new User("admin", "$2a$08$bCCcGjB03eulCWt3CY0AZew2rVzXFyouUolL5dkL/pBgFkUH9O4J2", "ADMIN");
			urepository.save(user1);
			urepository.save(user2); 
		};
	}
}
