package com.ydn.EaglesCM.controller;

import com.ydn.EaglesCM.domain.Member;
import com.ydn.EaglesCM.dto.member.CheckStatus;
import com.ydn.EaglesCM.dto.member.MemberLoginForm;
import com.ydn.EaglesCM.dto.member.MemberModifyForm;
import com.ydn.EaglesCM.dto.member.MemberSaveForm;
import com.ydn.EaglesCM.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @RequestMapping("/members/check/id")
    @ResponseBody
    public CheckStatus checkDuple(@RequestParam String loginId){

        boolean isExists = memberService.isDupleMember(loginId);
        CheckStatus checkStatus = new CheckStatus(isExists);

        return checkStatus;

    }


    /**회원가입 페이지 이동
     *
     * @param model
     * @return
     */
    @GetMapping("/members/join")
    public String showJoin(Model model){

        model.addAttribute("memberSaveForm", new MemberSaveForm());

        return "usr/member/join";
    }

    /**회원가입
     *
     * @param memberSaveForm
     * @param bindingResult
     * @param model
     * @return
     */
    @PostMapping("/members/join")
    public String doJoin(@Validated MemberSaveForm memberSaveForm, BindingResult bindingResult, Model model){

        if( bindingResult.hasErrors() ){
            return "usr/member/join";
        }

        try{
            memberService.save(memberSaveForm);
        } catch (Exception e){
            model.addAttribute("err_msg", e.getMessage());

            return "usr/member/join";
        }

        return "redirect:/";
    }

    @GetMapping("/members/login")
    public String showLogin(Model model){

        model.addAttribute("memberLoginForm", new MemberLoginForm());

        return "usr/member/login";
    }

    @GetMapping("/members/modify/{id}")
    public String showModify(@PathVariable(name = "id") Long id, Model model, Principal principal){

        Member findMember = memberService.findById(id);

        if( !findMember.getLoginId().equals(principal.getName()) ){
            return "redirect:/";
        }


        model.addAttribute("memberModifyForm", new MemberModifyForm(
                findMember
        ));

        return "usr/member/modify";

    }

    @PostMapping("/members/modify/{id}")
    public String doModify(@PathVariable(name="id") Long id, @Validated MemberModifyForm memberModifyForm, BindingResult bindingResult, Principal principal, Model model){

        if(bindingResult.hasErrors()){
            return "usr/member/modify";
        }

        Member findMember = memberService.findById(id);

        if(!findMember.getLoginId().equals(principal.getName()) ){
            return "redirect:/";
        }

        try {

            memberService.modifyMember(memberModifyForm, principal.getName());

        } catch (Exception e) {

            model.addAttribute("err_msg", e.getMessage());
            model.addAttribute("memberModifyForm", new MemberModifyForm(findMember));

            return "us/member/modify";
        }

        return "redirect:/";

    }

}
