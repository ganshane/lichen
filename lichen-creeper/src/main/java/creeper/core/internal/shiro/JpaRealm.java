package creeper.core.internal.shiro;

import creeper.user.dao.UserDao;
import creeper.user.entities.Permission;
import creeper.user.entities.Role;
import creeper.user.entities.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.tapestry5.func.F;
import org.apache.tapestry5.func.Flow;
import org.apache.tapestry5.func.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 通过JPA实现的Realm
 * @author jcai
 */
public class JpaRealm extends AuthorizingRealm {

    private static final Logger log = LoggerFactory.getLogger(JpaRealm.class);
    @Inject
    private UserDao _userDao;

    /**

    /*--------------------------------------------
    |               M E T H O D S               |
    ============================================*/

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();

        // Null username is invalid
        if (username == null) {
            throw new AccountException("Null usernames are not allowed by this realm.");
        }
        User user = _userDao.findByName(username);
        if (user == null) {
            throw new UnknownAccountException("No account found for user [" + username + "]");
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username, user.getPass().toCharArray(), getName());

        return info;
    }


    /**
     * This implementation of the interface expects the principals collection to return a String username keyed off of
     * this realm's {@link #getName() name}
     *
     * @see #getAuthorizationInfo(org.apache.shiro.subject.PrincipalCollection)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        //null usernames are invalid
        if (principals == null) {
            throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
        }

        String username = (String) getAvailablePrincipal(principals);
        User user = _userDao.findByName(username);
        Set<String> roleNames = new LinkedHashSet<String>();
        Flow<Permission> f = F.<Permission>flow();
        Set<Role> roles = user.getRoles();
        for(Role role:roles){
            roleNames.add(role.getName());
            f.append(role.getPermissions().toArray(new Permission[role.getPermissions().size()]));
        }

        Set<String> permissions = f.map(new Mapper<Permission, String>() {
            @Override
            public String map(Permission element) {
                return element.getPermission();
            }
        }).toSet();


        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roleNames);
        info.setStringPermissions(permissions);
        return info;
    }
}
