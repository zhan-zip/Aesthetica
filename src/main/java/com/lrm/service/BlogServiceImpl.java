package com.lrm.service;

import com.lrm.Dao.BlogRepository;
import com.lrm.NotFoundException;
import com.lrm.po.Blog;
import com.lrm.po.Type;
import com.lrm.vo.BlogQuery;
import jakarta.persistence.Table;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.lrm.util.MyBeanUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BlogServiceImpl implements BlogService{

    @Autowired
    private BlogRepository blogRepository;

    @Override
    public Blog getBlog(Long id) {
        return blogRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {         //以下部分代码为AI辅助生成：DeepSeek, 2026-3
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public @Nullable Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (blog.getTitle() != null && !"".equals(blog.getTitle())) {
                    predicates.add(cb.like(root.<String>get("title"),"%"+blog.getTitle()+"%"));
                }
                if (blog.getTypeId() != null) {
                    predicates.add(cb.equal(root.<Type>get("type").get("id"),blog.getTypeId()));
                }
                if (blog.isRecommend()) {
                    predicates.add(cb.equal(root.<Boolean>get("recommend"),blog.isRecommend()));
                }
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        },pageable);
    }

    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        if(blog.getId()==null) {
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
        } else {
            blog.setUpdateTime(new Date());
        }

        return blogRepository.save(blog);
    }

    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog b = blogRepository.findById(id).orElse(null);
        if (b == null) {
            throw new NotFoundException("该博客不存在");
        } else {
            blog.setCreateTime(b.getCreateTime());
            blog.setUpdateTime(new Date());
            BeanUtils.copyProperties(blog, b, MyBeanUtils.getNullPropertyNames(blog));
            return blogRepository.save(b);
        }
    }

    @Transactional
    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogRepository.findByPublishedTrue(pageable);
    }

    @Override       //ai辅助4-16
    public List<Blog> getLatestBlogs(int limit) {
        Pageable pageable = PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "updateTime"));
        return blogRepository.findAll(pageable).getContent();
    }

    @Override
    public long countByPublished(boolean published) {
        return blogRepository.countByPublished(published);
    }

    @Override
    public List<Blog> findByPublished(boolean published, Pageable pageable) {
        return blogRepository.findByPublished(published, pageable);
    }
    @Override
    public long count() {
        return blogRepository.count();
    }
}
