package com.ydn.EaglesCM.controller;

import com.ydn.EaglesCM.domain.Board;
import com.ydn.EaglesCM.domain.Member;
import com.ydn.EaglesCM.dto.article.ArticleListDTO;
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
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collections;
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

    @GetMapping("/boards/{id}")    // http://localhost:8085/boards/1?page=1 (디폴드값으로 1페이지를 보여줌)
    public String showBoardDetail(@PathVariable(name = "id")Long id, Model model, @RequestParam(name="page", defaultValue = "1") int page){

        int size = 10;

        try{

            BoardDTO boardDetail = boardService.getBoardDetail(id);

            List<ArticleListDTO> articleListDTO = boardDetail.getArticleListDTO();

            Collections.reverse(articleListDTO);
            // 0, 10, 20 ...
            int startIndex = (page - 1) * size;
            // 9, 19, 29 ... -> 15 -> 1.5 (총 게시글 개수 / 10(size)) -> 올림
            int lastIndex = ((page - 1) * size) + 9;

            int lastPage = (int)Math.ceil(articleListDTO.size()/(double)size);

            if( page == lastPage ){   // ?page=2 == 2  같을때

                lastIndex = articleListDTO.size();   // 15 [0,1,2,3,4,5,6,7, ... 14]

            } else if( page > lastPage ){   // ?page=100 == 2  클때

                return "redirect:/";

            } else {   // ?page=1 == 2  작을때

                lastIndex += 1;

            }

            // 페이지 자르기 (subList)
            List<ArticleListDTO> articlePage = articleListDTO.subList(startIndex, lastIndex);  // [0, 10] -> 0,1,2,3,4,5,6,7,8,9

            model.addAttribute("board", boardDetail);
            model.addAttribute("articles", articlePage);
            model.addAttribute("maxPage", lastPage);
            model.addAttribute("currentPage", page);

        } catch (Exception e){
            return "redirect:/";
        }

        return "adm/board/detail";

    }






}

