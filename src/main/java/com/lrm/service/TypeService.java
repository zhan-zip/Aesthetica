package com.lrm.service;

import com.lrm.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface TypeService {
    Type saveType(Type type);
    Type getType(Long id);
    Page<Type> listType(Pageable pageable);
    Type updateType(Long id, Type type);
    void deleteType(Long id);
    Type getTypeByName(String name);
    List<Type> listAllTypes();
    List<Type> listTypeTop(Integer size);
    List<Map<String, Object>> getCategoryViewCounts();
}