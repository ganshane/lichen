package creeper.core.services;

import creeper.core.models.CreeperMenu;

/**
 * 所有菜单的集合接口
 * @author jcai
 */
public interface MenuSource {
    public CreeperMenu buildCreeperMenu();
}
