package com.lrm.web.admin;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import com.lrm.po.Blog;
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

        List<Long> recommendIds = Arrays.asList(1L, 2L, 3L);  // 这里写了的推荐博客id
        List<Blog> recommendBlogs = new ArrayList<>();
        for (Long id : recommendIds) {
            Blog b = blogService.getBlog(id);
            if (b != null) {
                recommendBlogs.add(b);
            }
        }
        model.addAttribute("recommendBlogs", recommendBlogs);

        System.out.println("--index控制器执行了---");
        return "index";
    }



    @GetMapping("/archives")
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
