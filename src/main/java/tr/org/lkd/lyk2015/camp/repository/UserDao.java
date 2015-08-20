package tr.org.lkd.lyk2015.camp.repository;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import tr.org.lkd.lyk2015.camp.model.AbstractUser;

@Repository
public class UserDao extends GenericDao<AbstractUser> {

	public AbstractUser findByEmail(String username) {
		
		Criteria criteria = this.createCriteria();

		criteria.add(Restrictions.eq("email", username));

		return (AbstractUser) criteria.uniqueResult();
	}

}
