package cn.zkdcloud.repository;

import cn.zkdcloud.entity.Function;
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

    List<Function> findByMenuId(String menuId);
}
