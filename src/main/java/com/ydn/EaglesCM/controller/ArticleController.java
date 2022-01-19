package com.ydn.EaglesCM.controller;

import com.ydn.EaglesCM.domain.Member;
import com.ydn.EaglesCM.dto.article.ArticleSaveForm;
import com.ydn.EaglesCM.service.ArticleService;
import com.ydn.EaglesCM.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;
    private final MemberService memberService;

    @GetMapping("/articles/write")
    public String showWrite(Model model){

        model.addAttribute("articleSaveForm", new ArticleSaveForm());

        return "usr/article/write";

    }

    @PostMapping("/articles/write")
    public String doWrite(@Validated ArticleSaveForm articleSaveForm, BindingResult bindingResult, Model model, Principal principal){

        if( bindingResult.hasErrors() ){
            return "usr/article/write";
        }

        try {

            Member findMember = memberService.findByLoginId(principal.getName());

            articleService.save(
                    articleSaveForm,
                    findMember
            );

        } catch (IllegalStateException e){

            model.addAttribute("err_msg", e.getMessage() );

            return "usr/article/write";

        }

        return "redirect:/";
    }


}
