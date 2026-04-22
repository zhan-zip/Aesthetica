package com.lrm.service;

import com.lrm.po.Blog;
import com.lrm.po.Type;
import com.lrm.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BlogService {

    Blog getBlog(Long id);
    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);
    Blog saveBlog(Blog blog);
    Blog updateBlog(Long id,Blog blog);
    void deleteBlog(Long id);
    Page<Blog> listBlog(Pageable pageable);

    List<Blog> getLatestBlogs(int limit);   //ai辅助4-16
    long countByPublished(boolean published);
    List<Blog> findByPublished(boolean published, Pageable pageable);   //ai4-18
    long count();
    void updateViewCount(Long id);
}
