package fi.haagahelia.course.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
public class Student {
	private long id;	 
	private String firstName;	
	private String lastName;   
    private String email;    
    
	private Set<Course> courses = new HashSet<Course>(0);    
    
    public Student() {
    	super();
    }
    
    public Student(long id) {
		this();
		this.id = id;
	}

	public Student(String firstName, String lastName, String email) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

    @Column(name = "firstname")   	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

    @Column(name = "lastname")	
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

 
    @Column(name = "email")	
    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}	

	@ManyToMany(cascade = CascadeType.MERGE)
	@JoinTable(name = "student_course", joinColumns = { @JoinColumn(name = "id") }, inverseJoinColumns = { @JoinColumn(name = "courseid") })
	public Set<Course> getCourses() {
		return this.courses;
	}

	public void setCourses(Set<Course> courses) {
		this.courses = courses;
	}
	
	public boolean hasCourse(Course course) {
		for (Course studentCourse: getCourses()) {
			if (studentCourse.getCourseid() == course.getCourseid()) {
				return true;
			}
		}
		return false;
	}	
}
