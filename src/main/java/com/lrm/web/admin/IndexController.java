package com.lrm.web.admin;
import java.util.*;
import java.util.stream.Collectors;

import com.lrm.po.Blog;
import com.lrm.po.Type;
import com.lrm.service.BlogService;
import com.lrm.service.TypeService;
import com.lrm.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller("com.lrm")
public class IndexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @GetMapping("/")            //以下部分代码为AI辅助生成：DeepSeek, 2026-3
    public String index(@PageableDefault(size = 4,sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model) {
        model.addAttribute("page",blogService.listBlog(pageable));
        model.addAttribute("types",typeService.listTypeTop(6));

        List<Long> recommendIds = Arrays.asList(1L, 2L, 3L,252L);  // 这里写了的推荐博客id
        List<Blog> recommendBlogs = new ArrayList<>();
        for (Long id : recommendIds) {
            Blog b = blogService.getBlog(id);
            if (b != null) {
                recommendBlogs.add(b);
            }
        }
        model.addAttribute("recommendBlogs", recommendBlogs);
        model.addAttribute("latestBlogs", blogService.getLatestBlogs(10));      //ai辅助4-16

        System.out.println("--index控制器执行了---");
        return "index";
    }



    @GetMapping("/archives")        //归档页，去掉了
    public String archives() {
        return "archives";
    }

    @GetMapping("/blog/{id}")
    public String blogDetail(@PathVariable Long id, Model model) {
        Blog blog = blogService.getBlog(id);
        if (blog == null) {
            System.out.println("博客不存在，id=" + id);
            return "error/404";
        }
        model.addAttribute("blog", blog);
        return "blog";
    }

    @GetMapping("/bobbin")      //ds4-17
    public String bobbin(Model model) {
        List<Type> types = typeService.listTypeTop(10);

        // 构建数据：分类名称 -> {count, articles}
        Map<String, Map<String, Object>> categoryData = new LinkedHashMap<>();
        for (Type type : types) {
            Map<String, Object> data = new HashMap<>();
            data.put("count", type.getBlogs().size());

            // 获取该分类下的所有文章（包含 id 和 title）
            List<Map<String, Object>> articles = type.getBlogs().stream()
                    .map(blog -> {
                        Map<String, Object> article = new HashMap<>();
                        article.put("id", blog.getId());
                        article.put("title", blog.getTitle());
                        return article;
                    })
                    .collect(Collectors.toList());
            data.put("articles", articles);

            categoryData.put(type.getName(), data);
        }

        model.addAttribute("categoryData", categoryData);
        return "bobbin";
    }

    @GetMapping("/tag")
    public String tag() {
        return "tag";
    }

    @GetMapping("/types")
    public String types() {
        return "types";
    }

    @GetMapping("/me")
    public String me() {
        return "me";
    }

    @GetMapping("/adminHome")
    public String adminHome() {
        return "admin/index";
    }

    @GetMapping("/blogsInput")
    public String blogsInput() {
        return "admin/blogs-input";
    }

    @GetMapping("/login")
    public String login() {
        return "admin/login";
    }

    @GetMapping("/typesInput")
    public String typesInput() {
        return "admin/types-input";
    }


}
