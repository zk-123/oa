package cn.zkdcloud.repository;

import cn.zkdcloud.entity.Process;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @Author zk
 * @Date 2017/5/28.
 * @Email 2117251154@qq.com
 */
public interface ProcessRepository extends JpaRepository<Process,String>{

    @Modifying
    @Query("update Process set processName = :processName, processDescribe = :processDescribe, processUrl = :processUrl," +
            "processRoles = :processRoles where processId = :processId")
    void modifyProcess(@Param("processName")String processName,@Param("processDescribe")String processDescribe,
                       @Param("processUrl")String processUrl,@Param("processRoles")String processRoles,@Param("processId")String processId);

}
