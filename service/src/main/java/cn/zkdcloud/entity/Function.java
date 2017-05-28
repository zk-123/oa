package cn.zkdcloud.entity;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OrderBy;
import java.util.Set;

/** 功能项
 * @Author zk
 * @Date 2017/5/19.
 * @Email 2117251154@qq.com
 */
@Entity
public class Function {

    @Id
    @GeneratedValue(generator="idGenerator")
    @GenericGenerator(name="idGenerator", strategy="uuid")
    private String functionId;

    @Column
    private String functionName;
    @Column
    private String functionUrl;

    @Column
    private Integer functionSort;

    @Column
    private String functionDescribe;

    @Column
    private boolean display;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "functionId") //级联
    private  Set<MenuFunction> menuFunctionSet;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "functionId")
    private Set<RoleFunction> roleFunctionSet;


    public boolean isDisplay() {
        return display;
    }

    public void setDisplay(boolean display) {
        this.display = display;
    }

    public Set<MenuFunction> getMenuFunctionSet() {
        return menuFunctionSet;
    }

    public void setMenuFunctionSet(Set<MenuFunction> menuFunctionSet) {
        this.menuFunctionSet = menuFunctionSet;
    }

    public Set<RoleFunction> getRoleFunctionSet() {
        return roleFunctionSet;
    }

    public void setRoleFunctionSet(Set<RoleFunction> roleFunctionSet) {
        this.roleFunctionSet = roleFunctionSet;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getFunctionUrl() {
        return functionUrl;
    }

    public void setFunctionUrl(String functionUrl) {
        this.functionUrl = functionUrl;
    }

    public Integer getFunctionSort() {
        return functionSort;
    }

    public void setFunctionSort(Integer functionSort) {
        this.functionSort = functionSort;
    }


    public String getFunctionDescribe() {
        return functionDescribe;
    }

    public void setFunctionDescribe(String functionDescribe) {
        this.functionDescribe = functionDescribe;
    }

    public String getFunctionId() {
        return functionId;
    }

    public void setFunctionId(String functionId) {
        this.functionId = functionId;
    }

    @Override
    public  String toString(){
        return "functionId:" + functionId + ",functionName :" + functionName + ", functionSort : " +
                functionSort + ",functionDescribe:" + functionDescribe ;
    }
}
