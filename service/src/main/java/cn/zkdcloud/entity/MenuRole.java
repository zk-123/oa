package cn.zkdcloud.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @Author zk
 * @Date 2017/5/24.
 * @Email 2117251154@qq.com
 */
@Entity
public class MenuRole {

    @Id
    @GeneratedValue(generator="idGenerator")
    @GenericGenerator(name="idGenerator", strategy="uuid")
    private String menuRoleId;

    @Column
    private String menuId;

    @Column
    private String roleId;

    public String getMenuRoleId() {
        return menuRoleId;
    }

    public void setMenuRoleId(String menuRoleId) {
        this.menuRoleId = menuRoleId;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
