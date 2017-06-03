package cn.zkdcloud.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/** 申请的审批
 * @Author zk
 * @Date 2017/5/28.
 * @Email 2117251154@qq.com
 */
@Entity
public class Flow {

    @Id
    @GeneratedValue(generator="idGenerator")
    @GenericGenerator(name="idGenerator", strategy="uuid")
    private String flowId;

    @Column
    private String uid;

    @Column
    private String processId;

    @Column
    private String flowUrl;

    @Column
    private Date flowDate; //申请审批时间

    @Transient
    private String statusInstruction;

    @ManyToOne
    @JoinColumn(name = "processId",insertable = false,updatable = false)
    private Process process;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "flowId") //流程删除，对应的执行步骤立即删除，此期间申请的，需要重新申请
    @OrderBy("step asc")
    private Set<FlowStep> flowStepSet;

    @ManyToOne
    @JoinColumn(name = "uid",referencedColumnName = "uid",insertable = false,updatable = false)
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFlowUrl() {
        return flowUrl;
    }

    public void setFlowUrl(String flowUrl) {
        this.flowUrl = flowUrl;
    }

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public Set<FlowStep> getFlowStepSet() {
        return flowStepSet;
    }

    public void setFlowStepSet(Set<FlowStep> flowStepSet) {
        this.flowStepSet = flowStepSet;
    }

    public Process getProcess() {
        return process;
    }

    public void setProcess(Process process) {
        this.process = process;
    }

    public Date getFlowDate() {
        return flowDate;
    }

    public void setFlowDate(Date flowDate) {
        this.flowDate = flowDate;
    }

    public String getStatusInstruction() {
        return statusInstruction;
    }

    public void setStatusInstruction(String statusInstruction) {
        this.statusInstruction = statusInstruction;
    }
}
