package cn.zkdcloud.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/** 申请流程分步,因为需要分步，所以新建了个表
 * @Author zk
 * @Date 2017/5/31.
 * @Email 2117251154@qq.com
 */
@Entity
public class FlowStep {
    @Id
    @GeneratedValue(generator="idGenerator")
    @GenericGenerator(name="idGenerator", strategy="uuid")
    private String flowStepId;

    @Column
    private String flowId;

    @Column
    private Integer step;

    @Column
    private String roleId;

    @Column(columnDefinition = "false")
    private boolean status; //审批状态，true 审批完成 ,0 未审批

    @Column(columnDefinition = "false")
    private boolean accept;// 两个含义，1、是否轮到自己审批 true 是， false 否 2、是否同意， true同意，0 驳回
                            // (配合 status完成状态的改变)
    /*
    status |  1   |  0
    accept |      |
    ------------------------
      1    | 批准  | 待我审批
    -----------------------
      0    | 驳回  | 流转中
    */
    @Column
    private String remarks;

    @Column
    private Date FlowStepDate; //审批人审批时间

    @Column
    private String dealUsername;

    @ManyToOne
    @JoinColumn(name = "flowId",insertable = false,updatable = false)
    private Flow flow;

    @ManyToOne
    @JoinColumn(name = "roleId",insertable = false,updatable = false)
    private Role role;

    public String getDealUsername() {
        return dealUsername;
    }

    public void setDealUsername(String dealUsername) {
        this.dealUsername = dealUsername;
    }

    public Flow getFlow() {
        return flow;
    }

    public void setFlow(Flow flow) {
        this.flow = flow;
    }

    public String getFlowStepId() {
        return flowStepId;
    }

    public void setFlowStepId(String flowStepId) {
        this.flowStepId = flowStepId;
    }

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Date getFlowStepDate() {
        return FlowStepDate;
    }

    public void setFlowStepDate(Date flowStepDate) {
        FlowStepDate = flowStepDate;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isAccept() {
        return accept;
    }

    public void setAccept(boolean accept) {
        this.accept = accept;
    }
}
