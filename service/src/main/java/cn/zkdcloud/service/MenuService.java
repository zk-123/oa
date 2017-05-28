package cn.zkdcloud.service;

import cn.zkdcloud.entity.*;
import cn.zkdcloud.exception.TipException;
import cn.zkdcloud.repository.*;
import cn.zkdcloud.util.Const;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Author zk
 * @Date 2017/5/19.
 * @Email 2117251154@qq.com
 */
@Service
@Transactional
public class MenuService {

    private static Logger logger = Logger.getLogger(MenuService.class);

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private OperatorLogRepository operatorLogRepository;

    @Autowired
    private FunctionRepository functionRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuRoleRepository menuRoleRepository;

    @Autowired
    private RoleFunctionRepository roleFunctionRepository;

    /** 记录操作
     *
     * @param content
     */
    public void recordLog(String content){
        OperatorLog log = new OperatorLog();
        log.setOperatorContent(content);
        log.setOpreratorCreateDate(new Date());
        log.setOperatorType("menu");
        operatorLogRepository.save(log);

        logger.info(content);
    }


    /** 添加目录
     *
     * @param menu_name
     * @param menu_describe
     * @param parent_id
     * @param sort
     */
    public void addMenu(String menu_name,String menu_describe,String parent_id,Integer sort,User user){
        Menu menu = new Menu();
        menu.setMenuName(menu_name);
        menu.setMenuDescribe(menu_describe);
        menu.setParentId(parent_id);
        menu.setMenuSort(sort);

        Menu new_menu = menuRepository.save(menu);
        if( new_menu == null)
            throw new TipException("创建目录失败");

        Role role = user.getRole(); //为权限比自己高或等的人来分配权限
        List<Role> roleList = roleService.listRoleGreaterOrEqual(role.getRolePowerSize());
        for(Role r : roleList){
            MenuRole menuRole = new MenuRole();
            menuRole.setRoleId(r.getRoleId());
            menuRole.setMenuId(new_menu.getMenuId());
            menuRoleRepository.save(menuRole);
        }

        recordLog(user.getUsername()+"创建目录"+menu_name);
    }

    /** 根据Id获取menu
     *
     * @param menuId
     * @return
     */
    public Menu getMenu(String menuId){
        return menuRepository.findOne(menuId);
    }

    /** 更新操作
     *
     * @param menuId
     * @param menuDescribe
     * @param parentId
     * @param menuName
     * @param menuSort
     * @param username
     */
    public void updateMenu(String menuId,String menuDescribe,String parentId,String menuName,
                           Integer menuSort,String username){
        menuRepository.modifyMenu(menuId,menuName,menuSort,menuDescribe,parentId);
        recordLog(username+"更新目录"+menuName);

    }
    /** 删除目录
     *
     * @param menu_id
     */
    public void removeMenu(String menu_id,String username){
        Menu menu= menuRepository.getOne(menu_id);
        if(menu == null)
            throw new TipException("操作有误");
        menuRepository.delete(menu_id);
        menuRepository.deleteByParentId(menu_id); //删除其子目录

        recordLog(username+"删除目录"+ menu.getMenuName());
    }

    /** 目录列表分页
     *
     * @return
     */
    public Page<Menu> getMenuList(Integer page, Integer pageSize,User user){
        Sort sort = new Sort(Sort.Direction.ASC,"menuSort");
        Pageable pageable = new PageRequest(page-1,pageSize,sort);

        List<String> menuIds = new ArrayList<>(); //暂时用这个笨方法
        for(MenuRole menuRole : user.getRole().getMenuRoleSet()){
            menuIds.add(menuRole.getMenuId());
        }

        return menuRepository.findByMenuIdIn(menuIds,pageable);
    }

    /** 目录列表List
     *
     * @param user
     * @return
     */
    public List<Menu> getMenuList(User user){
        List<Menu> menuList = new ArrayList<>();
        for(MenuRole menuRole : user.getRole().getMenuRoleSet())
            menuList.add(menuRepository.getOne(menuRole.getMenuId()));
        Collections.sort(menuList,new MenuComparatorByMenuSort());
        return menuList;
    }

    /** 根据角色筛选目录列表
     *
     * @return
     */
    public MenuTree menuTree(User user){
        MenuTree rootMenuTree = new MenuTree();
        List<Menu> sumMenuList= new ArrayList<>();
        for(MenuRole menuRole : user.getRole().getMenuRoleSet())
            sumMenuList.add(menuRepository.getOne(menuRole.getMenuId()));
        Collections.sort(sumMenuList,new MenuComparatorByMenuSort());

        buildMenuTree(sumMenuList,rootMenuTree,true,user.getRole().getRoleId());
        return rootMenuTree;
    }

    /** menuTree结构 第一个menu是空也为#ROOT 遍历从它的子list开始遍历
     *
     * @param sumMenuList
     * @param menuTree
     * @param first
     */
    public void buildMenuTree(List<Menu> sumMenuList, MenuTree menuTree,boolean first,String roleId){
        List<MenuTree> menuTreeList ;
        if(first)
            menuTreeList = getMenuListByParent(sumMenuList,Const.MENU_ROOT,roleId);
        else
            menuTreeList = getMenuListByParent(sumMenuList,menuTree.getMenu().getMenuId(),roleId);

        if(!menuTreeList.isEmpty()){ //从第一个子列表递归实现
            for(MenuTree mt : menuTreeList){ //构建其子目录
                buildMenuTree(sumMenuList,mt,false,roleId);
            }
        }
        menuTree.setSpread(false);//默认不展开
        menuTree.setMenuTreeList(menuTreeList);
    }

    /** 根据parent获取子menuList
     *
     * @param sumMenuList
     * @param parent
     * @return
     */
    public List<MenuTree> getMenuListByParent(List<Menu> sumMenuList,String parent,String roleId){
        List<MenuTree> resultList = new ArrayList<MenuTree>();
        for(Menu menu : sumMenuList){
            if(menu.getParentId().equals(parent)){
                MenuTree menuTree = new MenuTree();
                for(Function function : menu.getFunctionSet()){
                    if(roleFunctionRepository.findByRoleIdAndFunctionId(roleId,function.getFunctionId())== null)
                        menu.getFunctionSet().remove(function);
                }
                menuTree.setMenu(menu);
                resultList.add(menuTree);
            }
        }
        return resultList;
    }

    /**根据菜单号获取功能列表
     *
     * @param menu_id
     * @return
     */
    public List<Function> getFunctionListByMenuId(String menu_id){
        return null;
    }

    /** 根据排序序号菜单排序
     *
     */
    class MenuComparatorByMenuSort implements Comparator<Menu>{

        @Override
        public int compare(Menu o1, Menu o2) {
            return o1.getMenuSort() > o2.getMenuSort() ? 1 : -1;
        }
    }
}
