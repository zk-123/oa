package cn.zkdcloud.entity;


import java.util.List;

/**
 * @Author zk
 * @Date 2017/5/19.
 * @Email 2117251154@qq.com
 */
public class MenuTree {

    private Menu menu;
    private List<MenuTree> menuTreeList;
    boolean spread; //是否展开

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public List<MenuTree> getMenuTreeList() {
        return menuTreeList;
    }

    public void setMenuTreeList(List<MenuTree> menuTreeList) {
        this.menuTreeList = menuTreeList;
    }

    public boolean isSpread() {
        return spread;
    }

    public void setSpread(boolean spread) {
        this.spread = spread;
    }

    @Override
    public String toString(){
        String str ="";
        if(menu != null)
            str += "菜单：" + menu.getMenuName();

        if(!menuTreeList.isEmpty()){
            for(MenuTree menuTree : menuTreeList){
                str += menuTree;
            }
        }
        return str;
    }
}
