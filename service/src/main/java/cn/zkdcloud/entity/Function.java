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
    private String menuId;

    @Column
    private String functionDescribe;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "functionId")
    private Set<RolePower> rolePowerSet;

    public Set<RolePower> getRolePowerSet() {
        return rolePowerSet;
    }

    public void setRolePowerSet(Set<RolePower> rolePowerSet) {
        this.rolePowerSet = rolePowerSet;
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

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
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
                functionSort + ",functionDescribe:" + functionDescribe + ", menuId : " + menuId;
    }
}
