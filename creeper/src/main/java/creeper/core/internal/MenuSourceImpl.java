package creeper.core.internal;

import creeper.core.models.CreeperMenu;
import creeper.core.services.MenuSource;

import java.util.*;

/**
 * menu source implements
 * @author jcai
 */
public class MenuSourceImpl implements MenuSource {
    private Collection<CreeperMenu> _coll;
    public MenuSourceImpl(Collection<CreeperMenu> coll){
        _coll = Collections.unmodifiableCollection(coll);
    }
    @Override
    public CreeperMenu buildCreeperMenu() {
        //进行排序
        CreeperMenu rootMenu = new CreeperMenu();
        rootMenu.setType(CreeperMenu.MENU_IS_ROOT);
        rootMenu.setUrl("/");
        rootMenu.setName("root");
        rootMenu.setTitle("root");

        CreeperMenuWrapper root = new CreeperMenuWrapper(rootMenu);

        Iterator<CreeperMenu> it = _coll.iterator();
        while(it.hasNext()){
            CreeperMenuWrapper menu = new CreeperMenuWrapper(it.next().copy());
            String url = menu.getUrl();
            String [] parents = findParents(url);
            //构建父类菜单
            CreeperMenuWrapper parentMenu = getOrFillParentMenu(root, parents, 0);
            parentMenu.children.put(url, menu);
        }


        return root.build();
    }
    private CreeperMenuWrapper getOrFillParentMenu(CreeperMenuWrapper menu,String[] parents,int seq){
        if(parents.length == seq)
            return menu;

        CreeperMenuWrapper childMenuWrapper = menu.children.get(parents[seq]);
        if(childMenuWrapper == null){
            CreeperMenu childMenu;
            childMenu = new CreeperMenu();
            childMenu.setUrl(parents[seq]);

            childMenuWrapper = new CreeperMenuWrapper(childMenu);
            menu.children.put(childMenu.getUrl(),childMenuWrapper);
        }
        return  getOrFillParentMenu(childMenuWrapper,parents,seq+1);
    }
    private String[] findParents(String url){
        String tmpUrl = url;
        //去掉头尾的 / 符号
        if(tmpUrl.startsWith("/")){
            tmpUrl = url.substring(1);
        }
        if(tmpUrl.endsWith("/")){
            tmpUrl = tmpUrl.substring(0,tmpUrl.length()-1);
        }
        String [] strings = tmpUrl.split("/");
        String [] parents = new String[strings.length - 1];
        for(int i=0;i<parents.length;i++){
            if(i>0){
                parents[i]=parents[i-1]+"/";
            }
            if(parents[i] == null)
                parents[i]="/";
            parents[i] += strings[i];
        }
        return parents;
    }
    class CreeperMenuWrapper {
        //子菜单
        Map<String,CreeperMenuWrapper> children = new HashMap<String, CreeperMenuWrapper>() ;
        private CreeperMenu _menu;
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        public CreeperMenuWrapper(CreeperMenu menu){
            _menu = menu;
        }
        public CreeperMenu build(){
            Iterator<CreeperMenuWrapper> it = children.values().iterator();
            while(it.hasNext()){
                CreeperMenuWrapper creeperMenuWrapper = it.next();
                CreeperMenu childMenu = creeperMenuWrapper.build();
                childMenu.setParent(_menu);
                _menu.getChildren().add(childMenu);
            }
            Collections.sort(_menu.getChildren());
            return _menu;
        }

        public String getUrl() {
            return _menu.getUrl();
        }
    }
}
