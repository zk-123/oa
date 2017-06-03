package cn.zkdcloud.repository;

import cn.zkdcloud.entity.FlowStep;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @Author zk
 * @Date 2017/5/31.
 * @Email 2117251154@qq.com
 */
public interface FlowStepRepository extends JpaRepository<FlowStep,String>{
    Page<FlowStep> findByStatusFalseAndAcceptTrueAndRoleId(String roleId, Pageable pageable);
}
