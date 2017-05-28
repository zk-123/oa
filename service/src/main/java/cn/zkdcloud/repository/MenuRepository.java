package cn.zkdcloud.repository;

import cn.zkdcloud.entity.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @Author zk
 * @Date 2017/5/19.
 * @Email 2117251154@qq.com
 */
public interface MenuRepository extends JpaRepository<Menu,String>{

    int deleteByParentId(String parentId);

    Page<Menu> findByMenuIdIn(List<String> list, Pageable pageable);

    List<Menu> findByMenuIdIn(List<String> list, Sort sort);

    @Modifying
    @Query("update Menu m set m.menuName = :menuName , m.menuSort = :menuSort , m.menuDescribe = :menuDescribe , m.parentId = :parentId " +
            "where m.menuId = :menuId")
    void modifyMenu(@Param("menuId")String menuId,@Param("menuName")String menuName,@Param("menuSort")Integer menuSort,
                    @Param("menuDescribe")String menuDescribe,@Param("parentId")String parentId);
}
