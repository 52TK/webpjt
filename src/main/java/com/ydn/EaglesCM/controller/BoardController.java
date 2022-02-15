package com.ydn.EaglesCM.controller;

import com.ydn.EaglesCM.domain.Board;
import com.ydn.EaglesCM.domain.Member;
import com.ydn.EaglesCM.dto.board.BoardDTO;
import com.ydn.EaglesCM.dto.board.BoardModifyForm;
import com.ydn.EaglesCM.dto.board.BoardSaveForm;
import com.ydn.EaglesCM.service.BoardService;
import com.ydn.EaglesCM.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final MemberService memberService;

    // list 작업
    @GetMapping("/boards")
    public String showBoardList(Model model){

        List<Board> boardList = boardService.findAll();

        model.addAttribute("boardList", boardList);

        return "adm/board/list";

    }

    @GetMapping("/boards/{id}")
    public String showBoardDetail(@PathVariable(name = "id")Long id, Model model){

        try{
        BoardDTO boardDetail = boardService.getBoardDetail(id);
        model.addAttribute("board", boardDetail);
        } catch (Exception e){
            return "redirect:/";
        }

        return "adm/board/detail";

    }






}

