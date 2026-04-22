package com.lrm.web.admin;

import com.lrm.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class StatisticsController {

    @Autowired
    private TypeService typeService;

    @GetMapping("/statistics")
    public String statistics(Model model) {
        List<Map<String, Object>> categoryData = typeService.getCategoryViewCounts();
        model.addAttribute("categoryData", categoryData);
        return "admin/statistics";
    }
}