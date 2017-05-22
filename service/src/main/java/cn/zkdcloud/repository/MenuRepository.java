package cn.zkdcloud.repository;

import cn.zkdcloud.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author zk
 * @Date 2017/5/19.
 * @Email 2117251154@qq.com
 */
public interface MenuRepository extends JpaRepository<Menu,String>{
    void removeByParentId(String parentId);
}
