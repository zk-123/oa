package cn.zkdcloud.repository;

import cn.zkdcloud.entity.FlowStep;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * @Author zk
 * @Date 2017/5/31.
 * @Email 2117251154@qq.com
 */
public interface FlowStepRepository extends JpaRepository<FlowStep,String>{

    /** 获取由我审批的分页列表
     *
     * @param roleId
     * @param pageable
     * @return
     */
    Page<FlowStep> findByStatusFalseAndAcceptTrueAndRoleId(String roleId, Pageable pageable);

    /** 审批指定的申请(修改字段，状态status， 是否接受 accept, 备注 remarks, 处理人 dealUsername, 处理日期 flowStepDate )
     *
     * @param flowStepId
     * @param accept
     * @param remarks
     */
    @Modifying
    @Query("update FlowStep fs set fs.accept = :accept, fs.remarks = :remarks, fs.status = true ," +
            " fs.dealUsername = :dealUsername , fs.flowStepDate = :flowStepDate  where fs.flowStepId = :flowStepId")
    void modifyApproveByFlowStepId(@Param("flowStepId")String flowStepId,@Param("accept")boolean accept,@Param("remarks")String remarks,
                                   @Param("dealUsername")String dealUsername,@Param("flowStepDate")Date flowStepDate);

    @Modifying
    @Query("update FlowStep fs set fs.accept = true where fs.flowStepId = :flowStepId")
    void modifyAcceptById(@Param("flowStepId")String flowStepId);
}
