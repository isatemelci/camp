package tr.org.lkd.lyk2015.camp.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class Admin extends AbstractUser {

	private static final long serialVersionUID = 132137112958728090L;
	
	@NotEmpty
	@Column(unique = true)
	private String lkdNo;

	public String getLkdNo() {
		return lkdNo;
	}

	public void setLkdNo(String lkdNo) {
		this.lkdNo = lkdNo;
	}
}
