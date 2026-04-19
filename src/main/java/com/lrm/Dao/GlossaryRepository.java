package com.lrm.Dao;

import com.lrm.po.Glossary;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GlossaryRepository extends JpaRepository<Glossary, Long> {
    List<Glossary> findAllByOrderByTermAsc();
}