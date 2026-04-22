package com.lrm.Dao;

import com.lrm.po.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TypeRepository extends JpaRepository<Type, Long> {

    Type findByName(String name);

    @Query(value = "SELECT t.* FROM t_type t ORDER BY (SELECT COUNT(*) FROM t_blog b WHERE b.type_id = t.id) DESC", nativeQuery = true)
    List<Type> findTop(Pageable pageable);

    @Query("SELECT t.name, COALESCE(SUM(b.view), 0) FROM Type t LEFT JOIN t.blogs b GROUP BY t.id")
    List<Object[]> findCategoryViewSum();
}
