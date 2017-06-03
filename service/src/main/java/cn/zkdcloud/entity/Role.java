package cn.zkdcloud.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
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

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "roleId")
    private Set<RoleUser> roleUserSet;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "roleId")
    private Set<RoleFunction>roleFunctionSet;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "roleId")
    private Set<MenuRole> menuRoleSet;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "roleId")
    private Set<FlowStep> flowStepSet;

    public Set<FlowStep> getFlowStepSet() {
        return flowStepSet;
    }

    public void setFlowStepSet(Set<FlowStep> flowStepSet) {
        this.flowStepSet = flowStepSet;
    }

    public Integer getRolePowerSize() {
        return rolePowerSize;
    }

    public Set<RoleUser> getRoleUserSet() {
        return roleUserSet;
    }

    public void setRoleUserSet(Set<RoleUser> roleUserSet) {
        this.roleUserSet = roleUserSet;
    }

    public Set<RoleFunction> getRoleFunctionSet() {
        return roleFunctionSet;
    }

    public void setRoleFunctionSet(Set<RoleFunction> roleFunctionSet) {
        this.roleFunctionSet = roleFunctionSet;
    }

    public Set<MenuRole> getMenuRoleSet() {
        return menuRoleSet;
    }

    public void setMenuRoleSet(Set<MenuRole> menuRoleSet) {
        this.menuRoleSet = menuRoleSet;
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


    @Override
    public String toString(){
        return "roleName : " + roleName + ", roleDescribe" + roleDescribe + "创建时间：" + roleDate;
    }
}
