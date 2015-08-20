package tr.org.lkd.lyk2015.camp.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@Entity
public class Student extends AbstractUser {

	private static final long serialVersionUID = 874952452972512067L;

	public enum Sex {
		MALE, FEMALE
	}

	@NotNull
	@Enumerated(EnumType.STRING)
	private Sex sex;

	@OneToMany(mappedBy = "owner")
	private Set<Application> applicationForms;

	public Sex getSex() {
		return sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

}
