package com.lrm.service;

import com.lrm.Dao.GlossaryRepository;
import com.lrm.NotFoundException;
import com.lrm.po.Glossary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service
public class GlossaryServiceImpl implements GlossaryService {

    @Autowired
    private GlossaryRepository glossaryRepository;

    @Override
    public List<Glossary> listAllGlossaries() {
        return glossaryRepository.findAllByOrderByTermAsc();
    }

    @Override
    public Glossary saveGlossary(Glossary glossary) {
        glossary.setCreateTime(new Date());
        glossary.setUpdateTime(new Date());
        return glossaryRepository.save(glossary);
    }

    @Override
    public Glossary getGlossary(Long id) {
        return glossaryRepository.findById(id).orElseThrow(() -> new NotFoundException("术语不存在，id=" + id));
    }

    @Override
    public Glossary updateGlossary(Long id, Glossary glossary) {
        Glossary existing = getGlossary(id);
        existing.setTerm(glossary.getTerm());
        existing.setDefinition(glossary.getDefinition());
        existing.setUpdateTime(new Date());
        return glossaryRepository.save(existing);
    }

    @Override
    public void deleteGlossary(Long id) {
        glossaryRepository.deleteById(id);
    }

    @Override
    public Page<Glossary> listGlossaries(Pageable pageable) {
        return glossaryRepository.findAll(pageable);
    }
}