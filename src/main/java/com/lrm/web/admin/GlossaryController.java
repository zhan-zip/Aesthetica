package com.lrm.web.admin;

import com.lrm.po.Glossary;
import com.lrm.service.GlossaryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class GlossaryController {

    @Autowired
    private GlossaryService glossaryService;


    @GetMapping("/glossaries")
    public String glossaries(@PageableDefault(size = 10, sort = {"term"}, direction = Sort.Direction.ASC) Pageable pageable,
                             Model model) {
        model.addAttribute("page", glossaryService.listGlossaries(pageable));
        return "admin/glossaries";
    }
    @PostMapping("/glossaries")
    public String save(@Valid Glossary glossary, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "admin/glossary-input";
        }
        try {
            glossaryService.saveGlossary(glossary);
            attributes.addFlashAttribute("message", "保存成功");
        } catch (DataIntegrityViolationException e) {
            attributes.addFlashAttribute("message", "保存失败：术语【" + glossary.getTerm() + "】已存在");
            return "redirect:/admin/glossaries/input";
        }
        return "redirect:/admin/glossaries";
    }

    @GetMapping("/glossaries/input")
    public String input(Model model) {
        model.addAttribute("glossary", new Glossary());
        model.addAttribute("existingTerms", glossaryService.listAllGlossaries().stream().map(Glossary::getTerm).collect(Collectors.toList()));
        return "admin/glossary-input";
    }

    @GetMapping("/glossaries/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("glossary", glossaryService.getGlossary(id));
        return "admin/glossary-input";
    }

    @PostMapping("/glossaries/{id}")
    public String editPost(@Valid Glossary glossary, BindingResult result,
                           @PathVariable Long id, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "admin/glossary-input";
        }
        Glossary updated = glossaryService.updateGlossary(id, glossary);
        if (updated == null) {
            attributes.addFlashAttribute("message", "更新失败");
        } else {
            attributes.addFlashAttribute("message", "更新成功");
        }
        return "redirect:/admin/glossaries";
    }

    @GetMapping("/glossaries/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        glossaryService.deleteGlossary(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/glossaries";
    }
}