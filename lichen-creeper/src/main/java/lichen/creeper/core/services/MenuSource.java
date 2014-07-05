package lichen.creeper.core.services;

import lichen.creeper.core.models.CreeperMenu;

/**
 * 所有菜单的集合接口
 * @author jcai
 */
public interface MenuSource {
    /**
     * 创建基于根目录的菜单
     * @return 根节点菜单
     */
    public CreeperMenu buildCreeperMenu();
}
