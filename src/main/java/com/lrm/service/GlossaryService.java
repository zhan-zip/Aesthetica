package com.lrm.service;

import com.lrm.po.Glossary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GlossaryService {
    List<Glossary> listAllGlossaries();
    Glossary saveGlossary(Glossary glossary);
    Glossary getGlossary(Long id);
    Glossary updateGlossary(Long id, Glossary glossary);
    void deleteGlossary(Long id);
    Page<Glossary> listGlossaries(Pageable pageable);
}