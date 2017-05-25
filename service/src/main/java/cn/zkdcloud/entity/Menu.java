package cn.zkdcloud.entity;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author zk
 * @Date 2017/5/19.
 * @Email 2117251154@qq.com
 */
@Entity
public class Menu {

    @Id
    @GeneratedValue(generator="idGenerator")
    @GenericGenerator(name="idGenerator", strategy="uuid")
    private String menuId;

    @Column
    private String menuName;

    @Column
    private String menuDescribe;

    @Column
    private Integer menuSort; //排序顺序，值越小优先级越高

    @Column
    private String parentId;

    @ManyToMany
    @JoinTable(name = "MenuFunction",joinColumns = @JoinColumn(name = "menuId"), //该目录下有什么功能 级联目录
    inverseJoinColumns = @JoinColumn(name = "functionId"))
    @OrderBy("functionSort asc")
    private Set<Function> functionSet;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "menuId") //级联目录角色中间表
    private Set<MenuRole> menuRoleSet;

    public Set<MenuRole> getMenuRoleSet() {
        return menuRoleSet;
    }

    public void setMenuRoleSet(Set<MenuRole> menuRoleSet) {
        this.menuRoleSet = menuRoleSet;
    }

    public Set<Function> getFunctionSet() {
        return functionSet;
    }

    public void setFunctionSet(Set<Function> functionSet) {
        this.functionSet = functionSet;
    }

    public String getMenuId() {
        return menuId;
    }

    public Integer getMenuSort() {
        return menuSort;
    }

    public void setMenuSort(Integer menuSort) {
        this.menuSort = menuSort;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuDescribe() {
        return menuDescribe;
    }

    public void setMenuDescribe(String menuDescribe) {
        this.menuDescribe = menuDescribe;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

}
