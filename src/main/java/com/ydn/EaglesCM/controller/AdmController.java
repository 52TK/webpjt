package com.ydn.EaglesCM.controller;

import com.ydn.EaglesCM.service.AdmService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/adm")
@RequiredArgsConstructor
public class AdmController {

    private final AdmService admService;

    @GetMapping("/page")
    public String showAdminPage(){
        return "adm/general/main";
    }

}
