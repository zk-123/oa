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

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "menuId")
    private Set<Function> functionSet = new HashSet<Function>();

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

    public Set<Function> getFunctionSet() {
        return functionSet;
    }

    public void setFunctionSet(Set<Function> functionSet) {
        this.functionSet = functionSet;
    }
}
