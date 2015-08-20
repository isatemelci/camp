package tr.org.lkd.lyk2015.camp.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tr.org.lkd.lyk2015.camp.model.Admin;
import tr.org.lkd.lyk2015.camp.repository.AdminDao;

@Service
@Transactional
public class AdminService extends GenericService<Admin> {

	private static final long serialVersionUID = 9010591266519402847L;

	@Autowired
	private AdminDao adminDao;

}
