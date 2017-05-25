package cn.zkdcloud.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * @Author zk
 * @Date 2017/5/22.
 * @Email 2117251154@qq.com
 */
@Entity
public class Role {

    @Id
    @GeneratedValue(generator="idGenerator")
    @GenericGenerator(name="idGenerator", strategy="uuid")
    private String roleId;

    @Column
    private String roleName;

    @Column
    private String roleDescribe;

    @Column
    private Integer rolePowerSize;

    @Column
    private Date roleDate;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "RoleFunction",joinColumns = @JoinColumn(name = "roleId"), //该角色有哪些权限
            inverseJoinColumns = @JoinColumn(name = "functionId"))
    @OrderBy("functionSort asc ")
    private Set<Function> functionSet;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "MenuRole",joinColumns = @JoinColumn(name = "roleId"),
    inverseJoinColumns = @JoinColumn(name = "menuId"))
    @OrderBy("menuSort asc")
    private Set<Menu> menuSet;

    public Integer getRolePowerSize() {
        return rolePowerSize;
    }

    public void setRolePowerSize(Integer rolePowerSize) {
        this.rolePowerSize = rolePowerSize;
    }

    public String getRoleId() {
        return roleId;
    }

    public Date getRoleDate() {
        return roleDate;
    }

    public void setRoleDate(Date roleDate) {
        this.roleDate = roleDate;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDescribe() {
        return roleDescribe;
    }

    public void setRoleDescribe(String roleDescribe) {
        this.roleDescribe = roleDescribe;
    }

    public Set<Function> getFunctionSet() {
        return functionSet;
    }

    public void setFunctionSet(Set<Function> functionSet) {
        this.functionSet = functionSet;
    }

    public Set<Menu> getMenuSet() {
        return menuSet;
    }

    public void setMenuSet(Set<Menu> menuSet) {
        this.menuSet = menuSet;
    }

    @Override
    public String toString(){
        return "roleName : " + roleName + ", roleDescribe" + roleDescribe + "创建时间：" + roleDate;
    }
}
