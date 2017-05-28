package cn.zkdcloud.repository;

import cn.zkdcloud.entity.Function;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @Author zk
 * @Date 2017/5/19.
 * @Email 2117251154@qq.com
 */
public interface FunctionRepository extends JpaRepository<Function,String>{

    List<Function> findByFunctionUrl(String url);

    Page<Function> findByFunctionIdIn(List<String> list, Pageable pageable);

    @Query("select f from Function f where f.functionId in (select rf.functionId from RoleFunction rf where rf.roleId = :roleId)")
    List<Function> findByRoleRoleId(@Param("roleId") String roleId,Sort sort);

    @Query("select f from  Function f where f.functionId in (select rf.functionId from RoleFunction rf " +
            "where rf.roleId = :roleId) and f.display = false")
    List<Function> findUndisplayByRoleId(@Param("roleId")String roleId);
}
