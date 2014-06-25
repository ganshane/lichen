package creeper.user.internal;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.springframework.data.jpa.domain.Specification;

import creeper.user.dao.RoleDao;
import creeper.user.entities.Role;
import creeper.user.services.RoleService;

public class RoleServiceImpl implements RoleService {
	
	@Inject
	private RoleDao _roleDao;

	@Override
	public void saveOrUpdateRole(Role role) {
		_roleDao.save(role);
	}
	
	@Override
	public List<Role> findAll(final Role role) {
		if(null != role){
			return _roleDao.findAll(new Specification<Role>() {
				@Override
				public Predicate toPredicate(Root<Role> root,
						CriteriaQuery<?> query, CriteriaBuilder cb) {
					List<Predicate> list = new ArrayList<Predicate>();
					if(null != role.getId()){list.add(cb.equal(root.get("id"),role.getId()));}
					if(null != role.getName()){list.add(cb.equal(root.get("name"),role.getName()));}
					Predicate[] p = new Predicate[list.size()];   
					return cb.and(list.toArray(p));
				}
	        });
		}
		return null;
	}
	
}
