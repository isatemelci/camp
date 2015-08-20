package tr.org.lkd.lyk2015.camp.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import tr.org.lkd.lyk2015.camp.model.Admin;

@Repository
public class AdminDao extends GenericDao<Admin> {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Long create(Admin admin) {

		admin.setPassword(passwordEncoder.encode(admin.getPassword()));

		return super.create(admin);
	}

}
