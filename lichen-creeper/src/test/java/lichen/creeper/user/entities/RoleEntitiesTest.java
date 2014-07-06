package lichen.creeper.user.entities;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import junit.framework.Assert;

import org.apache.tapestry5.ioc.ServiceBinder;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import lichen.creeper.core.models.CreeperModuleDef;
import lichen.creeper.core.services.jpa.BaseEntityTestCase;
import lichen.creeper.user.dao.RoleDao;

public class RoleEntitiesTest extends BaseEntityTestCase{

	@Override
	protected CreeperModuleDef[] getCreeperModules() {
		return new CreeperModuleDef[]{CreeperModuleDef.create("用户","lichen.creeper.user")};
	}

	@Override
	protected Class<?>[] getIocModules() {
		return new Class<?>[]{RoleTestModule.class};
	}
	
	public static class RoleTestModule{
        public static void bind(ServiceBinder binder){
            binder.bind(RoleTestService.class,RoleTestServiceImpl.class);
        }
    }
    public static interface RoleTestService{
        @Transactional
        public void saveOrUpdateRole(Role role);
    }
    public static class RoleTestServiceImpl implements RoleTestService {
        @Inject
    	private RoleDao _roleDao;
        @Override
        public void saveOrUpdateRole(Role role) {
        	_roleDao.save(role);
        }
    }
    
    @Test
    public void test_save(){
    	Role role = new Role();
		role.setName("管理员");
		
		Permission perm = new Permission();
		perm.setRole(role);
		perm.setPermission("添加");
		
		Permission perm2 = new Permission();
		perm2.setRole(role);
		perm2.setPermission("修改");
		
		Set<Permission> permissions = new HashSet<Permission>();
		permissions.add(perm);
		permissions.add(perm2);
		role.setPermissions(permissions);
		
		RoleTestService service = registry.getService(RoleTestService.class);
		service.saveOrUpdateRole(role);
		System.out.println("=======================");
		
		RoleDao dao = registry.getObject(RoleDao.class,null);
        System.out.println("uuid:"+role.getId());
		role = dao.findOne(role.getId());
		Permission delPerm = null;
		Set<Permission> perms = role.getPermissions();
		for(Permission p : perms){
			if("修改".equals(p.getPermission())){
				delPerm = p;
			}
		}
		System.out.println(delPerm.getPermission());
		perms.remove(delPerm);
		service.saveOrUpdateRole(role);
		perms = role.getPermissions();
		Assert.assertEquals(1, perms.size());
		
		Permission perm3 = new Permission();
		perm3.setRole(role);
		perm3.setPermission("删除");
		perms.add(perm3);
		service.saveOrUpdateRole(role);
    }

	
}
