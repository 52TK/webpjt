package com.ydn.EaglesCM.controller;

import com.ydn.EaglesCM.dto.adm.AdmMemberDetailDTO;
import com.ydn.EaglesCM.dto.member.MemberDTO;
import com.ydn.EaglesCM.service.AdmMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/adm")
@RequiredArgsConstructor
public class AdmMemberController {

    private final AdmMemberService admMemberService;

    @GetMapping("/members")
    public String showManageMember(Model model){

        List<MemberDTO> members = admMemberService.getMemberList();

        model.addAttribute("members", members);

        return "adm/member/main";
    }

    @GetMapping("/members/detail/{id}")
    public String showMemberDetail(@PathVariable(name = "id") Long id, Model model){

        AdmMemberDetailDTO memberDetail = admMemberService.getMemberDetail(id);

        model.addAttribute("member", memberDetail);

        return "adm/member/detail";

    }

    @GetMapping("/members/ban/{id}")
    public String doBanMember(@PathVariable(name="") Long id){

        admMemberService.banMember(id);

        return "redirect:/adm/members";


    }



}
