package cn.zkdcloud.service;

import cn.zkdcloud.entity.Function;
import cn.zkdcloud.entity.Menu;
import cn.zkdcloud.entity.MenuTree;
import cn.zkdcloud.entity.OperatorLog;
import cn.zkdcloud.exception.TipException;
import cn.zkdcloud.repository.FunctionRepository;
import cn.zkdcloud.repository.MenuRepository;
import cn.zkdcloud.repository.OperatorLogRepository;
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

    /** 添加根目录
     *
     * @param menu
     */
    public void addRootMenu(Menu menu){
        menu.setParentId(Const.MENU_ROOT);
        if(menuRepository.save(menu) == null)
            throw new TipException("创建一级目录失败");

    }

    /** 添加目录
     *
     * @param menu_name
     * @param menu_describe
     * @param parent_id
     * @param sort
     */
    public void addMenu(String menu_name,String menu_describe,String parent_id,Integer sort,String username){
        Menu menu = new Menu();
        menu.setMenuName(menu_name);
        menu.setMenuDescribe(menu_describe);
        menu.setParentId(parent_id);
        menu.setMenuSort(sort);

        if(parent_id == null)
            addRootMenu(menu);
        else if(menuRepository.save(menu) == null)
            throw new TipException("创建目录失败");

        recordLog(username+"创建目录"+menu_name);
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
        Menu oldMenu = menuRepository.findOne(menuId);

        Menu menu = new Menu();
        menu.setMenuId(menuId);
        menu.setMenuName(menuName);
        menu.setMenuDescribe(menuDescribe);
        menu.setParentId(parentId);
        menu.setMenuSort(menuSort);

        if(menuRepository.save(menu) == null)
            throw new TipException("修改失败");

        StringBuffer modifyLog = new StringBuffer("");//更新日志记录操作
        if(!oldMenu.getMenuName().equals(menuName))
            modifyLog.append(" 更新目录名称："+oldMenu.getMenuName() + "->" + menuName);
        if(!oldMenu.getMenuDescribe().equals(menuDescribe))
            modifyLog.append(" 更新描述："+oldMenu.getMenuDescribe() + "->" + menuDescribe);
        if(!oldMenu.getParentId().equals(parentId))
            modifyLog.append(" 更新parentId："+oldMenu.getParentId() + "->" + parentId);
        if(oldMenu.getMenuSort() != menuSort)
            modifyLog.append(" 更新目录顺序："+oldMenu.getMenuSort() + "->" + menuSort);

        recordLog(username+"更新目录"+oldMenu.getMenuName()+",记录为："+modifyLog);

    }
    /** 删除目录
     *
     * @param menu_id
     */
    public void removeMenu(String menu_id,String username){
        Menu menu= menuRepository.getOne(menu_id);
        if(menu == null)
            throw new TipException("输入有误");
        menuRepository.delete(menu_id);
        menuRepository.removeByParentId(menu_id); //删除其子目录

        recordLog(username+"删除目录"+ menu.getMenuName());
    }

    /** 全部菜单列表
     *
     * @return
     */
    public MenuTree menuTreeAll(){
        MenuTree rootMenuTree = new MenuTree();
        List<Menu> sumMenuList= menuRepository.findAll(new Sort(Sort.Direction.ASC,"menuSort"));
        buildMenuTree(sumMenuList,rootMenuTree,true);
        return rootMenuTree;
    }

    /** 目录列表
     *
     * @return
     */
    public Page<Menu> getMenuList(Integer page, Integer pageSize){
        Sort sort = new Sort(Sort.Direction.ASC,"menuSort");
        Pageable pageable = new PageRequest(page-1,pageSize,sort);
        return menuRepository.findAll(pageable);
    }

    public List<Menu> getMenuList(){
        Sort sort = new Sort(Sort.Direction.ASC,"menuSort");
        return menuRepository.findAll(sort);
    }

    /** menuTree结构 第一个menu是空也为#ROOT 遍历从它的子list开始遍历
     *
     * @param sumMenuList
     * @param menuTree
     * @param first
     */
    public void buildMenuTree(List<Menu> sumMenuList, MenuTree menuTree,boolean first){
        List<MenuTree> menuTreeList ;
        if(first)
            menuTreeList = getMenuListByParent(sumMenuList,Const.MENU_ROOT);
        else
            menuTreeList = getMenuListByParent(sumMenuList,menuTree.getMenu().getMenuId());

        if(!menuTreeList.isEmpty()){ //从第一个子列表递归实现
            for(MenuTree mt : menuTreeList){ //构建其子目录
                buildMenuTree(sumMenuList,mt,false);
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
    public List<MenuTree> getMenuListByParent(List<Menu> sumMenuList,String parent){
        List<MenuTree> resultList = new ArrayList<MenuTree>();
        for(Menu menu : sumMenuList){
            if(menu.getParentId().equals(parent)){
                MenuTree menuTree = new MenuTree();
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
        return functionRepository.findByMenuId(menu_id);
    }
}
