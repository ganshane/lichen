package lichen.creeper.user.internal;

import lichen.creeper.user.entities.Role;
import lichen.creeper.user.entities.User;
import lichen.creeper.user.services.UserSavedListener;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;

/**
 * 当保存用户的时候，同步用户到activiti中，便于工作流的运行
 * @author jcai
 */
public class SyncUserToActivitiListener implements UserSavedListener{
    @Inject
    private IdentityService identityService;

    @Override
    public void afterSaved(User user) {
        String userId = user.getId().toString();
        org.activiti.engine.identity.User activitiUser;

        List<org.activiti.engine.identity.User> activitiUsers = identityService.createUserQuery().userId(userId).list();
        if (activitiUsers.size() == 1) {
            //更新信息
            activitiUser = activitiUsers.get(0);

            // 删除用户的membership
            List<Group> activitiGroups = identityService.createGroupQuery().groupMember(userId).list();
            for (Group group : activitiGroups) {
                identityService.deleteMembership(user.getId().toString(), group.getId().toString());
            }
        } else {
            activitiUser = identityService.newUser(user.getId().toString());
        }
        activitiUser.setFirstName(user.getName());
        activitiUser.setLastName("");
        activitiUser.setPassword(user.getPass());
        activitiUser.setEmail(user.getMail());
        identityService.saveUser(activitiUser);
        // 添加membership
        Set<Role> roles = user.getRoles();
        if(roles != null)
            for (Role role : roles) {
                identityService.createMembership(user.getId().toString(), role.getName());
            }
    }
}
