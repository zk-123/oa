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
public class MenuFunction {

    @Id
    @GeneratedValue(generator="idGenerator")
    @GenericGenerator(name="idGenerator", strategy="uuid")
    private String menuFunctionId;

    @Column
    private String menuId;

    @Column
    private String functionId;

    public String getMenuFunctionId() {
        return menuFunctionId;
    }

    public void setMenuFunctionId(String menuFunctionId) {
        this.menuFunctionId = menuFunctionId;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getFunctionId() {
        return functionId;
    }

    public void setFunctionId(String functionId) {
        this.functionId = functionId;
    }
}
