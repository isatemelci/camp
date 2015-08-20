package tr.org.lkd.lyk2015.camp.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

@Entity
public class Instructor extends AbstractUser{

	private static final long serialVersionUID = 6715705207985213370L;
	
	@ManyToMany
	private Set<Course> courses = new HashSet<>();

	public Set<Course> getCourses() {
		return courses;
	}

	public void setCourses(Set<Course> courses) {
		this.courses = courses;
	}
}
