package cn.zkdcloud.entity;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.Transient;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;

/**  审批
 * @Author zk
 * @Date 2017/5/28.
 * @Email 2117251154@qq.com
 */
@Entity
public class Process {

    @Id
    @GeneratedValue(generator="idGenerator")
    @GenericGenerator(name="idGenerator", strategy="uuid")
    private String processId;

    @Column
    private String processName;

    @Column
    private String processDescribe;

    @Column
    private String processUrl;//流程模板路径

    @Column(length = 2250)
    private String processRoles; //该字段是json数组json格式（step,roleId）

    @Transient
    private List<Role> roleList;

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getProcessDescribe() {
        return processDescribe;
    }

    public void setProcessDescribe(String processDescribe) {
        this.processDescribe = processDescribe;
    }

    public String getProcessUrl() {
        return processUrl;
    }

    public void setProcessUrl(String processUrl) {
        this.processUrl = processUrl;
    }

    public String getProcessRoles() {
        return processRoles;
    }

    public void setProcessRoles(String processRoles) {
        this.processRoles = processRoles;
    }
}
