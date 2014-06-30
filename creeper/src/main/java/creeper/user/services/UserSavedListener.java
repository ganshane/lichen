package creeper.user.services;

import creeper.user.entities.User;

/**
 * 当用户发生变化的时候监听器
 * @author jcai
 */
public interface UserSavedListener {
    /**
     * 保存用户之后的操作
     * @param user 用户对象
     */
    public void afterSaved(User user);
}
